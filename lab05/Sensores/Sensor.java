package Sensores;

public abstract class Sensor {
    private final int raio;

    public Sensor(int r){
        this.raio = r;
    }

    int getRaio(){
        return this.raio;
    }

}
