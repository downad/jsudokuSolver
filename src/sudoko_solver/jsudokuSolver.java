package sudoko_solver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class jsudokuSolver {
/*
 	************************************
	***           BEGRIFFE           ***
	************************************
	Begriff		Variabel	Beschreibung
	grid 					Das ganze Sudoku wird als Gitter (engl.: grid) bezeichnet. 
	cell		[cell]		Ein Sudoku besteht aus Zellen
	values					Zellen werden gefüllt mit Werten (engl.: values).
	givens					Die Werte, die zu Beginn bereits vorhanden sind, sind die Angaben (engl.: givens)
	candidates				Mögliche Werte für noch nicht gefüllte Zellen sind Kandidaten (engl.: candidates).
	unit					Zeilen, Spalten oder Blöcke werden unit genannt.
	row						Zeilen (engl.: rows, mit "r" abgekürzt), 	
				[ROW]	Array über die Key der Reihe
	col						Spalten (engl.: columns, mit "c" abgekürzt) und 
				[COL]	Array über die Key der Spalte
	block					Blöcke (engl.: boxes oder blocks, mit "b" abgekürzt") angeordnet.
				[BLOCK]	Array über die Key des Blocks
	band					Drei Blöcke in einer Reihe sind ein Band (engl.: band). 
	floor					Ein horizontales Band ist ein Stock (engl.: floor).
	tower					Ein vertikales ist ein Turm (engl.: tower). 
 */
	public final static int BLOCKROW = 3;
	public final static int BLOCKCOL = 3;
	public final static int MAXNUMBER = 9;
	public final static int MAXROW = 9;
	public final static int MAXCOL = 9;

	
	public static void main(String[] args){		


		// Sudoku inititalisieren 
		MainControl Sudoku = new MainControl();
		Sudoku.StartSudoku();
		// Sudoku laden
		
		//GUI Starten		
		MainGUI gui = new MainGUI();
		//SimpleFTF.main();
	}
	

		
}
