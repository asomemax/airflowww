package airflowww;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
	static String status;
	static Figure fig;
	static Figure flowArrow;
	static String input;
	static Draw window;
	static ArrayList<Double> xs;
	static ArrayList<Double> ys;
	public static boolean hasBeenPaintedatLeastOnce;
	public static boolean airHasBeenPlacedAtLeastOnce;
	static int flowSpeed;

	public static void main(String[] args) {
		fig = new Figure();
		status = "none";
		window = new Draw();
		xs = new ArrayList<Double>();
		ys = new ArrayList<Double>();
		hasBeenPaintedatLeastOnce = false;
		airHasBeenPlacedAtLeastOnce = false;
		window.repaint();
	}

	private static void loadFile(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner sc = new Scanner(f);
		String input = "";
		while (sc.hasNext()) {
			input = sc.nextLine();
			String[] a = input.split(",");
			xs.add(Double.parseDouble(a[0]));
			ys.add(Double.parseDouble(a[1]));
		}
		sc.close();
		
	}

	public static void readFile() throws FileNotFoundException {
		loadFile("pointslist.txt");
	}
	
	// making dialogue boxes reference so user can change file name and maybe where
	// it will be saved to:
	// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	// file will be saved as .txt
	public static void saveFile() throws FileNotFoundException {
		PrintStream output = new PrintStream(new File("t.txt"));
		for (int i = 0; i < xs.size(); i++) {
			output.println(xs.get(i) + "," + ys.get(i)); // output will be "<x_coord>,<y_coord>"
		}
		output.close();
	}
	
	// helper method
	public static double[] arrayListToArray(ArrayList<Double> a) {
		double[] b = new double[a.size()];
		for (int i = 0; i < a.size(); i++) {
			b[i] = a.get(i);
		}
		return b;
	}

	public static void packShape() {
		double[] x = arrayListToArray(xs);
		double[] y = arrayListToArray(ys);
		fig = new Figure(x, y);
		status = "shapeReady";
	}

	public static void changeStatus(String stat) {
		status = stat;
	}

	public static void addPoint(int x, int y) {
		System.out.println("AddedPoint");
		xs.add((double) x);
		ys.add((double) y);

	}

	public static void clearlist() {
		int t = xs.size();
		for (int i = 0; i < t; i++) {
			xs.remove(0);
			ys.remove(0);
		}
	}

	public static int[] hasClosePoint(int x, int y) {
		Point np = new Point(x, y);
		for (int i = 0; i < xs.size(); i++) {
			if (np.distance(xs.get(i), ys.get(i)) < 10) {
				int[] ret = { 1, i };
				return ret;
			}
		}
		int[] ret = { 0, 0 };
		return ret;
	}

	public static void removePoint(int i) {
		xs.remove(i);
		ys.remove(i);
	}

	public static void setAng(double theta) {
		fig.rotate(-theta);
	}
	
	// creates air flow direction arrow
	public static void createFlowArrow() {
		// NOTE: have to have last vertex two times to properly close shape for color fill
		double[] xArrow = { 100, 150, 150, 250, 250, 150, 150, 100};
		double[] yArrow = { 100, 50, 80, 80, 120, 120, 150, 100 };
		flowArrow = new Figure(xArrow, yArrow);
		flowArrow.rotate(Math.PI);
		
		Point target = new Point((int) (Draw.CANVAS_WIDTH * 0.15), Draw.CENTER.y);
		translateToTarget(flowArrow, target);
	}
	
	// NOTE: make sure everything is in double data type to reduce loss of information during calculations
	public static void createSymmetricFoil() {
		// Equation of top half of airfoil:
		// y = 5t[0.2969*sqrt(x) - 0.1260x - 0.3516x^2 + 0.2843x^3 - 0.1015x^4] bounds: [0, 1]
		// t is the maximum thickness as a fraction of the chord
		// reference: https://en.wikipedia.org/wiki/NACA_airfoil
		final int NUM_POINTS = 40;
		ArrayList<Double> xListSymFoil = new ArrayList<Double>();
		ArrayList<Double> yListSymFoil = new ArrayList<Double>();
		double x = 0.0;
		for (int i = 0; i < NUM_POINTS; i++) { 
			double y = 0.2969 * Math.sqrt(x) - 0.1260 * x - 0.3516 * Math.pow(x, 2) + 0.2843 
					* Math.pow(x, 3) - 0.1015 * Math.pow(x, 4);
			if (i < NUM_POINTS / 2) {
				xListSymFoil.add(x);
				yListSymFoil.add(y);
				x += 1.0 / (NUM_POINTS / 2);
			} else {
				y *= -1;
				xListSymFoil.add(x);
				yListSymFoil.add(y);
				x -= 1.0 / (NUM_POINTS / 2);
			}
		}
		xListSymFoil.add(0.0);
		yListSymFoil.add(0.0);
		double[] xSymFoil = arrayListToArray(xListSymFoil);
		double[] ySymFoil = arrayListToArray(yListSymFoil);
		fig = new Figure(xSymFoil, ySymFoil);
		fig.scale(500);
		translateToTarget(fig, Draw.CENTER);
	}
	
	public static void createHighCamberFoil() {
		// y = 5t[0.2969*sqrt(x) - 0.1260x - 0.3516x^2 + 0.2843x^3 - 0.1015x^4] bounds: [0, 1]
		// t is the maximum thickness as a fraction of the chord
		final int NUM_POINTS = 30;
		final double MAX_THICKNESS = 0.5;
		ArrayList<Double> xListHiCamber = new ArrayList<Double>();
		ArrayList<Double> yListHiCamber = new ArrayList<Double>();
		double x = 0.0;
		for (int i = 0; i < NUM_POINTS; i++) { 
			double y = 5 * MAX_THICKNESS * (0.2969 * Math.sqrt(x) - 0.1260 * x - 0.3516 * Math.pow(x, 2) + 0.2843 
					* Math.pow(x, 3) - 0.1015 * Math.pow(x, 4));
			if (i < NUM_POINTS / 2) {
				xListHiCamber.add(x);
				yListHiCamber.add(y);
				x += 1.0 / (NUM_POINTS / 2);
			} else {
				y *= -1;
				xListHiCamber.add(x);
				yListHiCamber.add(y);
				x -= 1.0 / (NUM_POINTS / 2);
			}
		}
		xListHiCamber.add(0.0);
		yListHiCamber.add(0.0);
		double[] xHiCamber = arrayListToArray(xListHiCamber);
		double[] yHiCamber = arrayListToArray(yListHiCamber);
		fig = new Figure(xHiCamber, yHiCamber);
		fig.scale(500);
		translateToTarget(fig, Draw.CENTER);
	}
	
	// creates a thin rectangle
	public static void createFlatPlate() {
		double[] xFlatPlate = {0, 50, 50, 0, 0 };
		double[] yFlatPlate = {0, 0, 10, 10, 0 };
		fig = new Figure(xFlatPlate, yFlatPlate);
		translateToTarget(fig, Draw.CENTER);
	}
	
	// helper method
	private static void translateToTarget(Figure shape, Point target) {
		Point centerOfMass = shape.findCenterOfMass();
		int deltaX = target.x - (int) centerOfMass.getX();
		int deltaY = target.y - (int) centerOfMass.getY();
		shape.translate(deltaX, deltaY);
	}
}