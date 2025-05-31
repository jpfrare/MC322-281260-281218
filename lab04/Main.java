import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws RoboDesligadoException, EntradaException {
        Scanner leitor = new Scanner(System.in);
        //Criacao do Ambiente:
        System.out.println("\nOlá, bem vindo!\nEm primeiro lugar, saiba que este ambiente de simulacao possui dimensoes: (20,20,20).\n");
        Ambiente amb = new Ambiente(20, 20, 20);
        CentralComunicacao central = new CentralComunicacao();

        //Criacao dos robos e adicao ao ambiente:
        RoboTerrestreTopeira r_top = new RoboTerrestreTopeira(0, 5, "topeira", 8, amb, -10, 4);
        amb.adicionarEntidade(r_top);
        r_top.AdicionaSensores(new SensorTemperatura(r_top, 2));
        RoboTerrestreAOleo r_oleo = new RoboTerrestreAOleo(5, 4, "roboleo", 9, amb, 5);
        amb.adicionarEntidade(r_oleo);
        r_oleo.AdicionaSensores(new SensorTemperatura(r_oleo, 3));
        RoboAereoDinamico r_dinam = new RoboAereoDinamico(11, 10, 12, 16, "dinamico", amb, 6);
        amb.adicionarEntidade(r_dinam);
        r_dinam.AdicionaSensores(new SensorTemperatura(r_dinam, 3));
        RoboAereoRelator r_relator = new RoboAereoRelator(16, 12, 14, 14, "relator", amb, 3);
        amb.adicionarEntidade(r_dinam);
        r_relator.AdicionaSensores(new SensorTemperatura(r_relator, 4));

        while(true) {
                System.out.printf("\n ************* \n sistema de gerenciamento de ambiente! \n");
                System.out.printf("1- adicionar um robô \n 2- adicionar um obstáculo \n 3-mover um robô \n 4- relatório de temperatura \n 5- habilidade especiais \n 6- exibir posição \n 7- adicionar robo na central \n 8- comunicacao entre robos \n 9- sair \n ************* \n");
                int chave = leitor.nextInt();
                leitor.nextLine();

                if (chave == 2) {//Adicionar Obstáculo

                        System.out.println("Digite o x1 \n");
                        int x1 = leitor.nextInt();
                        System.out.println("Digite o x2");
                        int x2 = leitor.nextInt();
                        System.out.println("Digite o y1");
                        int y1 = leitor.nextInt();
                        System.out.println("Digite o y2");
                        int y2 = leitor.nextInt();

                        while (x1 > amb.getAmbienteX() || x2 > amb.getAmbienteX() || y1 > amb.getAmbienteY() || y2 > amb.getAmbienteY()) {
                                System.out.println("Valores inválidos! Tente novamente");
                                System.out.println("Digite o x1 \n");
                                x1 = leitor.nextInt();
                                System.out.println("Digite o x2");
                                x2 = leitor.nextInt();
                                System.out.println("Digite o y1");
                                y1 = leitor.nextInt();
                                System.out.println("Digite o y2");
                                y2 = leitor.nextInt();
                        }

                        System.out.println("Digite o tipo de obstáculo: \n 1- Muro: Altura igual a 10, impede passagem. \n 2-Bloco: Altura igual a 5, impede passagem. \n 3-Placa: Altura igual a 3, nao impede passagem.\n");
                        int opcao = leitor.nextInt();

                        while (opcao < 1 || opcao > 3) {
                                System.out.println("Opção inválida! Tente novamente \n");
                                opcao = leitor.nextInt();
                        }

                        TipoObstaculo tipo;

                        if (opcao == 1) {
                                tipo = TipoObstaculo.MURO;

                        } else if (opcao == 2) {
                                tipo = TipoObstaculo.BLOCO;

                        } else {
                                tipo = TipoObstaculo.PLACA;
                        }

                        amb.adicionarEntidade(new Obstaculo(x1, x2, y1, y2, tipo, amb));
                        System.out.println("Obstáculo Criado! \n");


                } else if (chave == 3) {//mover um robô
                        String robo;
                        int mov_x;
                        int mov_y;
                        int mov_z;

                        System.out.println("Digite o nome do Robô");
                        robo = leitor.nextLine();

                        //int pos = Main.buscar_robo(amb, robo);
                        Robo mover = amb.getRobo(robo);

                        if (mover == null) {
                                System.out.println("Nome inválido!");

                        } else {
                                //Robo mover = amb.getRobo(pos);
                                System.out.println("Digite o quanto deseja mover em x:");
                                mov_x = leitor.nextInt();
                                System.out.println("Digite o quanto deseja mover em y:");
                                mov_y = leitor.nextInt();
                                System.out.println("Digite o quanto deseja mover em z:");
                                mov_z = leitor.nextInt();
                                amb.moverEntidade(mover, mov_x, mov_y, mov_z);
                                System.err.println("Posicao apos tentativa de movimento (com ou sem exito):");
                                mover.exibirPosicao();
                        
                        }
                        
                } else if (chave == 4) { //relatório de temperatura
                        System.out.println("Digite o nome do robô \n");
                        String vulgo = leitor.nextLine();
                        //int pos = Main.buscar_robo(amb, vulgo);
                        Robo temp = amb.getRobo(vulgo);

                        if (temp == null) {
                                System.out.println("Nome inválido!");

                        } else {
                                temp.getSensorTemperatura().analise_temperatura();
                        }

                } else if (chave == 5) { //habilidades especiais
                        System.out.println("Digite o nome do rôbo \n");
                        String vulgo  = leitor.nextLine();

                        Robo p = amb.getRobo(vulgo);

                        //int pos = Main.buscar_robo(amb, vulgo);

                        if (p == null) {
                                System.out.println("Nome inválido! \n");

                        } else {

                                if (p instanceof RoboTerrestreAOleo) {
                                        System.out.println("Digite o valor do delta lubrificação \n");
                                        float valor = leitor.nextFloat();
                                        ((RoboTerrestreAOleo)p).AlterarLubrificacao(valor);
                                        ((RoboTerrestreAOleo)p).exibirLubrificacao();

                                } else if (p instanceof RoboAereoDinamico) {
                                        ((RoboAereoDinamico)p).recarregar();

                                } else {
                                        ((RoboAereoRelator)p).gerar_relatorio();
                                }
                        }

                } else if (chave == 6){ //Exibir Posição
                        System.out.println("Digite o nome do rôbo \n");
                        String vulgo  = leitor.nextLine();

                        Robo p = amb.getRobo(vulgo);

                        //int pos = Main.buscar_robo(amb, vulgo);

                        if (p == null) {
                            System.out.println("Nome inválido! \n");

                        } else {
                            p.exibirPosicao();
                        }
        
        
                }
                else if(chave == 7){
                        System.out.println("Digite o nome do rôbo \n");
                        String vulgo  = leitor.nextLine();
                        Robo p = amb.getRobo(vulgo);
                        if(p != null){
                                p.adicionarComunicacao(central);
                        }
                }
                
                else if(chave == 8){
                        String vulgo;
                        String mensagem;
                        int comunicacao;
                        System.out.println("Digite o nome do Robô");
                        vulgo = leitor.nextLine();
                        System.out.println("Deseja: \n 1- Enviar Mensagem\n 2- Receber Mensagem\n");
                        comunicacao = leitor.nextInt();
                        leitor.nextLine();
                        if(comunicacao != 1 && comunicacao != 2){
                                throw new EntradaException("Valores incorretos para selecionar as operacoes relacionadas a comunicacao entre robos.");
                        }
                        else{

                                if(comunicacao == 1){ //envio de mensagem
                                        Robo envia = amb.getRobo(vulgo);
                                        if(envia != null){
                                                System.out.println("Digite o nome do Robô");
                                                vulgo = leitor.nextLine();
                                                Robo recebe = amb.getRobo(vulgo);
                                                if(recebe != null){
                                                        System.out.println("Digite a sua mensagem:");
                                                        mensagem = leitor.nextLine();
                                                        envia.enviarMensagem(recebe, mensagem);
                                                }
                                        }
                                }
                                else{
                                        Robo recebe = amb.getRobo(vulgo);
                                        if(recebe != null){
                                                recebe.receberMensagem();
                                        }
                                }
                        }

                }
                
                else if (chave == 9) {//Sáida
                        System.out.println("Programa encerrado! Até Mais");
                        break;
                }

        }

        leitor.close();
        }

}