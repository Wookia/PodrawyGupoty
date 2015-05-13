package pszt;

import java.util.ArrayList;
import java.util.List;
public class Literal {
private
	List<Literal> argumenty;
	boolean negacja=false;
	String nazwa;
public
	Literal(String literalString){
		if(literalString.startsWith("!")){
			negacja = true;
			this.nazwa=literalString.substring(1);
		}
		else{
			this.nazwa = literalString;
		}
		argumenty = new ArrayList<Literal>();
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
}
