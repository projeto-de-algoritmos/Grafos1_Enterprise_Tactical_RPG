# Enterprise Tactical RPG

**Número da Lista**: 25<br>
**Conteúdo da Disciplina**: Grafos 1<br>

## Alunos
|Matrícula | Aluno |
| ---------- | -- |
| 15/0058462 |  Davi Antônio da Silva Santos |
| 18/0100840 |  Gabriel Azevedo Batalha |

## Sobre 
Jogo de sobrevivência onde o jogador precisa fugir durante o maior
tempo possível das unidades inimigas.

Vídeo de apresentação: https://youtu.be/S6_jEJ_Zz_U

## Screenshots
![Jogo em execução](https://i.imgur.com/eFW2pIh.png)

Jogo em execução

![Jogo em execução 2](https://i.imgur.com/riBN9PD.png)

Jogo em execução 

![Tela de fim de jogo](https://i.imgur.com/zP7EUYP.png)

Tela de fim de jogo

## Instalação 
**Linguagem**: Java<br>
**Framework**: Swing<br>

### Requisitos
- Java JRE 11 ou superior.
- Computador com *mouse*.

## Uso 
Clone o repositório ou baixe somente o .jar
`EnterpriseTacticalRPG..jar`

Para executar o programa, use
```
java -jar EnterpriseTacticalRPG.jar
```
O jogo é controlado pelo *mouse*.

O jogador é um ponto azul na tela e deve fugir dos pontos vermelhos
inimigos.

A partida termina quando o jogador é alcançado por qualquer um dos
inimigos.

## Desenvolvimento
Ao importar o projeto em sua IDE talvez seja necessário retirar o
.jar. É possível que a IDE tente usar as classes empacotadas no lugar
das que estão definidas no código fonte.

## Outros 
Os movimentos do jogador e dos inimigos são determinados por um
algoritmo de traçado de caminho usando busca em largura em uma
matriz genérica.

A biblioteca utilizada pode ser facilmente adaptada para uso de
busca em profundidade e definição de obstáculos para o movimento das
unidades.

Iniciou-se a implementação com uma biblioteca de grafos em lista de
adjacência também genérica, mas logo os autores perceberam que seguiam
pelo caminho errado...