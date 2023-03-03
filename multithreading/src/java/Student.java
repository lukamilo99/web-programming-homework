import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Student implements Runnable {

    private Integer studentId;
    private Long sleepTime;
    private Long presentationTime;
    private Examiner examiner;

    public Student(Integer studentId) {
        this.studentId = studentId;
        this.sleepTime = getRandomNumber(0L, 1000L);
        this.presentationTime = getRandomNumber(500L, 1000L);
        examiner = assignExaminer();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime);
            examiner.examine(this);

        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("Examination ended, student interrupted");
        }
    }

    private long getRandomNumber(long low, long high){
        Random random = new Random();

        return (random.nextLong(high - low) + low + 1L);
    }

    private Examiner assignExaminer(){
        Random random = new Random();
        int number = random.nextInt(2);

        if(number == 1) return Professor.getProfessor();
        else return Assistant.getAssistant();
    }

    public Long getPresentationTime() {
        return presentationTime;
    }

    public Integer getStudentId() {
        return studentId;
    }
}
