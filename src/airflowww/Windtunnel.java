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
		int[] xa = { 5, 5, 70, 70, 105, 70, 70 }; //{ 0, 10, 10, 50, 50, 10, 10 }; //
		int[] ya = { 220, 250, 250, 280, 230, 200, 220 };  //{ 30, 0, 25, 25, 35, 35, 60 }; //
		x = xa;
		y = ya;
		arrow = new Figure(x, y);

	}

	public Windtunnel() {
		this.angle = 0;
		int[] x = { 5, 5, 70, 70, 105, 70, 70 }; //{ 0, 10, 10, 50, 50, 10, 10 }; // 
		int[] y = { 220, 250, 250, 280, 230, 200, 220 }; //{ 30, 0, 25, 25, 35, 35, 60 }; //
		this.x = x;
		this.y = y;
		arrow = new Figure(x, y);
	}
}