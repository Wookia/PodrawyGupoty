package pszt;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class Main extends Application{
	static BazaWiedzy baza = new BazaWiedzy();
	static BazaWiedzy bazaTez = new BazaWiedzy(); //dodatkowa baza wiedzy jako przechowalnia samych tez, wrzucane z parsera
	static Graf graf;
	static Log log;
	static Stage stage;
	static Scene scene;
	static Parser parser;
	static final Pane pane  = new Pane();;
	public void start(Stage primaryStage) {
			testPodstawien();
			testZbioruUzasadnien();
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
	        
	        Button btn4 = new Button();
	        btn4.setText("Otworz plik");
	        btn4.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	parsujKonie(stage);
	            }
	        });

	        btn4.setTranslateX(0);
	        btn4.setTranslateY(75);
	        pane.getChildren().add(btn4);
	        
	        
	        stage.setScene(scene);
	        stage.show();
	}
	public static void main(String[] args) {
		
		launch(args);

	}
	
	private void parsujKonie(Stage stage) { //mozna zmienic na bool
		
		parser = new Parser();
		if(parser.wybierzPlik(stage)) {
			if(!parser.parsujPlik()) //zwraca boola zakladam, ze false jak plik do dupy, albo nie da sie go odczytac
			{
				final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                Label exitLabel = new Label("Wybrano plik o niepoprawnym formacie!");
                exitLabel.setAlignment(Pos.BASELINE_CENTER);

                Button yesBtn = new Button("OK");
                yesBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.close();
                    }
  
                });
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_CENTER);
                hBox.setSpacing(40.0);
                hBox.getChildren().addAll(yesBtn);

                VBox vBox = new VBox();
                vBox.setSpacing(40.0);
                vBox.getChildren().addAll(exitLabel, hBox);

                dialogStage.setScene(new Scene(vBox));
                dialogStage.show();
			}
			else
			{
			parser.wyswietlListy();
			baza.getBaza().clear();
			parser.dodajKlauzuleParser(baza,bazaTez);
			
			this.rozwiazuj("W");
			}
		
		
		
		
		return;
		}
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
        Button btn3 = new Button();
        btn3.setText("Uzasadnien");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        		if(test.equals("1")){
        			test1("U");
        		}
        		else if(test.equals("2")){
        			test2("U");
        		}
        		else{
        			test3("U");
        		}
        		graf.tworzGraf(log);
            }
        });
        btn3.setTranslateX(0);
        btn3.setTranslateY(50);
        pane.getChildren().add(btn3);
        
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
		else if(typ.equals("U"))log=solver.solveZbiorUzasadnien(test);
		
		
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
		else if(typ.equals("U"))log=solver.solveZbiorUzasadnien(test);
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
		test.add("B");		//czyli podajemy teze poprostu jako argument? no dobra to trzeba przerobic, zwlaszcza ze teza to nie koniecznie chyba 1 klauzula z tego co mowil PW
		
		Solver solver = new Solver(baza);
		if(typ.equals("L"))log=solver.solveLiniowe(test);
		else if(typ.equals("W"))log=solver.solveWszerz(test);
		else if(typ.equals("U"))log=solver.solveZbiorUzasadnien(test);
		
	}
	
	public void rozwiazuj(String typ)
	{
		ArrayList<String> test = new ArrayList<String>();
		test.add("B");
		Solver solver = new Solver(baza);
		if(typ.equals("L"))log=solver.solveLiniowe(test);
		else if(typ.equals("W"))log=solver.solveWszerz(test);
		else if(typ.equals("U"))log=solver.solveZbiorUzasadnien(test);
		graf.tworzGraf(log);
		
	}
	
	static void testPodstawien()
	{
		ArrayList<Literal> K1 = new ArrayList<Literal>();
		ArrayList<Literal> K2 = new ArrayList<Literal>();
		
		K1.add(new Literal("A"));
		K1.get(0).setNegacja(false);
		K1.get(0).getArgumenty().add(new Literal("d"));
		K1.get(0).getArgumenty().add(new Literal("d"));
		K1.get(0).getArgumenty().add(new Literal("d"));
		K1.get(0).getArgumenty().add(new Literal("d"));
		K1.get(0).getArgumenty().add(new Literal("d"));
		K1.get(0).getArgumenty().add(new Literal("a"));
		K1.get(0).getArgumenty().get(0).setStala(false);
		K1.get(0).getArgumenty().get(1).setStala(false);
		K1.get(0).getArgumenty().get(2).setStala(false);
		K1.get(0).getArgumenty().get(3).setStala(false);
		K1.get(0).getArgumenty().get(4).setStala(false);
		K1.get(0).getArgumenty().get(5).setStala(false);
		
		K2.add(new Literal("A"));
		K2.add(new Literal("B"));
		K2.get(1).setNegacja(true);
		K2.get(0).setNegacja(true);
		K2.get(1).getArgumenty().add(new Literal("c"));
		K2.get(1).getArgumenty().add(new Literal("z"));
		K2.get(1).getArgumenty().add(new Literal("stalaOOO"));
		K2.get(0).getArgumenty().add(new Literal("c"));
		K2.get(0).getArgumenty().add(new Literal("z"));
		K2.get(0).getArgumenty().add(new Literal("a"));
		K2.get(0).getArgumenty().add(new Literal("x"));
		K2.get(0).getArgumenty().add(new Literal("s"));
		K2.get(0).getArgumenty().add(new Literal("stalaf"));
		K2.get(1).getArgumenty().get(0).setStala(false);
		K2.get(1).getArgumenty().get(1).setStala(false);
		K2.get(1).getArgumenty().get(2).setStala(true);
		K2.get(0).getArgumenty().get(0).setStala(false);
		K2.get(0).getArgumenty().get(1).setStala(false);
		K2.get(0).getArgumenty().get(2).setStala(false);
		K2.get(0).getArgumenty().get(3).setStala(false);
		K2.get(0).getArgumenty().get(4).setStala(false);
		K2.get(0).getArgumenty().get(5).setStala(true);
		
		Klauzula klauz1 = new Klauzula();
		Klauzula klauz2 = new Klauzula();
		
		klauz1.getLiteraly().addAll(K1);
		klauz2.getLiteraly().addAll(K2);
		
		Klauzula.wykonajPodstawienie(klauz1, klauz2);
	}
	
	static void testZbioruUzasadnien()
	{
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
		solver.solveZbiorUzasadnien(test);
	}

}
