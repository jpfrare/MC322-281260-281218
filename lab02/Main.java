public class Main {
    
    public static void main(String[] args) {
        RoboTerrestre teste1 = new RoboTerrestre(1,1,"julio", 3);

        teste1.mover(-1, -2);

        teste1.exibirPosicao();

        teste1.mover(2,2);

        teste1.exibirPosicao();

        teste1.mover(4,1);

        teste1.exibirPosicao();
    }
}
