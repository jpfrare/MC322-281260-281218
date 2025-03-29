
public class RoboTerrestre extends Robo {
    private int velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, String direcao) {
        super(posicaoXo, posicaoYo, nome, a, direcao);
        this.velocidademax = velocidademax;
    }

    int getVelocidademax() {
        return velocidademax;
    }

    @Override
    void mover(int deltaX, int deltaY) {
        if (Math.abs(deltaX) > this.velocidademax || Math.abs(deltaY) > this.velocidademax) {
            System.out.printf("Movimento inválido! \n");
            return;
        }

        super.mover(deltaX, deltaY);
    }

}
