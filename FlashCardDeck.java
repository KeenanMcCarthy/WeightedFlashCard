package flashCardPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import flashCardPackage.FlashCard;

/* ********************************************** *
 * Flashcard deck manages flashcards and provides *
 * sampling logic				  *
 * ********************************************** */
 

public class FlashCardDeck {
	
	private ArrayList<FlashCard> deck = new ArrayList<FlashCard>();
	private static double weighted_deck_size = 0;
	
	public void addFlashCard(File file) throws IOException{
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String country;
		String capital;
		String weight;
		while ((country = br.readLine()) != null && (capital = br.readLine()) != null &&
				(weight = br.readLine()) != null){
			this.addFlashCard(new FlashCard(country, Arrays.asList(capital.split(",")), Double.parseDouble(weight)));
		}
	}
	
	public void addFlashCard(FlashCard fc){
		this.deck.add(fc);
		weighted_deck_size += fc.weight;
	}
	
	/* Method for weighted random sampling: A random double is selected between 
	 * 0 and the sum of all card weights in the deck. The cards are summed and the first
	 * card whose marginal weight causes the running total to equal or exceed the random 
	 * sample is selected. 
	 * */
	public FlashCard sample(){
		double sample = ThreadLocalRandom.current().nextDouble(weighted_deck_size);
		double running_total = 0.0;
		
		for (FlashCard fc: this.deck){
			running_total += fc.weight;
			if (running_total >= sample){
				return fc;
			}
		}
		System.out.println(weighted_deck_size);
		return null;
	}
	
	public boolean isCapital(FlashCard fc, String answer){
		weighted_deck_size -= fc.weight;
		weighted_deck_size += fc.updateWeight(answer);
		return fc.isCapital(answer)? true: false;
	}
	
	public void closeDeck(){
		try {
			File file = new File("./src/flashcardPackage/input.txt");
			FileOutputStream stream = new FileOutputStream(file);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(stream));
			
			for (FlashCard fc: this.deck){
				bw.write(fc.country+'\n');
				for (int i=0; i<fc.capitals.size(); i++){
					bw.write(fc.capitals.get(i));
					if (i != fc.capitals.size()-1){
						bw.write(',');
					}
				}
				bw.write('\n');
				bw.write(Double.toString(fc.weight)+'\n');
			}
			bw.close();
			stream.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
