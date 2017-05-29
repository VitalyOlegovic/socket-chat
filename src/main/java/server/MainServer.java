package server;

import static util.Util.stampa;
import static util.Util.pref;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import util.Util;

public class MainServer {

	private Set<ClientManager> clientManagers = new HashSet<>();
	public final static String SERVER_NAME = "SERVER";
    private ServerSocket serverSocket = null;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    public static void main(String[] args){
        MainServer mainServer = new MainServer();
    }

	public MainServer(){
        init();
    }

    private void init(){
        stampa(pref(SERVER_NAME) + "START CHATSERVER!");

        try{
            serverSocket = new ServerSocket(Util.PORTA_SERVER);
            acceptIncomingClients();
        }catch(Exception e){
            stampa( pref(SERVER_NAME) + "Errore improvviso in MainServer:" );
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void acceptIncomingClients() throws IOException {
        while(true){
            Socket socket = serverSocket.accept();
            String clientName = "CLIENT_" + socket.getInetAddress().getHostAddress();
            ClientManager clientData = new ClientManager(socket,clientName, this);
            clientManagers.add(clientData);
        }
    }

    void broadcast(String str){
        for(ClientManager cm : clientManagers){
            PrintWriter pw = cm.getPrintWriter();
            pw.println(str);
        }
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void terminate(ClientManager cm) throws IOException {
        broadcast( pref( MainServer.SERVER_NAME) + "ESCE " + cm.getName() );
        cm.getSocket().close();
        clientManagers.remove(cm);
    }
}
