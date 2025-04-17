public abstract class Robo {
    private final String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private final Ambiente habitat;
    private final Sensor sensor;

    public Robo (int posicaoXo, int posicaoYo, String nome, Ambiente habitat, String direcao, Sensor sensor) {
        //construtor padrão
        this.posicaoX = posicaoXo;
        this.posicaoY = posicaoYo;
        this.nome = nome;
        this.habitat = habitat;
        this.direcao = direcao;
        this.posicaoZ = 0;
        this.habitat.adicionaRobo(this);
        this.sensor = sensor;
        this.habitat.getMapa()[posicaoXo][posicaoYo] = 1;
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

    void setDirecao(String direcao){
        //muda a direção do robô
        this.direcao = direcao;
    }

    String getDirecao() {
        //retorna a direção do robô
        return this.direcao;
    }

    Ambiente getAmbiente() {
        //retorna o ambiente do qual o robô pertence
        return this.habitat;
    }

    boolean moverR(int deltaX, int deltaY) {
        if (this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY] != 0) return false;
        //está em uma posição inválida
        
        if (this.sensor.getRaio() >= Math.sqrt(deltaX*deltaX + deltaY*deltaY)) {

            if (this.getAmbiente().getMapa()[this.posicaoX + deltaX][this.posicaoY + deltaY] != 0) return false;

            //nesse caso, a posição desejada esta no alance do sensor e está desocupada
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;

            return true;

        } else {
            /*tenta se mover sempre começando pelo range máximo do sensor básico, primeiro no eixo X, depois no Y
            se não, diminui o passo em 1*/
            for (int passox = this.sensor.getRaio(); passox >= 0; passox--) {
                int xo = this.posicaoX;
                int deltaxo = deltaX;

                if (deltaX != 0 && passox <= Math.abs(deltaX)) {
                    if (deltaX > 0) {
                        deltaX -= passox;
                        this.posicaoX += passox;
                    } else {
                        deltaX += passox;
                        this.posicaoX -= passox;
                    }
    
                    if (!moverR(deltaX, deltaY)){
                        //não conseguiu fazer o movimento, retorna a posição em que estava
                        this.posicaoX = xo;
                        deltaX = deltaxo;
    
                    } else {
                        return true;
                    }
                }

                for (int passoy = this.sensor.getRaio(); passoy >= 0; passoy--) {

                    int yo = this.posicaoY;
                    int deltayo = deltaY;

                    if (deltaY != 0 && passoy <= Math.abs(deltaY)) {
                        if (deltaY > 0) {
                            deltaY -= passoy;
                            this.posicaoY += passoy;
                        } else {
                            deltaY += passoy;
                            this.posicaoY -= passoy;
                        }

                        if (!moverR(deltaX, deltaY)){
                            //semelhantemente ao eixo X
                            deltaY = deltayo;
                            this.posicaoY = yo;
                            return false;
                        }

                        return true;
                    }
                }
            }
        return false;
        }
    }

    void mover(int deltaX, int deltaY){
        if (!this.habitat.dentroDosLimites(deltaX, deltaY, 0)) return; //confere se a região está dentro dos limites
        int xo = this.posicaoX;
        int yo = this.posicaoY;
        this.getAmbiente().getMapa()[xo][yo] = 0;

        if (moverR(deltaX, deltaY)) {
            this.getAmbiente().getMapa()[this.posicaoX][this.posicaoY] = 1;

        } else {
            this.getAmbiente().getMapa()[xo][yo] = 1;
            System.out.println("movimento não realizado");
        }

    }


    void exibirPosicao() {
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d), direcao %s\n", this.getNome(), this.getPosicaoX(), this.getPosicaoY(), this.getDirecao());
    }


}
