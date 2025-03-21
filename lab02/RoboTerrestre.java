
public class RoboTerrestre extends Robo {
    private final int velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, int velocidademax) {
        super(posicaoXo, posicaoYo, nome);
        this.velocidademax = velocidademax;
    }

    @Override
    void mover(int deltaX, int deltaY) {
        if ((Math.abs(deltaX) > this.velocidademax) || (Math.abs(deltaY) > this.velocidademax) ||
        (this.getPosicaoX() + deltaX < 0) || (this.getPosicaoY() + deltaY < 0)) {

            System.out.printf("Movimento Invalido!\n");
            return;
        }

        this.setPosicaoX(this.getPosicaoX() + deltaX);
        this.setPosicaoY(this.getPosicaoY() + deltaY);
    }
}
