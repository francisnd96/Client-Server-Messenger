package CO2017.exercise3.fnd1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class MessageClient {

	// id to represent each message from a particular thread
	static int messageNo = 1;

	public MessageClient() {

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// must have two arguments, the host and the port or the program will
		// close
		if (args.length != 2) {
			System.err.println("Usage: java MessageClient <host> <port>");
			System.exit(1);
		}
		String servername = args[0];
		int port = Integer.parseInt(args[1]);

		try (Socket server = new Socket(servername, port)) {

			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream(), "UTF-8"));

			Writer out = new OutputStreamWriter(server.getOutputStream());

			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			String request = "BYE";

			do {
				//repeatedly prompt with question mark
				System.out.print("?");
				//where the user input is stored
				request = stdin.readLine();
				
				//if request starts with "SEND"
				if (request.startsWith("SEND")) {
					//write "SEND" + a message id + the message to be sent
					out.write(String.format("%s%n", "SEND:" + messageNo + ":" + request.substring(5)));
					//increment the message id counter
					messageNo++;
					//flush the stream
					out.flush();
				} else {
					//if the request starts with "GET" or "LIST" or "BYE"
					//write to server
					out.write(String.format("%s%n", request));
					out.flush();
				}

				
				
				//recieving messages
				if (!request.equals("BYE") && !request.startsWith("SEND")) {
					//if original request was "LIST"
					if (request.startsWith("LIST")) {
						String result;
						//continue to read lines from server and print them until a "." is reached
						//do not print the "."
						while ((result = in.readLine()) != null) {
							if (result.equals("."))
								break;
							else
								System.out.println(result);
						}
					} else {
						String result = in.readLine();
						//if original request does not exist
						if (result.equals("ERR"))
							System.out.println("No such message");
						//if original request was GET
						if (result.startsWith("OK"))
							System.out.println(result.substring(3));
						else
							System.out.println(result);
					}
				}
				
			} while (!(request.equals("BYE")));
			//if request is bye
			server.close();
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + servername);
			System.err.println(e);
			System.exit(1);
		} catch (SocketException e) {
			//catch exception and print error message when a server abruptly closes
			System.out.println("Server closed connection");
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}

}
