abstract class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    public Robo (int posicaoXo, int posicaoYo, String nome) {
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
    }

    void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }

    void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }

    int getPosicaoX() {
        return this.posicaoX;
    }

    int getPosicaoY() {
        return this.posicaoY;
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


    //identificarObstaculo() criar!!
}
