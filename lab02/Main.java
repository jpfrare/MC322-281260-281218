public class Main {
    
    public static void main(String[] args) {
        Ambiente simulador_1 = new Ambiente(40, 50, 20, 10, 15);
        
        RoboAereoDinamico dinamico_1 = new RoboAereoDinamico(10, 10, 10, 15, "Dinamico1", simulador_1, 4, "norte");
        RoboAereoDinamico dinamico_2 = new RoboAereoDinamico(12, 15, 7, 10, "Dinamico2", simulador_1, 5, "norte");
        RoboAereoDinamico dinamico_3 = new RoboAereoDinamico(12, 15, 16, 25, "Dinamico3", simulador_1, 3, "norte");
        RoboTerrestre terrestre_1 = new RoboTerrestre(1, 6, "Robo01", 3, simulador_1, "norte");
        RoboAereoRelator relator_1 = new RoboAereoRelator(35, 20, 12, 25, "Terrestre01", simulador_1, "norte");
        
        terrestre_1.exibirPosicao();
        System.out.println("");
        dinamico_1.exibirPosicao();
        dinamico_1.mover(10, 5, 8);
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,-2,1);
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,-2,1);
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,-2,1, simulador_1);
        dinamico_1.exibirPosicao();

        System.out.println("");
        dinamico_2.exibirPosicao();
        dinamico_3.exibirPosicao();
        relator_1.exibirPosicao();
        relator_1.gerar_relatorio(simulador_1.robos);
    }
}
