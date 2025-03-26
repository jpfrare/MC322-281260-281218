public class RoboAereo extends Robo {
    private int altitude;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome){
        super(posXo, posYo, nome);
        this.altitude = alt_o;
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h, Ambiente espaco){
        this.altitude += delta_h;
        if(!espaco.dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), this.altitude) || !(this.altitude <= this.altitudeMax)){
            //a altura apos a subida viola o criterio do ambiente ou do proprio robo
            if(this.altitudeMax <= espaco.getAltura())
                this.altitude = espaco.getAltura();
            else
                this.altitude = this.altitudeMax;
        }
    }

    void descer(int delta_h){
        this.altitude -= delta_h;
        if(this.altitude < 0){
            this.altitude = 0;
        }
    }

}
