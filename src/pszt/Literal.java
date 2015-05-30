package pszt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Literal {
private
	List<Literal> argumenty;
	boolean negacja=false;
	
	// mozna podstawiac zmienna za zmienna, mozna podstawiac stala za zmienna
	// ale nie mozna zmiennej za stala, wiec potrzebne rozroznienie
	boolean stala=true;
	String nazwa;
public
	Literal(){
		argumenty = new ArrayList<Literal>();
	}
	Literal(String literalString){
	//uwzglednic stale/zmienne
		if(literalString.startsWith("!")){
			negacja = true;
			this.nazwa=literalString.substring(1);
		}
		else{
			this.nazwa = literalString;
		}
		argumenty = new ArrayList<Literal>();
		argumenty = znajdzArgumenty(literalString);
		System.out.println("ARGUMENTY LITERALU");
		if(argumenty.size()!=0){
			stala = false;
		}
		for(Literal l: argumenty)
		{
			System.out.println(l.nazwa+ " " + argumenty.size() + " " + this.nazwa);
		}
		System.out.println("KONIEC ARG LITERALU");
	}
	Literal(Literal literal)
	{
		this.negacja = literal.negacja;
		this.stala = literal.stala;
		this.nazwa = new String(literal.nazwa);	//zeby nie przypisywac referencji
		this.argumenty = new ArrayList<Literal>();
		
		for(Literal l: literal.argumenty)
		{
			Literal nowy = new Literal(l.nazwa);
			nowy.stala = l.stala;
			this.argumenty.add(nowy);
		}
	}
	
	
	String getNazwa(){
		return nazwa;
	}
	boolean getNegacja(){
		return negacja;
	}
	
	void setNegacja(boolean a){
		negacja = a;
	}
	
	void setStala(boolean a){
		stala = a;
	}
	List<Literal> getArgumenty(){
		return argumenty;
	}
	
	private List<Literal> znajdzArgumenty(String literalString) {
		List<Literal> listaLiteralowArgumenty = new ArrayList<Literal>();
		String[] temp2;
		int indeksPoczatku;
		int indeksKonca;
		indeksPoczatku = literalString.indexOf('(');
		indeksKonca = literalString.lastIndexOf(')');
		if(!literalString.contains("("))
		{
			return new ArrayList<Literal>();
		}
		if(!literalString.contains(")"))
		{
			return new ArrayList<Literal>();
		}
		System.out.println(literalString.substring(indeksPoczatku+1, indeksKonca));
		String temp = new String(literalString.substring(indeksPoczatku+1, indeksKonca));
		temp2 = temp.split(",");
		for(String s : temp2)
		{
			listaLiteralowArgumenty.add(new Literal(s));	//i niech sie dzieje zuo rekurencja odpalanych konstruktorow, to nie moze zadzialac :D	
		}
		return listaLiteralowArgumenty;
		
		
		
	}
	
	static boolean sprawdzArgumenty(Literal literal1, Literal literal2){
		int rozmiar;
		int licznik = 0;
		if(literal1.argumenty.size() != literal2.argumenty.size())
			return false;
		
		rozmiar = literal1.argumenty.size();
		for( int i = 0; i < rozmiar; i++)
		{
			if( literal1.argumenty.get(i).negacja == literal2.argumenty.get(i).negacja && 
					literal1.argumenty.get(i).nazwa.equals(literal2.argumenty.get(i).nazwa))
					licznik++;
		}
		
		if(licznik == rozmiar) return true;
		else return false;
	}
	
	static boolean podstaw(Literal literal1, Literal literal2, List<Literal> listaLiteralow1, List<Literal> listaLiteralow2){
		ArrayList<Integer> paryZmiennych = new ArrayList<Integer>();	//miejsca, na ktorych mozemy zrobic podstawienia
		ArrayList<Integer> paryRozne = new ArrayList<Integer>();
		ArrayList<Literal> argUsuniete1 = new ArrayList<Literal>();	//jakie argumenty wyrzucilismy z pierwszej klauzuli
		ArrayList<Literal> argDodane1 = new ArrayList<Literal>();	//jakie argumenty dodalismy do pierwszej klauzuli
		ArrayList<Literal> argUsuniete2 = new ArrayList<Literal>();
		ArrayList<Literal> argDodane2 = new ArrayList<Literal>();

		Literal tmpLiteral1 = new Literal(literal1);	//tworze kopie dla bezpieczenstwa oryginalnych danych
		Literal tmpLiteral2 = new Literal(literal2);
		
		
		// czesc wykonujaca "do oporu" podstawienia stalych
		while(mozliwePodstawienie(tmpLiteral1, tmpLiteral2, true))
		{
			int licznik = 0;
			
			for(int i = 0; i < tmpLiteral1.getArgumenty().size(); i++)	//szukanie par stala/zmienna
			{
				if(!tmpLiteral1.getArgumenty().get(i).nazwa.equals(tmpLiteral2.getArgumenty().get(i).nazwa))
				paryRozne.add(i);
			}
			
			for(int i = 0; i < paryRozne.size(); i++)
			{
				Literal tmp1 = tmpLiteral1.getArgumenty().get(paryRozne.get(i));
				Literal tmp2 = tmpLiteral2.getArgumenty().get(paryRozne.get(i));
				
				
				if(tmp1.stala)
				{
					argUsuniete2.add(new Literal(tmp2));
					argDodane2.add(new Literal(tmp1));
					tmp2.nazwa = new String(tmp1.nazwa);
					tmp2.stala = true;
					licznik++;
					
				}
				else if(tmp2.stala)
				{
					argUsuniete1.add(new Literal(tmp1));
					argDodane1.add(new Literal(tmp2));
					tmp1.nazwa = new String(tmp2.nazwa);
					tmp1.stala = true;
					licznik++;
				}
				
				poprawUsuniete(tmpLiteral1, argUsuniete1, argDodane1);
				poprawUsuniete(tmpLiteral2, argUsuniete2, argDodane2);
			}
			paryRozne.clear();
			if(licznik == 0) break;
		}
		
		while(mozliwePodstawienie(tmpLiteral1, tmpLiteral2, false))
		{
			int licznik = 0;
			for(int i = 0; i < tmpLiteral1.getArgumenty().size(); i++)	//szukanie par zmiennych
			{
				if((tmpLiteral1.getArgumenty().get(i).stala == tmpLiteral2.getArgumenty().get(i).stala) && 
						!tmpLiteral1.getArgumenty().get(i).nazwa.equals(tmpLiteral2.getArgumenty().get(i).nazwa))
				{
					if (tmpLiteral1.getArgumenty().get(i).stala == true)
						return false;	//dwie stale, wychodzimy!
					else paryZmiennych.add(i);
				}
			}
			
			for(int i = 0; i < paryZmiennych.size(); i++)
			{
				Literal tmp1 = tmpLiteral1.getArgumenty().get(paryZmiennych.get(i));
				Literal tmp2 = tmpLiteral2.getArgumenty().get(paryZmiennych.get(i));
				
				if(!czyPodstawiono(tmp1.nazwa, argUsuniete2))
				{
					argUsuniete2.add(new Literal(tmp2));
					argDodane2.add(new Literal(tmp1));
					tmp2.nazwa = new String(tmp1.nazwa);
					tmp2.stala = tmp1.stala;
					licznik++;
					
				}
				else if(!czyPodstawiono(tmp2.nazwa, argUsuniete1))
				{
					argUsuniete1.add(new Literal(tmp1));
					argDodane1.add(new Literal(tmp2));
					tmp1.nazwa = new String(tmp2.nazwa);
					tmp1.stala = tmp2.stala;
					licznik++;
				}
				
				poprawUsuniete(tmpLiteral1, argUsuniete1, argDodane1);
				poprawUsuniete(tmpLiteral2, argUsuniete2, argDodane2);	
			}
			paryZmiennych.clear();
			if(licznik == 0) break;
		}
		
		
		System.out.println("K1 przed podstawieniem:");
		for(Literal l: literal1.argumenty)
			System.out.println(l.nazwa);
		System.out.println("K2 przed podstawieniem:");
		for(Literal l: literal2.argumenty)
			System.out.println(l.nazwa);
		System.out.println("K1 po podstawieniu:");
		for(Literal l: tmpLiteral1.argumenty)
			System.out.println(l.nazwa);
		System.out.println("K2 po podstawieniu:");
		for(Literal l: tmpLiteral2.argumenty)
			System.out.println(l.nazwa);
		
		System.out.println("Dodane do K1:");
		for(Literal arg: argDodane1)
			System.out.println(arg.nazwa);
		System.out.println("Usuniete z K1:");
		for(Literal arg: argUsuniete1)
			System.out.println(arg.nazwa);
		System.out.println("Dodane do K2:");
		for(Literal arg: argDodane2)
			System.out.println(arg.nazwa);
		System.out.println("Usuniete z K2:");
		for(Literal arg: argUsuniete2)
			System.out.println(arg.nazwa);
		

		for(Literal lit1: listaLiteralow1)
		{
			poprawUsuniete(lit1, argUsuniete1, argDodane1);
		}
		
		for(Literal lit2: listaLiteralow2)
		{
			poprawUsuniete(lit2, argUsuniete2, argDodane2);
		}
		
		return true;
	}
	
	static boolean czyPodstawiono(String nazwa, ArrayList<Literal> usuniete)
	{
		boolean wynik = false;
		
		for(Literal literal: usuniete)
		{
			if(literal.nazwa.equals(nazwa)) wynik = true;
		}
		
		return wynik;
	}
	
	static void poprawUsuniete(Literal literal1, ArrayList<Literal> usuniete, ArrayList<Literal> dodane)
	{
		int i = 0;
		for(Literal usunietyArgument: usuniete)
		{
			for(Literal argument: literal1.getArgumenty())
			{
				if(argument.getNazwa().equals(usunietyArgument.getNazwa()))
				{
					argument.nazwa = new String(dodane.get(i).getNazwa());
					argument.stala = dodane.get(i).stala;
				}
			}
			i++;
		}
	}
		
	static boolean mozliwePodstawienie(Literal literal1, Literal literal2, boolean wariant)
	{
		boolean mozliwePodstawienie = false;
		int i = 0;
		
		for(Literal l1: literal1.getArgumenty())
		{
			if(wariant && l1.stala!=literal2.getArgumenty().get(i).stala)
				mozliwePodstawienie = true;
			
			else if(!wariant && l1.stala == false && l1.stala == literal2.getArgumenty().get(i).stala && 
					!l1.getNazwa().equals(literal2.getArgumenty().get(i).getNazwa()))
				mozliwePodstawienie = true;
			i++;
		}
		return mozliwePodstawienie;
	}
}
