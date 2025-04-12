public abstract class Robo {
    private final String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final Ambiente habitat;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, String direcao) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.direcao = direcao;
        this.posicaoZ = 0;
        
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

    void mover(int deltaX, int deltaY){
        
        
    }

    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getDirecao());
    }


}
