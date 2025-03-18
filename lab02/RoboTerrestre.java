public class RoboTerrestre extends Robo {
    private int velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, int velocidademax) {
        super(posicaoXo, posicaoYo, nome);
        this.velocidademax = velocidademax;
    }

    @Override
    void mover(int deltaX, int deltaY) {
        
    }
}
