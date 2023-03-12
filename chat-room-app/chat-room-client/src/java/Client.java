import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_SOCKET_PORT = 9000;

    private Socket socket;

    private BufferedReader input;

    private PrintWriter output;

    private String username;

    public Client(String username){
        try {
            this.socket = new Socket("localhost", SERVER_SOCKET_PORT);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.username = username;

        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public void notifyServer(){
        output.println(username);
        Scanner scanner = new Scanner(System.in);

        while(socket.isConnected()){
            String message = scanner.nextLine();
            output.println(message);
        }
    }

    public void listenToServer(){
        Thread thread = new Thread(() -> {
            while(socket.isConnected()) {
                try {
                    System.out.println(input.readLine());
                } catch (IOException e) {
                    close();
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }

    private void close(){
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
