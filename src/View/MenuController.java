package View;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.List;

import MomRepository.MomRepository;

public class MenuController {
	MomRepository momRepository = new MomRepository();

	public MenuController() {
		try {
			momRepository.initializeMom();
		} catch (JMSException e) {
			System.out.println("erro ao inicializar momRepository");
			e.printStackTrace();
		}
	}

	public Destination createQueue(String queueName) {
		try {
			return momRepository.createQueue(queueName);
		} catch (JMSException e) {
			System.out.println("erro ao criar fila " + queueName);
			e.printStackTrace();
		}
		return null;
	}

	public void deleteQueue(String queueName) {
		try {
			momRepository.deleteQueue(queueName);
		} catch (JMSException e) {
			System.out.println("erro ao deletar fila " + queueName);
			e.printStackTrace();
		}

	}

	public String getListedQueuesAndItsAmountOfPendingMessages() {

		try {
			List<String> namesList = momRepository.getExistingQueuesNames();
			
			StringBuilder queueNames = new StringBuilder();
			
			namesList.forEach(queue -> {
				int amountOfPendingMessages = 0;
				try {
					amountOfPendingMessages = momRepository.getAmountOfPendingMessagesFromQueue(queue);
				} catch (JMSException e) {
					System.out.println("erro ao pegar quantidade de valores pendentes da fila" + queue);
					amountOfPendingMessages = 0;
					e.printStackTrace();

				}
				queueNames.append("NOME FILA: " + queue + ". \nMENSAGENS PENDENTES: " + amountOfPendingMessages + "\n\n");

			});
			
			
			return queueNames.toString();

		} catch (JMSException e) {
			System.out.println("erro ao listar filas ");
			e.printStackTrace();
			return "Erro ao listar filas";
		}
	}

	public Destination createTopic(String topicName) {
		try {
			return momRepository.createTopic(topicName);
		} catch (JMSException e) {
			System.out.println("erro ao criar Topico " + topicName);
			e.printStackTrace();
		}
		return null;
	}

	public void deleteTopic(String topicName) {
		try {
			momRepository.deleteTopic(topicName);
		} catch (JMSException e) {
			System.out.println("erro ao deletar Topico " + topicName);
			e.printStackTrace();
		}

	}

	public String getListedTopics() {

		try {
			List<String> namesList = momRepository.getExistingTopicNames();
			StringBuilder name = new StringBuilder();
			namesList.forEach(string -> name.append("TOPICO: "+string + "\n\n"));
			return name.toString();

		} catch (JMSException e) {
			System.out.println("erro ao listar topicos ");
			e.printStackTrace();
			return "Erro ao listar topicos";
		}
	}
}
