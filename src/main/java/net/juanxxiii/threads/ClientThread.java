package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private final Socket client;
    private final ClientsListener clientsListener;
    private ObjectOutputStream objectOutputStream;

    public ClientThread(Socket client, ClientsListener clientsListener){
        this.client = client;
        this.clientsListener = clientsListener;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            Message message = (Message) objectInputStream.readObject();
            clientsListener.addUsername(message.getUsername());
            while ((message = (Message) objectInputStream.readObject()) != null) {
                if (message.getUsername().equals("disconnect")) {
                    clientsListener.removeUsername(message.getMessage());
                }else{
                    clientsListener.sendMessageToAll(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
