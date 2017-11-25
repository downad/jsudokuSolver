package sudoko_solver;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Zeichnung extends JPanel {

    @Override
     public void paintComponent(Graphics g) {
     super.paintComponent(g);
     g.drawLine(100, 100, 200, 200);

 }
}
