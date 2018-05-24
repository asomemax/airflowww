package airflowww;

public class Figure {
	int numPoints;
	int[] xs;
	int[] ys;

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
		
	}

	public int[] getXs() {

		return this.xs;
	}

	public int[] getYs() {

		return this.ys;
	}

}
