import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public abstract class Examiner{

    private volatile CyclicBarrier cyclicBarrier;
    private volatile Semaphore semaphore;

    public Examiner(int numOfStudent){
        this.cyclicBarrier = new CyclicBarrier(numOfStudent);
        this.semaphore = new Semaphore(numOfStudent);
    }

    public void examine(Student student) throws InterruptedException, BrokenBarrierException {
        System.out.println("Student " + student.getStudentId() + " has arrived [Thread " + Thread.currentThread().getId() + "]");
        semaphore.acquire();
        System.out.println("Student " + student.getStudentId() + " is waiting for " + this.getClass().getName()
                + ", and " + getMinimumNumberOfStudents() + " needed to start [Thread " + Thread.currentThread().getId() + "]");
        cyclicBarrier.await();
        Thread.sleep(student.getPresentationTime());
        HomeworkPresentation.sumOfStudentsScore.getAndAdd(getRandomScore());
        HomeworkPresentation.fullyPresentedHomeworks.getAndIncrement();
        System.out.println("Student " + student.getStudentId() + " has been graded by " + this.getClass().getName()
                + " [Thread " + Thread.currentThread().getId() + "]");
        semaphore.release();
    }

    protected Integer getRandomScore(){
        Random random = new Random();

        return (random.nextInt(6) + 5);
    }

    private int getMinimumNumberOfStudents(){
        return cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting() - 1;
    }
}
