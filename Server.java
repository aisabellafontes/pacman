/*
*
* Jogo PacMan com 2 jogadores
*  @author: Flavia Crepaldi Mariano, Isabella Fontes
*  @cia: UNESP / 2015
*
*/

import java.net.*;
import java.io.*;
import java.util.*;

class Server(){

  //funcao principal
  public static void main(String[] args) {
    new Server();
  }

  //
  Server(){
    ServerSocket serverSocket=null;
    try {
      serverSocket = new ServerSocket(8000);
    } catch (IOException e) {
      System.out.println("Comunicacao recusada para a porta: " + 7890 + ", " + e);
      System.exit(1);
    }//fim do try

    for (int i=0; i < 2; i++) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
      } catch (IOException e) {
        System.out.println("Accept failed: " + 8000 + ", " + e);
        System.exit(1);
      }
      System.out.println("Comunicacao estabelecida!");
      new Servindo(clientSocket).start();
    }



  }


}
