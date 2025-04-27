public class RoboTerrestreTopeira extends RoboTerrestre {
    private final int profundidadeMax; //valor < 0

    public RoboTerrestreTopeira(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a, 
    int profundidadeMax, SensorMovimento sensor) {
        //construtor levando em consideração os novos atributos
        super(posicaoXo, posicaoYo, nome, velocidademax, a, sensor);
        this.profundidadeMax = profundidadeMax;
    }

    int getProfundidadeMax() {
        return this.profundidadeMax;
    }


    void mover(int deltaX, int deltaY, int deltaZ) {
        //verificando se a posição final está dentro dos limites
        if (!this.getAmbiente().dentroDosLimites(this.getPosicaoX() + deltaX, this.getPosicaoY() + deltaY, 0) || 
        this.getPosicaoZ() + deltaZ < this.profundidadeMax || this.getPosicaoZ() + deltaZ > 0) {
            System.out.println("Movimento inválido");
            return;
        }
       
        if (Math.abs(deltaX) >= this.getVelocidademax() || Math.abs(deltaY) >= this.getVelocidademax() || Math.abs(deltaZ) >= this.getVelocidademax()) {
            System.out.println("Movimento inválido");
            return;
        }

        if (this.getPosicaoZ() + deltaZ == 0 && this.getAmbiente().identifica_colisao(this.getPosicaoX() + deltaX,
        this.getPosicaoY() + deltaY, 0)) {
            //a única condição que deve-se ter cuidado, pois o robô topeira pode emergir em um lugar ocupado por obstáculo
            System.out.println("Movimento inválido");
            return;

        } else {
            /*por amor a simplicidade e sabendo que não existe um obstáculo no subsolo,
            supõe-se que sempre existirá um caminho que passa pelo subsolo que garante que o robô chegará ao destino final,
            eliminando a necessidade de algoritmos baseados em backtrack/uso de sensores*/

            this.setPosicaoX(this.getPosicaoX() + deltaX);
            this.setPosicaoY(this.getPosicaoY() + deltaY);
            this.setPosicaoZ(this.getPosicaoZ() + deltaZ);
        }
    }
    
    @Override
    void exibirPosicao() {
        //leva em cosideração a nova direção
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ());
    }
}