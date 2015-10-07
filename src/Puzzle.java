import java.io.*;
import java.util.*;

public class Puzzle {

	//Map between word category and soln index
	private Map<String, ArrayList<Integer>> categoryIndexMap;
	//size of the resultant array (solution to the puzzle)
	private int puzzleSize;
	
	/**
	 * Constructor
	 * @param puzzleSize
	 */
	public Puzzle(int puzzleSize){
		this.puzzleSize = puzzleSize;
		categoryIndexMap = new HashMap<String, ArrayList<Integer>>();
	}
	
	
	public void PrintPuzzle(){
		String line;
		for(String category : this.categoryIndexMap.keySet()){
			line = category + ": " ;
			for(Integer i : this.categoryIndexMap.get(category)){
				line += i + ", ";
			}
			System.out.println(line);
		}
	}
	
	
	
	public Map<String, ArrayList<Integer>> getWordMap() {
		return this.categoryIndexMap;
	}
	
	public int getPuzzleSize(){
		return this.puzzleSize;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
