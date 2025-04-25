import java.util.Scanner;

public class Main {
        public static int buscar_robo(Ambiente amb, String nome) {
                //busca robô pelo nome no array list do ambiente
                int len = amb.getArrayTamanho();

                for (int i = 0; i < len; i++) {
                        if (nome.equals(amb.getRobo(i).getNome())) {
                                return i;
                        }
                }

                return -1;
        }
    
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        System.out.println("\n Olá, bem vindo! Em primeiro lugar, escolha as dimensões do ambiente! \n");

        System.out.println("tamanho em X:");
        int x = leitor.nextInt();

        System.out.println("tamanho em y");
        int y = leitor.nextInt();
        

        System.out.println("tamanho em z");
        int z = leitor.nextInt();

        Ambiente amb = new Ambiente(x, y, z);
        System.out.println("Ambiente criado!");


        while(true) {
                System.out.printf("\n ************* \n sistema de gerenciamento de ambiente! \n");
                System.out.printf("1- adicionar um robô \n 2- adicionar um obstáculo \n 3-mover um robô \n 4- temperatura de algum ponto \n 5- habilidade especial de um robô \n 6- exibir informações de um robô \n 7- sair \n ************* \n");
                int chave = leitor.nextInt();

                if (chave == 1) {
                        //Adicionar Robô
                        Robo r;
                        System.out.println("Digite o nome do robô: \n");
                        String nome = leitor.nextLine();
                        
                        //coordenadas padrão iniciais
                        System.out.println("Digite a coordenada X do rôbo \n");
                        int posicaoXo = leitor.nextInt();
                        System.out.println("Digite a coordenada Y do rôbo \n");
                        int posicaoYo = leitor.nextInt();

                        while (!amb.dentroDosLimites(posicaoXo, posicaoYo, 0) || amb.eh_obstaculo(posicaoXo, posicaoYo, 0)) {
                                System.out.println("Coordenadas inválidas! Tente novamente \n");
                                System.out.println("Digite a coordenada X do rôbo \n");
                                posicaoXo = leitor.nextInt();
                                System.out.println("Digite a coordenada Y do rôbo \n");
                                posicaoYo = leitor.nextInt();
                        }

                        //raio sensor movimento
                        System.out.println("Digite o raio do sensor de movimento: \n");
                        int raio = leitor.nextInt();
                        while (raio <= 0) {
                                System.out.println("Raio de sensor inválido, digite novamente \n");
                                System.out.println("Digite o raio do sensor de movimento: \n");
                                raio = leitor.nextInt();
                        }
                        
                        Sensor s = new Sensor(raio);

                        //tipo de robõ
                        System.out.println("Quase lá! Agora, digite qual robô: ");
                        System.out.println("1- Robô terrestre Topeira \n 2- Rôbo Terrestre a Óleo \n 3- Robô Aéreo Dinâmico \n 4- Robô Aéreo Relator \n");
                        int opcao = leitor.nextInt();

                        while (opcao <= 0 || opcao >= 5) {
                                System.out.println("Opção inválida! Tente Novamente! \n");
                                System.out.println("1- Robô terrestre Topeira \n 2- Rôbo Terrestre a Óleo \n 3- Robô Aéreo Dinâmico \n 4- Robô Aéreo Relator \n");
                                opcao = leitor.nextInt();
                        }

                        if (opcao == 1 || opcao == 2) {
                                //Robôs Terrestres

                                System.out.println("Digite a velocidade máxima do Robô");
                                float velocidademax = leitor.nextFloat();

                                while (velocidademax <= 0) {
                                        System.out.println("velocidade máxima inválida! Tente Novamente \n");
                                        velocidademax = leitor.nextFloat();
                                }

                                if (opcao == 1) {
                                        //Robô terrestre topeira
                                        System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                        int profundidade_max = leitor.nextInt();

                                        while (profundidade_max >= 0) {
                                                System.out.println("Valor inválido! Tente novamente! \n");
                                                System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                                profundidade_max = leitor.nextInt(); 
                                        }
                                
                                        r = new RoboTerrestreTopeira(posicaoXo, posicaoYo, nome, velocidademax, amb, profundidade_max, s);
                                }
                                  
                                else {
                                        //robo terrestre a oleo
                                        r = new RoboTerrestreAOleo(posicaoXo, posicaoYo, nome, velocidademax, amb, s);
                                }
                                
                        } else {
                                //Robôs Aéreos
                                System.out.println("Digite a altura máxima do Robô \n");
                                int alt_max = leitor.nextInt();

                                while (alt_max <= 0 || alt_max > z) {
                                        System.out.println("Altura Máxima inválida! Tente novamente \n");
                                        alt_max = leitor.nextInt();
                                }


                                System.out.println("Digite a altura inicial do Robô \n");
                                int alt_o = leitor.nextInt();

                                while (alt_o < 0 || alt_o > alt_max || amb.eh_obstaculo(posicaoXo, posicaoYo, alt_o) || amb.dentroDosLimites(posicaoXo, posicaoYo, alt_o)) {
                                        System.out.println("Altura incial inválida! Tente Novamente \n");
                                        alt_o = leitor.nextInt();
                                }


                                if (opcao == 3) {
                                        //Robô aéreo dinâmico
                                        System.out.println("Digite a capacidade do Robô \n");
                                        int capacidade = leitor.nextInt();

                                        while (capacidade <= 0) {
                                                System.out.println("Capacidade inválida! Tente novamente \n");
                                                capacidade = leitor.nextInt();
                                        }

                                        r = new RoboAereoDinamico(posicaoXo, posicaoYo, alt_o, alt_max, nome, amb, capacidade, s);

                                }

                                else {
                                        //Robô Aéreo Relator
                                        r = new RoboAereoRelator(posicaoXo, posicaoYo, alt_o, alt_max, nome, amb, s);

                                }

                                amb.adicionaRobo(r);
                                System.out.println("Robô " + r.getNome() + " criado!");
                        }

                } else if (chave == 2) {
                        //Adicionar Obstáculo

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

                        System.out.println("Digite o tipo de obstáculo: \n 1- Muro \n 2-Bloco \n 3-Placa\n");
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

                        amb.adicionaObstaculo(x1, x2, y1, y2, tipo);
                        System.out.println("Obstáculo Criado! \n");
                        


                } else if (chave == 3) {
                  System.out.println("Digite o número do Robô \n");
                  int numero = leitor.nextInt();

                  while (numero < 0 || numero > amb.getArrayTamanho()) {
                        System.out.println("Número inválido! Tente novamente");
                        numero = leitor.nextInt();
                  }



                } else if (chave == 4) {


                } else if (chave == 5) {
                        //habilidades especiais
                        System.out.println("Digite o nome do rôbo \n");
                        String vulgo  = leitor.nextLine();

                        int pos = Main.buscar_robo(amb, vulgo);

                        if (pos == -1) {
                                System.out.println("Nome inválido! \n");

                        } else {
                                Robo p = amb.getRobo(pos);

                                if (p instanceof RoboTerrestreAOleo) {
                                        System.out.println("Digite o valor do delta lubrificação \n");
                                        float valor = leitor.nextFloat();
                                        ((RoboTerrestreAOleo)p).AlterarLubrificacao(valor);


                                } else if (p instanceof RoboAereoDinamico) {
                                        ((RoboAereoDinamico)p).recarregar();

                                } else {
                                        ((RoboAereoRelator)p).gerar_relatorio();
                                }
                        }

                } else if (chave == 6){
                        System.out.println("Digite o nome do rôbo \n");
                        String vulgo  = leitor.nextLine();

                        int pos = Main.buscar_robo(amb, vulgo);

                        if (pos == -1) {
                                System.out.println("Nome inválido! \n");

                        } else {
                                Robo p = amb.getRobo(pos);

                                p.exibirPosicao();
                        }
                
                
                } else if (chave == 7) {
                        System.out.println("Programa encerrado! Até Mais");
                        break;

                } else {
                        System.out.println("Valor inválido! Tente Novamente");
                }
        }


     }
}
