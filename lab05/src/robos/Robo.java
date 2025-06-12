package robos;
import ambiente.Ambiente;
import comunicacao.*;
import enums.*;
import exceptions.*;
import interfaces.*;
import java.util.ArrayList;
import sensores.*;

public abstract class Robo implements InterfaceEntidade, InterfaceComunicavel, InterfaceSensoravel {
    private final String nome;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final ArrayList<Sensor> sensores;
    private CentralComunicacao central;
    private CaixadeEntrada caixa;
    private final Ambiente habitat;
    private EstadoRobo estado;
    private TipoEntidade tipo;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, int r_sensor) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.posicaoZ = 0;
        this.sensores = new ArrayList<>();
        SensorMovimento sensor = new SensorMovimento(r_sensor);
        this.central = null;
        this.caixa = null;
        sensores.add(sensor);
        this.estado = EstadoRobo.LIGADO;
        this.tipo = TipoEntidade.ROBO;
    }

    public void ligarRobo() {
        System.out.println("O Robô " + this.getNome() + " está ligado!");
        this.estado = EstadoRobo.LIGADO;
    }

    public void desligarRobo() {
        System.out.println("O Robô " + this.getNome() + " está desligado!");
        this.estado = EstadoRobo.LIGADO;
    }

    public void Robofunciona() throws RoboDesligadoException{
        if (!this.estado.esta_ligado()) {
            throw new RoboDesligadoException("Não realizado, Robô " + this.nome + " desligado!");
        }
    }

    public String getNome() {
        return this.nome;
    }

    public CentralComunicacao getCentral(){
        return this.central;
    }

    public CaixadeEntrada getCaixadeEntrada(){
        return this.caixa;
    }

    public void adicionarComunicacao(CentralComunicacao central){
        this.central = central;
        central.adicionarRobo(this);
        this.caixa = new CaixadeEntrada();
    }

    @Override
    public void enviarMensagem(InterfaceComunicavel destinatario, String mensagem) throws RoboDesligadoException{
        try{
            this.Robofunciona();
        }
        catch(RoboDesligadoException e){
            System.err.println("Erro: " + e.getMessage());
        }
        if(this.central != null && this.caixa != null){
            Robo r = (Robo)destinatario;
            if(this.central.buscaRobo(r.getNome())){
                Mensagem enviar = new Mensagem( (Robo)this, mensagem);
                r.caixa.armazenar_mensagem(enviar);
            }
            else{
                System.out.println("O robo a quem se destina a mensagem eh incomunicavel.");
            }
        }
        else{
            System.out.println("Robo incomunicavel.");
        }
    }

    @Override
    public void receberMensagem() throws RoboDesligadoException{
        try{
            this.Robofunciona();
        }
        catch(RoboDesligadoException e){
            System.err.println("Erro: " + e.getMessage());
        }
        if(this.caixa.getNaoLidas() > 0){
            this.caixa.ler_mensagem();
        }
    }
    
    @Override
    public TipoEntidade getTipo(){
        return tipo;
    }

    public void setPosicaoX(int posicaoX) {
        //mudar a posição em X
        this.posicaoX = posicaoX;
    }

    public void setPosicaoY(int posicaoY) {
        //mudar a posição em Y
        this.posicaoY = posicaoY;
    }
    @Override
    public int getX() {
        //retorna a posicão em X
        return this.posicaoX;
    }
    @Override
    public int getY() {
        //retorna a posição em Y
        return this.posicaoY;
    }
    @Override
    public int getZ() {
        //esse método torna possível a implementação de uma análise de arraylist do ambiente baseada na dimensão Z
        return this.posicaoZ;
    }

    public final void setPosicaoZ(int z){
        this.posicaoZ = z;
    }

    public final Ambiente getAmbiente() {
        //retorna o ambiente do qual o robô pertence
        return this.habitat;
    }

    public void AdicionaSensores(Sensor s){
        this.sensores.add(s);
    }

    public SensorMovimento getSensorMovimento(){
        return (SensorMovimento)this.sensores.get(0);
    }

    public SensorTemperatura getSensorTemperatura() throws IndexOutOfBoundsException{
        if (this.sensores.size() < 2) {
            throw new IndexOutOfBoundsException();

        } else {
            return (SensorTemperatura)this.sensores.get(1);
        }
        
    }

    private boolean moverR(int deltaX, int deltaY, int passoX, int passoY, int [][] visitados){
        int posx_ini = this.getX();
        int posy_ini = this.getY();
        visitados[passoX][passoY] = 1;
        int avancar;
        if(deltaX == 0 && deltaY == 0){ //chegou ao destino
            return true;
        }
        for(int i = 1; i <= 2; i++){
            //iteracao: i igual a 1 move em x, i igual a 2 move em y
            //tenta andar o maior valor em uma direcao (int avancar) que corresponde ao raio de alcance do sensor
            if(i == 1){ //mover em x
                if(deltaX > 0){
                    //busca um  avancar dentro do alcance do sensor que resulte em um caminho valido a partir da posicao inicial
                    avancar = this.getSensorMovimento().consegueAvancar(1, this.getX(), this.getY(), this.getZ(), deltaX, this.getAmbiente());
                    while(avancar > 0){
                        try {
                            this.getAmbiente().identifica_colisao(this.getX() + avancar, this.getY(), this.getZ());
                        } catch (ColisaoException e) {
                            System.err.println("Buscando outro caminho!");
                        }

                        if(visitados[passoX + avancar][passoY] == 0){
                            this.setPosicaoX(this.getX() + avancar);
                            if(moverR(deltaX - avancar, deltaY, passoX + avancar, passoY, visitados))
                                return true;
                            else{
                                this.setPosicaoX(posx_ini);
                            }
                        }
                        avancar--;
                    }
                }
                else if(deltaX < 0){
                    avancar = this.getSensorMovimento().consegueAvancar(1, this.getX(), this.getY(), this.getZ(), deltaX, this.getAmbiente()); 
                    //busca um alcance que resulte em um caminho valido a partir da posicao inicial
                    while(avancar > 0){
                        try {
                            this.getAmbiente().identifica_colisao(this.getX() - avancar, this.getY(), this.getZ());

                        } catch(ColisaoException e) {
                            System.err.println("Buscando outro caminho!");
                        }

                        if(visitados[passoX + avancar][passoY] == 0){
                            this.setPosicaoX(this.getX() - avancar);
                            if(moverR(deltaX + avancar, deltaY, passoX + avancar, passoY, visitados))
                                return true;
                            else{
                                this.setPosicaoX(posx_ini);
                            }
                        }
                        avancar --;
                    }
                        
                }
            }
            if(i == 2){//mover em y
                if(deltaY > 0){
                    avancar = this.getSensorMovimento().consegueAvancar(2, this.getX(), this.getY(), this.getZ(), deltaY, this.habitat); 
                    //busca um alcance que resulte em um caminho valido a partir da posicao inicial
                    while(avancar > 0){
                        try {
                            this.getAmbiente().identifica_colisao(this.getX(), this.getY() + avancar, this.getZ());
                        } catch (ColisaoException e) {
                            System.err.println("Buscando outro caminho!");
                        }

                        if(visitados[passoX][passoY + avancar] == 0){
                            this.setPosicaoY(this.getY() + avancar);
                            if(moverR(deltaX, deltaY - avancar, passoX, passoY + avancar, visitados))
                                return true;
                            else{
                                this.setPosicaoY(posy_ini);
                            }
                        }
                        avancar --;
                    }
                }
                else if (deltaY < 0){
                    //tenta andar o maior valor em uma direcao (int avancar) que corresponde ao raio de alcance do sensor
                    avancar = this.getSensorMovimento().consegueAvancar(2, this.getX(), this.getY(), this.getZ(), deltaY, this.habitat);

                    while(avancar > 0){
                        try {
                            this.getAmbiente().identifica_colisao(this.getX(), this.getY() - avancar, this.getZ());
                        } catch (ColisaoException e) {
                            System.err.println("Buscando outro caminho!");
                        }

                        if(visitados[passoX][passoY + avancar] == 0){
                            this.setPosicaoY(this.getY() - avancar);
                            if(moverR(deltaX, deltaY + avancar, passoX, passoY + avancar, visitados))
                                return true;
                            else{
                                this.setPosicaoY(posy_ini);
                            }
                        }
                        avancar--;
                    }
                }
            }
        }
        return false;
    }

    public boolean mover(int deltaX, int deltaY) throws RoboDesligadoException, ForaDosLimitesException, ColisaoException {

        try {
            this.Robofunciona();
            this.habitat.dentroDosLimites(this.posicaoX + deltaX, this.posicaoY + deltaY, 0);
            this.getAmbiente().identifica_colisao(this.posicaoX + deltaX, this.posicaoY + deltaY, this.posicaoZ);

        } catch (ForaDosLimitesException | ColisaoException | RoboDesligadoException e) {
            System.err.println("Erro: " + e.getMessage());
            return false;

        }

        int xo = this.posicaoX;
        int yo = this.posicaoY;
        this.getAmbiente().getMapa()[xo][yo][this.posicaoZ] = TipoEntidade.VAZIO;
        
        int abs_x = Math.abs(deltaX);
        int abs_y = Math.abs(deltaY);
        int [][] visitados = new int[abs_x + 1][abs_y + 1];
        for(int i = 0; i <= abs_x; i++){
            for(int j = 0; j <= abs_y; j++){
                visitados[i][j] = 0;
            }
        }

        if (moverR(deltaX, deltaY, 0, 0, visitados)) {
            this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY][this.posicaoZ] = TipoEntidade.ROBO;
            System.out.println("Existe caminho!");
            return true;

        } else {
            this.getAmbiente().getMapa()[xo][yo][this.posicaoZ] = TipoEntidade.ROBO;
            System.err.println("Caminho cercado! Impossível avançar!");
            return false;
        }

    }


    public void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getX(), this.getY(), this.getZ());
    }

    @Override public void acionarSensores() throws ForaDosLimitesException, ColisaoException, RoboDesligadoException{
        try {
            this.Robofunciona();
            SensorTemperatura t = this.getSensorTemperatura();
            t.analise_temperatura();

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Este robô não possui sensor de temperatura, ainda");
        }
    }
}