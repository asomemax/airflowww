package airflowww;

public class Point {
	private int x;
	private int y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point o) {
		this.x = o.x;
		this.y = o.y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	// finding distance between two points using pythagorean theorem
	public double distance(Point o) {
		int dx = x - o.x;
		int dy = y - o.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	// returns the angle between two points in radians (range: [0, 2pi))
	public double getAngle(Point o) {
		// return Math.atan(Math.abs((x - o.getX()) / (y - o.getY())));
		double angle = 0.0;
		double oppSide = y - o.getY();	// opposite side
		double adjSide = x - o.getX();	// adjacent side
		angle = Math.abs(Math.atan(oppSide / adjSide));	
		// adjusting angle to achieve range [0, 2pi) 
		if (oppSide <= 0 && adjSide >= 0) {	// 2nd quadrant
			angle = Math.PI - angle; // finding the supplementary obtuse angle
		} else if (oppSide <= 0 && adjSide < 0) {	//3rd quadrant
			angle = Math.PI + angle;
		} else if (oppSide > 0 && adjSide < 0) {	// 4th quadrant
			angle = 2 * Math.PI - angle; 
		}
		return angle;
	}
	
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Point o) {
		return x == o.getX() && y == o.getY();
	}

}
