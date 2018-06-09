package airflowww;

import java.awt.Point;
import java.util.Arrays;

public class Figure {
	double[] xs;
	double[] ys;
	int px;
	int py;
	double curAngle;

	public Figure(double[] xs, double[] ys) {
		this.xs = xs;
		this.ys = ys;
		curAngle = 0;
	}

	public Figure() {
		this(new double[] { 0.0 }, new double[]{ 0.0 });
	}

	/**
	 * Rotates figure to degree amount
	 */
	public void rotate(double theta) {
		double temp = theta;
		theta -= curAngle;
		curAngle = temp;
		double mx = Helperjunk.average(getXs());
		double my = Helperjunk.average(getYs());
		for (int i = 0; i < getXs().length; i++) {
			double distance = Math.hypot(mx - getXs()[i], my - getYs()[i]);
			double newAnglePerPoint = Math.asin((getYs()[i] - my) / distance);
			if (getXs()[i] - mx < 0) {
				newAnglePerPoint = Math.PI - newAnglePerPoint;
			}
			xs[i] = Math.cos(newAnglePerPoint + theta) * distance + mx;
			ys[i] = Math.sin(newAnglePerPoint + theta) * distance + my;
		}
	}

	public double[] getXs() {
		double[] ret = Arrays.copyOf(xs, xs.length);
		for (int i = 0; i < xs.length; i++) {
			ret[i] += px;
		}
		return ret;
	}

	public double[] getYs() {
		double[] ret = Arrays.copyOf(ys, xs.length);
		for (int i = 0; i < xs.length; i++) {
			ret[i] += px;
		}
		return ret;
	}

	public int[] getDisplayXs() {
		int[] ret = new int[xs.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) getXs()[i];
		}
		return ret;
	}

	public int[] getDisplayYs() {
		int[] ret = new int[ys.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) getYs()[i];
		}
		return ret;
	}

	/**
	 * finds area of irregular polygon
	 * reference:https://www.mathsisfun.com/geometry/area-irregular-polygons.html
	 */
	public double getArea() {
		assert(xs.length >= 3);
		double areaSum = 0.0;
		for (int i = 1; i < xs.length; i++) {
			double avgHeight = 0.5 * (ys[i - 1] + ys[i]);
			double base = xs[i - 1] * xs[i];
			areaSum += avgHeight * base; // finding area of trapezoid
		}
		areaSum += 0.5 * (ys[0] + ys[ys.length - 1]) * xs[0] * xs[xs.length - 1];
		return areaSum;
	}
	
	public double referenceArea() {
		return 0;
	}
	
	public Point findCenterOfMass() {
		int x = (int) this.average(xs);
		int y = (int) this.average(ys);
		return new Point(x, y);
	}
	
	// helper method
	private double average(int[] arr) {
		double avg = 0;
		for (int i = 0; i < arr.length; i++) {
			avg += arr[i];
		}
		avg /= arr.length;
		return avg;
	}
	
	// moves each coordinate of figure a set amount
	public void translate(int deltaX, int deltaY) {
		for (int i = 0; i < xs.length; i++) {
			xs[i] += deltaX;
			ys[i] += deltaY;
		}
	}
}