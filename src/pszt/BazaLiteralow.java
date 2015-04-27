package pszt;

import java.util.ArrayList;
import java.util.List;

public final class BazaLiteralow {
	private
		BazaLiteralow(){
	}
	public
		static final List<Literal> baza = new ArrayList<Literal>();
		static Literal czyIstnieje(Literal literal){
			for(Literal liter: baza){
				if(liter.getNazwa().equals(literal.getNazwa()) 
						&& liter.getNegacja()==literal.getNegacja()){
					return liter;
				}
			}
			return null;
		}
		static Literal dodajLiteral(Literal literal){
			Literal liter = czyIstnieje(literal);
			if(liter==null){
				baza.add(literal);
				return literal;
			}
			return liter;
			
		}
}
