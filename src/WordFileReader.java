import java.io.*;
import java.util.*;


public class WordFileReader {
	
	//Map between word category and a list of all words belonging to that category
	private WordDatabase dataBase;
	
	/**
	 * Constructor
	 */
	public WordFileReader(WordDatabase db){
		this.dataBase = db;
	}
	
	/**
	 * Reads the specified file into a hashmap
	 * @param filename
	 */
	private void ReadFile(String filename){
		try {
			Scanner sc = new Scanner(new File(filename));
			String tempLine;
			String tempCategory;
			ArrayList<String> tempWords = new ArrayList<String>();
			
			while(sc.hasNextLine()){
            	tempLine = sc.nextLine();
            	tempCategory = tempLine.split(":")[0].trim();
            	tempWords.clear();
            	for(String s : tempLine.split(":")[1].split(",")){
            		tempWords.add(s.trim());
            	}
            	this.dataBase.getWordMap().put(tempCategory, tempWords);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Filename: \"" + filename + "\"\t could not be found.");
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Creates a puzzle object by reading the puzzle information contained in the specified file.
	 * @param filename the file containing the puzzle information
	 * @param db database of all categories and words
	 * @return a puzzle containing the data held in the specified file
	 */
	private Puzzle ReadPuzzle(String filename, WordDatabase db){
		
		try {
			Scanner sc = new Scanner(new File(filename));
			int puzzleSize = sc.nextInt();
			Puzzle p = new Puzzle(puzzleSize);
			
			String tempLine;
			String tempCategory;
			ArrayList<String> tempWords = new ArrayList<String>();
			
			while(sc.hasNextLine()){
            	tempLine = sc.nextLine();
            	if(tempLine.isEmpty()){
            		if(sc.hasNextLine())
            			tempLine = sc.nextLine();
            		else
            			return p;
            	}
            	tempCategory = tempLine.split(":")[0].trim();
            	tempWords.clear();
            	for(String s : tempLine.split(":")[1].split(",")){
            		tempWords.add(db.getWordMap().get(tempCategory).get(Integer.parseInt(s.trim())));
            	}
            	p.getWordMap().put(tempCategory, tempWords);
            	
            	System.out.print(tempCategory + ":");
            	for(String s : tempWords){
            		System.out.print(s);
            		System.out.println();
            	}
			}
			return p;
		} catch (FileNotFoundException e) {
			System.out.println("Filename: \"" + filename + "\"\t could not be found.");
			e.printStackTrace();
		}		
		return null;
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		
		String filename = "./words/wordlist.txt";
		fr.ReadFile(filename);
		
		String puzzleName = "./words/puzzle1.txt";
		fr.ReadPuzzle(puzzleName, db);
		
	}

}
