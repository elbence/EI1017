package exepciones;

public class NoDataException extends Exception {
    public NoDataException() {
        super("No hay datos");
    }
}
