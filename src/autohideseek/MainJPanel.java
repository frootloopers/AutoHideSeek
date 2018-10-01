/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autohideseek;

import static autohideseek.Finder.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Jia Jia Chen
 */
public class MainJPanel extends javax.swing.JPanel {

    public static int[][][] Map;
    public static int[][][] MapInfo;
    public static int pX;
    public static int pY;
    public static int mouseX;
    public static int mouseY;
    int distance;
    Timer time = new Timer(100, new TimerListener());

    /**
     * Creates new form MainJPanel
     */
    public MainJPanel() {

        initComponents();

        Map = new int[100][100][10];
        MapInfo = new int[100][100][10];
        pX = 100;
        pY = 100;
        distance = 0;

        //listen to the keyboard
        KeyListener kListener = new KeyListener() {

            //it wont work without this
            public void keyTyped(KeyEvent key) {
            }

            //when 'X' key is pressed
            public void keyPressed(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        break;
                    case KeyEvent.VK_DOWN:
                        break;
                    case KeyEvent.VK_RIGHT:
                        break;
                    case KeyEvent.VK_LEFT:
                        break;
                    case KeyEvent.VK_SPACE:
                        break;
                }
            }

            //what happens when you let go
            public void keyReleased(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        break;
                    case KeyEvent.VK_DOWN:
                        break;
                    case KeyEvent.VK_RIGHT:
                        break;
                    case KeyEvent.VK_LEFT:
                        break;
                    case KeyEvent.VK_SPACE:
                        distance = SmartFinder(10, 90, 10);
                        break;
                }
            }
        };

        //listen to the mouse
        MouseListener mListener = new MouseListener() {
            public void mouseClicked(MouseEvent ms) {
            }

            public void mouseEntered(MouseEvent ms) {
            }

            public void mouseExited(MouseEvent ms) {
            }

            public void mousePressed(MouseEvent ms) {
                PointerInfo pointer = MouseInfo.getPointerInfo();
                Point mousePoint = ms.getPoint();
                mouseX = mousePoint.x;
                mouseY = mousePoint.y;
                if (mouseX < pX * 10 && mouseX >= 0 && mouseY < pY * 10 && mouseY >= 0) {
                    //delete if right mouse button was clicked
                    if (SwingUtilities.isRightMouseButton(ms) == true) {
                        Map[mouseY / 10][mouseX / 10][1] = 0;
                        //otherwise, paint away
                    } else {
                        Map[mouseY / 10][mouseX / 10][1] = 1;
                    }
                }
//                System.out.println(mouseX + "," + mouseY);
            }

            public void mouseReleased(MouseEvent ms) {
            }
        };

        //listen to the mouse more closely (for when the user is holding the button down)
        MouseMotionListener mMListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent ms) {
                PointerInfo pointer = MouseInfo.getPointerInfo();
                //use .getPoint() or else it gets the point on the computer screen
                Point mousePoint = ms.getPoint();
                mouseX = mousePoint.x;
                mouseY = mousePoint.y;
                if (mouseX < pX * 10 && mouseX >= 0 && mouseY < pY * 10 && mouseY >= 0) {
                    //delete if right mouse button was clicked
                    if (SwingUtilities.isRightMouseButton(ms) == true) {
                        Map[mouseY / 10][mouseX / 10][1] = 0;
                        //otherwise, paint away
                    } else {
                        Map[mouseY / 10][mouseX / 10][1] = 1;
                    }
                }
//                System.out.println(mouseX + "," + mouseY);
            }

            public void mouseMoved(MouseEvent ms) {
            }
        };

        addKeyListener(kListener);
        addMouseListener(mListener);
        addMouseMotionListener(mMListener);
        setFocusable(true);

        time.start();
        
        //
        Map[90][10][0] = 20;
        Map[40][3][0] = 10;
        for (int x = 0; x < 20; x++) {
            Map[60][x][0] = 1;
        }
        MapCopy(0, 1, Map);
        
        //
    }

    public static int[][][] MapCopy(int from, int to, int Copy[][][]) {
        for (int x = 0; x < pX; x++) {
            for (int y = 0; y < pY; y++) {
//                try {
                Copy[y][x][to] = Copy[y][x][from];
//                } catch (Exception e) {
//                    System.out.println("MapCopy: error");
//                }
            }
        }
        return Copy;
    }

    

    public static void Game() {
        MapCopy(1, 0, Map);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1000, 1000);
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * 10, y * 10, 9, 9);
            }
        }
        //
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                //where searched
                if (MapInfo[y][x][0] > 0) {
                    g.setColor(Color.getHSBColor((float) (MapInfo[y][x][0]) / 1000, 1, 1));
                    g.fillRect(x * 10, y * 10, 9, 9);
                }
            }
        }
        //
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                if (Map[y][x][0] > 0) {
                    //seeker
                    if (Map[y][x][0] >= 20) {
                        g.setColor(Color.WHITE);
                        //hider
                    } else if (Map[y][x][0] >= 10) {
                        g.setColor(Color.BLACK);
                        //a target
                    } else if (Map[y][x][0] > 1) {
                        g.setColor(Color.YELLOW);
                        //wall
                    } else if (Map[y][x][0] == 1) {
                        g.setColor(Color.DARK_GRAY);
                        //floor
                    }
                    g.fillRect(x * 10, y * 10, 9, 9);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Game();
            repaint();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
