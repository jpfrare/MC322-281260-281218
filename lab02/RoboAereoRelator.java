import java.util.ArrayList;
public class RoboAereoRelator extends RoboAereo {
    //robo aereo capaz de identificar todos os robos que estao a uma altura abaixo dele e emitir um relatorio de sua posicao
    int n_relatorios;

    public RoboAereoRelator(int posicaoXo, int posicaoYo, int alt_o, int alt_max, String nome, Ambiente a, String direcao){
        super(posicaoXo, posicaoYo, alt_o, alt_max, nome, a, direcao);
        n_relatorios = 0; //numero de relatorios emitidos pelo robo
    }

    void gerar_relatorio(ArrayList<Robo> robos){
        //gera um relatorio do conjunto de robos (aereos ou terrestres) que estao a uma altura abaixo do RoboAereoRelator
        int altura_corte = this.getPosicaoZ(); //altura do proprio robo
        this.n_relatorios++;
        System.out.println("Relatorio numero " + this.n_relatorios + " do Robo " + this.getNome() + " realizado a uma altura " + this.getPosicaoZ());
        for(Robo r: robos){
            if(r.getPosicaoZ() < altura_corte){
                System.out.println("Robo " + r.getNome() + ":");
                r.exibirPosicao();
            }
        }
    }

    int getNrelatorios(){ //retorna a quantidade de relatorios feitos pelo robo
        return this.n_relatorios;
    }

    
}
