package pszt;

import java.util.ArrayList;
import java.util.List;
public class BazaWiedzy {
private
	ArrayList<Klauzula> bazaWiedzy;
	BazaLiteralow bazaLiteralow;
	
public
	BazaWiedzy(){
		bazaWiedzy = new ArrayList<Klauzula>();
	}
	boolean dodajKlauzule(List<String> literaly){
		Klauzula klauzula = new Klauzula(literaly);
		if (!czyIstnieje(klauzula)){
			bazaWiedzy.add(klauzula);
			return true;
		}
		return false;
	}
	boolean dodajKlauzule(Klauzula klauzula){
		if (!czyIstnieje(klauzula)){
			bazaWiedzy.add(klauzula);
			return true;
		}
		return false;
	}
	void czytajDzifko(){
		int i=1;
		for (Klauzula klauzula: bazaWiedzy){
			System.out.println("K"+i);
			for(Literal literal: klauzula.getLiteraly()){
				if(literal.getNegacja()){
					System.out.print("~");
				}
				System.out.print(literal.getNazwa()+" ");
			}
			System.out.print("\n");
			i=i+1;
		}
	}
	ArrayList<Klauzula> getBaza(){
		return bazaWiedzy;
	}
	
	boolean czyIstnieje(Klauzula klauzula){
		if (!bazaWiedzy.isEmpty()){
			for (Klauzula klauz : bazaWiedzy){
				if (klauz.czyRowne(klauzula)) return true;
			}
			
		}
		return false;
	}
	boolean sprawdzSprzecznosc(){
		for(Klauzula klauzula1: bazaWiedzy){
			for (Klauzula klauzula2: bazaWiedzy){
				if(klauzula1.czySprzeczne(klauzula2)){
					return true;
				}
			}
			
		}
		return false;
	}

	void addMikroBaza(ArrayList<Klauzula> mikroBaza){
		for (Klauzula klauzula: mikroBaza){
			if (!czyIstnieje(klauzula))bazaWiedzy.add(klauzula);
		}
	}
}
