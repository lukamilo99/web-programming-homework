import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeworkPresentation{

    public static AtomicInteger sumOfStudentsScore = new AtomicInteger(0);
    public static AtomicInteger fullyPresentedHomeworks = new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int numberOfStudents = scanner.nextInt();
        scanner.close();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 1; i < numberOfStudents + 1; i++) {
            executorService.execute(new Student(i));
        }

        try {
            Thread.sleep(5000);
            executorService.shutdownNow();

            System.out.println("Average score is: " + sumOfStudentsScore.doubleValue() / fullyPresentedHomeworks.doubleValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
