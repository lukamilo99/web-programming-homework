import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Student implements Runnable {

    private Integer studentId;
    private Integer score;
    private Long arrivalTime;
    private Long presentationTime;
    private Examiner examiner;

    public Student(Integer studentId) {
        this.studentId = studentId;
        this.arrivalTime = getRandomNumber(0L, 1000L);
        this.presentationTime = getRandomNumber(500L, 1000L);
        examiner = assignExaminer();
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime);
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

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore(){
        return score;
    }
}
