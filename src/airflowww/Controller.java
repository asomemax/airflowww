package airflowww;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
	static int status;
	static Figure fig;
	static String input;
	static Draw window;
	static ArrayList<Integer> xs;
	static ArrayList<Integer> ys;
	static Windtunnel wind;
	public static boolean hasBeenPaintedatLeastOnce;

	public static void main(String[] args) {
		fig = new Figure();
		status = 0;
		window = new Draw();
		wind = new Windtunnel();
		window = new Draw();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a point in this form x , y");
		xs = new ArrayList<Integer>();
		ys = new ArrayList<Integer>();
		xs.add(0);
		ys.add(0);
		int[] x = null;
		int[] y = null;
		x = arrayListToArray(xs);
		y = arrayListToArray(ys);
		fig = new Figure(x, y);
		System.out.println("x's : " + Arrays.toString(x) + " y's : " + Arrays.toString(y));
		status = 0;
		window.repaint();
		hasBeenPaintedatLeastOnce = false;
	}

	private static void parseFiles(ArrayList<Integer> xs, ArrayList<Integer> ys) throws FileNotFoundException {
		File f = new File("pointslist.txt");
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
		parseFiles(xs, ys);
	}
	
	// making dialogue boxes reference so user can change file name and maybe where it will be saved to:
	// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	// file will be saved as .txt
	public static void saveFile() throws FileNotFoundException{
		// creating a new file
		PrintStream output = new PrintStream(new File("t.txt"));
		for (int i = 0; i < xs.size(); i++) {
			output.println(xs.get(i) + "," + ys.get(i));	// output will be "<x_coord>,<y_coord>"
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
			status = 1;
	}
	
	public static void changeStatus(int stat) {
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
	
	public static void setAirAng(Double ang) {
		wind = new Windtunnel(ang);
	}
}
