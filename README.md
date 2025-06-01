
# Repositório destinado ao desenvolvimento dos laboratórios da disciplina de MC322
IDE utilizada: VS Code.  
Versão do Java:  11.0.26 2025-01-21  


<p>Considerações relevantes sobre o projeto, com foco no arquivo main: (Explicações minusciosas sobre as classes existentes e seus métodos estão contidas tanto nos cometários do código quanto no diagrama UML).  

<p>A classe main é uma interface interativa relativamente simples, porém seu tamanho é devido as várias etapas de tratamento de erro de input (talvez seja conveniente a criação de uma função que capta as entradas e já realiza este tratamento, porém optou-se, em função das variações de mensagem no casos de erro, por não fazê-la).

## Detalhes importantes:
  <p>I- Existência das exceções RoboDesligadoException (quando qualquer robô tenta realizar alguma das ações citadas abaixo e está desligado), EntradaException (quando há problema na entrada de dados), ForaDosLimitesException (quando um ponto está fora dos limites do ambiente) e ColisaoException (quando há um objeto ou um outro robô na posição da qual se deseja ocupar). Como observação utiliza-se também a exceção built-in IndexOutOfBoundsException quando tenta-se acessar o sensor de temperatura em um robô que não o possui (dado que o sensor se ecnontraria numa posição inválida do array de sensores).
  <p>II- Existência das interfaces personalizadas (além das exigidas no enunciado) térmica, fujão e furtocombustível; que serão explicadas juntamente com as ações na main.
  
  ## Main:
<p>Cria-se um ambiente e após isso, temos as opções de:  
  
  1. Mover um robô
  2. Adicionar sensor de temperatura a um robô
  3. Utilizar a habilidade especial de algum robô já inserido no ambiente
  4. Exibir a posição de um robô
  5. Adicionar robô à central de comunicação
  6. Realizar comunicação entre robôs
  7. Furtar combustivel
  8. Ativar sensor (de temperatura)
  9. Imprimir mapa
  10. Fugir
  11. Sair
 
## 1. Mover um robô:
<p> Nesta opção, há os inputs de destino final em cada coordenada (xy para o terrestre a óleo e xyz para os aéreos e terrestre toupeira) e a chamada da respectiva função de mover (condicionada a classe do robô escolhido), esta que é um algorítmo baseado em backtracking, que utiliza o sensor de movimento para perfazer um caminho livre de obstáculos (salvo o robô topeira, que se aproveita do fato de que não há obstáculos no subsolo para evitar o uso de chamadas recursivas/sensores).
  
## 2. Adicionar sensor de temperatura a um robô: 
<p> Busca o robô desejado pelo seu nome, verifica se este já não possui sensor de temperatura (mediante lançamento de exceção), caso contrário, adiciona para ele um sensor de temperatura com o raio escolhido.
  
## 3. Utilizar a habilidade especial de algum robô já inserido no ambiente: 
<p> Invoca a função única associada à classe do robô escolhido, a saber: alteração de lubrificação do robô terrestre a óleo, recarga de bateria do robô aéreo dinâmico e geração de relatório do robô aéreo relator.
  
## 4. Exibir a posição de um robô: 
<p> Exibe a posição do robô escolhido, com adicional de exibir a lubrificação no caso do robô terrestre a óleo.

## 5. Adicionar robô à central de comunicação:
<p> Adiciona o robô escolhido à central de comunicação, tornando-o capaz de enviar e receber mensagens.  
  
## 6. Realizar comunicação entre robôs:
<p> Apenas robôs comunicaveis (implementam a InterfaceComunicavel) desfrutam desta funcionalidade, da qual, por meio da central de comunicação, podem tanto enviar mensagens para outros robôs comunicaveis quanto receber mensagens previamente enviadas.

## 7. Furtar combustivel:
<p> Funcionalidade da qual apenas robôs que implementam a InterfaceFurtoCombustivel utilizam, a saber, robôs aéreos dinâmicos e robôs terrestres à óleo. Nessa opção, robôs terrestres à óleo podem furtar a autonomia dos robôs aéreos dinâmicos e vice-versa; a conversão de roubo se dá na proporção de 1:1.

## 8. Ativar sensor de temperatura:
<p> Por meio da InterfaceSensoravel, todos os robôs que possuem sensor de temperatura farão um relatório da temperatura numa região delimitada pelo raio desse (a temperatura num ponto, por sua vez, é dada por uma função (x,y,z) de distribuição completamente aleatória tirada da minha cabeça) exibindo tanto a temperatura mais alta da região, como a mais baixa e a sentida pelo robô na posição atual. Adicionalmente ao relatório, alguns robôs (que implementam a InterfaceTermica), além de realizar o relatório padrão, fazem a chamada de uma função de preferência térmica; a saber: robôs terrestres tentarão se mover uma única vez ao ponto de maior temperatura no eixo xy e o aéreo dinâmico tentará se mover, também unicamente, para o ponto de menor temperatura em xyz (o robô relator não implementa essa interface pois seu movimento não é dinâmico, não o permitindo mover simultaneamente em xy e em z).

## 9. Imprimir mapa.
<p> Imprime na tela, segundo o eixo cartesiano padrão, o mapa em uma altura dada, exibindo obstáculos como "x", espaços vazios como "." e os demais com sua respectiva representação em caracteres.

## 10. Fugir.
<p> Essa funcionalidade está relacionada a outra interface (InterfaceFujao). Nessa, admite-se a premissa de que robôs tem a preferência por evitar outros robôs na mesma coordenada xy, mas em diferente altura z, assim, todo robô que identificar outro nessa condição tentará fugir deste numa região que compreende um quadrado de lado igual ao raio do sensor, na mesma altura z em que se encontra, caso não seja possível (há sempre um robô aplicando essa condição ou há objetos atrapalhando a movimentação), o robô desligará.

## 11. Sair do programa.
<p> Encerra o programa, fechando o leitor de entrada.
  
***A título de observação, a busca por algum robô é sempre dada pelo seu nome, dando utilidade à variável.***
    
