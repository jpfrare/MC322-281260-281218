public enum TipoObstaculo {
    MURO(10, true), 
    BLOCO(1, true),
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

    boolean impede_passagem(int h){
        for(TipoObstaculo obstaculo: TipoObstaculo.values()){
            if(obstaculo.getAltura() == h && !obstaculo.bloqueia){
                return false;
            }
        }
        return true;
    }
}
