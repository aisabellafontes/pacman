/* Drew Schuster */
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.JApplet;
import java.awt.*;
import java.util.*;
import java.lang.*;

public class Pacman extends JApplet implements MouseListener, KeyListener{

  public Pacman()
  {
    b.requestFocus();

    /* Create and set up window frame*/
    JFrame f=new JFrame();
    f.setSize(800,600);

    /* Add the board to the frame */
    f.add(b,BorderLayout.CENTER);

    /*Set listeners for mouse actions and button clicks*/
    b.addMouseListener(this);
    b.addKeyListener(this);

    /* Make frame visible, disable resizing */
    f.setVisible(true);
    f.setResizable(false);
}

}
