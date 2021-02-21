package net.juanxxiii.controller;

import net.juanxxiii.threads.ClientsListener;

import java.io.IOException;
import java.net.ServerSocket;

public class Controller {

    public static void main(String[] args) {
        new Controller().main();
    }

    public void main(){
        try {
            ServerSocket server = new ServerSocket(9090);
            ClientsListener clientsListener = new ClientsListener(server);
            clientsListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
