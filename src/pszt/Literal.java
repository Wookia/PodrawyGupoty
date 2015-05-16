package pszt;

import java.util.ArrayList;
import java.util.List;
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
	
	
	//uwaga, nowe :D
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
		ArrayList<String> argUsuniete1 = new ArrayList<String>();	//jakie argumenty wyrzucilismy z pierwszej klauzuli
		ArrayList<String> argDodane1 = new ArrayList<String>();	//jakie argumenty dodalismy do pierwszej klauzuli
		ArrayList<String> argUsuniete2 = new ArrayList<String>();
		ArrayList<String> argDodane2 = new ArrayList<String>();
		
		Literal tmpLiteral1 = new Literal(literal1);	//tworze kopie dla bezpieczenstwa oryginalnych danych
		Literal tmpLiteral2 = new Literal(literal2);
		
		boolean sukcesPodstawienia = false;
	
		for(int i = 0; i < tmpLiteral1.getArgumenty().size(); i++)	//szukanie par zmiennych lub par stala/zmienna
		{
			if((tmpLiteral1.getArgumenty().get(i).stala == tmpLiteral2.getArgumenty().get(i).stala) && 
					!tmpLiteral1.getArgumenty().get(i).nazwa.equals(tmpLiteral2.getArgumenty().get(i).nazwa))
			{
				if (tmpLiteral1.getArgumenty().get(i).stala == true)
					return false;	//dwie stale, wychodzimy!
				else paryZmiennych.add(i);
			}
			else if(!tmpLiteral1.getArgumenty().get(i).nazwa.equals(tmpLiteral2.getArgumenty().get(i).nazwa))
				paryRozne.add(i);
		}
		

		for(int i = 0; i < paryZmiennych.size(); i++){
			
			Literal tmp1 = tmpLiteral1.getArgumenty().get(paryZmiennych.get(i));
			Literal tmp2 = tmpLiteral2.getArgumenty().get(paryZmiennych.get(i));
			
			argDodane2.add(new String(tmp1.nazwa));
			argUsuniete2.add(new String(tmp2.nazwa));
			tmp2.nazwa = new String(tmp1.nazwa);
			
		}	
		
		for(int i = 0; i < paryRozne.size(); i++){
			
			Literal tmp1 = tmpLiteral1.getArgumenty().get(paryRozne.get(i));
			Literal tmp2 = tmpLiteral2.getArgumenty().get(paryRozne.get(i));
			
			if(tmp2.stala){
				argDodane1.add(new String(tmp2.nazwa));
				argUsuniete1.add(new String(tmp1.nazwa));
				tmp1.stala = true;
				tmp1.nazwa = new String(tmp2.nazwa);
				
			}
			else{
				argDodane2.add(new String(tmp1.nazwa));
				argUsuniete2.add(new String(tmp2.nazwa));
				tmp2.stala = true;
				tmp2.nazwa = new String(tmp1.nazwa);
				
			}
			
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
		System.out.println(argDodane1);
		System.out.println("Usuniete z K1:");
		System.out.println(argUsuniete1);
		System.out.println("Dodane do K2:");
		System.out.println(argDodane2);
		System.out.println("Usuniete z K2:");
		System.out.println(argUsuniete2);
		
		if(sprawdzArgumenty(tmpLiteral1, tmpLiteral2)){
			sukcesPodstawienia = true;
			literal1 = tmpLiteral1;
			literal2 = tmpLiteral2;
			for(Literal l: listaLiteralow1)
			{
				for(Literal arg: l.argumenty)
				{	
					int i = 0;
					for(String nazwaUsunietego: argUsuniete1)
					{
						if(arg.nazwa.equals(nazwaUsunietego))
						{
							arg.nazwa = new String(argDodane1.get(i));
						}
					}
					i++;
				}
			}
			
			for(Literal l: listaLiteralow2)
			{
				for(Literal arg: l.argumenty)
				{	
					int i = 0;
					for(String nazwaUsunietego: argUsuniete2)
					{
						if(arg.nazwa.equals(nazwaUsunietego))
						{
							arg.nazwa = new String(argDodane2.get(i));
						}
					}
					i++;
				}
			}
		}
		return sukcesPodstawienia;
	}
}
