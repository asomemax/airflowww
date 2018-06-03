package airflowww;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.FileNotFoundException;

import javax.swing.*; // Using Swing's components and containers
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// creates GUI window for drawing 
public class Draw extends JFrame {
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	private int x1 = CANVAS_WIDTH / 2;
	private boolean curdraw = false;
	private DrawCanvas canvas;
	private MouseAdapter adap;

	public Draw() {

		// Set up a panel for the buttons
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton btnDraw = new JButton("Draw Shape");
		btnPanel.add(btnDraw);
		JButton btnSave = new JButton("Save File");
		btnPanel.add(btnSave);
		JButton btnLoad = new JButton("Load File");
		btnPanel.add(btnLoad);
		JButton btnRun = new JButton("Run");
		btnPanel.add(btnRun);

		btnPanel.add(new JLabel("Angle"));
		JSpinner angleSpin = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(-180, 180)));
		angleSpin.setPreferredSize(new Dimension(40, 20));
		angleSpin.setValue(0);
		btnPanel.add(angleSpin);

		btnPanel.add(new JLabel("Speed"));
		JSpinner flowSpeed = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(0, 100)));
		flowSpeed.setPreferredSize(new Dimension(40, 20));
		flowSpeed.setValue(0);
		btnPanel.add(flowSpeed);
		// saving
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Controller.saveFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		// loading
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
		// drawing shape
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
		btnRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Running simulator");
			}

		});
		angleSpin.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {
				System.out.println("Change angle to : " + Double.parseDouble(angleSpin.getValue().toString()));
				Controller.setAng(Math.toRadians(Double.parseDouble(angleSpin.getValue().toString())));
				repaint();
			}
		});

		flowSpeed.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println("Flow speed updated");
			}

		});
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE //
		setTitle("Airflow Simulator 2018");
		pack();
		setVisible(true); // show it
		requestFocus(); // set the focus to JFrame to receive KeyEvent
	}

}