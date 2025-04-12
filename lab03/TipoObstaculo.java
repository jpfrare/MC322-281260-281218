public enum TipoObstaculo {
    TIPO1(0), 
    TIPO2(6), 
    TIPO3(-5);

    private final int altura;

    TipoObstaculo(int altura) {
        this.altura = altura;
    }

    int getAltura() {
        return this.altura;
    }
}
