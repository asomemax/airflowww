package airflowww;

public class Windtunnel extends Figure { 
	public double angle;
	public double power;
	private final static int[] ARROW_X_COORD = new int[]{ 0, 10, 10, 50, 50, 10, 10 };
	private final static int[] ARROW_Y_COORD = new int[]{ 30, 0, 25, 25, 35, 35, 60 };

	public Windtunnel(Double angle) {
		super(ARROW_X_COORD, ARROW_Y_COORD);
		this.angle = angle;
		power = 1;
		/*
		int[] xa = { 0, 10, 10, 50, 50, 10, 10 }; //{ 5, 5, 70, 70, 105, 70, 70 }; //
		int[] ya = { 30, 0, 25, 25, 35, 35, 60 }; //{ 220, 250, 250, 280, 230, 200, 220 };  //
		x = xa;
		y = ya;
		arrow = new Figure(x, y);
		*/
	}

	public Windtunnel() {
		this(0.0);
		/*
		this.angle = 0;
		int[] x =  { 0, 10, 10, 50, 50, 10, 10 }; // { 5, 5, 70, 70, 105, 70, 70 }; //
		int[] y = { 30, 0, 25, 25, 35, 35, 60 }; //{ 220, 250, 250, 280, 230, 200, 220 }; //
		this.x = x;
		this.y = y;
		arrow = new Figure(x, y);
		*/
	}
}