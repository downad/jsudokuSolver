package sudoko_solver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


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
	private static int lhsWidth = 580;
	private static int lhsHeight = 770;
	
	JTextPane rhsPane = new JTextPane();
	private static String RightTitle = "Sudoku Hilfen";
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
	JButton ClearAllowedNumber = new JButton("Finde erlaubte Zahlen je Zelle");
	JButton btnAllValue = new JButton("Alle Möglichkeiten");
	JButton btnClear = new JButton("Lösche die Hilfen");
	JButton btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");

    public MainGUI() {
   	
        JFrame frame = new JFrame(WindowTitle);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(WindowWidth,WindowHeight));
        frame.setMaximumSize(new Dimension(WindowWidth,WindowHeight));
        frame.setLocation(WindowLocX, WindowLocY);
        

//      Sudokofeld (WEST)
        JPanel lhs = new JPanel();
        lhs.setPreferredSize(new Dimension(lhsWidth,lhsHeight));
        //JTextArea lhsText = new JTextArea();
        JTextPane lhsPane = new JTextPane();
        lhsPane.setEditable(false);
        //lhsPane.setWrapStyleWord(true);
        lhsPane.setText("This is a list of items on th left side");
        lhs.add(lhsPane);
        lhsPane.setEditable(true);
        //lhsText.setSize(400, 325);
        lhs.setBorder(BorderFactory.createTitledBorder(LeftTitle));
//      End Sudokofeld (EAST)


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
				  Strategie.ClearAllowedNumber();
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
    


}
