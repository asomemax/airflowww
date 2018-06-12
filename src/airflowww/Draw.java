package airflowww;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.FileNotFoundException;

import javax.swing.*; // Using Swing's components and containers
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// creates GUI window for drawing 
public class Draw extends JFrame {
	public static final int CANVAS_WIDTH = 640; //1280;
	public static final int CANVAS_HEIGHT = 480; //960;
	public static final Point CENTER = new Point(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
	//public static final int CENTER_X= CANVAS_WIDTH / 2;
	//public static final int CENTER_Y = CANVAS_HEIGHT / 2;
	private boolean curdraw = false;
	private DrawCanvas canvas;
	private MouseAdapter adap;
	public static boolean runSimulation = false;
	
	public Draw() {

		// set up a panel for the buttons
		JPanel btnPanel = new JPanel(new FlowLayout());
		
		// set up a panel for variables you can modify
		JPanel varPanel = new JPanel(new FlowLayout());
		
		// set up display panel for real-time calculations
		JPanel displayPanel = new JPanel(new FlowLayout());
		
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
		
		btnPanel.add(new JLabel("Flow Speed"));
		JSpinner flowSpeedSpin = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(0, 100)));
		flowSpeedSpin.setPreferredSize(new Dimension(40, 20));
		flowSpeedSpin.setValue(0);
		btnPanel.add(flowSpeedSpin);
		
		///////////////////////////FOR VARPANEL////////////////////////////////////////////////////////////////
		
		// angle slider
		varPanel.add(new JLabel("Angle (degrees)"));
		JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
		varPanel.add(angleSlider);
		
		angleSlider.setMajorTickSpacing(90);
		angleSlider.setMinorTickSpacing(30);
		angleSlider.setPaintTicks(true);
		angleSlider.setPaintLabels(true);
		
		angleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Controller.setAng(Math.toRadians(angleSlider.getValue()));
				repaint();
			}
		});
		
		////////////////////////////FOR DISPLAYPANEL////////////////////////////////////////////////////////////////
		
		displayPanel.add(new JLabel("Calculations:"));
		displayPanel.add(new JLabel("Lift force (N)"));
		displayPanel.add(new JLabel("Drag force (N)"));
		
		////////////////////////////////////////////////////////////////////////////////////////////
		
		// foil options
		// NOTE: predefined shapes should be automatically centered and a shape must be drawn first in order to use this
		foilOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Controller.clearlist();
				if(foilOption.getSelectedItem().equals("Symmetric")){
					System.out.println("Loading Symmetric foil");
					Controller.createSymmetricFoil();
				} else if(foilOption.getSelectedItem().equals("High Camber")){
					System.out.println("Loading High camber");
					Controller.createHighCamberFoil();
				} else if(foilOption.getSelectedItem().equals("Flat")){
					System.out.println("Loading Flat plate");
					Controller.createFlatPlate();
				}
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
		
		// centers shape drawn
		btnCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point centerOfMass = Controller.fig.findCenterOfMass();
				if ((int) centerOfMass.getX() != CENTER.x && (int) centerOfMass.getY() != CENTER.y) {
					int deltaX = CENTER.x - (int) centerOfMass.getX();
					int deltaY = CENTER.y - (int) centerOfMass.getY();
					Controller.fig.translate(deltaX, deltaY);
					repaint();
				}
			}
			
		});
		
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
		
		// running simulation
		// starts calculations and displays force vectors
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(runSimulation);
				Controller.createForceVector();
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
			public void stateChanged(ChangeEvent evt) {
				Controller.setAng(Math.toRadians(Double.parseDouble(angleSpin.getValue().toString())));
				repaint();
			}
		});
		
		// changes air flow speed
		flowSpeedSpin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				assert(flowSpeedSpin.getValue() != null);
				int flowSpeed = (int) flowSpeedSpin.getValue();
				Controller.flowSpeed = flowSpeed;
				System.out.println("Current flow speed: " + flowSpeed);
				System.out.println("Flow speed updated");
			}

		});
		
		/*
		 * TO-DO: Need to make a calculation panel to display results
		 */
		canvas = new DrawCanvas();	// canvas is instantiated, the methods in DrawCanvas will run
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);
		cp.add(varPanel, BorderLayout.EAST);
		cp.add(displayPanel, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE 
		setTitle("Airflow Simulator 2018");
		pack();
		setVisible(true); // show it
		requestFocus(); // set the focus to JFrame to receive KeyEvent
	}
	
	/*
	// updates calculations
	public void update() {
		while(true) {
			Calculate.liftForce(mass);
			Calculate.dragForce(massDensity, flowSpeed, dragCoeff, refArea);
		}
	}
	*/
}