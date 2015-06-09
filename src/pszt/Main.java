package pszt;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
	static final Pane pane  = new Pane();
	static final ScrollPane scroll = new ScrollPane();
	public void start(Stage primaryStage) {
			stage = primaryStage;
			stage.centerOnScreen();
		 	primaryStage.setTitle("Podstawy sztucznej inteligencji");
		 	scroll.setContent(pane);
		 	scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		 	scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		 	scroll.setFitToHeight(false);
		 	scroll.setFitToWidth(false);
	        scene = new Scene(scroll, 300, 250);
	        Widok widok = new Widok(stage, scene, pane);
	        graf = new Graf(scene, pane, stage, widok,scroll);
	        widok.addGraf(graf);
	        widok.mainView();
	}
	public static void main(String[] args) {
		
		launch(args);

	}
}