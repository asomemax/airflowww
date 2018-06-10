package airflowww;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.FileNotFoundException;

import javax.swing.*; // Using Swing's components and containers
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// creates GUI window for drawing 
public class Draw extends JFrame {
	public static final int CANVAS_WIDTH = 1280; //640;
	public static final int CANVAS_HEIGHT = 960; //480;
	public static final int CENTER_X= CANVAS_WIDTH / 2;
	public static final int CENTER_Y = CANVAS_HEIGHT / 2;
	private boolean curdraw = false;
	private DrawCanvas canvas;
	private MouseAdapter adap;
	public boolean runSimulation = false;

	public Draw() {

		// Set up a panel for the buttons
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton btnDraw = new JButton("Draw Shape");
		btnPanel.add(btnDraw);
		JButton btnCenter = new JButton("Center");
		btnPanel.add(btnCenter);
		JButton btnSave = new JButton("Save File");
		btnPanel.add(btnSave);
		JButton btnLoad = new JButton("Load File");
		btnPanel.add(btnLoad);
		String[] foilList = { "Symmetric", "High Camber", "Flat" };
		JComboBox<String> foilOption = new JComboBox<>(foilList);
		btnPanel.add(foilOption);
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
		//Foil options
		foilOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(foilOption.getSelectedItem().equals("Symmetric")){
					System.out.println("Loading Sysmetric foil");
				}else if(foilOption.getSelectedItem().equals("High Camber")){
					System.out.println("Loading High camber");
				}else if(foilOption.getSelectedItem().equals("Flat")){
					System.out.println("Loading Flat Plate");
				}
				
			}
		});
		// saving
		btnPanel.add(new JLabel("Flow Speed"));
		JSpinner flowSpeedSpin = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(0, 100)));
		flowSpeedSpin.setPreferredSize(new Dimension(40, 20));
		flowSpeedSpin.setValue(0);
		btnPanel.add(flowSpeedSpin);
		
		// saving shape coordinates to file
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Controller.saveFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		// loading shape coordinates into program
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Controller.clearlist();
				try {
					Controller.readFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Controller.packShape();
				Controller.hasBeenPaintedatLeastOnce = true;
				angleSpin.setValue(0);
				Controller.setAng(0);
				requestFocus();
				canvas.repaint();
			}

		});
		
		// drawing shape
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!curdraw) {
					Controller.clearlist();
					curdraw = true;
					btnDraw.setText("Finish");
				} else {
					curdraw = false;
					btnDraw.setText("Draw Shape");
				}
				canvas.removeMouseListener(adap);
				adap = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						if (Controller.hasClosePoint(evt.getX(), evt.getY())[0] == 1) {
							Controller.removePoint(Controller.hasClosePoint(evt.getX(), evt.getY())[1]);
							canvas.repaint();
						} else {
							Controller.addPoint(evt.getX(), evt.getY());
							canvas.repaint();
						}
						Controller.packShape();
						Controller.hasBeenPaintedatLeastOnce = true;
					}
				};
				canvas.addMouseListener(adap);
				canvas.repaint();
				requestFocus();
			}
		});
		
		// running simulation
		btnRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Running simulator");
				// when button is clicked the state of the program will switch to start calculating
				if (runSimulation) {
					System.out.println("Stopping simulator");
					btnRun.setText("Run");
					runSimulation = false;
					//e.getActionCommand().equals(arg0)
				} else {
					System.out.println("Running simulator");
					btnRun.setText("Stop");
					runSimulation = true;
				}
			}

		});
		
		// rotates shape drawn
		angleSpin.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {
				Controller.setAng(Math.toRadians(Double.parseDouble(angleSpin.getValue().toString())));
				repaint();
			}
		});
		
		flowSpeedSpin.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {
				// TODO Auto-generated method stub
				int flowSpeed = (int) flowSpeedSpin.getValue();
				System.out.println("Current flow speed: " + flowSpeed);
				System.out.println("Flow speed updated");
			}

		});
		
		// centers shape drawn
		btnCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point centerOfMass = Controller.fig.findCenterOfMass();
				if ((int) centerOfMass.getX() != CENTER_X && (int) centerOfMass.getY() != CENTER_Y) {
					int deltaX = CENTER_X - (int) centerOfMass.getX();
					int deltaY = CENTER_Y - (int) centerOfMass.getY();
					Controller.fig.translate(deltaX, deltaY);
					repaint();
				}
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