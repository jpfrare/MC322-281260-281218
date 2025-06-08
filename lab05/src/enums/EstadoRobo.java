package enums;

public enum EstadoRobo {
    LIGADO(true),
    DESLIGADO(false);

    boolean estado;

    private EstadoRobo(boolean estado) {
        this.estado = estado;
    }

    public boolean esta_ligado() {
        return estado;
    }

}