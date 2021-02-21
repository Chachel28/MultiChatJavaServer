package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsListener extends Thread{
    private final ServerSocket serverSocket;
    private static List<ClientThread> clientThreadList = new ArrayList<>();
    private static List<String> usernames = new ArrayList<>();

    public ClientsListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientThread clientThread = new ClientThread(client, this);
                clientThreadList.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToAll(Message message) {
        clientThreadList.forEach(client -> client.sendMessage(message));
    }

    public void addUsername(String username) {
        usernames.add(username);
        sendUserList();
    }

    private void sendUserList() {
        Message connection = new Message();
        connection.setUsername("server");
        connection.setMessage("");
        usernames.forEach(user -> {
            connection.setMessage(connection.getMessage().concat(user.concat("\n")));
        });
        System.out.println(connection.getMessage());
        sendMessageToAll(connection);
    }

    public void removeUsername(String message) {
        usernames.remove(message);
        Message messageDisconnected = new Message();
        messageDisconnected.setUsername("disconnected");
        messageDisconnected.setMessage(message + "\n");
        usernames.forEach(user -> {
            messageDisconnected.setMessage(messageDisconnected.getMessage().concat(user.concat("\n")));
        });
        sendMessageToAll(messageDisconnected);
    }
}
