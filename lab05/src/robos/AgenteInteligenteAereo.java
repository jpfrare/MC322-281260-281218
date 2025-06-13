package robos;

import ambiente.Ambiente;
import enums.TipoEntidade;
import interfaces.*;

public class AgenteInteligenteAereo extends RoboAereo  implements InterfaceRoboMissionario{
    protected InterfaceMissao missao; //tipo de missao que o robo se especializara
    
    public AgenteInteligenteAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, r_sensor);
        this.missao = null;
    }

    @Override public void executarMissao(String caminhoArquivo) {
        if(this.missao != null){
            this.missao.executar(this, caminhoArquivo);
        }
        else{
            System.out.println("Robo ainda nao se especializou. No menu interativo, primeiro especialize este Robo por meio da opcao 12.");
        }
    }

    @Override public void definirMissao(InterfaceMissao m){
        this.missao = m;
    }

    @Override public boolean temMissao(){
        return this.missao != null;
    }

    public void buscarPonto(int deltaX, int deltaY, int deltaZ){
        try {
            this.getAmbiente().dentroDosLimites(this.getX() + deltaX, this.getY() + deltaY, this.getZ() + deltaZ);
            this.getAmbiente().identifica_colisao(this.getX() + deltaX, this.getY() + deltaY, this.getZ() + deltaZ);
        } catch (Exception e) {
            System.err.println("Posicao final invalida");
            return;
        }
        int x0 = this.getX();
        int y0 = this.getY();
        int z0 = this.getZ();
        try{
            if(this.mover(deltaX, deltaY)){ 
                //Note que diferente do AereoDinamico, ele nao executa o melhor movimento
                //Primeiro ele se move horizontalmente, e por fim se move verticalmente
                //Esse movimento só e melhor em casos que ele ira descer, visto que uma altura inicial maior permite desviar de obstaculos
                //agora, caso ele suba, como o movimento horizontal ocorrera em uma altura menos, a probabilidade de nao conseguir desviar de um obstaculo e maior
                if(deltaZ > 0){
                    this.subir(deltaZ);
                }
                else{
                    this.descer(-deltaZ);
                }
            }
        }
        catch (Exception e){
            System.out.println("Caminho nao encontrado.");
            this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.VAZIO;
            this.setPosicaoX(x0);
            this.setPosicaoY(y0);
            this.setPosicaoZ(z0);
            this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.VAZIO;
        }

        
    }

    @Override public String getDescricao(){
        return "Agente inteligente aereo, possui capacidade de especializar missoes e altera-las.";
    }

    @Override public char getRepresentacao(){
        return 'A';
    }

}
