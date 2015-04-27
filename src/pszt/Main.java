package pszt;

import java.util.ArrayList;

public class Main {
	static BazaWiedzy baza = new BazaWiedzy();

	public static void main(String[] args) {
		test3();

	}
	static void  test1(){
		//PRZYK£AD 2
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

		//teza
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!A");
		Solver solver = new Solver(baza);
		solver.solveWszerz(test);
		
	}
	static void test2(){

		//PRZYK£AD 1
		ArrayList<String> test = new ArrayList<String>();
		test.add("!C");
		test.add("W");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!C");
		test.add("R");
		
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("C");

		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("O");
		//teza
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!O");
		test.add("!R");

		Solver solver = new Solver(baza);
		solver.solveWszerz(test);
	}
	static void test3(){
		//PRZYK£AD 2
		ArrayList<String> test = new ArrayList<String>();
		test.add("!A");
		test.add("B");
		test.add("C");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!B");
		test.add("D");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!C");
		test.add("!D");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("C");
		test.add("D");

		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!C");
		test.add("E");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("!D");
		test.add("!E");
		System.out.println(baza.dodajKlauzule(test));
		test.clear();
		test.add("E");
		System.out.println(baza.dodajKlauzule(test));
		//teza
		test.clear();
		test.add("B");
		Solver solver = new Solver(baza);
		solver.solveWszerz(test);
		
	}

}
