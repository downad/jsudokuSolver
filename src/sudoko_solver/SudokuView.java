package sudoko_solver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.NumberFormatter;

public class SudokuView  extends JFrame {

	// Titel der Fensters
	private static String WindowTitle = "Sudoku - My Sudoku-Solver mit Java";
	
	// Größe des Fensters
	private static int WindowWidth = 1200;
	private static int WindowHeight =970;
	// Position des Fensters
	private static int WindowLocX = 150;
	private static int WindowLocY = 50;
	
	// Titel / Position / Größe der Framebereiche
	private static String TopTitle = "Sudoku-Lösungs-Strategien";
	private static int TopPaneWidth = 1150;
	private static int TopPaneHeight = 100;
	
	private static String LeftTitle = "Sudokufeld";
	private JPanel lhs = new JPanel();
	private JPanel SudokuGrid = new JPanel(); // Sudokufeld Panel, nimmt die jformattedTextfield auf.
	private static int lhsWidth = 580;
	private static int lhsHeight = 770;
	private static int SudokuGridWidth = 570;
	private static int SudokuGridHeight = 750;
	
	private static String RightTitle = "Sudoku Hilfen";
	private JPanel rhs = new JPanel();
	private JTextPane rhsPane = new JTextPane(); // Text auf der Sudoku Hilfeseite
	private static int rhsWidth = 580;
	private static int rhsHeight = 770;
	private static int rhsPaneWidth = 570;
	private static int rhsPaneHeight = 750;
	
	private static String CenterTitle = "Spacer";
	
	private static String BottomTitle = "Ausgabefilter";
	private static int BottomPaneWidth = 1150;
	private static int BottomPaneHeight = 100;
	
	/* ShowInrhsPane 
	 	1 = btnClear = new JButton("Lösche die Hilfen"); 
		2 = btnAllValue = new JButton("Alle Möglichkeiten");
		3 = btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");
	*/
	private static int ShowInrhsPane = 1;
	
	// buttons
	JButton createCandidatesInCell = new JButton("find Candidates");
	JButton findNakedSingle = new JButton("NakedSingle");
	JButton refresh = new JButton("refresh rhs");
	
	
	JButton btnAllValue = new JButton("Alle Möglichkeiten");
	JButton btnClearrhsPane = new JButton("Lösche die Hilfen");
	JButton btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");
	
	
	// allgemeine Variablen
	private JFormattedTextField SudokuInputField[]= new JFormattedTextField[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1];
		
	private Font givenFont = new Font("SansSerif", Font.BOLD, 20);
	private Font font1 = new Font("SansSerif", Font.BOLD, 20);
	private String[] SudokuInputFieldValue = new String[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1];
	

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
//        JPanel lhs = new JPanel();
        lhs.setPreferredSize(new Dimension(lhsWidth,lhsHeight));
        lhs.setBorder(BorderFactory.createTitledBorder(LeftTitle));
        SudokuGrid.setPreferredSize(new Dimension(SudokuGridWidth,SudokuGridHeight));
        SudokuGrid.setLayout(new GridBagLayout());
        
        //SudokuGrid.add(getSudokuInputGrid());
        getSudokuInputGrid();
        lhs.add(SudokuGrid);
//      End Sudokofeld (WEST)

        
//      Sudoku Hilfen (EAST)
        rhs.setPreferredSize(new Dimension(rhsWidth,rhsHeight));
        rhs.setBorder(BorderFactory.createTitledBorder(RightTitle));        
        rhsPane.setPreferredSize(new Dimension(rhsPaneWidth,rhsPaneHeight));//setSize(400,300);
        rhsPane.setContentType("text/html");
        //String rhsPaneText = MainControl.SudokuHilfeClear();
        //rhsPane.setText(Sudoku.SudokuHilfeClear()); //rhsPaneText);
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
        //top.add(createCandidatesInCell);
        top.add(findNakedSingle);
        top.add(refresh);
//    	End Sudoku-Lösungs-Strategien (NORTH)

//    	Ausgabefilter (SOUTH)
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(BottomPaneWidth,BottomPaneHeight));
		bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottom.setBorder(BorderFactory.createTitledBorder(BottomTitle));
		bottom.add(createCandidatesInCell);
		bottom.add(btnClearrhsPane);

		//bottom.add(btnAllValue);
		//bottom.add(btnNotOnlyAllowedNumber);


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
    private void initSudokuInputField() {
        //NumberFormatter nf = new NumberFormatter();
    	NumberFormatter nf = new NumberFormatter() {
            // we have to allow the empty string, the call chain is
            //      DefaultFormatter
            //              DefaultDocumentFilter.remove
            //              replace
            //              canReplace
            //              isValidEdit
            //              stringToValue
            public Object stringToValue(String string)
                throws ParseException {
                if (string == null || string.length() == 0) {
                    return null;
                }
                return super.stringToValue(string);
            }
        };
    	nf.setMinimum(new Integer(0));
    	nf.setMaximum((jsudokuSolver.MAXNUMBER));
    	int FieldWidth = 63; //	63 rechnerisch
    	int FieldHeight= 83; // 83 rechnerisch
    	
    	for (int i = 1; i < (jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1; i++) {
    		SudokuInputField[i] = new JFormattedTextField(nf);
    		SudokuInputField[i].setHorizontalAlignment(JTextField.CENTER);
    		SudokuInputField[i].setFont(givenFont);
    		//SudokuInputField[i].setColumns(2);
    		SudokuInputField[i].setName("" + i);
    		//SudokuInputField[i].setText(SudokuInputField[i].getName()); //setText(Sudoku.getCellValueAsStringByNumber(i));
    		//SudokuInputField[i].setText(SudokuInputFieldValue[i]); //Sudoku.getCellValueAsStringByNumber(i));
    		
    		System.out.println("View: initSudokuInputField - SudokuInputFieldValue [" + i +"] = " + SudokuInputFieldValue[i]);
    		SudokuInputField[i].setPreferredSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMaximumSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMinimumSize(new Dimension(FieldWidth,FieldHeight));
    		/*
    		if (Sudoku.isCellSolvedByNumber(i) == true) {
    			SudokuInputField[i].setEditable(false);
    		} else {
    			SudokuInputField[i].setText("-");
    		}
    		*/
    		// Eventlistener
    		SudokuInputField[i].addPropertyChangeListener("value", new FormattedTextFieldListener());
    	}
    }
    private void getSudokuInputGrid(){
        GridBagConstraints gbc = new GridBagConstraints();
        //JPanel returnJPanel = new JPanel();
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
            			//SudokuInputField[GridCount].setText("3");
            		} else {
            			//alle andern Spalten 										 top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, borderWidth, 1, Color.BLACK));
            			//SudokuInputField[GridCount].setText("2");
            		}
        		} else {
        			// jede andere Reihe aber 3. Spalte
        			if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1 ,borderWidth, Color.BLACK));
            			//SudokuInputField[GridCount].setText("4");
            		} else {
            			//jede andere Reihe und andere spalte
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            			//SudokuInputField[GridCount].setText("5");
            		}
        		}
        		SudokuGrid.add(SudokuInputField[GridCount], gbc);
        		//returnJPanel.add(SudokuInputField[GridCount], gbc);
        	}
        }
    	//return returnJPanel;
    }
    //Sudoku View
    public void setrhsPaneText(String rhsPaneText){
    	 rhsPane.setText(rhsPaneText); //rhsPaneText);
    }
    public void setSudokuInputFieldValue( String[] Values){
    	for (int i = 1; i< SudokuInputField.length; i++) {
    		SudokuInputFieldValue[i] = Values[i];
    		System.out.println("View: setSudokuInputFieldValue - i: " + i + " Value = |" + Values[i] + "|");
    		SudokuInputField[i].setText(Values[i]);
    		//lhs.repaint();
    	}
    }
    


    /**
     * Funktionen bereitstellen, mit denen man später aus
     * dem Controller die nötigen Listener hinzufügen kann
     */

    // Sudoku Listener
    public void setCandidatesInCell(ActionListener l){
        this.createCandidatesInCell.addActionListener(l);
    }
    public void setFilledSudokuGrid(ActionListener l){
        this.btnClearrhsPane.addActionListener(l);
    }
}
