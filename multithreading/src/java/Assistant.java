
public class Assistant extends Examiner{

    private static Assistant assistant;

    public Assistant(int numOfStudent) {
        super(numOfStudent);
    }

    public static Assistant getAssistant(){
        if(assistant == null){
            assistant = new Assistant(1);
        }
        return assistant;
    }
}
