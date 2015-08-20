/* Drew Schuster */
import java.awt.*;
import javax.imageio.*;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.*;
import java.io.*;

class Movimentos{

}

class Jogador extends Movimentos{

}

class PocaoMagica extends Movimentos{

}

/*This board class contains the player, ghosts, pellets, and most of the game logic.*/
public class Background extends JPanel{

  Image pacmanClienteAImage = Toolkit.getDefaultToolkit().getImage("images/pacmanA.png");
  Image pacmanClienteBImage = Toolkit.getDefaultToolkit().getImage("images/pacmanB.png");
  Image pacmanMagicoImage = Toolkit.getDefaultToolkit().getImage("images/pacmanMagic.png");
  Image pocaoMagicaImage = Toolkit.getDefaultToolkit().getImage("images/magicPotion.png");
  Image backgrounImage = Toolkit.getDefaultToolkit().getImage("images/background.jpg");

  /* Initialize the player and ghosts */
  Jogador jogadorA = new Jogador(400,10);
  Jogador jogadorB = new Jogador(10,400);
  PocaoMagica pocao = new PocaoMagica(200,200);

  /* Constructor initializes state flags etc.*/
  public Board()
  {

  }

  public Dimension getPreferredSize() {
    return new Dimension(800, 400);
  }

  public void paint(Graphics g) {
    //Funcao que o sistema chama se precisar
    super.paint(g);
    //drawImageh(Image img, int x, int y, int width, int height, ImageObserver observer)
    g.drawImage(backgrounImage, 0, 0, getSize().width, getSize().height, this);
  }

  public void reset()
  {

  }

  public void atualizaFundo(int x,int y, int width, int height)
  {

  }

}
