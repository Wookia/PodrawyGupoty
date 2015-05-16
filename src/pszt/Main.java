package pszt;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class Main extends Application{
	static BazaWiedzy baza = new BazaWiedzy();
	static Graf graf;
	static Log log;
	static Stage stage;
	static Scene scene;
	static final Pane pane  = new Pane();;
	public void start(Stage primaryStage) {
			testPodstawien();
			stage = primaryStage;
		 	primaryStage.setTitle("Podstawy g³upoty");
	        scene = new Scene(pane, 300, 250);
	        graf = new Graf(scene, pane, stage);
	        Button btn1 = new Button();
	        btn1.setText("Test 1 '");
	        btn1.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	test("1");
	            }
	        });
	        btn1.setTranslateX(0);
	        btn1.setTranslateY(0);
	        pane.getChildren().add(btn1);
	        Button btn2 = new Button();
	        btn2.setText("Test 2 '");
	        btn2.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	test("2");
	            }
	        });
	        btn2.setTranslateX(0);
	        btn2.setTranslateY(25);
	        
	        pane.getChildren().add(btn2);
	        Button btn3 = new Button();
	        btn3.setText("Test 3 '");
	        btn3.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	test("3");
	            }
	        });

	        btn3.setTranslateX(0);
	        btn3.setTranslateY(50);
	        pane.getChildren().add(btn3);
	        stage.setScene(scene);
	        stage.show();
	}
	public static void main(String[] args) {
		
		launch(args);

	}
	static void test(final String test){
		pane.getChildren().clear();
		baza.getBaza().clear();
		Button btn1 = new Button();
        btn1.setText("Liniowe");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        		if(test.equals("1")){
        			test1("L");
        		}
        		else if(test.equals("2")){
        			test2("L");
        		}
        		else{
        			test3("L");
        		}
        		graf.tworzGraf(log);
            }
        });
        btn1.setTranslateX(0);
        btn1.setTranslateY(0);
        pane.getChildren().add(btn1);
        Button btn2 = new Button();
        btn2.setText("Wszerz");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        		if(test.equals("1")){
        			test1("W");
        		}
        		else if(test.equals("2")){
        			test2("W");
        		}
        		else{
        			test3("W");
        		}
        		graf.tworzGraf(log);
            }
        });
        btn2.setTranslateX(0);
        btn2.setTranslateY(25);
        pane.getChildren().add(btn2);
        
	}
	static void  test1(String typ){
		//PRZYK£AD 2
		ArrayList<String> test = new ArrayList<String>();
		test.add("A");
		test.add("B");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!B");
		test.add("C");
		
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!C");
		test.add("A");

		//teza
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!A");
		Solver solver = new Solver(baza);
		
		if(typ.equals("L"))log=solver.solveLiniowe(test);
		else if(typ.equals("W"))log=solver.solveWszerz(test);
		
		
	}
	static void test2(String typ){

		//PRZYK£AD 1
		ArrayList<String> test = new ArrayList<String>();
		test.add("!C");
		test.add("W");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!C");
		test.add("R");
		
		baza.dodajKlauzule(test);
		test.clear();
		test.add("C");

		baza.dodajKlauzule(test);
		test.clear();
		test.add("O");
		//teza
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!O");
		test.add("!R");

		Solver solver = new Solver(baza);
		if(typ.equals("L"))log=solver.solveLiniowe(test);
		else if(typ.equals("W"))log=solver.solveWszerz(test);
	}
	static void test3(String typ){
		//PRZYK£AD 2
		ArrayList<String> test = new ArrayList<String>();
		test.add("!A");
		test.add("B");
		test.add("C");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!B");
		test.add("D");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!C");
		test.add("!D");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("C");
		test.add("D");

		baza.dodajKlauzule(test);
		test.clear();
		test.add("!C");
		test.add("E");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("!D");
		test.add("!E");
		baza.dodajKlauzule(test);
		test.clear();
		test.add("E");
		baza.dodajKlauzule(test);
		//teza
		test.clear();
		test.add("B");
		
		Solver solver = new Solver(baza);
		if(typ.equals("L"))log=solver.solveLiniowe(test);
		else if(typ.equals("W"))log=solver.solveWszerz(test);
		
	}
	
	static void testPodstawien()
	{
		ArrayList<Literal> K1 = new ArrayList<Literal>();
		ArrayList<Literal> K2 = new ArrayList<Literal>();
		
		K1.add(new Literal("A"));
		K1.get(0).setNegacja(false);
		K1.get(0).getArgumenty().add(new Literal("a"));
		K1.get(0).getArgumenty().add(new Literal("b"));
		K1.get(0).getArgumenty().add(new Literal("c"));
		K1.get(0).getArgumenty().get(0).setStala(false);
		K1.get(0).getArgumenty().get(1).setStala(false);
		K1.get(0).getArgumenty().get(2).setStala(false);
		
		K2.add(new Literal("A"));
		K2.get(0).setNegacja(true);
		K2.get(0).getArgumenty().add(new Literal("d"));
		K2.get(0).getArgumenty().add(new Literal("b"));
		K2.get(0).getArgumenty().add(new Literal("StalaF"));
		K2.get(0).getArgumenty().get(0).setStala(false);
		K2.get(0).getArgumenty().get(1).setStala(false);
		K2.get(0).getArgumenty().get(2).setStala(true);
		
		Klauzula klauz1 = new Klauzula();
		Klauzula klauz2 = new Klauzula();
		
		klauz1.getLiteraly().addAll(K1);
		klauz2.getLiteraly().addAll(K2);
		
		Klauzula.wykonajPodstawienie(klauz1, klauz2);
		
		
	}

}
