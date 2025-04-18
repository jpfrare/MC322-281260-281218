

public class RoboAereoDinamico extends RoboAereo {
    //No robo aereo, os movimentos possiveis eram somente na vertical ou na horizontal(mesmo metodo herdado da classe robo)
    //Para o Robo Aereo Dinamico, sera possivel em apenas um movimento, poder executar simultaneamente o movimento na horizontal e na vertical
    //No entanto, o custo disso eh que a capacidade de autonomia sera perceptivel em nossa simulacao devido ao esforco para realizar essas duas tarefas simultaneas
    private final int capacidade;
    private int altitudemax_atual;
    private int nivel_energetico;

    public RoboAereoDinamico(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int capacidade, String direcao, Sensor sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, direcao, sensor);
        this.capacidade = capacidade; //capacidade energetica do robo
        this.nivel_energetico = capacidade; //inicializa o robo com capacidade energetica maxima ("bateria cheia")
        this.altitudemax_atual = alt_max; //como ele inicializa com a capacidade maxima, sua altura maxima inicial sera igual a altura maxima em que o robo pode alcancar com a carga maxima
    }

    void reduzir_autonomia(){
        //reduz a autonomia e altura maxima padrao
        if(this.nivel_energetico > 0){
            //caso o robo nao tenha sido totalmente descarregado
            this.nivel_energetico--;
            //reduz a capacidade de voar mais alto proporcionalente ao nivel energetico atual
            this.altitudemax_atual = (this.getAltitudeMax() * (this.nivel_energetico + 1)) / this.capacidade;
            if(this.getPosicaoZ() > this.altitudemax_atual) //corrige a altura atual com a altura maxima menor
                this.setPosicaoZ(this.altitudemax_atual);
            
        }
        else{//robo vai para o chao caso tenha descarregado
            this.altitudemax_atual = 0;
            this.setPosicaoZ(0);
        }
    }

    void recarregar(){
        //recarrega o nivel de energia e reestabelece a altura maxima padrao
        this.nivel_energetico = this.capacidade;
        this.altitudemax_atual = this.getAltitudeMax();
    }

    @Override
    void subir(int delta_h){
        //similar ao subir da classe robo, agora a altura maxima é condicionada ao nivel energetico atual do robo
        int pos_final = this.getPosicaoZ() + delta_h;
        if(this.getAmbiente().dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), pos_final) && (pos_final <= this.altitudemax_atual)){
            this.setPosicaoZ(pos_final);
        }
        else{
            System.out.println("Movimento invalido de Subida!");
        }
    }

    boolean moverDinamico(int delta_x, int delta_y, int delta_z){
        int pos_z0 = this.getPosicaoZ();
        if(delta_z > 0 && this.getPosicaoZ() + delta_z <= (this.altitudemax_atual * (this.nivel_energetico)) / this.capacidade){
            // o movimento pretendido de subida é possivel considerando a reducao do nivel energetico(e consequentemente a sua altura maxima)
            this.setPosicaoZ(this.getPosicaoZ() + 1);
            if(this.moverDinamico(delta_x, delta_y, delta_z - 1)){
                return true;
            }
            
        }
        else if(delta_z == 0){
            int abs_dx = Math.abs(delta_x);
            int abs_dy = Math.abs(delta_y);
            int [][] visitados = new int [abs_dx + 1][abs_dy + 1];
            
            for (int i = 0; i <= abs_dx; i++){
                for (int j = 0; j <= abs_dy; j++){
                    visitados[i][j] = 0;
                }
            }
            if(this.moveR(delta_x, delta_y, 0, 0, visitados)){
                this.setPosicaoX(this.getPosicaoX() + delta_x);
                this.setPosicaoY(this.getPosicaoY() + delta_y);
                return true;
            }

        }
        else if(delta_z < 0 && this.getPosicaoZ() + delta_z >= 0 && this.getPosicaoZ() + delta_z <= (this.altitudemax_atual * this.nivel_energetico) / this.capacidade){
            // o movimento pretendido de descida é possivel considerando a reducao do nivel energetico (e consequentemente a sua altura maxima), e a posicao final é valida acima de z=0
            this.setPosicaoZ(this.getPosicaoZ() - 1);
            if(this.moverDinamico(delta_x, delta_y, delta_z + 1)){
                return true;
            }
        }
        this.setPosicaoZ(pos_z0);
        return false;
    }

    boolean moveR(int deltaX, int deltaY, int passoX, int passoY, int [][] visitados){
        visitados[passoX][passoY] = 1;
        if(deltaX == 0 && deltaY == 0){
            return true;
        }
        for(int i = 1; i <= 2; i++){
            //iteracao: i igual a 1 move em x, i igual a 2 move em y
            if(i == 1){ //mover em x
                if(deltaX > 0){
                    if(this.getAmbiente().getMapa()[this.getPosicaoX() + 1][this.getPosicaoY()] <= this.getPosicaoZ() && visitados[passoX + 1][passoY] == 0){
                        if(moveR(deltaX - 1, deltaY, passoX + 1, passoY, visitados))
                            return true;
                    }
                }
                else if(deltaX < 0){
                    if(this.getAmbiente().getMapa()[this.getPosicaoX() - 1][this.getPosicaoY()] <= this.getPosicaoZ() && visitados[passoX + 1][passoY] == 0){
                        if(moveR(deltaX + 1, deltaY, passoX + 1, passoY, visitados))
                            return true;
                    }
                }
            }
            if(i == 2){//mover em y
                if(deltaY > 0){
                    if(this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY() + 1] <= this.getPosicaoZ() && visitados[passoX][passoY + 1] == 0){
                        if(moveR(deltaX, deltaY - 1, passoX, passoY + 1, visitados))
                            return true;
                    }
                }
                else if(deltaY < 0){
                    if(this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY() - 1] <= this.getPosicaoZ() && visitados[passoX][passoY + 1] == 0){
                        if(moveR(deltaX, deltaY + 1, passoX, passoY + 1, visitados))
                            return true;
                    }
                }
            }
        }
        return false;
    }

}
