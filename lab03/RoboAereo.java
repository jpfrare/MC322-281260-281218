public class RoboAereo extends Robo {
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, String direcao, Sensor sensor){
        super(posXo, posYo, nome, a, direcao, sensor);
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
    void mover(int deltaX, int deltaY){ // funcao de movimento horizontal
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

    @Override
    boolean moverR(int deltaX, int deltaY, int passoX, int passoY, int [][] visitados){
        visitados[passoX][passoY] = 1;
        if(deltaX == 0 && deltaY == 0){ //chegou ao destino
            return true;
        }
        for(int i = 1; i <= 2; i++){
            //iteracao: i igual a 1 move em x, i igual a 2 move em y
            if(i == 1){ //mover em x
                if(deltaX > 0){
                    //primeiro verifica se a andar +1 em x ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    if(!this.getAmbiente().identifica_colisao(this.getPosicaoX() + 1, this.getPosicaoY(), this.getPosicaoZ()) && visitados[passoX + 1][passoY] == 0){
                        this.setPosicaoX(this.getPosicaoX() + 1);
                        if(moverR(deltaX - 1, deltaY, passoX + 1, passoY, visitados))
                            return true;
                    }
                }
                else if(deltaX < 0){
                    //primeiro verifica se a andar -1 em x ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    if(!this.getAmbiente().identifica_colisao(this.getPosicaoX() - 1, this.getPosicaoY(), this.getPosicaoZ()) && visitados[passoX + 1][passoY] == 0){
                        this.setPosicaoX(this.getPosicaoX() - 1);
                        if(moverR(deltaX + 1, deltaY, passoX + 1, passoY, visitados))
                            return true;
                    }
                }
            }
            if(i == 2){//mover em y
                if(deltaY > 0){
                    //primeiro verifica se a andar +1 em y ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    if(!this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY() + 1, this.getPosicaoZ()) && visitados[passoX][passoY + 1] == 0){
                        this.setPosicaoY(this.getPosicaoY() + 1);
                        if(moverR(deltaX, deltaY - 1, passoX, passoY + 1, visitados))
                            return true;
                    }
                }
                else if(deltaY < 0){
                    //primeiro verifica se a andar -1 em y ira cair em uma posicao de obstaculo ou se essa posicao ja foi verificada
                    if(!this.getAmbiente().identifica_colisao(this.getPosicaoX(), this.getPosicaoY() + 1, this.getPosicaoZ()) && visitados[passoX][passoY + 1] == 0){
                        this.setPosicaoY(this.getPosicaoY() - 1);
                        if(moverR(deltaX, deltaY + 1, passoX, passoY + 1, visitados))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getPosicaoZ(), this.getDirecao());
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
