/**
 * Puzzle: Holds the data for a puzzle in the form of 2 maps
 * 	1. categoryIndexMap: 
 * 		key = category, 
 * 		value = array of indices corresponding to spaces in the solution 
 * 				array that belong to word from that category
 *  2. indexCategoryMap: 
 *  	key = index corresponding a space in the solution array
 *  	value = array of categories connected to that index 
 * 
 * @author dcyoung3
 */

import java.io.*;
import java.util.*;

public class Puzzle {

	//Map between word category and soln indices
	private Map<String, ArrayList<Integer>> categoryIndexMap;
	//Map between indices of the solution and categories
	private Map<Integer, ArrayList<String>> indexCategoryMap;
	//size of the resultant array (solution to the puzzle)
	private int puzzleSize;
	
	/**
	 * Constructor
	 * @param puzzleSize
	 */
	public Puzzle(int puzzleSize){
		this.puzzleSize = puzzleSize;
		this.categoryIndexMap = new HashMap<String, ArrayList<Integer>>();
		this.indexCategoryMap = new HashMap<Integer, ArrayList<String>>();
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
	
	public void PrintPuzzleInvert(){
		String line;
		for(Integer index : this.indexCategoryMap.keySet()){
			line = index + ": " ;
			for(String category : this.indexCategoryMap.get(index)){
				line += category + ", ";
			}
			System.out.println(line);
		}
	}
	
	
	public void GenerateInvertedMap(){
		for(int i = 0; i< this.puzzleSize; i++){
			this.indexCategoryMap.put(i, new ArrayList<String>());
		}
		for(String key : this.categoryIndexMap.keySet()){
			for(int x : this.categoryIndexMap.get(key)){
				this.indexCategoryMap.get(x).add(key);
			}
		}
	}
	
	public Map<String, ArrayList<Integer>> getCategoryIndexMap() {
		return this.categoryIndexMap;
	}
	
	public Map<Integer, ArrayList<String>> getIndexCategoryMap() {
		return indexCategoryMap;
	}
	
	
	public int getPuzzleSize(){
		return this.puzzleSize;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
