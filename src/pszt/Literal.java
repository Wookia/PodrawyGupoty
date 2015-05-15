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
		this.argumenty.addAll(literal.argumenty);
	}
	String getNazwa(){
		return nazwa;
	}
	boolean getNegacja(){
		return negacja;
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
		Literal tmpLiteral1 = new Literal(literal1);	//tworze kopie dla bezpieczenstwa oryginalnych danych
		Literal tmpLiteral2 = new Literal(literal2);
		
		
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
		
		/*REKURENCJA, DODAC METODY KORZYSTAJACE Z REKURENCJI
		
		wykonac dla kazdej kombinacji podstawien przy parach zmiennych podstawienie jednej za druga
		jesli nigdzie nie wyjdzie fajne podstawienie, to dorzucic jeszcze podstawianie stalej za zmienna
		ale przy podstawianiu stalej za zmienna wykonywac to az do powrotu rekurencji, aby okreslic
		podstawienie, ktore podstawia jak najmniej stalych (najbardziej ogolne)
		
		
		w skrocie - przy drugiej turze, czyli patrzeniu tez na stale, zapamietywac wszystkie podstawienia stalych
		podstawienia zmiennych zapamietujemy tylko ostateczne, aby "poprawic" potem cale listy literalow
		*/
		return false;
	}
}
