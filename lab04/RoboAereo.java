public class RoboAereo extends Robo {
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, nome, a, r_sensor);
        this.setPosicaoZ(alt_o);
        this.altitudeMax = alt_max;
        this.getAmbiente().getMapa()[posXo][posYo][0] = 0; 
        this.getAmbiente().getMapa()[posXo][posYo][alt_o] = 1;
    }

    void subir(int delta_h){
        int pos_inicial = this.getZ();
        int pos_final = this.getPosicaoZ() + delta_h; //posicao final prevista (caso seja um movimento valido)
        int avancar = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), delta_h, this.getAmbiente());
        if(this.getAmbiente().dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), pos_final) && (pos_final <= this.altitudeMax)){
            while(avancar > 0){
                this.setPosicaoZ(this.getPosicaoZ() + avancar);
                delta_h -= avancar;
                avancar = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), delta_h, this.getAmbiente());
            }
            if(delta_h > 0){ // encontrou uma posicao de um obstaculo antes de chegar ao destino
                this.setPosicaoZ(pos_inicial);
            }
            else{ //nao ocorreu colisao durante o caminho ate o destino
                this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][pos_inicial] = 0;
                this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
            }
            
        }
        else{
            System.out.println("Movimento Invalido de subida! Nao atende as especificacoes do ambiente e/ou do robo");
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    void descer(int delta_h){
        //apenas desce até uma posicao valida (altura > 0)
        int z_inicial = this.getPosicaoZ();
        int z_final = this.getPosicaoZ() - delta_h;
        int avanca = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), -delta_h, this.getAmbiente());
        if(z_final >= 0){
            while(avanca > 0){
                this.setPosicaoZ(this.getPosicaoZ() - avanca);
                delta_h -= avanca;
                avanca = this.getSensorMovimento().consegueAvancar(3, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), -delta_h, this.getAmbiente());
            }
            if(delta_h != 0) { //encontrou um obstaculo antes de chegar ao ponto final
                this.setPosicaoZ(z_inicial);
            }
            else{   //nao houve colisao no meio do caminho: percurso bem sucedido
                this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][z_inicial] = 0;
                this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
            }
        }else{
            System.out.println("Movimento invalido de descida! Altura abaixo de 0 somente é possível para o RoboTopeira!");

        }
    }

}