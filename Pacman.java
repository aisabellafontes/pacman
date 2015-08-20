import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.*;
import javax.imageio.*;

public class Pacman extends JFrame {
    public Pacman() {
        this.setTitle("Pacman Flavinha e Isa");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(new GamePanel());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Pacman();
    }

    public class GamePanel extends JPanel {
        private Image pmImage;
        private int xCoordinate = 200;
        private int yCoordinate = 200;
        boolean key_right, key_left, key_down, key_up; // Input booleans

        URL urlForImage;
        ImageIcon usFlag;
        Image avatar[] = new Image[6]; //para o boneco.gif

        public GamePanel() {
            loadImage("C");
            this.setFocusable(true);
            addKeyListener(new GameInput()); // Add it to the JPanel
        }

        public void loadImage(String img_type) {

          switch(img_type){

            case "D":
              avatar[0] = ImageIO.read(new File("anda0.gif"));
              break;
            case "U":
              avatar[0] = ImageIO.read(new File("anda0.gif"));
              break;
            case "R": break;
            case "L": break;
            case "C": break;
            //avatar[PARADO] = ImageIO.read(new File("parado.gif"));
            avatar[ANDA0] = ImageIO.read(new File("anda0.gif"));
            avatar[ANDA1] = ImageIO.read(new File("anda1.gif"));
            avatar[SOCO0] = ImageIO.read(new File("soco0.gif"));
            avatar[SOCO1] = ImageIO.read(new File("soco1.gif"));
            avatar[SOCO2] = ImageIO.read(new File("soco2.gif"));
          }

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.white);
            g.drawImage(pmImage, xCoordinate, yCoordinate, this);

            if (key_down) { loadImage("D"); yCoordinate++; }
            if (key_up) { loadImage("U"); yCoordinate--; }
            if (key_right) { loadImage("R"); xCoordinate++; }
            if (key_left) { loadImage("L"); xCoordinate--; }
            //for (int index = 0; index < 10000000; index++) {}

            repaint();
        }

        private class GameInput implements KeyListener {
            public void keyTyped(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == e.VK_DOWN) key_down = false;
                if (e.getKeyCode() == e.VK_UP) key_up = false;
                if (e.getKeyCode() == e.VK_RIGHT) key_right = false;
                if (e.getKeyCode() == e.VK_LEFT) key_left = false;
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == e.VK_DOWN) key_down = true;
                if (e.getKeyCode() == e.VK_UP) key_up = true;
                if (e.getKeyCode() == e.VK_RIGHT) key_right = true;
                if (e.getKeyCode() == e.VK_LEFT) key_left = true; //teste
            }
        }
    }
}
