package sudoko_solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	
	// Liste für dan Logging
	private ArrayList<SudokoSolvingStep> logSudokuSteps = new ArrayList<>();
	
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
    
	// booelan sollen die cellen.candats resettet und neu berechnet werden?
	// wichtig beim Löschen eines Wertes.
	private boolean setResetCandidates = false;
    
    /*
     * Initialisiere das Sudoku
     * und starte einige Methoden
     */
	public void startSudoku() {
		initSudoku();
		createSudoku();
		createStringWithFilledSudoku();
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
	private void createSudoku(){
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
	public void createStringWithFilledSudoku(){
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
	 * /////////////////// ENDE INIT Methoden
	 */
	

	/*
	 * berechne die Candidaten, lösche Zahlen aus den Listen die nicht in der Zelle sein können.
	 * Welche Zellen sind noch nicht gelöst
	 */
	public void createListOfCandidatesInAllCells(){
		/* durchlaufe alle Zellen
		 * ist die Celle gelöst IsSolved = true, lösche diese Zahl in allen Unit
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		if (setResetCandidates == true){
			for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
				for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
					cell.get(""+row+col).resetCandidates();
				}
			}
		}
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (cell.get(""+row+col).getIsSolved() == true) {
					createListCandidatesInCell(row, col);
				}
			}
		}
	}
	/*
	 * Hilfsmethode dazu
	 * berechne die Candidaten, lösche Zahlen aus den Listen die nicht in der Zelle sein können.
	 */
	private void createListCandidatesInCell(int row, int col){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		int Value = cell.get(""+row+col).getCellValue();
		//hole MyRow
		String[] MyRow = cell.get(""+row+col).getMyROW();
		for (int i = 1; i< (MyRow.length) - 1; i++) {
			testValueInCellAndSetNotPossibleValue(MyRow[i], Value);
		}
		//hole MyCol
		String[] MyCol = cell.get(""+row+col).getMyCOL();
		for (int i = 1; i< (MyCol.length) - 1; i++) {
			testValueInCellAndSetNotPossibleValue(MyCol[i], Value);
		}
		//hole MyBlock
		String[] MyBlock = cell.get(""+row+col).getMyBLOCK();
		for (int i = 1; i< (MyBlock.length) - 1; i++) {
			testValueInCellAndSetNotPossibleValue(MyBlock[i], Value);
		}
	}
	/*
	 * Hilfsmethode dazu
	 * berechne die Candidaten, lösche Zahlen aus den Listen die nicht in der Zelle sein können.
	 */
	private boolean testValueInCellAndSetNotPossibleValue(String CellKoordiante, int Number ){
		boolean returnvalue = false;
		if (cell.get(CellKoordiante).getIsSolved() == false) {
			cell.get(CellKoordiante).setNotPossibleValue(Number);
			returnvalue = true;
			//System.out.println("Model: testValueInCellAndSetNotPossibleValue - Die Zelle [" + CellKoordiante + "] ist nicht gelöst, lösche die Nummer " + Number);
		}
		//System.out.println(" Ende TestNumberAndClear!");
		return returnvalue;
	}
    
	
	/*
	 * Fülle rhsText mit einer Tabelle der Candidates
	 */
	public void createStringWithCandidatesInCell(){
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

	
	public String getRhsPaneText(){
		return rhsPaneText;
	}
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
				//ystem.out.println("getSudokuAsStringArray - GridCount: " + GridCount + " Value = |" + CellValueAsInt + "|");
			}
		}
		return sudokuInputFieldValue;
	}
	
	// Mache aus Gridnummer eine ROS, COL oder Koordinate 
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
	 * Teste ob der Value in die Zelle passt.
	 * Es gibt keinen Value in Unit ROW, COL und BLOCK
	 */
	public boolean testAndSetValue(int gridNumber, int value, int isSolvedBy){
		boolean testAndSet = false;
		int row = getRowFromNumber(gridNumber);
		int col = getColFromNumber(gridNumber);
		String coordinate = ""+row+col;
		//System.out.println("Model: testAndSetValue: - Teste Value: " + value + " in Zelle " + gridNumber + " [" + row + "/" + col + "]");
		if (cell.get(coordinate).getIsSolved() == false) {
			//Test kann die Zahl in die Reihe, die Spalte und Block engesetzt werden?
			if (testValueInUnit("all", coordinate, value) == true) {
				setCellValue(coordinate, value, isSolvedBy);
				testAndSet = true;
			}
		}		
		return testAndSet;
	}
	/*
	 * Hilfsmethode zu testAndSetValue
	 */
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
	/*
	 * Hilfsmethode zu testAndSetValue
	 */
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
	/*
	 * Setzte den Value in die Zelle [coordinate]
	 * Logge das Ergebnis ind logSudokuSteps
	 */
	private void setCellValue(String coordinate, int value, int isSolvedBy){
		//setValue ( int Value, boolean IsSolved, int IsSolvedBy)
		cell.get(coordinate).setCellValue(value, true, isSolvedBy);
		logSudokuSteps.add(new SudokoSolvingStep(getCoordinateStringWithSlashFromCoordinate(coordinate), value, isSolvedBy) );
		if (setSudokuGameIsSolved() == true){			
			logSudokuSteps.add(new SudokoSolvingStep("0/0", value, sudokuSolved) );
		}		
		System.out.println("setCellValue - Setze Value " + value + " in Zelle [" + coordinate + "]");
		//printLog();
	}
	/*
	 * Schicke das logSudokuSteps auf die Console
	 */
	public void printLog() {
		System.out.println(" LogSudokuStep -> " );
		for (int i = 0; i < logSudokuSteps.size(); i++) {
			System.out.println("Model: - printLog - Step " + i + " " + logSudokuSteps.get(i).getAll()); 
		}
	}
	
	// Generiere rhsPaneTExt mit den Infos zum gelösten Spiel
	public void setrhsPaneForGameIsSolved(){
		String SudokuHilfe ="Du hast das Sudoku in " + logSudokuSteps.size() + " Zügen gelöst.<table>" + 
					"<tr><th>Schritte</th><th>Zelle</th><th>Wert</th><th>Wer?</th></tr>";
		String[] solvingSteps = new String[3];
		for (int i = 0; i< logSudokuSteps.size() - 1; i++) {
			solvingSteps = logSudokuSteps.get(i).getSolvingStepTripple();
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
		System.out.println("Model: - setSudokuGameIsSolved - " + rhsPaneText);
	}
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
	private void resetCandidates(){
		setResetCandidates = true;
		createStringWithCandidatesInCell();
		setResetCandidates = false;
	}
	/*
	 * Methoden - findNakedSingle
	 */
	public ArrayList<int[]> findNakedSingle(){
		ArrayList<int[]> returnList = new ArrayList<int[]>();
		resetCandidates();
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
				setCellValue(coordinates.get(i),candidateValue, 3);
				returnList.add(setCellDouble);
			}
		}
		return returnList;
	}
}