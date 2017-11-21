package sudoko_solver;

import java.util.ArrayList;

public class SudokuCell {
	
	private String cellName = "";
	private int value = 0;
	private boolean isSolved = false;
	private int isSolvedBy = 0;
	private boolean[] possibleCellNumbers = {true,false,false,false,false,false,false,false,false,false};
	private int row = 0;
	private int col = 0;
	
	
	public SudokuCell(int initRow, int initCol, int initValue, boolean initIsSolved, int initIsSolvedBy) {
		// Checke values
		//System.out.println("row = " + initRow + " col = " + initCol + " Value = " + String.valueOf(initValue) + " name = " + cellName);
		boolean ValueCheckIsOK = true;
		if (initRow < 1 && initRow > jsudokuSolver.MAXROW) {
			ValueCheckIsOK = false;
		}
		if (initCol < 1 && initCol > jsudokuSolver.MAXCOL) {
			ValueCheckIsOK = false;
		}
		if (initValue < 1 && initValue > jsudokuSolver.MAXNUMBER) {
			ValueCheckIsOK = false;
		}
		if (ValueCheckIsOK) {
			cellName = "" + initRow + initCol;
			row = initRow;
			col = initCol;		
			System.out.println("row = " + initRow + " col = " + initCol + " name = " + cellName);
			
			//setCoordinate( initRow, initCol);
			//setValue ( initValue, initIsSolved, initIsSolvedBy);
			value = initValue;
			isSolved = initIsSolved;
			isSolvedBy = initIsSolvedBy;
			
		}
	}
	
	private void setCoordinate(int initRow, int initCol){
		StringBuilder str = new StringBuilder(String.valueOf (initRow));
		str.append(String.valueOf (initCol));
		cellName = str.toString();
		row = initRow;
		col = initCol;		
		System.out.println("row = " + initRow + " col = " + initCol + " name = " + cellName);
	}
	public void setCellValue ( int Value, boolean IsSolved, int IsSolvedBy) {
		value = Value;
		isSolved = IsSolved;
		isSolvedBy = IsSolvedBy;
	}
	public int getCellValue(){
		//System.out.println("row = " + row + " col = " + col + " Value = " + String.valueOf(value));
		int returnint = value;
		return returnint;
	}
	public String getName(){
		return cellName;
	}
	public void setPossibleCellNumber(int Value) {
		possibleCellNumbers[Value] = true;
	}
	public void clearPossibleCellNumber(int Value) {
		possibleCellNumbers[Value] = false;
	}
	public ArrayList<Integer> getPossibleCellNumber() {
		ArrayList<Integer> returnint = new ArrayList<Integer>();
		for (int i = 1; i<= jsudokuSolver.MAXNUMBER; i++){
			if (possibleCellNumbers[i] = true) {
				returnint.add(i);
			}
		}
		return returnint;
	}
	public String getPosibleCellNumberAsStringTable(){
		String returnstring = "<table style=\"font-size:9px\" border=0><tr>";
		String tdValue = "";
		if (isSolved == true) {
			return "<table style=\"font-size:1.2em\" border=0><tr><td>" + value + "</td></tr></table>";}
		
		for (int i = 1; i<= jsudokuSolver.MAXNUMBER; i++){
			if (possibleCellNumbers[i] = true ) {
				tdValue = String.valueOf(i);
			} else {
				tdValue = " ";
			}
			returnstring = returnstring + "<td>" + tdValue + "</td>";
			if (i % 3 == 0) {
				returnstring = returnstring + "</tr>"; 
				if (i < 9) {
					returnstring = returnstring + "<tr>";
				}
			}
	
		}
		returnstring = returnstring + "</table>";
		return returnstring;
	}
}
