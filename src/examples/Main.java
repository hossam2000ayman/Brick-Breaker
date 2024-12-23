/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examples;

import javax.swing.JFrame;

/**
 *
 * @author hossa
 */
public class Main {
    
    public static void main(String[] args) {
    // Create a JFrame for the game window
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the GamePlay panel to the JFrame
        GamePlay gameplay = new GamePlay();
        frame.add(gameplay);

        // Set the frame visible
        frame.setVisible(true);
    }
}
