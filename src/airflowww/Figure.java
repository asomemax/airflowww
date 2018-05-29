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

	public void rotate(Double theta) {
		System.out.println("Figure Rotating " + Math.toDegrees(theta) + " degrees");
		double mx = Helperjunk.average(getXs());
		double my = Helperjunk.average(getYs());
		for (int i = 0; i < getXs().length; i++) {
			theta += Math.atan(getYs()[i] - my / getXs()[i] - mx);
			double dy = getYs()[i] - my;
			double dx = getXs()[i] - mx;
			double dhyp = Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2));
			dx = Math.sin(theta) * dhyp;
			dy = Math.cos(theta) * dhyp;
			xs[i] = (int) (dx + mx);
			ys[i] = (int) (dy + my);
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

}