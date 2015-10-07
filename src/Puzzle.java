import java.io.*;
import java.util.*;

public class Puzzle {

	//Map between word category and a list of all words belonging to that category
	private Map<String, ArrayList<String>> wordMap;
	//size of the resultant array (solution to the puzzle)
	private int puzzleSize;
	
	/**
	 * Constructor
	 * @param puzzleSize
	 */
	public Puzzle(int puzzleSize){
		this.puzzleSize = puzzleSize;
		wordMap = new HashMap<String, ArrayList<String>>();
	}
	
	public Map<String, ArrayList<String>> getWordMap() {
		return this.wordMap;
	}
	
	public int getPuzzleSize(){
		return this.puzzleSize;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
