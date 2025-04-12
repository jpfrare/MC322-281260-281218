public class Main {
    
    public static void main(String[] args) {
        Ambiente teste = new Ambiente(40,50,30 );
        teste.inicializa_mapa();
        TipoObstaculo muro = TipoObstaculo.MURO;
        TipoObstaculo bloco = TipoObstaculo.BLOCO;
        Obstaculo ob_1 = new Obstaculo(5, 10, 15, 10, muro);
        Obstaculo ob_2 = new Obstaculo(30, 28, 45, 50, bloco);
        teste.adicionaObstaculo(ob_1);
        teste.adicionaObstaculo(ob_2);
        teste.imprime_mapa();
    }
}
