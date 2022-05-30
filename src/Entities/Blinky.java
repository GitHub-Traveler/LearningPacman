package Entities;

import Tiles.TileManager;
import Core.Algorithms;
import Core.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Blinky extends Entity implements Ghost{
    TileManager manager;
    GamePanel gp;
    Player player;
    public int frame_counter;
    public int chasing_time = 20;
    public int scattering_time = 5;
    public int fleeing_time = 12;
    public boolean isChasing;
    public boolean isFleeing;
    public boolean isDead;
    public boolean end_fleeing = false;
    BufferedImage image,fleeing1,fleeing2,fleeing3,fleeing4,deadup,deaddown,deadleft,deadright;
    public String difficulty = "easy";

    public Blinky(GamePanel gp, TileManager manager, Player player, int initial_x, int initial_y, String difficulty) {
        this.gp = gp;
        this.manager = manager;
        this.player = player;
        getGhostImage();
        setDefaultValue(initial_x,initial_y);
        this.difficulty = difficulty;
        setInterval();
        setSpeed();
    }

    @Override
    public void changeStatus() {
        if (player.gethigh) {
            frame_counter = 0;
            end_fleeing = false;
            isFleeing = true;
        }
        if (isDead) {
            fixPosition();
            speed = base_speed;
            isFleeing = false;
            if (x_coordinate == initial_x_coordinate && y_coordinate == initial_y_coordinate) {
                isDead = false;
            }
        } else if (isFleeing) {
            speed = base_speed/2;
            frame_counter++;
            if (frame_counter >= fleeing_time*2/3*gp.fps) {
                end_fleeing = true;
            }
            if (frame_counter > fleeing_time * gp.fps) {
                frame_counter = 0;
                end_fleeing = false;
                isFleeing = false;
                speed = base_speed;
                fixPosition();
            }
        } else if (!isFleeing) {
            fixPosition();
            speed = base_speed;
            frame_counter++;
            if (isChasing) {
                if (frame_counter > chasing_time * gp.fps) {
                    isChasing = false;
                    frame_counter = 0;
                }
            } else {
                if (frame_counter > scattering_time * gp.fps) {
                    isChasing = true;
                    frame_counter = 0;
                }
            }
        }
    }

    public void fixPosition() {
        if (x % base_speed != 0) {
            if (direction.equals("left")) {
                x -= base_speed/2;
            }
            if (direction.equals("right")) {
                x += base_speed/2;
            }
        }
        if (y % base_speed != 0) {
            if (direction.equals("up")) {
                y -= base_speed/2;
            }
            if (direction.equals("down")) {
                y += base_speed/2;
            }
        }
    }
    @Override
    public void setDefaultValue(int x, int y) {
        isFleeing = false;
        isChasing = false;
        isDead = false;
        initial_x_coordinate = x;
        initial_y_coordinate = y;
        x_coordinate = initial_x_coordinate;
        y_coordinate = initial_y_coordinate;
        this.x = x_coordinate * gp.block_size;
        this.y = y_coordinate * gp.block_size;
        direction = "right";
        incoming_direction = "right";
    }

    @Override
    public void getGhostImage() {
        try {
            deaddown = ImageIO.read(new File("resources/ghost/Blinky/deaddown.png"));
            deadleft = ImageIO.read(new File("resources/ghost/Blinky/deadleft.png"));
            deadright = ImageIO.read(new File("resources/ghost/Blinky/deadright.png"));
            deadup = ImageIO.read(new File("resources/ghost/Blinky/deadup.png"));
            up1 = ImageIO.read(new File("resources/ghost/Blinky/u1.png"));
            up2 = ImageIO.read(new File("resources/ghost/Blinky/u2.png"));
            right1 = ImageIO.read(new File("resources/ghost/Blinky/r1.png"));
            right2 = ImageIO.read(new File("resources/ghost/Blinky/r2.png"));
            left1 = ImageIO.read(new File("resources/ghost/Blinky/l1.png"));
            left2 = ImageIO.read(new File("resources/ghost/Blinky/l2.png"));
            down1 = ImageIO.read(new File("resources/ghost/Blinky/d1.png"));
            down2 = ImageIO.read(new File("resources/ghost/Blinky/d2.png"));
            fleeing1 = ImageIO.read(new File("resources/ghost/Blinky/FleeingBlue1.png"));
            fleeing2 = ImageIO.read(new File("resources/ghost/Blinky/FleeingBlue2.png"));
            fleeing3 = ImageIO.read(new File("resources/ghost/Blinky/FleeingWhite1.png"));
            fleeing4 = ImageIO.read(new File("resources/ghost/Blinky/FleeingWhite2.png"));
        } catch (Exception e) {
            e.printStackTrace(System.out);
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


    public void updateIncomingDirection() {
        if (isDead) {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, initial_x_coordinate, initial_y_coordinate,manager, this);
        } else if (isFleeing) {
            if (x % gp.block_size == 0 && y % gp.block_size == 0 && manager.NodeMap[y_coordinate][x_coordinate] != null) {
                String temporary;
                ArrayList<String> lst = new ArrayList<>();
                lst.add("up"); lst.add("down"); lst.add("left"); lst.add("right");
                Collections.shuffle(lst);
                for (int i = 3; i >= 0; i--) {
                    temporary = lst.get(i);
                    if (temporary.equals("up") && !manager.collisionMap[y_coordinate-1][x_coordinate] && direction != "down") {
                        incoming_direction = temporary;
                        break;
                    }
                    if (temporary.equals("down") && !manager.collisionMap[y_coordinate+1][x_coordinate] && direction != "up") {
                        incoming_direction = temporary;
                        break;
                    }
                    if (temporary.equals("left") && !manager.collisionMap[y_coordinate][x_coordinate-1] && direction != "right") {
                        incoming_direction = temporary;
                        break;
                    }
                    if (temporary.equals("right") && !manager.collisionMap[y_coordinate][x_coordinate+1] && direction != "left") {
                        incoming_direction = temporary;
                        break;
                    }
                }
            }
        } else if (isChasing) {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, player.x_coordinate, player.y_coordinate,manager, this);
        } else {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, 19, 1,manager, this);
        }
    }


    private void updateChasing() {
        if (x % gp.block_size == 0 && y % gp.block_size == 0 && manager.NodeMap[y_coordinate][x_coordinate] != null) {
            if ((incoming_direction.equals("left") && !manager.collisionMap[y_coordinate][x_coordinate - 1]) ||
                    (incoming_direction.equals("right") && !manager.collisionMap[y_coordinate][x_coordinate + 1]) ||
                    (incoming_direction.equals("up") && !manager.collisionMap[y_coordinate - 1][x_coordinate]) ||
                    (incoming_direction.equals("down") && !manager.collisionMap[y_coordinate + 1][x_coordinate])) {
                direction = incoming_direction;
            }
        }
        switch (direction) {
            case "up" -> y -= speed;
            case "down" -> y += speed;
            case "left" -> x -= speed;
            case "right" -> x += speed;
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        if (isDead) {
            image = switch (direction) {
                case "up" -> deadup;
                case "down" -> deaddown;
                case "left" -> deadleft;
                case "right" -> deadright;
                default -> null;
            };
        } else if (!isFleeing) {
            image = switch (direction + SpriteNum) {
                case "up1" -> up1;
                case "up2" -> up2;
                case "down1" -> down1;
                case "down2" -> down2;
                case "right1" -> right1;
                case "right2" -> right2;
                case "left1" -> left1;
                case "left2" -> left2;
                default -> null;
            };
        } else if (!end_fleeing){
            image = switch (SpriteNum) {
                case 1 -> fleeing1;
                case 2 -> fleeing2;
                case 3 -> fleeing1;
                case 4 -> fleeing2;
                default -> fleeing1;
            };
        } else {
            image = switch (SpriteNum) {
                case 1 -> fleeing1;
                case 2 -> fleeing2;
                case 3 -> fleeing3;
                case 4 -> fleeing4;
                default -> fleeing1;
            };
        }
        g2.drawImage(image, x ,y ,gp.block_size,gp.block_size, null);
    }

    private void updateSprite() {
        SpriteCounter++;
        if (SpriteCounter > 3) {
            if (!isFleeing) {
                if (SpriteNum >= 2) {
                    SpriteNum = 1;
                } else {
                    SpriteNum++;
                }
            } else {
                if (SpriteNum >= 4) {
                    SpriteNum = 1;
                } else {
                    SpriteNum++;
                }
            }
            SpriteCounter = 0;
        }
    }
    @Override
    public void update() {
        updateLocation();
        changeStatus();
        updateIncomingDirection();
        updateSprite();
        updateChasing();
    }
    public boolean playerCollide() {
        //if (x_coordinate == player.x_coordinate && y_coordinate == player.y_coordinate) {
        return (x - player.x) * (x - player.x) + (y - player.y) * (y - player.y) <= 36;
    }

    public void setInterval() {
        if (difficulty == "easy") {
            fleeing_time = 12;
            chasing_time = 24;
            scattering_time = 12;
        }
        if (difficulty == "normal") {
            fleeing_time = 8;
            chasing_time = 30;
            scattering_time = 10;
        }
        if (difficulty == "hard") {
            fleeing_time = 6;
            chasing_time = 40;
            scattering_time = 5;
        }
    }
    public void setSpeed() {
        if (difficulty == "easy") {
            base_speed = 2;
        }
        if (difficulty == "normal") {
            base_speed = 3;
        }
        if (difficulty == "hard") {
            base_speed = 4;
        }
    }
}
