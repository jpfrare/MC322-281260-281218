package obstaculo;

public class Obstaculo implements InterfaceEntidadeObstaculo{
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int z1;
    private int z2;
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
        z1 = 0;
        z2 = tipo.getAltura();
        this.forma = tipo;
        this.ambiente = local;
        //this.ambiente.adicionaObstaculo(this);
    }

    TipoObstaculo getForma() {
        return this.forma;
    }

    public void setX(int x){
        this.x1 = x;
    }

    public void setX2(int x){
        this.x2 = x;
    }
    
    public void setY(int y){
        this.y1 = y;
    }

    public void setY2(int y){
        this.y2 = y;
    }

    public void setZ(int z){
        this.z1 = z;
    }

    public void setZ2(int z){
        this.z2 = z;
    }

    @Override
    public TipoEntidade getTipo(){
        return TipoEntidade.OBSTACULO;
    }

    @Override
    public int getX(){
        return x1;
    }

    @Override
    public int getX2(){
        return x2;
    }
    
    @Override
    public int getY() {
        return y1;
    }

    @Override
    public int getY2(){
        return y2;
    }

    @Override
    public int getZ(){
        return z1;
    }

    @Override
    public int getZ2(){
        return z2;
    }

    Ambiente getAmbiente() {
        return this.ambiente;
    }
    
    @Override
    public String getDescricao() {
        return "Obstáculo com dimensões consideraveis e altura definida a depender de seu tipo, bloqueia o caminho no caso de movimentação de robôs";
    }

    @Override
    public char getRepresentacao() {
        return 'o';
    }
}