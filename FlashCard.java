package flashCardPackage;
import java.util.List;

/* ******************************************************************* *
 * FlashCard Class, contains flashcard data as well as weighting logic *
 * ******************************************************************* */

public class FlashCard {
	public String country;
	public List<String> capitals;
	double weight;
	
	public FlashCard(String country, List<String> capitals, double weight){
		this.country = country;
		this.capitals = capitals;
		this.weight = weight;
	}
	
	public FlashCard(String country, List<String> list){
		this.country = country;
		this.capitals = list;
		this.weight = 1.0;
	}
	
	public boolean isCapital(String capital){
		for (String cap: this.capitals){
			if (cap.toLowerCase().equals(capital.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	/* Evaluates the level of "incorrectness" for a given answer,
	 * score is calculated based on the Levenshtein distance between
	 * the answer and the capital, divided by the maximum of the 
	 * input string and the capital with the lowest distance  
	 * */
	private double get_k_score(String answer){
		int min_dist = Integer.MAX_VALUE;
		String matching_capital = null;
		for (String capital: this.capitals){
			int[][] arr = new int[answer.length()+1][capital.length()+1];
			for (int i=0; i<arr[0].length; i++){
				arr[0][i] = i;
			}
			for (int i=0; i<arr.length; i++){
				arr[i][0] = i;
			}
			for (int i=1; i<arr.length; i++){
				for (int j=1; j<arr[0].length; j++){
					if (Character.toLowerCase(capital.charAt(j-1)) == Character.toLowerCase(answer.charAt(i-1))){
						arr[i][j] = arr[i-1][j-1];
					} else {
						arr[i][j] = Integer.min(Integer.min(arr[i-1][j], arr[i][j-1]),arr[i-1][j-1])+1;
					}
				}
			}
			if (arr[arr.length-1][arr[0].length-1] < min_dist){
				min_dist = arr[arr.length-1][arr[0].length-1];
				matching_capital = capital;
			}
			min_dist = Integer.min(min_dist, arr[arr.length-1][arr[0].length-1]);
		}
		return min_dist/(double)Integer.max(answer.length(), matching_capital.length());
	}
	
	@Override
	public String toString(){
		return this.country + " , " + this.capitals.get(0) + " weight: " + this.weight;
	}
	
	public double updateWeight(String answer){
		if (this.isCapital(answer)){
			this.weight *= 0.5;
		} else {
			double keenan_score = this.get_k_score(answer);
			this.weight = Double.min(this.weight*(1+keenan_score), 10.0);
		}
		return this.weight;
	}
}
