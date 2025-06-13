package missao;

import interfaces.InterfaceMissao;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import robos.*;

public class MissaoBuscarPonto implements InterfaceMissao{

    private Scanner leitor;

    public MissaoBuscarPonto(Scanner s){
        this.leitor = s;
    }

    @Override public void executar(Robo r, String caminhoArquivo) {
        try {
            PrintStream console = System.out;
            int novo_x, novo_y, novo_z;

            System.out.println("digite as novas coordenadas x y z");
            novo_x = leitor.nextInt();
            novo_y = leitor.nextInt();
            novo_z = leitor.nextInt();
            leitor.nextLine();
            
            PrintStream arquivo = new PrintStream(new FileOutputStream(caminhoArquivo, true), true, "UTF-8");

            System.setOut(arquivo);
            System.out.println("Iniciando missão de busca de ponto do robo " + r.getNome());
            System.out.printf("Posicao inicial: (%d,%d,%d)\n", r.getX(), r.getY(), r.getZ());
            r.getAmbiente().moverEntidade(r, novo_x, novo_y, novo_z);
            System.out.printf("Posicao final: (%d,%d,%d)\n", r.getX(), r.getY(), r.getZ());
            System.out.println();
            System.setOut(console);
            arquivo.close();

            System.out.println("Relatório da missão no log!");


        } catch (FileNotFoundException | java.io.UnsupportedEncodingException e) {
            System.err.println("Erro na escrita do arquivo");
        }
        

    }
}
 