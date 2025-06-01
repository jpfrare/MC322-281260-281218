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

    public boolean espaco_vazio(Obstaculo objeto){
        int i, j;
        for(i = objeto.getX(); i <= objeto.getX2(); i++){
            for(j = objeto.getY(); j <= objeto.getY2(); j++){
                for(int k = objeto.getZ(); k <= objeto.getZ2(); k++){
                    try {
                        this.identifica_colisao(i, j, k);
                    } catch (ColisaoException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void registra_obstaculo(Obstaculo objeto){
        for(int i = objeto.getX(); i <= objeto.getX2(); i++){
            for(int j = objeto.getY(); j <= objeto.getY2(); j++){
                for(int k = objeto.getZ(); k <= objeto.getZ2(); k++){
                    this.mapa[i][j][k] = TipoEntidade.OBSTACULO;
                }
            }
        }        
    }

    public void apaga_obstaculo(Obstaculo objeto){
        for(int i = objeto.getX(); i <= objeto.getX2(); i++){
            for(int j = objeto.getY(); j <= objeto.getY2(); j++){
                for(int k = objeto.getZ(); k <= objeto.getZ2(); k++){
                    this.mapa[i][j][k] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    public boolean registra_robo(InterfaceEntidade robo){
        try {
            this.identifica_colisao(robo.getX(), robo.getY(), robo.getZ());

        } catch (ColisaoException e) {
            System.err.println("Erro ao registrar robô: " + e.getMessage());
            return false;
        }

        this.mapa[robo.getX()][robo.getY()][robo.getZ()] = TipoEntidade.ROBO;
        return true;
    }

    public void apaga_robo(InterfaceEntidade robo){
        this.mapa[robo.getX()][robo.getY()][robo.getZ()] = TipoEntidade.VAZIO;
    }

    public void dentroDosLimites(int x, int y, int z) throws ForaDosLimitesException{
        if (x < 0 || this.X < x || this.Y < y || y < 0 || z < 0 || this.Z < z) throw new ForaDosLimitesException("Fora dos limites!");

    }

    public void adicionarEntidade(InterfaceEntidade adicionar){
        if(adicionar.getTipo() == TipoEntidade.ROBO){
            if(this.registra_robo(adicionar))
                this.elementos.add((Robo)adicionar);
        }
        else if(adicionar.getTipo() == TipoEntidade.OBSTACULO){
            if(this.espaco_vazio((Obstaculo)adicionar)){
                this.registra_obstaculo((Obstaculo)adicionar);
                this.elementos.add((Obstaculo)adicionar);
            }
        }
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

    public void moverEntidade(InterfaceEntidade mover, int novoX, int novoY, int novoZ){
        //funcao que altera as posicoes dos elementos que compoem o ambiente
        try {
            dentroDosLimites(novoX, novoY, novoZ);
            this.identifica_colisao(novoX, novoY, novoZ);

        } catch (ForaDosLimitesException e) {
            System.err.println("Erro: " + e.getMessage());
            return;

        } catch (ColisaoException f) {
            System.err.println("Erro: " + f.getMessage());
            return;
        }

        if(mover.getTipo() == TipoEntidade.ROBO){
            try {
                int deltaX = novoX - mover.getX();
                int deltaY = novoY - mover.getY();
                int deltaZ = novoZ - mover.getZ();
                if (mover instanceof RoboAereoRelator) {
                    if((deltaZ != 0 && (deltaX != 0 || deltaY != 0))){
                        throw new EntradaException("RoboAereoRelator nao eh capaz de realizar movimento multidimensional simultaneamente nas tres dimensoes (movimento dinamico)");
                    }
                    if (deltaZ < 0) {
                        ((RoboAereoRelator)mover).descer(-deltaZ);

                    } else if(deltaZ > 0){
                        ((RoboAereoRelator)mover).subir(deltaZ);
                    }
                    else{
                        ((RoboAereoRelator)mover).mover(deltaX, deltaY);
                    }
                    
                } else if (mover instanceof RoboAereoDinamico) {
                    ((RoboAereoDinamico)mover).moverDinamico(deltaX, deltaY, deltaZ);

                } else if (mover instanceof RoboTerrestreAOleo) {
                    if(deltaZ != 0){
                        throw new EntradaException("Alerta! RoboTerrestreAOleo nao se movimenta no eixo z!\n");
                    }
                    ((RoboTerrestreAOleo)mover).mover(deltaX, deltaY);


                } else {
                    ((RoboTerrestreTopeira)mover).mover(deltaX, deltaY, deltaZ);
                }

            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
        if(mover.getTipo() == TipoEntidade.OBSTACULO){
            InterfaceEntidadeObstaculo objeto = (InterfaceEntidadeObstaculo)mover;
            Obstaculo obstaculo = (Obstaculo)objeto;
            int x1_ini = obstaculo.getX();
            int x2_ini = obstaculo.getX2();
            int y1_ini = obstaculo.getY();
            int y2_ini = obstaculo.getY2();
            int z1_ini = obstaculo.getZ();
            int z2_ini = obstaculo.getZ2();
            int largx = x2_ini - x1_ini;
            int largy = y2_ini - y1_ini;
            int largz = z2_ini - z1_ini;
            
            try {
                dentroDosLimites(novoX  + largx, novoY + largy, novoZ + largz);
                dentroDosLimites(novoX, novoY, novoZ);

            } catch (ForaDosLimitesException e) {
                System.err.println("Erro: " + e.getMessage());
                return;
            }
    
            this.apaga_obstaculo(obstaculo);
            obstaculo.setX(novoX);
            obstaculo.setX2(novoX + largx);
            obstaculo.setY(novoY);
            obstaculo.setY2(novoY + largy);
            obstaculo.setZ(novoZ);
            obstaculo.setZ2(novoZ + largz);
            if(this.espaco_vazio(obstaculo))
                this.registra_obstaculo(obstaculo);
            else{
                obstaculo.setX(x1_ini);
                obstaculo.setX2(x2_ini);
                obstaculo.setY(y1_ini);
                obstaculo.setY2(y2_ini);
                obstaculo.setZ(z1_ini);
                obstaculo.setZ2(z2_ini);
                this.registra_obstaculo(obstaculo);
            }
            
            }
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

    public Robo getRobo(String nome){
        for(InterfaceEntidade entidade: this.elementos){
            if(entidade.getTipo() == TipoEntidade.ROBO){
                Robo busca = (Robo)entidade;
                if(nome.equals(busca.getNome())){
                    return busca;
                }

            }
        }
        return null;
    }

    public TipoEntidade [][][] getMapa(){
        return this.mapa;
    }

    void identifica_colisao(int x, int y, int z) throws ColisaoException{
        if (this.mapa[x][y][z] != TipoEntidade.VAZIO) throw new ColisaoException("Colisão identificada! " + "Entidade em (" + x + ", " + y + ", " + z + ")");
    }

    public int[][][] getTemperatura() {
        return this.temperatura;
    }

    ArrayList<InterfaceEntidade> getElementos() {
        return this.elementos;
    }
}