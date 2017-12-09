package sudoko_solver;

import java.util.ArrayList;

public class LogEntryError {
	private String coordinate = "";
	private int value = 0;
	private int solvedBy = 0;
	private String errorText = "";
	private ArrayList<String> errorList = new ArrayList<String>();
	
	public LogEntryError(String coor, int val, int isSolved, String errortext,   ArrayList<String> errorlist){
		 coordinate = coor;
		 value = val;
		 solvedBy = isSolved;
		 errorText = errortext;
		 errorList.addAll(errorlist);
	}
	public String getAll() {
		return ("LogEntryError - Fehler in Zelle: " + coordinate + " mit Value " + value + " durch " + solvedBy);
	}

	
}
