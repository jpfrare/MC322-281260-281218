package interfaces;
import exceptions.*;

public interface InterfaceComunicavel {
    public void enviarMensagem(InterfaceComunicavel destinatario, String mensagem) throws RoboDesligadoException;
    public void receberMensagem() throws RoboDesligadoException;
}
