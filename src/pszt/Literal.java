package pszt;

import java.util.ArrayList;
import java.util.List;
public class Literal {
private
	List<Literal> argumenty;
	boolean negacja=false;
	String nazwa;
public
	Literal(String literalString){
		if(literalString.startsWith("!")){
			negacja = true;
			this.nazwa=literalString.substring(1);
		}
		else{
			this.nazwa = literalString;
		}
		argumenty = new ArrayList<Literal>();
	}
	String getNazwa(){
		return nazwa;
	}
	boolean getNegacja(){
		return negacja;
	}
	List<Literal> getArgumenty(){
		return argumenty;
	}
}
