import java.awt.*;

/**
 * A player in the game with an ID, position, and color.
 *
 * @author javiergs
 * @version 1.0
 */
public class Player {
	
	private String id;
	private int x, y;
	private Color color;
	
	public Player(String id, int x, int y, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, 20, 20);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}