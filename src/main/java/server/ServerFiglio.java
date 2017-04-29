package server;

import static util.Util.pref;
import static util.Util.stampa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 *
 * @author Ezio Sperduto
 * @author vitalij
 */
public class ServerFiglio implements Runnable {

	private Socket socket;
	private PrintWriter printWriter;
	BufferedReader input;

	private String name;

	public ServerFiglio(Socket s,String nome){
		socket=s;
		setName(nome);
	}

	@Override
	public void run() {

		stampa( pref( ServerCentrale.SERVER_NAME) + "ENTRA " + getName() );
		

		try {
			input = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			broadcast( pref( ServerCentrale.SERVER_NAME) + "ENTRA " + getName() );
			ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(1);
			e.scheduleAtFixedRate(this::readAndRun, 100, 100, TimeUnit.MILLISECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readAndRun(){
		try {
			String str = input.readLine();
			if(str!=null){
				eseguiComando(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void eseguiComando(String str){

		String[] tokens = str.split(" ");

		String comando = tokens[0];

		switch(comando){

			// comando uscita singolo thread
			case "/esci":
				break;

			// comando uscita tutto il server
			case "/stop":
				System.exit(0);
				break;

			// cambio nome client (nick)
			case "/nick":
				stampa( pref( ServerCentrale.SERVER_NAME) + "CAMBIO NICK SU " + getName() + " -> " + tokens[1] );
				broadcast( pref( ServerCentrale.SERVER_NAME ) + "CAMBIO NICK SU " + getName() + " -> " + tokens[1] );
				setName( tokens[1] );
				break;

			// nessun comando: solo stampare
			default:
				stampa(pref(getName())+str);
				broadcast(pref(getName())+str);
				break;
		}

	}


	void broadcast(String str){
		ServerCentrale.figli.forEach( (ServerFiglio sf) -> sf.printWriter.println(str) );
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
