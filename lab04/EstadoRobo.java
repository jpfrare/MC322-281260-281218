public enum EstadoRobo {
    LIGADO(true),
    DESLIGADO(false);

    boolean estado;

    private EstadoRobo(boolean estado) {
        this.estado = estado;
    }

    boolean esta_ligado() {
        return estado;
    }

}
