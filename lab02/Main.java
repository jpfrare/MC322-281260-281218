public class Main {
    
    public static void main(String[] args) {
        Ambiente simulador_1 = new Ambiente(40, 50, 20, 10, 15);
        
        RoboAereoDinamico dinamico_1 = new RoboAereoDinamico(10, 10, 10, 15, "Dinamico1", simulador_1, 4, "norte");
        RoboAereoDinamico dinamico_2 = new RoboAereoDinamico(12, 15, 7, 10, "Dinamico2", simulador_1, 5, "norte");
        RoboAereoDinamico dinamico_3 = new RoboAereoDinamico(12, 15, 16, 25, "Dinamico3", simulador_1, 3, "norte");
        RoboTerrestre terrestre_1 = new RoboTerrestre(1, 6, "Robo01", 3, simulador_1, "norte");
        RoboAereoRelator relator_1 = new RoboAereoRelator(35, 20, 12, 25, "Relator01", simulador_1, "norte");
        RoboAereoRelator relator_2 = new RoboAereoRelator(5, 5, 15, 20, "Relator02", simulador_1, "norte");

        terrestre_1.exibirPosicao();
        System.out.println("");
        dinamico_1.exibirPosicao();
        dinamico_1.mover(10, 5, 8); //movimento sera invalido devido extrapolar a altura maxima: robo nao movera
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,-2,1); // o robo executara o movimento e reduzira seu nivel energetico, consequentemente sua altura maxima
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,-2,-1); // o robo executara o movimento e reduzira seu nivel energetico a sua altura maxima
        dinamico_1.recarregar(); //recarrega o robo e aumenta sua altura macima
        dinamico_1.exibirPosicao();
        dinamico_1.mover(5,5,5); //apos recarregado, o robo podera subir ate a sua altura maxima!
        dinamico_1.exibirPosicao();

        System.out.println("");
        dinamico_2.exibirPosicao();
        dinamico_3.exibirPosicao();
        relator_1.exibirPosicao(); //somente podera imprimir um relatorio dos robos que estao abaixo dele (nao exergara os robos dinamico_3 e relator_2)
        relator_1.gerar_relatorio(); //gerará um relatorio com o nome e posicao de cada robo abaixo dele
        relator_2.exibirPosicao();
        relator_2.gerar_relatorio(); //relator_2 acima do relator_1 gerara um relatorio contendo mais robos
        relator_1.subir(8); 
        relator_1.gerar_relatorio(); //apos subir a uma altura maior do que o relator_2, o relator_1 podera visualizar o relator_2

        RoboTerrestreTopeira topeira = new RoboTerrestreTopeira(0, 0, "topeira", 50, simulador_1, -20, "sul");
        topeira.exibirPosicao();
        topeira.mover(30, 20, -10);
        topeira.exibirPosicao();
        topeira.mover(10, 10,  -10);
        topeira.exibirPosicao();
        topeira.mover(0,0, 21);
        topeira.identificarObstaculo();


        System.out.println("");

        RoboTerrestreAOleo oleo = new RoboTerrestreAOleo(0, 0, "oleo", 10, simulador_1, "sul");
        oleo.exibirLubrificacao();

        oleo.mover(10, 10);
        oleo.identificarObstaculo();
        oleo.exibirPosicao();
        oleo.exibirLubrificacao();

        oleo.mover(0, 5); //vai bater no obstaculo
        oleo.exibirPosicao();
        oleo.exibirLubrificacao();

        oleo.mover(0,2); //vai ficar na direção do obstaculo
        oleo.identificarObstaculo();
        oleo.exibirPosicao();
        oleo.mover(-10, -10);
        oleo.exibirPosicao();
        oleo.exibirLubrificacao();

        oleo.AlterarLubrificacao(0.2f);
        oleo.exibirLubrificacao();
    }
}
