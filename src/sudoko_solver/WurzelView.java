package sudoko_solver;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/** Die View-Klasse diese Enthält nur die Präsentation
 * hier sollte man keinerlei Programmlogik finden
 * alle Berechnungen und Reaktionen auf Benutzeraktionen
 * sollten allesamt im Controller stehen
 */
public class WurzelView extends JFrame{

    private JLabel lbl1 = new JLabel("Eingabe: ");
    private JTextField txtEingabe = new JTextField(3);
    private JButton cmdCalc = new JButton("Wurzen Berechnen >");
    private JTextField txtErg = new JTextField(5);
    private JButton cmdClear = new JButton("Zurücksetzen");

    public WurzelView(){
        super("Wurzel Berechnen");

        initForm();
    }

    /**
     * Die JForm initialisieren und alle Steuerelemente
     * darauf positionieren
     */
    private void initForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(200, 200, 500, 100);

        this.add(lbl1);
        this.add(txtEingabe);
        this.add(cmdCalc);
        this.add(txtErg);
        this.add(cmdClear);

    }

    public void resetView(){
        this.txtEingabe.setText("");
        this.txtErg.setText("");
    }

    public String getEingabe(){
        return this.txtEingabe.getText();
    }

    public void setErgebnis(String erg){
        this.txtErg.setText(erg);
    }

    /**
     * Funktionen bereitstellen, mit denen man später aus
     * dem Controller die nötigen Listener hinzufügen kann
     */
    public void setWurzelBerechnenListener(ActionListener l){
        this.cmdCalc.addActionListener(l);
    }

    public void setResetFormListener(ActionListener l){
        this.cmdClear.addActionListener(l);
    }
}
