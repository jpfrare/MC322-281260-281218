public class Main {
    
    public static void main(String[] args) {
        Ambiente teste = new Ambiente(10,10,30 );
        TipoObstaculo muro = TipoObstaculo.MURO;
        Obstaculo ob_1 = new Obstaculo(3, 5, 2, 4, muro, teste);
        
        teste.imprime_mapa();
        System.out.println("");


        Sensor sensor = new Sensor(3);
        RoboTerrestre r1 = new RoboTerrestre(0, 0, null, 30, teste, "norte", sensor);
        teste.imprime_mapa();
        r1.mover(9, 9);

        System.out.println("");
        teste.imprime_mapa();
    }
}
