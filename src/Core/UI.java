package Core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class UI {
    private int counter = 0;
    public int command_num = 0;
    GamePanel gp;
    Font main_font;
    BufferedImage pacman_lives,ghost1,ghost2,ghost3,ghost4,ghostflee;
    Image rightPac, leftPac;
    Graphics2D g2;

    Leaderboard leaderboard;
    int current_x = -200;
    boolean reverse = false;
    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            leaderboard = new Leaderboard("resources/leaderboard.txt");
            main_font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/emulogic-font/Emulogic.ttf"));
            ghost1 = ImageIO.read(new File("resources/ghost/Blinky/l1.png"));
            ghost2 = ImageIO.read(new File("resources/ghost/Pinky/l1.png"));
            ghost3 = ImageIO.read(new File("resources/ghost/Inky/l1.png"));
            ghost4 = ImageIO.read(new File("resources/ghost/Clyde/l1.png"));
            ghostflee = ImageIO.read(new File("resources/ghost/Blinky/FleeingBlue1.png"));
            rightPac = new ImageIcon(getClass().getResource("/right_eat_pac.gif")).getImage();
            leftPac = new ImageIcon(getClass().getResource("/left_eat_pac.gif")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        //SetFont
        g2.setFont(main_font);
        g2.setColor(Color.white);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //draw play state
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        if (gp.gameState == gp.endgameState) {
            drawEndgame();
        }
    }

    private void drawPlayScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16));
        g2.drawString("Score: " + gp.player.score, gp.maxScreenCol * gp.block_size - 200, gp.maxScreenRow * gp.block_size + 50);
        for (int i = 0; i < gp.player.lives; i++) {
            g2.drawImage(gp.player.lives_image, gp.block_size + i * 30, gp.block_size * gp.maxScreenRow + 20,gp.block_size,gp.block_size, null);
        }
    }

    private void drawTitleScreen() {
        //MAIN MENU
        if (gp.UIstate == gp.UIbegin) {
           drawMainMenu();
        }
        if (gp.UIstate == gp.UIdifficulty) {
            drawDifficulty();
        }
        if (gp.UIstate == gp.UIleaderboard) {
            drawLeaderboard();
        }
    }



    public int getCenteredCoordinateX(String txt) {
        int length = (int) g2.getFontMetrics().getStringBounds(txt, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    private void drawMainMenu() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));
        String title = "PAK-MAN";
        int title_x = getCenteredCoordinateX(title);
        int title_y = gp.screenHeight / 4;
        g2.drawString(title, title_x, title_y);
        if (!reverse) {
            g2.drawImage(rightPac, current_x + 50, title_y + 65, gp.block_size * 3, gp.block_size * 3, null);
            g2.drawImage(ghost1, current_x - 50, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghost2, current_x - 100, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghost3, current_x - 150, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghost4, current_x - 200, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            current_x += 4;
            if (current_x >= 1000) {
                reverse = true;
            }
        }
        if (reverse) {
            g2.drawImage(leftPac, current_x + 50, title_y + 65, gp.block_size * 3, gp.block_size * 3, null);
            g2.drawImage(ghostflee, current_x - 50, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghostflee, current_x - 100, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghostflee, current_x - 150, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            g2.drawImage(ghostflee, current_x - 200, title_y + 75, gp.block_size * 2, gp.block_size * 2, null);
            current_x -= 4;
            if (current_x <= -500) {
                reverse = false;
            }
        }
        AffineTransform transform = new AffineTransform();
        transform.setToScale(1, 1.25);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, transform));


        String text = "NEW GAME";
        int text_x = getCenteredCoordinateX(text);
        int text_y = title_y + 200;
        g2.drawString(text, text_x, text_y);
        if (command_num == 0) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        text = "LEADERBOARD";
        text_x = getCenteredCoordinateX(text);
        text_y = title_y + 250;
        g2.drawString(text, text_x, text_y);
        if (command_num == 1) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        text = "EXIT";
        text_x = getCenteredCoordinateX(text);
        text_y = title_y + 300;
        g2.drawString(text, text_x, text_y);
        if (command_num == 2) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24));
        text = "MADE BY GROUP 3";
        text_x = getCenteredCoordinateX(text);
        text_y = title_y + 450;
        g2.drawString(text, text_x, text_y);
    }

    private void drawLeaderboard() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        String name;
        String score;
        String title = "Name";
        int y = 200;
        g2.drawString(title, 100, y - 50);
        title = "Score";
        g2.drawString(title, 350, y - 50);
        title = "No";
        g2.drawString(title,20,y- 50);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        for (int i = 0; i < 10; i++, y+=50) {
            String[] info = leaderboard.getMaxInfo();
            name = info[0];
            score = info[1];
            g2.drawString(name, 100, y);
            g2.drawString(score, 350, y);
            g2.drawString(Integer.toString(i+1), 20, y);
        }
        leaderboard = new Leaderboard("resources/leaderboard.txt");

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36));
        title = "Leaderboard";
        int titleX = getCenteredCoordinateX(title);
        g2.drawString(title, titleX, 80);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        title = "PRESS ENTER TO EXIT";
        titleX = getCenteredCoordinateX(title);
        g2.drawString(title,titleX,720);
    }

    public void drawDifficulty() {
        AffineTransform transform = new AffineTransform();
        transform.setToScale(1, 1.25);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, transform));

        int base_y = 200;
        String text = "CHOOSE DIFFICULTY";
        int text_x = getCenteredCoordinateX(text);
        int text_y = base_y + 200;
        g2.drawString(text, text_x, text_y);

        text = "EASY";
        text_x = getCenteredCoordinateX(text);
        text_y = base_y + 250;
        g2.drawString(text, text_x, text_y);
        if (command_num == 0) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        text = "MEDIUM";
        text_x = getCenteredCoordinateX(text);
        text_y = base_y + 300;
        g2.drawString(text, text_x, text_y);
        if (command_num == 1) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        text = "HARD";
        text_x = getCenteredCoordinateX(text);
        text_y = base_y + 350;
        g2.drawString(text, text_x, text_y);
        if (command_num == 2) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }

        text = "RETURN";
        text_x = getCenteredCoordinateX(text);
        text_y = base_y + 500;
        g2.drawString(text, text_x, text_y);
        if (command_num == 3) {
            g2.drawString(">", text_x - gp.block_size * 3, text_y);
        }
    }

    private void drawEndgame() {
        //DRAW THE ENDGAME INTERFACE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));
        String txt = "GAME OVER";
        int txt_x = getCenteredCoordinateX(txt);
        int txt_y = 200;
        g2.drawString(txt,txt_x,txt_y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        txt = "ENTER YOUR NAME";
        txt_x = getCenteredCoordinateX(txt);
        txt_y += 100;
        g2.drawString(txt,txt_x,txt_y);

        //DRAW THE NAME INPUT FIELD
        txt = gp.player.player_name;
        txt_x = getCenteredCoordinateX(txt);
        txt_y += 100;
        g2.drawString(txt,txt_x,txt_y);
        //DRAW THE INSERTION POINT
        if (counter % 30 > 14) {
            g2.drawString("_", gp.screenWidth - txt_x, txt_y);
        }
        counter++;
        //DRAW THE SCORE
        txt = "Score: " + Integer.toString(gp.player.score);
        txt_x = getCenteredCoordinateX(txt);
        txt_y += 200;
        g2.drawString(txt,txt_x,txt_y);
    }
}
