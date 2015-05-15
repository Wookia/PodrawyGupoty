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
	
	static boolean podstaw(Literal literal1, Literal literal2, List<Literal> listaLiteralow1, List<Literal> listaLiteralow2)
	{
		ArrayList<Integer> paryZmiennych = new ArrayList<Integer>();	//miejsca, na ktorych mozemy zrobic podstawienia
		ArrayList<Integer> paryRozne = new ArrayList<Integer>();
		ArrayList<Integer> podstawieniaZmiennych = new ArrayList<Integer>();	//miejsca, na ktorych wykonalismy podstawienia
		ArrayList<Integer> podstawieniaPar = new ArrayList<Integer>();
		
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
		

		for(int i = 0; i < paryZmiennych.size(); i++)
		{
			
			Literal tmp1 = tmpLiteral1.getArgumenty().get(paryZmiennych.get(i));
			Literal tmp2 = tmpLiteral2.getArgumenty().get(paryZmiennych.get(i));
			
			tmp2.nazwa = new String(tmp1.nazwa);
			
		}	
		
		if(sprawdzArgumenty(tmpLiteral1, tmpLiteral2))
			sukcesPodstawienia = true;
		
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
		
		
		return sukcesPodstawienia;
	}
}
