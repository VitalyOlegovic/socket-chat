package server;

import static util.Util.stampa;
import static util.Util.pref;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import util.Util;

/***
 * 
 * @author Ezio Sperduto
 *
 */
public class ServerCentrale {

	public static Set<ServerFiglio> figli=new HashSet<>();
	private static int numFigli = 0;
	public final static String SERVER_NAME = "SERVER";
	
	public static void main(String...args){
		stampa(pref(SERVER_NAME)+"START CHATSERVER!");
		ServerSocket ss=null;

		try{
			ss = new ServerSocket(Util.PORTA_SERVER);
			while(true){
				Socket socket = ss.accept();

				numFigli++;
				String nomeFiglio = "CLIENT"+numFigli;
				ServerFiglio serverFiglio = new ServerFiglio(socket,nomeFiglio);
				figli.add(serverFiglio);

				new Thread(serverFiglio).start();			// lancio figlio
			}
		}catch(Exception e){
			stampa(pref(SERVER_NAME)+"Errore improvviso in ServerCentrale:");
			e.printStackTrace();
			System.exit(0);
		}finally{
			try{
				ss.close();
			}catch(IOException e){}			
		}
		stampa(pref(SERVER_NAME)+"STOP CHATSERVER!");
	}
}
