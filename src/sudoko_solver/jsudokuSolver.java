package sudoko_solver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class jsudokuSolver {
/*
 	************************************
	***           BEGRIFFE           ***
	************************************
	Begriff		Variabel	Beschreibung
	grid 					Das ganze Sudoku wird als Gitter (engl.: grid) bezeichnet. 
	cell		[cell]		Ein Sudoku besteht aus Zellen
	values					Zellen werden gefüllt mit Werten (engl.: values).
	givens					Die Werte, die zu Beginn bereits vorhanden sind, sind die Angaben (engl.: givens)
	candidates				Mögliche Werte für noch nicht gefüllte Zellen sind Kandidaten (engl.: candidates).
	unit					Zeilen, Spalten oder Blöcke werden unit genannt.
	row						Zeilen (engl.: rows, mit "r" abgekürzt), 	
				[ROW]	Array über die Key der Reihe
	col						Spalten (engl.: columns, mit "c" abgekürzt) und 
				[COL]	Array über die Key der Spalte
	block					Blöcke (engl.: boxes oder blocks, mit "b" abgekürzt") angeordnet.
				[BLOCK]	Array über die Key des Blocks
	band					Drei Blöcke in einer Reihe sind ein Band (engl.: band). 
	floor					Ein horizontales Band ist ein Stock (engl.: floor).
	tower					Ein vertikales ist ein Turm (engl.: tower). 
 */
	public final static int BLOCKROW = 3;
	public final static int BLOCKCOL = 3;
	public final static int MAXNUMBER = 9;
	public final static int MAXROW = 9;
	public final static int MAXCOL = 9;
	
	public static Map<String, SudokuCell> cell = new HashMap<String, SudokuCell>();
	public static String[][] ROW = new String[11][11];
	public static String[][] COL = new String[11][11];
	//public static String[] BLOCK = new String[11];
	public static String[][] BLOCK = { {},
			{"", "11", "12", "13", "21", "22", "23", "31", "32", "33"}, 
			{"","14", "15", "16", "24", "25", "26", "34", "35", "36"}, // 2
			{"","17", "18", "19", "27", "28", "29", "37", "38", "39"}, // 3
			{"","41", "42", "43", "51", "52", "53", "61", "62", "63"}, // 4
			{"","44", "45", "46", "54", "55", "56", "64", "65", "66"}, // 5
			{"","47", "48", "49", "57", "58", "59", "67", "68", "69"}, // 6
			{"","71", "72", "73", "81", "82", "83", "91", "92", "93"}, // 7
			{"","74", "75", "76", "84", "85", "86", "94", "95", "96"}, // 8
			{"","77", "78", "79", "87", "88", "89", "97", "98", "99"}, // 9
	};

	
	
	public static void main(String[] args){		

		// Init Sudoku
		int col = 1;
		for (int row = 1; row<= MAXROW; row++) {
			for (col = 1; col<= MAXCOL; col++) {
				int blockCol = (col-1) / BLOCKCOL;
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
				jsudokuSolver.cell.get(ROW[i][ii]).setMyROW(Arrays.copyOf(ROW[i], ROW[i].length));
			}
			//System.out.println("---");
		}
		
		for (int i = 1; i<= 9; i++) {
			//System.out.print("COL [" + i + "]" );
			for (int ii = 1; ii<= 9; ii++) {
			//	System.out.print(", " + COL[i][ii]);
				jsudokuSolver.cell.get(COL[i][ii]).setMyCOL(Arrays.copyOf(COL[i], COL[i].length));
			}
			//System.out.println("---");
		}
		
		for (int i = 1; i< BLOCK.length; i++) {
			//System.out.print("BLOCK [" + i + "]" );
			for (int ii = 1; ii< BLOCK[i].length; ii++) {
			//	System.out.print(", " + BLOCK[i][ii]);
				jsudokuSolver.cell.get(BLOCK[i][ii]).setMyBLOCK(Arrays.copyOf(BLOCK[i], BLOCK[i].length));
			}
			//System.out.println("---");
		}

		// erstes Sudoku erzeugen	
		createSudoku();

		//SudokuCell a = new SudokuCell(1,2,3,true,1);
		//SudokuCell b = new SudokuCell(4,5,6,true,1);
		//System.out.println(" a -> " + a.getName());
		//System.out.println(" b -> " + b.getName());
		MainGUI gui = new MainGUI();
	    
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
