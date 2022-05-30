package Entities;

import Sound.SoundManager;
import Tiles.TileManager;
import Core.GamePanel;
import Core.KeyControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity{

    public String player_name = "";
    GamePanel gp;
    KeyControl player_control;
    public boolean gethigh = false;
    public Image plr;
    public Image lives_image;
    public TileManager manager;
    public int lives;
    public int score;
    public SoundManager soundmanager;
    public Player(GamePanel gp, KeyControl player_control, TileManager manager, SoundManager soundmanager) {
        this.gp = gp;
        this.player_control = player_control;
        this.manager = manager;
        lives = 3;
        setDefaultValue();
        getPlayerImage();
        this.soundmanager = soundmanager;
    }
    public void setDefaultValue() {
        x_coordinate = 10;
        y_coordinate = 21;
        x = x_coordinate * gp.block_size;
        y = y_coordinate * gp.block_size;
        speed = 4;
        direction = "up";
        incoming_direction = "none";
    }
    private void updateScoreAndStatus() {
        if (manager.MapData[y_coordinate][x_coordinate] == 00) {
            manager.MapData[y_coordinate][x_coordinate] = 99;
            score += 10;
        } else if (manager.MapData[y_coordinate][x_coordinate] == 01) {
            manager.MapData[y_coordinate][x_coordinate] = 99;
            score += 10;
            gethigh = true;
            soundmanager.stopAllSound();
            soundmanager.playSound(0);
        }
    }
    private void updateSprite() {
        SpriteCounter++;
        if (SpriteCounter > 3) {
            if (SpriteNum == 4) {
                SpriteNum = 1;
            } else {
                SpriteNum++;
            }
            SpriteCounter = 0;
        }
    }
    private void updateLocation() {
        if (x > (gp.maxScreenCol-1) * gp.block_size) {
            x = x - (gp.maxScreenCol-1) * gp.block_size;
        }
        if (x < 0) {
            x = x + (gp.maxScreenCol-1) * gp.block_size;
        }
        if (x % gp.block_size == 0 && y % gp.block_size == 0) {
            x_coordinate = x / gp.block_size;
            y_coordinate = y / gp.block_size;
        }
    }
    private void updateDirection() {
        int x = x_coordinate;
        int y = y_coordinate;
        try {
            if (incoming_direction.equals("up") && this.x % gp.block_size == 0 && !manager.collisionMap[y - 1][x] ||
                    incoming_direction.equals("down") && this.x % gp.block_size == 0 && !manager.collisionMap[y + 1][x] ||
                    incoming_direction.equals("left") && this.y % gp.block_size == 0 && !manager.collisionMap[y][x - 1] ||
                    incoming_direction.equals("right") && this.y % gp.block_size == 0 && !manager.collisionMap[y][x + 1]) {
                direction = incoming_direction;
            }
            if ((!direction.equals("up") || !manager.collisionMap[y - 1][x]) &&
                    (!direction.equals("down") || !manager.collisionMap[y + 1][x]) &&
                    (!direction.equals("left") || !manager.collisionMap[y][x - 1]) &&
                    (!direction.equals("right") || !manager.collisionMap[y][x + 1])) {
                switch (direction) {
                    case "up" -> this.y -= speed;
                    case "down" -> this.y += speed;
                    case "left" -> this.x -= speed;
                    case "right" -> this.x += speed;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (player_control.leftPressed) {
                direction = "left";
            } else if (player_control.rightPressed) {
                direction = "right";
            }
            switch (direction) {
                case "up" -> this.y -= speed;
                case "down" -> this.y += speed;
                case "left" -> this.x -= speed;
                case "right" -> this.x += speed;
            }
        }
    }
    private void updateIncomingDirection() {
        if (player_control.upPressed || player_control.leftPressed || player_control.rightPressed || player_control.downPressed) {
            if (player_control.upPressed) {
                incoming_direction = "up";
            } else if (player_control.leftPressed) {
                incoming_direction = "left";
            } else if (player_control.rightPressed) {
                incoming_direction = "right";
            } else if (player_control.downPressed) {
                incoming_direction = "down";
            }
        }
    }
    public void update() {
        gethigh = false;
        updateLocation();
        updateSprite();
        updateIncomingDirection();
        updateDirection();
        updateScoreAndStatus();
    }

    public void getPlayerImage() {
        try {
            plr = new ImageIcon(this.getClass().getResource("/left_eat_pac.gif")).getImage();
            up1 = ImageIO.read(new File("resources/player/u1.png"));
            down1 = ImageIO.read(new File("resources/player/d1.png"));
            left1 = ImageIO.read(new File("resources/player/l1.png"));
            right1 = ImageIO.read(new File("resources/player/r1.png"));
            up2 = ImageIO.read(new File("resources/player/u2.png"));
            down2 = ImageIO.read(new File("resources/player/d2.png"));
            left2 = ImageIO.read(new File("resources/player/l2.png"));
            right2 = ImageIO.read(new File("resources/player/r2.png"));
            up3 = ImageIO.read(new File("resources/player/u3.png"));
            down3 = ImageIO.read(new File("resources/player/d3.png"));
            left3 = ImageIO.read(new File("resources/player/l3.png"));
            right3 = ImageIO.read(new File("resources/player/r3.png"));
            up4 = ImageIO.read(new File("resources/player/u4.png"));
            down4 = ImageIO.read(new File("resources/player/d4.png"));
            left4 = ImageIO.read(new File("resources/player/l4.png"));
            right4 = ImageIO.read(new File("resources/player/r4.png"));
            lives_image = ImageIO.read(new File("resources/player/lives.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction + SpriteNum){
            case "right1" -> right1;
            case "down1" -> down1;
            case "up1" -> up1;
            case "left1" -> left1;
            case "right2" -> right2;
            case "down2" -> down2;
            case "up2" -> up2;
            case "left2" -> left2;
            case "right3" -> right3;
            case "down3" -> down3;
            case "up3" -> up3;
            case "left3" -> left3;
            case "right4" -> right4;
            case "down4" -> down4;
            case "up4" -> up4;
            case "left4" -> left4;
            default -> null;
        };
        g2.drawImage(image,x,y, gp.block_size, gp.block_size,null);
    }
}
