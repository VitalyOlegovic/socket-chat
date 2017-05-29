package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import static util.Util.pref;
import static util.Util.stampa;

public class ServerRunnable implements Runnable {
    private ClientManager cm;
    private MainServer ms;
    private BufferedReader br;

    public ServerRunnable(MainServer ms, ClientManager cm, BufferedReader br){
        this.cm = cm;
        this.ms = ms;
        this.br = br;
    }

    @Override
    public void run() {
        try {
            String str = br.readLine();
            if(str!=null){
                eseguiComando(str);
            }
        }catch(SocketException se) {
            // Non fare nulla
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    void eseguiComando(String str) throws IOException {
        String[] tokens = str.split(" ");
        String comando = tokens[0];

        switch(comando){

            // comando uscita singolo thread
            case "/esci":
                ms.terminate(cm);
                break;

            // comando uscita tutto il server
            case "/stop":
                System.exit(0);
                break;

            // cambio nome client (nick)
            case "/nick":
                stampa( pref( MainServer.SERVER_NAME) + "CAMBIO NICK SU " + cm.getName() + " -> " + tokens[1] );
                ms.broadcast( pref( MainServer.SERVER_NAME ) + "CAMBIO NICK SU " + cm.getName() + " -> " + tokens[1] );
                cm.setName(tokens[1]);
                break;

            // nessun comando: solo stampare
            default:
                stampa(pref(cm.getName())+str);
                ms.broadcast(pref(cm.getName())+str);
                break;
        }

    }

}
