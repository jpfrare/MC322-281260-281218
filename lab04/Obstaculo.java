public class Obstaculo implements InterfaceEntidadeObstaculo{
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final TipoObstaculo forma;
    private final Ambiente ambiente;

    public Obstaculo(int x1, int x2, int y1, int y2, TipoObstaculo tipo, Ambiente local) {
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
        this.forma = tipo;
        this.ambiente = local;
        //this.ambiente.adicionaObstaculo(this);
    }

    TipoObstaculo getForma() {
        return this.forma;
    }

    @Override
    int getX(){
        return x1;
    }

    @Override
    int getX2(){
        return x2;
    }
    
    @Override
    int getY() {
        return y1;
    }
    @Override
    int getY2(){
        return y2;
    }
    
    @Override
    int getZ(){
        return this.getForma().getAltura();
    }

    Ambiente getAmbiente() {
        return this.ambiente;
    }
    
}
