
class Main {
    public static void main(String[] args) {
        
        Robo r1;
        Ambiente a1;

        r1 = new Robo(1, 2, "Leonardo");
        r1.exibirPosicao();
        r1.mover(2, 2);
        r1.exibirPosicao();

        a1 = new Ambiente(40, 35);
        
        System.out.printf("%b %b\n", a1.dentroDosLimites(0, 0), a1.dentroDosLimites(41, 35));

    }
}