import java.util.ArrayList;

public abstract class Robo {
    private final String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final Ambiente habitat;
    private final ArrayList<Sensor> sensores;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, String direcao) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.direcao = direcao;
        this.posicaoZ = 0;
        this.sensores = new ArrayList<>();
        this.habitat.adicionaRobo(this);
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
        
        if (this.sensores.get(0).getRaio() < Math.sqrt(deltaX*deltaX + deltaY*deltaY)) {

            if (this.getAmbiente().getMapa()[this.posicaoX + deltaX][this.posicaoY + deltaY] != 0) return false;

            //nesse caso, a posição desejada esta no alance do sensor e está desocupada
            this.habitat.getMapa()[this.posicaoX][this.posicaoY] = 0; //desocupando a posição original
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;

            this.habitat.getMapa()[this.posicaoX][this.posicaoY] = 1; //ocupando a posição final

            return true;
        } else {
            int xo = this.posicaoX;
            int yo = this.posicaoY;

            if (deltaX < 0) {
                this.posicaoX--;
                deltaX++;

            } else {
                this.posicaoX++;
                deltaX--;
            }

            

        }
    }

    void mover(int deltaX, int deltaY){
        if (!this.habitat.dentroDosLimites(deltaX, deltaY, 0)) return; //confere se a região está dentro dos limites

        if (this.sensores.get(0).getRaio() < Math.sqrt(deltaX*deltaX + deltaY*deltaY)) {
    
        }

        
    }

    void adicionaSensor(Sensor s) {
        this.sensores.add(s);
    }

    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getDirecao());
    }


}
