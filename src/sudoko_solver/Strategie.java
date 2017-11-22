package sudoko_solver;

public class Strategie {

	
	public static void ClearAllowedNumber(){
		/* durchlaufe alle Zellen
		 * ist eine Zahl gesetzt, so lösche dies in 
		 * COL, ROW, BLOCK
		 */
		int col = 1;
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				if (jsudokuSolver.cell.get(""+row+col).getIsSolved() == true) {
					System.out.println( "gelöste Zelle: " + jsudokuSolver.cell.get(""+row+col).getName());
					//hole MyRow
					String[] myRow = jsudokuSolver.cell.get(""+row+col).getMyROW();
					System.out.println("myRow ");
					for (int i = 1; i< (myRow.length) - 1; i++) {
						System.out.print(" ," + myRow[i]);
					}
					System.out.println("!");
					//hole MyCol
					String[] MyCol = jsudokuSolver.cell.get(""+row+col).getMyCOL();
					System.out.println("MyCol ");
					for (int i = 1; i< (MyCol.length) - 1; i++) {
						System.out.print(" ," + MyCol[i]);
					}
					System.out.println("!");
					//hole MyBlock
					String[] MyBlock = jsudokuSolver.cell.get(""+row+col).getMyBLOCK();
					System.out.println("MyBlock ");
					for (int i = 1; i< (MyBlock.length) - 1; i++) {
						System.out.print(" ," + MyBlock[i]);
					}
					System.out.println("!");
				}
			}
		}
	}
}
