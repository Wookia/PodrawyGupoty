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
			if (klauzula1.czyFalsz())return true;
			
		}
		return false;
	}

	void addMikroBaza(ArrayList<Klauzula> mikroBaza){
		for (Klauzula klauzula: mikroBaza){
			if (!czyIstnieje(klauzula))bazaWiedzy.add(klauzula);
		}
	}
}
