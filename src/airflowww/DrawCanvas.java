package airflowww;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.decode("#b3ffb3"));
		if (Controller.status.equals("shapeReady") || Controller.hasBeenPaintedatLeastOnce) {
			drawShape(g, Controller.fig.getDisplayXs(), Controller.fig.getDisplayYs());
		}
	}

	// draws the shape of object to be tested on
	public void drawShape(Graphics g, int[] x, int[] y) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillPolygon(x, y, x.length);
		g.setColor(Color.RED);
		for (int i = 0; i < x.length; i++) {
			g.drawOval(x[i] - 5, y[i] - 5, 10, 10);
		}
		g.setColor(Color.CYAN);
		if (x.length >= 3) {
			g.drawOval((int) Helperjunk.average(x), (int) Helperjunk.average(y), 7, 7);
		}
	}

}