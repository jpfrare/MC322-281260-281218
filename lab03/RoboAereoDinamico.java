

public class RoboAereoDinamico extends RoboAereo {
    //No robo aereo, os movimentos possiveis eram somente na vertical ou na horizontal(mesmo metodo herdado da classe robo)
    //Para o Robo Aereo Dinamico, sera possivel em apenas um movimento, poder executar simultaneamente o movimento na horizontal e na vertical
    //No entanto, o custo disso eh que a capacidade de autonomia sera perceptivel em nossa simulacao devido ao esforco para realizar essas duas tarefas simultaneas
    private final int capacidade;
    private int altitudemax_atual;
    private int nivel_energetico;

    public RoboAereoDinamico(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int capacidade, SensorMovimento sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, sensor);
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
    void moverDinamico(int delta_x, int delta_y, int delta_z){
        int pos_xo = this.getPosicaoX();
        int pos_yo = this.getPosicaoY();
        int pos_zo = this.getPosicaoZ();
        if(!this.getAmbiente().identifica_colisao(this.getPosicaoX() + delta_x, this.getPosicaoY() + delta_y, this.getPosicaoZ() + delta_z)
        && this.getAmbiente().dentroDosLimites(this.getPosicaoX() + delta_x, this.getPosicaoY() + delta_y, this.getPosicaoZ() + delta_z)
        && this.getPosicaoZ() + delta_z <= (this.altitudemax_atual * (this.nivel_energetico)) / this.capacidade){
            //verificar se a posicao final ja nao esta ocupada e/ou a posicao final esta no ambiente e/ou a posicao final atende aos requisitos do robo
            if(this.mover_3d(delta_x, delta_y, delta_z)){
                this.getAmbiente().getMapa()[pos_xo][pos_yo][pos_zo] = 0;
                this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
                this.reduzir_autonomia();
            }
            else{
                this.setPosicaoX(pos_xo);
                this.setPosicaoX(pos_yo);
                this.setPosicaoZ(pos_zo);
            }
        }

    }

    boolean mover_3d(int delta_x, int delta_y, int delta_z){
        if(this.getAmbiente().identifica_colisao(this.getPosicaoX() + delta_x, this.getPosicaoY() + delta_y, this.getPosicaoZ() + delta_z)){
            return false;
        }
        int pos_z0 = this.getPosicaoZ();
        int avancar;
        if(delta_z > 0 ){
            // o movimento pretendido de subida é possivel considerando a reducao do nivel energetico(e consequentemente a sua altura maxima)
            avancar = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), delta_z, this.getAmbiente());
            if(avancar > 0){
                this.setPosicaoZ(this.getPosicaoZ() + avancar);
                if(this.mover_3d(delta_x, delta_y, delta_z - avancar)){
                    return true;
                }
            }
            
        }
        else if(delta_z == 0){
            int x_ini = this.getPosicaoX();
            int y_ini = this.getPosicaoY();
            if((delta_x == 0 && delta_y == 0) || this.mover_horizontal(delta_x, delta_y)){
                return true;
            }
            else{
                this.setPosicaoX(x_ini);
                this.setPosicaoY(y_ini);
            }

        }
        else if(delta_z < 0 ){
            // o movimento pretendido de descida é possivel considerando a reducao do nivel energetico (e consequentemente a sua altura maxima), e a posicao final é valida acima de z=0
            if(delta_x != 0 || delta_y != 0){ //caso o robo desca e mais provavel que primeiro mover horizontalmente e depois mover verticalmente seja um caminho valido
                if(mover_horizontal(delta_x, delta_y)){ //mover horizontalmente em uma altura maior tem maior chance de ele passar por um obstaculo
                    avancar = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), delta_z, this.getAmbiente());
                    if(avancar > 0){
                        this.setPosicaoZ(this.getPosicaoZ() - avancar);
                        if(this.mover_3d(0, 0, delta_z + avancar)){
                            return true;
                        }
                    }
                }
            }
            else{
                avancar = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), delta_z, this.getAmbiente());
                if(avancar > 0){
                    this.setPosicaoZ(this.getPosicaoZ() - avancar);
                    if(this.mover_3d(delta_x, delta_y, delta_z + avancar)){
                        return true;
                    }
                }
            }
        }
        this.setPosicaoZ(pos_z0);
        return false;
    }

    boolean mover_horizontal(int delta_x, int delta_y){
        int x_ini = this.getPosicaoX();
        int y_ini = this.getPosicaoY();
        this.mover(delta_x, delta_y);
        return (x_ini + delta_x == this.getPosicaoX() && y_ini + delta_y == this.getPosicaoY());
    }
}