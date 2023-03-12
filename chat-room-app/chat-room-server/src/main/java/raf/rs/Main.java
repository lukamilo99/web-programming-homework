package raf.rs;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final int SERVER_SOCKET_PORT = 9000;

    public static void main( String[] args ) {

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            Server server = new Server(serverSocket);

            server.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
