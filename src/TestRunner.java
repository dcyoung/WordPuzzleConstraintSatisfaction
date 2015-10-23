import java.util.ArrayList;

public class TestRunner {
	
	/**
	 * Constructor
	 */
	public TestRunner(){
		
	}
	
	public void testWordDatabase(){
		this.printSeparator();
		
		System.out.println("Beginning Test: testWordDatabase()");
		WordDatabase db = new WordDatabase();
		//do some test
		
		System.out.println("Completed Test: testWordDatabase()");
	}
	
	public void testReadDatabaseFile(String file){
		this.printSeparator();
		System.out.println("Beginning Test: testReadDatabaseFile( " + file + " )");
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		
		String filename = "./words/wordlist.txt";
		fr.readDatabaseFile(filename);
		
		//do some test
		
		//fr.dataBase.PrintDatabase();
		System.out.println("Completed Test: testReadDatabaseFile( " + file + " )");
	}
	
	public void testReadPuzzleFile(String file){
		this.printSeparator();
		System.out.println("Beginning Test: testReadPuzzleFile( " + file + " )");
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		String puzzleName = "./words/puzzle1.txt";
		Puzzle p = fr.readPuzzleFile(puzzleName);
		
		//do some test
		
		//p.PrintPuzzle();
		//p.PrintPuzzleInvert();
		System.out.println("Completed Test: testReadPuzzleFile( " + file + " )");
	}
	
	public void testLetterBasedCSP(String dbFile, String pFile){
		this.printSeparator();
		System.out.println("Beginning Test: testLetterBasedCSP( " + dbFile + ", " + pFile + " )");
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		
		fr.readDatabaseFile(dbFile);
		
		Puzzle p = fr.readPuzzleFile(pFile);
		
		LetterBasedCSP csp = new LetterBasedCSP(p, db);
		ArrayList<Character> assignment = getBlankAssignment(p);
		
		csp.RecursiveBacktracking(assignment);
		
		//csp.printTrace(); //this function doesn't do what we want.... we have to modify the recursivebacktracking function to print the trace
		csp.printResults();
		
		System.out.println("Completed Test: testLetterBasedCSP( " + dbFile + ", " + pFile + " )");
	}
	
	public void testWordBasedCSP(String dbFile, String pFile){
		this.printSeparator();
		System.out.println("Beginning Test: testWordBasedCSP( " + dbFile + ", " + pFile + " )");
		WordDatabase db = new WordDatabase();
		WordFileReader fr = new WordFileReader(db);
		
		fr.readDatabaseFile(dbFile);
		
		Puzzle p = fr.readPuzzleFile(pFile);
		
		WordBasedCSP csp = new WordBasedCSP(p, db);
		ArrayList<Character> assignment = getBlankAssignment(p);
		
		csp.RecursiveBacktracking(assignment, 0);
		
		csp.printResults();
		System.out.println("Completed Test: testWordBasedCSP( " + dbFile + ", " + pFile + " )");
	}
	
	public ArrayList<Character> getBlankAssignment(Puzzle p){
		ArrayList<Character> assignment = new ArrayList<Character>();
		for (int i = 0; i < p.getPuzzleSize(); i++){
			assignment.add(null);
		}
		return assignment;
	}
	
	private void printSeparator(){
		System.out.println("\n-----------------------------------------------------"
				+ "---------------------------------------------------------\n");
	}
	public static void main(String[] args) {
		TestRunner tr = new TestRunner();
		String dbFile = "./words/wordlist.txt";
		tr.testWordDatabase();
		tr.testReadDatabaseFile(dbFile);
		tr.testReadPuzzleFile("./words/puzzle1.txt");
		
		for(int i = 1; i < 6; i++ ){
			tr.testLetterBasedCSP(dbFile, "./words/puzzle" + i + ".txt");
			tr.testWordBasedCSP(dbFile, "./words/puzzle" + i + ".txt");
			
		}
		
	}

}
