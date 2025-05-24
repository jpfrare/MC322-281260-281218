public class RoboTerrestreAOleo extends RoboTerrestre {
    float coeficienteDeLubrificacao; //coeficiente que limita a velocidade máxima (velmax = coef*velmax), toda ação custa lubrificação
    float VelMaxInstantanea; //parametro que lida com o fato da velocidade máxima ser do tipo final

    public RoboTerrestreAOleo(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a, int r_sensor) {
        //construtor que leva em consideração os novos atributos, inicializando a lubrificação com 100%
        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);
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

        } else if (this.coeficienteDeLubrificacao + valor > 1) {
          this.coeficienteDeLubrificacao = 1;  

        } else {
            System.out.println("Valor inválido de lubrificação! lembre-se que é um valor decimal!");
        }
    }

    @Override
    void mover(int deltaX, int deltaY) {
        //move-se, priorizando se o movimento atende as condições de lubrificação atuais do robô
        if (Math.abs(deltaX) > this.VelMaxInstantanea || Math.abs(deltaY) > this.VelMaxInstantanea) {
            System.out.printf("Movimento Inválido! Baixa Lubrificação\n");
            return;
        }

        int xo = this.getX();
        int yo = this.getY();
        super.mover(deltaX, deltaY);


        if (deltaX == this.getX() - xo && deltaY == this.getY() - yo) {
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

    @Override
    void exibirPosicao() {
        super.exibirPosicao();
        exibirLubrificacao();
    }

    @Override
    public String getDescricao() {
        return "Sua velocidade máxima, por ser robô terrestre, depende do quanto lubrificado ele está!";
    }

    @Override public char getRepresentacao() {
        return 'o';
    }

}