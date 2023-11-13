package MomRepository;

import java.util.function.Consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SubscriberRepository implements MessageListener {
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	String subscriptionId;
	String lastMessage = "";
	Consumer<String> reactToMessage;

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	MessageConsumer subscriber;
	
	
	public SubscriberRepository() {
	}

	public void createSubscription(String topicName, Consumer<String> reactiToMessage) throws JMSException {
		subscriptionId = topicName;
		reactToMessage = reactiToMessage;
		
		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination dest = session.createTopic(topicName);

		subscriber = session.createConsumer(dest);
		System.out.println("chegou aqui antes");
		
		try {
		subscriber.setMessageListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onMessage(Message message) {
		System.out.println("chegou aqui");
		if (message instanceof TextMessage) {
			try {
				System.out.println(((TextMessage) message).getText());
				lastMessage = ((TextMessage) message).getText();
				reactToMessage.accept("TOPICO "+ subscriptionId +": \n" +lastMessage);
			} catch (Exception e) {
			}
		}

	}

	public void removeSubscription() throws JMSException {
		connection.close();
		session.close();
		subscriber.close();
	}

	

}
