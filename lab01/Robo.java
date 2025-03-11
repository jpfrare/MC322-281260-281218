public class Robo {
    private final String nome;
    private int x;
    private int y;

    public Robo(int x, int y, String nome) {
        this.x = x;
        this.y = y;
        this.nome = nome;
    }

    public void mover(int deltax, int deltay) {
        this.x += deltax;
        this.y += deltay;
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void exibirPosicao() {
        System.out.printf("Robo: %s, PosicaoX: %d, PosicaoY: %d\n", this.nome, this.x, this.y);
    }

}