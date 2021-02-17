package net.juanxxiii.threads;

import java.net.Socket;

public class ClientThread extends Thread {
    private Socket client;
    public ClientThread(Socket client){
        this.client = client;
    }
    @Override
    public void run() {

    }
}
