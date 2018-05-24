package airflowww;

public class Windtunnel {
	public double angle;
	public double power;
	public Figure arrow;

	public Windtunnel(Double angle) {
		this.angle = angle;
		power = 1;
		int[] x = { 0, 10, 10, 50, 50, 10, 10 };
		int[] y = { 30, 0, 25, 25, 35, 35, 60 };
		arrow = new Figure(x, y);

	}
}
