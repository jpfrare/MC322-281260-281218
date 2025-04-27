IDE utilizada: vs code
Versão do Java:  11.0.26 2025-01-21

Repositório destinado ao desenvolvimento dos laboratórios da disciplina de MC322


Considerações relevantes sobre o projeto, com foco no arquivo main: (Explicações minusciosas sobre as classes existentes e seus métodos estão contidas tanto nos cometários do código quanto no diagrama UML)
OBS: é fortemente recomendado a leitura deste arquivo no modo de edição, pois este contém espaçamentos e quebra de linhas que tornam a leitura mais agradável

  1-A classe main é uma interface interativa relativamente simples, porém seu tamanho é devido as várias etapas de tratamento de erro de input (talvez seja conveniente a criação de uma função que capta as entradas e já realiza este tratamento, porém optou-se, em função das variações de mensagem no casos de erro, por não fazê-la).
  2- Cria-se um ambiente (dados os inputs relacionados ao comprimento do mesmo), e após isso, temos as opções de:
    1- adicionar um robô ao ambiente
    2- adicionar um obstáculo ao ambiente
    3- mover um robô
    4- realizar um relatório de temperatura
    5- utilizar a habilidade especial de algum robô já inserido no ambiente 
    6- exibir a posição de um robô 
    7- sair do programa.
      2.1- Adição de Robô: 
      contém os inputs de nome, posição inicial, raio do sensor de movimento e de temperatura e escolha de qual tipo de robô (terrestres toupeira e a óleo e aéreos relator e dinânico) e suas respectivas especificidades; após isso, há sua inserção no ambiente.
      2.2- Adição de Obstáculo: 
      contém os inputs de posição, tamanho em largura, tamanho em comprimento e o tipo de obstáculo (Muro, Bloco ou Placa), este que define sua altura; após isso, há sua inserção no ambiente.
      2.3- Mover um Robô: 
      nesta opção, há os inputs de variação de comprimento em cada coordenada (xy para o terrestre a óleo e xyz para os aéreos e terrestre toupeira) e a chamada da respectiva função de mover (condicionada a classe do robô escolhido), esta que é um algorítmo baseado em backtracking, que utiliza o sensor de movimento para perfazer um caminho livre de obstáculos (salvo o robô topeira, que se aproveita do fato de que não há obstáculos no subsolo para evitar o uso de chamadas recursivas/sensores).
      2.4- Relatório de Temperatura: 
      o sensor de temperatura de cada robô utiliza a matriz de temperatura contida no ambiente (existe uma temperatura relacionada a cada ponto xyz no ambiente (salvo no subsolo), que é baseada em uma função arbitrária e aleatória definida no construtor de ambiente) e a posição em que o robô se encontra no ambiente da seguinte forma: o sensor procura, na região compreendida por um cubo de lado de duas vezes o raio deste sensor e de centro na posição atual do robô a maior e menor temperatura e as imprime na tela (juntamente com suas respectivas localizações), bem como a temperatura associada a posição atual do robô.
      2.5- Habilidades Especiais:
      invoca a função única assocdiada â classe robô escolhido, a saber: alteração de lubrificação do robô terrestre a óleo, recarga de bateria do robô aéreo dinâmico e geração de relatório do robô aéreo relator
      2.6- Exibir Posição:
      exibe a posição do robô escolhido, com adicional de exibir a lubrificação no caso do robô terrestre a óleo

  3- A título de observação, a busca por algum robô é sempre dada pelo seu nome, dando utilidade à variável.
    
