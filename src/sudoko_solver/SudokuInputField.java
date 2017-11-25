package sudoko_solver;

import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class SudokuInputField {

	private Font givenFont = new Font("SansSerif", Font.BOLD, 20);
	private Font font1 = new Font("SansSerif", Font.BOLD, 20);
	private JFormattedTextField InputField = new JFormattedTextField();
	private static int GridCount = 0;
	
    private void SudokuInputField() {
    	GridCount++;
    	System.out.println("GridCount = " + GridCount);
        NumberFormatter nf = new NumberFormatter();  
    	nf.setMinimum(new Integer(1));
    	nf.setMaximum((jsudokuSolver.MAXNUMBER+1));
    	DefaultFormatterFactory factory = new DefaultFormatterFactory(nf);
    	InputField = new JFormattedTextField(nf);
    	InputField.setFont(givenFont);
    	InputField.setColumns(3);
    	InputField.setText("5");
    	InputField.setSize(new Dimension(61, 46));
		/*
    	InputField.setHorizontalAlignment(JFormattedTextField.CENTER);
		InputField.setFont(givenFont);
		InputField.setColumns(3);
		InputField.setText("5");
		InputField.setSize(new Dimension(61, 46));
		InputField.addPropertyChangeListener(new PropertyChangeListener() {
        	@Override public void propertyChange(PropertyChangeEvent e) {
        		Object source = e.getSource();
        		
        			System.out.println("GridCount = " + SudokuInputField.GridCount); 
        		//if (SudokuInputField[i].isValid() == true) {
        		//	System.out.println("Value = " + SudokuInputField[i].getText() + " valid? " + SudokuInputField[i].isValid());
        		//}
        	}
		});
		*/
    }
    public JFormattedTextField getCell() {
    	return InputField;
    }
    
}
