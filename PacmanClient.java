import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PacmanClient extends JFrame implements Runnable {
  static PrintStream outputStremClient = null;
  static int PORT = 5000;
  /* Create a new board */
  Background fundo = new Background();

  PacmanClient() {
    super("Corrida de pacman - BSI 2015");
    fundo.requestFocus();
    add(fundo, BorderLayout.CENTER);/* Add the board to the frame */
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new Thread(new PacmanClient()).start();
  }

  //OBRIGATORIA DECLARACAO POIS CLASSE EXTENDE DE Runnable
  public void run() {
    Socket socket = null;
    Scanner inputStreamClient = null;

    try {
      socket = new Socket("127.0.0.1", PORT);
      outputStremClient = new PrintStream(socket.getOutputStream(), true);
      inputStreamClient = new Scanner(socket.getInputStream());
      String inputLine;

      do {
        System.out.println(inputLine=inputStreamClient.nextLine());
      } while (!inputLine.equals(""));

      outputStremClient.close();
      inputStreamClient.close();
      socket.close();

    } catch (UnknownHostException e) {
      System.err.println("Não reconheço a origem do host.");
    } catch (IOException e) {
      System.err.println("falha na comunicação com servidor.IOException.");
    }

  }

}
