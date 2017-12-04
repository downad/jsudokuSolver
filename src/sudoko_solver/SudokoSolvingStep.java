package sudoko_solver;

public class SudokoSolvingStep {
	private String Coordinate = "";
	private int Value = 0;
	private int IsSolvedBy = 0;
	private String[] solvingTripple = new String[3];
	
	public SudokoSolvingStep(String coor, int val, int isSolved){
		 Coordinate = coor;
		 Value = val;
		 IsSolvedBy = isSolved;
		 solvingTripple[0] = coor;
		 solvingTripple[1] = ""+val;
		 solvingTripple[2] = ""+isSolved;
	}
	public String getAll() {
		return ("SudokoSolvingStep - Die Zelle: " + Coordinate + " wurde mit dem Wert " + Value + " gelöst durch " + IsSolvedBy);
	}
	public String[] getSolvingStepTripple() {
		return solvingTripple;
	}
	
}
