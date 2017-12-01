package sudoko_solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//import sudoko_solver.SudokuView.FormattedTextFieldListener;

/**
 * Der Controller muss beide die View und das Model kennen
 * da dieser für die Kommunikation zwischen den Beiden sorgt
 */
public class SudokuController {

    //private WurzelView _view;
    private SudokuView _view;
    private SudokuModel _model;

    public SudokuController() throws Exception {
        this._model = new SudokuModel();
        this._view = new SudokuView(); //WurzelView();

        addListener();
        _model.StartSudoku();
        _view.setrhsPaneText(_model.getrhsText());
        _view.setSudokuInputFieldValue(_model.getSudokuAsStringArray());
        
        
    }

    public void showView(){
        this._view.setVisible(true);
    }
/**
     * Die Listener, die wir aus den Internen Klassen generieren
     * werden der View bekannt gemacht, sodass diese mit
     * uns (dem Controller) kommunizieren kann
     */
    private void addListener(){
        // Sudoku
        this._view.setCandidatesInCell(new createStringWithCandidatesInCell()); //btn createCandidatesInCell
        this._view.setFilledSudokuGrid(new createStringWithFilledSudoku());		//btn btnClearrhsPane
        this._view.setChangeOnInputField(new changesOnInputfield());			//SudokuInputField[i].addPropertyChangeListener
        	
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
     * wude der Button "createCandidatesInCell" gedrückt, soll
     * _model eine html formatierte Tabelle berechen, in der in jeder Zelle 
     * entweder die Zahl oder eine Candidates-Liste steht.
     */
    class createStringWithCandidatesInCell implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            _model.createStringWithCandidatesInCell();
            _view.setrhsPaneText(_model.getrhsText());
        }
    }
    /*
     * wude der Button "btnClearrhsPane" gedrückt, soll
     * _model eine html formatierte Tabelle berechen, in der in jeder Zelle 
     * entweder die Zahl oder ein leeres Feld steht.
     */
    class createStringWithFilledSudoku implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            _model.createStringWithFilledSudoku();
            _view.setrhsPaneText(_model.getrhsText());
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
        	System.out.println("Control: changesOnInputfield " + " Event " + e.getPropertyName() + " "  + e.getNewValue());
        	// [0] GridNumber [1] Value [2] solvedBy
            int[] InputEventTripple = _view.getInputEventTripple();
            /*
        	int GridNumber = _view.getEventGridNumber(); 	// berechnetes Feld im SudokuGrid
        	String OldValue = _view.getEventOldValue(); //    e.getOldValue();         
        	String NewValue = e.getNewValue().toString(); 
        	int solvedBy = _view.getEventSolvedBy(); 		// bei Manuellem Lösen ist solvedBy = 2
        	*/
        	System.out.println("Control: changesOnInputfield " + " GridNumber " + InputEventTripple[0] + " NewValue "  + InputEventTripple[1] + " solvedBy " + InputEventTripple[2]);
            boolean valueIsSetInCell = _model.testAndSetValue( InputEventTripple[0],InputEventTripple[1],InputEventTripple[2]);
            _view.inputEventValueIsSetInCell(valueIsSetInCell);
            System.out.println("Control: changesOnInputfield passt? " + valueIsSetInCell );
            //_view.setrhsPaneText(_model.getrhsText());
        }
    }
    
}