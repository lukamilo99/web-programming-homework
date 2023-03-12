import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");

        String username = scanner.nextLine();
        Client client = new Client(username);
        client.listenToServer();
        client.notifyServer();
    }
}
