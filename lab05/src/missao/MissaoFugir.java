package missao;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


import enums.TipoEntidade;
import exceptions.ColisaoException;
import exceptions.RoboDesligadoException;
import interfaces.InterfaceMissao;
import robos.*;

public class MissaoFugir implements InterfaceMissao{
    @ Override public void executar(Robo r, String caminhoArquivo) {
        try {
            PrintStream console = System.out;
            PrintStream arquivo = new PrintStream(new FileOutputStream(caminhoArquivo));
            System.setOut(arquivo);
            System.out.println("Iniciando missão de fuga do robô " + r.getNome());
            this.fugir(r);
            System.out.close();
            System.setOut(console);
            System.out.println("Relatório da missão no log!");

        } catch (FileNotFoundException e) {
            System.err.println("Erro na escrita de arquivo");
        }
       
    }

    private boolean procura(int x, int y, int raio, Robo robo){
        //vê se tem algum robô no range do sensor, acima e abaixo
        try {
            robo.getAmbiente().identifica_colisao(x, y, robo.getZ());

            int menorz;
            int maiorz;

            //definindo os limites de iteração
            if(robo.getZ() + raio > robo.getAmbiente().getAmbienteZ()) {
                maiorz = robo.getAmbiente().getAmbienteZ();
            } else {
                maiorz = robo.getZ() + raio;
            }
            
            if (robo.getZ() - raio < 0) {
                menorz = 0;
            } else {
                menorz = robo.getZ() - raio;
            }

            for (int z = menorz; z <= maiorz; z++) {
                if (robo.getAmbiente().tipoPosicao(robo.getX(), robo.getY(), z) == TipoEntidade.ROBO) {
                    return true;
                }
            }
            return false;
        
        } catch (ColisaoException e) {
            return false;
        }
    }

    private void fugir(Robo robo){
        try {
            robo.Robofunciona();
            int raio = robo.getSensorMovimento().getRaio();
            if (!procura(robo.getX(), robo.getY(), raio, robo)) {
                System.out.printf("não há robô acima, dentro do raio do sensor, está tudo bem!\n");
                return;
            }

            int menorx, maiorx, menory, maiory;

            //pegando os limites das variáveis
            if (robo.getX() - raio < 0) {
                menorx = 0;
            } else {
                menorx = robo.getX() - raio;
            }

            if(robo.getX() + raio > robo.getAmbiente().getAmbienteX()) {
                maiorx = robo.getAmbiente().getAmbienteX();
            } else {
                maiorx = robo.getX() + raio;
            }

            if(robo.getY() - raio < 0) {
                menory = 0;
            } else {
                menory = robo.getY() - raio;
            }

            if(robo.getY() + raio > robo.getAmbiente().getAmbienteY()){
                maiory = robo.getAmbiente().getAmbienteY();
            } else {
                maiory = robo.getY() + raio;
            }

            for (int x = menorx; x <= maiorx; x++) {
                for (int y = menory; y <= maiory; y++) {
                    if ((x != robo.getX() || y != robo.getY()) && !this.procura(x, y, raio, robo)) {
                        System.out.printf("Tentando mover para (%d, %d, %d)...\n", x, y, robo.getZ());

                        try{
                            if(robo.mover(x - robo.getX(), y - robo.getY())) {
                                System.out.println("Agora o robô " + robo.getNome() + " está seguro!");
                                return;
                            }
                        }
                        catch(Exception e){
                            System.out.println("erro relacionado ao movimento!");
                        }

                        System.out.println("falhou! tentando de novo...");
                    }
                }
            }

            System.out.println("Não deu certo! há um robô no mesmo xy deste em toda a região ou obstáculo que o impeça de fugir! Desligando...");
            robo.desligarRobo();
            

        } catch (RoboDesligadoException e) {
            System.out.println(e.getMessage());

        }
    }
}
