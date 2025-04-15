import java.util.ArrayList;

public abstract class Robo {
    private final String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final Ambiente habitat;
    private final Sensor sensor;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, String direcao, Sensor sensor) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.direcao = direcao;
        this.posicaoZ = 0;
        this.habitat.adicionaRobo(this);
        this.sensor = sensor;
        this.habitat.getMapa()[posicaoXo][posicaoYo] = 1;
    }


    String getNome() {
        return this.nome;
    }

    void setPosicaoX(int posicaoX) {
        //mudar a posição em X
        this.posicaoX = posicaoX;
    }

    void setPosicaoY(int posicaoY) {
        //mudar a posição em Y
        this.posicaoY = posicaoY;
    }

    int getPosicaoX() {
        //retorna a posicão em X
        return this.posicaoX;
    }

    int getPosicaoY() {
        //retorna a posição em Y
        return this.posicaoY;
    }

    int getPosicaoZ() {
        //esse método torna possível a implementação de uma análise de arraylist do ambiente baseada na dimensão Z
        return this.posicaoZ;
    }

    void setPosicaoZ(int z){
        this.posicaoZ = z;
    }

    void setDirecao(String direcao){
        //muda a direção do robô
        this.direcao = direcao;
    }

    String getDirecao() {
        //retorna a direção do robô
        return this.direcao;
    }

    Ambiente getAmbiente() {
        //retorna o ambiente do qual o robô pertence
        return this.habitat;
    }

    boolean moverR(int deltaX, int deltaY) {
        if (this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY] != 0) return false;
        //está em uma posição inválida
        
        if (this.sensor.getRaio() >= Math.sqrt(deltaX*deltaX + deltaY*deltaY)) {

            if (this.getAmbiente().getMapa()[this.posicaoX + deltaX][this.posicaoY + deltaY] != 0) return false;

            //nesse caso, a posição desejada esta no alance do sensor e está desocupada
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;

            return true;

        } else {
            int xo = this.posicaoX;
            int deltaxo = deltaX;

            if (deltaX < 0) {
                this.posicaoX--;
                deltaX++;

            } else if (deltaX > 0) {
                this.posicaoX++;
                deltaX--;

            }

            return true;
        }
    }

    void mover(int deltaX, int deltaY){
        if (!this.habitat.dentroDosLimites(deltaX, deltaY, 0)) return; //confere se a região está dentro dos limites
        int xo = this.posicaoX;
        int yo = this.posicaoY;
        this.getAmbiente().getMapa()[xo][yo] = 0;

        if (moverR(deltaX, deltaY)) {
            this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY] = 1;

        } else {
            this.getAmbiente().getMapa()[xo][yo] = 1;
            System.out.println("movimento não realizado");
        }

    }


    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getDirecao());
    }


}
