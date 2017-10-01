package chatserver;

import java.io.IOException;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class chatserver {
	static ArrayList<User> users = new ArrayList<User>(10);
	static int port = 1111;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// Starting server
		ServerSocket servSock;
		try {
			servSock = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Can't start server");
			return;
		}
		System.out.println("Server started");
		// Accepting new users
		while (true) {
			try {
				Socket newConnection = servSock.accept();
				User u = new User(newConnection);
				u.start();
			} catch (IOException e) {
				System.err.println("ERR: Can't connect to user: " + e.getMessage());
			}
		}
	}

	synchronized static void removeUser(User u) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u)) {
				users.remove(i);
				System.out.println("User removed");
				break;
			}
		}
	}

	synchronized static boolean usernameIsFree(String username) {
		boolean result = true;
		for (User u : users) {
			if (u.username.equals(username)) {
				result = false;
				break;
			}
		}
		return result;
	}

	synchronized static void sendToAll(String message) throws IOException {
		for (User u : users) {
			u.send(message);
		}
	}
}