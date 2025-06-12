package missao;

import interfaces.InterfaceMissao;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import robos.*;

public class MissaoBuscarPonto implements InterfaceMissao{

    public MissaoBuscarPonto(){}

    @Override public void executar(Robo r, String caminhoArquivo) {
        try {
            Scanner leitor = new Scanner(System.in);
            PrintStream console = System.out;
            int novo_x, novo_y, novo_z;

            System.out.println("digite as novas coordenadas x y z");
            novo_x = leitor.nextInt();
            novo_y = leitor.nextInt();
            novo_z = leitor.nextInt();

            PrintStream arquivo = new PrintStream(new FileOutputStream(caminhoArquivo));

            System.setOut(arquivo);
            System.out.println("Iniciando missão debusca de ponto do robô " + r.getNome());
            r.getAmbiente().moverEntidade(r, novo_x, novo_y, novo_z);
            System.setOut(console);

            System.out.println("Relatório da missão no log!");

            leitor.close();

        } catch (FileNotFoundException e) {
            System.err.println("Erro na escrita do arquivo!");
        }

    }
}
 