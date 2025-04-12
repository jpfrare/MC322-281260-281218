import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    private final int Z;
    private final ArrayList<Robo> robos;
    private final ArrayList<Obstaculo> obstaculos;
    private int[][] mapa;

    public Ambiente(int x, int y, int z) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.robos = new ArrayList<>();
        this.obstaculos = new ArrayList<>();
        this.mapa = new int[x + 1][y + 1]; //posicoes (0,0) e (x, y) serao validas
    }

    public void inicializa_mapa(){
        int i, j;
        for (i = 0; i <= this.X; i++){
            for(j = 0; j <= Y; j++)
                this.mapa[i][j] = 0;
        }
    }


    public void registra_no_mapa(Obstaculo objeto){
        int i, j, x_ini, x_fim, y_ini, y_fim;
        x_ini = objeto.getx1();
        x_fim = objeto.getx2();
        y_ini = objeto.gety1();
        y_fim = objeto.gety2();
        for(i = x_ini; i <= x_fim && i <= this.X; i++){
            for(j = y_ini; j <= y_fim && j <= this.Y; j++){
                this.mapa[i][j] = objeto.getTipo().getAltura();
            }
        }
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

    public void adicionaObstaculo(Obstaculo objeto){
        this.obstaculos.add(objeto);
        this.registra_no_mapa(objeto);
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

    public int getPosicaoMapa(int x, int y){
        //retorna o valor correspondente a posicao x y no mapa(0 se nao for posicao de um obstaculo, e z diferente de zero sendo a altura do obstaculo)
        return this.mapa[x][y];
    }

    public Robo getRobo(int pos){
        return this.robos.get(pos);
    }

}
