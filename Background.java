/* Drew Schuster */
import java.awt.*;
import javax.imageio.*;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.*;
import java.io.*;

class Movimentos{
  /* State contains the game map */
  boolean[][] state;

  /* Generic constructor */
  public Movimentos()
  {
    state = new boolean[20][20];
  }
}

class Jogador extends Movimentos{
  /* Current location */
  int x, y;
  public Jogador(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
}

class PocaoMagica extends Movimentos{
  /* Current location */
  int x,y;
  public PocaoMagica(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
}

/*This board class contains the player, ghosts, pellets, and most of the game logic.*/
public class Background extends JPanel {

  Image pacmanClienteAImage = Toolkit.getDefaultToolkit().getImage("images/pacmanA.png");
  Image pacmanClienteBImage = Toolkit.getDefaultToolkit().getImage("images/pacmanB.png");
  Image pacmanMagicoImage = Toolkit.getDefaultToolkit().getImage("images/pacmanMagic.png");
  Image pocaoMagicaImage = Toolkit.getDefaultToolkit().getImage("images/magicPotion.png");
  Image backgroundImage = Toolkit.getDefaultToolkit().getImage("images/background.jpg");

  /* Initialize the player and ghosts */
  Jogador jogadorA = new Jogador(90,90);
  Jogador jogadorB = new Jogador(10,400);
  PocaoMagica pocao = new PocaoMagica(200,200);

  /* Constructor initializes state flags etc.*/
  public Background() {
  
  }

  public Dimension getPreferredSize() {
    return new Dimension(1024, 800);
  }

  //A PRINCIPAL FUNCAO - IRA REDESENHAR A TELA DURANTE O JOGO
  public void paint(Graphics g) {
    //Funcao que o sistema chama se precisar
    super.paint(g);
    int width = 50;
    int height = 50;
    //drawImageh(Image img, int x, int y, int width, int height, ImageObserver observer)
    g.drawImage(backgroundImage, 0, 0, getSize().width, getSize().height, this);
    g.drawImage(pacmanClienteAImage, 400, 10, width, height, this);
    g.drawImage(pacmanClienteBImage, 10, 400, width, height, this);
    g.drawImage(pocaoMagicaImage, 250, 250, width, height, this);

  }

  public void reset() {

  }

  public void atualizaFundo(int x,int y, int width, int height){

  }

  static public void main(String[] args) {
    Background board = new Background();
  }

}
