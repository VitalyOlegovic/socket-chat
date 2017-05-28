package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import util.Util;

public class ChatClient {

	private BufferedReader input;

	public ChatClient(String serverIp, int serverPort){
        init(serverIp, serverPort);
	}

	private void init(String serverIp, int serverPort){
        try(
                Socket s = new Socket( serverIp, serverPort)
        ) {
            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader( is );
            input = new BufferedReader( isr );
            ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(1);

            ClientRunnable cr = new ClientRunnable(input);
            e.scheduleAtFixedRate(cr, 100, 100, TimeUnit.MILLISECONDS);

            readAndPrint(s);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void readAndPrint(Socket s) throws IOException {
	    OutputStream os = s.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        Scanner scanner = new Scanner(System.in);
        while(true){
            String line = scanner.nextLine();
            pw.println(line);
        }
    }

	public static void main(String...a){
		String ipServer="127.0.0.1";

		ChatClient c = new ChatClient(ipServer, Util.PORTA_SERVER);
	}

}
