package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import util.Util;

public class ChatClient {

	private BufferedReader input;

	public ChatClient(String serverIp, int serverPort){
		try(
		        Socket s = new Socket( serverIp, serverPort)
        ) {
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			input = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
			ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(1);

			ClientRunnable cr = new ClientRunnable(input);
			e.scheduleAtFixedRate(cr, 100, 100, TimeUnit.MILLISECONDS);

			Scanner scanner = new Scanner(System.in);
			while(true){
				String line = scanner.nextLine();
				pw.println(line);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String...a){
		String ipServer="127.0.0.1";

		ChatClient c = new ChatClient(ipServer, Util.PORTA_SERVER);
	}

}
