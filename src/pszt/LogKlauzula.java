package pszt;

public class LogKlauzula {
	private 
		Klauzula rodzic1;
		Klauzula rodzic2;
		Klauzula klauzula;
		boolean czyUzasadnien;
		int iteracja;
		boolean czyTeza;
	public 
	LogKlauzula(Klauzula dziecko, boolean czyUzasadnien, boolean czyTeza){
		this.czyUzasadnien=czyUzasadnien;
		this.rodzic1=null;
		this.rodzic2=null;
		this.klauzula=dziecko;
		this.iteracja=0;
		this.czyTeza=czyTeza;
	}
		LogKlauzula(Klauzula rodzic1, Klauzula rodzic2, Klauzula dziecko, int iteracja, boolean czyUzasadnien, boolean czyTeza){
		this.czyUzasadnien=czyUzasadnien;
		this.rodzic1=rodzic1;
		this.rodzic2=rodzic2;
		this.klauzula=dziecko;
		this.iteracja=iteracja;
		this.czyTeza = czyTeza;
	}
	boolean czyTeza(){
		return czyTeza;
	}
	boolean isUzasanienie(){
		return czyUzasadnien;
	}
	Klauzula getRodzic1(){
		return rodzic1;
	}
	Klauzula getRodzic2(){
		return rodzic2;
	}
	Klauzula getDziecko(){
		return klauzula;
	}
	int getIter(){
		return iteracja;
	}
}
