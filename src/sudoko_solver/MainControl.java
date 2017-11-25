package sudoko_solver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainControl {
	public static Map<String, SudokuCell> cell = new HashMap<String, SudokuCell>();
	public static String[][] ROW = new String[(jsudokuSolver.MAXROW+1)][(jsudokuSolver.MAXCOL+1)];
	public static String[][] COL = new String[(jsudokuSolver.MAXROW+1)][(jsudokuSolver.MAXCOL+1)];
	//public static String[][] BLOCK = new String[(MAXROW+1)][(MAXCOL+1)];
	
	public static String[][] BLOCK = { {},
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
	
	public static void StartSudoku() {
		initSudoku();
		createSudoku();
	}

	/* ShowInrhsPane 
		1 = btnAllValue = new JButton("Alle Möglichkeiten");
		2 = btnClear = new JButton("Lösche die Hilfen"); 
		3 = btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");
	*/
	public static String SudokuHilfen( int ShowInrhsPane){
		String returnString = "";
		if (ShowInrhsPane == 1) { returnString = SudokuHilfeClear();}
		if (ShowInrhsPane == 2) { returnString = SudokuHilfeValue();}
		if (ShowInrhsPane == 3) { returnString = SudokuHilfeNotAllowedNumbers();}
		return returnString;
	}
	public static String SudokuHilfeValue(){
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

		return SudokuHilfe;
	}
	
	public static String SudokuHilfeNotAllowedNumbers(){
		String SudokuHilfe = "<table Border=1>" +
	            "<tr><td> Feld 11</td><td> Feld 12</td><td> Feld 13</td></tr>" +
	            "<tr><td> Feld 21</td><td> Feld 22</td><td> Feld 23</td></tr>" +
	            "<tr><td> Feld 31</td><td> Feld 32</td><td> Feld 33</td></tr>" +
	            "</table>";
		//MainGUI.setSudokuHilfe(SudokuHilfe);
		return SudokuHilfe;
	}
	
	public static String SudokuHilfeClear(){
		
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

		return SudokuHilfe;
	}
	private static void initSudoku() {
		// Init Sudoku
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				COL[col][row] = "" + row + col;
				ROW[row][col] = "" + row + col;
				// SudokuCell(int initRow, int initCol, int initValue, boolean initIsSolved, int initIsSolvedBy)
				cell.put(""+row+col, new SudokuCell(row, col, 0, false, 0));
			}
		}
		// Initalisiere Hilfs Felder
		for (int i = 1; i<= 9; i++) {
			//System.out.print("Row [" + i + "]" );
			for (int ii = 1; ii<= 9; ii++) {
			//	System.out.print( ", " + ROW[i][ii]);
				cell.get(ROW[i][ii]).setMyROW(Arrays.copyOf(ROW[i], ROW[i].length));
			}
			//System.out.println("---");
		}
		
		for (int i = 1; i<= 9; i++) {
			//System.out.print("COL [" + i + "]" );
			for (int ii = 1; ii<= 9; ii++) {
			//	System.out.print(", " + COL[i][ii]);
				cell.get(COL[i][ii]).setMyCOL(Arrays.copyOf(COL[i], COL[i].length));
			}
			//System.out.println("---");
		}
		
		for (int i = 1; i< BLOCK.length; i++) {
			//System.out.print("BLOCK [" + i + "]" );
			for (int ii = 1; ii< BLOCK[i].length; ii++) {
			//	System.out.print(", " + BLOCK[i][ii]);
				cell.get(BLOCK[i][ii]).setMyBLOCK(Arrays.copyOf(BLOCK[i], BLOCK[i].length));
			}
			//System.out.println("---");
		}
	}
	public static int getCellValue(int row, int col){
		int CellValue = cell.get(""+row+col).getCellValue();
		return CellValue;
	}
	
	public static String getCellValueAsStringByNumber(int Number){
		int col = ((Number - 1)/ jsudokuSolver.MAXCOL) + 1;
    	int row = Number - (col  - 1)* jsudokuSolver.MAXCOL;
    	System.out.println("getCellValueByNumber: " +  " Number = " + Number + " row = " + row + " col = " + col);
		int CellValue = cell.get(""+row+col).getCellValue();
		String StringCellValue = "";
		if (CellValue == 0) {
			StringCellValue = "";
		} else {
			StringCellValue = "" + CellValue;
		}
		return StringCellValue;
	}
	private static void createSudoku(){
		// setValue ( int Value, boolean IsSolved, int IsSolvedBy)
		//	IsSolvedBy 1 = Vorgabe / default
		cell.get(""+1+1).setCellValue(3, true, 1);
		//System.out.println(" str -> " + cell.get(""+1+1).getName() + " value = " + cell.get(""+1+1).getValue());
		cell.get(""+1+5).setCellValue(5, true, 1);
		cell.get(""+1+7).setCellValue(9, true, 1);
		cell.get(""+2+1).setCellValue(2, true, 1);
		cell.get(""+2+6).setCellValue(8, true, 1);
		cell.get(""+2+7).setCellValue(4, true, 1);
		cell.get(""+2+9).setCellValue(5, true, 1);
		cell.get(""+3+4).setCellValue(2, true, 1);
		cell.get(""+3+7).setCellValue(3, true, 1);
		cell.get(""+4+3).setCellValue(1, true, 1);
		cell.get(""+4+4).setCellValue(4, true, 1);
		cell.get(""+4+6).setCellValue(5, true, 1);
		cell.get(""+4+7).setCellValue(7, true, 1);
		cell.get(""+4+9).setCellValue(8, true, 1);
		cell.get(""+6+1).setCellValue(7, true, 1);
		cell.get(""+6+3).setCellValue(5, true, 1);
		cell.get(""+6+5).setCellValue(9, true, 1);
		cell.get(""+6+6).setCellValue(6, true, 1);
		cell.get(""+6+7).setCellValue(2, true, 1);
		cell.get(""+7+3).setCellValue(8, true, 1);
		cell.get(""+7+6).setCellValue(1, true, 1);
		cell.get(""+8+1).setCellValue(9, true, 1);
		cell.get(""+8+3).setCellValue(2, true, 1);
		cell.get(""+8+4).setCellValue(2, true, 1);
		cell.get(""+8+9).setCellValue(6, true, 1);
		cell.get(""+9+3).setCellValue(4, true, 1);
		cell.get(""+9+5).setCellValue(2, true, 1);
		cell.get(""+9+9).setCellValue(3, true, 1);
	}
}
