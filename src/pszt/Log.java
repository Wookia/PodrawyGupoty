package pszt;

import java.util.ArrayList;
import java.util.List;

public class Log {
	private 
		List<LogKlauzula> log;
		LogKlauzula teza=null;	//to co robimy z tego arraylist?
	public
	Log(){
		log = new ArrayList<LogKlauzula>();
	}
	void dodajKlauzule(Klauzula dziecko, boolean uzasad){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(dziecko, uzasad));
	}
	boolean czyIstniejeDziecko(Klauzula dziecko){
		for(LogKlauzula logKlauzula: log){
			if(logKlauzula.getDziecko().czyRowne(dziecko))return true;
		}
		return false;
	}
	void dodajKlauzule(Klauzula rodzic1, Klauzula rodzic2, Klauzula dziecko, int iteracja, boolean uzasad){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(rodzic1, rodzic2, dziecko, iteracja, uzasad));
	}
	void piszDzifko(){
		int i=-1;
		for (LogKlauzula logKlauzula: log){
			if(logKlauzula.getIter()!=i){
				i=logKlauzula.getIter();
				System.out.println("n="+i);
			}
			Klauzula rodzic1 = logKlauzula.getRodzic1();
			Klauzula rodzic2 = logKlauzula.getRodzic2();
			Klauzula dziecko = logKlauzula.getDziecko();
			if (rodzic1!=null){
				System.out.print("(");
				piszKlauzule(rodzic1);
				System.out.print("^");
			}
			if (rodzic2!=null){
				piszKlauzule(rodzic2);
				System.out.print(")");
				System.out.print(" => ");
			}
			if (logKlauzula.equals(teza))System.out.print("TEZA: ");
			if (dziecko!=null)piszKlauzule(dziecko);
			System.out.println("");
			
		}
		
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
			dodajKlauzule(klauzula, false);
		}
		if(!zbior)this.teza = log.get(log.size()-1);
		else this.teza=null;
		//TODO pewnie bedzie trzeba przerobic, teza to wiecej niz jedna klauzula :/
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
		if(teza!=null && teza.equals(klauzula))return true;
		return false;
	}
	boolean czyUzasadnienie(Klauzula klauzula){
		for(LogKlauzula klauz: log){
			if(klauz.getDziecko().czyRowne(klauzula) && klauz.czyUzasadnien)return true;
		}
		return false;
	}
	
	ArrayList<Integer> dlugoscKlauzulNaIteracje(){
		ArrayList<Integer> wynik = new ArrayList<Integer>();
		for(LogKlauzula klauzula: log){
			int numerIter = klauzula.getIter();
			int rozmiar = wynik.size();
			if(numerIter>=rozmiar){
				for(int i=0;i<=numerIter-rozmiar;i++){
					wynik.add(0);
				}
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
