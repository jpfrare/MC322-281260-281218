public enum TipoObstaculo {
    MURO(10), 
    BLOCO(1),
    PLACA(2);

    private final int altura; //dimensao da altura;

    TipoObstaculo(int altura) {
        this.altura = altura;
    }

    int getAltura() {
        return this.altura;
    }
}
