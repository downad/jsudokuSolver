package sudoko_solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Das Model ist komplett unabhängig von den anderen
 * Klassen und weiß nicht was um ihn herum geschieht.
 * Es ist völlig egal ob man dieses Model aus einem
 * Fenster oder einer Konsolen Eingabe verwendet -
 * beiden würde funktionieren.
 * 
 * * angelehtn an 
 * http://blog.bigbasti.com/tutorial-model-view-controller-mvc-struktur-in-java-projekten-nutzen/
 *
 */
public class SudokuModel  {


	// Variablen die Cell beschreiben
    public  Map<String, SudokuCell> cell = new HashMap<String, SudokuCell>();
    
    // Konstanten für die Unit
	public  String[][] ROW = new String[(jsudokuSolver.MAXROW+1)][(jsudokuSolver.MAXCOL+1)];
	public  String[][] COL = new String[(jsudokuSolver.MAXROW+1)][(jsudokuSolver.MAXCOL+1)];
	public String[][] BLOCK = { {},
			{"", "11", "12", "13", "21", "22", "23", "31", "32", "33"}, // 1
			{"", "14", "15", "16", "24", "25", "26", "34", "35", "36"}, // 2
			{"", "17", "18", "19", "27", "28", "29", "37", "38", "39"}, // 3
			{"", "41", "42", "43", "51", "52", "53", "61", "62", "63"}, // 4
			{"", "44", "45", "46", "54", "55", "56", "64", "65", "66"}, // 5
			{"", "47", "48", "49", "57", "58", "59", "67", "68", "69"}, // 6
			{"", "71", "72", "73", "81", "82", "83", "91", "92", "93"}, // 7
			{"", "74", "75", "76", "84", "85", "86", "94", "95", "96"}, // 8
			{"", "77", "78", "79", "87", "88", "89", "97", "98", "99"}, // 9
	};
	
	// Liste für das Logging
	private ArrayList<logEntrySolving> logSolvingSteps = new ArrayList<>();
	private ArrayList<LogEntryError> logError = new ArrayList<>();
	
	// TextFeld für rhsPane
    private String rhsPaneText = "";
    
    // boolean die Anzeigt, dass das Spiel gelöst ist. 
    private boolean sudokuGameIsSolved =false;
    // passende Methode dazu
    public boolean getsudokuGameIsSolved() {
    	return sudokuGameIsSolved;
    }
    // Test ob das SPiel gelöst sit, und setze setSudokuGameIsSolved = true
	private boolean setSudokuGameIsSolved(){
		boolean returnboolean = false;
		if (getUnsolvedCell().size() < 1) {
			returnboolean = true;
			sudokuGameIsSolved = true;
		}
		return returnboolean;
	}
    
    /// IsSolvedBy für ENDE, das Sudoku ist fertig
	private int sudokuSolved = 99;
	// Zuordnung der Lösungsstrategie zu IsSolvedBy Intteger
	private String[] solvedByText = { "keine Angaben",
			"given", 		//	IsSolvedBy 	1 = Vorgabe / default
			"User",			// 	IsSolvedBy	2 = manuelle Eingabe
			"naked single",	//  IsSolvedBy	3 = naked single
			"Ende",			// 	IsSolvedBy 99 = Sudoku ist Solved
	};
    private boolean[] solvingStrategie = { false,false,false, // 0,1,2 ohne Bedeutung
    		false, //  3 - naked Single
    		false, //  4 - hidden Single
    		false, //  5 - 
    		false, //  6 - 
    		false, //  7 - 
    		false, //  8 - 
    		false, //  9 - 
    		false, // 10 -
    		false, // 11 -
    		
    };
    private String[] solvingStrategieText = { "1","2","3", // 0,1,2 ohne Bedeutung
    		"Naked Single", 	//  3 - naked Single
    		"Hidden Single", 	//  4 - hidden Single
    		"5", //  5 - 
    		"6", //  6 - 
    		"7", //  7 - 
    		"8", //  8 - 
    		"9", //  9 - 
    		"10", // 10 -
    		"11", // 11 -
    		
    };
    public void changeSolvingStrategie (int number){
    	if (solvingStrategie[number] == true) {
        	System.out.println("Model: changeSolvingStrategie - Change " +solvingStrategieText[number] + " to false");
        	solvingStrategie[number] = false;
    	} else {
    		System.out.println("Model: changeSolvingStrategie - Change " +solvingStrategieText[number] + " to true");
    		solvingStrategie[number] = true;
    	}
    }
    
    /*
     * Liste der Coordinaten in denen ein Value gefunden wurde
     * es wird ein Tripple int abgelegt.
     * [0] coordiante
     * [1] Value
     * [2] solvedBy
     */
    private LinkedList<logEntrySolving> coordiantesOfCellWhereValueWasSet = new LinkedList<logEntrySolving>();
    public LinkedList<logEntrySolving> getChangedCells(){
    	LinkedList<logEntrySolving> returnList = coordiantesOfCellWhereValueWasSet;
    	return returnList;
    }
    
	// booelan sollen die cellen.candats resettet und neu berechnet werden?
	// wichtig beim Löschen eines Wertes.
	private boolean setResetCandidates = false;
    
    /*
     * Initialisiere das Sudoku
     * und starte einige Methoden
     */
	public void startSudoku() {
		initSudoku();
		initLoadSudoku();
		initRhsPaneText();
	}
	
	/*
	 * Methoden für das Initialisieren
	 * lege alle ROW, und COL an
	 * lebe in jede Zelle einen Array mit den Koordinaten von Mitgliedern aus "deine Reihe", "deine Spalte", "dein Block"
	 */
	private void initSudoku() {
		// Init Sudoku
		// Jede Zelle wird angelegt.
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				COL[col][row] = "" + row + col;
				ROW[row][col] = "" + row + col;
				cell.put(""+row+col, new SudokuCell(row, col, 0, false, 0));
			}
		}
		// Initalisiere Hilfs Felder
		// Mitglieder deiner Reihe
		for (int i = 1; i<= 9; i++) {
			for (int ii = 1; ii<= 9; ii++) {
				cell.get(ROW[i][ii]).setMyROW(Arrays.copyOf(ROW[i], ROW[i].length));
			}
		}
		// Mitglieder deiner Spalte
		for (int i = 1; i<= 9; i++) {
			for (int ii = 1; ii<= 9; ii++) {
				cell.get(COL[i][ii]).setMyCOL(Arrays.copyOf(COL[i], COL[i].length));
			}
		}
		//Mitglieder deines Blocks
		for (int i = 1; i< BLOCK.length; i++) {
			for (int ii = 1; ii< BLOCK[i].length; ii++) {
				cell.get(BLOCK[i][ii]).setMyBLOCK(Arrays.copyOf(BLOCK[i], BLOCK[i].length));
			}
		}
	}
	
	/*
	 * Hole das Sudoku aus dem Datenspeicher
	 */
	private void initLoadSudoku(){
		// setCellValue ( int Value, boolean IsSolved, int IsSolvedBy)
		//	IsSolvedBy 	1 = Vorgabe / default
		// 	IsSolvedBy	2 = manuelle Eingabe
		//  IsSolvedBy	3 = naked single
		// 	IsSolvedBy 99 = Sudoku ist Solved
		ArrayList<String[]> SudokuGame = SudokuLoadData.myGame();
		for (int i = 0; i <SudokuGame.size(); i++) {
			//System.out.println("Model: createSudoku - Coordinate: " + SudokuGame.get(i)[0] + " Value " + SudokuGame.get(i)[1]);
			cell.get(SudokuGame.get(i)[0]).setCellValue(Integer.parseInt(SudokuGame.get(i)[1]), true, 1);
		}
	}
	
	/*
	 * Erzeuge den Ersten Eintrag in rhsPaneText 
	 */
	public void initRhsPaneText(){
		String SudokuHilfe = "<table Border=1  >";
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				int CellValueAsInt = cell.get(""+row+col).getCellValue();
				String CellValueAsString = "";
				if ( CellValueAsInt != 0) { 
					CellValueAsString = cell.get(""+row+col).getCandidatesAsStringTable();
				}
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px \">" +
				CellValueAsString + "</td>";							
			}
			SudokuHilfe = SudokuHilfe + "</tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";
		rhsPaneText = SudokuHilfe;
	}
	
	/*
	 * gehört zu den Init Methoden,
	 * aus dem geladenem Spiel wird ein Array aus Zahlen und leer = 0, damit die _view die InputFieldVlaue setzen kann 
	 */
	public String[] getSudokuAsStringArray() {
		String[] sudokuInputFieldValue = new String[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 2];
		int GridCount = 0;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				int CellValueAsInt = cell.get(""+row+col).getCellValue();
				GridCount++;
				sudokuInputFieldValue[GridCount] = "0";
				if ( CellValueAsInt != 0) { 
					sudokuInputFieldValue[GridCount] = ""+cell.get(""+row+col).getCellValue();
				}
			}
		}
		return sudokuInputFieldValue;
	}
	
	
	/*
	 * ////////////////////////////////////////////////////////////////
	 * //////
	 * //////					ENDE INIT Methoden
	 * //////
	 * ////////////////////////////////////////////////////////////////
	 */
	
	
	/*
	 * Setzte den Value in die Zelle [coordinate]
	 * Logge das Ergebnis in logSudokuSteps
	 * speichere das Tripple in coordiantesOfCellWhereValueWasSet
	 */
	//public LinkedList<int[]> trySetValueInCell (String coordinate, int value, int isSolvedBy){
	public boolean trySetValueInCell (String coordinate, int value, int isSolvedBy){
		//LinkedList<int[]> returnList = new LinkedList<int[]>();

		//ArrayList<int[]> returnList = new ArrayList<int[]>();
		ArrayList<String> errorList = new ArrayList<String>();
		boolean iCanSetValue = true;
		// ist die Zahl erlaubt?
		if (value < 1 || value > jsudokuSolver.MAXNUMBER) {
			iCanSetValue = false;
			errorList.add("nichterlaubter Value!");
		}
		// ist die Zelle leer?
		if (cell.get(coordinate).getIsSolved() == true) {
			iCanSetValue = false;
			errorList.add("Die Zelle is nicht leer!");
		}
		// passt Value in Units
		if (testValueInUnits(coordinate,value) == false) {
			iCanSetValue = false;
			errorList.add("Der Value passt nicht in die Units!");
		}
		if (iCanSetValue == true) {
			// schreibe einen Logeintrag
			logSolvingSteps.add(new logEntrySolving(getCoordinateStringWithSlashFromCoordinate(coordinate), value, isSolvedBy) );
			// setzte den Value
			setValueInCell(coordinate, value, isSolvedBy);
			//fülle den Speicher.
			coordiantesOfCellWhereValueWasSet.add(new logEntrySolving(coordinate, value, isSolvedBy));
			/*
			int setCellDouble[] = new int[2];
			setCellDouble[0] = cell.get(coordinate).getGridnumber();
			setCellDouble[1] = value;
			returnList.add(setCellDouble);
			*/
		} else {
			//LogEntryError(String coor, int val, int isSolved, String errortext,   ArrayList<String> errorlist)
			logError.add(new LogEntryError(coordinate,value, isSolvedBy, "Einsetzen", errorList));
		}
//		System.out.println("Model: trySetValueInCell - Setze Value " + value + " in Zelle [" + getCoordinateStringWithSlashFromCoordinate(coordinate) + "]");
//		System.out.println("Model: trySetValueInCell - iCanSetValue " + iCanSetValue);
//		for (int i = 0; i < errorList.size(); i++){
//			System.out.println("Model: trySetValueInCell - ErrorTest ["+i+"] - " + errorList.get(i));
//		}
//		return returnList;
		return iCanSetValue;
	}
	/*
	 * Setzte Value in Zelle
	 * Hilfsmethode zu trySetValueInCell
	 */
	private void setValueInCell(String coordinate, int value, int isSolvedBy){
		// Hier bin ich wenn coordinate, value und isSolvedBy passen.
		// setze das alles in die Zelle
		cell.get(coordinate).setCellValue(value, true, isSolvedBy);
		getValueAndSetValueInMyUnitsAsNotPossible(coordinate);
		
		if (setSudokuGameIsSolved() == true){			
			logSolvingSteps.add(new logEntrySolving("0/0", value, sudokuSolved) );
		}		
		//System.out.println("Model: setCellValue - Setze Value " + value + " in Zelle [" + getCoordinateStringWithSlashFromCoordinate(coordinate) + "]");
	}
	/*
	 * prüfe ob der Value in die Units gesetzt werden darf.
	 */
	private boolean testValueInUnits(String coordinate, int value){
		boolean returnBoolean = true;
		//hole alle Koordinaten der Value
		String[] myUnits = cell.get(coordinate).getMyUnits();
		
		for (int i = 0; i< (myUnits.length); i++) {  
			if (cell.get(myUnits[i]).getIsSolved() == true){
				if (value == cell.get(myUnits[i]).getCellValue()){
					returnBoolean = false;
					return returnBoolean;
				}
			}
		}
		//System.out.println("Model: testValueInUnits - returnBoolean = " + returnBoolean);
		return returnBoolean;
	}
	/*
	 * Schicke das logSudokuSteps auf die Console
	 */
	public void printLog() {
		System.out.println(" LogSudokuStep -> " );
		for (int i = 0; i < logSolvingSteps.size() -1; i++) {
			System.out.println("Model: - printLog - Step " + i + " " + logSolvingSteps.get(i).getAll()); 
		}
	}
	public void printErrorLog() {
		System.out.println(" ErrorLog -> " );
		for (int i = 0; i < logError.size(); i++) {
			System.out.println("Model: - ErrorLog - Step " + i + " " + logError.get(i).getAll()); 
		}
	}
	
	/*
	 * ////////////////////////////////////////////////////////////////
	 * //////
	 * //////					ENDE Setze Value in Zelle
	 * //////
	 * ////////////////////////////////////////////////////////////////
	 */	
	
	
	/*
	 * berechne die Candidaten und notPossibleValue
	 */
	private void resetCandidates(){
		setResetCandidates = true;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				cell.get(""+row+col).resetCandidates();
			}
		}
		setResetCandidates = false;
	}
	
	public void createListOfCandidatesInAllCells(){
		/* 
		 * durchlaufe alle Zellen
		 * ist die Celle gelöst IsSolved = true, lösche diese Zahl in allen Unit
		 * COL, ROW, BLOCK
		 */
		if (setResetCandidates == true){
			resetCandidates();
		}
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (cell.get(""+row+col).getIsSolved() == true) {
					getValueAndSetValueInMyUnitsAsNotPossible(""+row+col);
				}
			}
		}
	}
	
	/*
	 * Hilfsmethode dazu
	 * berechne die Candidaten, lösche Zahlen aus den Listen die nicht in der Zelle sein können.
	 */
	private void getValueAndSetValueInMyUnitsAsNotPossible(String coordinate){
		/* 
		 * setze ín MyUnits den Value als notPossible
		 */
		int value = cell.get(coordinate).getCellValue();
		String[] myUnits = cell.get(coordinate).getMyUnits();
		//System.out.println("Model: getValueAndsetValueInMyUnitsAsNotPossible - myUnits.length = " + myUnits.length );
		for (int i = 0; i< (myUnits.length); i++) {  
			//System.out.println("Model: getValueAndsetValueInMyUnitsAsNotPossible - myUnits = " + myUnits[i]);
			isCellUnsolvedSetValueAsNotPossible(myUnits[i], value);
		}
	}
	/*
	 * Hilfsmethode dazu
	 * berechne die Candidaten, lösche Zahlen aus den Listen die nicht in der Zelle sein können.
	 */
	private void  isCellUnsolvedSetValueAsNotPossible(String cellCoordinate, int Number ){
		if (cell.get(cellCoordinate).getIsSolved() == false) {
			cell.get(cellCoordinate).setNotPossibleValue(Number);
		}
	}
	
	/*
	 * ////////////////////////////////////////////////////////////////
	 * //////
	 * //////					ENDE Candidates
	 * //////
	 * ////////////////////////////////////////////////////////////////
	 */		

	
	
	/*
	 * Fülle rhsText mit einer Tabelle der Candidates
	 */
	public void createRhsPaneTextWithCandidatesInCell(){
		createListOfCandidatesInAllCells();
		//createListOfCandidatesInAllCells();
		String SudokuHilfe = "<table Border=1 >";
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px\">" +
				cell.get(""+row+col).getCandidatesAsStringTable() + "</td>";		
			}
			SudokuHilfe = SudokuHilfe + "</tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";
		rhsPaneText = SudokuHilfe;
	}

	// Generiere rhsPaneTExt mit den Infos zum gelösten Spiel
	public void setrhsPaneForGameIsSolved(){
		String SudokuHilfe ="Du hast das Sudoku in " + logSolvingSteps.size() + " Zügen gelöst.<table>" + 
					"<tr><th>Schritte</th><th>Zelle</th><th>Wert</th><th>Wer?</th></tr>";
		String[] solvingSteps = new String[3];
		for (int i = 0; i< logSolvingSteps.size() - 1; i++) {
			solvingSteps = logSolvingSteps.get(i).getSolvingStepTripple();
			int isolvedByText = Integer.parseInt(solvingSteps[2]);
			String thisSolvingText = solvedByText[isolvedByText];
			if (isolvedByText == 99) {
				thisSolvingText = "Ende";
			}
			SudokuHilfe = SudokuHilfe + "<tr><td>" + i + "</td><td> " + solvingSteps[0] + "</td>" + 
					"<td>" + solvingSteps[1] + "</td><td>" + thisSolvingText + "</td></tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";
		rhsPaneText = SudokuHilfe;	
	}
	
	/*
	 * hole den rhsPaneText
	 */
	public String getRhsPaneText(){
		return rhsPaneText;
	}
	

	/*
	 * ////////////////////////////////////////////////////////////////
	 * //////
	 * //////					ENDE rhsPaneText
	 * //////
	 * ////////////////////////////////////////////////////////////////
	 */	
	
	
	/*
	 * 	Mache aus Gridnummer eine ROW, COL oder Koordinate 
	 */
	private int getRowFromNumber( int Number){
		int row = getCoordinateByNumber(Number)[0];
    	return row;
	}
	private int getColFromNumber( int Number){
		int col = getCoordinateByNumber(Number)[1];
    	return col;
	}
	public int[] getCoordinateByNumber(int Number){
		int[] returnvalue= new int[2];
		int row = ((Number - 1)/ jsudokuSolver.MAXCOL) + 1;
    	int col = Number - (row  - 1)* jsudokuSolver.MAXCOL;
    	returnvalue[0] = row;
    	returnvalue[1] = col;
    	return returnvalue;
	}
	public String getCoordinateStringByNumber(int number){
		int[] intCoordinates= getCoordinateByNumber(number);
		String returnString = ""+intCoordinates[0]+intCoordinates[1];
		return returnString;
	}
	private String getCoordinateStringWithSlashFromCoordinate(String coordinate){
		String returnString = cell.get(coordinate).getCoordinateWithSlash();		
		return returnString;		
	}	
		
	/*
	 * ////////////////////////////////////////////////////////////////
	 * //////
	 * //////					ENDE umrechnungen GridNumber ->coordinate 
	 * //////
	 * ////////////////////////////////////////////////////////////////
	 */	
	
	

	// Welche Zellen sind noch ungelöst
	private ArrayList<String> getUnsolvedCell(){
		ArrayList<String> coordinates = new ArrayList<>();
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if ( cell.get(""+row+col).getIsSolved() == false) { 
					coordinates.add(""+row+col);
				}
			}
		}
		//System.out.println("Model: - getUnsolvedCell - " + coordinates.size());
		return coordinates;
	}
	
	// Ist die Zelle eine Given, d.h. sei wurde beim Start initialisert oder nicht?
	public boolean isGivenOrDeleteCellValue(int gridNumber){
		boolean returnboolean = true;
		if ( cell.get(getCoordinateStringByNumber(gridNumber)).getIsSolvedBy() == 1) {
			returnboolean = false;
		} else {
			cell.get(getCoordinateStringByNumber(gridNumber)).reset();
			resetCandidates();
		}
		return returnboolean;
	}


	
	
	
	/*
	 * autoSolving
	 */
	public LinkedList<int[]> autoSolving(){
		//public ArrayList<int[]> autoSolving(){
		//ArrayList<int[]> returnList = new ArrayList<int[]>();
		LinkedList<int[]> returnList = new LinkedList<int[]>();
		int oldValueUnsolvedCells = 1000; 	// egal, hauptsache groß
		int unsolvedCells = 999;			// egal, hauptsache kleiner
		ArrayList<String> coordinates;
		// initial Candidatenliste anlegen
		createListOfCandidatesInAllCells();
		//resetCandidates();
		String cellCoordinate = "";
		int counter = 0;
		ArrayList<String> testCoordinates = getUnsolvedCell();
		while (oldValueUnsolvedCells > unsolvedCells) {
			coordinates = getUnsolvedCell();
			testCoordinates = getUnsolvedCell();
			counter++;
			int returnListSize = 0;
			int i = -1;
			while (!testCoordinates.isEmpty()) {
			//for (int i = 0; i < coordinates.size(); i++){
				if (!testCoordinates.isEmpty()) {
					cellCoordinate = testCoordinates.remove(0);
				}
				i++;
				//if (returnList.size() >= returnListSize) {
				//	cellCoordinate = coordinates.remove(0);
				//}
				//System.out.println("Model: for-Shleife   - " + i +" coordinates " + coordinates.get(i));
				//System.out.println("Model: while-Shleife - " + i +" coordinates " + cellCoordinate);
				
				// solvingStrategie[3] -> naked single
				if (solvingStrategie[3]  == true) {
					System.out.println("Model: vergleich der Koordinaten - " + i +" for: " + coordinates.get(i)+ " while: " + cellCoordinate);
					//returnList.addAll(findNakedSingle(coordinates.get(i)));
					returnListSize = returnList.size();
					if (findNakedSingle(cellCoordinate) == true) {
						
					}
					//returnList.addAll(findNakedSingle(cellCoordinate));					
				}
				/*
				// solvingStrategie[4] -> hidden single
				if (solvingStrategie[4]  == true) {
					returnList.addAll(findHiddenSingle(cellCoordinate));		
//					returnList.addAll(findHiddenSingle(coordinates.get(i)));
				}
				*/
			}
			oldValueUnsolvedCells = unsolvedCells;
			unsolvedCells = coordinates.size();
			System.out.println("Model: While-Schleife - " + counter + " oldValueUnsolvedCells = " +oldValueUnsolvedCells + 
					" unsolvedCells = " +unsolvedCells);	
		}
		return returnList;
	}
	/*
	 * Methoden - findNakedSingle
	 */
	public boolean findNakedSingle(String coordinate){
		boolean returnBoolean = false; // nichts gefunden
		ArrayList<Integer> cellCandidates;
		cellCandidates = cell.get(coordinate).getCandidates();
		int solvedBy = 3; // nakedSingle
		if (cellCandidates.size()== 1) {
			// public ArrayList<int[]> trySetValueInCell (String coordinate, int value, int isSolvedBy	
			returnBoolean = trySetValueInCell (coordinate, cellCandidates.get(0), solvedBy);
		}
		return returnBoolean;
	}
	public LinkedList<int[]> findNakedSingle_old(String coordinate){
	//public ArrayList<int[]> findNakedSingle(String coordinate){
		LinkedList<int[]> returnList = new LinkedList<int[]>();
		//ArrayList<int[]> returnList = new ArrayList<int[]>();
		ArrayList<Integer> cellCandidates;
		cellCandidates = cell.get(coordinate).getCandidates();
		int solvedBy = 3; // nakedSingle
		if (cellCandidates.size()== 1) {
			// public ArrayList<int[]> trySetValueInCell (String coordinate, int value, int isSolvedBy	
			//returnList = trySetValueInCell (coordinate, cellCandidates.get(0), solvedBy);
		}
		return returnList;
	}
	/*
	 * Methoden - findNakedSingle
	 */
	/*
	public LinkedList<int[]> findHiddenSingle(String coordinate){
	//public ArrayList<int[]> findNakedSingle(String coordinate){
		LinkedList<int[]> returnList = new LinkedList<int[]>();
		//ArrayList<int[]> returnList = new ArrayList<int[]>();
		ArrayList<Integer> cellCandidates;
		cellCandidates = cell.get(coordinate).getCandidates();
		int solvedBy = 3; // nakedSingle
		if (cellCandidates.size()== 1) {
			// public ArrayList<int[]> trySetValueInCell (String coordinate, int value, int isSolvedBy	
			returnList = trySetValueInCell (coordinate, cellCandidates.get(0), solvedBy);
		}
		return returnList;
	}
	*/
}

/*
public ArrayList<int[]> findNakedSingle_old(){
	ArrayList<int[]> returnList = new ArrayList<int[]>();
	//resetCandidates();
	// hole alle ungelösten Zellen
	ArrayList<String> coordinates = getUnsolvedCell();
	ArrayList<Integer> cellCandidates;
	// durchlaufe sie und suche nach der, in der nur ein Candidate steht.
	for (int i = 0; i < coordinates.size(); i++){
		cellCandidates = cell.get(coordinates.get(i)).getCandidates();
		//System.out.println("Model: - findNakedSingle - List of unsolved Cells |" + coordinates.get(i) + "| Anzahl der Candidates; " + cellCandidates.size());
		if (cellCandidates.size()== 1) {
			int candidateValue = cellCandidates.get(0);
			System.out.println("Model: - findNakedSingle - nur 1 Candidate in Cell |" + coordinates.get(i) + 
					"| Zellenname: " + cell.get(coordinates.get(i)).getName() + " Wert: "  + candidateValue);// +
					//" Candidates: " + cellCandidates.get(i));
			//  IsSolvedBy	3 = naked single
			//setCellValue(String coordinate, int value, int isSolvedBy)
			int setCellDouble[] = new int[2];
			setCellDouble[0] = cell.get(coordinates.get(i)).getGridnumber();
			setCellDouble[1] = candidateValue;
			setValueInCell(coordinates.get(i),candidateValue, 3);
			returnList.add(setCellDouble);
		}
	}
	return returnList;
}
*/

/*
 *  ////////////////////////////////////////////////////////////////////////////////////////////////////////
 */

/*
 * Teste ob der Value in die Zelle passt.
 * Es gibt keinen Value in Unit ROW, COL und BLOCK
 */
/*
public boolean testAndSetValue(int gridNumber, int value, int isSolvedBy){
	boolean testAndSet = false;
	int row = getRowFromNumber(gridNumber);
	int col = getColFromNumber(gridNumber);
	String coordinate = ""+row+col;
	//System.out.println("Model: testAndSetValue: - Teste Value: " + value + " in Zelle " + gridNumber + " [" + row + "/" + col + "]");
	if (cell.get(coordinate).getIsSolved() == false) {
		//Test kann die Zahl in die Reihe, die Spalte und Block engesetzt werden?
		if (testValueInUnit("all", coordinate, value) == true) {
			setValueInCell(coordinate, value, isSolvedBy);
			testAndSet = true;
		}
	}		
	return testAndSet;
}
*/
/*
 * Hilfsmethode zu testAndSetValue
 */
/*
private boolean testValueInUnit(String unit, String coordinate, int value){
	boolean allIsOK = true;
	ArrayList<String> unitCoordinate = new ArrayList<>();
	switch (unit) {
    case "all": 
    case "row": 
    case "col":  
    case "block":  
    	String[] rowCoordinate = cell.get(coordinate).getMyROW();
    	String[] colCoordinate = cell.get(coordinate).getMyCOL();
    	String[] blockCoordinate = cell.get(coordinate).getMyBLOCK();        	
    	for (int i = 1 ; i < (rowCoordinate.length -1); i++){
    		if (unit.equals("all") || unit.equals("row")) {
        		if (cell.get(rowCoordinate[i]).getIsSolved() == true){
        			unitCoordinate.add(rowCoordinate[i]);
        		}
    		}
    		if (unit.equals("all") || unit.equals("col")) {
	        	if (cell.get(colCoordinate[i]).getIsSolved() == true){
        			unitCoordinate.add(colCoordinate[i]);
        		}
    		}
	        	if (unit.equals("all") || unit.equals("block")) {
	        		if (cell.get(blockCoordinate[i]).getIsSolved() == true){
        			unitCoordinate.add(blockCoordinate[i]);
        		}
	        	}
    	}
    	allIsOK = testThisValueInCoordinateList(value, unitCoordinate);
        break;
        
    default: allIsOK = false;;
        break;
	}
	return allIsOK;
}
*/
/*
 * Hilfsmethode zu testAndSetValue
 */
/*
private boolean testThisValueInCoordinateList(int value, ArrayList<String> coordinateList){
	// nur solved Cells sind in der Liste
	boolean valueIsAllowed = true;
	for (int i = 0; i < coordinateList.size(); i++){
		if (value == cell.get(coordinateList.get(i)).getCellValue()){
			valueIsAllowed = false;
			System.out.println("testThisValueInCoordinateList - Der Value " + value + " ist in der Zelle " + coordinateList.get(i) + " schon gesetzt");
		}
	}
	return valueIsAllowed;
}
*/
/*
 *  /////////////////////////////////////////////////////////////////////////////////////////////////////////
 */