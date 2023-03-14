package raf.rs;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectedClient implements Runnable {

    private static CopyOnWriteArraySet<ConnectedClient> connectedClients = new CopyOnWriteArraySet<>();

    private Socket socket;

    private String username;

    private BufferedReader input;

    private PrintWriter output;

    private Database database;

    private Validator validator;

    public ConnectedClient(Socket socket){

        try {
            this.socket = socket;
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.username = input.readLine();

            while(true){
                if(connectedClients.add(this)) {
                    this.validator = new Validator();
                    this.database = new Database();
                    notifyCurrentClient(loadMessageHistory());
                    notifyClients(username + " has entered the chat room!");
                    break;
                } else {
                    output.println("Username already in use, enter another one.");
                    this.username = input.readLine();
                }
            }
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(socket.isConnected()) {
            try {
                String message = input.readLine();
                String formattedMessage = validator.getFormattedMessage(username, message);
                notifyClients(formattedMessage);
                database.addMessage(formattedMessage);

            } catch (IOException e) {
                close();
                throw new RuntimeException(e);
            }
        }
    }

    private void notifyClients(String message){
        for (ConnectedClient client: connectedClients){
            if (!client.getUsername().equals(username)){
                client.getOutput().println(message);
            }
        }
    }

    private void notifyCurrentClient(String message){
        output.println(message);
    }

    private String loadMessageHistory(){
        return database.getMessages();
    }

    private void removeConnectedClient(){
        connectedClients.remove(this);
    }

    private void close(){
        removeConnectedClient();

        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public PrintWriter getOutput() {
        return output;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) return false;
        if(!(object instanceof ConnectedClient)) return false;

        ConnectedClient connectedClient = (ConnectedClient) object;
        return Objects.equals(username, connectedClient.username);
    }
}
