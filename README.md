
# Repositório destinado ao desenvolvimento dos laboratórios da disciplina de MC322
IDE utilizada: VS Code.  
Versão do Java:  11.0.26 2025-01-21  


<p>Considerações relevantes sobre o projeto, com foco no arquivo main: (Explicações minusciosas sobre as classes existentes e seus métodos estão contidas tanto nos cometários do código quanto no diagrama UML).  

<p>A classe main é uma interface interativa relativamente simples, porém seu tamanho é devido as várias etapas de tratamento de erro de input (talvez seja conveniente a criação de uma função que capta as entradas e já realiza este tratamento, porém optou-se, em função das variações de mensagem no casos de erro, por não fazê-la).  
  
<p>Cria-se um ambiente (dados os inputs relacionados ao comprimento do mesmo), e após isso, temos as opções de:  
  
  1. Adicionar um robô ao ambiente
  2. Adicionar um obstáculo ao ambiente
  3. Mover um robô
  4. Realizar um relatório de temperatura
  5. Utilizar a habilidade especial de algum robô já inserido no ambiente
  6. Exibir a posição de um robô
  7. Sair de um programa 
 
## 1. Adição de Robô:
Contém os inputs de nome, posição inicial, raio do sensor de movimento e de temperatura e escolha de qual tipo de robô (terrestres toupeira e a óleo e aéreos relator e dinânico) e suas respectivas especificidades; após isso, há sua inserção no ambiente.
## 2. Adição de Obstáculo: 
<p> Contém os inputs de posição, tamanho em largura, e o tipo de obstáculo (Muro, Bloco ou Placa), o tipo que define sua altura, e caso o obstaculo impeca um robo de ocupar uma posição; após isso, há sua inserção no ambiente.  
  
## 3. Mover um Robô: 
<p> Nesta opção, há os inputs de variação de comprimento em cada coordenada (xy para o terrestre a óleo e xyz para os aéreos e terrestre toupeira) e a chamada da respectiva função de mover (condicionada a classe do robô escolhido), esta que é um algorítmo baseado em backtracking, que utiliza o sensor de movimento para perfazer um caminho livre de obstáculos (salvo o robô topeira, que se aproveita do fato de que não há obstáculos no subsolo para evitar o uso de chamadas recursivas/sensores).  
  
## 4. Relatório de Temperatura: 
</p> Sensor de temperatura de cada robô utiliza a matriz de temperatura contida no ambiente (existe uma temperatura relacionada a cada ponto xyz no ambiente (salvo no subsolo), que é baseada em uma função arbitrária e aleatória definida no construtor de ambiente) e a posição em que o robô se encontra no ambiente da seguinte forma: o sensor procura, na região compreendida por um cubo de lado de duas vezes o raio deste sensor e de centro na posição atual do robô a maior e menor temperatura e as imprime na tela (juntamente com suas respectivas localizações), bem como a temperatura associada a posição atual do robô.  

## 5. Habilidades Especiais:
<p> Invoca a função única assocdiada â classe robô escolhido, a saber: alteração de lubrificação do robô terrestre a óleo, recarga de bateria do robô aéreo dinâmico e geração de relatório do robô aéreo relator.  
  
## 6. Exibir Posição:
<p> Exibe a posição do robô escolhido, com adicional de exibir a lubrificação no caso do robô terrestre a óleo.  
  
***A título de observação, a busca por algum robô é sempre dada pelo seu nome, dando utilidade à variável.***
    
