package pszt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Widok {
	private
	static BazaWiedzy baza = new BazaWiedzy();
	static BazaWiedzy bazaTez = new BazaWiedzy(); 
	static Graf graf;
	static Log log;
	static Stage stage;
	static Scene scene;
	static Parser parser;
	static Pane pane;
	public
		Widok(Stage stage, Scene scene, Pane pane){
		this.stage = stage;
		this.scene = scene;
		this.pane = pane;
        stage.setScene(scene);
        stage.show();
	}
	void mainView(){
		stage.setHeight(200);
		stage.setWidth(300);
		pane.getChildren().clear();
		Text text = new Text("Program na zaliczenie projektu z przedmiotu PSZT\n "
				+ "\n"
				+ "\n");
		text.setTranslateX(0);
		text.setTranslateY(10);
		pane.getChildren().add(text);
        Button btn4 = new Button();
        btn4.setText("Otworz plik");
        btn4.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	parsujKonie(stage);
            }
        });

        btn4.setTranslateX(100);
        btn4.setTranslateY(125);
        pane.getChildren().add(btn4);
		
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
			wybierzMetode();
			
			}
		
		return;
		}
		
		
	}
	public void wybierzMetode(){
		stage.setHeight(200);
		stage.setWidth(300);
		stage.centerOnScreen();
		baza.getBaza().clear();
		bazaTez.getBaza().clear();
		parser.dodajKlauzuleParser(baza,bazaTez);
		pane.getChildren().clear();
		Button btn1 = new Button();
        btn1.setText("Liniowe");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        		rozwiazuj("L");
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
        		rozwiazuj("W");
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
        		rozwiazuj("U");
        		graf.tworzGraf(log);
            }
        });
        btn3.setTranslateX(0);
        btn3.setTranslateY(50);
        pane.getChildren().add(btn3);
        Button btn5 = new Button();
        btn5.setText("Najkrotsze");
        btn5.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        		rozwiazuj("N");
        		graf.tworzGraf(log);
            }
        });
        btn5.setTranslateX(0);
        btn5.setTranslateY(75);
        pane.getChildren().add(btn5);
        Button btn6 = new Button();
        btn6.setText("Wroc do wyboru");
        btn6.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	mainView();
            }
        });
        btn6.setTranslateX(0);
        btn6.setTranslateY(125);
        pane.getChildren().add(btn6);
		
	}
	public void rozwiazuj(String typ)
	{
		Solver solver = new Solver(baza);
		if(typ.equals("L"))log=solver.solveLiniowe(bazaTez);
		else if(typ.equals("W"))log=solver.solveWszerz(bazaTez);
		else if(typ.equals("U"))log=solver.solveZbiorUzasadnien(bazaTez);
		else if(typ.equals("N"))log=solver.solveNajkrotsze(bazaTez);
		graf.tworzGraf(log);
		
	}
	public void addGraf(Graf graf) {
		this.graf=graf;
		
	}

}
