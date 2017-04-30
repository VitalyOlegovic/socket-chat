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
public class ClientManager {

    private MainServer mainServer;
	private Socket socket;
	private PrintWriter printWriter;
	BufferedReader input;

	private String name;

	public ClientManager(Socket s, String nome, MainServer mainServer){
	    this.mainServer = mainServer;
		socket = s;
		this.name = nome;
		init();
	}

	public void init() {
		stampa( pref( MainServer.SERVER_NAME) + "ENTRA " + getName() );

		try {
			input = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			mainServer.broadcast( pref( MainServer.SERVER_NAME) + "ENTRA " + getName() );
			mainServer.getExecutor().scheduleAtFixedRate(this::readAndRun, 100, 100, TimeUnit.MILLISECONDS);
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

	void eseguiComando(String str) throws IOException {
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
				stampa( pref( MainServer.SERVER_NAME) + "CAMBIO NICK SU " + getName() + " -> " + tokens[1] );
				mainServer.broadcast( pref( MainServer.SERVER_NAME ) + "CAMBIO NICK SU " + getName() + " -> " + tokens[1] );
				name = tokens[1];
				break;

			// nessun comando: solo stampare
			default:
				stampa(pref(getName())+str);
				mainServer.broadcast(pref(getName())+str);
				break;
		}

	}



	public String getName() {
		return name;
	}

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
