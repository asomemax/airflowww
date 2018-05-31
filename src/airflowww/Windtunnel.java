package airflowww;

public class Windtunnel {
	public double angle;
	public double power;
	public Figure arrow;
	int[] x;
	int[] y;

	public Windtunnel(Double angle) {
		this.angle = angle;
		power = 1;
		int[] xa = { 0, 10, 10, 50, 50, 10, 10 }; //{ 5, 5, 70, 70, 105, 70, 70 }; //
		int[] ya = { 30, 0, 25, 25, 35, 35, 60 }; //{ 220, 250, 250, 280, 230, 200, 220 };  //
		x = xa;
		y = ya;
		arrow = new Figure(x, y);

	}

	public Windtunnel() {
		this.angle = 0;
		int[] x =  { 0, 10, 10, 50, 50, 10, 10 }; // { 5, 5, 70, 70, 105, 70, 70 }; //
		int[] y = { 30, 0, 25, 25, 35, 35, 60 }; //{ 220, 250, 250, 280, 230, 200, 220 }; //
		this.x = x;
		this.y = y;
		arrow = new Figure(x, y);
	}
}