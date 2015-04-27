package pszt;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		BazaWiedzy baza = new BazaWiedzy();
		ArrayList<String> test = new ArrayList<String>();
		test.add("A");
		test.add("B");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!B");
		test.add("C");
		
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!C");
		test.add("A");

		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!A");
		
		//baza.dodajKlauzule(new Klauzula(baza.getBaza().get(), baza.getBaza().get(2)));
		Solver solver = new Solver(baza);
		solver.solveWszerz(test);

	}

}
