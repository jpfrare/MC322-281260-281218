import java.util.Scanner;

public class Main {
    
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
                System.out.printf("1- adicionar um robô \n 2- adicionar um obstáculo \n 3-mover um robô \n 4- temperatura de algum ponto \n 5- sair \n ************* \n");
                int chave = leitor.nextInt();

                if (chave == 1) {
                        System.out.println("Digite o nome do robô: \n");
                        String nome = leitor.nextLine();
                        
                        //coordenadas padrão iniciais
                        System.out.println("Digite a coordenada X do rôbo \n");
                        int posicaoXo = leitor.nextInt();
                        System.out.println("Digite a coordenada Y do rôbo \n");
                        int posicaoYo = leitor.nextInt();

                        while (!amb.dentroDosLimites(posicaoXo, posicaoYo, 0) || amb.eh_obstaculo()) {
                                System.out.println("Coordenadas inválidas! Tente novamente");
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
                                opcao = leitor.nextInt();
                        }

                        if (opcao == 1 || opcao == 2) {

                                if (opcao == 1) {
                                        System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                        int profundidade_max = leitor.nextInt();

                                        while (profundidade_max >= 0) {
                                                System.out.println("Valor inválido! Tente novamente! \n");
                                                System.out.println("Digite a profundidade máxima do robô (valor < 0) \n");
                                                profundidade_max = leitor.nextInt(); 
                                        }
                                
                                        RoboTerrestreTopeira r = new RoboTerrestreTopeira(posicaoXo, posicaoYo, nome, x, amb, profundidade_max, nome, s);
                                }
                                  
                                else if (opcao == 2) {}
                                
                        }

                } else if (chave == 2) {


                } else if (chave == 3) {
                        String robo;
                        int mov_x;
                        int mov_y;
                        Robo mover = null;
                        while(mover == null){
                                System.out.println("Digite o nome do Robo a se mover:");
                                robo = leitor.nextLine();
                                for(Robo r: amb.getArrayRobos()){
                                        if(robo.equals(r.getNome())){
                                                mover = r;
                                                break;
                                        }
                                }
                                if(mover == null){
                                        System.out.println("Não foi possivel identificar um Robo com o nome inserido");
                                }
                        }
                        
                        if (mover instanceof RoboTerrestre){
                                System.out.println("Digite o quanto deseja mover em x:");
                                mov_x = leitor.nextInt();
                                System.out.println("Digite o quanto deseja mover em y:");
                                mov_y = leitor.nextInt();
                                mover.mover(mov_x, mov_y);
                        }
                        else if(mover instanceof RoboAereoRelator){
                                RoboAereoRelator relator = (RoboAereoRelator)mover;
                                int movimento; 
                                System.out.println("Deseja se mover horizontalmente(digite 1) ou verticalmente(digite 2)?");
                                movimento = leitor.nextInt();
                                if(movimento == 1){
                                        System.out.println("Digite o quanto deseja mover em x:");
                                        mov_x = leitor.nextInt();
                                        System.out.println("Digite o quanto deseja mover em y:");
                                        mov_y = leitor.nextInt();
                                        relator.mover(mov_x, mov_y);
                                }
                                else if(movimento == 2){
                                        int mov_z;
                                        System.out.println("Digite o quanto deseja mover em z:");
                                        mov_z = leitor.nextInt();
                                        if(mov_z > 0){
                                                relator.subir(mov_z);
                                        }
                                        else{
                                                relator.descer(-mov_z);
                                        }
                                }
                                
                        }
                        



                } else if (chave == 4) {


                } else if (chave == 5) {
                        System.out.println("Programa encerrado! Até Mais");
                        break;

                } else {
                        System.out.println("Valor inválido! Tente Novamente");
                }
        }


     }
}
