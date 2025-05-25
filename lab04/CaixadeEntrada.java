import java.util.ArrayList;

public class CaixadeEntrada {
    private final ArrayList<Mensagem> mensagem;
    private int nao_lidas;

    public CaixadeEntrada(){
        this.mensagem = new ArrayList<>();
        this.nao_lidas = 0;
    }

    public void armazenar_mensagem(Mensagem armazenar){
        this.mensagem.add(armazenar);
        this.nao_lidas++;
    }

    public void ler_mensagem(){
        if(mensagem.size() > 0 && nao_lidas > 0){
            System.out.println(mensagem.get(mensagem.size() - this.nao_lidas));
            this.nao_lidas--;
        }
    }

    public void esvaziar_caixa(){
        while(this.nao_lidas > 0){
            this.ler_mensagem();
        }
    }
}
