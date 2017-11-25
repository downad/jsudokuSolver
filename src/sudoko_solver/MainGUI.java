package sudoko_solver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;


public class MainGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2275551785684792559L;
	
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
	private JPanel SudokuGrid = new JPanel(); // Sudokufeld Panel, nimmt die jformattedTextfield auf.
	private static int lhsWidth = 580;
	private static int lhsHeight = 770;
	private static int SudokuGridWidth = 570;
	private static int SudokuGridHeight = 750;
	
	private static String RightTitle = "Sudoku Hilfen";
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
	JButton ClearAllowedNumber = new JButton("find Candidates");
	JButton FindNakedSingle = new JButton("NakedSingle");
	
	JButton btnAllValue = new JButton("Alle Möglichkeiten");
	JButton btnClear = new JButton("Lösche die Hilfen");
	JButton btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");
	
	// allgemeine Variablen
	private int FoundWithMethod = 0;
	private JFormattedTextField SudokuInputField[]= new JFormattedTextField[(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 2];
		
	private Font givenFont = new Font("SansSerif", Font.BOLD, 20);
	private Font font1 = new Font("SansSerif", Font.BOLD, 20);
	



    public MainGUI()  {
    	
    	initSudokuInputField();
   	
        JFrame frame = new JFrame(WindowTitle);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(WindowWidth,WindowHeight));
        frame.setMaximumSize(new Dimension(WindowWidth,WindowHeight));
        frame.setLocation(WindowLocX, WindowLocY);
        

//      Sudokofeld (WEST)
        JPanel lhs = new JPanel();
        lhs.setPreferredSize(new Dimension(lhsWidth,lhsHeight));
        SudokuGrid.setPreferredSize(new Dimension(SudokuGridWidth,SudokuGridHeight));
        SudokuGrid.setLayout(new GridBagLayout());
        CreateSudokuGrid();
        //lhs.setLayout(new GridBagLayout());
        /*
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

        int GridCount = 0;
        int borderWidth = 3;
        for (int gridx = 0; gridx < jsudokuSolver.MAXROW; gridx ++) {
        	for (int gridy = 0; gridy < jsudokuSolver.MAXCOL; gridy ++) {
        		GridCount++;
        		gbc.gridx = gridx;
        		gbc.gridy = gridy;
        		//
        		// jede 3. Reihe
        		if (((gridy + 1) / 3) == ((gridy + 1.0) / 3.0)) {
        			// jede 3. Spalte
               		if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, borderWidth ,borderWidth, Color.BLACK));
            			SudokuInputField[GridCount].setText("3");
            		} else {
            			//alle andern Spalten 										 top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, borderWidth, 1, Color.BLACK));
            			SudokuInputField[GridCount].setText("2");
            		}
        		} else {
        			// jede andere Reihe aber 3. Spalte
        			if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1 ,borderWidth, Color.BLACK));
            			SudokuInputField[GridCount].setText("4");
            		} else {
            			//jede andere Reihe und andere spalte
            			SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            			SudokuInputField[GridCount].setText("5");
            		}
        		}
        		
           		//if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
        		//	SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(0, 0, 0 ,borderWidth, Color.BLACK));	
        		//}
        		SudokuGrid.add(SudokuInputField[GridCount], gbc);
        	}
        }
        */
        //SudokuGrid.add(new Zeichnung());
        lhs.add(SudokuGrid);
        //lhs.add(new Zeichnung());
        lhs.setBorder(BorderFactory.createTitledBorder(LeftTitle));
//      End Sudokofeld (WEST)


//      Sudoku Hilfen (EAST)
        JPanel rhs = new JPanel();
        rhs.setPreferredSize(new Dimension(rhsWidth,rhsHeight));
        //JTextPane rhsPane = new JTextPane();
        rhsPane.setPreferredSize(new Dimension(rhsPaneWidth,rhsPaneHeight));//setSize(400,300);
        
        rhsPane.setContentType("text/html");
        //String rhsPaneText = MainControl.SudokuHilfeClear();
        rhsPane.setText(MainControl.SudokuHilfeClear()); //rhsPaneText);
        rhsPane.setEditable(false);
        rhs.add(rhsPane);
        rhs.setBorder(BorderFactory.createTitledBorder(RightTitle));        
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
        top.add(ClearAllowedNumber);
        ClearAllowedNumber.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  Strategie.FindValueAndClearAllowedNumber();
				  rhsPane.setText(MainControl.SudokuHilfen(ShowInrhsPane));
			  }
		} );
        top.add(FindNakedSingle);
        FindNakedSingle.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  FoundWithMethod = Strategie.findNakedSingle();
				  rhsPane.setText(MainControl.SudokuHilfen(ShowInrhsPane));
			  }
		} );
//    	End Sudoku-Lösungs-Strategien (NORTH)

//    	Ausgabefilter (SOUTH)
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(BottomPaneWidth,BottomPaneHeight));
		bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottom.setBorder(BorderFactory.createTitledBorder(BottomTitle));
		bottom.add(btnClear);
		btnClear.addActionListener( new ActionListener() {
			@Override public void actionPerformed( ActionEvent e ) {
				ShowInrhsPane = 1;
				rhsPane.setText(MainControl.SudokuHilfen(ShowInrhsPane));
				setField(11,7);
			    //rhsPane.setText(MainControl.SudokuHilfeClear());
			  }
		} );
		bottom.add(btnAllValue);
		btnAllValue.addActionListener( new ActionListener() {
			@Override public void actionPerformed( ActionEvent e ) {
				ShowInrhsPane = 2;
				rhsPane.setText(MainControl.SudokuHilfen(ShowInrhsPane));
			    //rhsPane.setText(MainControl.SudokuHilfeValue());
			  }
		} );
		bottom.add(btnNotOnlyAllowedNumber);
		btnNotOnlyAllowedNumber.addActionListener( new ActionListener() {
			@Override public void actionPerformed( ActionEvent e ) {
				ShowInrhsPane = 3;
				rhsPane.setText(MainControl.SudokuHilfen(ShowInrhsPane));
			    //rhsPane.setText(MainControl.SudokuHilfeNotAllowedNumbers());
			  }
		} );

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
        NumberFormatter nf = new NumberFormatter();  
    	nf.setMinimum(new Integer(1));
    	nf.setMaximum((jsudokuSolver.MAXNUMBER));
    	int FieldWidth = 63; //	63 rechnerisch
    	int FieldHeight= 83; // 83 rechnerisch
    	
    	for (int i = 1; i < (jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1; i++) {
    		SudokuInputField[i] = new JFormattedTextField(nf);
    		SudokuInputField[i].setHorizontalAlignment(JTextField.CENTER);
    		SudokuInputField[i].setFont(givenFont);
    		//SudokuInputField[i].setColumns(2);
    		SudokuInputField[i].setText(MainControl.getCellValueAsStringByNumber(i));
    		SudokuInputField[i].setName("" + i);
    		SudokuInputField[i].setPreferredSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMaximumSize(new Dimension(FieldWidth,FieldHeight));
    		SudokuInputField[i].setMinimumSize(new Dimension(FieldWidth,FieldHeight));

    		SudokuInputField[i].addPropertyChangeListener(new PropertyChangeListener() {
            	@Override public void propertyChange(PropertyChangeEvent e) {
            		Object source = e.getSource();
            		for (int i = 1; i <(jsudokuSolver.MAXROW * jsudokuSolver.MAXCOL) + 1 ; i++ ) {
            			if (source == SudokuInputField[i]) {
                			//System.out.println("InputField: " + i + (SudokuGridWidth/jsudokuSolver.MAXCOL) + "- " + (SudokuGridHeight/jsudokuSolver.MAXROW)); 
                     		int GridCellNumber = Integer.parseInt(SudokuInputField[i].getName());
                     		Object Value = SudokuInputField[GridCellNumber].getValue();
                			if (Value != null ) {
                				System.out.println("Value: |" + Value.toString() + "| getName " + SudokuInputField[i].getName());
                			}
                		} 
            		}

            	}
    		});
    	}
    }
    private void CreateSudokuGrid(){
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

        int GridCount = 0;
        int borderWidth = 3;
        for (int gridx = 0; gridx < jsudokuSolver.MAXROW; gridx ++) {
        	for (int gridy = 0; gridy < jsudokuSolver.MAXCOL; gridy ++) {
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
        		
           		//if (((gridx + 1) / 3) == ((gridx + 1.0) / 3.0)) {									// top left buttom right
        		//	SudokuInputField[GridCount].setBorder(BorderFactory.createMatteBorder(0, 0, 0 ,borderWidth, Color.BLACK));	
        		//}
        		SudokuGrid.add(SudokuInputField[GridCount], gbc);
        	}
        }
    }
    public void setField(int Number, int Value){
    	int row = (Number / jsudokuSolver.MAXROW);
    	int col = Number - row * jsudokuSolver.MAXROW;
    	System.out.println("row = " + row + " col = " + col);
    	SudokuInputField[Number].setText(""+Value);
    }
}
