

public class RoboAereoDinamico extends RoboAereo implements InterfaceTermica, InterfaceFurtoCombustivel{
    //No robo aereo, os movimentos possiveis eram somente na vertical ou na horizontal(mesmo metodo herdado da classe robo)
    //No entanto, o custo disso eh que a capacidade de autonomia sera perceptivel em nossa simulacao devido ao esforco para realizar essas duas tarefas simultaneas
    private int altitudemax_atual;
    private float coeficiente_energetico;

    public RoboAereoDinamico(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, alt_o, alt_max, nome, a, r_sensor);
        this.coeficiente_energetico = 1; //inicializa o robo com capacidade energetica maxima (100% de coeficiente energetico)
        this.altitudemax_atual = alt_max; //como ele inicializa com a capacidade maxima, sua altura maxima inicial sera igual a altura maxima em que o robo pode alcancar com a carga maxima
    }

    void reduzir_autonomia(){
        //reduz a autonomia e altura maxima padrao
        if(this.coeficiente_energetico > 0){
            //caso o robo nao tenha sido totalmente descarregado: reducao de 10% de seu nivel energetico
            this.coeficiente_energetico = this.coeficiente_energetico - 0.1f;
            //reduz a capacidade de voar mais alto proporcionalente ao nivel energetico atual
            this.altitudemax_atual = (int)(this.getAltitudeMax() * (this.coeficiente_energetico + 0.1f));
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
        this.coeficiente_energetico = 1;
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

        if(this.getZ() + delta_z <= (this.altitudemax_atual * (this.coeficiente_energetico))){
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

    int z_minimo_descida(){
        int z = this.getZ();
        int desce = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -this.getZ(), this.getAmbiente()); 
        do { 
            z -= desce;
            desce = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -this.getZ() + desce, this.getAmbiente()); 
        } while (desce > 0);
        return z; //retorna o menor z que o robo consegue de descer (o robo desce ate chegar ao chao ou identificar uma colisao)
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

    @Override public void setCoeficiente(float c) throws EntradaException{
        if(c > 0 && c < 0){
            this.coeficiente_energetico = c;
            this.altitudemax_atual = (int)(this.getAltitudeMax() * c);
        }
        else if(c == 0){
            this.coeficiente_energetico = 0;
            this.altitudemax_atual = 0;
            this.setPosicaoZ(0);
            this.desligarRobo();
        }
        else{
            throw new EntradaException("Coeficiente energetico compreende valores entre 0 e 1!");
        }
    }

    @Override public float getCoeficiente(){
        return this.coeficiente_energetico;
    }

    @Override public float perder_combustivel(float quantidade) throws  EntradaException{
        float maximo;
        if(quantidade > 0.3f){
            maximo = 0.3f;
        }
        else{
            maximo = quantidade;
        }
        
        if(this.getCoeficiente() > maximo){ //sobra combustivel no robo que sera furtado
            if((int)(this.getAltitudeMax() * (this.getCoeficiente() - maximo)) >= this.z_minimo_descida()){
                //a reducao da altura maxima como consequencia da perda de combustivel nao implica em uma colisao com um obstaculo
                //nesse caso a perda o furto sera de 30% (maximo igual a 0.3f)
                try{
                    this.setCoeficiente(this.getCoeficiente() - maximo);
                    if(this.getZ() > this.altitudemax_atual){
                        this.setPosicaoZ(this.altitudemax_atual);
                    }
                    return maximo;
                }
                catch(EntradaException e){
                    return 0.0f;
                }

            }
            else{ // robo ira descer menos do que desceria caso nao identificasse colisao, a perda de combustivel sera menor
                //nesse caso a perda sera menor que 0.3f
                maximo = this.getCoeficiente() - (float)(((float)this.z_minimo_descida())/this.getAltitudeMax());
                if(maximo > 0.f){
                    try{
                        this.setCoeficiente(this.getCoeficiente() - maximo);
                        if(this.getZ() > this.altitudemax_atual){
                            this.setPosicaoZ(this.altitudemax_atual);
                        }
                        return maximo;
                    }
                    catch(EntradaException e){
                        return 0.0f;
                    }
                }
            }
        }
        else{ // combustivel pode acabar
            maximo = this.getCoeficiente() - (float)(((float)this.z_minimo_descida())/this.getAltitudeMax());
            this.setCoeficiente(this.getCoeficiente() - maximo);
            return maximo;
        }
        return 0;
        
    }

    @Override public void furtar_combustivel(InterfaceFurtoCombustivel furtado) throws EntradaException{
        try {    
            float furto = furtado.perder_combustivel(1 - this.getCoeficiente());
            this.setCoeficiente(this.getCoeficiente() + furto);
        }
        catch(EntradaException e){
            return;
        }
    }
}