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

public class ClientManager {

    private MainServer mainServer;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader input;

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
			ServerRunnable sr = new ServerRunnable(mainServer,this,  input);
			mainServer.getExecutor().scheduleAtFixedRate(sr, 100, 100, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
		return name;
	}

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
