package comunicacao;
import robos.*;

public class Mensagem {
    private final Robo autor;
    private final String mensagem;
    
    public Mensagem(Robo r, String texto){
        this.autor = r;
        this.mensagem = texto;
    }

    public Robo getAutor(){
        return this.autor;
    }

    public String getMensagem(){
        return this.mensagem;
    }
}
