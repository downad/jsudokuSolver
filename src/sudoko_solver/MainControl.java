package sudoko_solver;

public class MainControl {
	
	public static String SudokuHilfeValue(){
		String SudokuHilfe = "<table Border=1 >";
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px\">" +
				jsudokuSolver.cell.get(""+row+col).getPosibleCellNumberAsStringTable() + "</td>";		
			}
			SudokuHilfe = SudokuHilfe + "</tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";

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
		String SudokuHilfe = "<table Border=1  >";
		for (int row = 1; row<= jsudokuSolver.MAXROW; row++) {
			SudokuHilfe = SudokuHilfe + "<tr>";
			for (int col = 1; col<= jsudokuSolver.MAXCOL; col++) {
				int CellValueAsInt = jsudokuSolver.cell.get(""+row+col).getCellValue();
				String CellValueAsString = "";
				if ( CellValueAsInt != 0) { 
					CellValueAsString = jsudokuSolver.cell.get(""+row+col).getPosibleCellNumberAsStringTable();
				}
				SudokuHilfe = SudokuHilfe + "<td  align=\"center\" valign=\"middle\" style=\"height:61px; width:46px \">" +
				CellValueAsString + "</td>";							
			}
			SudokuHilfe = SudokuHilfe + "</tr>";
		}
		SudokuHilfe = SudokuHilfe + "</table>";

		return SudokuHilfe;
	}
}
