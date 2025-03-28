public class RoboAereo extends Robo {
    private int posicaoZ;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a){
        super(posXo, posYo, nome, a);
        this.posicaoZ= alt_o;
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h, Ambiente espaco){
        this.posicaoZ += delta_h;
        if(!espaco.dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), this.posicaoZ) || !(this.posicaoZ <= this.altitudeMax)){
            //a altura apos a subida viola o criterio do ambiente ou do proprio robo: robo sobe para a menor altura possivel (satisfaz ambos os casos)
            if(this.altitudeMax <= espaco.getAltura())
                this.posicaoZ = this.altitudeMax;
            else
                this.posicaoZ = espaco.getAltura();
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    int getPosicaoZ(){
        return this.posicaoZ;
    }

    void setPosicaoZ(int pos_z){
        this.posicaoZ = pos_z;
    }


    void descer(int delta_h){
        this.posicaoZ -= delta_h;
        if(this.posicaoZ <= 0){
            //desce ate o zero(maior descida possivel)
            this.posicaoZ = 0;
        }
    }

}
