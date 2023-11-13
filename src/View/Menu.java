package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {
	
	MenuController menuController = new MenuController();

	private JFrame frame;
	private JTextField addFilaTextfield;
	private JTextField addTopicTextfield;
	private JTextField removeFilaTextfield;
	private JTextField removeTopicTextfield;
	private JTextField clientNameTextField;
	private JTextPane queuePanel;
	private JTextPane topicPanel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Menu window = new Menu();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	
	public Menu() {
		//initialize();
	}
	
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public void renderView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
					
					String panelContentQ = menuController.getListedQueuesAndItsAmountOfPendingMessages();
					queuePanel.setText(panelContentQ);
					
					String panelContentT = menuController.getListedTopics();
					topicPanel.setText(panelContentT);
					
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
		frame.setBounds(100, 100, 770, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 11, 734, 494);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel brokerManager = new JPanel();
		layeredPane.add(brokerManager, "name_12177437132000");
		brokerManager.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Broker Manager");
		lblNewLabel_1.setBounds(310, 11, 105, 14);
		brokerManager.add(lblNewLabel_1);
		
		JButton addQueueButton = new JButton("Adicionar Fila");
		addQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String queueName = addFilaTextfield.getText();
				menuController.createQueue(queueName);
				addFilaTextfield.setText("");
				
				String panelContent = menuController.getListedQueuesAndItsAmountOfPendingMessages();
				queuePanel.setText(panelContent);	
				
				
			}
		});
		addQueueButton.setBounds(10, 88, 131, 23);
		brokerManager.add(addQueueButton);
		
		addFilaTextfield = new JTextField();
		addFilaTextfield.setBounds(139, 89, 174, 22);
		brokerManager.add(addFilaTextfield);
		addFilaTextfield.setColumns(10);
		
		JButton addTopicButton = new JButton("Adicionar Topico");
		addTopicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topicName = addTopicTextfield.getText();
				menuController.createTopic(topicName);
				addTopicTextfield.setText("");
				
				String panelContent = menuController.getListedTopics();
				topicPanel.setText(panelContent);
			}
		});
		addTopicButton.setBounds(372, 88, 131, 23);
		brokerManager.add(addTopicButton);
		
		addTopicTextfield = new JTextField();
		addTopicTextfield.setBounds(500, 89, 169, 20);
		brokerManager.add(addTopicTextfield);
		addTopicTextfield.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("FILAS");
		lblNewLabel_3.setBounds(141, 50, 73, 14);
		brokerManager.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("TOPICOS");
		lblNewLabel_4.setBounds(500, 50, 53, 14);
		brokerManager.add(lblNewLabel_4);
		
		JButton removeQueueButton = new JButton("Remover Fila");
		removeQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String queueName = removeFilaTextfield.getText();
				menuController.deleteQueue(queueName);
				removeFilaTextfield.setText("");
				
				String panelContent = menuController.getListedQueuesAndItsAmountOfPendingMessages();
				queuePanel.setText(panelContent);
			}
		});
		removeQueueButton.setBounds(10, 115, 131, 23);
		brokerManager.add(removeQueueButton);
		
		JButton removeTopicButton = new JButton("Remover Topico");
		removeTopicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topicName = removeTopicTextfield.getText();
				menuController.deleteTopic(topicName);
				removeTopicTextfield.setText("");
				
				String panelContent = menuController.getListedTopics();
				topicPanel.setText(panelContent);
			}
		});
		removeTopicButton.setBounds(372, 115, 131, 23);
		brokerManager.add(removeTopicButton);
		
		removeFilaTextfield = new JTextField();
		removeFilaTextfield.setBounds(139, 116, 174, 20);
		brokerManager.add(removeFilaTextfield);
		removeFilaTextfield.setColumns(10);
		
		removeTopicTextfield = new JTextField();
		removeTopicTextfield.setBounds(500, 116, 169, 20);
		brokerManager.add(removeTopicTextfield);
		removeTopicTextfield.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 149, 303, 219);
		brokerManager.add(scrollPane);
		
		queuePanel = new JTextPane();
		scrollPane.setViewportView(queuePanel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 149, 297, 218);
		brokerManager.add(scrollPane_1);
		
		topicPanel = new JTextPane();
		scrollPane_1.setViewportView(topicPanel);
		
		JButton initiateClient = new JButton("Inicializar Cliente");
		initiateClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clientId = clientNameTextField.getText();
				Client client = new Client(clientId);
				client.renderView();
				
				clientNameTextField.setText("");
			}
		});
		initiateClient.setBounds(153, 448, 148, 23);
		brokerManager.add(initiateClient);
		
		clientNameTextField = new JTextField();
		clientNameTextField.setBounds(296, 449, 201, 20);
		brokerManager.add(clientNameTextField);
		clientNameTextField.setColumns(10);
	}
	
	public void paralelThread() {
		(new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				while (true) {
					try {
						Thread.sleep(10000);
						String panelContentQ = menuController.getListedQueuesAndItsAmountOfPendingMessages();
						queuePanel.setText(panelContentQ);
						
						String panelContentT = menuController.getListedTopics();
						topicPanel.setText(panelContentT);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}).execute();
	}
}
