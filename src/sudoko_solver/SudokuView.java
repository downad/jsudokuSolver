package sudoko_solver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class SudokuView  extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4441458086756034032L;

	

	// Titel der Fensters
	private static String WindowTitle = "Sudoku - My Sudoku-Solver mit Java";
	
	// Größe des Fensters
	private static int WindowWidth = 1200;
	private static int WindowHeight =970;
	// Position des Fensters
	private static int WindowLocX = 150;
	private static int WindowLocY = 50;
	
	// Oberer Bereich des Fenster
	private static String TopTitle = "Sudoku-Lösungs-Strategien";
	private static int TopPaneWidth = 1150;
	private static int TopPaneHeight = 100;
	
	// Linker Bereich im Fenster
	private static String LeftTitle = "Sudokufeld";
	private JPanel lhs = new JPanel();
	private JPanel SudokuGrid = new JPanel(); // Sudokufeld Panel, nimmt die jformattedTextfield auf.
	private static int lhsWidth = 580;
	private static int lhsHeight = 770;
	private static int SudokuGridWidth = 570;
	private static int SudokuGridHeight = 750;
	
	// Rechter Bereich im Fenster
	private static String RightTitle = "Sudoku Hilfen";
	private JPanel rhs = new JPanel();
	private JTextPane rhsPane = new JTextPane(); // Text auf der Sudoku Hilfeseite
	private static int rhsWidth = 580;
	private static int rhsHeight = 770;
	private static int rhsPaneWidth = 570;
	private static int rhsPaneHeight = 750;
	
	// Center des Fenster
	private static String CenterTitle = "Spacer";
	
	
	// Unterer Bereich des Fenster
	private static String BottomTitle = "Ausgabefilter";
	private static int BottomPaneWidth = 1150;
	private static int BottomPaneHeight = 100;
	
	
	// Unterer Bereich - buttons und Auswahlboxen
	JButton createCandidatesInCell = new JButton("find Candidates"); 	// Hilfen - Bottom
	JButton btnClearrhsPane = new JButton("Lösche die Hilfen");			// Hilfen - Bottom
	JButton btnDeleteEntry = new JButton("Eingabe löschen");			// Hilfen - Bottom
		
	// Lösestrategien - buttons und Auswahlboxen
	JButton autoSolve = new JButton("Automatik");						// AutoSolve - Top
	JCheckBox findNakedSingle = new JCheckBox("Naked Single");			// Methode Top 	Naked Single
	JCheckBox findHiddenSingle = new JCheckBox("Hidden Single");		// Methode Top 	Hidden Single
	JCheckBox findNakedPair = new JCheckBox("Naked Pair");				// Methode Top 	Naked Pair
	JCheckBox findHiddenPair = new JCheckBox("Hidden Pair");			// Methode Top 	Hidden Pair
	JCheckBox findBlockLineInterception = new JCheckBox("Block-Line-Interception");			// Methode Top 	Block-Line-Interception

	/*
	 * 
	 * allgemeine Variablen
	 * 
	 */
	// TextFeld als InputFeld
	private JTextField SudokuInputField[]= new JTextField[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1];
	
	// Integer des Zellennummer 
	private int gridNumberOfActiveCell = 0;
	private void setGridNumberOfActiveCell(int number){
		gridNumberOfActiveCell = number;
	}
	public int getGridNumberOfActiveCell(){
		return gridNumberOfActiveCell;
	}
	
	// Zeichensatz
	private Font givenFont = new Font("SansSerif", Font.BOLD, 20);

	// gab es Änderungen?	
	private PropertyChangeSupport changes = new PropertyChangeSupport( this );
	
	// Hilfvariablen für die Inputfelder
    private int InputFieldEventGridNumber = 0;
	private int InputFieldEventNewValue =0;
	private int InputFieldEventsolvedBy = 0;
	public void setInputEventTriple(int initGridnumber, int initNewValue, int initSolvedBy) { 
		InputFieldEventGridNumber = initGridnumber; 
		InputFieldEventNewValue = initNewValue;
		InputFieldEventsolvedBy = initSolvedBy; 
	}
	public int[] getInputEventTripple(){
		int[] returnInt = new int[3];
		// [0] GridNumber [1] Value [2] solvedBy
		returnInt[0] = InputFieldEventGridNumber; 
		returnInt[1] = InputFieldEventNewValue;
		returnInt[2] = InputFieldEventsolvedBy;
		return returnInt;
	}
	
	public void setValueInCellByGridNumber(boolean valueIsSetInCell, int gridNumber, int value){
		if (valueIsSetInCell == true){
			SudokuInputField[gridNumber].setText(""+value);
			SudokuInputField[gridNumber].setEditable(false);
			SudokuInputField[gridNumber].setBackground(Color.lightGray); 
			//createCandidatesInCell.doClick();
		} else {
			if (SudokuInputField[gridNumber].isEditable() == true) {
				SudokuInputField[gridNumber].setText("");
			}
		}
	}

	/*
	 * Starte die View 
	 * Init View
	 */
	
    public SudokuView()  {
    	initSudokuInputField();
    	init();
    }
    private void init() {
    	   	
        JFrame frame = new JFrame(WindowTitle);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(WindowWidth,WindowHeight));
        frame.setMaximumSize(new Dimension(WindowWidth,WindowHeight));
        frame.setLocation(WindowLocX, WindowLocY);
        
//      Sudokofeld (WEST)
        lhs.setPreferredSize(new Dimension(lhsWidth,lhsHeight));
        lhs.setBorder(BorderFactory.createTitledBorder(LeftTitle));
        SudokuGrid.setPreferredSize(new Dimension(SudokuGridWidth,SudokuGridHeight));
        SudokuGrid.setLayout(new GridBagLayout());
        getSudokuInputGrid();
        lhs.add(SudokuGrid);
//      End Sudokofeld (WEST)
        
//      Sudoku Hilfen (EAST)
        rhs.setPreferredSize(new Dimension(rhsWidth,rhsHeight));
        rhs.setBorder(BorderFactory.createTitledBorder(RightTitle));        
        rhsPane.setPreferredSize(new Dimension(rhsPaneWidth,rhsPaneHeight));//setSize(400,300);
        rhsPane.setContentType("text/html");
        rhsPane.setText("rechts"); //rhsPaneText);
        rhsPane.setEditable(false);
        rhs.add(rhsPane);
//      End Sudoku-Hilfen (EAST)
                
//      Spacer (CENTER)
        JPanel center = new JPanel();
        center.setSize(0, 0);
//      End Spacer (CENTER)
                
//    	Sudoku-Lösungs-Strategien (NORTH)
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(TopPaneWidth,TopPaneHeight));
        top.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.setBorder(BorderFactory.createTitledBorder(TopTitle));
        top.add(autoSolve);
        top.add(findNakedSingle);
        top.add(findHiddenSingle);
        top.add(findNakedPair);
        top.add(findHiddenPair);
        top.add(findBlockLineInterception);
//    	End Sudoku-Lösungs-Strategien (NORTH)

//    	Ausgabefilter (SOUTH)
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(BottomPaneWidth,BottomPaneHeight));
		bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottom.setBorder(BorderFactory.createTitledBorder(BottomTitle));
		bottom.add(createCandidatesInCell);
		bottom.add(btnClearrhsPane);
		bottom.add(btnDeleteEntry);
//    	End Ausgabefilter (SOUTH) 
    
// bastle das alles zusammen
    
        frame.add(lhs, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        frame.add(rhs, BorderLayout.EAST);
        frame.add(top, BorderLayout.NORTH);
        frame.add(bottom, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Inwelchem Feld (int Gridnumber ) ist der Focus?
     */
    public int getGridNumberByFocusEvent(FocusEvent e){
    	int GridNumber = 0;
    	// berechen Gridnumber aus source-name
    	Object source = e.getSource();
		String a[] = source.toString().split(",");
		String b[] = a[0].split("Field");
		StringBuilder c = new StringBuilder(b[1]);
		// Ersetze das DOOFE [
		c.setCharAt(0, '0');
		GridNumber= Integer.parseInt(c.toString());
    	return GridNumber;
    }
    
    /*
     * Setze das Sudoku inital
     */
    private void initSudokuInputField() {
    	int FieldWidth = 63; //	63 rechnerisch
    	int FieldHeight= 83; // 83 rechnerisch
    	
    	for (int i = 1; i < (jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1; i++) {
    		SudokuInputField[i] = new JTextField();
    		SudokuInputField[i].setHorizontalAlignment(JTextField.CENTER);
    		SudokuInputField[i].setFont(givenFont);
    		SudokuInputField[i].setName("" + i);
    		SudokuInputField[i].setPreferredSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMaximumSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMinimumSize(new Dimension(FieldWidth,FieldHeight));		
    		SudokuInputField[i].addFocusListener(new FocusAdapter(){
    				//private String OldValue = "";
    				private String NewValue = "";

    				@Override
    		       public void focusGained(final FocusEvent e){
    		           //i always like to wrap this method with swing utilities
    		           // which puts it at the end of the EventQueue, so it's executed after all pending events
    		           SwingUtilities.invokeLater(new Runnable(){
    		                @Override
    		                public void run(){
    		                    // Hole aus der Quelle die GridNumber
    		                	// färbe das Feld rot
    		                	int GridNumber= getGridNumberByFocusEvent(e);
    		                	setGridNumberOfActiveCell(GridNumber);
    		            		SudokuInputField[GridNumber].setBackground(Color.RED); 
    		            		//OldValue = SudokuInputField[GridNumber].getText();
    		                }
    		           });
    		       } 
    		       @Override
    		       public void focusLost(final FocusEvent e){
    		           //i always like to wrap this method with swing utilities
    		           // which puts it at the end of the EventQueue, so it's executed after all pending events
    		           SwingUtilities.invokeLater(new Runnable(){
    		                @Override
    		                public void run(){
    		                    // Hole aus der Quelle die GridNumber
    		                	// färbe das Feld hellgrau
    		                	int GridNumber= getGridNumberByFocusEvent(e);
    		            		if (SudokuInputField[GridNumber].isEditable() == true) {
    		            			SudokuInputField[GridNumber].setBackground(Color.WHITE); 
    		            		} else {
    		            			SudokuInputField[GridNumber].setBackground(Color.lightGray); 
    		            		}
    		            		NewValue = SudokuInputField[GridNumber].getText();
    		            		// Prüfe die Eingabe
    		            		int number = getIntegerFromString(NewValue);
     		            		if (number >0 && number <= jsudokuSolver.MAXNUMBER ) {
    		            			System.out.println("View - focusLost NewValue ist ein erlaubter Integer > 0 Value = >"+number+"<");
         		            		int solvedBy = 2;
         		            		// [0] GridNumber [1] Value [2] solvedBy
         		            		setInputEventTriple(GridNumber,  number,  solvedBy);
	
									changes.firePropertyChange("Text", NewValue, number);    		            			
    		            		} else {
    		            			if (SudokuInputField[GridNumber].isEditable()== true) {
    		            				SudokuInputField[GridNumber].setText("");
    		            			}
    		            		}
    		            		System.out.println("View - focusLost NewValue " + NewValue );
    		                }
    		           });
    		       }
    		  });
    		  
    	}
    	
    }
    /*
     * Ist the Value an Integer?
     * if yes - return that integer
     */
    private static Integer getIntegerFromString(String str) {
        Integer n = null;
        System.out.println("View - getIntegerFromString str |" + str + "|");
        try {
            n = Integer.parseInt(str);
        } catch (Exception ex) {
            // leave n null, the string is invalid
        	n= -99;
        }
        System.out.println("View - getIntegerFromString n |" + n + "|");
        return n;
    }

    /*
     * erzeuge das Sudoku-Feld für die Inputfelder
     */
    private void getSudokuInputGrid(){
        GridBagConstraints gbc = new GridBagConstraints();
 		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

        int GridCount = 0;
        int borderWidth = 3;
        //row
    	for (int gridy = 0; gridy < jsudokuSolver.MAXCOL; gridy ++) {
    		//col
    		for (int gridx = 0; gridx < jsudokuSolver.MAXROW; gridx ++) {
        		GridCount++;
        		gbc.gridx = gridx;
        		gbc.gridy = gridy;
        		//
        		// jede 3. Reihe
        		if (((gridy + 1) / 3) == ((gridy + 1.0) / 3.0)) {
        			// jede 3. Spalte
               		if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, borderWidth ,borderWidth, Color.BLACK));
            		} else {
            			//alle andern Spalten 										 top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, borderWidth, 1, Color.BLACK));
            		}
        		} else {
        			// jede andere Reihe aber 3. Spalte
        			if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1 ,borderWidth, Color.BLACK));
            		} else {
            			//jede andere Reihe und andere spalte
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            		}
        		}
        		SudokuGrid.add(SudokuInputField[GridCount], gbc);
        	}
        }
    }
    
    /*
     * Sudoku View - setze in die rhsPane den Text
     */
    public void setrhsPaneText(String rhsPaneText){
    	 rhsPane.setText(rhsPaneText); 
    }
    /*
     * Ist das Feld gelöst, setze letzmalig eine rhsPane, jetzt mit Scrollbalken
     */
    public void setLastrhsPaneText(String rhsPaneText){
    	JScrollPane scrollPane = new JScrollPane(rhsPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
   	 	rhsPane.setText(rhsPaneText); //rhsPaneText);
   	 	rhs.add(scrollPane);
   }
    
    /*
     * wird beim Initialiseiren aufgerufen, 
     * läuft einmalig, 
     * 
     */
    public void setSudokuInputFieldValue( String[] Values){
    	for (int i = 1; i< SudokuInputField.length; i++) {
    		//SudokuInputFieldValue[i] = Values[i];
    		String Value = Values[i];
    		if (Integer.parseInt(Value) > 0) {
     			SudokuInputField[i].setText(Values[i]);
    			SudokuInputField[i].setEditable(false);
    			SudokuInputField[i].setBackground(Color.lightGray);
    			SudokuInputField[i].setForeground(Color.RED);
    		} else {
    			SudokuInputField[i].setText("");
    		}
    	}
    }
    /*
     * kann die Zelle gelöschtwerden?
     * Ja - dann löschen
     */
    public void deleteCellValue(int gridNumber, boolean canBeDeleted, String rhsPaneText){
    	if (canBeDeleted == true) {
			SudokuInputField[gridNumber].setText("");
			SudokuInputField[gridNumber].setEditable(true);
			SudokuInputField[gridNumber].setBackground(Color.WHITE);
			rhsPane.setText(rhsPaneText); //rhsPaneText);
    	}
    }

    /*
     * model hat werte gefunden, diese werden in die Inputfelder eingesetzt
     */
    public void foundValueWithStrategie(LinkedList<logEntrySolving> allChangedCells) {
    	int value = 0;		
    	int gridNumber = 0; 
    	//final long timeStart = System.currentTimeMillis(); 
    	//long timeEnd = System.currentTimeMillis(); 
    	//System.out.println("View: foundValueWithStrategie ");
    	logEntrySolving changedCell;
    	int i = 0;
    	while (!allChangedCells.isEmpty()) {
    		changedCell = allChangedCells.remove(0);  
    		i++;
    		//timeEnd = System.currentTimeMillis(); 
            //System.out.println("View: foundValueWithStrategie "+i+". Durchlauf " + (timeEnd - timeStart) + " Millisek."); 
            gridNumber = changedCell.getGridNumber(); 
    		value = changedCell.getValue(); 
        	if ( value> 0) {
        		setValueInCellByGridNumber(true, gridNumber, value);
        	} else {
        		setValueInCellByGridNumber(false, gridNumber, value);
        	}        	  		
    	}
        //timeEnd = System.currentTimeMillis(); 
        //System.out.println("View: foundValueWithStrategie " + (timeEnd - timeStart) + " Millisek."); 
    }

    
    /**
     * Funktionen bereitstellen, mit denen man später aus
     * dem Controller die nötigen Listener hinzufügen kann
     */

    // Sudoku Listener
    /*
     * Button createCandidatesInCell - setze in rhsPaneText eine
     * html-Table mit Zahl oder Candidats-List
     */
    public void setCandidatesInCell(ActionListener l){
        this.createCandidatesInCell.addActionListener(l);
    }
    /*
     * Button btnClearrhsPane - setze in rhsPaneText eine
     * html-Table mit Zahl oder leerem Feld
     */
    public void setFilledSudokuGrid(ActionListener l){
        this.btnClearrhsPane.addActionListener(l);
    }
    /*
     * Button btnDeleteEntry - Löschen den aktuellen Zelleninhalt
     * falls die Zelle keine "given" war.
     */
    public void setbtnDeleteEntry(ActionListener l){
        this.btnDeleteEntry.addActionListener(l);
    }
    /*
     * Listerner für die InputFelder
     */
    public void setChangeOnInputField( PropertyChangeListener l )
    {
    	//System.out.println("View: -setChangeOnInputField ");
    	this.changes.addPropertyChangeListener( l );
    }
    /*
     * Button autoSolve - Starte autosolving
     * berücksichtigt die angewählten Strategien
     */
    public void autoSolve(ActionListener l){
        this.autoSolve.addActionListener(l);
    }

    /*
     * Checkbox findNakedSingle - Durchsuche die Candidates 
     * gibt es einen Candidaten nur einmal ->NakedSingle
     */
    public void setFindNakedSingle(ActionListener l){
        this.findNakedSingle.addActionListener(l);
    }    
    /*
     * Checkbox findHiddenSingle - Durchsuche die Candidates 
     * gibt es einen Candidaten nur einmal in der Unit?
     */
    public void setHiddenSingle(ActionListener l){
        this.findHiddenSingle.addActionListener(l);
    }
    /*
     * Checkbox findNakedSingle - Durchsuche die Candidates 
     * gibt es einen Candidaten nur einmal ->NakedSingle
     */
    public void setFindNakedPair(ActionListener l){
        this.findNakedPair.addActionListener(l);
    }    
    /*
     * Checkbox findHiddenSingle - Durchsuche die Candidates 
     * gibt es einen Candidaten nur einmal in der Unit?
     */
    public void setHiddenPair(ActionListener l){
        this.findHiddenPair.addActionListener(l);
    }
    /*
     * Checkbox findBlockLineInterception - Durchsuche die Candidates 
     * gibt es einen Candidaten nur einmal in der Unit?
     */
    public void setBlockLineInterception(ActionListener l){
        this.findBlockLineInterception.addActionListener(l);
    }
}
/*
 * findNakedPair findBlockLineInterception
private boolean[] solvingStategie = { false,
		true, // naked Single
		//true, // hidden Single
	};
	*/
/*
public boolean[] getSolvingStrategie(){
	return solvingStategie;
}
*/

/*
public void inputEventValueIsSetInCell_old(boolean valueIsSetInCell){
	if (valueIsSetInCell == true){
		SudokuInputField[InputFieldEventGridNumber].setText(""+InputFieldEventNewValue);
		SudokuInputField[InputFieldEventGridNumber].setEditable(false);
		SudokuInputField[InputFieldEventGridNumber].setBackground(Color.lightGray); 
		createCandidatesInCell.doClick();
	} else {
		if (SudokuInputField[InputFieldEventGridNumber].isEditable() == true) {
			SudokuInputField[InputFieldEventGridNumber].setText("");
		}
	}
}
*/
/*
public int getEventGridNumber() { 
	return InputFieldEventGridNumber; 
}
public void setEventGridNumber(int initGridnumber) { 
	InputFieldEventGridNumber = initGridnumber; 	
}

public int getEventNewValue() { 
	return InputFieldEventNewValue;
}
public void setEventNewValue(int initNewValue) { 
	InputFieldEventNewValue = initNewValue;
}
public int getEventSolvedBy() { 
	return InputFieldEventsolvedBy; 
}
public void setEventSolvedBy(int initSolvedBy) { 
	InputFieldEventsolvedBy = initSolvedBy; 	
}

*/
/*
public void foundValueWithStrategie_old(LinkedList<int[]> setCellDouble) {
	//setCellDouble[0] = coordinates.get(i);
	//setCellDouble[1] = ""+candidateValue;
	int value = 0;		// was int
	int gridNumber = 0; // was int
	final long timeStart = System.currentTimeMillis(); 
	long timeEnd = System.currentTimeMillis(); 
	System.out.println("View: foundValueWithStrategie ");
	int[] cellDouble = new int[2];
	int i = 0;
	while (!setCellDouble.isEmpty()) {
		i++;
		timeEnd = System.currentTimeMillis(); 
        System.out.println("View: foundValueWithStrategie "+i+". Durchlauf " + (timeEnd - timeStart) + " Millisek."); 
        cellDouble = setCellDouble.remove();
		gridNumber = cellDouble[0]; //setCellDouble.get(i)[0];
		value = cellDouble[1]; // setCellDouble.get(i)[1];
    	if ( value> 0) {
    		setValueInCellByGridNumber(true, gridNumber, value);
    	} else {
    		setValueInCellByGridNumber(false, gridNumber, value);
    	}
	}
    timeEnd = System.currentTimeMillis(); 
    System.out.println("View: foundValueWithStrategie" + (timeEnd - timeStart) + " Millisek."); 
}
*/