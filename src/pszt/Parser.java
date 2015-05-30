/**
 * 
 */
package pszt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author JA!
 *
 */
public class Parser{
	final FileChooser fileChooser = new FileChooser();
	private List<String> wszystkieLinie;
	File file;
	Charset cp1252 = Charset.forName("CP1252");
	
	Parser(){
		
		
	}
	
	
	
	boolean wybierzPlik(Stage stage) {
		file = fileChooser.showOpenDialog(stage);
	     if (file != null) {
	        return true;
	     }
	     return false; //nie chce mi sie wyjatkow rzucac pff

		
	}



	public boolean parsujPlik() {
		try {
			wszystkieLinie = Files.readAllLines(Paths.get(file.getPath()), cp1252);
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			
			return false;
		}
		
		
	}
	
	public void wyswietlListy() {
		System.out.println("Zawartosc list do parsowania:");
		for(String s: wszystkieLinie){
			System.out.println(s);
		}
		
		
	}
}

//List<String> lines = Files.readAllLines(Paths.get(path), encoding);



