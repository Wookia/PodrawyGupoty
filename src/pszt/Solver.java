package pszt;

import java.util.ArrayList;

public class Solver {
	private
		Klauzula teza;
		BazaWiedzy bazaWiedzy;
		ArrayList<Klauzula> mikroBaza = new ArrayList<Klauzula>();
		
		//zmiana typu zwracanego na int:
		//0 - nie da sie
		//1 - da sie
		//2 - podstawionko
		int czyMerge(Klauzula K1, Klauzula K2){
			int check = 0, checkPodstawienie = 0;
			
			for (Literal literal1: K1.getLiteraly()){
				for(Literal literal2: K2.getLiteraly()){
					if(literal1.getNazwa().equals((literal2).getNazwa()) && literal1.getNegacja()!=literal2.getNegacja())
					{
						if(!Literal.sprawdzArgumenty(literal1, literal2))
						{
							checkPodstawienie++;
							continue;
						}
						check++;
					}
				}
			}
			Klauzula klauzula = new Klauzula(K1, K2);
			if(bazaWiedzy.czyIstnieje(klauzula)) return 0;
			if (check==1)return 1;
			else if(checkPodstawienie!=0) return 2;
			return 0;
		}
	public
		Solver(BazaWiedzy bazaWiedzy){
			this.bazaWiedzy = bazaWiedzy;
		}
		Log solveWszerz(ArrayList<String> teza){
			bazaWiedzy.dodajKlauzule(teza);
			Log log = new Log();
			log.dodajBazê(bazaWiedzy);
			int i=1;
			int size = bazaWiedzy.getBaza().size();
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
					for (Klauzula klauzula2: bazaWiedzy.getBaza()){
						if(czyMerge(klauzula1, klauzula2) == 1){
							Klauzula klauzula = new Klauzula(klauzula1, klauzula2);
							mikroBaza.add(klauzula);
							log.dodajKlauzule(klauzula1, klauzula2, klauzula, i);
							if(klauzula.czyFalsz()){
								stop=true;
								break;
							}
						}
						else if(czyMerge(klauzula1, klauzula2) == 2){
							
							
							// do zrobienia wstawienie podstawionej klauzuli
							//pamietac, ze Klauzula.wykonajPodstawienie() zwraca tez nulla!
							
						}
					}
				if(stop)break;
				}
				i=i+1;
				bazaWiedzy.addMikroBaza(mikroBaza);
				mikroBaza.clear();
				if(stop)break;
				
				if(size==bazaWiedzy.getBaza().size()){
					System.out.println("Teza nieprawdziwa");
					break;
				}
				size=bazaWiedzy.getBaza().size();
			}
			return log;
		}
		Log solveLiniowe(ArrayList<String> teza){
			bazaWiedzy.dodajKlauzule(teza);
			Klauzula aktualna = bazaWiedzy.getBaza().get(bazaWiedzy.getBaza().size()-1);
			Log log = new Log();
			log.dodajBazê(bazaWiedzy);
			int size = bazaWiedzy.getBaza().size();
			int i=1;
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
						if(czyMerge(klauzula1, aktualna) == 1){
							Klauzula klauzula = new Klauzula(klauzula1, aktualna);
							mikroBaza.add(klauzula);
							log.dodajKlauzule(klauzula1, aktualna, klauzula, i);
							aktualna = klauzula;
							
							if(klauzula.czyFalsz()){
								stop=true;
							}
							break;
						
						}
				if(stop)break;
				}
				i=i+1;
				bazaWiedzy.addMikroBaza(mikroBaza);
				mikroBaza.clear();
				if(stop)break;
				if(size==bazaWiedzy.getBaza().size()){
					System.out.println("Teza nieprawdziwa lub zadanie nierozwi¹zywalne t¹ metod¹");
					break;
				}
				size=bazaWiedzy.getBaza().size();

			}
			return log;
		}

}
