package robos;

import ambiente.*;

public abstract class RoboAereo extends Robo {
    private final int altitudeMax;

    public RoboAereo(int posXo, int posYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posXo, posYo, nome, a, r_sensor);
        this.setPosicaoZ(alt_o);
        this.altitudeMax = alt_max;
    }

    public void subir(int delta_h){

        try {
            this.Robofunciona();

            int pos_inicial = this.getZ();
            int pos_final = this.getZ() + delta_h; //posicao final prevista (caso seja um movimento valido)
            int avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_h, this.getAmbiente());
            this.getAmbiente().dentroDosLimites(this.getX(), this.getY(), pos_final);

            if((pos_final <= this.altitudeMax)){
                while(avancar > 0){
                    this.setPosicaoZ(this.getZ() + avancar);
                    delta_h -= avancar;
                    avancar = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), delta_h, this.getAmbiente());
                }
                if(delta_h > 0){ // encontrou uma posicao de um obstaculo antes de chegar ao destino
                    this.setPosicaoZ(pos_inicial);
                }
                else{ //nao ocorreu colisao durante o caminho ate o destino
                    this.getAmbiente().getMapa()[this.getX()][this.getY()][pos_inicial] = TipoEntidade.VAZIO;
                    this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.ROBO;
                }
                
            }
            else{
                System.err.println("Movimento Invalido de subida! Nao atende as especificacoes do ambiente e/ou do robo");
            }

        } catch (ForaDosLimitesException e) {
            System.err.println("Erro: " + e.getMessage());

        } catch (RoboDesligadoException f) {
            System.err.println("Erro " + f.getMessage());
        }

    }

    int getAltitudeMax(){
        return this.altitudeMax;
    }

    public void descer(int delta_h) throws RoboDesligadoException{
        //apenas desce até uma posicao valida (altura > 0)
        try {
            this.Robofunciona();
            int z_inicial = this.getZ();
            int z_final = this.getZ() - delta_h;
            int avanca = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -delta_h, this.getAmbiente());
            if(z_final >= 0){
                while(avanca > 0){
                    this.setPosicaoZ(this.getZ() - avanca);
                    delta_h -= avanca;
                    avanca = this.getSensorMovimento().consegueAvancar(3, this.getX(), this.getY(), this.getZ(), -delta_h, this.getAmbiente());
                }
                if(delta_h != 0) { //encontrou um obstaculo antes de chegar ao ponto final
                    this.setPosicaoZ(z_inicial);
                }
                else{   //nao houve colisao no meio do caminho: percurso bem sucedido
                    this.getAmbiente().getMapa()[this.getX()][this.getY()][z_inicial] = TipoEntidade.VAZIO;
                    this.getAmbiente().getMapa()[this.getX()][this.getY()][this.getZ()] = TipoEntidade.ROBO;
                }
            }else{
                System.err.println("Movimento invalido de descida! Altura abaixo de 0 somente é possível para o RoboTopeira!");

            }
        
        } catch (RoboDesligadoException e) {
            System.err.println("Erro " + e.getMessage());
        }
    }

    @Override public boolean procura(int x, int y, int raio) {
        //vê se tem algum robô no range do sensor, acima e abaixo
        try {
            if (x != this.getX() || y != this.getY()) this.getAmbiente().identifica_colisao(x, y, this.getZ());

            int menorz;
            int maiorz;

            //definindo os limites de iteração
            if(this.getZ() + raio > this.getAmbiente().getAmbienteZ()) {
                maiorz = this.getAmbiente().getAmbienteZ();
            } else {
                maiorz = this.getZ() + raio;
            }
            
            if (this.getZ() - raio < 0) {
                menorz = 0;
            } else {
                menorz = this.getZ() - raio;
            }

            for (int z = menorz; z <= maiorz; z++) {
                if (this.getAmbiente().tipoPosicao(x, y, z) == TipoEntidade.ROBO) {
                    return true;
                }
            }
            return false;
        
        } catch (ColisaoException e) {
            return false;
        }
    }

    @Override public void fugir() {
        //Robôs aéreos fogem de robôs abaixo ou acima, faz a tentativa de fuga apenas num quadrado ao redor do seu raio
        try {
            this.Robofunciona();
            int raio = this.getSensorMovimento().getRaio();
            if (!procura(this.getX(), this.getY(), raio)) {
                System.out.println("não há robô acima, dentro do raio do sensor, está tudo bem!");
                return;
            }

            int menorx, maiorx, menory, maiory;

            //pegando os limites das variáveis
            if (this.getX() - raio < 0) {
                menorx = 0;
            } else {
                menorx = this.getX() - raio;
            }

            if(this.getX() + raio > this.getAmbiente().getAmbienteX()) {
                maiorx = this.getAmbiente().getAmbienteX();
            } else {
                maiorx = this.getX() + raio;
            }

            if(this.getY() - raio < 0) {
                menory = 0;
            } else {
                menory = this.getY() - raio;
            }

            if(this.getY() + raio > this.getAmbiente().getAmbienteY()){
                maiory = this.getAmbiente().getAmbienteY();
            } else {
                maiory = this.getY() + raio;
            }

            for (int x = menorx; x <= maiorx; x++) {
                for (int y = menory; y <= maiory; y++) {
                    if ((x != this.getX() || y != this.getY()) && !this.procura(x, y, raio)) {
                        System.out.printf("Tentando mover para (%d, %d, %d)...\n", x, y, this.getZ());

                        if(this.mover(x - this.getX(), y - this.getY())) {
                            System.out.println("Agora o robô " + this.getNome() + " está seguro!");
                            return;
                        }

                        System.out.println("falhou! tentando de novo...");
                    }
                }
            }

            System.out.println("Não deu certo! há um robô no mesmo xy deste em toda a região ou obstáculo que o impeça de fugir! Desligando...");
            this.desligarRobo();
            return;
            

        } catch (RoboDesligadoException e) {
            System.err.println(e.getMessage());
        }
    }

}