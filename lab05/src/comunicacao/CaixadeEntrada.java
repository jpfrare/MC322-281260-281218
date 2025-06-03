package comunicacao;

import java.util.ArrayList;

public class CaixadeEntrada {
    private final ArrayList<Mensagem> mensagem;
    private int nao_lidas;

    public CaixadeEntrada(){
        this.mensagem = new ArrayList<>();
        this.nao_lidas = 0;
    }

    public int getNaoLidas(){
        return this.nao_lidas;
    }

    public void armazenar_mensagem(Mensagem armazenar){
        this.mensagem.add(armazenar);
        this.nao_lidas++;
    }

    public void ler_mensagem(){
        if(!mensagem.isEmpty() && nao_lidas > 0){
            System.out.println("Mensagem de " + mensagem.get(mensagem.size() - this.nao_lidas).getAutor().getNome() + ":");
            System.out.println(mensagem.get(mensagem.size() - this.nao_lidas).getMensagem());
            this.nao_lidas--;
        }
    }
}
