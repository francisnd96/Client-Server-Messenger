package CO2017.exercise3.fnd1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MessageServer {

	public MessageServer() {

	}
	
	//creation of a message board
	static MessageBoard board = new MessageBoard();
	
	//give a unique id to each client that connects to the server 
	static char clientID = 'A';
	
	//indicates when to increment the clientID
	static int counter = 0;
	
	public static void main(String[] args) throws IOException {
		int port = Integer.parseInt(args[0]);

		try (ServerSocket server = new ServerSocket(port)) {
			
			while (true) {
				if(counter > 1){
					clientID++;
				}
				//System.out.println("Waiting for client...");
				Socket client = server.accept();

				// get and display Fclient's IP address
				InetAddress clientAddress = client.getInetAddress();
				System.out.println("Starting Message server on port " + port);
				System.out.println("connection : " + clientAddress);
				
				//start a thread pool executor to execute the client threads
				ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newCachedThreadPool();
				tpe.execute(new MessageServerHandler(board, client));
				counter++;
				
			}

		}
	}
	
}
