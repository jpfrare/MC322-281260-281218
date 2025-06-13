package cliente;

import ambiente.*;
import comunicacao.*;
import enums.*;
import exceptions.*;
import interfaces.*;
import java.util.Scanner;
import missao.MissaoBuscarPonto;
import missao.MissaoFugir;
import missao.MissaoMonitorar;
import obstaculo.*;
import robos.*;
import sensores.*;

public class Main {
    
        public static void main(String[] args) throws RoboDesligadoException, EntradaException {
                //Criacao do Ambiente:
                System.out.println("\nOlá, bem vindo!\nEm primeiro lugar, saiba que este ambiente de simulacao possui dimensoes: (20,20,20).\n");
                Ambiente amb = new Ambiente(20, 20, 20);
                CentralComunicacao central = new CentralComunicacao();

                Scanner leitor = new Scanner(System.in);
        
                //insercao dos obstaculos no ambiente
                amb.adicionarEntidade(new Obstaculo(2, 4, 3, 6, TipoObstaculo.BLOCO, amb)); // obstaculo de altura 5
                amb.adicionarEntidade(new Obstaculo(14, 18, 10, 12, TipoObstaculo.MURO, amb)); // obstaculo de altura 10
                amb.adicionarEntidade(new Obstaculo(7, 3, 17, 19, TipoObstaculo.PLACA, amb)); // "obstaculo" de altura 2. Robos passam pelo obstaculo independentemente

                //Criacao dos robos e adicao ao ambiente:
                RoboTerrestreTopeira rTop = new RoboTerrestreTopeira(0, 5, "topeira", 8, amb, -10, 4);
                amb.adicionarEntidade(rTop);
                RoboTerrestreAOleo rOleo = new RoboTerrestreAOleo(5, 4, "roboleo", 9, amb, 5);
                amb.adicionarEntidade(rOleo);
                RoboAereoDinamico rDinam = new RoboAereoDinamico(11, 10, 12, 16, "dinamico", amb, 6);
                amb.adicionarEntidade(rDinam);
                RoboAereoRelator rRelat = new RoboAereoRelator(16, 12, 14, 14, "relator", amb, 3);
                amb.adicionarEntidade(rRelat);
                AgenteInteligenteAereo agAereo = new AgenteInteligenteAereo(10, 18, 8, 16, "agenteAereo", amb, 5);
                amb.adicionarEntidade(agAereo);
                AgenteInteligenteTerrestre agTerrestre = new AgenteInteligenteTerrestre(17, 9, "agenteTerrestre", 7, amb, 4);
                amb.adicionarEntidade(agTerrestre);
        


                while(true) {
                        System.out.printf("\n ************* \n sistema de gerenciamento de ambiente! \n");
                        System.out.printf("1-  mover um robô \n2-  adicionar sensor de temperatura \n3-  habilidade especiais \n4-  exibir posição de um robo especifico \n5-  adicionar robo na central de comunicacao\n6-  realizar comunicacao entre robos \n7-  furtar combustivel (valido para RoboAereoDinamico e RoboTerrestreAOleo) \n8-  ativar sensor de temperatura \n9-  imprimir mapa \n10- fugir \n11- mover obstaculo\n12- especializar um robo agente (definir ou alterar o tipo de missao que ele executa)\n13- delegar missao a um robo agente\n14- sair\n ************* \n");
                        int chave = leitor.nextInt();
                        leitor.nextLine();
                        
                       
                        if (chave == 1) {//mover um robô
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
                                        System.out.println("Digite a posicao x do destino:");
                                        mov_x = leitor.nextInt();
                                        System.out.println("Digite a posicao y do destino:");
                                        mov_y = leitor.nextInt();
                                        System.out.println("Digite a posicao z do destino: (Sera considerado apenas para Robos Aereos e TerrestreTopeira).\nCaso deseja mover um RoboTerrestreAOleo, digite 0.");
                                        mov_z = leitor.nextInt();
                                        amb.moverEntidade(mover, mov_x, mov_y, mov_z);
                                        System.err.println("Posicao apos tentativa de movimento (com ou sem exito):");
                                        mover.exibirPosicao();
                                
                                }
                                
                        } else if (chave == 2) { //adicionar sensor de temperatura
                                System.out.println("Digite o nome do robô \n");
                                String vulgo = leitor.nextLine();
                                Robo temp = amb.getRobo(vulgo);

                                if (temp == null) {
                                        System.out.println("Nome inválido!");

                                } else {
                                        try {
                                                temp.getSensorTemperatura();

                                        } catch (IndexOutOfBoundsException e) {
                                                System.out.println("digite o raio do sensor de temperatura");
                                                int raio = leitor.nextInt();
                                                SensorTemperatura s = new SensorTemperatura(temp, raio);
                                                temp.AdicionaSensores(s);
                                        }
                                }

                        } else if (chave == 3) { //habilidades especiais
                                System.out.println("Digite o nome do rôbo \n");
                                String vulgo  = leitor.nextLine();

                                Robo p = amb.getRobo(vulgo);

                                if (p == null) {
                                        System.out.println("Nome inválido! \n");

                                } else {

                                        if (p instanceof RoboTerrestreAOleo) {
                                                System.out.println("Digite o valor da nova lubrificação \n");
                                                float valor = leitor.nextFloat();
                                                ((RoboTerrestreAOleo)p).setCoeficiente(valor);
                                                ((RoboTerrestreAOleo)p).exibirLubrificacao();

                                        } else if (p instanceof RoboAereoDinamico) {
                                                ((RoboAereoDinamico)p).recarregar();

                                        } else {
                                                ((RoboAereoRelator)p).gerar_relatorio();
                                        }
                                }

                        } else if (chave == 4){ //Exibir Posição
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
                        else if(chave == 5){ //adicionar um robo a central de comunicacao
                                System.out.println("Digite o nome do rôbo \n");
                                String vulgo  = leitor.nextLine();
                                Robo p = amb.getRobo(vulgo);
                                if(p != null){
                                        p.adicionarComunicacao(central);
                                }
                        }
                        
                        else if(chave == 6){ //operar a comunicacao entre robos (operacao de enviar ou receber uma mensagem)
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
                        else if(chave == 7){ //furto de combustivel
                                InterfaceFurtoCombustivel furtar;
                                InterfaceFurtoCombustivel furtado;
                                System.out.println("Digite o nome do robo que vai furtar combustivel: \n");
                                String ator  = leitor.nextLine();
                                Robo r1 = amb.getRobo(ator);
                                if(r1 != null && (r1 instanceof RoboAereoDinamico || r1 instanceof RoboTerrestreAOleo)){ //usuario buscou um robo que existe e que implementa a InterfaceFurtoCombustivel
                                        if(r1 instanceof  RoboTerrestreAOleo){
                                                furtar = (InterfaceFurtoCombustivel)((RoboTerrestreAOleo)r1);
                                        }
                                        else{
                                                furtar = (InterfaceFurtoCombustivel)((RoboAereoDinamico)r1);
                        
                                        }
                                        System.out.println("Digite o nome do robo que tera seu combustivel furtado: \n");
                                        String vitima = leitor.nextLine();
                                        Robo r2 = amb.getRobo(vitima);
                                
                                        if(r2 != null && (r2 instanceof RoboTerrestreAOleo || r2 instanceof RoboAereoDinamico)){
                                                if(r2 instanceof  RoboTerrestreAOleo){
                                                        furtado = (InterfaceFurtoCombustivel)((RoboTerrestreAOleo)r2);
                                                }
                                                else{
                                                        furtado = (InterfaceFurtoCombustivel)((RoboAereoDinamico)r2);
                                
                                                }
                                                furtar.furtar_combustivel(furtado);
                                        }
                                }
                        }
                        else if (chave == 8) {//acionar sensor de temperatura
                                System.out.println("digite o nome do robô");
                                String vulgo = leitor.nextLine();
                                Robo r = amb.getRobo(vulgo);
                                try{
                                        if (r != null) r.acionarSensores();
                                }
                                catch(Exception e){
                                        System.out.println("erro");
                                }

                        } 
                        else if (chave == 9) { //imprimir mapa
                                System.out.println("digite a altura da impressao");
                                int altura = leitor.nextInt();
                                leitor.nextLine();
                                if (altura <= amb.getAmbienteZ()) amb.imprimeMapa(altura);


                        } 
                        else if (chave == 10) {//fugir
                                System.out.println("digite o nome do robô");
                                //String vulgo = leitor.nextLine();
                                //Robo r = amb.getRobo(vulgo);

                
                        }
                        else if(chave == 11) { //mover obstaculo
                                System.err.println("Digite a altura do obstaculo que deseja mover:");
                                int h = leitor.nextInt();
                                leitor.nextLine();
                                Obstaculo mover = amb.getObscatulo(h);
                                if(mover != null){
                                        System.out.println("Digite a posicao x do destino:");
                                        int movx = leitor.nextInt();
                                        System.out.println("Digite a posicao y do destino:");
                                        int movy = leitor.nextInt();
                                        System.out.println("Digite a posicao z do destino: (Sera considerado apenas para Robos Aereos e TerrestreTopeira).\nCaso deseja mover um RoboTerrestreAOleo, digite 0.");
                                        int movz = leitor.nextInt();
                                        amb.moverEntidade(mover, movx, movy, movz);
                                        leitor.nextLine();
                                }
                        }
                        else if(chave == 12){
                                System.out.println("Digite o nome do robo \n");
                                String vulgo  = leitor.nextLine();
                                Robo p = amb.getRobo(vulgo);
                                if(p != null){
                                        if(p instanceof AgenteInteligenteAereo || p instanceof AgenteInteligenteTerrestre){
                                                InterfaceRoboMissionario missionario = (InterfaceRoboMissionario)p;
                                                System.out.println("Digite a opcao desejada para especializacao do Robo:\n1- Buscar Ponto\n2- Fugir\n3 - Monitorar");
                                                int selecao = leitor.nextInt();
                                                if(selecao == 1)
                                                        missionario.definirMissao(new MissaoBuscarPonto(leitor));
                                                else if(selecao == 2)
                                                        missionario.definirMissao(new MissaoFugir());
                                                else if(selecao == 3)
                                                        missionario.definirMissao(new MissaoMonitorar());
                                                else{
                                                        System.out.println("Opcao/selecao invalida! missao especializada nao foi definida.\n");
                                                        break;
                                                }
                                                System.out.println("Missao definida.\n");

                                        }
                                        else{
                                                System.out.println("Robo selecionado nao executa missao.\n");
                                        }
                                }
                                else{
                                        System.out.println("Nenhum robo foi encontrado.\n");
                                }
                        }
                        else if(chave == 13){
                                System.out.println("Digite o nome do robo \n");
                                String vulgo  = leitor.nextLine();
                                Robo p = amb.getRobo(vulgo);
                                if(p != null){
                                        if(p instanceof AgenteInteligenteAereo || p instanceof AgenteInteligenteTerrestre){
                                               InterfaceRoboMissionario missionario = (InterfaceRoboMissionario)p;
                                               missionario.executarMissao("../../logMissao.txt"); 
                                        }
                                        else
                                                System.out.println("Robo selecionado nao executa missao.");
                                }
                                
                        }
                        else if (chave == 14) {//Sáida
                                System.out.println("Programa encerrado! Até Mais");
                                leitor.close();
                                break;


                        }
                }               
        }
}