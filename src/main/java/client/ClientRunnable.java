package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientRunnable implements Runnable{

    private BufferedReader input;

    public ClientRunnable(BufferedReader input){
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String str = input.readLine();
            if(str != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
