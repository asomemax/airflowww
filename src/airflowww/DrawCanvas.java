package airflowww;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

// creates Canvas
public class DrawCanvas extends JPanel {
	int[] xarrow = { 625, 497, 490, 375, 492, 496, 626 };
	int[] yarrow = { 293, 274, 380, 284, 173, 233, 244 };

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
		g.setColor(Color.ORANGE);
		Point[] Xsec = Controller.fig.getXsection();
		g.drawLine(Xsec[0].x, Xsec[0].y, Xsec[1].x, Xsec[1].y);
		g.setColor(Color.decode("#fffa00"));
		g.fillPolygon(xarrow, yarrow, xarrow.length - 1);
	}
	
}