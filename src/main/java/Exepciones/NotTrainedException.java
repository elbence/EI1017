package Exepciones;

public class NotTrainedException extends Exception{
    public NotTrainedException(){
        super("Debes ejecutar primero el train");
    }
}
