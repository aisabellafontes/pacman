/*
*
* Jogo Corrida de Pacman com 2 jogadores
*  @author: Isabella Fontes, Flavia Crepaldi Mariano
*  @cia: UNESP Brazil / 2015
*  @email: aisabellafontes@gmail.com / fla_mariano@hotmail.com
*  @github: https://github.com/aisabellafontes/pacman.git
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PacmanClient extends JFrame implements Runnable {
  static PrintStream outputStremClient = null;
  static int PORT = 5000;
  JPanel board = new JPanel();
  Image pacmanClienteAImage = Toolkit.getDefaultToolkit().getImage("images/pacmanA.png");
  Image pacmanClienteBImage = Toolkit.getDefaultToolkit().getImage("images/pacmanB.png");
  Image pacmanMagicoImage = Toolkit.getDefaultToolkit().getImage("images/pacmanMagic.png");
  Image pocaoMagicaImage = Toolkit.getDefaultToolkit().getImage("images/magicPotion.png");
  Image backgroundImage = Toolkit.getDefaultToolkit().getImage("images/background.jpg");
  int posMagicPotionX = -1;
  int posMagicPotionY = -1;
  int posClientAX = -1;
  int posClientAY = -1;
  int posClientBX = -1;
  int posClientBY = -1;
  int clientNumber = 0;

  PacmanClient() {
    super("Corrida de pacman - BSI 2015");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(board, BorderLayout.CENTER);/* Add the board to the frame */
    addKeyListener(new Tecla());
    pack();
    setVisible(true);
  }

  public Dimension getPreferredSize() {
    return new Dimension(1024, 800);
  }

  //A PRINCIPAL FUNCAO - IRA REDESENHAR A TELA DURANTE O JOGO
  public void paint(Graphics g) {
    /* Essa funcao é chamada toda vez que as teclas sao pressionadas */
    super.paint(g);
    int width = 50;
    int height = 50;
    if (posClientAX == -1){
      g.drawImage(backgroundImage, 0, 0, getSize().width, getSize().height, this);
    }
    if (posClientAX != -1){
      g.drawImage(pacmanClienteAImage, posClientAX, posClientAY, width, height, this);
    }
    if (posClientBX != -1){
      g.drawImage(pacmanClienteBImage, posClientBX, posClientBY, width, height, this);
    }
    if (posMagicPotionX != -1){
      g.drawImage(pocaoMagicaImage, posMagicPotionX, posMagicPotionY, width, height, this);
    }
  }

  public void updateScreen(int x, int y){
    System.out.println("Enviar coordenadas x:"+x+" y:" + y);
  }



  public static void main(String[] args) {
    new Thread(new PacmanClient()).start();
  }

  //OBRIGATORIA DECLARACAO POIS CLASSE EXTENDE DE Runnable
  public void run() {
    Socket socket = null;
    DataInputStream inputStreamClient = null;

    try {
      socket = new Socket("127.0.0.1", PORT);
      outputStremClient = new PrintStream(socket.getOutputStream(), true);
      inputStreamClient = new DataInputStream(socket.getInputStream());
      String inputLine;
      inputLine = inputStreamClient.readLine();
      clientNumber = Integer.parseInt(inputLine);
      System.out.println("Server mandou:" + clientNumber);

      //enviar coordenada do cliente
      do {
        inputLine = inputStreamClient.readLine();
        System.out.println("Server mandou2:" + inputLine);
      } while ();

      outputStremClient.close();
      inputStreamClient.close();
      socket.close();

    } catch (UnknownHostException e) {
      System.err.println("Não reconheço a origem do host.");
    } catch (IOException e) {
      System.err.println("falha na comunicação com servidor.IOException.");
    }

  }

  class Tecla extends KeyAdapter{
       public void keyPressed(KeyEvent ke){
           //System.out.println("Pressionado:" + ke.getKeyCode());
           //System.out.println("X:" + posX + "Y:" + posY);
           int posX, posY;
           if (clientNumber == 1){
             posX = posClientAX;
             posY = posClientAY;
           }else{
             posX = posClientBX;
             posY = posClientBY;
           }
            switch (ke.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    posX = posX - 5;
                    break;
                case KeyEvent.VK_RIGHT:
                    posX = posX + 5;
                    break;
                case 40: //pra baixo
                      posY = posY + 5;
                     break;
               case 38: //pra cima
                    posY = posY - 5;
                    break;
            }
            if (clientNumber == 1){
              posClientAX = posX;
              posClientAY = posY;
            }else{
              posClientBX = posX;
              posClientBY = posY;
            }
            repaint();
            updateScreen(posX,posY);
       }
   }

}
