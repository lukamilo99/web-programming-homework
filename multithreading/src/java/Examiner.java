import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public abstract class Examiner{

    private CyclicBarrier cyclicBarrier;
    private Semaphore semaphore;

    public Examiner(int numOfStudent){
        this.cyclicBarrier = new CyclicBarrier(numOfStudent);
        this.semaphore = new Semaphore(numOfStudent);
    }

    public void examine(Student student) throws InterruptedException, BrokenBarrierException {

        printMessage(student, "arrival");
        semaphore.acquire();
        printMessage(student, "wait");
        cyclicBarrier.await();

        Thread.sleep(student.getPresentationTime());

        Integer score = getRandomScore();
        student.setScore(score);
        HomeworkPresentation.sumOfStudentsScore.getAndAdd(score);
        HomeworkPresentation.fullyPresentedHomeworks.getAndIncrement();

        printMessage(student, "grade");
        semaphore.release();
    }

    protected Integer getRandomScore(){
        Random random = new Random();

        return (random.nextInt(6) + 5);
    }

    private int getMinimumNumberOfStudents(){
        return cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting() - 1;
    }

    private void printMessage(Student student, String command){
        switch (command) {
            case "arrival" -> System.out.println("Student " + student.getStudentId() + " has arrived after " + student.getArrivalTime()
                    + " ms " + "[Thread " + Thread.currentThread().getId() + "]");

            case "wait" -> System.out.println("Student " + student.getStudentId() + " is waiting for " + this.getClass().getName()
                    + ", and " + getMinimumNumberOfStudents() + " needed to start [Thread " + Thread.currentThread().getId() + "]");

            case "grade" -> System.out.println("Student " + student.getStudentId() + " has been graded " + student.getScore() +  " by " + this.getClass().getName()
                    + " after " + student.getPresentationTime() + " ms " + "[Thread " + Thread.currentThread().getId() + "]");

            default -> System.out.println("Unknown command");
        }
    }
}
