import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    private final int Z;
    public final ArrayList<Robo> robos;
    public final ArrayList<TipoObstaculo> obstaculos;
    private int[][] mapa;

    public Ambiente(int x, int y, int z) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.robos = new ArrayList<>();
        this.obstaculos = new ArrayList<>();
        this.mapa = new int[x][y];
    }
    
    boolean eh_obstaculo(){ return true;}

    public boolean dentroDosLimites(int x, int y, int z) {
        //retorna true se o robo esta dentro do ambiente e false caso contrario
        return (x >= 0 && this.X >= x && this.Y >= y && y >= 0 && z >=0 && this.Z >= z);

    }

    public void adicionaRobo(Robo robo){
        //adiciona um objeto da classe robo ao array de robos
        this.robos.add(robo);
    }

    public int getArrayTamanho() {
        return this.robos.size();
    }

    public int getAmbienteX(){
        return this.X;
    }

    public int getAmbienteY(){
        return this.Y;
    }

    public int getAltura() {
        return this.Z;
    }

    public Robo getRobo(int pos){
        return this.robos.get(pos);
    }

}
