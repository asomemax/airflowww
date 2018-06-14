package airflowww;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*; // Using Swing's components and containers
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// creates GUI window for drawing 
public class Draw extends JFrame {
	public final int CANVAS_WIDTH = 960; // 640; //1280;
	public final int CANVAS_HEIGHT = 720; // 480; //960;
	public final Point CENTER = new Point(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
	// public static final int CENTER_X= CANVAS_WIDTH / 2;
	// public static final int CENTER_Y = CANVAS_HEIGHT / 2;
	public boolean curdraw = false;
	public DrawCanvas canvas;
	public MouseAdapter adap;
	public boolean runSimulation = false;
	public int angle;	// angle of rotation
	public int width;	// airfoil width
	// private static double liftCoeff;
	// private static double dragCoeff;
	public double flowVelocity;
	public double atmoPressure; // atmospheric pressure
	
	public static JPanel btnPanel;
	public static JPanel varPanel;
	public static JPanel displayPanel;
	
	public Draw() {

		/////////////////////////// FOR
		/////////////////////////// BTNPANEL////////////////////////////////////////////////////////////////
		// set up a panel for the buttons
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

		/*
		 * btnPanel.add(new JLabel("Flow Speed")); JSpinner flowSpeedSpin = new
		 * JSpinner(new SpinnerListModel(Helperjunk.intsBetween(0, 100)));
		 * flowSpeedSpin.setPreferredSize(new Dimension(40, 20));
		 * flowSpeedSpin.setValue(0); btnPanel.add(flowSpeedSpin);
		 */

		/////////////////////////// FOR
		/////////////////////////// VARPANEL////////////////////////////////////////////////////////////////

		// set up a panel for variables you can modify
		JPanel varPanel = new JPanel(new FlowLayout());
		varPanel.setLayout(new BoxLayout(varPanel, BoxLayout.Y_AXIS)); // makes
																		// JPanel
																		// elements
																		// align
																		// vertically
		varPanel.add(new JLabel("Parameters: "));

		// angle
		varPanel.add(new JLabel("Angle (degrees): "));
		JPanel anglePanel = new JPanel(new FlowLayout());
		JSpinner angleSpin = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(-180, 180)));
		angleSpin.setPreferredSize(new Dimension(40, 20));
		angleSpin.setValue(0);
		varPanel.add(angleSpin);
		
		JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
		angleSlider.setMajorTickSpacing(90);
		angleSlider.setMinorTickSpacing(30);
		angleSlider.setPaintTicks(true);
		angleSlider.setPaintLabels(true);
		anglePanel.add(angleSlider);
		varPanel.add(anglePanel);

		angleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Controller.setAng(Math.toRadians(angleSlider.getValue()));
				//angleSpin.setValue((int) angle);
				repaint();
			}
		});
		
		// width of airfoil 
		varPanel.add(new JLabel("Airfoil width (m): "));
		JPanel widthPanel = new JPanel(new FlowLayout());

		JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);		
		widthSlider.setMajorTickSpacing(10);
		widthSlider.setMinorTickSpacing(5);
		widthSlider.setPaintTicks(true);
		widthSlider.setPaintLabels(true);
		
		widthPanel.add(widthSlider);
		varPanel.add(widthPanel);
		
		// lift coefficient (will use predefined experimental values)
		JPanel liftCoPanel = new JPanel(new FlowLayout());
		liftCoPanel.add(new JLabel("Lift coefficient: "));
		liftCoPanel.add(new JTextField("placeholder"));
		varPanel.add(liftCoPanel);

		// drag coefficient (will use predefined experimental values)
		JPanel dragCoPanel = new JPanel(new FlowLayout());
		dragCoPanel.add(new JLabel("Drag coefficient: "));
		dragCoPanel.add(new JTextField("placeholder"));
		varPanel.add(dragCoPanel);

		// density of fluid (changes based on altitude, temperature, and
		// humidity but this program won't account for that)
		JPanel densityPanel = new JPanel(new FlowLayout());
		densityPanel.add(new JLabel("Fluid density (kg/m^3): "));
		densityPanel.add(new JTextField("1.225")); // density of air at 15
													// degrees C and sea level
													// will be default value
		varPanel.add(densityPanel);

		// flow velocity
		JPanel flowSpeedPanel = new JPanel(new FlowLayout());
		flowSpeedPanel.add(new JLabel("Flow velocity (m/s): "));
		JSpinner flowSpeedSpin = new JSpinner(new SpinnerListModel(Helperjunk.intsBetween(0, 100)));
		flowSpeedSpin.setPreferredSize(new Dimension(40, 20));
		flowSpeedSpin.setValue(0);
		flowSpeedPanel.add(flowSpeedSpin);
		varPanel.add(flowSpeedPanel);
		
		// atmospheric pressure
		JPanel atmoPressPanel = new JPanel(new FlowLayout());
		atmoPressPanel.add(new JLabel("Atmospheric pressure (kPa): "));
		atmoPressPanel.add(new JTextField("101.325"));
		varPanel.add(atmoPressPanel);
		
		//////////////////////////// FOR
		//////////////////////////// DISPLAYPANEL////////////////////////////////////////////////////////////////

		// set up display panel for real-time calculations
		JPanel displayPanel = new JPanel(new FlowLayout());
		displayPanel.add(new JLabel("Calculations:"));

		// lift force
		JPanel liftForcePanel = new JPanel(new FlowLayout());
		liftForcePanel.add(new JLabel("Lift force (N): "));
		liftForcePanel.add(new JTextField("placeholder"));
		displayPanel.add(liftForcePanel);

		// drag force
		JPanel dragForcePanel = new JPanel(new FlowLayout());
		dragForcePanel.add(new JLabel("Drag force (N): "));
		dragForcePanel.add(new JTextField("placeholder"));
		displayPanel.add(dragForcePanel);

		// aerodynamic force
		JPanel aeroForcePanel = new JPanel(new FlowLayout());
		aeroForcePanel.add(new JLabel("Aerodynamic force (N): "));
		aeroForcePanel.add(new JTextField("placeholder"));
		displayPanel.add(aeroForcePanel);
		
		/*
		// angle of attack
		JPanel angleAtkPanel = new JPanel(new FlowLayout());
		angleAtkPanel.add(new JLabel("Angle of Attack: "));
		JTextField atkTextField = new JTextField("placeholder");
		displayPanel.add(angleAtkPanel);

		*/
		JPanel refAreaPanel = new JPanel(new FlowLayout());
		refAreaPanel.add(new JLabel("Reference area (m^2): "));
		refAreaPanel.add(new JTextField("placeholder"));
		displayPanel.add(refAreaPanel);

		////////////////////////////////////////BUTTON FUNCTIONALITY////////////////////////////////////////////////////

		// foil options
		// NOTE: predefined shapes should be automatically centered and a shape
		// must be drawn first in order to use this
		foilOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Controller.clearlist();
				if (foilOption.getSelectedItem().equals("Symmetric")) {
					System.out.println("Loading Symmetric foil");
					Controller.createSymmetricFoil();
				} else if (foilOption.getSelectedItem().equals("High Camber")) {
					System.out.println("Loading High camber");
					Controller.createHighCamberFoil();
				} else if (foilOption.getSelectedItem().equals("Flat")) {
					System.out.println("Loading Flat plate");
					Controller.createFlatPlate();
				}
				Controller.hasBeenPaintedatLeastOnce = true;
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
				//Controller.createForceVector();
				// when button is clicked the state of the program will switch
				// to start calculating
				if (runSimulation) {
					System.out.println("Stopping simulator");
					btnRun.setText("Run");
					runSimulation = false;
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
				double angle = Math.toRadians(Double.parseDouble(angleSpin.getValue().toString()));
				Controller.setAng(angle);
				//angleSlider.setValue((int) angle);
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
		canvas = new DrawCanvas(); // canvas is instantiated, the methods in
									// DrawCanvas will run
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
	
	public void updateValues() {
		Component[] varPanelArr = varPanel.getComponents();
		System.out.println(Arrays.toString(varPanelArr));
		angle = ((JSlider) varPanelArr[3]).getValue();
		width = ((JSlider) varPanelArr[5]).getValue();
		flowVelocity = Double.parseDouble((((JSpinner) varPanelArr[9]).getValue()).toString());
		atmoPressure = Double.parseDouble(((JTextField) varPanelArr[10]).getText());
	}
	public double getFlowVelocity() {
		return flowVelocity;
	}

	public double getAtmoPressure() {
		return atmoPressure; 
	}
}