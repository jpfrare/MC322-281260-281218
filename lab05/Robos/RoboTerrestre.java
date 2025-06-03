package Robos;


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
    boolean mover(int deltaX, int deltaY) throws RoboDesligadoException {
        /*move o robo terrestre considerando o limite de velocidade (supoe que o salto de coordenadas individuais 
        tem que ser menor que a velocidade maxima)*/
        if (Math.abs(deltaX) > this.velocidademax || Math.abs(deltaY) > this.velocidademax) {
            System.out.printf("Movimento inválido! \n");
            return false;
        }

        return super.mover(deltaX, deltaY);
    }

    @Override public void preferenciaTermica() {
        //o robô terrestre prefere altas temperaturas
        try {
            SensorTemperatura t = this.getSensorTemperatura();
            int[] temps = t.getMaiorTemperatura();

            int deltax = temps[0] - this.getX();
            int deltay = temps[1] - this.getY();

            //tenta mover apenas uma vez, se não conseguir ir para a maior temperatura, fica no mesmo local
            this.mover(deltax, deltay);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("este robô não possui sensor de temperatura, ainda");

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override public void acionarSensores() {
        super.acionarSensores();
        System.out.println("Tentando ir para a maior temperatura...");
        this.preferenciaTermica();
    }

    @Override public boolean procura(int x, int y, int raio) {
        //vê se tem algum robô no range do sensor e acima
        try {
            if (x != this.getX() || y != this.getY()) this.getAmbiente().identifica_colisao(x, y, 0);

            for (int z = 1; z <= raio; z++) {
                if (this.getAmbiente().tipoPosicao(x, y, z) == TipoEntidade.ROBO) {
                    return true;
                }
            }
            return false;
        
        } catch (ColisaoException e) {
            return false;
        }
    }

    @Override public void fugir() {
        //Robôs terrestres fogem de robôs aéreos, faz a tentativa de fuga apenas num quadrado ao redor do seu raio
        try {
            this.Robofunciona();
            int raio = this.getSensorMovimento().getRaio();
            if (!procura(this.getX(), this.getY(), raio)) {
                System.out.println("não há robô acima, dentro do raio do sensor, está tudo bem!");
                return;
            }

            int menorx, maiorx, menory, maiory;

            //pegando os limites das variáveis
            if (this.getX() - raio < 0) {
                menorx = 0;
            } else {
                menorx = this.getX() - raio;
            }

            if(this.getX() + raio > this.getAmbiente().getAmbienteX()) {
                maiorx = this.getAmbiente().getAmbienteX();
            } else {
                maiorx = this.getX() + raio;
            }

            if(this.getY() - raio < 0) {
                menory = 0;
            } else {
                menory = this.getY() - raio;
            }

            if(this.getY() + raio > this.getAmbiente().getAmbienteY()){
                maiory = this.getAmbiente().getAmbienteY();
            } else {
                maiory = this.getY() + raio;
            }

            for (int x = menorx; x <= maiorx; x++) {
                for (int y = menory; y <= maiory; y++) {
                    if ((x != this.getX() || y != this.getY()) && !this.procura(x, y, raio)) {
                        System.out.printf("Tentando mover para (%d, %d, %d)...\n", x, y, this.getZ());

                        if(this.mover(x - this.getX(), y - getY())) {
                            System.out.println("Agora o robô " + this.getNome() + " está seguro!");
                            return;
                        }

                        System.out.println("falhou! tentando de novo...");
                    }
                }
            }

            System.out.println("Não deu certo! há um robô acima deste em toda a região ou obstáculo que o impeça de fugir! Desligando...");
            this.desligarRobo();
            return;
            

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());
        }
    }
    
}