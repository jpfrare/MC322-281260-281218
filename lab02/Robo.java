public abstract class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    public Robo (int posicaoXo, int posicaoYo, String nome) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
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

    void setDirecao(String direcao){
        //muda a direção do robô
        this.direcao = direcao;
    }

    String getDirecao() {
        //retorna a direção do robô
        return this.direcao;
    }

    void mover(int deltaX, int deltaY){
        if ((this.getPosicaoX() + deltaX < 0) || (this.getPosicaoY() + deltaY < 0)) {
            System.out.printf("Movimento Inválido!\n");
            return;
        }

        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }

    void exibirPosicao() {
        System.out.printf("Posição X: %d, PosiçãoY: %d\n", this.getPosicaoX(), this.getPosicaoY());
    }


    void identificarObstaculo(Ambiente a) {
        
    }
}
