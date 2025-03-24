public class RoboAereo extends Robo {
    private final int altitude;
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome){
        super(posXo, posYo, nome);
        this.altitude = alt_o;
        this.altitudeMax = alt_max;
    }

}
