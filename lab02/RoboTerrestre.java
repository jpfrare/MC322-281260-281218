
public class RoboTerrestre extends Robo {
    private float velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, String direcao) {
        super(posicaoXo, posicaoYo, nome, a, direcao);
        this.velocidademax = velocidademax;
    }

    float getVelocidademax() {
        return velocidademax;
    }

    void setVelocidadeamx(float valor) {
        this.velocidademax = valor;
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
