/**
 * WordDatabase: holds all of the words as hashmap with categories as keys
 * 
 * @author dcyoung3
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordDatabase {

	//Map between word category and a list of all words belonging to that category
	private Map<String, ArrayList<String>> wordMap;
		
	/**
	 * Constructor
	 */
	public WordDatabase(){
		wordMap = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Print the database to the console
	 */
	public void PrintDatabase(){
		String line;
		for(String category : this.wordMap.keySet()){
			line = category + ": " ;
			for(String w : this.wordMap.get(category)){
				line += w + ", ";
			}
			System.out.println(line);
		}
	}
	
	
	/**
	 * 
	 * @return the hashmap containing the database
	 */
	public Map<String, ArrayList<String>> getWordMap() {
		return wordMap;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
