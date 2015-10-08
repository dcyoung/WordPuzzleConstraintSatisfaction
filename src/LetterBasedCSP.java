import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class LetterBasedCSP {
	
	private Puzzle puzzle;
	private WordDatabase db;
	private ArrayList<ArrayList<Character>> results;
	private ArrayList<Character> alphabet;
	
	public LetterBasedCSP(Puzzle puzzle, WordDatabase db){
		this.puzzle = puzzle;
		this.db = db;
		this.results = new ArrayList<ArrayList<Character>>();
		DefineAlphabet();
	}
	
	private void DefineAlphabet(){
		alphabet = new ArrayList<Character>();
		char firstChar = 'A';
		for(int i = 0; i < 26; i++ ){
			alphabet.add((char) (firstChar+i));
		}
	}
	private int SelectUnassignedVariable(ArrayList<Character> assignment){
		//naive
		for(int i = 0; i <assignment.size(); i++){
			if (assignment.get(i) == null){
				return i;
			}
		}
		System.out.println("Should not be selecting a variable when no index is null");
		return -1;
	}
	
	private ArrayList<Character> OrderDomainValues(int index){
		//naive: return a-z since there will be lots of chars anyways
		return this.alphabet;
		//eventually: return the chars in the corresponding categories
		//return null;
	}
	
	
	private String GetWordRegEx(String category, ArrayList<Character> assignment){
		//"A.A"
		String partialWord = "";
		for(int index : this.puzzle.getCategoryIndexMap().get(category)){
			if (assignment.get(index) == null) {
				partialWord+= ".";
			}
			else{
				partialWord+= assignment.get(index);
			}
		}
		return partialWord;
	}
	
	/**
	 * Checks that the char c proposed for the specified index is able to form a word 
	 * from every connected category given previous specified letters 
	 * @param index
	 * @param assignment
	 * @return
	 */
	private boolean CheckIfConsistent(int index, ArrayList<Character> assignment){
		
		//for each category linked to the index
		for(String category: this.puzzle.getIndexCategoryMap().get(index)){
			String partialWord = GetWordRegEx(category, assignment);
			boolean WordExistsInCategory = false;
			//for each word in that category
			for(String word : db.getWordMap().get(category)){
				//could the partial word construct word
				if(Pattern.matches(partialWord,word)){
					WordExistsInCategory = true;
					break;
				}
			}
			
			if(!WordExistsInCategory)
				return false;
		}
		
		return true;
	}
	
	private ArrayList<Character> DeepCopyCharArrayList(ArrayList<Character> inputArray){
		ArrayList<Character> newArrayList = new ArrayList<Character>();
		for(char c : inputArray){
			newArrayList.add(c);
		}
		return newArrayList;
	}
	
	public void RecursiveBacktracking(ArrayList<Character> assignment){
		//if(assignment is valid and complete) 
		 	//add copy of assignment to results
		 
		int index = SelectUnassignedVariable(assignment);
		for(char c : this.OrderDomainValues(index)){
			// Add it to the assignment
			assignment.set(index, c);
			//if value is consistent with assignment given constraints
			if(CheckIfConsistent(index, assignment)){
				if (!assignment.contains(null)) {
					// add it to solution set
					this.results.add(DeepCopyCharArrayList(assignment));
				} else {
					RecursiveBacktracking(assignment);
				}
			}
			// Remove from assignment
			assignment.set(index, null);
		}
	}
	
	
	public void printResults(){
		System.out.println("Size of results: "+ this.results.size());
		for(ArrayList<Character> soln : results){
			System.out.println();
			for(char c : soln){
				System.out.print(c + " -> ");
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
