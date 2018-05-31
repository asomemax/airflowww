package airflowww;

public class Point {
	private double x;
	private double y;
	
	public Point() {
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point o) {
		this.x = o.x;
		this.y = o.y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	// finding distance between two points using pythagorean theorem
	public double distance(Point o) {
		double dx = x - o.x;
		double dy = y - o.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double getAngle(Point o) {
		return Math.atan(Math.abs(x - o.getX()) / (y - o.getY()));
	}
	
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

}
