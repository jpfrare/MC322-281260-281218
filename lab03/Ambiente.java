import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    private final int Z;
    private final ArrayList<Robo> robos;
    private final ArrayList<Obstaculo> obstaculos;
    private int[][][] mapa;

    public Ambiente(int x, int y, int z) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.robos = new ArrayList<>();
        this.obstaculos = new ArrayList<>();
        this.mapa = new int[x + 1][y + 1][z + 1]; //posicoes (0,0) e (x, y) serao validas
        
        //inicialização do mapa
        for (int i = 0; i < x + 1; i++) {
            for (int j = 0; j < y + 1; j++) {
                for(int k = 0; k < z + 1; k++){
                    mapa[i][j][k] = 0;
                }
            }
        }

    }

    public void imprime_mapa(){
        int i, j, k;
        for (k = 0; k <= this.Z; k++){
            System.out.printf("z = %d:\n", k);
            for(i = 0; i <= this.X; i++){     
                for(j = 0; j <= this.Y; j++){
                    System.out.printf("%d ", this.mapa[i][j][k]);
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public void registra_no_mapa(Obstaculo objeto){
        int x_ini, x_fim, y_ini, y_fim;
        x_ini = objeto.getx1();
        x_fim = objeto.getx2();
        y_ini = objeto.gety1();
        y_fim = objeto.gety2();
        if(objeto.getTipo().getBloqueia()){ // obstaculo é do tipo que bloqueia
            for(int i = x_ini; i <= x_fim; i++){
                for(int j = y_ini; j <= y_fim; j++){
                    for(int k = 0; k <= objeto.getTipo().getAltura(); k++){
                        this.mapa[i][j][k] = 1;
                    }
                }
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

    public void adicionaObstaculo(int x1, int x2, int y1, int y2, TipoObstaculo tipo){
        //composicao ambiente-obstaculo
        Obstaculo novo = new Obstaculo(x1, x2, y1, y2, tipo, this);
        this.obstaculos.add(novo);
        this.registra_no_mapa(novo);
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

    public int[][][] getMapa(){
        //retorna o valor correspondente a posicao x y no mapa(0 se nao for posicao de um obstaculo, e z diferente de zero sendo a altura do obstaculo)
        return this.mapa;
    }

    public ArrayList<Robo> getArrayRobos(){
        return this.robos;
    }

    boolean identifica_colisao(int x, int y, int h){
        return this.getMapa()[x][y][h] == 1;
    }

    public Robo getRobo(int pos){
        return this.robos.get(pos);
    }

}
