package airflowww;

import java.util.Arrays;

public class Figure {
	int numPoints;
	int[] xs;
	int[] ys;
	int px;
	int py;

	public Figure(int[] xs, int[] ys) {
		this.xs = xs;
		this.ys = ys;
		numPoints = xs.length;
	}

	public Figure() {
		int[] fx = { 0 };
		int[] fy = { 0 };
		xs = fx;
		ys = fy;
	}

	public void rotate(double theta) {
		System.out.println("Figure Rotating " + Math.toDegrees(theta) + " degrees");
		double midx = Helperjunk.average(getXs());
		double midy = Helperjunk.average(getYs());
		for(int i = 0; i < getXs().length;i++) {
			int dx = (int) (getXs()[i] - midx);
			int dy = (int) (getYs()[i] - midy);
			double pretheta = 
		}
	}

	public int[] getXs() {
		int[] ret = Arrays.copyOf(xs, xs.length);
		for (int i = 0; i < xs.length; i++) {
			ret[i] += px;
		}
		return ret;
	}

	public int[] getYs() {
		int[] ret = Arrays.copyOf(ys, xs.length);
		for (int i = 0; i < xs.length; i++) {
			ret[i] += px;
		}

		return ret;
	}

	// finds area of irregular polygon
	// reference: https://www.mathsisfun.com/geometry/area-irregular-polygons.html
	public double getArea() {
		assert (xs.length >= 3);
		double areaSum = 0.0;
		for (int i = 1; i < xs.length; i++) {
			double avgHeight = 0.5 * (ys[i - 1] + ys[i]);
			double base = xs[i - 1] * xs[i];
			areaSum += avgHeight * base; // finding area of trapezoid
		}
		areaSum += 0.5 * (ys[0] + ys[ys.length - 1]) * xs[0] * xs[xs.length - 1];
		return areaSum;
	}
}