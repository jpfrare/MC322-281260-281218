package Robos;

public class RoboTerrestreAOleo extends RoboTerrestre implements InterfaceFurtoCombustivel{
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

    @Override public float getCoeficiente(){
        return this.coeficienteDeLubrificacao;
    }

    @Override public void setCoeficiente(float valor) {
        //altera a lubrificação, e portanto, a velocidade máxima
        if (valor >= 0 && valor <= 1) {
            this.coeficienteDeLubrificacao = valor;  
            this.VelMaxInstantanea = this.getVelocidademax()*this.coeficienteDeLubrificacao;

        } else if (valor > 1) {
          this.coeficienteDeLubrificacao = 1;  

        } else {
            System.out.println("Valor inválido de lubrificação! lembre-se que é um valor decimal!");
        }
    }

    @Override public float perder_combustivel(float quantidade){
        float maximo;
        float perda;
        // limitar o furto de combustivel a 50% ou o necessario para completar o combustivel de quem ira furtar
        if(quantidade > 0.5f){
            maximo = 0.5f;
        }
        else{
            maximo = quantidade;
        }
        //dado o limite anterior de furto, verificar a quantidade de combustiveis que esta disponivel para furto
        if(maximo > this.getCoeficiente()){
            perda = this.getCoeficiente();
            this.setCoeficiente(0);
        }
        else{
            perda = maximo;
            this.setCoeficiente(this.getCoeficiente() - maximo);
        }
        return perda;

    }

    @Override public void furtar_combustivel(InterfaceFurtoCombustivel furtado) throws EntradaException{
        try {    
            float furto = furtado.perder_combustivel(1 - this.getCoeficiente());
            this.setCoeficiente(this.getCoeficiente() + furto);
            System.out.printf("Porcentagem furtada: %.2f\n", furto);
        }
        catch(EntradaException e){
            return;
        }
    }

    @Override
    boolean mover(int deltaX, int deltaY) throws RoboDesligadoException {
        //move-se, priorizando se o movimento atende as condições de lubrificação atuais do robô
        if (Math.abs(deltaX) > this.VelMaxInstantanea || Math.abs(deltaY) > this.VelMaxInstantanea) {
            System.err.printf("Movimento Inválido! Baixa Lubrificação\n");
            return false;
        }

        boolean moveu = super.mover(deltaX, deltaY);

        if (moveu) {
            /*se ele conseguiu se mover, desce a lubrificação pela média das razoes
            do tamanho do deslocamento pelo tamanho do ambiente em cada coordenada*/
            int ambienteX = this.getAmbiente().getAmbienteX();
            int ambienteY = this.getAmbiente().getAmbienteY();

            float coefx = Math.abs((float) deltaX)/ambienteX;
            float coefy = Math.abs((float) deltaY)/ambienteY;

            float coeffinal = (coefx + coefy)/(2);

            this.setCoeficiente(this.coeficienteDeLubrificacao - coeffinal);
            return true;
        }

        return false;
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