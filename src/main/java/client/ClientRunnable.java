package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientRunnable implements Runnable{

    private BufferedReader br;

    public ClientRunnable(BufferedReader input){
        this.br = input;
    }

    @Override
    public void run() {
        try {
            String str = br.readLine();
            if(str != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
