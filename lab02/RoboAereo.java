public class RoboAereo extends Robo {
    private int altitude;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome){
        super(posXo, posYo, nome);
        this.altitude = alt_o;
        this.altitudeMax = alt_max;
    }

    void subir(int delta_h){
        if(this.altitude + delta_h <= this.altitudeMax){
            this.altitude += delta_h;
        }
        else{
            this.altitude = this.altitudeMax;
        }
    }

    void descer(int delta_h){
        if(this.altitude - delta_h >= 0){
            this.altitude -= delta_h;
        }
        else{
            this.altitude = 0;
        }
    }

}
