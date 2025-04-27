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
                System.out.printf("1- adicionar um robô \n 2- adicionar um obstáculo \n 3-mover um robô \n 4- relatório de temperatura \n 5- habilidade especiais \n 6- exibir posição \n 7- sair \n ************* \n");
                int chave = leitor.nextInt();
                leitor.nextLine();

                if (chave == 1) {//Criação de robô
        
                        System.out.println("Digite o nome do robô: \n");
                        String nome = leitor.nextLine();
                        
                        //coordenadas padrão iniciais
                        System.out.println("Digite a coordenada X do rôbo \n");
                        int posicaoXo = leitor.nextInt();
                        System.out.println("Digite a coordenada Y do rôbo \n");
                        int posicaoYo = leitor.nextInt();

                        while (!amb.dentroDosLimites(posicaoXo, posicaoYo, 0) || amb.identifica_colisao(posicaoXo, posicaoYo, 0)) {
                                System.out.println("Coordenadas inválidas! Tente novamente");
                                System.out.println("Digite a coordenada X do rôbo \n");
                                posicaoXo = leitor.nextInt();
                                System.out.println("Digite a coordenada Y do rôbo \n");
                                posicaoYo = leitor.nextInt();
                        }

                        //raio sensor movimento
                        System.out.println("Digite o raio dos sensores de movimento (1) e temperatura (2) \n");
                        int raio = leitor.nextInt();
                        int raiotemp = leitor.nextInt();
                        while (raio <= 0 || raiotemp <= 0) {
                                System.out.println("Raio de sensor inválido, digite novamente \n");
                                System.out.println("Digite o raio do sensor de movimento: \n");
                                raio = leitor.nextInt();
                                System.out.println("Digite o raio do sensor de temperatura \n");
                                raiotemp = leitor.nextInt();
                        }
                  


                        //tipo de robô

                        System.out.println("Quase lá! Agora, digite qual robô: ");
                        System.out.println("1- Robô terrestre Topeira \n 2- Rôbo Terrestre a Óleo \n 3- Robô Aéreo Dinâmico \n 4- Robô Aéreo Relator \n");
                        int opcao = leitor.nextInt();

                        while (opcao <= 0 || opcao >= 5) {
                                System.out.println("Opção inválida! Tente Novamente! \n");
                                opcao = leitor.nextInt();
                        }

                        if (opcao == 1 || opcao == 2) {
                                //Robôs Terrestres: possuem velocidade máxima
                                System.out.println("Digite a velocidade máxima do robô");
                                float velocidademax = leitor.nextFloat();

                                while (velocidademax <= 0) {
                                        System.out.println("valor de velocidade máxima inválido! Tente novamente \n");
                                        velocidademax = leitor.nextFloat();
                                }

                                if (opcao == 1) {
                                        //robô terrestre topeira
                                        System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                        int profundidade_max = leitor.nextInt();

                                        while (profundidade_max >= 0) {
                                                System.out.println("Valor inválido! Tente novamente! \n");
                                                System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                                profundidade_max = leitor.nextInt(); 
                                        }
                                

                                        RoboTerrestreTopeira r = new RoboTerrestreTopeira(posicaoXo, posicaoYo, nome, velocidademax, amb, profundidade_max, raio);
                                        amb.adicionaRobo(r);

                                }
                                  
                                else if (opcao == 2) {
                                        //robo terrestre a óleo

                                        RoboTerrestreAOleo ro = new RoboTerrestreAOleo(posicaoXo, posicaoYo, nome, velocidademax, amb, raio);
                                        amb.adicionaRobo(ro);

                                }
                                
                        }
                        else {
                                //Robôs aéreos: possuem altitude máxima
                                System.out.println("Digite a altura inicial do robo:");
                                int posicaoZo = leitor.nextInt();
                                while(posicaoZo < 0){
                                        System.out.println("Valor inválido! Digite novamente! \n");
                                        System.out.println("Digite a altura inicial do robo: \n");
                                        posicaoZo = leitor.nextInt();
                                }

                                System.out.println("Digite a altura máxima do robô");
                                int alturamaxima = leitor.nextInt();
                                

                                while (alturamaxima > amb.getAltura() || alturamaxima <= 0) {
                                        System.out.println("Altura máxima inválida! Tente novamente \n");
                                        alturamaxima = leitor.nextInt();
                                }

                                if(opcao == 3) {
                                        //robô aereo dinâmico
                                        System.out.println("Digite a capacidade do Robô");
                                        int capacidade = leitor.nextInt();

                                        while (capacidade <= 0) {
                                                System.out.println("Valor de capacidade inválido! Tente novamente");
                                                System.out.println("Digite a capacidade do Robô");
                                                capacidade = leitor.nextInt();
                                        }

                                        RoboAereoDinamico rd = new RoboAereoDinamico(posicaoXo, posicaoYo, posicaoZo, alturamaxima, nome, amb, capacidade, raio);
                                }

                                else if(opcao == 4){
                                        RoboAereoRelator ar = new RoboAereoRelator(posicaoXo, posicaoYo, posicaoZo, alturamaxima, nome, amb, raio);
                                        amb.adicionaRobo(ar);
                                }

                                SensorTemperatura temp = new SensorTemperatura(r, raiotemp);
                                r.AdicionaSensores(temp);
                        }

                } else if (chave == 2) {//Adicionar Obstáculo

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


                } else if (chave == 3) {//mover um robô
                        String robo;
                        int mov_x;
                        int mov_y;
                        int mov_z;

                        System.out.println("Digite o nome do Robô");
                        robo = leitor.nextLine();

                        int pos = Main.buscar_robo(amb, robo);

                        if (pos == -1) {
                                System.out.println("Nome inválido!");

                        } else {
                                Robo mover = amb.getRobo(pos);
                                System.out.println("Digite o quanto deseja mover em x:");
                                mov_x = leitor.nextInt();
                                System.out.println("Digite o quanto deseja mover em y:");
                                mov_y = leitor.nextInt();
                                

                                if (mover instanceof RoboTerrestre){
                                        System.out.println("Digite o quanto deseja mover em x:");
                                        mov_x = leitor.nextInt();
                                        System.out.println("Digite o quanto deseja mover em y:");
                                        mov_y = leitor.nextInt();
                                        if(mover instanceof RoboTerrestreAOleo){
                                                mover.mover(mov_x, mov_y); 
                                        }
                                        else if(mover instanceof RoboTerrestreTopeira){
                                                System.out.println("Digite o quanto deseja mover em z:");
                                                mov_z = leitor.nextInt();
                                                ((RoboTerrestreTopeira) mover).mover(mov_x, mov_y, mov_z);
                                        }
                                }
                                else if(mover instanceof RoboAereoRelator relator){
                                        int movimento; 
                                        System.out.println("Deseja se mover apenas horizontalmente (digite 1) ou verticalmente também (digite 2)?");
                                        movimento = leitor.nextInt();

                                        if(movimento == 1){
                                                relator.mover(mov_x, mov_y);
                                        } else if(movimento == 2){

                                                relator.mover(mov_x, mov_y);

                                                System.out.println("Digite o quanto deseja mover em z:");
                                                mov_z = leitor.nextInt();
                                                if(mov_z > 0){
                                                        relator.subir(mov_z);
                                                }
                                                else{
                                                        relator.descer(-mov_z);
                                                }
                                        } else{
                                                System.out.println("Movimento inválido!");
                                        }

                                } else if (mover instanceof RoboAereoDinamico){
                                        RoboAereoDinamico dinamico = (RoboAereoDinamico)mover;


                                        System.out.println("Digite o quanto deseja mover em x:");
                                        mov_x = leitor.nextInt();
                                        System.out.println("Digite o quanto deseja mover em y:");
                                        mov_y = leitor.nextInt();

                                        System.out.println("Digite o quanto deseja mover em z:");
                                        mov_z = leitor.nextInt();
                                        dinamico.moverDinamico(mov_x, mov_y, mov_z);   
                                }
                        
                        }
                        
                } else if (chave == 4) { //relatório de temperatura
                        System.out.println("Digite o nome do robô \n");
                        String vulgo = leitor.nextLine();
                        int pos = Main.buscar_robo(amb, vulgo);

                        if (pos == -1) {
                                System.out.println("Nome inválido!");

                        } else {
                                amb.getRobo(pos).getSensorTemperatura().analise_temperatura();
                        }

                } else if (chave == 5) { //habilidades especiais
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

                        int pos = Main.buscar_robo(amb, vulgo);

                        if (pos == -1) {
                                System.out.println("Nome inválido! \n");

                        } else {
                                Robo p = amb.getRobo(pos);

                                p.exibirPosicao();
                        }
        
        
                } else if (chave == 7) {//Sáida
                        System.out.println("Programa encerrado! Até Mais");
                        break;
                }

        }

        leitor.close();
        }

}