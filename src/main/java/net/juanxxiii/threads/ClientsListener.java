package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsListener extends Thread{
    private final ServerSocket serverSocket;
    private static final List<ClientThread> clientThreadList = new ArrayList<>();
    private static final List<String> usernames = new ArrayList<>();
    private static final List<String> disconnectedUsers = new ArrayList<>();

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
        disconnectedUsers.remove(username);
        usernames.add(username);
        sendUserList("connected");
    }

    private void sendUserList(String state) {
        Message connection = new Message();
        connection.setUsername(state);
        connection.setMessage("");
        usernames.forEach(user -> {
            connection.setMessage(connection.getMessage().concat(user.concat("\n")));
        });
        connection.setMessage(connection.getMessage().concat("-"));
        disconnectedUsers.forEach(user -> {
            connection.setMessage(connection.getMessage().concat(user.concat("\n")));
        });
        System.out.println(connection.getMessage());
        sendMessageToAll(connection);
    }

    public void removeUsername(String message) {
        usernames.remove(message);
        disconnectedUsers.add(message);
        sendUserList("disconnected");
    }
}
