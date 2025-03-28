import java.util.ArrayList;
public class RoboAereoRelator extends RoboAereo {
    private ArrayList<String> relatorios;

    public RoboAereoRelator(int posicaoXo, int posicaoYo, int alt_o, int alt_max, String nome, Ambiente a){
        super(posicaoXo, posicaoYo, alt_o, alt_max, nome, a);
        this.relatorios = new ArrayList<>();
    }

    void gerar_relatorio(ArrayList<Robo> robos){
        int altura_corte = this.getAltitude();
        for(Robo r: robos){
            if(r.getA)
        }
    }

    
}
