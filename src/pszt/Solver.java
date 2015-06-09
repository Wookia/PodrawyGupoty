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
		Log solveWszerz(BazaWiedzy bazaTez){
			Log log = new Log();
			log.dodajBaz�(bazaWiedzy, false);
			log.dodajTeze(bazaTez, false);
			bazaWiedzy.addMikroBaza(bazaTez.getBaza());
			int i=1;
			int size = bazaWiedzy.getBaza().size();
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
					for (Klauzula klauzula2: bazaWiedzy.getBaza()){
						if(czyMerge(klauzula1, klauzula2) == 1){
							Klauzula klauzula = new Klauzula(klauzula1, klauzula2);
							mikroBaza.add(klauzula);
							log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, false);
							if(klauzula.czyFalsz()){
								stop=true;
								break;
							}
						}
						else if(czyMerge(klauzula1, klauzula2) == 2)
						{
							Klauzula klauzula = Klauzula.wykonajPodstawienie(klauzula1, klauzula2);
							System.out.println("Probuje podstawic!");
							if(klauzula != null && !bazaWiedzy.czyIstnieje(klauzula))
							{
								if(klauzula.czyFalsz())
								{
									stop=true;
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, false);
									break;
								}
								else{
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, false);
								}
							}
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
		Log solveLiniowe(BazaWiedzy bazaTez){
			Log log = new Log();
			log.dodajBaz�(bazaWiedzy, false);
			log.dodajTeze(bazaTez, false);
			bazaWiedzy.addMikroBaza(bazaTez.getBaza());
			Klauzula aktualna = bazaWiedzy.getBaza().get(bazaWiedzy.getBaza().size()-1);
			int size = bazaWiedzy.getBaza().size();
			int i=1;
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
						if(czyMerge(klauzula1, aktualna) == 1){
							Klauzula klauzula = new Klauzula(klauzula1, aktualna);
							mikroBaza.add(klauzula);
							log.dodajKlauzule(klauzula1, aktualna, klauzula, i, false);
							aktualna = klauzula;
							
							if(klauzula.czyFalsz()){
								stop=true;
							}
							break;
						
						}
						else if(czyMerge(klauzula1, aktualna) == 2)
						{
							Klauzula klauzula = Klauzula.wykonajPodstawienie(klauzula1, aktualna);
							System.out.println("Probuje podstawic!");
							if(klauzula != null  && !bazaWiedzy.czyIstnieje(klauzula))
							{
								if(klauzula.czyFalsz())
								{
									stop=true;
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, aktualna, klauzula, i, false);
									aktualna = klauzula;
									break;
								}
								else{
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, aktualna, klauzula, i, false);
									aktualna = klauzula;
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
				if(size==bazaWiedzy.getBaza().size()){
					System.out.println("Teza nieprawdziwa lub zadanie nierozwi�zywalne t� metod�");
					break;
				}
				size=bazaWiedzy.getBaza().size();

			}
			return log;
		}
		
		Log solveZbiorUzasadnien(BazaWiedzy bazaTez){
			Log log = new Log();
			log.dodajBaz�(bazaWiedzy, false);
			log.dodajTeze(bazaTez, true);
			boolean stop = false;
			boolean niemozliwyDowod = false;
			BazaWiedzy zbiorUzasadnien = new BazaWiedzy();
			zbiorUzasadnien.addMikroBaza(bazaTez.getBaza());
			int i=1;
			while(!bazaWiedzy.sprawdzSprzecznosc())
			{
				for (Klauzula klauzula1: bazaWiedzy.getBaza())
				{
					for(Klauzula klauzula2: zbiorUzasadnien.getBaza())
					{
						if(czyMerge(klauzula1, klauzula2) == 1)
						{
							Klauzula klauzula = new Klauzula(klauzula1, klauzula2);
							if(klauzula.czyFalsz())
							{
								stop=true;
								mikroBaza.add(klauzula);
								log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, true);
								
								break;
							}
							else{
								mikroBaza.add(klauzula);
								log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, true);
							}
							
						}
						else if(czyMerge(klauzula1, klauzula2) == 2)
						{
							Klauzula klauzula = Klauzula.wykonajPodstawienie(klauzula1, klauzula2);
							System.out.println("Probuje podstawic!");
							if(klauzula != null  && !bazaWiedzy.czyIstnieje(klauzula))
							{
								if(klauzula.czyFalsz())
								{
									stop=true;
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, true);
									break;
								}
								else{
									mikroBaza.add(klauzula);
									log.dodajKlauzule(klauzula1, klauzula2, klauzula, i, true);
								}
							}
						}
					}
					if (stop) break;
				}

				i=i+1;
				if(mikroBaza.size() == 0)
				{
					niemozliwyDowod = true;
					break;
				}
				bazaWiedzy.addMikroBaza(mikroBaza);
				zbiorUzasadnien.addMikroBaza(mikroBaza);
				mikroBaza.clear();
			}
			if(niemozliwyDowod)
				System.out.println("Teza nieprawdziwa.");
			
			System.out.println("Klauzule wchodzace w sklad zbioru uzasadnien:");
			for(Klauzula k: zbiorUzasadnien.getBaza())
			{
				System.out.println("Klauzula:");
				for(Literal l: k.getLiteraly())
				{
					System.out.println(l.negacja + l.nazwa);
				}
				System.out.println("");
			}

			return log;
		}
		
		
 		Log solveNajkrotsze(BazaWiedzy bazaTez){
			Log log = new Log();
			log.dodajBaz�(bazaWiedzy, false);
			log.dodajTeze(bazaTez, false);
			bazaWiedzy.addMikroBaza(bazaTez.getBaza());//powinno dzialac chyba :D
			int najkrotszeZlaczenie = 99999;
			int i = 1;
			Klauzula temp1=null;
			Klauzula temp2=null;
			Klauzula toNajkrotszeZlaczenie = null;
			boolean stop = false;
			while(!bazaWiedzy.sprawdzSprzecznosc()){
				for (Klauzula klauzula1: bazaWiedzy.getBaza()){
					for(Klauzula klauzula2: bazaWiedzy.getBaza()) {
						if(czyMerge(klauzula1, klauzula2) == 1){
							Klauzula klauzula = new Klauzula(klauzula1,klauzula2);
							if(klauzula.getLiteraly().size()<najkrotszeZlaczenie)
							{
								toNajkrotszeZlaczenie = klauzula;
								najkrotszeZlaczenie = klauzula.getLiteraly().size();
								temp1 = klauzula1;
								temp2 = klauzula2;
							}
						
						}
						else if(czyMerge(klauzula1, klauzula2) == 2)
						{
							System.out.println("Probuje podstawic!");
							Klauzula klauzula = Klauzula.wykonajPodstawienie(klauzula1, klauzula2);
							if(klauzula != null && !bazaWiedzy.czyIstnieje(klauzula))
							{
								if(klauzula.czyFalsz())
								{
									if(klauzula.getLiteraly().size()<najkrotszeZlaczenie){
										toNajkrotszeZlaczenie = klauzula;
										najkrotszeZlaczenie = klauzula.getLiteraly().size();
										temp1 = klauzula1;
										temp2 = klauzula2;
										stop=true;
										break;
									}
								}
								else{
									if(klauzula.getLiteraly().size()<najkrotszeZlaczenie){
										toNajkrotszeZlaczenie = klauzula;
										najkrotszeZlaczenie = klauzula.getLiteraly().size();
										temp1 = klauzula1;
										temp2 = klauzula2;
									}
								}
							}
							
						}
					}
					if(stop)break;
				}
				if(toNajkrotszeZlaczenie==null)
					break;
				if(toNajkrotszeZlaczenie.czyFalsz())
				{
					stop = true;
				}
				mikroBaza.add(toNajkrotszeZlaczenie);
				log.dodajKlauzule(temp1, temp2, toNajkrotszeZlaczenie, i, false); //1 dla przypalu
				bazaWiedzy.addMikroBaza(mikroBaza); //poczebne
				if(mikroBaza.isEmpty())break;
				mikroBaza.clear(); //poczebne
				najkrotszeZlaczenie = 9999;
				i=i+1;
				if(stop)
				{
					System.out.println("Podobno cos jest");
					break;
				}
			}
			return log;
		} 

}
