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
	private static int WindowWidth = 1000;
	private static int WindowHeight = 640;
	// Position des Fensters
	private static int WindowLocX = 150;
	private static int WindowLocY = 150;
	
	// Titel / Position / Größe der Framebereiche
	private static String TopTitle = "Sudoku-Lösungs-Strategien";
	private static int TopPaneWidth = 950;
	private static int TopPaneHeight = 100;
	
	private static String LeftTitle = "Sudokufeld";
	private static int lhsWidth = 440;
	private static int lhsHeight = 440;
	
	JTextPane rhsPane = new JTextPane();
	private static String RightTitle = "Sudoku Hilfen";
	private static int rhsWidth = 440;
	private static int rhsHeight = 440;
	private static int rhsPaneWidth = 400;
	private static int rhsPaneHeight = 350;
	
	private static String CenterTitle = "Spacer";
	
	private static String BottomTitle = "Ausgabefilter";
	private static int BottomPaneWidth = 950;
	private static int BottomPaneHeight = 100;
	
	// buttons
	JButton btn1 = new JButton("Random Button");
	JButton btnAllValue = new JButton("Alle Möglichkeiten");
	JButton btnClear = new JButton("Lösche die Hilfen");
	JButton btnNotOnlyAllowedNumber = new JButton("Alle Zahlen die Nicht erlaubt sind");

	
	// Value
	/*
	private static String SudokuHilfe = "<table Border=1>" +
            "<tr><td> Feld 11</td><td> Feld 12</td><td> Feld 13</td></tr>" +
            "<tr><td> Feld 21</td><td> Feld 22</td><td> Feld 23</td></tr>" +
            "<tr><td> Feld 31</td><td> Feld 32</td><td> Feld 33</td></tr>" +
            "</table>";
*/
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
        top.add(btn1);
//    	End Sudoku-Lösungs-Strategien (NORTH)

//    	Ausgabefilter (SOUTH)
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(BottomPaneWidth,BottomPaneHeight));
		bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottom.setBorder(BorderFactory.createTitledBorder(BottomTitle));
		bottom.add(btnAllValue);
		btnAllValue.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
			    rhsPane.setText(MainControl.SudokuHilfeValue());
			  }
		} );
		bottom.add(btnNotOnlyAllowedNumber);
		btnNotOnlyAllowedNumber.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
			    rhsPane.setText(MainControl.SudokuHilfeNotAllowedNumbers());
			  }
		} );
		bottom.add(btnClear);
		btnClear.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
			    rhsPane.setText(MainControl.SudokuHilfeClear());
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
