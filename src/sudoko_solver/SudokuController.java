package sudoko_solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
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
        // 	4	FindNakedSingle
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
        this._view.setFindNakedSingle(new FindNakedSingle());					//FindNakedSingle
    }
    private void deactivateAllListener(){
    	Arrays.fill(listenerIsActive, false);
    }
    private void checkIfSudokuIsSolved(){
    	if (_model.getsudokuGameIsSolved() == true) {
        	System.out.println("Das Sudoku ist gelöst! " +_model.getRhsPaneText() );
        	deactivateAllListener();
        	_model.setrhsPaneForGameIsSolved();
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
        		_model.createStringWithCandidatesInCell();
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
        		_model.createStringWithFilledSudoku();
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
	            boolean valueIsSetInCell = _model.testAndSetValue( InputEventTripple[0],InputEventTripple[1],InputEventTripple[2]);
	            _view.inputEventValueIsSetInCell(valueIsSetInCell);
	            checkIfSudokuIsSolved();
	            
	            System.out.println("Control: changesOnInputfield passt? " + valueIsSetInCell );
	            //_view.setrhsPaneText(_model.getrhsText());
        	}
        }
    }
    
    /*
     * wurde der Button "FindNakedSingle" gedrückt, 
     * durchlaufe alles unsolved Cell und suche die in der er nur einen Candidate gibt.
     */
    class FindNakedSingle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if (listenerIsActive[4] == true) {
	        	System.out.println("Control: FindNakedSingle ");
	        	ArrayList<int[]>   nakedSingleValueList = _model.findNakedSingle();
	            _view.foundNakedSingle(nakedSingleValueList);
	            checkIfSudokuIsSolved();
        	}
        }
    }
}