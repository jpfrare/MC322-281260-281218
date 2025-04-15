public class RoboTerrestreAOleo extends RoboTerrestre {
    float coeficienteDeLubrificacao; //coeficiente que limita a velocidade máxima (velmax = coef*velmax), toda ação custa lubrificação
    float VelMaxInstantanea; //parametro que lida com o fato da velocidade máxima ser do tipo final

    public RoboTerrestreAOleo(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, String direcao, Sensor sensor
    ) {
        //construtor que leva em consideração os novos atributos, inicializando a lubrificação com 100%
        super(posicaoXo, posicaoYo, nome, velocidademax, a, direcao, sensor);
        this.coeficienteDeLubrificacao = 1;
        this.VelMaxInstantanea = this.getVelocidademax();
    }

    void exibirLubrificacao() {
        //exibe a lubrificação e qual a velocidade máxima atual do robô
        System.out.printf("O coeficiente de lubrificação é de %.2f, e portanto, a velocidade máxima instantanea é de %.2f\n", this.coeficienteDeLubrificacao, this.VelMaxInstantanea);
    }

    void AlterarLubrificacao(float valor) {
        //altera a lubrificação, e portanto, a velocidade máxima
        if (this.coeficienteDeLubrificacao + valor >= 0 && this.coeficienteDeLubrificacao + valor <= 1) {
            coeficienteDeLubrificacao += valor;
            
            this.VelMaxInstantanea = this.getVelocidademax()*this.coeficienteDeLubrificacao;
        }
    }

    @Override
    void mover(int deltaX, int deltaY) {
        //move-se, priorizando se o movimento atende as condições de lubrificação atuais do robô
        if (Math.abs(deltaX) > this.VelMaxInstantanea || Math.abs(deltaY) > this.VelMaxInstantanea) {
            System.out.printf("Movimento Inválido!\n");
            return;
        }

        int xo = this.getPosicaoX();
        int yo = this.getPosicaoY();
        super.mover(deltaX, deltaY);


        if (deltaX == this.getPosicaoX() - xo && deltaY == this.getPosicaoY() - yo) {
            /*se ele conseguiu se mover, desce a lubrificação pela média das razoes
            do tamanho do deslocamento pelo tamanho do ambiente em cada coordenada*/
            int ambienteX = this.getAmbiente().getAmbienteX();
            int ambienteY = this.getAmbiente().getAmbienteY();

            float coefx = Math.abs((float) deltaX)/ambienteX;
            float coefy = Math.abs((float) deltaY)/ambienteY;

            float coeffinal = (coefx + coefy)/(2);

            this.AlterarLubrificacao(-coeffinal);
            return;
        } 
    }

}