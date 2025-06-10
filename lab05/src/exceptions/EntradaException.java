package exceptions;

public class EntradaException extends Exception{

    public EntradaException(String mensagem) {
        super("Problema de Entrada. " + mensagem);
    }
    
}
