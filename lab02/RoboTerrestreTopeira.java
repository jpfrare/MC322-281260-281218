public class RoboTerrestreTopeira extends RoboTerrestre {
    private int profundidade; 
    private int profundidadeMax; //valor < 0

    public RoboTerrestreTopeira(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, 
    int profundidadeMax, String direcao) {
        super(posicaoXo, posicaoYo, nome, velocidademax, a, direcao);
        this.profundidade = 0;
        this.profundidadeMax = profundidadeMax;
    }

    int getProfundidade() {
        return this.profundidade;
    }
 
    int getProfundidadeMax() {
        return this.profundidadeMax;
    }


    void mover(int deltaX, int deltaY, int deltaZ) {
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
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.profundidade, this.getDirecao());
    }
}