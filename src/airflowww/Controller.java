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
	static String input;
	static Draw window;
	static ArrayList<Integer> xs;
	static ArrayList<Integer> ys;
	static Windtunnel wind;
	public static boolean hasBeenPaintedatLeastOnce;
	public static boolean airHasBeenPlacedAtLeastOnce;

	public static void main(String[] args) {
		fig = new Figure();
		status = "none";
		wind = new Windtunnel();
		window = new Draw();
		xs = new ArrayList<Integer>();
		ys = new ArrayList<Integer>();
		window.repaint();
		hasBeenPaintedatLeastOnce = false;
		airHasBeenPlacedAtLeastOnce = false;
	}

	private static void loadFile(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner sc = new Scanner(f);
		String input = "";
		while (sc.hasNext()) {
			input = sc.nextLine();
			String[] a = input.split(",");
			xs.add(Integer.parseInt(a[0]));
			ys.add(Integer.parseInt(a[1]));
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

	public static int[] arrayListToArray(ArrayList<Integer> a) {
		int[] b = new int[a.size()];
		for (int i = 0; i < a.size(); i++) {
			b[i] = a.get(i);
		}
		return b;
	}

	public static void packShape() {
		int[] x = arrayListToArray(xs);
		int[] y = arrayListToArray(ys);
		fig = new Figure(x, y);
		System.out.println("x's : " + Arrays.toString(fig.getXs()) + " y's : " + Arrays.toString(fig.getYs()));
		status = "shapeReady";
	}

	public static void changeStatus(String stat) {
		status = stat;
	}

	public static int[] getXs() {
		return fig.getXs();
	}

	public static int[] getYs() {
		return fig.getYs();
	}

	public static void addPoint(int x, int y) {
		System.out.println("AddedPoint");
		xs.add((int) x);
		ys.add((int) y);

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
		wind.rotate(theta);
	}
}