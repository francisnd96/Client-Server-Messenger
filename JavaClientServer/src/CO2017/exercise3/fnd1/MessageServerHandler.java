package CO2017.exercise3.fnd1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class MessageServerHandler implements Runnable {

	Writer out;
	BufferedReader in;
	Socket client;
	MessageBoard board;
	int i = 1;
	String result;

	public MessageServerHandler(MessageBoard b, Socket cl) {
		client = cl;
		board = b;
		try {
			//to write messages to the client
			out = new OutputStreamWriter(client.getOutputStream());
			//to recieve messages from the client 
			in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			//error message if connection I/O streams fail
			System.err.printf("Failed to create Data streams to %s%n", cl.getInetAddress());
			System.err.println(e);
			System.exit(1);
		}
	}

	public void run() {
		try {
			do {
				//read in message from the client
				result = in.readLine();
				//if it begins with "GET"
				if (result.startsWith("GET")) {
					//remove "GET:" to be left with just the message header
					String header = result.substring(4);
					//create a new MessageHeader object
					MessageHeader mh = new MessageHeader(header.charAt(0), Integer.parseInt(header.substring(2)));
					//if the message header exists
					if (board.GetMessage(mh) != null) {
						//write "OK:" and message to the client according to the protocol
						out.write(String.format("%s%n","OK:" + board.GetMessage(mh).toString()));
						out.flush();
						System.out.println(
								"GET:" + header + "\n" + header + "=" + board.GetMessage(mh).toString() + "\n" + "OK");
					} else {
						//if message header does not exist
						out.write(String.format("%s%n", "ERR"));
						out.flush();
						System.out.println("ERR");
						
					}

				} else if (result.equals("BYE")) {

					try {
						client.close();
					} catch (IOException se) {
						System.out.println("BYE");
					}
					//if request starts with send
				} else if (result.startsWith("SEND")) {
					System.out.println(result);
					//split result into 3 parts by the ":"
					String splitResult[] = result.split(":");
					//save that message to the message board
					board.SaveMessage(
							new MessageHeader(MessageServer.clientID, Integer.parseInt(splitResult[1])),
							splitResult[2]);
					//if request is "LIST"
				} else if (result.equals("LIST")) {
					System.out.println("LIST");
					//send all message headers back to client
					for(MessageHeader msg : board.ListHeaders()){
						out.write(msg.toString() + "\r\n");
						out.flush();
					}
					out.write("." + "\r\n");
					out.flush();
					
				}

				// System.out.print(temp);
				out.flush();
			} while (!(result.equals("BYE")));
			System.out.println("BYE");
			client.close();
			//handles abrupt termination
		} catch (NullPointerException e){
			System.out.println("Connection dropped unexpectedly.");
		} catch (IOException e) {
			System.err.println(e);

		}
	}
}
