package pszt;

import java.util.ArrayList;

import com.sun.glass.events.MouseEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Graf {
	private
	Scene scene;
	Pane pane;
	Stage stage;
	public
	Graf(Scene scene, Pane pane, Stage stage){
		this.scene=scene;
		this.pane=pane;
		this.stage=stage;
	}
	void tworzGraf(Log log){
		int iloscIter = 0;
		int maxIloscKlauzul = 0;
		ArrayList<Integer> iloscIteracji = log.iloscKlauzulNaIteracje();
		for(Integer i:iloscIteracji){
			if(maxIloscKlauzul<i)maxIloscKlauzul=i;
			iloscIter++;
		}
		stage.setHeight(iloscIter*100);
		stage.setWidth(maxIloscKlauzul*80);
		pane.getChildren().clear();
		final ArrayList<Button> listaKlauzul = new ArrayList<Button>();
		final ArrayList<Pair<Integer, Line>> mapaLinii =  new ArrayList<Pair<Integer, Line>>();
		for(int i = 0; i<=iloscIter; i++){
			Text iter = new Text();
			iter.setText("n = " + i);
			iter.setTranslateX(5);
			iter.setTranslateY(10+i*100);
			pane.getChildren().add(iter);
			int powt = 1;
			double last=100;
			for(LogKlauzula klauzula: log.getLog()){
				if(klauzula.getIter()==i){
					final Button klauz = new Button();
					if(log.czyTeza(klauzula))klauz.setTextFill(Color.GREEN);
					else klauz.setTextFill(Color.RED);
					klauz.setText(klauzula.getDziecko().getNazwa());
					klauz.setMinHeight(20);
					klauz.setMinWidth(10*klauz.getText().length());
					klauz.setTranslateX(last+10);
					klauz.setTranslateY(i*100);
					final Klauzula rodzic1 = klauzula.getRodzic1();
					final Klauzula rodzic2 = klauzula.getRodzic2();
					klauz.setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent event) {
			        		for(Button button: listaKlauzul){
			        			button.setTextFill(Color.RED);
			        		}
			        		klauz.setTextFill(Color.BLUEVIOLET);

			        		for(Pair<Integer, Line> pair: mapaLinii){
			        			if(pair.getKey().equals(listaKlauzul.indexOf(klauz))){
			        				pair.getValue().setStroke(Color.BLUEVIOLET);
			        			}
			        			else pair.getValue().setStroke(Color.RED);
			        		}
			        		
							if(rodzic1!=null && rodzic2!=null){

								for(Button text: listaKlauzul){
									if(rodzic1.getNazwa().equals(text.getText())){
										text.setTextFill(Color.BLUEVIOLET);
									}
									if(rodzic2.getNazwa().equals(text.getText())){
										text.setTextFill(Color.BLUEVIOLET);
									}
								}
							
							}
			        		
			            }
			        });
					last = last+klauz.getMinWidth()+10;
					listaKlauzul.add(klauz);
					if(rodzic1!=null && rodzic2!=null){
						boolean check = false;
						for(Button text: listaKlauzul){
							if(rodzic1.getNazwa().equals(text.getText())){
								scene.snapshot(null);
								Line line = new Line(klauz.getTranslateX()+klauz.getMinWidth()/2, klauz.getTranslateY(),
										text.getTranslateX()+text.getMinWidth()/2, text.getTranslateY()+text.getMinHeight());
								line.setStroke(Color.RED);
								
								pane.getChildren().add(line);
								mapaLinii.add(new Pair<Integer, Line>(listaKlauzul.indexOf(klauz), line));
								
								if(check)break;
								check=true;
							}
							else if(rodzic2.getNazwa().equals(text.getText())){
								scene.snapshot(null);
								Line line = new Line(klauz.getTranslateX()+klauz.getMinWidth()/2, klauz.getTranslateY(),
										text.getTranslateX()+text.getMinWidth()/2, text.getTranslateY()+text.getMinHeight());

								line.setStroke(Color.RED);
								pane.getChildren().add(line);
								mapaLinii.add(new Pair<Integer, Line>(listaKlauzul.indexOf(klauz), line));
								
								if(check)break;
								check=true;
							}
						}
					}
					powt++;
				}
			}
			if(last+40>stage.getWidth())stage.setWidth(last+40);
			
		}
		for (Button klauz: listaKlauzul){
			pane.getChildren().add(klauz);

		}
	}
}
