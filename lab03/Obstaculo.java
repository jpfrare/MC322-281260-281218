public class Obstaculo {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final TipoObstaculo tipo;

    public Obstaculo(int x1, int x2, int y1, int y2, TipoObstaculo tipo) {
        //garantir que this.x1 e this.y1 sejam menores que this.x2 e this.y2
        if(x1 < x2){
            this.x1 = x1;
            this.x2 = x2;
        }
        else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if(y1 < y2){
            this.y1 = y1;
            this.y2 = y2;
        }
        else{
            this.y1 = y2;
            this.y2 = y1;
        }
        this.tipo = tipo;
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
