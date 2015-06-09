package pszt;

import java.util.ArrayList;
import java.util.List;

public class Log {
	private 
		List<LogKlauzula> log;
	public
	Log(){
		log = new ArrayList<LogKlauzula>();
	}
	void dodajKlauzule(Klauzula dziecko, boolean uzasad, boolean czyTeza){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(dziecko, uzasad, czyTeza));
	}
	boolean czyIstniejeDziecko(Klauzula dziecko){
		for(LogKlauzula logKlauzula: log){
			if(logKlauzula.getDziecko().czyRowne(dziecko))return true;
		}
		return false;
	}
	void dodajKlauzule(Klauzula rodzic1, Klauzula rodzic2, Klauzula dziecko, int iteracja, boolean uzasad){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(rodzic1, rodzic2, dziecko, iteracja, uzasad, false));
	}
	void piszKlauzule(Klauzula klauzula){
		System.out.print("(");
		int max = klauzula.getLiteraly().size()-1;
		for(Literal literal: klauzula.getLiteraly()){
			if(literal.getNegacja()){
				System.out.print("~");
			}
			System.out.print(literal.getNazwa());
			if(max!=0)System.out.print(" v ");
			
			max--;
		}

		System.out.print(")");
	}
	void dodajBazê(BazaWiedzy baza, boolean zbior){
		for(Klauzula klauzula: baza.getBaza()){
			dodajKlauzule(klauzula, false, false);
		}
	}
	void dodajTeze(BazaWiedzy baza, boolean zbior){
		for(Klauzula klauzula: baza.getBaza()){
			dodajKlauzule(klauzula, zbior, true);
		}
	}
	ArrayList<Integer> iloscKlauzulNaIteracje(){
		int n=0;
		ArrayList<Integer> wynik = new ArrayList<Integer>();
		for(LogKlauzula klauzula: log){
			int numerIter = klauzula.getIter();
			int rozmiar = wynik.size();
			if(numerIter>=rozmiar){
				for(int i=0;i<=numerIter-rozmiar;i++){
					wynik.add(0);
				}
			}
			wynik.set(numerIter, wynik.get(numerIter)+1);
		}
		return wynik;
	}
	List<LogKlauzula> getLog(){
		return log;
	}
	boolean czyTeza(LogKlauzula klauzula){
		return klauzula.czyTeza();
	}
	boolean czyUzasadnienie(Klauzula klauzula){
		for(LogKlauzula klauz: log){
			if(klauz.getDziecko().czyRowne(klauzula) && klauz.czyUzasadnien)return true;
		}
		return false;
	}
	
	ArrayList<Integer> dlugoscKlauzulNaIteracje(){
		ArrayList<Integer> wynik = new ArrayList<Integer>();
		boolean czyPierwsza = true;
		for(LogKlauzula klauzula: log){
			int numerIter = klauzula.getIter();
			int rozmiar = wynik.size();
			if(numerIter>=rozmiar){
				for(int i=0;i<=numerIter-rozmiar;i++){
					wynik.add(0);
				}
			}
			if (czyPierwsza){
				
				czyPierwsza=false;
			}
			
			wynik.set(numerIter, wynik.get(numerIter)+klauzula.getDziecko().getNazwa().length());
		}
		return wynik;
	}
	int maxWidth(){
		int max=0;
		for(Integer i: dlugoscKlauzulNaIteracje()){
			if (i>max)max=i;
		}
		return max;
	}
}
