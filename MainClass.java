package flashCardPackage;
import flashCardPackage.SwingFrame;
import java.io.IOException;

/* ************************************* *
 * MainClass, instantiates and runs GUI  * 
 * ************************************* */

public class MainClass {
	
	public static void main(String args[]) throws IOException{
		
		SwingFrame gui = new SwingFrame();
		try {
			gui.createAndShowGUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
