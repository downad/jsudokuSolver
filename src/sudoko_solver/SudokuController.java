package sudoko_solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Der Controller muss beide die View und das Model kennen
 * da dieser für die Kommunikation zwischen den Beiden sorgt
 */
public class SudokuController {

    //private WurzelView _view;
    private SudokuView _view;
    private SudokuModel _model;

    public SudokuController(){
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
        this._view.setCandidatesInCell(new createStringWithCandidatesInCell());
        this._view.setFilledSudokuGrid(new createStringWithFilledSudoku());
        
    }

    /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     *
     * 1: Hier wird erst der eingegebene Wert aus der View geholt
     * 2: Der Wert wird dem Model übergeben und die Wurzel berechnet
     * 3: Die berechnete Wurzel wird aus dem Model geladen und
     * 4: Wieder der View zum Darstellen übergeben
     *
     * ACHTUNG: Fehlerprüfung muss noch implementeirt werden
     */
 
    /**
     * Hier sagt View gib mir bitte den Werte für die Candidaten.
     * 
     */
    class createStringWithCandidatesInCell implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            _model.createStringWithCandidatesInCell();
            _view.setrhsPaneText(_model.getrhsText());
        }
    }
    class createStringWithFilledSudoku implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            _model.createStringWithFilledSudoku();
            _view.setrhsPaneText(_model.getrhsText());
        }
    }
}