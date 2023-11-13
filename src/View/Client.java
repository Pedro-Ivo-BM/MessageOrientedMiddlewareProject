package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;



public class Client {

	ClientController clientController = new ClientController();

	private JFrame frame;
	private JTextField signTopicTextfield;
	private JTextField topicTextfield;
	private JTextField topicMessageTextfield;
	private JTextField clientTextField;
	private JTextField queueMessageTextfield;
	private JLabel ClientLabel;
	private JTextPane clientPane;
	private JTextPane topicPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Client window = new Client();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Client(String clientId) {
		clientController.clientId = clientId;
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void renderView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);

					ClientLabel.setText("Cliente: " + clientController.clientId);
					getPendingMessages();
					paralelThread();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 773, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		ClientLabel = new JLabel("Client");
		ClientLabel.setBounds(351, 11, 192, 14);
		frame.getContentPane().add(ClientLabel);

		JLabel lblNewLabel = new JLabel("TOPICOS");
		lblNewLabel.setBounds(123, 51, 46, 14);
		frame.getContentPane().add(lblNewLabel);

		JButton signTopicButton = new JButton("Assinar Topico");
		signTopicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topicName = signTopicTextfield.getText();
				clientController.subscribeToTopic(topicName, reactionToTopicMessage);
				signTopicTextfield.setText("");
			}
		});
		signTopicButton.setBounds(21, 78, 148, 23);
		frame.getContentPane().add(signTopicButton);

		signTopicTextfield = new JTextField();
		signTopicTextfield.setBounds(166, 76, 159, 26);
		frame.getContentPane().add(signTopicTextfield);
		signTopicTextfield.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 112, 266, 290);
		frame.getContentPane().add(scrollPane);

		topicPane = new JTextPane();
		scrollPane.setViewportView(topicPane);

		topicTextfield = new JTextField();
		topicTextfield.setBounds(146, 431, 198, 20);
		frame.getContentPane().add(topicTextfield);
		topicTextfield.setColumns(10);

		topicMessageTextfield = new JTextField();
		topicMessageTextfield.setBounds(146, 476, 198, 20);
		frame.getContentPane().add(topicMessageTextfield);
		topicMessageTextfield.setColumns(10);

		JButton sendMessageToTopicButton = new JButton("Enviar Mensagem\r\n");
		sendMessageToTopicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topicName = topicTextfield.getText();
				String message = topicMessageTextfield.getText();
				clientController.sendMessageToTopic(topicName, message);
				
				String previusText = topicPane.getText();

				if (previusText == null) {
					previusText = "";
				}

				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(previusText + "\n\n");
				stringBuilder.append("VOCÊ :"+message + "\n\n");

				String newText = stringBuilder.toString();
				topicPane.setText(newText);

				topicTextfield.setText("");
				topicMessageTextfield.setText("");
			}
		});
		sendMessageToTopicButton.setBounds(10, 431, 136, 65);
		frame.getContentPane().add(sendMessageToTopicButton);

		JLabel lblNewLabel_1 = new JLabel("Mensagem de/para outro Cliente");
		lblNewLabel_1.setBounds(529, 82, 174, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(484, 112, 246, 290);
		frame.getContentPane().add(scrollPane_1);

		clientPane = new JTextPane();
		scrollPane_1.setViewportView(clientPane);

		JButton sendMessageToQueueButton = new JButton("Enviar Mnesagem");
		sendMessageToQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clientDestinyId = clientTextField.getText();
				String message = queueMessageTextfield.getText();
				clientController.sendMessageToClient(clientDestinyId, message);

				String previusText = clientPane.getText();

				if (previusText == null) {
					previusText = "";
				}

				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(previusText + "\n\n");
				stringBuilder.append("VOCÊ para "+clientDestinyId+ ": " +message + "\n\n");

				String newText = stringBuilder.toString();
				clientPane.setText(newText);

				clientTextField.setText("");
				queueMessageTextfield.setText("");
			}
		});
		sendMessageToQueueButton.setBounds(407, 430, 136, 66);
		frame.getContentPane().add(sendMessageToQueueButton);

		clientTextField = new JTextField();
		clientTextField.setBounds(545, 431, 202, 20);
		frame.getContentPane().add(clientTextField);
		clientTextField.setColumns(10);

		queueMessageTextfield = new JTextField();
		queueMessageTextfield.setBounds(541, 476, 206, 20);
		frame.getContentPane().add(queueMessageTextfield);
		queueMessageTextfield.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Topico");
		lblNewLabel_2.setBounds(214, 413, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Client");
		lblNewLabel_3.setBounds(608, 413, 46, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("mensagem");
		lblNewLabel_4.setBounds(214, 456, 83, 14);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("mensagem");
		lblNewLabel_5.setBounds(608, 456, 83, 14);
		frame.getContentPane().add(lblNewLabel_5);
	}

	public void getPendingMessages() {
		List<String> pendingMessages = clientController.receivePendingMessageFromQueue();
		String previusText = clientPane.getText();

		if (previusText == null) {
			previusText = "";
		}

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(previusText);

		pendingMessages.forEach(message -> {

			stringBuilder.append(message + "\n\n");

		});

		String newText = stringBuilder.toString();

		clientPane.setText(newText);
	}
	
	Consumer<String> reactionToTopicMessage = (message) -> {
		String previusText = topicPane.getText();

		if (previusText == null) {
			previusText = "";
		}

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(previusText);

		stringBuilder.append(message + "\n\n");

		String newText = stringBuilder.toString();

		topicPane.setText(newText);
		
	};

	public void paralelThread() {
		(new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				while (true) {
					try {
						Thread.sleep(5000);
						boolean hasPendingMessages = clientController.hasPendingMessages();
						if (hasPendingMessages) {
							getPendingMessages();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}).execute();
	}
}
