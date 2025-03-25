import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    private final int X_obstaculo;
    private final int Y_obstaculo;
    public final ArrayList<Robo> robos;

    public Ambiente(int x, int y, int x_obstaculo, int y_obstaulo) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.X_obstaculo = x_obstaculo;
        this.Y_obstaculo = y_obstaulo;
        this.robos = new ArrayList<>();
    }

    public boolean eh_obstaculo(int x, int y){
        //retorna true se as coordenadas x e y no parametro correspondem a localizacao x e y do obstaculo
        return (x == X_obstaculo && y == Y_obstaculo);
    }

    public boolean dentroDosLimites(int x, int y, int z) {
        //retorna true se o robo esta dentro do ambiente e false caso contrario
        return (x >= 0 && this.X >= x && this.Y >= y && y >= 0);

    }

    public void adicionaRobo(Robo robo){
        //adiciona um objeto da classe robo ao array de robos
        this.robos.add(robo);

    }

    public int getArrayTamanho() {
        return this.robos.size();
    }

    public Robo getRobo(int pos){
        return this.robos.get(pos);
    }

}
