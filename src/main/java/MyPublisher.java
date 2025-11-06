import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

/**
 * Publishes the player's position to the MQTT broker
 *
 * @author javiergs
 * @version 1.0
 */
public class MyPublisher implements PropertyChangeListener {
	
	private String CLIENT_ID = "brokerverse-publisher-";
	private int lastX = -1;
	private int lastY = -1;
	private MqttClient client;
	
	public MyPublisher() {
		CLIENT_ID = CLIENT_ID + UUID.randomUUID();
		try {
			client = new MqttClient(Blackboard.BROKER, CLIENT_ID, new MemoryPersistence());
			MqttConnectOptions opts = new MqttConnectOptions();
			opts.setAutomaticReconnect(true);
			opts.setCleanSession(true);
			client.connect(opts);
			System.out.println("Publisher connected: " + Blackboard.BROKER);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (lastX == Blackboard.getInstance().getMe().getX() &&
			lastY == Blackboard.getInstance().getMe().getY()) {
			return;
		}
		try {
			Player me = Blackboard.getInstance().getMe();
			String content = me.getId() + "," + me.getX() + "," + me.getY();
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(2);
			if (client != null && client.isConnected()) {
				client.publish(Blackboard.TOPIC, message);
				System.out.println("-- sending ->");
				lastX = Blackboard.getInstance().getMe().getX();
				lastY = Blackboard.getInstance().getMe().getY();
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
}