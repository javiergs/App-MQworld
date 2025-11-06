import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * WorldPanel visualizes all connected players.
 *
 * @author javiergs
 * @version 1.0
 */
public class WorldPanel extends JPanel implements PropertyChangeListener {
	
	public WorldPanel() {
		setBackground(new Color(172, 248, 199));
	}
	
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		drawGrid (g);
		drawMe(g);
		drawPlayers (g);
	}
	
	private void drawMe(Graphics g) {
		Blackboard.getInstance().getMe().draw(g);
	}
	
	private void drawPlayers(Graphics g) {
		for (Player player : Blackboard.getInstance().getPlayers()) {
			player.draw(g);
		}
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < getWidth(); x += 20) {
			for (int y = 0; y < getHeight(); y += 20) {
				g.drawRect(x, y, 20, 20);
			}
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (SwingUtilities.isEventDispatchThread()) {
			repaint();
		} else {
			SwingUtilities.invokeLater(this::repaint);
		}
	}
	
}