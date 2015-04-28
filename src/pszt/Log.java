package pszt;

import java.util.ArrayList;
import java.util.List;

public class Log {
	private 
		List<LogKlauzula> log;
		LogKlauzula teza;
	public
	Log(){
		log = new ArrayList<LogKlauzula>();
	}
	void dodajKlauzule(Klauzula dziecko){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(dziecko));
	}
	boolean czyIstniejeDziecko(Klauzula dziecko){
		for(LogKlauzula logKlauzula: log){
			if(logKlauzula.getDziecko().czyRowne(dziecko))return true;
		}
		return false;
	}
	void dodajKlauzule(Klauzula rodzic1, Klauzula rodzic2, Klauzula dziecko, int iteracja){
		if(!czyIstniejeDziecko(dziecko))log.add(new LogKlauzula(rodzic1, rodzic2, dziecko, iteracja));
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
	void dodajBazê(BazaWiedzy baza){
		for(Klauzula klauzula: baza.getBaza()){
			dodajKlauzule(klauzula);
		}
		this.teza = log.get(log.size()-1);
	}
}
