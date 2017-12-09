package sudoko_solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;



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
        // 	5	FindNakedSingle
        Arrays.fill(listenerIsActive, true);
        
        addListener();
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
        this._view.setHiddenNakedSingle(new findHiddenSingle());				//Find Hidden Single
        
    }
    private void deactivateAllListener(){
    	Arrays.fill(listenerIsActive, false);
    }
    private void checkIfSudokuIsSolved(){
    	if (_model.getsudokuGameIsSolved() == true) {
        	System.out.println("Das Sudoku ist gelöst! " );
        	deactivateAllListener();
        	_model.setrhsPaneForGameIsSolved();
        	//_model.printLog();
        	//_model.printErrorLog();
        	_view.setLastrhsPaneText(_model.getRhsPaneText());        	
        }
    }
    /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     *
     * 1: Hier wird erst der eingegebene Wert aus der View geholt
     * 2: Der Wert wird dem Model übergeben und die Wurzel berechnet
     * 3: Die berechnete Wurzel wird aus dem Model geladen und
     * 4: Wieder der View zum Darstellen übergeben
     *
     * ACHTUNG: Fehlerprüfung muss noch implementiert werden
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
	            //boolean valueIsSetInCell = _model.testAndSetValue( InputEventTripple[0],InputEventTripple[1],InputEventTripple[2]);
	        	//_view.inputEventValueIsSetInCell(valueIsSetInCell);
	        	String coordinate = _model.getCoordinateStringByNumber(InputEventTripple[0]);
	        	int value = InputEventTripple[1];
	        	int solvedBy = InputEventTripple[2];
	        	//LinkedList<int[]>   eingetragen = _model.trySetValueInCell(coordinate, value, solvedBy);
//	        	ArrayList<int[]>   eingetragen = _model.trySetValueInCell(coordinate, value, solvedBy);
	            //_view.foundValueWithStrategie(eingetragen);
	            _view.foundValueWithStrategie(_model.getChangedCells());
	            checkIfSudokuIsSolved();
	            
	            //System.out.println("Control: changesOnInputfield passt? " + eingetragen.get(1) );
	            //_view.setrhsPaneText(_model.getrhsText());
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
	        	LinkedList<int[]>   autoSolvingValueList = _model.autoSolving();
	        	// rufe autosoving auf
	        	// hole die Liste der gelösten Zellen und übergebe sie _view
	        	
	            long timeEnd = System.currentTimeMillis(); 
	            System.out.println("Laufszeit des Autosovers: " + (timeEnd - timeStart) + " Millisek."); 
	            //_view.foundValueWithStrategie(autoSolvingValueList);
	            _view.foundValueWithStrategie(_model.getChangedCells());
	            timeEnd = System.currentTimeMillis(); 
	            System.out.println("Laufszeit des Autosovers + _view: " + (timeEnd - timeStart) + " Millisek."); 
	            checkIfSudokuIsSolved();
	            timeEnd = System.currentTimeMillis(); 
	            System.out.println("Laufszeit des Autosovers + _view + testGameEnde: " + (timeEnd - timeStart) + " Millisek."); 
        	}
        }
    }
    /*
     * wurde der Button "FindNakedSingle" gedrückt, 
     * aktiviere diese Methode
     */
    class findNakedSingle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 3 - Naked Single
        	_model.changeSolvingStrategie(3);
        }
    }
    /*
     * wurde der Button "FindNakedSingle" gedrückt, 
     * aktiviere diese Methode
     */
    class findHiddenSingle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	// 4 - Hidden Single
        	_model.changeSolvingStrategie(4);
        }
    }
}