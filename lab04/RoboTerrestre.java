
public abstract class RoboTerrestre extends Robo implements InterfaceTermica {
    private final float velocidademax;

    public RoboTerrestre(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a,  int r_sensor) {
        //adiconar o atributo velocidade máxima
        super(posicaoXo, posicaoYo, nome, a, r_sensor);
        this.velocidademax = velocidademax;
    }

    float getVelocidademax() {
        //get para velMax
        return velocidademax;
    }
  
    @Override
    void mover(int deltaX, int deltaY) throws RoboDesligadoException {
        /*move o robo terrestre considerando o limite de velocidade (supoe que o salto de coordenadas individuais 
        tem que ser menor que a velocidade maxima)*/
        if (Math.abs(deltaX) > this.velocidademax || Math.abs(deltaY) > this.velocidademax) {
            System.out.printf("Movimento inválido! \n");
            return;
        }

        super.mover(deltaX, deltaY);
    }

    @Override public void preferenciaTermica() {
        //o robô terrestre prefere altas temperaturas
        try {
            SensorTemperatura t = this.getSensorTemperatura();
            int[] temps = t.getMaiorTemperatura();

            int deltax = temps[0] - this.getX();
            int deltay = temps[1] - this.getY();

            this.mover(deltax, deltay);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("este robô não possui sensor de temperatura, ainda");

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());
        }
    }
    
}