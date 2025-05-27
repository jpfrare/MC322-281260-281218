public abstract class RoboAereo extends Robo {
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, nome, a, r_sensor);
        this.setPosicaoZ(alt_o);
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h) throws RoboDesligadoException{
        if (!this.getEstado().esta_ligado()) {
            throw new RoboDesligadoException("Movimento não realizado, Robô" + this.getNome() + " desligado!");
        }

        int pos_inicial = this.getZ();
        int pos_final = this.getZ() + delta_h; //posicao final prevista (caso seja um movimento valido)
        int avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_h, this.getAmbiente());
        try {
            this.getAmbiente().dentroDosLimites(this.getX(), this.getY(), pos_final);

        } catch (ForaDosLimitesException e) {
            System.err.println("Erro: " + e.getMessage());
        }

        if((pos_final <= this.altitudeMax)){
            while(avancar > 0){
                this.setPosicaoZ(this.getZ() + avancar);
                delta_h -= avancar;
                avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_h, this.getAmbiente());
            }
            if(delta_h > 0){ // encontrou uma posicao de um obstaculo antes de chegar ao destino
                this.setPosicaoZ(pos_inicial);
            }
            else{ //nao ocorreu colisao durante o caminho ate o destino
                this.getAmbiente().getMapa()[this.getX()][this.getY()][pos_inicial] = TipoEntidade.VAZIO;
                this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.ROBO;
            }
            
        }
        else{
            System.err.println("Movimento Invalido de subida! Nao atende as especificacoes do ambiente e/ou do robo");
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    void descer(int delta_h) throws RoboDesligadoException{
        //apenas desce até uma posicao valida (altura > 0)
        if (!this.getEstado().esta_ligado()) {
            throw new RoboDesligadoException("Movimento não realizado, Robô" + this.getNome() + " desligado!");
        }

        int z_inicial = this.getZ();
        int z_final = this.getZ() - delta_h;
        int avanca = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -delta_h, this.getAmbiente());
        if(z_final >= 0){
            while(avanca > 0){
                this.setPosicaoZ(this.getZ() - avanca);
                delta_h -= avanca;
                avanca = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -delta_h, this.getAmbiente());
            }
            if(delta_h != 0) { //encontrou um obstaculo antes de chegar ao ponto final
                this.setPosicaoZ(z_inicial);
            }
            else{   //nao houve colisao no meio do caminho: percurso bem sucedido
                this.getAmbiente().getMapa()[this.getX()][this.getY()][z_inicial] = TipoEntidade.VAZIO;
                this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.ROBO;
            }
        }else{
            System.err.println("Movimento invalido de descida! Altura abaixo de 0 somente é possível para o RoboTopeira!");

        }
    }

}