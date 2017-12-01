package sudoko_solver;

public class SudokoSolvingStep {
	private String Coordinate = "";
	private int Value = 0;
	private int IsSolvedBy = 0;
	
	public SudokoSolvingStep(String coor, int val, int isSolved){
		 Coordinate = coor;
		 Value = val;
		 IsSolvedBy = isSolved;
	}
	public String getAll() {
		return ("SudokoSolvingStep - Die Zelle: " + Coordinate + " wurde mit dem Wert " + Value + " gelöst durch " + IsSolvedBy);
	}
}
