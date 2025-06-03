package Robos;

public class RoboAereoRelator extends RoboAereo {
    //robo aereo capaz de identificar todos os robos que estao a uma altura abaixo dele e emitir um relatorio de sua posicao
    int n_relatorios;

    public RoboAereoRelator(int posicaoXo, int posicaoYo, int alt_o, int alt_max, String nome, Ambiente a, int r_sensor){
        super(posicaoXo, posicaoYo, alt_o, alt_max, nome, a, r_sensor);
        n_relatorios = 0; //numero de relatorios emitidos pelo robo
    }

    void gerar_relatorio(){
        //gera um relatorio do conjunto de robos (aereos ou terrestres) que estao a uma altura abaixo do RoboAereoRelator
        int altura_corte = this.getZ(); 
        this.n_relatorios++; 

        System.out.println("\nRelatorio numero " + this.getNrelatorios() + " do Robo " + this.getNome() + " realizado a uma altura " + this.getZ());
        for(InterfaceEntidade r: this.getAmbiente().getElementos()){
            if(r.getTipo() == TipoEntidade.ROBO && r.getZ() < altura_corte && r.getZ() >= 0){
                System.out.printf("Representação: %c. Posicão (%d, %d, %d)\n", r.getRepresentacao(), r.getX(), r.getY(), r.getZ());
            }
        }

        System.out.println("Fim do relaório!\n");
    }

    int getNrelatorios(){ //retorna a quantidade de relatorios feitos pelo robo
        return this.n_relatorios;
    }

    @Override
    public String getDescricao() {
        return "Robo Aéreo que faz relatórios sobre os robôs que estão abaixo de sua altura, exibindo seu nome e posição";
    }

    @Override
    public char getRepresentacao() {
        return 'r';
    }
}