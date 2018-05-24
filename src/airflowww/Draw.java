package airflowww;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*; // Using Swing's components and containers

public class Draw extends JFrame {
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	private int x1 = CANVAS_WIDTH / 2;
	private boolean curdraw = false;
	private DrawCanvas canvas;
	private MouseAdapter adap;
	private boolean curwind = false;

	public Draw() {
		// Set up a panel for the buttons
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton btnDraw = new JButton("Draw Shape");
		btnPanel.add(btnDraw);
		JButton btnSave = new JButton("Save File");
		btnPanel.add(btnSave);
		JButton btnLoad = new JButton("Load File");
		btnPanel.add(btnLoad);
		JButton btnAirspawn = new JButton("Choose Airflow Direction");
		btnPanel.add(btnAirspawn);
		JButton btnRun = new JButton("Run");
		btnPanel.add(btnRun);

		// functionality to buttons
		btnRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
			System.out.println("Ran? Runned?");
				
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Controller.saveFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		btnLoad.addActionListener(new ActionListener() {

			// TODO Auto-generated method stub
			public void actionPerformed(ActionEvent evt) {
				Controller.clearlist();
				try {
					Controller.readFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Controller.packShape();
				canvas.repaint();
				Controller.hasBeenPaintedatLeastOnce = true;
				requestFocus();
			}

		});
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!curdraw) {
					Controller.clearlist();
					curdraw = true;
					btnDraw.setText("Finish");
					btnDraw.repaint();
					canvas.repaint();
				} else {
					curdraw = false;
					btnDraw.setText("Draw Shape");
				}
				canvas.removeMouseListener(adap);
				adap = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						if (Controller.hasClosePoint(evt.getX(), evt.getY())[0] == 1) {
							System.out.println("Close point");
							Controller.removePoint(Controller.hasClosePoint(evt.getX(), evt.getY())[1]);
						} else {
							Controller.addPoint(evt.getX(), evt.getY());
						}
						Controller.packShape();
						Controller.hasBeenPaintedatLeastOnce = true;
						canvas.repaint();
					}
				};
				canvas.addMouseListener(adap);
				canvas.repaint();
				requestFocus();
			}
		});
		btnAirspawn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (!curwind) {
					curwind = true;
					btnAirspawn.setText("Place airflow");
					canvas.repaint();
				} else {
					curwind = false;
					btnAirspawn.setText("Choose Airflow Direction");
				}
				canvas.removeMouseListener(adap);
				adap = new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						Point loc = new Point(evt.getX(), evt.getY());
						Point center = new Point(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
						Double ang = Helperjunk.getAngle(loc, center);
						Controller.setAirAng(ang);
						Controller.changeStatus("airReady");
						canvas.repaint();
					}
				};
				canvas.addMouseListener(adap);

			}
		});
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);

		// "super" JFrame fires KeyEvent
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					break;
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE //
		setTitle("Airflow Simulator 2018");
		pack();
		setVisible(true); // show it
		requestFocus(); // set the focus to JFrame to receive KeyEvent
	}

	/**
	 * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
	 */

	// The entry main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Draw(); // Let the constructor do the job
			}
		});
	}
}