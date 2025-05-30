

public class RoboAereoDinamico extends RoboAereo implements InterfaceTermica{
    //No robo aereo, os movimentos possiveis eram somente na vertical ou na horizontal(mesmo metodo herdado da classe robo)
    //No entanto, o custo disso eh que a capacidade de autonomia sera perceptivel em nossa simulacao devido ao esforco para realizar essas duas tarefas simultaneas
    private final int capacidade;
    private int altitudemax_atual;
    private int nivel_energetico;

    public RoboAereoDinamico(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int capacidade, int r_sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, r_sensor);
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
            if(this.getZ() > this.altitudemax_atual) //corrige a altura atual com a altura maxima menor
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
        System.out.printf("Robo %s recarregado, altura máxima: %d\n", this.getNome(), this.altitudemax_atual);
    }
    void moverDinamico(int delta_x, int delta_y, int delta_z) throws RoboDesligadoException{
        int pos_xo = this.getX();
        int pos_yo = this.getY();
        int pos_zo = this.getZ();
        try {
            this.getAmbiente().dentroDosLimites(this.getX() + delta_x, this.getY() + delta_y, this.getZ() + delta_z);
            this.getAmbiente().identifica_colisao(this.getX() + delta_x, this.getY() + delta_y, this.getZ() + delta_z);

        } catch (Exception e) {
            return;
        }

        if(this.getZ() + delta_z <= (this.altitudemax_atual * (this.nivel_energetico)) / this.capacidade){
            //verificar se a posicao final ja nao esta ocupada e/ou a posicao final esta no ambiente e/ou a posicao final atende aos requisitos do robo
            if(this.mover_3d(delta_x, delta_y, delta_z)){
                this.getAmbiente().getMapa()[pos_xo][pos_yo][pos_zo] = TipoEntidade.VAZIO;
                this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.ROBO;
                this.reduzir_autonomia();
            }
            else{
                this.setPosicaoX(pos_xo);
                this.setPosicaoY(pos_yo);
                this.setPosicaoZ(pos_zo);
            }
        }

    }

    private boolean mover_3d(int delta_x, int delta_y, int delta_z) throws RoboDesligadoException{
        try {
            this.getAmbiente().identifica_colisao(this.getX() + delta_x, this.getY() + delta_y, this.getZ() + delta_z);

        } catch (ColisaoException e) {
            return false;
        }

        int pos_z0 = this.getZ();
        int avancar;
        if(delta_z > 0 ){
            // o movimento pretendido de subida é possivel considerando a reducao do nivel energetico(e consequentemente a sua altura maxima)
            avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_z, this.getAmbiente());
            if(avancar > 0){
                this.setPosicaoZ(this.getZ() + avancar);
                if(this.mover_3d(delta_x, delta_y, delta_z - avancar)){
                    return true;
                }
            }
            
        }
        else if(delta_z == 0){
            int x_ini = this.getX();
            int y_ini = this.getY();
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
                    avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_z, this.getAmbiente());
                    if(avancar > 0){
                        this.setPosicaoZ(this.getZ() - avancar);
                        if(this.mover_3d(0, 0, delta_z + avancar)){
                            return true;
                        }
                    }
                }
            }
            else{
                avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_z, this.getAmbiente());
                if(avancar > 0){
                    this.setPosicaoZ(this.getZ() - avancar);
                    if(this.mover_3d(delta_x, delta_y, delta_z + avancar)){
                        return true;
                    }
                }
            }
        }
        this.setPosicaoZ(pos_z0);
        return false;
    }

    private boolean mover_horizontal(int delta_x, int delta_y) throws RoboDesligadoException{
        boolean moveu = this.mover(delta_x, delta_y);
        this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.VAZIO;
        return moveu;
    }

    @Override public String getDescricao() {
        return "Robô Aéreo que possui autonomia, ou seja, seus movimentos custam capacidade energética, caso esta esgote, não consegue se mover";
    }

    @Override public char getRepresentacao() {
        return 'd';
    }

    @Override public void preferenciaTermica() {
        //robôs aéreos têm preferência por baixas temperaturas, ele tentará se mover para o lugar de menor temperatura possível
        try {
            SensorTemperatura t = this.getSensorTemperatura();
            int[] menor_temperatura = t.getMenorTemperatura();
            int delta_x = menor_temperatura[0] - this.getX();
            int delta_y = menor_temperatura[1] - this.getY();
            int delta_z = menor_temperatura[2] - this.getZ();

            //tenta mover apenas uma vez, se não conseguir ir para a menor temperatura, fica no mesmo local
            this.mover_3d(delta_x, delta_y, delta_z);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("este robô não possui sensor de temperatura, ainda");

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override public void acionarSensores() {
        super.acionarSensores();
        this.preferenciaTermica();
    }
}