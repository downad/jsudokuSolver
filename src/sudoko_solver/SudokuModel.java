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
 */
public class SudokuModel  {

    // Sudoku
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
	private ArrayList<SudokoSolvingStep> LogSudokuStep = new ArrayList<>();
	
	// TextFeld für rhsPane
    private String rhsText = "";
    
    /*
     * Initialisiere das Sudoku
     */
	public void StartSudoku() {
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
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
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
		// Mitgliedder deiner Spalte
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
		ArrayList<String[]> SudokuGame = SudokuLoadData.myGame();
		for (int i = 0; i <SudokuGame.size(); i++) {
			//System.out.println("Model: createSudoku - Coordinate: " + SudokuGame.get(i)[0] + " Value " + SudokuGame.get(i)[1]);
			cell.get(SudokuGame.get(i)[0]).setCellValue(Integer.parseInt(SudokuGame.get(i)[1]), true, 1);
		}
	}
	/*
	 * Erzeuge den Ersten Eintrag in rhsText 
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
		rhsText = SudokuHilfe;
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
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
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
			System.out.println("Model: testValueInCellAndSetNotPossibleValue - Die Zelle [" + CellKoordiante + "] ist nicht gelöst, lösche die Nummer " + Number);
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
		rhsText = SudokuHilfe;
	}

	
	public String getrhsText(){
		return rhsText;
	}
	public String[] getSudokuAsStringArray() {
		String[] SudokuInputFieldValue = new String[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 2];
		int GridCount = 0;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				int CellValueAsInt = cell.get(""+row+col).getCellValue();
				GridCount++;
				SudokuInputFieldValue[GridCount] = "0";
				if ( CellValueAsInt != 0) { 
					SudokuInputFieldValue[GridCount] = ""+cell.get(""+row+col).getCellValue();
				}
				//ystem.out.println("getSudokuAsStringArray - GridCount: " + GridCount + " Value = |" + CellValueAsInt + "|");
			}
		}
		return SudokuInputFieldValue;
	}
	private int getRowFromNumber( int Number){
		int row = getCoordinateByNumber(Number)[0];
//		int row = ((Number - 1)/ jsudokuSolver.MAXCOL) + 1;
 //   	int col = Number - (row  - 1)* jsudokuSolver.MAXCOL;
    	return row;
	}
	private int getColFromNumber( int Number){
		int col = getCoordinateByNumber(Number)[1];
//		int row = ((Number - 1)/ jsudokuSolver.MAXCOL) + 1;
 //   	int col = Number - (row  - 1)* jsudokuSolver.MAXCOL;
    	return col;
	}
	private int[] getCoordinateByNumber(int Number){
		int[] returnvalue= new int[2];
		int row = ((Number - 1)/ jsudokuSolver.MAXCOL) + 1;
    	int col = Number - (row  - 1)* jsudokuSolver.MAXCOL;
    	returnvalue[0] = row;
    	returnvalue[1] = col;
    	return returnvalue;
	}
	
	/*
	 * Teste ob der Value in die Zelle passt.
	 * Es gibt keinen Value in Unit ROW, COL und BLOCK
	 */
	public boolean testAndSetValue(int GridNumber, int Value, int IsSolvedBy){
		boolean TestAndSet = false;
		int row = getRowFromNumber(GridNumber);
		int col = getColFromNumber(GridNumber);
		String Coordinate = ""+row+col;
		System.out.println("Model: testAndSetValue: - Teste Value: " + Value + " in Zelle " + GridNumber + " [" + row + "/" + col + "]");
		//int Value = Integer.parseInt(ValueAsString);
		// Teste ist die Zelle ungelöst?
		if (cell.get(Coordinate).getIsSolved() == false) {
			//Test kann die Zahl in die Reihe, die Spalte und Block engesetzt werden?
			if (testValueInUnit("all", Coordinate, Value) == true) {
				setCellValue(Coordinate, Value, IsSolvedBy);
				TestAndSet = true;
			}
		}		
		return TestAndSet;
	}
	private boolean testValueInUnit(String Unit, String Coordinate, int Value){
		boolean allIsOK = true;
		ArrayList<String> UnitCoordinate = new ArrayList<>();
		switch (Unit) {
        case "all": 
        case "row": 
        case "col":  
        case "block":  
        	String[] RowCoordinate = cell.get(Coordinate).getMyROW();
        	String[] ColCoordinate = cell.get(Coordinate).getMyCOL();
        	String[] BlockCoordinate = cell.get(Coordinate).getMyBLOCK();        	
        	for (int i = 1 ; i < (RowCoordinate.length -1); i++){
        		if (Unit.equals("all") || Unit.equals("row")) {
	        		if (cell.get(RowCoordinate[i]).getIsSolved() == true){
	        			UnitCoordinate.add(RowCoordinate[i]);
	        		}
        		}
        		if (Unit.equals("all") || Unit.equals("col")) {
    	        	if (cell.get(ColCoordinate[i]).getIsSolved() == true){
	        			UnitCoordinate.add(ColCoordinate[i]);
	        		}
        		}
   	        	if (Unit.equals("all") || Unit.equals("block")) {
   	        		if (cell.get(BlockCoordinate[i]).getIsSolved() == true){
	        			UnitCoordinate.add(BlockCoordinate[i]);
	        		}
   	        	}
        	}
        	allIsOK = testThisValueInCoordinateList(Value, UnitCoordinate);
            break;
            
        default: allIsOK = false;;
            break;
		}
		return allIsOK;
	}
	private boolean testThisValueInCoordinateList(int Value, ArrayList<String> CoordinateList){
		// nur solved Cells sind in der Liste
		boolean ValueIsAllowed = true;
		for (int i = 0; i < CoordinateList.size(); i++){
			if (Value == cell.get(CoordinateList.get(i)).getCellValue()){
				ValueIsAllowed = false;
				System.out.println("testThisValueInCoordinateList - Der Value " + Value + " ist in der Zelle " + CoordinateList.get(i) + " schon gesetzt");
			}
		}
		return ValueIsAllowed;
	}
	private void setCellValue(String Coordinate, int Value, int IsSolvedBy){
		//setValue ( int Value, boolean IsSolved, int IsSolvedBy)
		cell.get(Coordinate).setCellValue(Value, true, IsSolvedBy);
		LogSudokuStep.add(new SudokoSolvingStep(Coordinate, Value, IsSolvedBy) );
		System.out.println("setCellValue - Setze Value " + Value + " in Zelle [" + Coordinate + "]");
		printLog();
	}
	private void printLog() {
		System.out.println(" LogSudokuStep -> " );
		for (int i = 0; i < LogSudokuStep.size(); i++) {
			System.out.println(" Step " + i + " " + LogSudokuStep.get(i).getAll()); 
		}
	}
	
}