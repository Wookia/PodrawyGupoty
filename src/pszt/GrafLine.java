package pszt;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GrafLine extends Line{
	private Color fill = Color.BLACK;
	public GrafLine(double startX, double startY, double endX, double endY){
		super(startX, startY, endX, endY);
	}
	public void podswietl(){
		setStroke(Color.RED);
		setStrokeWidth(2);
	}
	public void odswietl(){
		setStroke(fill);
		setStrokeWidth(1);
	}
	public void setKolor(Color color){
		fill=color;
		setStroke(fill);
	}
}
