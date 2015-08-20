package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

  Dimension d;
  Font smallfont = new Font("Helvetica", Font.BOLD, 14);
  FontMetrics fmsmall, fmlarge;
  Image ii;
  Color dotcolor = new Color(192, 192, 0);
  Color mazecolor;
  boolean ingame = false;
  boolean dying = false;

  final int blocksize = 24;
  final int nrofblocks = 15;
  final int scrsize = nrofblocks * blocksize;
  final int pacanimdelay = 2;
  final int pacmananimcount = 4;
  final int maxghosts = 12;
  final int pacmanspeed = 6;
  


}
