package lab02;

public abstract class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

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
            System.out.printf("Movimento Inválido! \n");
            return;
        }

        
    }

}
