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
			boolean check = true;
			for(Literal literal: BazaLiteralow.baza){
				if(literalString.equals(literal.getNazwa())){
					check = false;
					literaly.add(literal);
				}
			}
			if(check){
				BazaLiteralow.baza.add(new Literal(literalString));
				literaly.add(BazaLiteralow.baza.get(BazaLiteralow.baza.size()-1));
			}
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
					//wyjeb duplikaty
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
				if (literal1.getNazwa().equals(literal2.getNazwa()) && literal1.getNegacja()==literal2.getNegacja()){
					check=check-1;
					break;
				}
			}
		}
		if (check!=0) return false;
		return true;
	}
	boolean czySprzeczne(Klauzula klauzula){
		if (klauzula.literaly.size()!= literaly.size()) return false;
		int check = klauzula.literaly.size();
		for (Literal literal1 : klauzula.literaly)
		{
			for(Literal literal2 : literaly){
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
}
