package obstaculo;

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

    int getAltura() {
        return this.altura;
    }

    boolean getBloqueia(){
        return this.bloqueia;
    }

    
}
