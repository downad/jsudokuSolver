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
