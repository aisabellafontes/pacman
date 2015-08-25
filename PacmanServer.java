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

  ThreadEscutandoClient[] clients;

  StructClient (){
	  clients = new ThreadEscutandoClient[2];
	  clients[0] = null;
	  clients[1] = null;
  }

  public void updateAllClients(){
	  if (clients[0]!=null) clients[0].sendUpdate();
	  if (clients[1]!=null) clients[1].sendUpdate();
  }

  public void move(int client, int x, int y){
	  if (statusClientA || statusClientB) {
		  if ((client==1 && checkCollision(x, y, posClientBX, posClientBY)) || (client==2 && checkCollision(posClientAX, posClientAY, x, y))) {
			  endGame();
			  return;
		  }
	  }
	//if (!objSC.checkCollision(objSC.posClientAX, objSC.posClientAY, objSC.posClientBX, objSC.posClientBY)) {
		if (client==1 && !checkCollision(x, y, posClientBX, posClientBY)) {
			posClientAX = x;
			posClientAY = y;
			if (checkCollision(x, y, posMagicPotionX, posMagicPotionY)) {
				getPotion(1);
			}
		}
		else if (client==2 && !checkCollision(posClientAX, posClientAY, x, y)) {
			posClientBX = x;
			posClientBY = y;
			if (checkCollision(x, y, posMagicPotionX, posMagicPotionY)) {
				getPotion(2);
			}
		}
	//}
	updateAllClients();
  }

  public void endGame() {
	  if (statusClientA) {
		  clients[0].sendGameEnd("WIN");
		  clients[1].sendGameEnd("LOSE");
	  } else {
		  clients[0].sendGameEnd("LOSE");
		  clients[1].sendGameEnd("WIN");
	  }
  }

  public boolean checkCollision(int x1, int y1, int x2, int y2) {
	  if ((x2 >= x1 && x2 <= x1+50) && (y2 >= y1 && y2 <= y1+50)) return true;
	  if ((x2 >= x1 && x2 <= x1+50) && (y2+50 >= y1 && y2+50 <= y1+50)) return true;
	  if ((x2+50 >= x1 && x2+50 <= x1+50) && (y2 >= y1 && y2 <= y1+50)) return true;
	  if ((x2+50 >= x1 && x2+50 <= x1+50) && (y2+50 >= y1 && y2+50 <= y1+50)) return true;
	  return false;
  }

  public void getPotion(int client) {
	  if (client==1) statusClientA = true;
	  else statusClientB = true;
	  posMagicPotionX = -1;
	  posMagicPotionY = -1;
	  Timer timer = new Timer();
	  timer.schedule(new PotionTask(this), 10000);
  }

  public void resetPotion() {
	  statusClientA = false;
	  statusClientB = false;
	  posMagicPotionX = (new Random()).nextInt(950 - 0) + 0;
      posMagicPotionY = (new Random()).nextInt(750 - 0) + 0;
	  updateAllClients();
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
  static int CONNECTION_NUMBER = 2;   //quantas conexoes o servidor devera abrir

  public static int defineRamdonXY(int min, int max){
      Random objrandom = new Random();
      int random = objrandom.nextInt(max - min) + min;
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
        Socket clientSocket = new Socket();
        try {
          clientSocket = serverSocket.accept();
          System.out.println("Conexão estabelecida com o client:" + i);
          if (objSC.clients[0] == null ){
            objSC.statusClientA = false; //nao bebeu porcao
            objSC.posClientAX = defineRamdonXY(800,900);
            //System.out.println("posClientAX:" + objSC.posClientAX);
            objSC.posClientAY = defineRamdonXY(10, 50);
			objSC.clients[0] = new ThreadEscutandoClient(clientSocket, objSC, 1);
            objSC.clients[0].start();
          }else{
            objSC.statusClientB = false; //nao bebeu porcao
            objSC.posClientBX = defineRamdonXY(10,50);
            objSC.posClientBY = defineRamdonXY(600, 700);
            objSC.posMagicPotionX = defineRamdonXY(0,950);
            objSC.posMagicPotionY = defineRamdonXY(0,750);
			objSC.clients[1] = new ThreadEscutandoClient(clientSocket, objSC, 2);
            objSC.clients[1].start();
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
  int clientNumber;
  PrintStream outputStreamClient;

  StructClient objSC = null;
  //IMPLEMENTACAO OBRIGATORIA DE UMA CLASSE THREAD
  public void run() {
    System.out.println("Iniciando a thread de escuta do cliente " + clientNumber);
    try{

      //tratamento de comunicacao com cliente- canal de entrada e saida
      Scanner inputStreamClient = new Scanner(clientSocket.getInputStream());
      outputStreamClient = new PrintStream(clientSocket.getOutputStream(), true);
      String inputLine;

      outputStreamClient.println(clientNumber);
	  objSC.updateAllClients();

      do{
		inputLine = inputStreamClient.nextLine();
		inputLine = inputLine.replace("[", "");
		inputLine = inputLine.replace("]", "");
		String[] splitedInput = inputLine.split(",");
		if (splitedInput[0]!=null && splitedInput[1]!=null){
			int x = Integer.parseInt(splitedInput[0].trim());
			int y = Integer.parseInt(splitedInput[1].trim());
			if (clientNumber==1) objSC.move(1, x, y);
			else objSC.move(2, x, y);
			System.out.println("ClientThread = " + clientNumber);
		}
      }while(inputLine!="");

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

  public void sendGameEnd(String result) {
	  try{
			outputStreamClient.println(result);
		} catch (NoSuchElementException e) {
			System.out.println("Conexacao terminada pelo cliente.");
		}
  }

  public void sendUpdate() {
		Vector v = new Vector(6);
		v.addElement(objSC.posClientAX);
		v.addElement(objSC.posClientAY);
		v.addElement(objSC.posClientBX);
		v.addElement(objSC.posClientBY);
		v.addElement(objSC.posMagicPotionX);
		v.addElement(objSC.posMagicPotionY);
		v.addElement(objSC.statusClientA);
		v.addElement(objSC.statusClientB);
		System.out.println("SendUpdate, Client = " + clientNumber + ", V = " + v);
		try{
			outputStreamClient.println(v);
		} catch (NoSuchElementException e) {
			System.out.println("Conexacao terminada pelo cliente.");
		}
  }

  ThreadEscutandoClient(Socket clientSocket, StructClient objSC, int clientNumber) {
      this.clientSocket = clientSocket;
      this.clientNumber =  clientNumber;
      this.objSC = objSC;
  }
}

class PotionTask extends TimerTask {

	StructClient objSC;

	PotionTask(StructClient objSC) {
		this.objSC = objSC;
	}

    public void run() {
      System.out.println("Time's up!");
	  objSC.resetPotion();
    }
};
