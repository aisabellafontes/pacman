/* Drew Schuster */
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.JApplet;
import java.awt.*;
import java.util.*;
import java.lang.*;

/* This class contains the entire game... most of the game logic is in the Board class but this
   creates the gui and captures mouse and keyboard input, as well as controls the game states */
public class Pacman extends JApplet implements MouseListener, KeyListener
{
  /* Create a new board */
  Background fundo = new Background();

  public Pacman()
  {
      fundo.requestFocus();

      /* Create and set up window frame*/
      JFrame frame = new JFrame();
      frame.setSize(800, 600);

      /* Add the board to the frame */
      frame.add(fundo, BorderLayout.CENTER);

      /*Set listeners for mouse actions and button clicks*/
      fundo.addMouseListener(this);
      fundo.addKeyListener(this);

      /* Make frame visible, disable resizing */
      frame.setVisible(true);
      frame.setResizable(false);
  }

  public void keyPressed(KeyEvent e) {}
  public void mousePressed(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}

  /* Main function simply creates a new pacman instance*/
  public static void main(String [] args)
  {
      Pacman c = new Pacman();
  }

}
