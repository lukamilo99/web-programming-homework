
public class Professor extends Examiner{

    private static Professor professor;

    public Professor(int numOfStudent) {
        super(numOfStudent);
    }

    public static Professor getProfessor(){
        if(professor == null){
            professor = new Professor(2);
        }
        return professor;
    }
}
