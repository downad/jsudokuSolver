package sudoko_solver;

import java.util.HashMap;
import java.util.Map;

public class Strategie {

//	public Map<String, SudokuCell> cell = new HashMap<String, SudokuCell>();
	
/*	
	public void FindValueAndClearAllowedNumber(){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
	/*
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (cell.get(""+row+col).getIsSolved() == true) {
					ClearAllowedNumber(row, col);
				}
			}
		}
	}
	public void ClearAllowedNumber(int row, int col){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		//int Value = cell.get(""+row+col).getCellValue();
		//hole MyRow
	/*
		String[] MyRow = cell.get(""+row+col).getMyROW();
		for (int i = 1; i< (MyRow.length) - 1; i++) {
			TestNumberAndClear(MyRow[i], Value);
		}
		//hole MyCol
		String[] MyCol = cell.get(""+row+col).getMyCOL();
		for (int i = 1; i< (MyCol.length) - 1; i++) {
			TestNumberAndClear(MyCol[i], Value);
		}
		//hole MyBlock
		String[] MyBlock = cell.get(""+row+col).getMyBLOCK();
		for (int i = 1; i< (MyBlock.length) - 1; i++) {
			TestNumberAndClear(MyBlock[i], Value);
		}
	}

	public boolean TestNumberAndClear(String CellKoordiante, int Number ){
		if (cell.get(CellKoordiante).getIsSolved() == false) {
			cell.get(CellKoordiante).setNotPossibleValue(Number);
			System.out.println(" Die Zelle [" + CellKoordiante + "] ist nicht gelöste, lösche die Nummer " + Number);
		}
		return true;
	}
	public int findNakedSingle(){
		return 0;
	}
	*/
}
