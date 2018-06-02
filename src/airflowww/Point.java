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
	
	// returns the angle between two points in radians ( range: [0, 2pi) )
	public double getAngle(Point o) {
		// return Math.atan(Math.abs((x - o.getX()) / (y - o.getY())));
		double angle = 0.0;
		angle = Math.atan((y - o.getY()) / (x - o.getX()));	// tan = opposite / adjacent
		if (x - o.getX() < 0 && y - o.getY() > 0) {	// 2nd quadrant
			angle = Math.PI - angle; // finding the supplementary obtuse angle
		} else if (x - o.getX() < 0 && y - o.getY() < 0) {	//3rd quadrant
			angle = Math.PI + angle;
		} else if (x - o.getX() > 0 && y - o.getY() < 0) {	// 4th quadrant
			angle = 1.5 * Math.PI + angle; 
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
