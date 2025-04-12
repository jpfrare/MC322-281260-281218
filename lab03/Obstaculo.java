public class Obstaculo {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final TipoObstaculo tipo;

    public Obstaculo(int x1, int x2, int y1, int y2, TipoObstaculo tipo) {
        this.tipo = tipo;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    TipoObstaculo getTipo() {
        return this.tipo;
    }

    int getx1(){
        return x1;
    }

    int getx2(){
        return x2;
    }
    
    int gety1() {
        return y1;
    }

    int gety2(){
        return y2;
    }
}
