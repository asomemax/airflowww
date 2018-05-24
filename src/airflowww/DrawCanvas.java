package airflowww;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.decode("#b3ffb3"));
		int[] xs = Controller.getXs();
		int[] ys = Controller.getYs();
		if (Controller.status == 1) {
			drawShape(g, xs, ys);
		}
		if (Controller.status == 3) {
			drawWindDir(g);
<<<<<<< HEAD
=======
			//git do you see this
			//i do - t
>>>>>>> c0539f6d43b1d61515c43203274f80c6e7024589
		}
	}
	
	// draws the wind direction, represented by an arrow
	public void drawWindDir(Graphics g) {
		g.drawPolygon(Controller.wind.arrow.getXs(), Controller.wind.arrow.getYs(),
				Controller.wind.arrow.getXs().length);
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
	}

}
