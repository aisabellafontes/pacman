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
import java.awt.image.BufferedImage;

public class PacmanClient extends JFrame implements Runnable {
  private BufferedImage bf;
  static PrintStream outputStremClient = null;
  static String IP = "127.0.0.1";
  static int PORT = 5000;
  JPanel board = new JPanel();
  Image pacmanClienteAImage = Toolkit.getDefaultToolkit().getImage("images/pacmanA.png");
  Image pacmanClienteBImage = Toolkit.getDefaultToolkit().getImage("images/pacmanB.png");
  Image pacmanMagicoImage = Toolkit.getDefaultToolkit().getImage("images/pacmanMagic.png");
  Image pocaoMagicaImage = Toolkit.getDefaultToolkit().getImage("images/magicPotion.png");
  Image backgroundImage = Toolkit.getDefaultToolkit().getImage("images/background.jpg");
  Image endImageW = Toolkit.getDefaultToolkit().getImage("images/win.jpg");
  Image endImageL = Toolkit.getDefaultToolkit().getImage("images/lose.jpg");
  int posMagicPotionX = -1;
  int posMagicPotionY = -1;
  int posClientAX = -1;
  int posClientAY = -1;
  int posClientBX = -1;
  int posClientBY = -1;
  int clientNumber = 0;
  boolean statusClientA = false;
  boolean statusClientB = false;
  int endGame = 0; //0 = não acabou, 1 = ganhou, -1 = perdeu

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
		bf = new BufferedImage( this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);

		try{
			animation(bf.getGraphics());
			g.drawImage(bf,0,0,null);
		}catch(Exception ex){

		}
  }
  
  public void animation(Graphics g) {
    /* Essa funcao é chamada toda vez que as teclas sao pressionadas */
    super.paint(g);
    int width = 50;
    int height = 50;
	if (endGame > 0) {
		g.drawImage(endImageW, 0, 0, getSize().width, getSize().height, this);
	} else if (endGame < 0) {
		g.drawImage(endImageL, 0, 0, getSize().width, getSize().height, this);
	} else {
		g.drawImage(backgroundImage, 0, 0, getSize().width, getSize().height, this);
		if (posMagicPotionX != -1){
		  g.drawImage(pocaoMagicaImage, posMagicPotionX, posMagicPotionY, width, height, this);
		}
		if (posClientAX != -1){
			if (statusClientA) g.drawImage(pacmanMagicoImage, posClientAX, posClientAY, width, height, this);
			else g.drawImage(pacmanClienteAImage, posClientAX, posClientAY, width, height, this);
		}
		if (posClientBX != -1){
			if (statusClientB) g.drawImage(pacmanMagicoImage, posClientBX, posClientBY, width, height, this);
			else g.drawImage(pacmanClienteBImage, posClientBX, posClientBY, width, height, this);
		}
	}
  }

  public static void main(String[] args) {
	  if (args.length > 0) IP = args[0];
    new Thread(new PacmanClient()).start();
  }

  //OBRIGATORIA DECLARACAO POIS CLASSE EXTENDE DE Runnable
  public void run() {
    Socket socket = null;
    DataInputStream inputStreamClient = null;

    try {
      socket = new Socket(IP, PORT);
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
		inputLine = inputLine.replace("[", "");
		inputLine = inputLine.replace("]", "");
		if (!inputLine.equals("WIN") && !inputLine.equals("LOSE")) {
			String[] splitedInput = inputLine.split(",");
			posClientAX = Integer.parseInt(splitedInput[0].trim());
			posClientAY = Integer.parseInt(splitedInput[1].trim());
			posClientBX = Integer.parseInt(splitedInput[2].trim());
			posClientBY = Integer.parseInt(splitedInput[3].trim());
			posMagicPotionX = Integer.parseInt(splitedInput[4].trim());
			posMagicPotionY = Integer.parseInt(splitedInput[5].trim());
			statusClientA = Boolean.parseBoolean(splitedInput[6].trim());
			statusClientB = Boolean.parseBoolean(splitedInput[7].trim());
		} else {
			if (inputLine.equals("WIN")) endGame = 1;
			else endGame = -1;
			outputStremClient.println("");
		}
		repaint();
      } while (!inputLine.equals("WIN") && !inputLine.equals("LOSE"));

      outputStremClient.close();
      inputStreamClient.close();
      socket.close();

    } catch (UnknownHostException e) {
      System.err.println("Não reconheço a origem do host.");
    } catch (IOException e) {
      System.err.println("falha na comunicação com servidor.IOException.");
    }

  }
  
  public void sendUpdate() {
	  Vector v = new Vector(2);
	  if (clientNumber==1) {
		  v.addElement(posClientAX);
		  v.addElement(posClientAY);
	  }
	  else {
		  v.addElement(posClientBX);
		  v.addElement(posClientBY);
	  }
	  try {
		outputStremClient.println(v);
	  } catch (Exception e) {
		System.err.println("falha na comunicação com servidor. Exception.");
	  }
  }

  class Tecla extends KeyAdapter{
       public void keyPressed(KeyEvent ke){
           //System.out.println("Pressionado:" + ke.getKeyCode());
           //System.out.println("X:" + posX + "Y:" + posY);
           int posX, posY, speed;
		   speed = 5;
           if (clientNumber == 1){
             posX = posClientAX;
             posY = posClientAY;
			 if (statusClientA) speed = 10;
           }else{
             posX = posClientBX;
             posY = posClientBY;
			 if (statusClientB) speed = 10;
           }
            switch (ke.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    posX = posX - speed;
                    break;
                case KeyEvent.VK_RIGHT:
                    posX = posX + speed;
                    break;
                case 40: //pra baixo
                      posY = posY + speed;
                     break;
               case 38: //pra cima
                    posY = posY - speed;
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
			sendUpdate();
       }
   }

}
