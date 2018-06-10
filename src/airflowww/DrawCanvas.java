package airflowww;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

// creates Canvas
public class DrawCanvas extends JPanel {
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.decode("#b3ffb3"));
		
		// drawing shape
		if (Controller.status.equals("shapeReady") || Controller.hasBeenPaintedatLeastOnce) {
			this.drawShape(g, Controller.fig.getDisplayXs(), Controller.fig.getDisplayYs());
		}
	}

	// draws shapes
	public void drawShape(Graphics g, int[] x, int[] y) {
		super.paintComponent(g);
		
		// for shape drawn
		g.setColor(Color.GRAY);
		g.fillPolygon(x, y, x.length);
		
		// for vertex points of shape drawn
		g.setColor(Color.RED);
		for (int i = 0; i < x.length; i++) {
			g.drawOval(x[i] - 5, y[i] - 5, 10, 10);
		}
		
		// for center of mass point of shape drawn
		g.setColor(Color.CYAN);
		if (x.length >= 3) {
			g.drawOval((int) Helperjunk.average(x), (int) Helperjunk.average(y), 7, 7);
		}
		
		// for the line connecting the highest and lowest vertexes of shape drawn
		g.setColor(Color.ORANGE);
		Point[] Xsec = Controller.fig.getXsection();
		g.drawLine(Xsec[0].x, Xsec[0].y, Xsec[1].x, Xsec[1].y);
		
		// for flow direction arrow
		Controller.createFlowArrow();
		int[] xArrow = Controller.flowArrow.getDisplayXs();
		int[] yArrow = Controller.flowArrow.getDisplayYs();
		int numPtsArrow = Controller.flowArrow.getDisplayXs().length - 1;
		g.setColor(Color.YELLOW);
		g.fillPolygon(xArrow, yArrow, numPtsArrow);
	}
	
}