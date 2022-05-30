package Core;

import Entities.*;
import Sound.*;
import Tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.Set;


public class GamePanel extends JPanel implements Runnable {
    private final int original_block_size = 24;
    final int scale = 1;
    //Basic Information
    public final int block_size = original_block_size * scale;
    public final int maxScreenCol = 21;
    public final int maxScreenRow = 28;
    public final int screenWidth = maxScreenCol * block_size;
    public final int screenHeight = maxScreenRow * block_size + 100;
    //FPS
    public int fps = 30;
    //GAME STATE
    public int gameState = 0 ;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int endgameState = 3;
    public int UIstate = 0;
    public final int UIbegin = 0;
    public final int UIleaderboard = 1;
    public final int UIdifficulty = 2;
    public String difficulty = "easy";


    long mili_time = System.currentTimeMillis();

    //MAIN ELEMENT
    Thread gameThread;
    KeyControl Ctrl;
    //COMPONENTS
    public UI ui;
    SoundManager soundmanager1;

    TileManager tileM1;

    //ENTITY
    Player player;
    Blinky blinky1;
    Pinky pinky1;
    Inky inky1;
    Clyde clyde1;




    public void initiateGame(String difficulty, int level) {
        tileM1.changeMap(tileM1.level);
        //ENTITY
        player = new Player(this, Ctrl, tileM1, soundmanager1);
        blinky1 = new Blinky(this, tileM1, player, 10, 11, difficulty);
        pinky1 = new Pinky(this, tileM1,player,10,11, difficulty);
        inky1 = new Inky(this, tileM1,player,10,11,blinky1,difficulty);
        clyde1 = new Clyde(this, tileM1, player, 10, 11,difficulty);
    }
    public GamePanel() {
        Ctrl = new KeyControl(this);
        //COMPONENTS
        ui = new UI(this);
        soundmanager1 = new SoundManager(this,"resources/Audio/PakmanSound.txt");

        tileM1 = new TileManager(this,"resources/MapListDirectory.txt");
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(Ctrl);
        this.setFocusable(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double sleep_time = 1000000000/fps; //10^9 nanoseconds = 1 second -> reach 60 fps
        double next_loop_time = System.nanoTime() + sleep_time;
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println(threadSet);

        while (gameThread != null) {
            // 1. UPDATE INFORMATION: update infomation such as player's location based on real time value
            update();
            // 2. DRAW: draw the screen of the software based on the updated infomation
            repaint();
            // 3. SLEEP UNTIL NEXT LOOP
            try {
                double remaining_time = next_loop_time - System.nanoTime();
                remaining_time /= 1000000;
                if (remaining_time < 0) {
                    remaining_time = 0;
                }
                Thread.sleep((long) remaining_time);
                next_loop_time = System.nanoTime() + sleep_time;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update() {
        manageSound();
        if (gameState == playState) {
            if (tileM1.isComplete()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tileM1.level != tileM1.map_list.size()) {
                    nextLevel();
                } else {
                    resetLevel();
                }
            }
            player.update();
            blinky1.update();
            pinky1.update();
            inky1.update();
            clyde1.update();
            if (blinky1.playerCollide() || pinky1.playerCollide() || inky1.playerCollide() || clyde1.playerCollide()) {
                if ((blinky1.playerCollide() && !blinky1.isFleeing && !blinky1.isDead)
                        || (pinky1.playerCollide() && !pinky1.isFleeing && !pinky1.isDead)
                        || (inky1.playerCollide() &&!inky1.isFleeing && !inky1.isDead)
                        || (clyde1.playerCollide() &&!clyde1.isFleeing && !clyde1.isDead)) {
                    soundmanager1.stopAllSound();
                    soundmanager1.playSound(1);
                    player.lives--;
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    player.setDefaultValue();
                    blinky1.setDefaultValue(10, 11);
                    pinky1.setDefaultValue(10, 11);
                    inky1.setDefaultValue(10, 11);
                    clyde1.setDefaultValue(10, 11);
                } else {
                    if (blinky1.isFleeing & blinky1.playerCollide()) {
                        blinky1.isDead = true;
                        blinky1.fixPosition();
                        try {
                            //soundmanager1.stopAllSound();
                            soundmanager1.playSound(2);
                            Thread.sleep(1500);
                            //soundmanager1.resumeAllSound();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inky1.isFleeing && inky1.playerCollide()) {
                        inky1.isDead = true;
                        inky1.fixPosition();
                        try {
                            //soundmanager1.stopAllSound();
                            soundmanager1.playSound(2);
                            Thread.sleep(1500);
                            //soundmanager1.resumeAllSound();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (pinky1.isFleeing && pinky1.playerCollide()) {
                        pinky1.isDead = true;
                        pinky1.fixPosition();
                        try {
                            //soundmanager1.stopAllSound();
                            soundmanager1.playSound(2);
                            Thread.sleep(1500);
                            //soundmanager1.resumeAllSound();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (clyde1.isFleeing && clyde1.playerCollide()) {
                        clyde1.isDead = true;
                        clyde1.fixPosition();
                        try {
                            //soundmanager1.stopAllSound();
                            soundmanager1.playSound(2);
                            Thread.sleep(1500);
                            //soundmanager1.resumeAllSound();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (player.lives <= 0) {
                gameState = endgameState;
                UIstate = UIbegin;
                ui.command_num = 0;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //TITLE SCREEN
        if (gameState == playState) {
            drawPlayScreen(g2);
        }
        ui.draw(g2);
        g2.dispose();
    }
    public void drawPlayScreen(Graphics2D g2) {

        tileM1.draw(g2);
        player.draw(g2);
        blinky1.draw(g2);
        pinky1.draw(g2);
        inky1.draw(g2);
        clyde1.draw(g2);
    }

    public void nextLevel() {
        tileM1.level++;
        tileM1.changeMap(tileM1.level);
        player.setDefaultValue();
        blinky1.setDefaultValue(10, 11);
        inky1.setDefaultValue(10, 11);
        pinky1.setDefaultValue(10, 11);
        clyde1.setDefaultValue(10, 11);
    }
    public void resetLevel() {
        tileM1.level = 1;
        tileM1.changeMap(tileM1.level);
        player.setDefaultValue();
        blinky1.setDefaultValue(10, 11);
        inky1.setDefaultValue(10, 11);
        pinky1.setDefaultValue(10, 11);
        clyde1.setDefaultValue(10, 11);
        soundmanager1.playSound(3);
    }

    void manageSound() {
        if (gameState == playState) {
            soundmanager1.stopSound(4);
            if (!blinky1.isFleeing && !pinky1.isFleeing && !inky1.isFleeing && !clyde1.isFleeing) {
                if (soundmanager1.isStopped(3)) {
                    soundmanager1.stopSound(0);
                    soundmanager1.playSound(3);
                } else {
                    soundmanager1.continueSound(3);
                }
            } else {
                soundmanager1.continueSound(0);
            }
        } else {
            if (soundmanager1.isStopped(4)) {
                soundmanager1.stopAllSound();
                soundmanager1.playSound(4);
            } else {
                soundmanager1.continueSound(4);
            }
        }
    }
}
