import java.util.ArrayList;
public class RoboAereoRelator extends RoboAereo {
    //robo aereo capaz de identificar todos os robos que estao a uma altura abaixo dele e emitir um relatorio de sua posicao
    int n_relatorios;

    public RoboAereoRelator(int posicaoXo, int posicaoYo, int alt_o, int alt_max, String nome, Ambiente a){
        super(posicaoXo, posicaoYo, alt_o, alt_max, nome, a);
        n_relatorios = 0;
    }

    void gerar_relatorio(ArrayList<Robo> robos){
        int altura_corte = this.getPosicaoZ();
        this.n_relatorios++;
        System.out.println("Relatorio numero " + this.n_relatorios + " do Robo " + this.getNome() + " realizado a uma altura " + this.getPosicaoZ());
        for(Robo r: robos){
            if(r.getPosicaoZ() < altura_corte){
                System.out.println("Robo " + r.getNome() + " na posicao(" + r.getPosicaoX() +"," + r.getPosicaoY() + "," + r.getPosicaoZ() + ")");
            }
        }
    }

    
}
