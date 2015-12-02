package Refrigerator_Client;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole {
	private final static String DEFAULT_HOST = "localhost";
	private final static int DEFAULT_PORT = 31337;
	public static void main(String[] args) {
		String host = null;
		int port = 0;
		try {
			host = args[0];
			port = Integer.parseInt(args[1]); // The port number
		} catch (Exception e) {
			if(host == null) host = DEFAULT_HOST;
			port = DEFAULT_PORT;
		}

		Packet chat = new Packet(host, port);
		chat.accept(); // Wait for console data
	}

}
// End of ConsoleChat class
