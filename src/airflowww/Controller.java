package airflowww;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
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
		// have to have last vertex two times to properly close shape for color fill
		double[] xArrow = { 100, 150, 150, 250, 250, 150, 150, 100};
		double[] yArrow = { 100, 50, 80, 80, 120, 120, 150, 100 };
		flowArrow = new Figure(xArrow, yArrow);
		flowArrow.scale(1);
		
		// moving flow arrow to center-right of canvas
		int centerRightX = (int) (Draw.CANVAS_WIDTH * 0.85);
		int centerRightY = Draw.CENTER_Y;
		Point centerOfMass = Controller.flowArrow.findCenterOfMass();
		int deltaX = centerRightX - (int) centerOfMass.getX();
		int deltaY = centerRightY - (int) centerOfMass.getY();
		flowArrow.translate(deltaX, deltaY);
	}
}