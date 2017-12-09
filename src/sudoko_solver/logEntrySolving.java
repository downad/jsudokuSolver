package sudoko_solver;

public class logEntrySolving {
	private String coordinate = "";
	private int value = 0;
	private int isSolvedBy = 0;
	private String[] solvingTripple = new String[3];
	private int gridNumber = 0;
	
	public logEntrySolving(String coor, int val, int isSolved){
		 coordinate = coor;
		 value = val;
		 isSolvedBy = isSolved;
		 solvingTripple[0] = coor;
		 solvingTripple[1] = ""+val;
		 solvingTripple[2] = ""+isSolved;
		 for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
					if ((""+row+col).equals(coor)) {
						gridNumber =   ((row-1) * jsudokuSolver.MAXROW + col);
					}
			}
		 }		 
	}
	public String getAll() {
		return ("SudokoSolvingStep - Die Zelle: " + coordinate + " wurde mit dem Wert " + value + " gelöst durch " + isSolvedBy);
	}
	public String[] getSolvingStepTripple() {
		return solvingTripple;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public int getValue(){
		return value;
	}
	public int getGridNumber(){
		return gridNumber;
	}
}
