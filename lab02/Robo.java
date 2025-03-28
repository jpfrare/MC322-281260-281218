public abstract class Robo {
    private final String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private final Ambiente habitat;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        
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
        return 0;
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
        
        if (this.habitat.dentroDosLimites(this.posicaoX + deltaX, this.posicaoY + deltaY, 0) 
        && !this.habitat.eh_obstaculo(this.posicaoX + deltaX, this.posicaoY + deltaY)) {
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


    void identificarObstaculo() {
        int dx = habitat.getXobstaculo() - this.posicaoX;
        int dy = habitat.getYobstaculo() - this.posicaoY;

        double distancia = Math.sqrt(dx*dx + dy*dy);

        System.out.printf("a distância entre %s e o obstáculo é de %.2f\n", this.nome, distancia);

        String direcaoObstaculo; //mesma lógica usada para decidir a direção do robô
    
        if (dx*dx > dy*dy) {
            if (dx > 0) {
                direcaoObstaculo = "leste";
            } else {
                direcaoObstaculo = "oeste";
            }

        } else {
            if (dy > 0) {
                direcaoObstaculo = "norte";

            } else {
                direcaoObstaculo = "sul";
            }
        }

        if (this.nome.equals(direcaoObstaculo)) {
            System.out.printf("e %s está virado para mesma direção do objeto!", this.nome);
        }
    }
}
