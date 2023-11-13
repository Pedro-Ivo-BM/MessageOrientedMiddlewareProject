package View;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.jms.*;

import MomRepository.MomRepository;
import MomRepository.SubscriberRepository;


public class ClientController {
	String clientId = "";

	MomRepository momRepository = new MomRepository();

	public ClientController() {
		try {
			momRepository.initializeMom();
		} catch (JMSException e) {
			System.out.println("erro ao inicializar momRepository");
			e.printStackTrace();
		}
	}

	public void sendMessageToClient(String destinyClientId, String message) {
		try {
			MessageProducer producer = momRepository.initializeMessageProducer(destinyClientId);
			TextMessage textMessage = momRepository.createTextMessage("CLIENTE " + clientId + ": " + message);
			producer.send(textMessage);
			momRepository.closeProducerOrPublisher(producer);
		} catch (JMSException e) {
			System.out.println("erro ao mandar mensagem para fila " + destinyClientId);
			e.printStackTrace();
		}

	}
	
	public boolean hasPendingMessages() {
		try {
			return momRepository.hasPendingMessagesFromQueue(clientId);
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("erro ao checar se há mensagens pendentes" );
			return false;
		}
		
	}

	public String receiveMessageFromQueue() {
		try {
			MessageConsumer consumer = momRepository.initializeClient(clientId);
			Message message = consumer.receive();

			momRepository.closeClient(consumer);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				return text;
			} else {
				return "";
			}

		} catch (JMSException e) {

			e.printStackTrace();
			return "";
		}

	}

	public List<String> receivePendingMessageFromQueue() {
		List<String> messages = new ArrayList<>();
		try {
			MessageConsumer consumer = momRepository.initializeClient(clientId);
			messages = momRepository.getAllPendingMessagesFromQueue(clientId, consumer);

			momRepository.closeClient(consumer);

			return messages;

		} catch (JMSException e) {
			List<String> messagesError = new ArrayList<>();
			e.printStackTrace();
			return messagesError;
		}

	}
	
	public void sendMessageToTopic(String topic, String message) {
		try {
			MessageProducer producer = momRepository.initializeMessagePublisher(topic);
			TextMessage textMessage = momRepository.createTextMessage("CLIENTE " + clientId + ": " + message);
			producer.send(textMessage);
			momRepository.closeProducerOrPublisher(producer);
		} catch (JMSException e) {
			System.out.println("erro ao mandar mensagem para topico " + topic);
			e.printStackTrace();
		}

	}
	
	public void subscribeToTopic(String topic, Consumer<String> reactToMessage) {
		SubscriberRepository subscriber = new SubscriberRepository();
		try {
			subscriber.createSubscription(topic, reactToMessage);
		} catch (JMSException e) {
			System.out.println("erro ao assinar topico " + topic);
			e.printStackTrace();
		}
		
	}
}
