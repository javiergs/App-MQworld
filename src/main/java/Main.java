import javax.swing.*;
import java.awt.*;

/**
 * Main application class for the Brokerverse Swing Application.
 *
 * @author javiergs
 * @version 1.0
 */
public class Main extends JFrame {
	
	public Main() {
		WorldPanel worldPanel = new WorldPanel();
		this.add(worldPanel);
		// listener
		MyKeyListener keyListener = new MyKeyListener();
		worldPanel.addKeyListener(keyListener);
		worldPanel.setFocusable(true);
		worldPanel.requestFocusInWindow();
		Blackboard.getInstance().addPropertyChangeListener(worldPanel);

	}
	
	public static void main(String[] args) {
		Player me = new Player(args.length > 0 ? args[0] : "default", 400, 300, Color.BLUE);
		Blackboard.getInstance().setMe(me);
		SwingUtilities.invokeLater(() -> {
			Main main = new Main();
			main.setTitle("Brokerverse Swing Application");
			main.setSize(800, 600);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			main.setVisible(true);
		});
		MyPublisher publisher = new MyPublisher();
		Blackboard.getInstance().addPropertyChangeListener(publisher);
		MySubscriber subscriber = new MySubscriber();
	}
	
}
