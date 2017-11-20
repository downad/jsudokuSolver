package sudoko_solver;

public class MainControl {
	
	public static String SudokuHilfeValue(){
		String SudokuHilfe = "<table Border=1 width=100% >";
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\">" +
				String.valueOf(jsudokuSolver.cell.get(""+row+col).getValue()) + "</td>";
				
				System.out.println("row = " + row + " col = " + col + 
						" CellName = " + jsudokuSolver.cell.get(""+row+col).getName() + 
						" Value = " + String.valueOf(jsudokuSolver.cell.get(""+row+col).getValue()));
						
			}
			SudokuHilfe = SudokuHilfe + "</tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";
/*
		String SudokuHilfe = "<table Border=1>" +
	            "<tr><td>11</td><td>12</td><td>13</td></tr>" +
	            "<tr><td>21</td><td>22</td><td>23</td></tr>" +
	            "<tr><td>31</td><td>32</td><td>33</td></tr>" +
	            "</table>";
	            */
		//MainGUI.setSudokuHilfe(SudokuHilfe);
		return SudokuHilfe;
	}
	
	public static String SudokuHilfeNotAllowedNumbers(){
		String SudokuHilfe = "<table Border=1>" +
	            "<tr><td> Feld 11</td><td> Feld 12</td><td> Feld 13</td></tr>" +
	            "<tr><td> Feld 21</td><td> Feld 22</td><td> Feld 23</td></tr>" +
	            "<tr><td> Feld 31</td><td> Feld 32</td><td> Feld 33</td></tr>" +
	            "</table>";
		//MainGUI.setSudokuHilfe(SudokuHilfe);
		return SudokuHilfe;
	}
	
	public static String SudokuHilfeClear(){
		String SudokuHilfe = "<table Border=1>" +
	            "<tr><td>C</td><td>C</td><td>C</td></tr>" +
	            "<tr><td>C</td><td>C</td><td>C</td></tr>" +
	            "<tr><td>C</td><td>C</td><td>C</td></tr>" +
	            "</table>";
		//MainGUI.setSudokuHilfe(SudokuHilfe);
		return SudokuHilfe;
	}
}
