package robos;

import ambiente.*;
import interfaces.InterfaceMissao;
import interfaces.InterfaceRoboMissionario;

public class AgenteInteligenteTerrestre extends RoboTerrestre implements InterfaceRoboMissionario{
    protected InterfaceMissao missao; //tipo de missao que o robo se especializara
    
    public AgenteInteligenteTerrestre(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a,  int r_sensor){
        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);
        this.missao = null;
    }

    @Override public void executarMissao(String caminhoArquivo) {
        if(this.missao != null){
            this.missao.executar(this, caminhoArquivo);
        }
        else{
            System.out.println("Robo ainda nao se especializou. No menu interativo, primeiro especialize este Robo por meio da opcao 11.");
        }
    }

    @Override public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    @Override public boolean temMissao(){
        return this.missao != null;
    }

    @Override public String getDescricao(){
        return "Agente inteligente terrestre, possui capacidade de especializar missoes e altera-las.";
    }

    @Override public char getRepresentacao(){
        return 'T';
    }
}
