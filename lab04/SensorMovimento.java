public class SensorMovimento extends Sensor{
    public SensorMovimento(int r){
        super(r);
    }

    int consegueAvancar(int mov, int x, int y, int z, int falta, Ambiente espaco){
        //retorna a maior distancia que o robo pode avancar limitado ao raio do sensor
        //passo é a distancia que o Robo ira se mover em uma direcao no seu percurso
        int passo;
        
        if(mov == 1){ //"mover em x"
            if(falta > 0){ //mover no sentido positivo de x
                for(passo = 1; passo <= this.getRaio() && passo <= falta; passo++){
                    try {
                        espaco.identifica_colisao(x + passo, y, z);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
            else if(falta < 0){//mover no sentido negativo de x
                for(passo = 1; passo <= this.getRaio() && passo <= -falta; passo++){ 
                    try {
                        espaco.identifica_colisao(x - passo, y, z);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
        }
        else if(mov == 2){ //mover em y
            if(falta > 0){//sentido positivo; delta y > 0
                for(passo = 1; passo <= this.getRaio() && passo <= falta; passo++){
                    try {
                        espaco.identifica_colisao(x, y + passo, z);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
            else if(falta < 0){//sentido negativo; delta y < 0
                for(passo = 1; passo <= this.getRaio() && passo <= -falta; passo++){
                    try {
                        espaco.identifica_colisao(x, y - passo, z);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
        }
        else if(mov == 3){ // mover em z
            if(falta > 0){//sentido positivo de z
                for(passo = 1; passo <= this.getRaio() && passo <= falta; passo++){
                    try {
                        espaco.identifica_colisao(x, y, z + passo);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
            else if(falta < 0){//sentido negativo de z
                for(passo = 1; passo <= this.getRaio() && passo <= -falta; passo++){
                    try {
                        espaco.identifica_colisao(x, y, z - passo);
                    } catch(ColisaoException e) {
                        return passo - 1;
                    }
                }
                return passo - 1;
            }
        }
        return 0;
    }
}