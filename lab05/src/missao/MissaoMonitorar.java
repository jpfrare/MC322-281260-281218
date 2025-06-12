package missao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import enums.TipoEntidade;
import interfaces.InterfaceMissao;
import robos.*;

public class MissaoMonitorar implements InterfaceMissao{
    @ Override public void executar(Robo r, String caminhoArquivo) {
        try {
            PrintStream console = System.out;
            PrintStream arquivo = new PrintStream(new FileOutputStream(caminhoArquivo));

            System.setOut(arquivo);
            System.out.println("Iniciando missão de monitoramento do robô " + r.getNome());

            if (r.getZ() < 0) {
                System.out.println( r.getNome() + " está debaixo da terra, não há nada o que monitorar!");
            
            } else {
                int raio = r.getSensorMovimento().getRaio();
                int menorx, maiorx;
                int menory, maiory;
                int menorz, maiorz;

                //definindo limites de iteração
                menorx = r.getX() - raio < 0 ? 0 : r.getX() - raio;
                maiorx = r.getX() + raio > r.getAmbiente().getAmbienteX() ? r.getAmbiente().getAmbienteX() : r.getX() + raio;

                menory = r.getY() - raio < 0 ? 0 : r.getY() - raio;
                maiory = r.getY() + raio > r.getAmbiente().getAmbienteY() ? r.getAmbiente().getAmbienteY() : r.getY() + raio;

                menorz = r.getZ() - raio < 0 ? 0 : r.getZ() - raio;
                maiorz = r.getZ() + raio > r.getAmbiente().getAmbienteZ() ? r.getAmbiente().getAmbienteZ() : r.getZ() + raio;

                for (int i = menorx; i <= maiorx; i++) {
                    for (int j = menory; j <= maiory; j++) {
                        for (int k = menorz; k <= maiorz; k++) {

                            if (!(i == r.getX() && j == r.getY() && k == r.getZ())){

                                TipoEntidade tipo = r.getAmbiente().tipoPosicao(i, j, k);
                                if (tipo == TipoEntidade.OBSTACULO) {
                                    System.out.printf("Obstáculo identificado em (%d, %d, %d)\n", i,j,k);

                                } else if (tipo == TipoEntidade.ROBO) {
                                    System.out.printf("Robô identificado em (%d, %d, %d)\n", i,j,k);
                                }
                            }
                        }
                    }
                }
            }

                System.setOut(console);
                System.out.println("Relatório da missão no log!");

            } catch (FileNotFoundException e) {
                System.err.println("Erro na escrita do arquivo!");
            }

    }
}
