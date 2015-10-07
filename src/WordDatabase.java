import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordDatabase {

	//Map between word category and a list of all words belonging to that category
	private Map<String, ArrayList<String>> wordMap;
		

	public WordDatabase(){
		wordMap = new HashMap<String, ArrayList<String>>();
	}
	
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
	
	
	
	public Map<String, ArrayList<String>> getWordMap() {
		return wordMap;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
