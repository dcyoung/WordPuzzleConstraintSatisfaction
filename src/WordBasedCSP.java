/**
 * WordBasedCSP: 
 *  A wordVar is a the three indices linked to a category (as defined by the puzzle definition)
 *	A wordVal is the assigned letters at the indices linked to a category 
 *
 *  Variables X: a wordVar
 *  Domain D: all potential wordVals from the linked category
 *  Constraints: for every wordVar, the wordVal assigned to that wordVar 
 *  must exist in the wordVal's category
 *  
 * @author dcyoung3, nprince2, cwan3
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class WordBasedCSP {
	
	//Holds the puzzle defining the current CSP
	private Puzzle puzzle;
	//Holds the database of possible words for lookup by category
	private WordDatabase db;
	//Holds the final set of solutions. Each solution is an array of characters
	private ArrayList<ArrayList<Character>> results;
	
	/**
	 * Constructor
	 * @param puzzle
	 * @param db
	 */
	public WordBasedCSP(Puzzle puzzle, WordDatabase db){
		this.puzzle = puzzle;
		this.db = db;
		this.results = new ArrayList<ArrayList<Character>>();
	}
	
	/**
	 * 
	 * @param assignment
	 * @return a category with any unassigned characters
	 */
	private String SelectUnassignedVariable(ArrayList<Character> assignment){
		//should return a category
		int index = 0;
		for(int i = 0; i <assignment.size(); i++){
			if (assignment.get(i) == null){
				index = i;
				break;
			}
		}
		return this.puzzle.getIndexCategoryMap().get(index).get(0);
	}
	
	/**
	 * 
	 * @param category
	 * @return a domain of possible values for the variable
	 */
	private ArrayList<String> OrderDomainValues(String category){
		return this.db.getWordMap().get(category);
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
	 * Checks that the wordVal proposed for the specified category is does
	 * not violate any constraints at indices shared with other categories 
	 * @param augmentedCategory
	 * @param assignment
	 * @return
	 */
	private boolean CheckIfConsistent(String augmentedCategory, ArrayList<Character> assignment){
		//Eventually an efficient solution would check
		//each category linked to any index of the augmented category
		
		//naive, check every category		
		for(String category: this.puzzle.getCategoryIndexMap().keySet()){
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
		for(int i = 0 ; i < inputArray.size(); i++){
			newArrayList.add(inputArray.get(i));
		}
		return newArrayList;
	}
	
	/**
	 * Adds the 3 character from wordVal to the assignment at the 3 indices linked the category
	 * @param assignment
	 * @param category
	 * @param wordVal
	 */
	private void AddToAssignment(ArrayList<Character> assignment, String category, String wordVal){
		ArrayList<Integer> indices = this.puzzle.getCategoryIndexMap().get(category);
		int counter = 0;
		for (int i : indices){
			assignment.set(i, wordVal.charAt(counter));
			counter++;
		}
	}
	
	/**
	 * Main constraint satisfaction solver. 
	 * Effectively conducts depth first search on the state space of possible
	 * assignments for a CSP, halting any dive when a constraint is violated.
	 * @param assignment
	 * @return nothing, any found solutions are stored in the instance variable "results"
	 */
	public void RecursiveBacktracking(ArrayList<Character> assignment, int depth){
		//wordVar here logically refers to 3 indices, but will be a category
		String category = SelectUnassignedVariable(assignment);
		
		for(String wordVal : this.OrderDomainValues(category)){
			//Remember the current assignment for later
			ArrayList<Character> old_assignment = DeepCopyCharArrayList(assignment);
			// Add it to the assignment
			AddToAssignment(assignment, category, wordVal);
			
			//if wordVal is consistent with assignment given constraints
			if(CheckIfConsistent(category, assignment)){
				System.out.print(" -> " + wordVal );
				
				if (!assignment.contains(null)) {
					//add it to solution set
					if(!DuplicateResultCheck(assignment)){
						this.results.add(DeepCopyCharArrayList(assignment));
						System.out.print("(found result: " + assignment.toString() + ")\n");
						for (int i = 0; i < depth; i++) {
							System.out.print("       ");
						}
					}
				} else {
					//dive deeper into the tree (the passed in assignment here contains the wordVal)
					RecursiveBacktracking(assignment, depth+1);
				}
			}
			// Remove from assignment, keeping the tree at the current depth
			assignment = old_assignment;
			// removing the word ensures the next loop iteration is searching breadth
		}
		
		System.out.print(" -> backtrack\n");
		for (int i = 0; i < depth-1; i++) {
			System.out.print("       ");
		}
	}
	
	/**
	 * Return true if the completed assignment already exists in results
	 * @param assignment
	 * @return
	 */
	private boolean DuplicateResultCheck(ArrayList<Character> assignment) {
		for(ArrayList<Character> r : this.results){
			if(r.equals(assignment)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Print the solution results
	 */
	public void printResults(){
		System.out.println("Test Results:");
		boolean[] testResults = this.testResults();
		
		//print out the assembled words for each category... 
		int solnCount = 0;
		for(ArrayList<Character> soln : results){
			System.out.println();
			System.out.print("(Soln #"+ solnCount + ": ");
			for(char c : soln){
				System.out.print(c);
			}
			System.out.print(")");
			
			for(String category : this.puzzle.getCategoryIndexMap().keySet()){
				System.out.print("  " + category + ": ");
				for(int index : this.puzzle.getCategoryIndexMap().get(category)){
					System.out.print(soln.get(index));
				}
			}
			
			System.out.print("\t[Valid = "+ testResults[solnCount] + "]");
			solnCount++;
		}
		System.out.println();
	}
	
	public boolean[] testResults(){
		boolean[] testResults = new boolean[this.results.size()];
		int solnCount = 0;
		boolean bSolnValid;
		String tempWord;
		
		for(ArrayList<Character> soln : results){
			bSolnValid = true;
			for(String category : this.puzzle.getCategoryIndexMap().keySet()){
				tempWord = "";
				for(int index : this.puzzle.getCategoryIndexMap().get(category)){
					tempWord += soln.get(index);
				}
				if(!this.db.getWordMap().get(category).contains(tempWord)){
					bSolnValid = false;
				}
			}
			testResults[solnCount] = bSolnValid;
			solnCount++;
		}
		return testResults;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
