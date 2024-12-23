/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author hossa
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean isPlay = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerPosX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;
    private BrickMap brickMap;

    public GamePlay() {
        brickMap = new BrickMap(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Draw bricks
        brickMap.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Paddle
        g.setColor(Color.green);
        g.fillRect(playerPosX, 550, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // Score
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 550, 30);

        // Win or Game Over
        if (totalBricks == 0) {
            isPlay = false;
            ballXDirection = 0;
            ballYDirection = 0;
            g.setColor(Color.green);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("You Win!", 260, 300);

            g.setFont(new Font("Serif", Font.PLAIN, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if (ballPosY > 570) {
            isPlay = false;
            ballXDirection = 0;
            ballYDirection = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over!", 240, 300);

            g.setFont(new Font("Serif", Font.PLAIN, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (isPlay) {
            // Ball movement
            ballPosX += ballXDirection;
            ballPosY += ballYDirection;

            // Ball collision with paddle
            if (ballPosY >= 530 && ballPosX + 20 >= playerPosX && ballPosX <= playerPosX + 100) {
                ballYDirection = -ballYDirection;
            }

            // Ball collision with walls
            if (ballPosX < 0 || ballPosX > 670) {
                ballXDirection = -ballXDirection;
            }
            if (ballPosY < 0) {
                ballYDirection = -ballYDirection;
            }

            // Ball collision with bricks
            A:
            for (int i = 0; i < brickMap.bricks.length; i++) {
                for (int j = 0; j < brickMap.bricks[0].length; j++) {
                    if (brickMap.bricks[i][j] > 0) {
                        int brickX = j * brickMap.brickWidth + 80;
                        int brickY = i * brickMap.brickHeight + 50;
                        int brickWidth = brickMap.brickWidth;
                        int brickHeight = brickMap.brickHeight;

                        if (ballPosX + 20 >= brickX && ballPosX <= brickX + brickWidth
                                && ballPosY + 20 >= brickY && ballPosY <= brickY + brickHeight) {
                            brickMap.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickX || ballPosX + 1 >= brickX + brickWidth) {
                                ballXDirection = -ballXDirection;
                            } else {
                                ballYDirection = -ballYDirection;
                            }

                            break A;
                        }
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerPosX >= 600) {
                playerPosX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerPosX < 10) {
                playerPosX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!isPlay) {
                isPlay = true;
                resetGame();
            }
        }
    }

    private void moveRight() {
        isPlay = true;
        playerPosX += 20;
    }

    private void moveLeft() {
        isPlay = true;
        playerPosX -= 20;
    }

    private void resetGame() {
        ballPosX = 120;
        ballPosY = 350;
        ballXDirection = -1;
        ballYDirection = -2;
        playerPosX = 310;
        score = 0;
        totalBricks = 21;
        brickMap = new BrickMap(3, 7);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
