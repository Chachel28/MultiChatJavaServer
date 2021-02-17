package net.juanxxiii.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientsListener extends Thread{
    private ServerSocket serverSocket;
    private List<ClientThread> clientThreadList;

    public ClientsListener(ServerSocket serverSocket, List<ClientThread> clientThreadList) {
        this.serverSocket = serverSocket;
        this.clientThreadList = clientThreadList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientThread clientThread = new ClientThread(client);
                clientThread.start();
                clientThreadList.add(clientThread);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
