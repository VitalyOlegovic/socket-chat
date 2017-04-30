package server;

import static util.Util.stampa;
import static util.Util.pref;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import util.Util;

/***
 *
 * @author Ezio Sperduto
 * @author vitalij
 */
public class MainServer {

	private Set<ClientManager> clientManagers = new HashSet<>();
	public final static String SERVER_NAME = "SERVER";
    private ServerSocket serverSocket = null;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    public static void main(String... args){
        MainServer mainServer = new MainServer();
    }

	public MainServer(){
        init();
    }

    private void init(){
        stampa(pref(SERVER_NAME) + "START CHATSERVER!");

        try{
            serverSocket = new ServerSocket(Util.PORTA_SERVER);
            executor.schedule(this::acceptIncomingClients, 0, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            stampa( pref(SERVER_NAME) + "Errore improvviso in MainServer:" );
            e.printStackTrace();
            System.exit(0);
        }
    }

	private void acceptIncomingClients() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            String clientName = "CLIENT" + (clientManagers.size() + 1);
            ClientManager clientData = new ClientManager(socket,clientName, this);
            clientManagers.add(clientData);
            executor.schedule(this::acceptIncomingClients, 100, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void broadcast(String str){
        clientManagers.forEach( (ClientManager clientManager) -> clientManager.getPrintWriter().println(str) );
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }
}
