package chatclient;

import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

class ClientGUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// For the system messages
	private static JPanel panel1 = new JPanel(new FlowLayout());
	private static JLabel status = new JLabel("Connecting to server...");
	// For the textbox
	private static JPanel panel2 = new JPanel(new BorderLayout());
	private static JTextArea chatText = new JTextArea();
	private static JScrollPane chatTextPane = new JScrollPane(chatText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	// For writing and sending messages
	private static JPanel panel3 = new JPanel(new FlowLayout());
	private static JTextField userInput = new JTextField(20);
	private static JButton sendButton = new JButton("Send");

	public void run() {
		// We are ready to go!
		this.configFrame();
		this.configPanel1();
		this.configPanel2();
		this.configPanel3();

		this.setVisible(true);
	}

	private void configFrame() {
		this.setLayout(new BorderLayout());
		this.setTitle("My Chat");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);
		this.pack();
		this.setSize(400, 300);
	}

	private void configPanel1() {
		// Adding the status to panel1
		ClientGUI.panel1.add(ClientGUI.status);
	}

	private void configPanel2() {
		// Configuring and adding the textbox to panel2
		ClientGUI.chatText.setLineWrap(true);
		ClientGUI.chatText.setEditable(false);
		ClientGUI.chatText.setRows(10);
		ClientGUI.panel2.add(ClientGUI.chatTextPane, BorderLayout.CENTER);
	}

	private void configPanel3() {
		// Adding the input field to panel3
		ClientGUI.panel3.add(userInput);

		// Adding the SEND button to panel3
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String text = ClientGUI.userInput.getText();
				if (text != null && !text.isEmpty()) {
					chatclient.c.sendMessage(text);
					if (text.equalsIgnoreCase("exit")) {
						chatclient.c.interrupt();
						ClientGUI.disableInputAndButton();
						ClientGUI.appendChatText("Bye bye!\n");
					}
				}
				ClientGUI.userInput.setText("");
			}
		});
		sendButton.setToolTipText("Send");
		panel3.getRootPane().setDefaultButton(sendButton);
		ClientGUI.panel3.add(sendButton);
	}

	static void autoScrollTextBox() {
		ClientGUI.chatText.setCaretPosition(ClientGUI.chatText.getDocument().getLength());
	}

	static void setStatusText(final String s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientGUI.status.setText(s);
			}
		});
	}

	static void appendChatText(final String s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientGUI.chatText.append(s + '\n');
				ClientGUI.autoScrollTextBox();
			}
		});
	}

	static void disableInputAndButton() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientGUI.userInput.setEnabled(false);
				ClientGUI.sendButton.setEnabled(false);
			}
		});
	}
}
