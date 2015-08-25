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
  static int posX, posY = 0;
  static int posPotionX, posPotionY;
  int hasInitializeGame = 0;
  JPanel board = new JPanel();
  Image pacmanClienteAImage = Toolkit.getDefaultToolkit().getImage("images/pacmanA.png");
  Image pacmanClienteBImage = Toolkit.getDefaultToolkit().getImage("images/pacmanB.png");
  Image pacmanMagicoImage = Toolkit.getDefaultToolkit().getImage("images/pacmanMagic.png");
  Image pocaoMagicaImage = Toolkit.getDefaultToolkit().getImage("images/magicPotion.png");
  Image backgroundImage = Toolkit.getDefaultToolkit().getImage("images/background.jpg");

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
    posPotionX = getSize().width / 2;
    posPotionY = getSize().height / 2;
    //drawImageh(Image img, int x, int y, int width, int height, ImageObserver observer)
    g.drawImage(backgroundImage, 0, 0, getSize().width, getSize().height, this);
    g.drawImage(pacmanClienteAImage, posX, posY, width, height, this);
    g.drawImage(pocaoMagicaImage, posPotionX, posPotionY, width, height, this);
  }



  public static void main(String[] args) {
    //Step 1 - Define ramdom x,y
    defineRamdonXY();
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
      inputLine = inputStreamClient.readLine();
      System.out.println("Server mandou:" + inputLine);

      /*
      do {
        System.out.println(inputLine=inputStreamClient.nextLine());
      } while (!inputLine.equals(""));*/

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
           System.out.println("X:" + posX + "Y:" + posY);
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
            repaint();
       }
   }

}
