package robos;

import ambiente.*;
import interfaces.InterfaceMissao;

public abstract class AgenteInteligenteTerrestre extends RoboTerrestre{
    protected InterfaceMissao missao;
    
    public AgenteInteligenteTerrestre(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a,  int r_sensor){
        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);
        this.missao = null;
    }

    public void setMissao(InterfaceMissao m){
        this.missao = m;
    }

    public abstract void executarMissao(Ambiente a);

    public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    public boolean temMissao(){
        return this.missao != null;
    }
}
