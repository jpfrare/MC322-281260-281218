package comunicacao;

import java.util.ArrayList;

public class CentralComunicacao {
    private final ArrayList<Robo> comunicaveis;
    
    public CentralComunicacao(){
        this.comunicaveis = new ArrayList<>();
    }

    public void adicionarRobo(Robo r){
        this.comunicaveis.add(r);
    }

    public boolean buscaRobo(String nome){
        for(Robo r : this.comunicaveis){
            if(nome.equals(r.getNome()))
                return true;
        }
        return false;
    }

}
