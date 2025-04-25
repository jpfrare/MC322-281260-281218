public class RoboTerrestreTopeira extends RoboTerrestre {
    private int profundidade; //escava o ambiente
    private final int profundidadeMax; //valor < 0

    public RoboTerrestreTopeira(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, 
    int profundidadeMax, String direcao, SensorMovimento sensor) {
        //construtor levando em consideração os novos atributos
        super(posicaoXo, posicaoYo, nome, velocidademax, a, direcao, sensor);
        this.profundidade = 0;
        this.profundidadeMax = profundidadeMax;
    }

    //sets e gets para as profundidades
    int getProfundidade() {
        return this.profundidade;
    }
    
    void setProfundidade(int profundidade) {
        if (profundidade < 0 && profundidade > this.profundidadeMax) {
            this.profundidade = profundidade;
        }
    }

    int getProfundidadeMax() {
        return this.profundidadeMax;
    }


    void mover(int deltaX, int deltaY, int deltaZ) {
        //faz o movimento, dando prioridade a possibilidade de movimento na na direção z
        int xo = this.getPosicaoX();
        int yo = this.getPosicaoY();

        if (Math.abs(deltaZ) > this.getVelocidademax() || this.profundidade + deltaZ < profundidadeMax || 
        this.profundidade + deltaZ > 0) {
            System.err.printf("Movimento inválido! \n");
            return;
        }

        super.mover(deltaX, deltaY);

        if (deltaX == this.getPosicaoX() - xo && deltaY == this.getPosicaoY() - yo) {
            //se realmente se moveu em xy, move-se em z
            this.profundidade += deltaZ;
        }
    }
    
    @Override
    void exibirPosicao() {
        //leva em cosideração a nova direção
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.profundidade);
    }
}