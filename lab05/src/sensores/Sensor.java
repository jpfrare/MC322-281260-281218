package sensores;


public abstract class Sensor {
    private final int raio;

    public Sensor(int r){
        this.raio = r;
    }

    public int getRaio(){
        return this.raio;
    }

}
