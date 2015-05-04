package pszt;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
		ArrayList<Text> listaKlauzul = new ArrayList<Text>();
		for(int i = 0; i<=iloscIter; i++){
			Text iter = new Text();
			iter.setText("n = " + i);
			iter.setTranslateX(5);
			iter.setTranslateY(10+i*100);
			pane.getChildren().add(iter);
			int powt = 1;
			for(LogKlauzula klauzula: log.getLog()){
				if(klauzula.getIter()==i){
					Text klauz = new Text();
					if(log.czyTeza(klauzula))klauz.setFill(Color.GREEN);
					else klauz.setFill(Color.rgb((i+1)*255/(iloscIter+1), 0, 0));
					klauz.setText(klauzula.getDziecko().getNazwa());
					klauz.setTranslateX(5+powt*stage.getWidth()/(iloscIteracji.get(i) + 1));
					klauz.setTranslateY(10+i*100);
					listaKlauzul.add(klauz);
					if(klauzula.getRodzic1()!=null && klauzula.getRodzic2()!=null){
						boolean check = false;
						for(Text text: listaKlauzul){
							if(klauzula.getRodzic1().getNazwa().equals(text.getText())){
								Line line = new Line(klauz.getTranslateX()+klauz.getLayoutBounds().getWidth()/2, klauz.getTranslateY()-klauz.getLayoutBounds().getHeight()/2,
										text.getTranslateX()+text.getLayoutBounds().getWidth()/2, text.getTranslateY());
								line.setStroke(Color.rgb((i+1)*255/(iloscIter+1), 0, 0));
								
								pane.getChildren().add(line);
								
								if(check)break;
								check=true;
							}
							if(klauzula.getRodzic2().getNazwa().equals(text.getText())){
								Line line = new Line(klauz.getTranslateX()+klauz.getLayoutBounds().getWidth()/2, klauz.getTranslateY()-klauz.getLayoutBounds().getHeight()/2,
										text.getTranslateX()+text.getLayoutBounds().getWidth()/2, text.getTranslateY());

								line.setStroke(Color.rgb((i+1)*255/(iloscIter+1), 0, 0));
								pane.getChildren().add(line);
								
								if(check)break;
								check=true;
							}
						}
					}
					pane.getChildren().add(klauz);
					powt++;
				}
			}
			
		}
	}
}
