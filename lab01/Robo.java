public class Robo {
    private final String nome;
    private int X;
    private int Y;

    public Robo(int x, int y, String nome) {
        this.X = x;
        this.Y = y;
        this.nome = nome;
    }

    public void mover(int novox, int novoy) {
        this.X = novox;
        this.Y = novoy;
    }

    public void exibirPosicao() {
        System.out.printf("Robo: %s, PosicaoX: %d, PosicaoY: %d\n", this.nome, this.X, this.Y);
    }

}