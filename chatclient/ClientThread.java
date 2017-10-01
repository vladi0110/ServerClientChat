package chatclient;

import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;

class ClientThread extends Thread {
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;

	public ClientThread(String host, int port) {
		try {
			this.socket = new Socket(host, port);
			ClientGUI.setStatusText("Connected!");
			this.in = new DataInputStream(this.socket.getInputStream());
			this.out = new DataOutputStream(this.socket.getOutputStream());
			this.start();
		} catch (IOException e) {
			ClientGUI.setStatusText("Can't connect to server");
			ClientGUI.disableInputAndButton();
			this.close();
		}
	}

	public void run() {
		try {
			String message;
			do {
				message = this.in.readUTF();
				ClientGUI.appendChatText(message);
			} while (!message.equalsIgnoreCase("exit"));
			ClientGUI.setStatusText("Disconnected");
		} catch (IOException e) {
			ClientGUI.setStatusText("Connection lost");
			ClientGUI.disableInputAndButton();
		} finally {
			this.close();
		}
	}

	void sendMessage(String message) {
		try {
			this.out.writeUTF(message);
			this.out.flush();
		} catch (IOException e) {
			ClientGUI.setStatusText("Can't send message");
			ClientGUI.disableInputAndButton();
			this.close();
		}
	}

	void close() {
		try {
			if (this.in != null)
				in.close();
			if (this.out != null)
				out.close();
			if (this.socket != null)
				socket.close();
		} catch (IOException e) {
			ClientGUI.setStatusText("Can't close socket: " + e.getMessage());
		}
	}
}
