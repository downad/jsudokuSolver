package sudoko_solver;

public class Strategie {

	
	public static void FindValueAndClearAllowedNumber(){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (jsudokuSolver.cell.get(""+row+col).getIsSolved() == true) {
					ClearAllowedNumber(row, col);
				}
			}
		}
	}
	public static void ClearAllowedNumber(int row, int col){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		int Value = jsudokuSolver.cell.get(""+row+col).getCellValue();
		//System.out.println( "gelöste Zelle: " + jsudokuSolver.cell.get(""+row+col).getName());
		//hole MyRow
		String[] MyRow = jsudokuSolver.cell.get(""+row+col).getMyROW();
		//System.out.println("MyRow ");
		for (int i = 1; i< (MyRow.length) - 1; i++) {
			//System.out.print(" ," + MyRow[i]);
			TestNumberAndClear(MyRow[i], Value);
		}
//		System.out.println("!");
		//hole MyCol
		String[] MyCol = jsudokuSolver.cell.get(""+row+col).getMyCOL();
//		System.out.println("MyCol ");
		for (int i = 1; i< (MyCol.length) - 1; i++) {
	//		System.out.print(" ," + MyCol[i]);
			TestNumberAndClear(MyCol[i], Value);
		}
		//System.out.println("!");
		//hole MyBlock
		String[] MyBlock = jsudokuSolver.cell.get(""+row+col).getMyBLOCK();
		//System.out.println("MyBlock ");
		for (int i = 1; i< (MyBlock.length) - 1; i++) {
			//System.out.print(" ," + MyBlock[i]);
			TestNumberAndClear(MyBlock[i], Value);
		}
//		System.out.println("!");
	}
	public static boolean TestNumberAndClear(String CellKoordiante, int Number ){
		if (jsudokuSolver.cell.get(CellKoordiante).getIsSolved() == false) {
			jsudokuSolver.cell.get(CellKoordiante).setNotPossibleValue(Number);
			System.out.println(" Die Zelle [" + CellKoordiante + "] ist nicht gelöste, lösche die Nummer " + Number);
		}
		return true;
	}
}
