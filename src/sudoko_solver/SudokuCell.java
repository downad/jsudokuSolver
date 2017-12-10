package sudoko_solver;


import java.util.ArrayList;
import java.util.Arrays;

/*
 * Das ist die Kleinste Einheit eines Sudoku, die Zelle
 *
 */
public class SudokuCell {
	
	private String cellName = "";
	private int value = 0;
	private boolean isSolved = false;
	private int isSolvedBy = 0;
	private boolean[] Candidates = {true,true,true,true,true,true,true,true,true,true};
	private boolean[] notPossibleValues = {true,false,false,false,false,false,false,false,false,false};
	private int row = 0;
	private int col = 0;
	private int gridNumber = 0;
	private String coordiateWithSlash = "";
	private String[] myROW = new String[(jsudokuSolver.MAXNUMBER+2)]; // Array starten bei 0 meine Zahlen bei 1, 
	private String[] myCOL = new String[(jsudokuSolver.MAXNUMBER+2)]; // und myRow, usw Schleife fragt kleine als
	private String[] myBLOCK = new String[(jsudokuSolver.MAXNUMBER+2)];
	private String[] myUnits = new String[3 * (jsudokuSolver.MAXNUMBER)];
	private boolean myUnitsAreCreated = false;
	
	
	public SudokuCell(int initRow, int initCol, int initValue, boolean initIsSolved, int initIsSolvedBy) {
		// Checke values
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
			gridNumber =  ((row-1) * jsudokuSolver.MAXROW + col);
			coordiateWithSlash = row + "/" +col;
			//System.out.println("row = " + initRow + " col = " + initCol + " name = " + cellName);
			value = initValue;
			isSolved = initIsSolved;
			isSolvedBy = initIsSolvedBy;
			
		}
	}

	public void setCellValue ( int Value, boolean IsSolved, int IsSolvedBy) {
		value = Value;
		isSolved = IsSolved;
		isSolvedBy = IsSolvedBy;
	}
	public boolean getIsSolved() {
		return isSolved;
	}
	public int getIsSolvedBy() {
		return isSolvedBy;
	}
	public int getCellValue(){
		//System.out.println("row = " + row + " col = " + col + " Value = " + String.valueOf(value));
		int returnint = value;
		return returnint;
	}
	public String getName(){
		return cellName;
	}
	public void setCandidate(int Value) {
		Candidates[Value] = true;
	}
	public void clearCandidate(int Value) {
		Candidates[Value] = false;
		//System.out.println("SudokuCell: clearPossibleCellNumber: " + "lösche die Nummer " + Value);
	}
	public ArrayList<Integer> getCandidates() {
		ArrayList<Integer> returnint = new ArrayList<Integer>();
		for (int i = 1; i<= jsudokuSolver.MAXNUMBER; i++){
			if (Candidates[i] == true) {
				returnint.add(i);
			}
		}
		return returnint;
	}
	public String getCandidatesAsStringTable(){
		String returnstring = "<table style=\"font-size:9px\" border=0><tr>";
		String tdValue = "";
		if (isSolved == true) {
			return "<table style=\"font-size:1.2em\" border=0><tr><td>" + value + "</td></tr></table>";}
		
		for (int i = 1; i<= jsudokuSolver.MAXNUMBER; i++){
			if (Candidates[i] == true ) {
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
	public String[] getMyUnits(){
		if (myUnitsAreCreated == false){
			myUnitsAreCreated = true;
			int counter = 0;
			for (int i = 1; i< myROW.length; i++) {
				if (myROW[i] !=null) {
					myUnits[counter] = myROW[i];
					counter++;
				}
			}
			for (int i = 1; i< myCOL.length; i++) {
				if (myCOL[i] !=null) {
					myUnits[counter] = myCOL[i];
					counter++;
				}
			}
			for (int i = 1; i< myBLOCK.length; i++) {
				if (myBLOCK[i] !=null) {
					myUnits[counter] = myBLOCK[i];
					counter++;
				}
			}			
		}
		return myUnits;
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
		clearCandidate(Number);
	}
	public void reset(){
		value = 0;
		isSolved = false;
		isSolvedBy = 0;
		resetCandidates();		
	}
	public void resetCandidates(){
		if (isSolved == false) {
			Arrays.fill(Candidates, true);
			Arrays.fill(notPossibleValues, false);
			notPossibleValues[0] = true;
		}
	}
	public String getCoordinateWithSlash(){
		return coordiateWithSlash; 
	}
	public int getGridnumber(){
		return gridNumber; 
	}
}
