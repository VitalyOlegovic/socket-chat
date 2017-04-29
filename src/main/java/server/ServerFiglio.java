package server;

import static util.Util.pref;
import static util.Util.riceviRiga;
import static util.Util.spedisciRiga;
import static util.Util.stampa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/***
 * 
 * @author Ezio Sperduto
 *
 */
public class ServerFiglio implements Runnable {
	
	private Socket socket;

	private boolean ripeti=true;
	private String name;

	public ServerFiglio(Socket s,String nome){
		socket=s;
		setName(nome);
	}
	
	@Override
	public void run() {

		stampa(pref(ServerCentrale.SERVER_NAME)+"ENTRA "+getName());
		spedisciAtutti(pref(ServerCentrale.SERVER_NAME)+"ENTRA "+getName());
		
		try {	
			BufferedReader input =     new BufferedReader(new InputStreamReader(socket.getInputStream()));		
			while(ripeti){
				String str = riceviRiga(input);
				if(str!=null){
					eseguiComando(str);
				}
				Thread.sleep(100);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		stampa(pref(ServerCentrale.SERVER_NAME)+"ESCE "+getName());
		spedisciAtutti(pref(ServerCentrale.SERVER_NAME)+"ESCE "+getName());

	}

	
	void eseguiComando(String str){
		
		String[] tokens = str.split(" ");
		
		String comando = tokens[0];
		
		switch(comando){
			
			// comando uscita singolo thread
			case "/esci":					
				ripeti=false;
				break;
			
			// comando uscita tutto il server
			case "/stop":			
				System.exit(0);
				break;
			
			// cambio nome client (nick)
			case "/nick":
				stampa(pref(ServerCentrale.SERVER_NAME)+"CAMBIO NICK SU "+getName()+" -> "+tokens[1]);
				spedisciAtutti(pref(ServerCentrale.SERVER_NAME)+"CAMBIO NICK SU "+getName()+" -> "+tokens[1]);
				setName(tokens[1]);
				break;
				
			// nessun comando: solo stampare
			default:						
				stampa(pref(getName())+str);
				spedisciAtutti(pref(getName())+str);	
				break;
		}

	}
	
	
	void spedisciAtutti(String str){
		for(ServerFiglio figlio:ServerCentrale.figli){
			figlio.manda(str);
		}
	}

	
	void manda(String str){
		try{
			spedisciRiga(socket.getOutputStream(),str);
		
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
