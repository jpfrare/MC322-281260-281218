
class Main {
    public static void main(String[] args) {
        
        Robo r1;
        Ambiente a1;

        r1 = new Robo(1, 1, "Leonardo");
        r1.exibirPosicao();
        r1.mover(2, 2);
        r1.exibirPosicao();

        a1 = new Ambiente(40, 35);
        
        System.out.printf("%b\n", a1.dentroDosLimites(r1.getX(), r1.getY()));

        r1.mover(40, 20);
        r1.exibirPosicao();

        System.out.printf("%b\n", a1.dentroDosLimites(r1.getX(), r1.getY()));

        r1.mover(-70, -80);
        r1.exibirPosicao();

        System.out.printf("%b\n", a1.dentroDosLimites(r1.getX(), r1.getY()));
    }
}