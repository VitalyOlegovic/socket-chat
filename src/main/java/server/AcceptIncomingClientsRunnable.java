package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AcceptIncomingClientsRunnable implements Runnable {

    private ServerSocket serverSocket;
    private Set<ClientManager> clientManagers;
    private MainServer mainServer;
    private ScheduledThreadPoolExecutor executor;

    public AcceptIncomingClientsRunnable(
            ServerSocket serverSocket, Set<ClientManager> clientManagers,
            MainServer mainServer, ScheduledThreadPoolExecutor executor){
        this.serverSocket = serverSocket;
        this.clientManagers = clientManagers;
        this.mainServer = mainServer;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            String clientName = "CLIENT" + (clientManagers.size() + 1);
            ClientManager clientData = new ClientManager(socket,clientName, mainServer);
            clientManagers.add(clientData);
            executor.schedule(this, 100, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
