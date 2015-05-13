package pszt;

import java.util.ArrayList;
import java.util.List;
public class Klauzula {

private
	List<Literal> literaly;

public
	Klauzula(){
		literaly = new ArrayList<Literal>();
	}
	Klauzula(List<String> listaLiteralow){
		literaly = new ArrayList<Literal>();
		for (String literalString: listaLiteralow){
			Literal literal = new Literal(literalString);
			Literal liter = BazaLiteralow.dodajLiteral(literal);
			if(liter==null)literaly.add(literal);
			else literaly.add(liter);
		}
		
	}
	Klauzula(Klauzula K1, Klauzula K2){
		literaly = new ArrayList<Literal>();
		for (Literal literal1 : K1.literaly){
			boolean znaleziony = false;
			for(Literal literal2 : K2.literaly){
				if(literal1.nazwa.equals(literal2.nazwa) && 
						literal1.negacja != literal2.negacja)
				{
					znaleziony = true;
					this.literaly.addAll(K1.literaly);
					this.literaly.addAll(K2.literaly);
					if(this.literaly.remove(literal1) == false) System.out.println("Nie udalo sie utworzyc nowej klauzuli.\n");
					this.literaly.remove(literal2);
					
					break;
				}
			}
			if(znaleziony == true) break;
		}
	}
	boolean czyRowne(Klauzula klauzula){
		if (klauzula.literaly.size()!= literaly.size()) return false;
		int check = klauzula.literaly.size();
		for (Literal literal1 : klauzula.literaly)
		{
			for(Literal literal2 : literaly){
				
				//dodane sprawdzanie argumentów!!! do przetestowania
				
				if (literal1.getNazwa().equals(literal2.getNazwa()) && literal1.getNegacja()==literal2.getNegacja() && 
						Literal.sprawdzArgumenty(literal1, literal2)){
					check=check-1;
					break;
				}
			}
		}
		if (check!=0) return false;
		return true;
	}
	boolean czySprzeczne(Klauzula klauzula){
		if (klauzula.literaly.size()>1 || literaly.size()>1) return false;
		int check = klauzula.literaly.size();
		for (Literal literal1 : klauzula.literaly)
		{
			for(Literal literal2 : literaly){
				
				//TODO
				//dodaæ sprawdzanie argumentów!!!
				
				if (literal1.getNazwa().equals( literal2.getNazwa()) && literal1.getNegacja()!=literal2.getNegacja()){
					check=check-1;
					break;
				}
			}
		}
		if (check!=0) return false;
		return true;
		
	}
	List<Literal> getLiteraly(){
		return literaly;
	}
	boolean czyFalsz(){
		if (literaly.size()==0)return true;
		return false;
	}
	String getNazwa(){
		String nazwa = "(";
		for(Literal literal: literaly){
			if (literal.getNegacja())nazwa=nazwa+"~";
			nazwa=nazwa+literal.getNazwa();
			if(literaly.size()-1!=literaly.indexOf(literal))nazwa=nazwa+"v";
		}
		nazwa=nazwa+")";
		return nazwa;
	}
}
