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

    void mover(int deltaX, int deltaY, Ambiente a){
        
        if (a.dentroDosLimites(this.posicaoX + deltaX, this.posicaoY + deltaY, 0) 
        && !a.eh_obstaculo(this.posicaoX + deltaX, this.posicaoY + deltaY)) {
            //verificação se o robô ficará nos limites e não ocupará o mesmo lugar que o obstáculo
            //supõe-se que, caso haja um obstaculo no caminho, há uma combinação de direções que ele usará para evitá-lo

            this.posicaoX += deltaX;
            this.posicaoY += deltaY;

            //define a nova direção baseado em qual direção o módulo do deslocamento foi maior, e qual o sentido
            if (deltaX*deltaX > deltaY*deltaY) {
                if (deltaX > 0) {
                    this.direcao = "leste";

                } else {
                    this.direcao = "oeste";
                }

            } else {
                if (deltaY > 0) {
                    this.direcao = "norte";

                } else {

                    this.direcao = "sul";
                }
            }
        }

        else {
            System.out.printf("Movimento Inválido!\n");
        }
    }

    void exibirPosicao() {
        System.out.printf("Posição X: %d, PosiçãoY: %d\n", this.getPosicaoX(), this.getPosicaoY());
    }


    void identificarObstaculo(Ambiente a, int largura, int altura) {
        if (Math.abs(a.get))
        
    }
}
