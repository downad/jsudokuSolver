package sudoko_solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class jsudokuSolver {
	public final static int MAXROW = 9;
	public final static int MAXCOL = 9;
	public final static int MAXNUMBER = 9;
	
	//public static SudokuCell[][] cell; //= new SudokuCell[MAXROW+1][MAXCOL+1];
	//public static ArrayList<SudokuCell> cell = new ArrayList<SudokuCell>();
	public static Map<String, SudokuCell> cell = new HashMap<String, SudokuCell>();
	
	public static void main(String[] args){		

		// Init Sudoku
		for (int row = 1; row<= MAXROW; row++) {
			for (int col = 1; col<= MAXCOL; col++) {
				// SudokuCell(int initRow, int initCol, int initValue, boolean initIsSolved, int initIsSolvedBy)
				//cell[row][col] = new SudokuCell(row, col, 0, false, 0);
				//StringBuilder str = new StringBuilder(String.valueOf (row));
				//str.append(String.valueOf (col));
				//cellName = str.toString();
				//cell.put(str.toString(), new SudokuCell(row, col, 0, false, 0));
				cell.put(""+row+col, new SudokuCell(row, col, 0, false, 0));
				//System.out.println("row = " + row + " col = " + col + " Value = " + String.valueOf(cell[row][col].getValue()));
				//System.out.println(cell);
				//System.out.println(" str -> " + cell.get(str).getName());
			}
		}
		createSudoku();
		
		MainGUI gui = new MainGUI();
	    
	}
	
	private static void createSudoku(){
		// setValue ( int Value, boolean IsSolved, int IsSolvedBy)
		//	IsSolvedBy 1 = Vorgabe / default
		cell.get(""+1+1).setValue(3, true, 1);
		System.out.println(" str -> " + cell.get(""+1+1).getName() + " value = " + cell.get(""+1+1).getValue());
		cell.get(""+1+5).setValue(5, true, 1);
		cell.get(""+1+7).setValue(9, true, 1);
		cell.get(""+2+1).setValue(2, true, 1);
		cell.get(""+2+6).setValue(8, true, 1);
		cell.get(""+2+7).setValue(4, true, 1);
		cell.get(""+2+9).setValue(5, true, 1);
		cell.get(""+3+4).setValue(2, true, 1);
		cell.get(""+3+7).setValue(3, true, 1);
		cell.get(""+4+3).setValue(1, true, 1);
		cell.get(""+4+4).setValue(4, true, 1);
		cell.get(""+4+6).setValue(5, true, 1);
		cell.get(""+4+7).setValue(7, true, 1);
		cell.get(""+4+9).setValue(8, true, 1);
		cell.get(""+6+1).setValue(7, true, 1);
		cell.get(""+6+3).setValue(5, true, 1);
		cell.get(""+6+5).setValue(9, true, 1);
		cell.get(""+6+6).setValue(6, true, 1);
		cell.get(""+6+7).setValue(2, true, 1);
		cell.get(""+7+3).setValue(8, true, 1);
		cell.get(""+7+6).setValue(1, true, 1);
		cell.get(""+8+1).setValue(9, true, 1);
		cell.get(""+8+3).setValue(2, true, 1);
		cell.get(""+8+4).setValue(2, true, 1);
		cell.get(""+8+9).setValue(6, true, 1);
		cell.get(""+9+3).setValue(4, true, 1);
		cell.get(""+9+5).setValue(2, true, 1);
		cell.get(""+9+9).setValue(3, true, 1);
	}
		
}
