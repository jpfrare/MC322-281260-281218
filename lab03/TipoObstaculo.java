public enum TipoObstaculo {
    MURO(10, 0), 
    BLOCO(1, 0),
    BALAO(1, 10), 
    PLACA(2, 0);

    private final int altura; //dimensao da altura;
    private final int apoio;  //localizacao do obstaculo na vertical(ex: apoio igual a zero, base do obstaculo no chao)

    TipoObstaculo(int altura, int apoio) {
        this.altura = altura;
        this.apoio = apoio;
    }

    int getAltura() {
        return this.altura;
    }

    int getLocalApoio(){
        return this.apoio;
    }
}
