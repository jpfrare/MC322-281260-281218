public class RoboAereoDinamico extends RoboAereo {
    //No robo aereo, os movimentos possiveis eram somente na vertical ou na horizontal(mesmo metodo herdado da classe robo)
    //Para o Robo Aereo Dinamico, sera possivel em apenas um movimento, poder executar simultaneamente o movimento na horizontal e na vertical
    //No entanto, o custo disso eh que a capacidade de autonomia sera perceptivel em nossa simulacao devido ao esforco para realizar essas duas tarefas simultaneas
    private final int capacidade;
    private int altitudemax_atual;
    private int nivel_energetico;

    public RoboAereoDinamico(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int capacidade){
        super(posXo, posYo, alt_o, alt_max, nome, a);
        this.capacidade = capacidade; //capacidade energetica do robo
        this.nivel_energetico = capacidade; //inicializa o robo com capacidade energetica maxima ("bateria cheia")
        this.altitudemax_atual = alt_max; //como ele inicializa com a capacidade maxima, sua altura maxima inicial sera igual a altura maxima em que o robo pode alcancar com a carga maxima
    }

    void reduzir_autonomia(){
        //reduz a autonomia e altura maxima padrao
        this.nivel_energetico--;
        if(this.nivel_energetico > 0 && (this.capacidade - this.nivel_energetico > 1)){
            //caso o robo nao tenha sido totalmente descarregado
            this.altitudemax_atual = this.getAltitudeMax() * ((this.nivel_energetico + 1) / this.capacidade); //reduz a capacidade de voar mais alto
            if(this.getPosicaoZ() > this.altitudemax_atual){ //corrige a altura atual com a altura maxima menor
                this.setPosicaoZ(this.altitudemax_atual);
            }
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
    void subir(int delta_h, Ambiente espaco){
        //similar ao subir da classe robo, agora a altura maxima é condicionada ao nivel energetico atual do robo
        int pos_final = this.getPosicaoZ() + delta_h;
        if(!espaco.dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), pos_final) || !(this.getPosicaoZ() <= this.altitudemax_atual)){
            if(this.altitudemax_atual <= espaco.getAltura()){
                this.setPosicaoZ(this.altitudemax_atual);
            }
            else{
                this.setPosicaoZ(espaco.getAltura());
            }
        }
    }

    void mover(int delta_x, int delta_y, int delta_z, Ambiente espaco){
        //o movimento horizontal aparentemente sera herdado da classe robo
        super.mover(delta_x, delta_y); //
        this.reduzir_autonomia(); //reducao do nivel energetico ("bateria") e consequentemente altura maxima possivel para o robo
        if(delta_z > 0){
            this.subir(delta_z, espaco);
        }
        else{ //verificar se nao sera necessario override
            super.descer(delta_z);
        }
    }


}
