package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Util;

/***
 * 
 * @author Ezio Sperduto
 *
 */
public class ChatClient {

	
	public static void main(String...a){
		String ipServer="127.0.0.1";
		
		try(Socket s = new Socket( ipServer, Util.PORTA_SERVER)) {
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			
			pw.println("Prova");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
