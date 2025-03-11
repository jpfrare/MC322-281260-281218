public class Ambiente {
    private final int X;
    private final int Y;

    public Ambiente(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && this.X >= x && this.Y >= y && y >= 0);
    }

}
