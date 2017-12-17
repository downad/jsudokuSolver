package sudoko_solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;



/**
 * Der Controller muss beide die View und das Model kennen
 * da dieser für die Kommunikation zwischen den Beiden sorgt
 * angelegt an
 * http://blog.bigbasti.com/tutorial-model-view-controller-mvc-struktur-in-java-projekten-nutzen/
 */
public class SudokuController {
	
	// die Klassen View und Model
    private SudokuView _view;
    private SudokuModel _model;
    
    // ein Boolean für die Listener
    private boolean[] listenerIsActive = new boolean[10];
    
    // initialiserte alles
    public SudokuController() throws Exception {
        this._model = new SudokuModel();
        this._view = new SudokuView(); 
        
        // listenerIsActive
		// 	0	btn createCandidatesInCell
		// 	1	btn btnClearrhsPane
		//	2	btn btnDeleteEntry
		//	3	SudokuInputField[i].addPropertyChangeListener
        //	4	autoSoving
        // die Checkboxen werden nicht berücksichtigt.
        Arrays.fill(listenerIsActive, true);
        
        // baue alle Listener ein
        addListener();
        // Starte das Sudoku, erzeuge den 1. rhsPaneText und übergebe ihn an die View
        _model.startSudoku();
        _view.setrhsPaneText(_model.getRhsPaneText());
        _view.setSudokuInputFieldValue(_model.getSudokuAsStringArray());
        
        
    }
    
    // starte die GUI
    public void showView(){
        this._view.setVisible(true);
    }
    
    /**
     * Die Listener, die wir aus den Internen Klassen generieren
     * werden der View bekannt gemacht, sodass diese mit
     * uns (dem Controller) kommunizieren kann
     */
    private void addListener(){
        this._view.setCandidatesInCell(new createStringWithCandidatesInCell()); //btn createCandidatesInCell
        this._view.setFilledSudokuGrid(new createStringWithFilledSudoku());		//btn btnClearrhsPane
        this._view.setbtnDeleteEntry(new deleteGridValue());					//btn btnDeleteEntry
        this._view.setChangeOnInputField(new changesOnInputfield());			//SudokuInputField[i].addPropertyChangeListener
        this._view.autoSolve(new autoSolving());								//autoSolving
        this._view.setFindNakedSingle(new findNakedSingle());					//Find Naked Single
        this._view.setHiddenSingle(new findHiddenSingle());						//Find Hidden Single
        this._view.setFindNakedPair(new findNakedPair());						//Find Naked Pair
        this._view.setHiddenPair(new findHiddenPair());							//Find Hidden Pair
        this._view.setBlockLineInterception(new findBlockLineInterception());	//Find Block-Line-Interception
        
        
    }
    /*
     *  deaktivier die Listener
     *  z.B. weil das Sudoku gelöst wurde
     */
    
    private void deactivateAllListener(){
    	Arrays.fill(listenerIsActive, false);
    }
    
    /*
     * Ist das sudoku fertig?
     */
    private void checkIfSudokuIsSolved(){
    	if (_model.getsudokuGameIsSolved() == true) {
        	System.out.println("Das Sudoku ist gelöst! " );
        	deactivateAllListener();
        	_model.setrhsPaneForGameIsSolved();
        	_model.printLog();
        	//_model.printErrorLog();
        	_view.setLastrhsPaneText(_model.getRhsPaneText());        	
        }
    }
    /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     *
     */

    /*
     * wurde der Button "createCandidatesInCell" gedrückt, soll
     * _model eine html formatierte Tabelle berechen, in der in jeder Zelle 
     * entweder die Zahl oder eine Candidates-Liste steht.
     */
    class createStringWithCandidatesInCell implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if (listenerIsActive[0] == true) {
        		_model.createRhsPaneTextWithCandidatesInCell();
        		_view.setrhsPaneText(_model.getRhsPaneText());
        	}
        }
    }
    /*
     * wurde der Button "btnClearrhsPane" gedrückt, soll
     * _model eine html formatierte Tabelle berechen, in der in jeder Zelle 
     * entweder die Zahl oder ein leeres Feld steht.
     */
    class createStringWithFilledSudoku implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if (listenerIsActive[1] == true) {
        		_model.initRhsPaneText();
        		_view.setrhsPaneText(_model.getRhsPaneText());
        	}
        }
    }
    /*
     * wurde der Button "btnDeleteEntry" gedrückt, soll
     * _model eine prüfen ob die Zelle ein "given" ist, falls nein, wird die Zelle gelössht
     */
    class deleteGridValue implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if (listenerIsActive[2] == true) {
	            int gridNumber = _view.getGridNumberOfActiveCell();
	        	boolean deleteThisCellValue = _model.isGivenOrDeleteCellValue(gridNumber);
	        	System.out.println("Control: deleteGridValue " + " gridNumber " + gridNumber + 
	        			"[" + _model.getCoordinateByNumber(gridNumber)[0] + _model.getCoordinateByNumber(gridNumber)[1] + "]");
	        	
	            _view.deleteCellValue(gridNumber, deleteThisCellValue,_model.getRhsPaneText() );
        	}
        }
    }
    
    /*
     * Wurde in ein InputField etwas eingetragen, so wird das Event -> somit Old und NewValue übergeben
     * Die GridNummer entpricht dem Feld in dem was eingetagen wurde.
     * _model prüft die Eingabe auf Plausibilität
     * _view schreibt das Ergebnis + setEditable(false) oder null
     */
    class changesOnInputfield implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent e)  {
        	if (listenerIsActive[3] == true) {
	        	System.out.println("Control: changesOnInputfield " + " Event " + e.getPropertyName() + " "  + e.getNewValue());
	        	// [0] GridNumber [1] Value [2] solvedBy
	            int[] InputEventTripple = _view.getInputEventTripple();
	
	        	System.out.println("Control: changesOnInputfield " + " GridNumber " + InputEventTripple[0] + " NewValue "  + InputEventTripple[1] + " solvedBy " + InputEventTripple[2]);

	        	String coordinate = _model.getCoordinateStringByNumber(InputEventTripple[0]);
	        	int value = InputEventTripple[1];
	        	int solvedBy = InputEventTripple[2];
	        	// trage das Tripple ein
	        	boolean eingetragen = _model.trySetValueInCell(coordinate, value, solvedBy);
	        	// hole die Liste der gelösten Zellen und übergebe sie _view
	        	if (eingetragen == true) {
	        		_view.foundValueWithStrategie(_model.getChangedCells());
	        	}
	            // ist das Sudoku fertig?
	            checkIfSudokuIsSolved();
	            
	        	// Soll rhspaneTExt geändert werden?
	        	// muss noch eingebaut werden
	        	_model.createRhsPaneTextWithCandidatesInCell();
	        	_view.setrhsPaneText(_model.getRhsPaneText());
        	}
        }
    }
    /*
     * wurde der Button "autosolving" gedrückt, 
     * durchlaufe alles unsolved Cell und suche die in der er nur einen Candidate gibt.
     */
    class autoSolving implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if (listenerIsActive[4] == true) {
        		final long timeStart = System.currentTimeMillis(); 
	        	System.out.println("Control: autoSolving ");
	            long timeEnd = System.currentTimeMillis(); 
	            System.out.println("Laufszeit des Autosovers: " + (timeEnd - timeStart) + " Millisek."); 
	        	
	            // rufe autosoving auf
	        	boolean autoSolving = _model.autoSolving();
	        	// hole die Liste der gelösten Zellen und übergebe sie _view
	        	if (autoSolving== true) {
	        		_view.foundValueWithStrategie(_model.getChangedCells());

	        		timeEnd = System.currentTimeMillis(); 
	        		System.out.println("Laufszeit des Autosovers + _view: " + (timeEnd - timeStart) + " Millisek.");
	        	}
	            // ist das Sudoku fertig?
	            checkIfSudokuIsSolved();
	            
	            timeEnd = System.currentTimeMillis(); 
	            System.out.println("Laufszeit des Autosovers + _view + testGameEnde: " + (timeEnd - timeStart) + " Millisek."); 
        	}
        }
    }
    /*
     * wurde die CheckBox "NakedSingle" aktiviert/deaktiviert?, ändere den Zustand der Methode
     */
    class findNakedSingle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 3 - Naked Single
        	_model.changeSolvingStrategie(3);
        }
    }
    /*
     * wurde die CheckBox "HiddenSingle" aktiviert/deaktiviert?, ändere den Zustand der Methode
     */
    class findHiddenSingle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 4 - Hidden Single
        	_model.changeSolvingStrategie(4);
        }
    }
    /*
     * wurde die CheckBox "NakedSingle" aktiviert/deaktiviert?, ändere den Zustand der Methode
     */
    class findNakedPair implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 5 - Naked Single
        	_model.changeSolvingStrategie(5);
        }
    }
    /*
     * wurde die CheckBox "HiddenSingle" aktiviert/deaktiviert?, ändere den Zustand der Methode
     */ 
    class findHiddenPair implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 6 - Hidden Single
        	_model.changeSolvingStrategie(6);
        }
    }
    /*
     * wurde die CheckBox "HiddenSingle" aktiviert/deaktiviert?, ändere den Zustand der Methode
     */ 
    class findBlockLineInterception implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 6 - Hidden Single
        	_model.changeSolvingStrategie(7);
        }
    }
}