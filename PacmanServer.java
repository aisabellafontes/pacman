/*
*
* Jogo Corrida de Pacman com 2 jogadores
*  @author: Isabella Fontes, Flavia Crepaldi Mariano
*  @cia: UNESP Brazil / 2015
*  @email: aisabellafontes@gmail.com / fla_mariano@hotmail.com
*  @github: https://github.com/aisabellafontes/pacman.git
*/

import java.net.*;
import java.io.*;
import java.util.*;

class PacmanServer {
  /*
  * ServerSocket is a java.net class that provides a system-independent
  * implementation of the server side of a client/server socket connection.
  */
  static ServerSocket serverSocket = null;
  static int PORT = 5000;
  static int CONNECTION_NUMBER = 3;   //quantas conexoes o servidor devera abrir
  static int posMaginPotionX, posMagicPotionY = null;
  static int posClientAX, posClientAY = null;
  static int posClientBX, posClientBY = null;
  static boolean statusClientA, statusClientB = null;

  public static int defineRamdonXY(int min, int max){
      System.out.println("Definir coordenada de inicio.");
      Random random = new Random();
      int random = random.nextInt(max - min) + min;
      System.out.println("Coordenada: " + random);
      return random;
  }

  public static void disconnect(){
    System.out.println("Disconectando o servidor...");
    try {
      serverSocket.close();
    } catch (IOException e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

  public static void connect(){
      try {
        serverSocket = new ServerSocket(PORT);
      } catch (IOException e) {
        System.out.println("Nao foi possivel habilitar servidor na porta: " + PORT + ", " + e);
        System.exit(1);
      }
    }

  public static void peer_client(){
      for (int i=0; i < CONNECTION_NUMBER; i++) {
        Socket clientSocket = null;
        try {
          clientSocket = serverSocket.accept();
          System.out.println("Conexão estabelecida com o client:" + (i+1));
          if (statusClientA == null){
            statusClientA = false; //nao bebeu porcao
            posClientAX = defineRamdonXY(600,800);
            posClientAY = defineRamdonXY(10, 50);
          }else{
            statusClientB = false; //nao bebeu porcao
            posClientAX = defineRamdonXY(10,50);
            posClientAY = defineRamdonXY(600, 800);
          }
          new ThreadEscutandoClient(clientSocket).start();
        } catch (IOException e) {
          System.out.println("Falha na conexao: " + PORT + ", " + e);
          System.exit(1);
        }
      }
  }

  public static void main(String[] args) {
      //funcao principal
      try{
        connect();
        peer_client();
      }catch (Exception e){
        disconnect();
      }
  }
}

class ThreadEscutandoClient extends Thread {
  Socket clientSocket;
  static int CONNECTION_NUMBER = 3;   //quantas conexoes o servidor devera abrir

  //IMPLEMENTACAO OBRIGATORIA DE UMA CLASSE THREAD
  public void run() {
    System.out.println("Iniciando a thread de escuta do cliente...");
    try{

      //tratamento de comunicacao com clientes - o que fazer com o que chega do cliente
      Scanner inputStreamClient = new Scanner(clientSocket.getInputStream());
      PrintStream outputStreamClient = new PrintStream(clientSocket.getOutputStream(), true);
      String inputLine;

      outputStreamClient

      /*
      while (!(inputLine = inputStreamClient.nextLine()).equals("")) {
        System.out.println(inputLine);
      }*/

      System.out.println("Cliente encerrou mensagens");
      outputStreamClient.flush();
      outputStreamClient.close();
      inputStreamClient.close();
      clientSocket.close();

    } catch (IOException e) {
      System.out.println("Falha na conexao.IOException.");
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      System.out.println("Conexacao terminada pelo cliente.");
    }
  }

  ThreadEscutandoClient(Socket clientSocket) {
      this.clientSocket = clientSocket;
  }
};
