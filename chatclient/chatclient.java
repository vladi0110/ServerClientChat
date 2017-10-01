package chatclient;

import javax.swing.SwingUtilities;

public class chatclient {
	final static String host = "localhost";
	final static int port = 1111;
	final static ClientThread c = new ClientThread(chatclient.host, chatclient.port);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClientGUI());
	}
}