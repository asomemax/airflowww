// code from https://stumpygames.wordpress.com/2012/07/18/particles-tutorial-part-1-get-something-on-the-screen/

package airflowww;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
 
public class Particle {
 
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private Color color;
 
    public Particle(int x, int y, int dx, int dy, int size, int life, Color c){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.color = c;
    }
    
    public boolean update(){
        x += dx;
        y += dy;
        life--;
        if(life <= 0)
            return true;	// when particle is 'dead'
        return false;
    }
 
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
 
        g2d.setColor(color);
        g2d.fillRect(x-(size/2), y-(size/2), size, size);
 
        g2d.dispose();
    }
 
}
