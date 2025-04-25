public class RoboAereo extends Robo {
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, Sensor sensor){
        super(posXo, posYo, nome, a, sensor);
        this.setPosicaoZ(alt_o);
        this.altitudeMax = alt_max;
        this.getAmbiente().getMapa()[posXo][posYo][alt_o] = 1;
    }

    void subir(int delta_h){
        int pos_inicial = this.getPosicaoZ();
        int pos_final = this.getPosicaoZ() + delta_h; //posicao final prevista (caso seja um movimento valido)
        if(this.getAmbiente().dentroDosLimites(this.getPosicaoX(), this.getPosicaoY(), pos_final) && (pos_final <= this.altitudeMax)){
            for(int i = 0; i < delta_h; i++){
                if(this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ())){
                    System.out.printf("Movimento invalido de subida! Obstaculo encontrado em ( %d, %d, %d)", this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ());
                    this.setPosicaoZ(pos_inicial);
                    return;
                }
            }
            this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][pos_inicial] = 0;
            this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
        }
        else{
            System.out.println("Movimento Invalido de subida! Nao atende as especificacoes do ambiente e/ou do robo");
        }
    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }
    @Override
    void mover(int deltaX, int deltaY){
        int x_final = this.getPosicaoX() + deltaX;
        int y_final = this.getPosicaoY() + deltaY;
        int x_ini = this.getPosicaoX();
        int y_ini = this.getPosicaoY();

        if(this.getAmbiente().dentroDosLimites(x_final, y_final, this.getPosicaoZ())){
            if(this.getAmbiente().getMapa()[x_final][y_final][this.getPosicaoZ()] == 0){
                //posicao final nao é um obstaculo
                int x_abs = Math.abs(deltaX);
                int y_abs = Math.abs(deltaY);
                int [][] visitados = new int[x_abs + 1][y_abs + 1];
                for(int i = 0; i < x_abs; i++){
                    for(int j = 0; j < y_abs; j++){
                        visitados[i][j] = 0;
                    }
                }
                if(!moverR(deltaX, deltaY, 0, 0, visitados)){
                    this.setPosicaoX(x_ini);
                    this.setPosicaoY(y_ini);
                }
                else{
                    this.getAmbiente().getMapa()[x_ini][y_ini][this.getPosicaoZ()] = 0;
                    this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
                }
            }
        }
    }
/* 
    @Override
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
*/
    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ());
    }

    void descer(int delta_h){
        //apenas desce até uma posicao valida (altura > 0)
        int z_inicial = this.getPosicaoZ();
        int z_final = this.getPosicaoZ() - delta_h;
        if(z_final >= 0){
            for(int i = 0; i < delta_h; i++){
                this.setPosicaoZ(this.getPosicaoZ() - 1);
                if(this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ())){
                    System.out.printf("Movimento invalido de descida! Obstaculo encontrado em ( %d, %d, %d)", this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ());
                    this.setPosicaoZ(z_inicial);
                    return;
                }
            }
            this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][z_inicial] = 0;
            this.getAmbiente().getMapa()[this.getPosicaoX()][this.getPosicaoY()][this.getPosicaoZ()] = 1;
        }else{
            System.out.println("Movimento invalido de descida! Altura abaixo de 0 somente é possível para o RoboTopeira!");

        }
    }

}
