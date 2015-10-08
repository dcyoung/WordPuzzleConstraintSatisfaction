/**
 * LetterBasedCSP: 
 * 	Solves a constraint satisfaction problem described by a puzzle object 
 * 	using a recursive backtracking algorithm. Functionally the algorithm 
 * 	conducts depth first search through the state space of possible assignments,
 * 	but halts each dive if a constraint is violated. 
 * 	
 * 	Letter Based version:
 * 		Variables X 	= 	index (of the solution array)
 * 		Domain D 		= 	permit-able letters for a given variable
 * 		Constraints C 	= 	a letter must be able to form a word from a connected 
 * 							category given previous specified letters
 *  
 * @author dcyoung3, nprince2
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class LetterBasedCSP {
	
	//Holds the puzzle defining the current CSP
	private Puzzle puzzle;
	//Holds the database of possible words for lookup by category
	private WordDatabase db;
	//Holds the final set of solutions. Each solution is an array of characters
	private ArrayList<ArrayList<Character>> results;
	//Holds a naive domain, where every letter from the capital alphabet is tried
	private ArrayList<Character> alphabet;
	
	/**
	 * Constructor
	 * @param puzzle
	 * @param db
	 */
	public LetterBasedCSP(Puzzle puzzle, WordDatabase db){
		this.puzzle = puzzle;
		this.db = db;
		this.results = new ArrayList<ArrayList<Character>>();
		DefineAlphabet();
	}
	
	/**
	 * Define the alphabet instance variable, so that it need not 
	 * be recreated every time a domain is querried
	 */
	private void DefineAlphabet(){
		alphabet = new ArrayList<Character>();
		char firstChar = 'A';
		for(int i = 0; i < 26; i++ ){
			alphabet.add((char) (firstChar+i));
		}
	}
	
	/**
	 * This is a helper function for the main Backtracking algorithm.
	 * Selects an unassigned variable from the assignment. Eventually
	 * this should consider factors such as most/least constrained variables
	 * but currently the naive implementation simply looks for the next undefined
	 * index. 
	 * @param assignment
	 * @return
	 */
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
	
	/**
	 * This is a helper method for the main backtracking algorithm.
	 * Eventually this will intelligently order the domain of possible values for 
	 * the variable index, but currently the naive method simply returns the normal 
	 * alphabet of uppercase characters. This is not efficient, but will work.
	 * @param index 
	 * @return a domain of possible values (permit-able letters) for the variable index
	 */
	private ArrayList<Character> OrderDomainValues(int index){
		//naive: return a-z since there will be lots of chars anyways
		return this.alphabet;
		//eventually: return the chars in the corresponding categories
		//return null;
	}
	
	/**
	 * Considers all the indices of the assignment connected to the given category
	 * and creates a regular expression using the already assigned characters and
	 * wildcard flags for unassigned characters.  
	 * @param category
	 * @param assignment
	 * @return a regular expression to compare against words in a category
	 */
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
	 * Checks that the char c proposed for the specified index is able to form
	 * a word from every connected category given previous specified letters 
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
	
	/**
	 * Deep copies the input array
	 * @param inputArray
	 * @return a copy of the input array which can be saved without 
	 * 			being affected by changes in the input array
	 */
	private ArrayList<Character> DeepCopyCharArrayList(ArrayList<Character> inputArray){
		ArrayList<Character> newArrayList = new ArrayList<Character>();
		for(char c : inputArray){
			newArrayList.add(c);
		}
		return newArrayList;
	}
	
	/**
	 * Main constraint satisfaction solver. 
	 * Effectively conducts depth first search on the state space of possible
	 * assignments for a CSP, halting any dive when a constraint is violated.
	 * @param assignment
	 * @return nothing, any found solutions are stored in the instance variable "results"
	 */
	public void RecursiveBacktracking(ArrayList<Character> assignment){
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
					//dive deeper into the tree (the passed in assignment here contains the char c)
					RecursiveBacktracking(assignment);
				}
			}
			// Remove from assignment, keeping the tree at the current depth 
			assignment.set(index, null);
			// removing the character ensures the next loop iteration is searching breadth
		}
	}
	
	
	/**
	 * Print the solution results
	 */
	public void printResults(){
		System.out.println("Size of results: "+ this.results.size());
		for(ArrayList<Character> soln : results){
			System.out.println();
			for(char c : soln){
				System.out.print(c + " -> ");
			}
		}
		
		int solnCount = 0;
		for(ArrayList<Character> soln : results){
			System.out.println();
			System.out.print("(Soln #"+ solnCount + ")");
			for(String category : this.puzzle.getCategoryIndexMap().keySet()){
				System.out.print("  " + category + ": ");
				for(int index : this.puzzle.getCategoryIndexMap().get(category)){
					System.out.print(soln.get(index));
				}
			}
			solnCount++;
		}
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
