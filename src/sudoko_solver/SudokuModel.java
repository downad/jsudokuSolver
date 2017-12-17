package sudoko_solver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.BorderFactory;

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
			"Naked Single",	//  IsSolvedBy	3 = naked single
			"Hidden Single",
			"Naked Pair",
			"Hidden Pair",
			"Block-Line-Interception",
			"Ende",			// 	IsSolvedBy 99 = Sudoku ist Solved
	};
    private boolean[] solvingStrategie = { false,false,false, // 0,1,2 ohne Bedeutung
    		false, //  3 - Naked Single
    		false, //  4 - Hidden Single
    		false, //  5 - Naked Pair
    		false, //  6 - Hidden Pair
    		false, //  7 - Block-Line-Interaction
    		false, //  8 - 
    		false, //  9 - 
    		false, // 10 -
    		false, // 11 -
    		
    };
    private String[] solvingStrategieText = { "1","2","3", // 0,1,2 ohne Bedeutung
    		"Naked Single", 			//  3 - Naked Single
    		"Hidden Single", 			//  4 - Hidden Single
    		"Naked Pair", 				//  5 - Naked Pair
    		"Hidden Pair", 				//  6 - Hidden Pair
    		"Block-Line-Interaction", 	//  7 - Block-Line-Interaction
    		"8", //  8 - 
    		"9", //  9 - 
    		"10", // 10 -
    		"11", // 11 -
    		
    };
    // aktiviere und deaktiviere Solving-Strategien
    public void changeSolvingStrategie (int number){
    	if (solvingStrategie[number] == true) {
        	System.out.println("Model: changeSolvingStrategie - Change " +solvingStrategieText[number] + " to false");
        	solvingStrategie[number] = false;
    	} else {
    		System.out.println("Model: changeSolvingStrategie - Change " +solvingStrategieText[number] + " to true");
    		solvingStrategie[number] = true;
    	}
    }
    // Koodinatenspeicher der ungelösten Zellen
    private ArrayList<String> coordinatsOfUnsolvedCells = new ArrayList<>();
    private int indexOfCoordinatsOfUnsolvedCells = 0;
    
    // setzte Counter auf 0
    private void setIndexOfCoodinatesOfUnsolvedCells(){
    	indexOfCoordinatsOfUnsolvedCells = 0;
    }
    // erhöhe den Counter
    private void incIndexOfCoordinatsOfUnsolvedCells() {
    	// indexOfCoordinatsOfUnsolvedCells darf den Wert von coordiante..size() nicht überschreiten / erreichen 
    	if (indexOfCoordinatsOfUnsolvedCells +1 < coordinatsOfUnsolvedCells.size() && getIndexOfCoordinatsOfUnsolvedCells() != -1){
    		indexOfCoordinatsOfUnsolvedCells++;
    	} else {
    		indexOfCoordinatsOfUnsolvedCells = -1; // so lange ist der Array Nie!
    	}
    }
    // hole den Counter
    private int getIndexOfCoordinatsOfUnsolvedCells() {
    	return indexOfCoordinatsOfUnsolvedCells;
    }
    
    
    /*
     * Liste der Coordinaten in denen ein Value gefunden wurde
     * es wird ein Tripple abgelegt.
     * String  coordiante -->evtl. mal überarbeiten auf GridNumber
     * int Value
     * int solvedBy
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
				cell.get(""+i+ii).setMyROW(Arrays.copyOf(ROW[i], ROW[i].length));
				//System.out.println("ROW[i]["+ii+"] = " + ROW[i][ii]);
				//System.out.println("ROW[i].length] = " + ROW[i].length);
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
			String bgColor[] = {"yellow", "gray"};
			int color = 0;
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				int CellValueAsInt = cell.get(""+row+col).getCellValue();
				String CellValueAsString = "";
				if ( CellValueAsInt != 0) { 
					CellValueAsString = cell.get(""+row+col).getCandidatesAsStringTable();
				}
				if (((row - 1) / 3) == 0 || (row - 1) / 3 == 2) {
        			color = 1;
        			if (((col - 1) / 3) == 1) {									// top left buttom right
            			color = 0;
            		} 
        		} else {
        			color = 1;
        			if (((col - 1) / 3) == 0 || (col - 1) / 3 == 2) {									// top left buttom right
            			color = 0;
            		}
        		}
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px; background: "+bgColor[color] + "; \">" +
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
	 * bei erfolg return true
	 */
	public boolean trySetValueInCell (String coordinate, int value, int isSolvedBy){
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
			createLogEntry(getCoordinateStringWithSlashFromCoordinate(coordinate), value, isSolvedBy);
//			logSolvingSteps.add(new logEntrySolving(getCoordinateStringWithSlashFromCoordinate(coordinate), value, isSolvedBy) );
			// setzte den Value
			setValueInCell(coordinate, value, isSolvedBy);
			//fülle den Speicher.
			coordiantesOfCellWhereValueWasSet.add(new logEntrySolving(coordinate, value, isSolvedBy));
		} else {
			logError.add(new LogEntryError(coordinate,value, isSolvedBy, "Einsetzen", errorList));
		}
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
		//System.out.println("Model: - isCellUnsolvedSetValueAsNotPossible - cellCoordinate= " + cellCoordinate + " Number " + Number);
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
		String bgColor[] = {"yellow", "gray"};
		int color = 0;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (((row - 1) / 3) == 0 || (row - 1) / 3 == 2) {
        			color = 1;
        			if (((col - 1) / 3) == 1) {									// top left buttom right
            			color = 0;
            		} 
        		} else {
        			color = 1;
        			if (((col - 1) / 3) == 0 || (col - 1) / 3 == 2) {									// top left buttom right
            			color = 0;
            		}
        		}
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px; background: "+bgColor[color] + ";  \">" +
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
	/*
	private int getRowFromNumber( int Number){
		int row = getCoordinateByNumber(Number)[0];
    	return row;
	}
	private int getColFromNumber( int Number){
		int col = getCoordinateByNumber(Number)[1];
    	return col;
	}
	*/
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
	 * //////					ENDE Umrechnungen GridNumber ->coordinate 
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
	private void setCoordinateOfUnsolvedCell(){
		coordinatsOfUnsolvedCells = new ArrayList<>();
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if ( cell.get(""+row+col).getIsSolved() == false) { 
					coordinatsOfUnsolvedCells.add(""+row+col);
				}
			}
		}
		setIndexOfCoodinatesOfUnsolvedCells();
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
	private void createLogEntry(String coordinate, int value, int solvedBy){
		//String[] solvedByLog = {"1","2"} ;
		ArrayList<Integer> solvedByLog = new ArrayList<Integer>();
		solvedByLog.add(3); // Naked Single
		solvedByLog.add(4); // Hidden Single
		if (solvedByLog.contains(solvedBy)== true) {
			// die Zelle wurde gelöst, lege das Log an
			logSolvingSteps.add(new logEntrySolving(coordinate, value, solvedBy) );
		} else {
			logSolvingSteps.add(new logEntrySolving(coordinate, value, solvedBy) );
			logSolvingSteps.get(logSolvingSteps.size()-1).setHelperStategie(solvedBy);
		}
	}

	
	
	
	/*
	 * autoSolving
	 */
	public boolean autoSolving(){
		boolean returnBoolean = true; 		// alles OK
		int oldValueUnsolvedCells = 1000; 	// egal, hauptsache groß
		int unsolvedCells = 999;			// egal, hauptsache kleiner

		// initial Candidatenliste anlegen
		createListOfCandidatesInAllCells();
		
		int counter = 0;					// Für die Anzahl der Schleifen
		boolean foundValue = true;			// hat die Strategie einen Value erzeugt?
		while (oldValueUnsolvedCells > unsolvedCells) {
			setCoordinateOfUnsolvedCell();
			foundValue = true; // damit der nächste While kein inc macht

			// berechne die Abbruchbedingung
			oldValueUnsolvedCells = unsolvedCells;
			unsolvedCells = coordinatsOfUnsolvedCells.size();
			
			counter++; // ein Zähler für die Schleifen


			while (getIndexOfCoordinatsOfUnsolvedCells() != -1  && unsolvedCells > 0) {
				if (foundValue == true) {
					foundValue = false;			// hat die Strategie einen Value erzeugt?
	
				} else {
					foundValue = false;
					incIndexOfCoordinatsOfUnsolvedCells();
				}
				// solvingStrategie[3] -> Naked Single
				if (solvingStrategie[3]  == true && getIndexOfCoordinatsOfUnsolvedCells() != -1) {
					foundValue = findNakedSingle(getCoordianteOfUnsolvedCells(foundValue)); 
				}
				// solvingStrategie[4] -> Hidden Single
				if (solvingStrategie[4]  == true && getIndexOfCoordinatsOfUnsolvedCells() != -1) {
					foundValue = findHiddenSingle(getCoordianteOfUnsolvedCells(foundValue));		
				}
				// solvingStrategie[5] -> Naked Pair
				if (solvingStrategie[5]  == true && getIndexOfCoordinatsOfUnsolvedCells() != -1) {
					foundValue = findNakedPair(getCoordianteOfUnsolvedCells(foundValue));		
				}
				// solvingStrategie[6] -> Hidden Pair
				if (solvingStrategie[6]  == true && getIndexOfCoordinatsOfUnsolvedCells() != -1) {
					//foundValue = findHiddenPair(getCoordianteOfUnsolvedCells(foundValue));		
				} 
				// solvingStrategie[7] -> Block-Line-Interception
				if (solvingStrategie[7]  == true && getIndexOfCoordinatsOfUnsolvedCells() != -1) {
					foundValue = findBlockLineInterception(getCoordianteOfUnsolvedCells(foundValue));		
				} 
				
			
			}
			System.out.println("Model: While-Schleife - " + counter + " oldValueUnsolvedCells = " +oldValueUnsolvedCells + 
					" unsolvedCells = " +unsolvedCells);
			//setIndexOfCoodinatesOfUnsolvedCells();
			//break;
		}
		
		return returnBoolean; //returnList;
	}
	/*
	 * hole eine Cell-Coordinaten aus der Liste der noch nicht gelösten Zellen
	 * wurde eine Zelle in der vorherigen Strategie gelöst, foundValue = true, so muss der Counter erhögt werden.
	 */
	private String getCoordianteOfUnsolvedCells(boolean foundValue){
		String returnString ="";
		
		if (foundValue == true) {
			incIndexOfCoordinatsOfUnsolvedCells(); 
		} 
		int IndexOfCoordinatsOfUnsolvedCells = getIndexOfCoordinatsOfUnsolvedCells();
		if (IndexOfCoordinatsOfUnsolvedCells < 0) {
			returnString="";
		} else {
			returnString = coordinatsOfUnsolvedCells.get(IndexOfCoordinatsOfUnsolvedCells);
		}
		return returnString;
	}
	
	/*
	 * Methoden - findNakedSingle
	 */
	public boolean findNakedSingle(String coordinate){
		boolean returnBoolean = false; // nichts gefunden
		int solvedBy = 3; // nakedSingle
		//System.out.println("Model: findNakedSingle -  prüfe " + coordinate );
		ArrayList<Integer> cellCandidates;
		// coordinate = "" wenn coordinateOfUnsolveCell am Ende ist.
		if (coordinate.equals("") == false) {
			cellCandidates = cell.get(coordinate).getCandidates();			
			if (cellCandidates.size()== 1) {
				// public ArrayList<int[]> trySetValueInCell (String coordinate, int value, int isSolvedBy	
				//System.out.println("Model: findNakedSingle -  " + coordinate + " Value= " + cellCandidates.get(0));
				returnBoolean = trySetValueInCell (coordinate, cellCandidates.get(0), solvedBy);
			}
		}
		return returnBoolean;
	}
	/*
	 * Methoden - findHiddenSingle
	 */
	public boolean findHiddenSingle(String coordinate){
		boolean returnBoolean = false; // nichts gefunden
		int solvedBy = 4 ; // Hidden Single
		//System.out.println("Model: findHiddenSingle -  prüfe " + coordinate );
		
		int[] cellCandidatesInUnit = new int[(jsudokuSolver.MAXNUMBER+1)];
		String[] unitCoordinate = new String[jsudokuSolver.MAXNUMBER];
		// coordinate = "" wenn coordinateOfUnsolveCell am Ende ist.
		if (coordinate.equals("") == false ) {
			ArrayList<Integer> cellCandidates = cell.get(coordinate).getCandidates();
			if ( cellCandidates.size() > 1) {
				// hole unit, 
				for (int unitNumber = 1; unitNumber <=3; unitNumber++) {
					//System.out.println("Model: findHiddenSingle -  Unit " + unitNumber );
					cellCandidatesInUnit = new int[(jsudokuSolver.MAXNUMBER+1)];
					unitCoordinate = getUnitCoordinateRowColBlockByNumber(coordinate , unitNumber);
					cellCandidatesInUnit = SumCellCandidatesInUnit(cellCandidatesInUnit, coordinate);
					//cellCandidatesInUnit = cell.get(coordinate).getCandidates();
					for (int i = 0; i < unitCoordinate.length; i++){
						// ist es eine andere Koordinate? und ist die Zelle unsolved?
						if (unitCoordinate[i].equals(coordinate)== false && cell.get(unitCoordinate[i]).getIsSolved() == false){
							cellCandidatesInUnit = SumCellCandidatesInUnit(cellCandidatesInUnit, unitCoordinate[i]);
						}
					}
					for (int i = 1; i < cellCandidatesInUnit.length; i++) {
						//System.out.println("Model: findHiddenSingle - " + cellCandidatesInUnit[i] + " mal die " + i);
						if (cellCandidatesInUnit[i] == 1 && cellCandidates.contains(i)){
							//System.out.println("Model: findHiddenSingle - gefunden Koordinate " + coordinate + " mal die " + i);
							//System.out.println("Model: findHiddenSingle - gefunden Wert " + cellCandidatesInUnit[i] + " mal die " + i);
							returnBoolean = trySetValueInCell (coordinate, i, solvedBy);
						}
					}
				}
			}


		}
		return returnBoolean;
	}
	/*
	 * Hilfsmethode der find Hidden Pair
	 * Addiere alle Candidaten auf
	 */
	private int[] SumCellCandidatesInUnit(int[] cellCandidatesInUnit, String coordinate) {
		ArrayList<Integer> cellCandidates = cell.get(coordinate).getCandidates();;
		for (int i = 1; i <= cellCandidatesInUnit.length; i++) {
			if (cellCandidates.contains(i)){
				cellCandidatesInUnit[i]++;
			}
		}
		return cellCandidatesInUnit;
	}
		
		
	/*
	 * Methoden - findNakedPair
	 */
	public boolean findNakedPair(String coordinate){
		boolean returnBoolean = false; // nichts gefunden
		int solvedBy = 5 ; // Naked Pair
		//System.out.println("Model: findNakedPair -  prüfe " + coordinate );
		ArrayList<Integer> cellCandidates;
		ArrayList<Integer> maybeNakedPair;
		ArrayList<String> maybeNakedPairCoordinate = new ArrayList<String> ();
		//maybeNakedPairCoordinate.add(coordinate);
		String[] unitCoordinate = new String[jsudokuSolver.MAXNUMBER];
		ArrayList<String>  unsolvedUnitCells = new ArrayList<String> ();
		// coordinate = "" wenn coordinateOfUnsolveCell am Ende ist.
		if (coordinate.equals("") == false) {
			cellCandidates = cell.get(coordinate).getCandidates();
			// hat die Zelle 2 Candidaten?
			if (cellCandidates.size()== 2) {
				// Hole die Coorditante der Unit row = 1, col = 2, block = 3
				for (int unitNumber = 1; unitNumber <=3; unitNumber++) {
					unitCoordinate = getUnitCoordinateRowColBlockByNumber(coordinate , unitNumber);
					maybeNakedPairCoordinate = new ArrayList<String> ();
					maybeNakedPairCoordinate.add(coordinate);
					// durchlaufe alle Koordinaten, 
					for (int i = 0; i < unitCoordinate.length; i++){
						//ist es eine andere Koordinate? und ist die Zelle unsolved?
						if (unitCoordinate[i].equals(coordinate)== false && cell.get(unitCoordinate[i]).getIsSolved() == false){
							// hole die andidaten, sind sie gleich
							maybeNakedPair = cell.get(unitCoordinate[i]).getCandidates();
							if (maybeNakedPair.equals(cellCandidates)) {
								maybeNakedPairCoordinate.add(unitCoordinate[i]);
								//System.out.println("Model: findNakedPair -  Zelle gefunden " + unitCoordinate[i] );
							}
						}	
					}
					if (maybeNakedPairCoordinate.size()==2){
						// lösche in allen anderen Koordianten der Zelle diese Candidaten
						// resete  maybeNakedPairCoordinate für den nächsten durchlauf
						//System.out.println("Model: findNakedPair -  Zellen gefunden " + maybeNakedPairCoordinate.get(0) 
						//	+ " und " + maybeNakedPairCoordinate.get(1) );
						unsolvedUnitCells = getUnsolvedUnitCells(maybeNakedPairCoordinate,unitCoordinate );
						createLogEntry(maybeNakedPairCoordinate.get(0), 0, solvedBy);
						createLogEntry(maybeNakedPairCoordinate.get(1), 0, solvedBy);
						//returnBoolean = true; // sonst endlosschleife
						// lösche die Kandidaten der anderne Units-Zellen
						deleteCandidatesInUnit( unsolvedUnitCells,  cellCandidates);
						/*
						for (int cellInUnit = 0; cellInUnit < unsolvedUnitCells.size(); cellInUnit++) {
							for (int cellCandidateNumber = 0; cellCandidateNumber < cellCandidates.size(); cellCandidateNumber++){
								cell.get(unsolvedUnitCells.get(cellInUnit)).setNotPossibleValueByStrategie(cellCandidates.get(cellCandidateNumber));
//								System.out.println("Model: findNakedPair - Lösche in Zelle " + unsolvedUnitCells.get(cellInUnit) + " den Candidate " + cellCandidates.get(cellCandidateNumber));
							}
						}
						*/
						
					}					
				}
			}
			

		}
		return returnBoolean;
	}
	/*
	 * Hilfsmethode zu findNakedPair
	 * hole aus der Unit nur die Koordinaten ungelöste Zellen, die nicht in doNotChange sind.
	 */
	private ArrayList<String>getUnsolvedUnitCells(ArrayList<String> doNotChange, String[] unitCoordinate ){
		ArrayList<String>  returnString = new ArrayList<String> ();
		for (int i = 0; i < unitCoordinate.length; i++){
			if (cell.get(unitCoordinate[i]).getIsSolved() == false){
				if (doNotChange.contains(unitCoordinate[i]) == false){
					returnString.add(unitCoordinate[i]);
				}
			}
		}
		return returnString;
	}
	/*
	 * abhängig von der unitNumber 1 -> row, 2 -> col, 3 -> block
	 * wird ein Koordinaten-String zurückgegebeben
	 */
	private String[] getUnitCoordinateRowColBlockByNumber(String coordinate, int unitNumber){
		String[] returnString = new String[jsudokuSolver.MAXNUMBER];
		if (unitNumber == 1) {
			returnString = cell.get(coordinate).getMyROW();
		} 
		if (unitNumber == 2) {
			returnString = cell.get(coordinate).getMyCOL();
		} 
		if (unitNumber == 3) {
			returnString = cell.get(coordinate).getMyBLOCK();
		} 
		return returnString;
	}
	/*
	 * lösche in der CoordinateListe unsolvedUnitCells die candidaten
	 */
	private void deleteCandidatesInUnit(ArrayList<String> unsolvedUnitCells, ArrayList<Integer> cellCandidates) {
		// lösche die Kandidaten der anderne Units-Zellen
		for (int cellInUnit = 0; cellInUnit < unsolvedUnitCells.size(); cellInUnit++) {
			for (int cellCandidateNumber = 0; cellCandidateNumber < cellCandidates.size(); cellCandidateNumber++){
				cell.get(unsolvedUnitCells.get(cellInUnit)).setNotPossibleValueByStrategie(cellCandidates.get(cellCandidateNumber));
//				System.out.println("Model: findNakedPair - Lösche in Zelle " + unsolvedUnitCells.get(cellInUnit) + " den Candidate " + cellCandidates.get(cellCandidateNumber));
			}
		}
	}
	
	/*
	 * Methoden - findBlockLineInterception
	 */
	public boolean findBlockLineInterception(String coordinate){
		boolean returnBoolean = false; // nichts gefunden
		int solvedBy = 7 ; // Naked Pair
		System.out.println("Model: findBlockLineInterception -  prüfe " + coordinate );
		boolean debug = false;
		if (coordinate.equals("13") == true){
			System.out.println("Model: findBlockLineInterception -  prüfe " + "8/9" );
			debug = true;
		}
		ArrayList<Integer> cellCandidates;	// Kandidaten der Aktuellen Zelle
		String[] unitCoordinate = new String[jsudokuSolver.MAXNUMBER];
		unitCoordinate = getUnitCoordinateRowColBlockByNumber(coordinate , 3); // 3 = BLOCK
		ArrayList<String> BLICoordinate = new ArrayList<String>();		
		ArrayList<Integer> BLICandidate = new ArrayList<Integer>();
		
		//maybeNakedPairCoordinate.add(coordinate);
		ArrayList<String>  unsolvedUnitCells = new ArrayList<String> ();
		
		// coordinate = "" wenn coordinateOfUnsolveCell am Ende ist.
		if (coordinate.equals("") == false) {
			cellCandidates = cell.get(coordinate).getCandidates();
			// hat die Zelle mehr als 1 Candidaten?
			if (cellCandidates.size() > 1) {
				// durchlaufe alle Candidaten und schaue in welchen Zelle des Blockes sie noch vorkommen
				for (int cellCandidateNumber = 0; cellCandidateNumber < cellCandidates.size(); cellCandidateNumber++){
					// durchlaufe alle Felder im BLOCK
					BLICoordinate = new ArrayList<String>();		
					BLICandidate = new ArrayList<Integer>();
					for (int unitNumber = 0; unitNumber < unitCoordinate.length; unitNumber++) {
						if (cell.get(unitCoordinate[unitNumber]).getIsSolved() == false) {
							if (debug == true){
								//System.out.println("Model: findBlockLineInterception -  coordinaten " + unitCoordinate[unitNumber] + " Candidaten: " + 
								//		cell.get(unitCoordinate[unitNumber]).getCandidatesAsString());
							}
							if (cell.get(unitCoordinate[unitNumber]).isNumberACandidate(cellCandidates.get(cellCandidateNumber)) == true) {
								BLICoordinate.add(unitCoordinate[unitNumber]);
							}
						}

					}
					if (BLICoordinate.isEmpty() == false || BLICoordinate.size()<3 ) {
						// es gibt mindest 1 Zelle des BLOCK mit der Zahl oder maximal 3
						// prüfe ob die Coordinaten in einer ROW oder COL sind.
						boolean sameROW = true;
						boolean sameCOL = true;
						int rowOfCoordiante = cell.get(coordinate).getROW();
						int colOfCoordiante = cell.get(coordinate).getCOL();
						BLICandidate.add(cellCandidates.get(cellCandidateNumber));
						System.out.println("Model: findBlockLineInterception - BLICoordinate " + printStringArrayList(BLICoordinate));
						for (int i = 0; i < BLICoordinate.size(); i++) {
							if (debug == true){
								//System.out.println("Model: findBlockLineInterception - rowOfCoordiante = " + rowOfCoordiante);
								//System.out.println("Model: findBlockLineInterception - BLICoordinate = " + BLICoordinate.get(i) +" " + cell.get(BLICoordinate.get(i)).getROW());
							
								//System.out.println("Model: findBlockLineInterception - colOfCoordiante = " + colOfCoordiante);
								//System.out.println("Model: findBlockLineInterception - BLICoordinate = " + BLICoordinate.get(i) +" "  + cell.get(BLICoordinate.get(i)).getCOL());
								
							}
							if (rowOfCoordiante != cell.get(BLICoordinate.get(i)).getROW()) {
								sameROW = false;
							}
							if (colOfCoordiante != cell.get(BLICoordinate.get(i)).getCOL()) {
								sameCOL = false;
							}
						}
						if (sameROW == true) {
							// gleiche ROW
							// hole die Reihe, lösche daraus die Liste
							// und lösche die Kandidaten
							unitCoordinate = getUnitCoordinateRowColBlockByNumber(coordinate , 1); // 1 = ROW
							unsolvedUnitCells = getUnsolvedUnitCells(BLICoordinate,unitCoordinate );
							//createLogEntry(maybeNakedPairCoordinate.get(0), 0, solvedBy);
							// lösche die Kandidaten der anderne Units-Zellen
							deleteCandidatesInUnit( unsolvedUnitCells,  BLICandidate);
							if (debug == true){
								//for (int n = 0; n < unsolvedUnitCells.size(); n++) {
									System.out.println("Model: findBlockLineInterception - sameROW");
									System.out.println("Model: findBlockLineInterception -Cell ["+coordinate+"] BLICoordinate " + printStringArrayList(BLICoordinate));
									System.out.println("Model: findBlockLineInterception - unitCoordinate " + printStringArray(unitCoordinate));
									System.out.println("Model: findBlockLineInterception - unsolvedUnitCells.size() " + unsolvedUnitCells.size());
									System.out.println("Model: findBlockLineInterception -  lösche in den Koordinaten " + printStringArrayList(unsolvedUnitCells)
									+ " die Candidaten: " + BLICandidate.get(0));
								//}
							}
							
							
						}
						if (sameCOL == true) {
							// gleiche COL
							// hole die Reihe, lösche daraus die Liste
							// und lösche die Kandidaten
							unitCoordinate = getUnitCoordinateRowColBlockByNumber(coordinate , 2); // 2 = COL
							unsolvedUnitCells = getUnsolvedUnitCells(BLICoordinate,unitCoordinate );
							//createLogEntry(maybeNakedPairCoordinate.get(0), 0, solvedBy);
							// lösche die Kandidaten der anderne Units-Zellen
							deleteCandidatesInUnit( unsolvedUnitCells,  BLICandidate);
							if (debug == true){
								//for (int n = 0; n < unsolvedUnitCells.size(); n++) {
									System.out.println("Model: findBlockLineInterception - sameCOL");
									System.out.println("Model: findBlockLineInterception -Cell ["+coordinate+"] BLICoordinate " + printStringArrayList(BLICoordinate));
									System.out.println("Model: findBlockLineInterception - unitCoordinate " + printStringArray(unitCoordinate));
									System.out.println("Model: findBlockLineInterception - unsolvedUnitCells.size() " + unsolvedUnitCells.size());
									System.out.println("Model: findBlockLineInterception -  lösche in den Koordinaten " + printStringArrayList(unsolvedUnitCells)
									+ " die Candidaten: " + BLICandidate.get(0));
								//}
							}
						}
						
					}

				}
			}
		}
		return returnBoolean;
	}
	private String printStringArrayList (ArrayList<String> stringArrayList){
		String returnString ="";
		for (int n = 0; n < stringArrayList.size(); n++) {
			returnString = returnString + " " + stringArrayList.get(n);
		}
		return returnString;
	}
	private String printStringArray (String[] stringArray){
		String returnString ="";
		for (int n = 0; n < stringArray.length; n++) {
			returnString = returnString + " " + stringArray[n];
		}
		return returnString;
	}
	
	
}