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

class StructClient{
  int posMagicPotionX = -1;
  int posMagicPotionY = -1;
  int posClientAX = -1;
  int posClientAY = -1;
  int posClientBX = -1;
  int posClientBY = -1;
  boolean statusClientA, statusClientB;
  StructClient (){
  }

}

class PacmanServer {
  /*
  * ServerSocket is a java.net class that provides a system-independent
  * implementation of the server side of a client/server socket connection.
  */
  static ServerSocket serverSocket = null;
  static StructClient objSC = null;
  static int PORT = 5000;
  static int CONNECTION_NUMBER = 3;   //quantas conexoes o servidor devera abrir

  public static int defineRamdonXY(int min, int max){
      //System.out.println("Definir coordenada de inicio.");
      Random objrandom = new Random();
      int random = objrandom.nextInt(max - min) + min;
      //System.out.println("Coordenada: " + random);
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
          System.out.println("Conexão estabelecida com o client:" + i);
          System.out.println("posClientAX:" + objSC.posClientAX);
          if (objSC.posClientAX == -1 ){
            objSC.statusClientA = false; //nao bebeu porcao
            objSC.posClientAX = defineRamdonXY(600,800);
            System.out.println("posClientAX:" + objSC.posClientAX);
            objSC.posClientAY = defineRamdonXY(10, 50);
            new ThreadEscutandoClient(clientSocket, objSC, 1).start();
          }else{
            objSC.statusClientB = false; //nao bebeu porcao
            objSC.posClientAX = defineRamdonXY(10,50);
            objSC.posClientAY = defineRamdonXY(600, 800);
            objSC.posMagicPotionX = defineRamdonXY(250,300);
            objSC.posMagicPotionY = defineRamdonXY(250,300);
            new ThreadEscutandoClient(clientSocket, objSC, 2).start();
          }

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
        objSC = new StructClient();
        peer_client();
      }catch (Exception e){
        disconnect();
      }
  }
}

class ThreadEscutandoClient extends Thread {
  Socket clientSocket;
  static int clientNumber;

  StructClient objSC = null;
  //IMPLEMENTACAO OBRIGATORIA DE UMA CLASSE THREAD
  public void run() {
    System.out.println("Iniciando a thread de escuta do cliente...");
    try{

      //tratamento de comunicacao com cliente- canal de entrada e saida
      Scanner inputStreamClient = new Scanner(clientSocket.getInputStream());
      PrintStream outputStreamClient = new PrintStream(clientSocket.getOutputStream(), true);
      String inputLine;

      outputStreamClient.println(clientNumber);
      Vector v = new Vector(6 );
      do{
        v.addElement(posClientAX);
        v.addElement(posClientAY);
        v.addElement(posClientBX);
        v.addElement(posClientBY);
        v.addElement(posMagicPotionX);
        v.addElement(posMagicPotionY);
        outputStreamClient.println(v);
      }while(true);

      while (!(inputLine = inputStreamClient.nextLine()).equals("")) {
        System.out.println("CLiente enviou:" +inputLine);
      }



    } catch (IOException e) {
      System.out.println("Falha na conexao.IOException.");
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      System.out.println("Conexacao terminada pelo cliente.");
      //System.out.println("Cliente encerrou mensagens");
      outputStreamClient.flush();
      outputStreamClient.close();
      inputStreamClient.close();
      clientSocket.close();
    }
  }

  ThreadEscutandoClient(Socket clientSocket, StructClient objSC, int clientNumber) {
      this.clientSocket = clientSocket;
      this.clientNumber =  clientNumber;
      this.objSC = objSC;

  }
};
