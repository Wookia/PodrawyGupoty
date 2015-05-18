package pszt;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GrafButton extends Button{
	private Color fill = Color.BLACK;
	public GrafButton(){
		
	}
	public void podswietl(){
		setTextFill(Color.RED);
	}
	public void odswietl(){
		setTextFill(fill);
	}
	public void setKolor(Color color){
		fill=color;
		setTextFill(fill);
	}
}
