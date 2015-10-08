/**
 * WordFileReader: reads files of a specific format into word databases or puzzle structures
 * 
 * @author dcyoung3
 */
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
	 * Used to read the database file called "wordList.txt" into a WordDatabase structure
	 * @param filename
	 */
	private void ReadFile(String filename){
		try {
			Scanner sc = new Scanner(new File(filename));
			String tempLine;
			String tempCategory;
			
			while(sc.hasNextLine()){
            	tempLine = sc.nextLine();
            	tempCategory = tempLine.split(":")[0].trim();
            	ArrayList<String> tempWords = new ArrayList<String>();
            	String[] words = tempLine.split(":")[1].split(",");
            	for(String w : words){
            		tempWords.add(w.trim());
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
	 * Also converts the index from the file (1 base) to index for the database arraylists vals (0 base)
	 * @param filename the file containing the puzzle information
	 * @return a puzzle containing the data held in the specified file
	 */
	private Puzzle ReadPuzzle(String filename){
		
		try {
			Scanner sc = new Scanner(new File(filename));
			int puzzleSize = sc.nextInt();
			Puzzle p = new Puzzle(puzzleSize);
			
			String tempLine;
			String tempCategory;
			
			while(sc.hasNextLine()){
            	tempLine = sc.nextLine();
            	if(tempLine.isEmpty()){
            		if(sc.hasNextLine())
            			tempLine = sc.nextLine();
            		else
            			return p;
            	}
            	tempCategory = tempLine.split(":")[0].trim();
            	ArrayList<Integer> tempIndices = new ArrayList<Integer>();
            	for(String s : tempLine.split(":")[1].split(",")){
            		tempIndices.add(Integer.parseInt(s.trim())-1);
            	}
            	Collections.sort(tempIndices);
            	//generate the normal map (ie: mapping categories as keys to indices as values)
            	p.getCategoryIndexMap().put(tempCategory, tempIndices);
			}
			//Generate the inverted map (ie: mapping indices as keys to categories as values)
			p.GenerateInvertedMap();
			return p;
		} catch (FileNotFoundException e) {
			System.out.println("Filename: \"" + filename + "\"\t could not be found.");
			e.printStackTrace();
		}		
		return null;
	}
	
	
	/**
	 * Main, used temporarily to test the functionality
	 * @param args
	 */
	public static void main(String[] args) {
		
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		
		String filename = "./words/wordlist.txt";
		fr.ReadFile(filename);
		//fr.dataBase.PrintDatabase();

		String puzzleName = "./words/puzzle1.txt";
		Puzzle p = fr.ReadPuzzle(puzzleName);
		
		LetterBasedCSP csp = new LetterBasedCSP(p, db);
		ArrayList<Character> assignment = new ArrayList<Character>();
		for (int i = 0; i < p.getPuzzleSize(); i++){
			assignment.add(null);
		}
		csp.RecursiveBacktracking(assignment);
		csp.printResults();
		//p.PrintPuzzle();
		//p.PrintPuzzleInvert();
		
	}

}
