public class RoboTerrestreAOleo extends RoboTerrestre {
    float coeficienteDeLubrificacao;
    float VelMaxInstantanea;

    public RoboTerrestreAOleo(int posicaoXo, int posicaoYo, String nome, int velocidademax, Ambiente a, String direcao
    ) {
        super(posicaoXo, posicaoYo, nome, velocidademax, a, direcao);
        this.coeficienteDeLubrificacao = 1;
        this.VelMaxInstantanea = this.getVelocidademax();
    }

    void exibirLubrificacao() {
        System.out.printf("O coeficiente de lubrificação é de %.2f, e portanto, a velocidade máxima instantanea é de %.2f\n", this.coeficienteDeLubrificacao, this.VelMaxInstantanea);
    }

    void AlterarLubrificacao(float valor) {
        if (this.coeficienteDeLubrificacao + valor >= 0 && this.coeficienteDeLubrificacao + valor <= 1) {
            coeficienteDeLubrificacao += valor;
            
            this.VelMaxInstantanea = this.getVelocidademax()*this.coeficienteDeLubrificacao;
        }
    }

    @Override
    void mover(int deltaX, int deltaY) {
        if (Math.abs(deltaX) > this.VelMaxInstantanea || Math.abs(deltaY) > this.VelMaxInstantanea) {
            System.out.printf("Movimento Inválido!\n");
            return;
        }

        int xo = this.getPosicaoX();
        int yo = this.getPosicaoY();
        super.mover(deltaX, deltaY);

        if (deltaX == this.getPosicaoX() - xo && deltaY == this.getPosicaoY() - yo) {
            this.AlterarLubrificacao(-0.1f);
            return;
        } 
    }

}