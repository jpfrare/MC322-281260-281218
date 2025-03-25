import java.util.ArrayList;

public class Ambiente {
    private final int X;
    private final int Y;
    public final ArrayList<Robo> robos;

    public Ambiente(int x, int y) {
        //inicializa o Ambiente atribuindo valores as dimensoes x e y e cria um array vazio de robos
        this.X = x;
        this.Y = y;
        this.robos = new ArrayList<>();
    }

    public boolean eh_obstaculo(ArrayList<Robo> robos, int x, int y){
        //retorna true se as coordenadas x e y no parametro correspondem a localizacao x e y de um obstaculo (tradado como robo)
        int i;
        for(i = 0; i < robos.size(); i++){
            if(robos.get(i).getPosicaoX() == x && robos.get(i).getPosicaoY() == y){
                return true;
            }
        }
        return false;
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
