package pszt;

import java.util.ArrayList;

public class Solver {
	private
		Klauzula teza;
		BazaWiedzy bazaWiedzy;
		ArrayList<Klauzula> mikroBaza = new ArrayList<Klauzula>();
		void rezolucja(){
			
		}
		boolean czyMerge(Klauzula K1, Klauzula K2){
			int check = 0;
			for (Literal literal1: K1.getLiteraly()){
				for(Literal literal2: K2.getLiteraly()){
					if(literal1.getNazwa().equals((literal2).getNazwa()) && literal1.getNegacja()!=literal2.getNegacja()){
						check = check + 1;
					}
				}
			}
			Klauzula klauzula = new Klauzula(K1, K2);
			if(bazaWiedzy.czyIstnieje(klauzula))return false;
			if (check==1)return true;
			return false;
		}
	public
		Solver(BazaWiedzy bazaWiedzy){
			this.bazaWiedzy = bazaWiedzy;
		}
		void solveWszerz(ArrayList<String> teza){
			bazaWiedzy.dodajKlauzule(teza);
			Log log = new Log();
			log.dodajBazê(bazaWiedzy);
			int i=1;
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
					for (Klauzula klauzula2: bazaWiedzy.getBaza()){
						if(czyMerge(klauzula1, klauzula2)){
							Klauzula klauzula = new Klauzula(klauzula1, klauzula2);
							mikroBaza.add(klauzula);
							log.dodajKlauzule(klauzula1, klauzula2, klauzula, i);
							if(klauzula.czyFalsz()){
								stop=true;
								break;
							}
						}
					}
				if(stop)break;
				}
				i=i+1;
				bazaWiedzy.addMikroBaza(mikroBaza);
				mikroBaza.clear();
				if(stop)break;
				

			}
			log.piszDzifko();
		}

}
