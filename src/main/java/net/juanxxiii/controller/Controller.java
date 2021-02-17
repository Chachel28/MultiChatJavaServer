package net.juanxxiii.controller;

import net.juanxxiii.threads.ClientThread;
import net.juanxxiii.threads.ClientsListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ClientThread> clientList = new ArrayList<>();

    public static void main(String[] args) {
        new Controller().main();
    }

    public void main(){
        try {
            ServerSocket server = new ServerSocket(9090);
            ClientsListener clientsListener = new ClientsListener(server, clientList);
            clientsListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
