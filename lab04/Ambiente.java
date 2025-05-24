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

    public void registra_obstaculo(Obstaculo objeto){
        for(int i = objeto.getX(); i <= objeto.getX2(); i++){
            for(int j = objeto.getY(); j <= objeto.getY2(); j++){
                for(int k = 0; k <= objeto.getZ(); k++){
                    this.mapa[i][j][k] = TipoEntidade.OBSTACULO;
                }
            }
        }
        
    }

    public void apaga_obstaculo(Obstaculo objeto){
        for(int i = objeto.getX(); i <= objeto.getX2(); i++){
            for(int j = objeto.getY(); j <= objeto.getY2(); j++){
                for(int k = 0; k <= objeto.getZ(); k++){
                    this.mapa[i][j][k] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    public void registra_robo(InterfaceEntidade robo){
        this.mapa[robo.getX()][robo.getY()][robo.getZ()] = TipoEntidade.ROBO;
    }

    public void apaga_robo(InterfaceEntidade robo){
        this.mapa[robo.getX()][robo.getY()][robo.getZ()] = TipoEntidade.VAZIO;
    }

    public boolean dentroDosLimites(int x, int y, int z) {
        //retorna true se o robo esta dentro do ambiente e false caso contrario
        return (x >= 0 && this.X >= x && this.Y >= y && y >= 0 && z >=0 && this.Z >= z);

    }

    public void adicionarEntidade(InterfaceEntidade adicionar){
        if(adicionar.getTipo() == TipoEntidade.ROBO){
            this.registra_robo(adicionar);
        }
        else if(adicionar.getTipo() == TipoEntidade.OBSTACULO){
            this.registra_obstaculo((Obstaculo)adicionar);
        }
        this.elementos.add(adicionar);

    }

    public void removerEntidade(InterfaceEntidade remover){
        if(remover.getTipo() == TipoEntidade.ROBO){
            this.apaga_robo(remover);
        }
        else if(remover.getTipo() == TipoEntidade.OBSTACULO){
            this.apaga_obstaculo((Obstaculo)(InterfaceEntidadeObstaculo)remover);
        }
        elementos.remove(remover);
    }



    public int getArrayTamanho() {
        return this.elementos.size();
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

    public TipoEntidade tipoPosicao(int x, int y, int z){
        //retorna o  tipo de elemento que esta ocupando a posicao x,y,z do ambiente
        return this.mapa[x][y][z];
    }

    public TipoEntidade [][][] getMapa(){
        return this.mapa;
    }

    boolean identifica_colisao(int x, int y, int h){
        return this.mapa[x][y][h] != TipoEntidade.VAZIO;
    }

    public int[][][] getTemperatura() {
        return this.temperatura;
    }

}
