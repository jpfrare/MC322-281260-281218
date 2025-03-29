public class RoboAereo extends Robo {
    private int posicaoZ;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, String direcao){
        super(posXo, posYo, nome, a, direcao);
        this.posicaoZ= alt_o;
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h){
        int pos_final = this.getPosicaoZ() + delta_h;
        if(this.getAmbiente().dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), pos_final) && (pos_final <= this.altitudeMax)){
            this.setPosicaoZ(pos_final);
        }
        else{
            System.out.println("Movimento Invalido de subida!");
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    @Override
    int getPosicaoZ(){
        return this.posicaoZ;
    }

    void setPosicaoZ(int pos_z){
        this.posicaoZ = pos_z;
    }

    @Override
    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ());
    }

    void descer(int delta_h){
        //apenas desce até uma posicao valida (altura > 0)
        int pos_final = this.getPosicaoZ() - delta_h;
        if(pos_final > 0){
            this.setPosicaoZ(pos_final);
        }
        else{
            System.out.println("Movimento invalido de descida!");
        }
    }

}
