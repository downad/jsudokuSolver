package sudoko_solver;


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
	
	Optimierungen jeweisl 10 Messungen
	05.12.2017 - Optimierungen 			10.12.	Liste, Refresh, View
		Zeit [ms]:							Zeit [ms]:
			Min			5388					Min			157
			Max			5609					Max			223
			Mittelwert	5440,45					Mittelwert	174,64
			Median		5427					Median		170



	
 */
	// Info zum Sudoku
	public final static int BLOCKROW = 3;
	public final static int BLOCKCOL = 3;
	public final static int MAXNUMBER = 9;
	public final static int MAXROW = 9;
	public final static int MAXCOL = 9;

	static SudokuController controller;

    /**
     * Diese Klasse wird nur dazu benutzt alle nötigen
     * Komponenten zu Initialisieren und die erste
     * View anzuzeigen
     * angelegt an
     * http://blog.bigbasti.com/tutorial-model-view-controller-mvc-struktur-in-java-projekten-nutzen/
     */
    public static void main(String [] args) throws Exception {
        controller = new SudokuController();

        controller.showView();
    }

	

		
}
