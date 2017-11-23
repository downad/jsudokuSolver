package sudoko_solver;

import java.util.ArrayList;
import java.util.Arrays;

public class SudokuCell {
	
	private String cellName = "";
	private int value = 0;
	private boolean isSolved = false;
	private int isSolvedBy = 0;
	private boolean[] possibleValues = {true,true,true,true,true,true,true,true,true,true};
	private boolean[] notPossibleValues = {true,false,false,false,false,false,false,false,false,false};
	private int row = 0;
	private int col = 0;
	private String[] myROW = new String[11];
	private String[] myCOL = new String[11];
	private String[] myBLOCK = new String[11];
	
	
	
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
			//System.out.println("row = " + initRow + " col = " + initCol + " name = " + cellName);
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
	public boolean getIsSolved() {
		return isSolved;
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
		possibleValues[Value] = true;
	}
	public void clearPossibleCellNumber(int Value) {
		possibleValues[Value] = false;
		System.out.println("clearPossibleCellNumber: " + "lösche die Nummer " + Value);
	}
	public ArrayList<Integer> getPossibleCellNumber() {
		ArrayList<Integer> returnint = new ArrayList<Integer>();
		for (int i = 1; i<= jsudokuSolver.MAXNUMBER; i++){
			if (possibleValues[i] == true) {
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
			if (possibleValues[i] == true ) {
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
	public void setMyROW( String[] ROW){
		for (int i = 1; i< ROW.length; i++) {
			myROW[i] = ROW[i];
		}
	}
	public void setMyCOL( String[] COL){
		for (int i = 1; i< COL.length; i++) {
			myCOL[i] = COL[i];
		}
	}
	public void setMyBLOCK( String[] BLOCK){
		for (int i = 1; i< BLOCK.length; i++) {
			myBLOCK[i] = BLOCK[i];
		}
	}
	public String[] getMyROW() { 
		return Arrays.copyOf(myROW, myROW.length);
	}
	public String[] getMyCOL() { 
		return Arrays.copyOf(myCOL, myCOL.length);
	}
	public String[] getMyBLOCK() { 
		return Arrays.copyOf(myBLOCK, myBLOCK.length);
	}
	public void setNotPossibleValue(int Number){
		notPossibleValues[Number] = true;
		clearPossibleCellNumber(Number);
	}
}
