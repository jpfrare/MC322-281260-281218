import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    private final int Z;
    private final ArrayList<InterfaceEntidade> elementos;
    private TipoEntidade[][][] mapa;
    private int[][][] temperatura;

    public Ambiente(int x, int y, int z) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.elementos = new ArrayList<>();
        this.mapa = new TipoEntidade[x + 1][y + 1][z + 1]; //posicoes (0,0) e (x, y) serao validas
        //inicialização do mapa
        for (int i = 0; i < x + 1; i++) {
            for (int j = 0; j < y + 1; j++) {
                for(int k = 0; k < z + 1; k++){
                    mapa[i][j][k] = TipoEntidade.VAZIO;
                }
            }
        }

        this.temperatura = new int[x + 1][y + 1][z + 1];
        for (int i = 0; i < x + 1; i++) {
            for (int j = 0; j < y + 1; j++) {
                for (int k = 0; k < z + 1; k++) {
                    //função completamente aleatória de distribuição de temperatura
                    this.temperatura[i][j][k] = (i*i - j*j)*((x + y + z)/3 - k);
                    
                }
            }
        }

    }

    public void registra_objeto(InterfaceEntidadeObstaculo objeto){
        int x_ini, x_fim, y_ini, y_fim;
        x_ini = objeto.getX();
        x_fim = objeto.getX2();
        y_ini = objeto.getY();
        y_fim = objeto.getY2();
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
        return this.mapa[x][y][h] == 1;
    }

    public Robo getRobo(int pos){
        return this.robos.get(pos);
    }

    public int[][][] getTemperatura() {
        return this.temperatura;
    }

}
