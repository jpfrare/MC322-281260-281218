public class RoboTerrestreTopeira extends RoboTerrestre {
    private int profundidade; //escava o ambiente
    private final int profundidadeMax; //valor < 0

    public RoboTerrestreTopeira(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a, 
    int profundidadeMax, int r_sensor) {
        //construtor levando em consideração os novos atributos
        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);
        this.profundidade = 0;
        this.profundidadeMax = profundidadeMax;
    }

    //sets e gets para as profundidades
    int getProfundidade() {
        return this.profundidade;
    }
    
    void setProfundidade(int profundidade) {
        if (profundidade < 0 && profundidade > this.profundidadeMax) {
            this.profundidade = profundidade;
        }
    }

    int getProfundidadeMax() {
        return this.profundidadeMax;
    }

    void mover_horizontal(int delta_x, int delta_y){
        //para o roboterrestretopeira nao haverá obstaculo (essa é a sua vantagem)
        int x_final = this.getPosicaoX() + delta_x;
        int y_final = this.getPosicaoY() + delta_y;
        if(x_final >= 0 && x_final <= this.getAmbiente().getAmbienteX() && y_final >= 0 && y_final <= this.getAmbiente().getAmbienteY()){
            int avancar = this.getSensorMovimento().getRaio();
            while(delta_x != 0){
                if(delta_x > 0){
                    if(delta_x > avancar){ //alcance do sensor nao chega no destino
                        delta_x -= avancar;
                        this.setPosicaoX(this.getPosicaoX() + avancar);
                    }
                    else{   //alcance do sensor chega no destino
                        this.setPosicaoX(this.getPosicaoX() + delta_x);
                        delta_x = 0;
                    }
                }
                else{
                    if(avancar < -delta_x){ //alcance do sensor nao chega no destino
                        delta_x += avancar;
                        this.setPosicaoX(this.getPosicaoX() - avancar);
                    }
                    else{   //alcance do sensor chega no destino
                        this.setPosicaoX(this.getPosicaoX() + delta_x);
                        delta_x = 0;
                    }
                }
            }
            while(delta_y != 0){
                if(delta_y > 0){
                    if(delta_y > avancar){ //alcance do sensor nao chega no destino
                        delta_y -= avancar;
                        this.setPosicaoY(this.getPosicaoY() + avancar);
                    }
                    else{   //alcance do sensor chega no destino
                        this.setPosicaoY(this.getPosicaoY() + delta_y);
                        delta_y = 0;
                    }
                }
                else{
                    if(avancar < -delta_y){ //alcance do sensor nao chega no destino
                        delta_y += avancar;
                        this.setPosicaoY(this.getPosicaoY() - avancar);
                    }
                    else{   //alcance do sensor chega no destino
                        this.setPosicaoY(this.getPosicaoY() + delta_y);
                        delta_y = 0;
                    }
                }
            }
            
        }
        else{
            System.out.println("Movimento Invalido!");
        }
    }


    void mover(int deltaX, int deltaY, int deltaZ) {
        //faz o movimento, dando prioridade a possibilidade de movimento na na direção z
        int xo = this.getPosicaoX();
        int yo = this.getPosicaoY();

        if (Math.abs(deltaZ) > this.getVelocidademax() || this.profundidade + deltaZ < profundidadeMax || 
        this.profundidade + deltaZ > 0) {
            System.out.printf("Movimento inválido! \n");
            return;
        }

        this.mover_horizontal(deltaX, deltaY);

        if (deltaX == this.getPosicaoX() - xo && deltaY == this.getPosicaoY() - yo) {
            //se realmente se moveu em xy, move-se em z
            this.profundidade += deltaZ;
        }
    }
    
    @Override
    void exibirPosicao() {
        //leva em cosideração a nova direção
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.profundidade);
    }
}