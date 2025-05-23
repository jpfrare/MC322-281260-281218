public class RoboTerrestreTopeira extends RoboTerrestre {
    private final int profundidadeMax; //valor < 0

    public RoboTerrestreTopeira(int posicaoXo, int posicaoYo, String nome, float velocidademax, Ambiente a, 
    int profundidadeMax, int r_sensor) {
        //construtor levando em consideração os novos atributos

        super(posicaoXo, posicaoYo, nome, velocidademax, a, r_sensor);

        this.profundidadeMax = profundidadeMax;
    }

    int getProfundidadeMax() {
        return this.profundidadeMax;
    }

    void mover(int deltaX, int deltaY, int deltaZ) {
        //verificando se a posição final está dentro dos limites
        if (!this.getAmbiente().dentroDosLimites(this.getX() + deltaX, this.getY() + deltaY, 0) || 
        this.getZ() + deltaZ < this.profundidadeMax || this.getZ() + deltaZ > 0) {
            System.out.println("Movimento inválido");
            return;
        }
       
        if (Math.abs(deltaX) >= this.getVelocidademax() || Math.abs(deltaY) >= this.getVelocidademax() || Math.abs(deltaZ) >= this.getVelocidademax()) {
            System.out.println("Movimento inválido");
            return;
        }


        if (this.getZ() + deltaZ == 0 && this.getAmbiente().identifica_colisao(this.getX() + deltaX,
        this.getY() + deltaY, 0)) {
            //a única condição que deve-se ter cuidado, pois o robô topeira pode emergir em um lugar ocupado por obstáculo
            System.out.println("Movimento inválido");
            return;

        } else {
            /*por amor a simplicidade e sabendo que não existe um obstáculo no subsolo,
            supõe-se que sempre existirá um caminho que passa pelo subsolo que garante que o robô chegará ao destino final,
            eliminando a necessidade de algoritmos baseados em backtrack/uso de sensores*/


            this.setPosicaoX(this.getX() + deltaX);
            this.setPosicaoY(this.getY() + deltaY);
            this.setPosicaoZ(this.getZ() + deltaZ);
        }
    }
    
    @Override
    void exibirPosicao() {
        //leva em cosideração a nova direção
        System.out.printf("Robo %s: \n r(x,y,z) = (%d, %d, %d)\n", this.getNome(), this.getX(), this.getY(), this.getZ());
    }

    @Override
    public String getDescricao() {
        return "O único robô que consegue escavar o ambiente e portanto desviar de obstáculos, tal como um robô terrestre, possui velocidade máxima";
    }

    @Override
    public char getRepresentacao() {
        return 't';
    }
}