package enums;


public enum TipoObstaculo {
    MURO(10, true), 
    BLOCO(5, true),
    PLACA(2, false);

    private final int altura; //dimensao da altura;
    private final boolean bloqueia;

    TipoObstaculo(int altura, boolean bloqueia) {
        this.altura = altura;
        this.bloqueia = bloqueia;
    }

    public int getAltura() {
        return this.altura;
    }

    public boolean getBloqueia(){
        return this.bloqueia;
    }

    
}
