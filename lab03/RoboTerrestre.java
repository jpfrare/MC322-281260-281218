
public class RoboTerrestre extends Robo {
    private final float velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, String direcao, Sensor sensor) {
        //adiconar o atributo velocidade máxima
        super(posicaoXo, posicaoYo, nome, a, sensor);
        this.velocidademax = velocidademax;
    }

    float getVelocidademax() {
        //get para velMax
        return velocidademax;
    }
     
    @Override
    void mover(int deltaX, int deltaY) {
        /*move o robo terrestre considerando o limite de velocidade (supoe que o salto de coordenadas individuais 
        tem que ser menor que a velocidade maxima)*/
        if (Math.abs(deltaX) > this.velocidademax || Math.abs(deltaY) > this.velocidademax) {
            System.out.printf("Movimento inválido! \n");
            return;
        }

        super.mover(deltaX, deltaY);
    }
    
}
