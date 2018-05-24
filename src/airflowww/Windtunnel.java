package airflowww;

public class Windtunnel {
	public double angle;
	public double power;	// velocity of the wind
	public Figure arrow;
	int[] x;
	int[] y;

	public Windtunnel(Double angle) {
		this.angle = angle;
		power = 1;
		int[] xa = { 0, 10, 10, 50, 50, 10, 10 };
		int[] ya = { 30, 0, 25, 25, 35, 35, 60 };
		x = xa;
		y = ya;
		arrow = new Figure(x, y);
	}

	public Windtunnel() {
		this.angle = 0;
		int[] x = { 0, 10, 10, 50, 50, 10, 10 };
		int[] y = { 30, 0, 25, 25, 35, 35, 60 };
		this.x = x;
		this.y = y;
		arrow = new Figure(x, y);
	}
}
