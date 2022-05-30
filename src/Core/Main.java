package Core;

import javax.swing.*;

public class Main {
    

    public static void main(String[] args) {
        //initiating a new JFrame object
        JFrame window = new JFrame();
        //exit the program when click on close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set to be unable to resize the window
        window.setResizable(true);
        //set title for the window
        window.setTitle("Pacman");
        //initiating a new gamepanel
        GamePanel gamepanel = new GamePanel();
        //add the "gamepanel" layer in the "window" layer
        window.add(gamepanel);
        //set the window size to be equal to the "gamepanel" layer
        window.pack();
        //window location to be in the middle of the screen
        window.setLocationRelativeTo(null);
        //set the window to be visible
        window.setVisible(true);
        gamepanel.startGameThread();
    }
}
