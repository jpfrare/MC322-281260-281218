package robos;

import ambiente.Ambiente;
import interfaces.*;

public class AgenteInteligenteAereo extends RoboAereo  implements InterfaceRoboMissionario{
    protected InterfaceMissao missao;
    
    public AgenteInteligenteAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, r_sensor);
        this.missao = null;
    }

    @Override public void executarMissao(String caminhoArquivo) {
        if(this.missao != null){
            this.missao.executar(this, caminhoArquivo);
        }
    }

    @Override public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    @Override public boolean temMissao(){
        return this.missao != null;
    }

    @Override public String getDescricao(){
        return "Agente inteligente aereo, possui capacidade de especializar missoes e altera-las.";
    }

    @Override public char getRepresentacao(){
        return 'A';
    }

}
