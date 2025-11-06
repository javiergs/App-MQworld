import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

/**
 * Blackboard is a singleton that holds the state of all players
 * in the game, including "me".
 *
 * @author javiergs
 * @version 1.0
 */
public class Blackboard extends PropertyChangeSupport {
	
	public static final String BROKER = "tcp://broker.hivemq.com:1883";
	public static final String TOPIC = "csc509/brokerverse";
	private Player me;
	private Vector<Player> players = new Vector<>();
	private static volatile Blackboard instance;
	
	private Blackboard() {
		super(new Object());
	}
	
	public static Blackboard getInstance() {
		if (instance == null) {
			synchronized (Blackboard.class) {
				if (instance == null) {
					instance = new Blackboard();
				}
			}
		}
		return instance;
	}
	
	public Vector<Player> getPlayers() {
		return players;
	}
	
	public Player getMe() {
		return me;
	}
	
	public void setMe(Player me) {
		this.me = me;
	}
	
	public void addPlayerFromPayload(String payload) {
		String[] parts = payload.split(",");
		if (parts.length == 3) {
			String id = parts[0];
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			if (id.equals(me.getId())) {
				return;
			}
			Player p = findOrCreate(id);
			p.setX(x);
			p.setY(y);
			System.out.println("<- received --");
			
			firePropertyChange("players", null, p);
		}
	}
	
	private Player findOrCreate(String id) {
		for (Player p : players) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		Player p = new Player(id, 0, 0, Color.RED);
		players.add(p);
		return p;
	}
	
	public void up() {
		me.setY(me.getY() - 10);
		firePropertyChange("me", null, me);
	}
	
	public void down() {
		me.setY(me.getY() + 10);
		firePropertyChange("me", null, me);
	}
	
	public void left() {
		me.setX(me.getX() - 10);
		firePropertyChange("me", null, me);
	}
	
	public void right() {
		me.setX(me.getX() + 10);
		firePropertyChange("me", null, me);
	}
	
}
