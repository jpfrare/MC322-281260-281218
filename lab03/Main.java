

public class Main {
    
    public static void main(String[] args) {
        Ambiente teste = new Ambiente(10,10,30 );
        TipoObstaculo muro = TipoObstaculo.MURO;
        teste.adicionaObstaculo(3, 5, 2, 4, muro);
        
        System.out.println("");


        Sensor sensor = new Sensor(3);
        RoboTerrestre r1 = new RoboTerrestre(0, 0, null, 30, teste, "norte", sensor);
        r1.mover(5, 5);
        r1.exibirPosicao();
        r1.mover(-2, -4);
        r1.exibirPosicao();


        System.out.println("");

        RoboAereoDinamico rd1 = new RoboAereoDinamico(5,6,9,25,"dinamico010", teste, 7, "leste", sensor);
        rd1.moverDinamico(-2,-5,2);
        rd1.exibirPosicao();
        

        System.out.println("");
    }
}
