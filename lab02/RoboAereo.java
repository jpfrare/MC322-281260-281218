public class RoboAereo extends Robo {
    private int altitude;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a){
        super(posXo, posYo, nome, a);
        this.altitude = alt_o;
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h, Ambiente espaco){
        this.altitude += delta_h;
        if(!espaco.dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), this.altitude) || !(this.altitude <= this.altitudeMax)){
            //a altura apos a subida viola o criterio do ambiente ou do proprio robo: robo sobe para a menor altura possivel (satisfaz ambos os casos)
            if(this.altitudeMax <= espaco.getAltura())
                this.altitude = this.altitudeMax;
            else
                this.altitude = espaco.getAltura();
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    int getAltitude(){
        return this.altitude;
    }

    void setAltitude(int alt){
        this.altitude = alt;
    }


    void descer(int delta_h){
        this.altitude -= delta_h;
        if(this.altitude <= 0){
            //desce ate o zero(maior descida possivel)
            this.altitude = 0;
        }
    }

}
