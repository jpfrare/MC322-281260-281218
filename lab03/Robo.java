import java.util.ArrayList;

public abstract class Robo {
    private final String nome;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final ArrayList<Sensor> sensores; 
    private final Ambiente habitat;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, Sensor sensor) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.posicaoZ = 0;
        this.habitat.adicionaRobo(this);
        this.sensores = new ArrayList<>();
        sensores.add(sensor);
        this.habitat.getMapa()[posicaoXo][posicaoYo][this.posicaoZ] = 1;
    }


    String getNome() {
        return this.nome;
    }

    void setPosicaoX(int posicaoX) {
        //mudar a posição em X
        this.posicaoX = posicaoX;
    }

    void setPosicaoY(int posicaoY) {
        //mudar a posição em Y
        this.posicaoY = posicaoY;
    }

    int getPosicaoX() {
        //retorna a posicão em X
        return this.posicaoX;
    }

    int getPosicaoY() {
        //retorna a posição em Y
        return this.posicaoY;
    }

    int getPosicaoZ() {
        //esse método torna possível a implementação de uma análise de arraylist do ambiente baseada na dimensão Z
        return this.posicaoZ;
    }

    void setPosicaoZ(int z){
        this.posicaoZ = z;
    }

    Ambiente getAmbiente() {
        //retorna o ambiente do qual o robô pertence
        return this.habitat;
    }

    void AdicionaSensores(Sensor s){
        this.sensores.add(s);
    }

    SensorMovimento getSensorMovimento(){
        return (SensorMovimento)this.sensores.get(0);
    }

    boolean moverR(int deltaX, int deltaY, int passoX, int passoY, int [][] visitados){
        visitados[passoX][passoY] = 1;
        int avancar;
        if(deltaX == 0 && deltaY == 0){ //chegou ao destino
            return true;
        }
        for(int i = 1; i <= 2; i++){
            //iteracao: i igual a 1 move em x, i igual a 2 move em y
            if(i == 1){ //mover em x
                if(deltaX > 0){
                    //primeiro verifica se a andar +1 em x ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    avancar = this.getSensorMovimento().consegueAvancar(1, 1, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), deltaX, this.getAmbiente());
                    while(avancar > 0){
                        if(!this.getAmbiente().identifica_colisao(this.getPosicaoX() + avancar, this.getPosicaoY(), this.getPosicaoZ()) && visitados[passoX + avancar][passoY] == 0){
                            this.setPosicaoX(this.getPosicaoX() + avancar);
                            if(moverR(deltaX - avancar, deltaY, passoX + avancar, passoY, visitados))
                                return true;
                        }
                        avancar--;
                    }
                }
                else if(deltaX < 0){
                    avancar = this.getSensorMovimento().consegueAvancar(1, 2, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), -deltaX, this.getAmbiente()); 
                    //primeiro verifica se a andar -1 em x ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    while(avancar > 0){
                        if(!this.getAmbiente().identifica_colisao(this.getPosicaoX() - avancar, this.getPosicaoY(), this.getPosicaoZ()) && visitados[passoX + avancar][passoY] == 0){
                            this.setPosicaoX(this.getPosicaoX() - avancar);
                            if(moverR(deltaX + avancar, deltaY, passoX + avancar, passoY, visitados))
                                return true;
                        }
                        avancar --;
                    }
                        
                }
            }
            if(i == 2){//mover em y
                if(deltaY > 0){
                    avancar = this.getSensorMovimento().consegueAvancar(2, 1, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), deltaY, this.getAmbiente()); 
                    //primeiro verifica se a andar +1 em y ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    while(avancar > 0){
                        if(!this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY() + avancar, this.getPosicaoZ()) && visitados[passoX][passoY + avancar] == 0){
                            this.setPosicaoY(this.getPosicaoY() + avancar);
                            if(moverR(deltaX, deltaY - avancar, passoX, passoY + avancar, visitados))
                                return true;
                        }
                        avancar --;
                    }
                }
                else if(deltaY < 0){
                    avancar = this.getSensorMovimento().consegueAvancar(2, 2, this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), -deltaY, this.getAmbiente()); 
                    //primeiro verifica se a andar -1 em y ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    while(avancar > 0){
                        if(!this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY() - avancar, this.getPosicaoZ()) && visitados[passoX][passoY + avancar] == 0){
                            this.setPosicaoY(this.getPosicaoY() - avancar);
                            if(moverR(deltaX, deltaY + avancar, passoX, passoY + avancar, visitados))
                                return true;
                        }
                        avancar--;
                    }
                }
            }
        }
        return false;
    }
    void mover(int deltaX, int deltaY){
        if (!this.habitat.dentroDosLimites(this.posicaoX + deltaX, this.posicaoY + deltaY, 0)) return; //confere se a região está dentro dos limites
        int xo = this.posicaoX;
        int yo = this.posicaoY;
        this.getAmbiente().getMapa()[xo][yo][this.posicaoZ] = 0;
        
        int abs_x = Math.abs(deltaX);
        int abs_y = Math.abs(deltaY);
        int [][] visitados = new int[abs_x + 1][abs_y + 1];
        for(int i = 0; i <= abs_x; i++){
            for(int j = 0; j <= abs_y; j++){
                visitados[i][j] = 0;
            }
        }

        if (moverR(deltaX, deltaY, 0, 0, visitados)) {
            this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY][this.posicaoZ] = 1;

        } else {
            this.getAmbiente().getMapa()[xo][yo][this.posicaoZ] = 1;
            System.out.println("movimento não realizado");
        }

    }


    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d)\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY());
    }


}

