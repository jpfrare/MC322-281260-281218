package robos;

import ambiente.Ambiente;
import interfaces.*;

public abstract class AgenteInteligenteAereo extends RoboAereo{
    protected InterfaceMissao missao;
    
    public AgenteInteligenteAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, r_sensor);
        this.missao = null;
    }

    public abstract void executarMissao(Ambiente a);

    public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    public boolean temMissao(){
        return this.missao != null;
    }


}
