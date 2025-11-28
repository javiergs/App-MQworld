import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.UUID;

/**
 * Subscribes to the MQTT broker to receive other players' positions
 *
 * @author javiergs
 * @version 1.0
 */
public class MySubscriber implements MqttCallback {
	
	private String CLIENT_ID = "brokerverse-subscriber-";
	private MqttClient client;
	
	public MySubscriber() {
		CLIENT_ID = CLIENT_ID + UUID.randomUUID();
		try {
			client = new MqttClient(Blackboard.BROKER, CLIENT_ID, new MemoryPersistence());
			MqttConnectOptions opts = new MqttConnectOptions();
			opts.setAutomaticReconnect(true);
			opts.setCleanSession(true);
			client.connect(opts);
			client.setCallback(this);
			client.subscribe(Blackboard.TOPIC);
			System.out.println("Subscriber connected: " + Blackboard.BROKER);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void messageArrived(String s, MqttMessage message) {
		String payload = new String(message.getPayload());
		Blackboard.getInstance().addPlayerFromPayload(payload);
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	
	}
	
	@Override
	public void connectionLost(Throwable throwable) {
	
	}
	
}
