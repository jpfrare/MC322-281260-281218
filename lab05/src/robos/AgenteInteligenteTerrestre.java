package robos;

import ambiente.*;
import interfaces.InterfaceMissao;
import interfaces.InterfaceRoboMissionario;

public class AgenteInteligenteTerrestre extends RoboTerrestre implements InterfaceRoboMissionario{
    protected InterfaceMissao missao;
    
    public AgenteInteligenteTerrestre(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a,  int r_sensor){
        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);
        this.missao = null;
    }

    public void setMissao(InterfaceMissao m){
        this.missao = m;
    }

    @Override public void executarMissao(String caminhoArquivo) {
        if(this.missao != null){
            this.missao.executar(this, caminhoArquivo);
        }
    }

    @Override public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    public boolean temMissao(){
        return this.missao != null;
    }

    @Override public String getDescricao(){
        return "Agente inteligente terrestre, possui capacidade de especializar missoes e altera-las.";
    }

    @Override public char getRepresentacao(){
        return 'T';
    }
}
