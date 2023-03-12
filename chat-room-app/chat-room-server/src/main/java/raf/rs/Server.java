package raf.rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void initialize(){
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Thread connectedClientTread = new Thread(new ConnectedClient(socket));
                connectedClientTread.start();
            } catch (IOException e) {
                close();
                throw new RuntimeException(e);
            }
        }
    }

    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
