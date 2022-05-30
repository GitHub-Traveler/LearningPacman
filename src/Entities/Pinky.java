package Entities;

import Tiles.TileManager;
import Core.Algorithms;
import Core.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Pinky extends Blinky {
    public Pinky(GamePanel gp, TileManager manager, Player player, int initial_x, int initial_y, String difficulty) {
        super(gp, manager, player, initial_x, initial_y, difficulty);
    }
    @Override
    public void updateIncomingDirection(){
        if (isDead) {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, initial_x_coordinate, initial_y_coordinate, manager, this);
        } else if (isFleeing) {
            if (x % gp.block_size == 0 && y % gp.block_size == 0 && manager.NodeMap[y_coordinate][x_coordinate] != null) {
                String temporary;
                ArrayList<String> lst = new ArrayList<String>();
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
            if (player.direction == "up") {
                try {
                    incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate, player.y_coordinate - 4, manager, this);
                } catch (Exception e) {
                    for (int i = player.y_coordinate; i >= 0; i--) {
                        if (!manager.collisionMap[i][player.x_coordinate]) {
                            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate, i, manager, this);
                            break;
                        }
                    }
                }
            }
            if (player.direction == "left") {
                try {
                    incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate - 4, player.y_coordinate, manager, this);
                } catch (Exception e) {
                    for (int i = player.x_coordinate; i >= 0; i--) {
                        if (!manager.collisionMap[player.y_coordinate][i]) {
                            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, i, player.y_coordinate, manager, this);
                            break;
                        }
                    }
                }
            }
            if (player.direction == "down") {
                try {
                    incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate, player.y_coordinate + 4, manager, this);
                } catch (Exception e) {
                    for (int i = player.y_coordinate; i <= gp.maxScreenRow - 1; i++) {
                        if (!manager.collisionMap[i][player.x_coordinate]) {
                            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate, i, manager, this);
                            break;
                        }
                    }
                }
            }
            if (player.direction == "right") {
                try {
                    incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, player.x_coordinate + 4, player.y_coordinate, manager, this);
                } catch (Exception e) {
                    for (int i = player.x_coordinate; i <= gp.maxScreenCol; i++) {
                        if (!manager.collisionMap[player.y_coordinate][i]) {
                            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, i, player.y_coordinate, manager, this);
                            break;
                        }
                    }
                }
            }
        } else {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, 1, 1,manager, this);
        }
    }
    public void getGhostImage() {
        try {
            deaddown = ImageIO.read(new File("resources/ghost/Pinky/deaddown.png"));
            deadleft = ImageIO.read(new File("resources/ghost/Pinky/deadleft.png"));
            deadright = ImageIO.read(new File("resources/ghost/Pinky/deadright.png"));
            deadup = ImageIO.read(new File("resources/ghost/Pinky/deadup.png"));
            up1 = ImageIO.read(new File("resources/ghost/Pinky/u1.png"));
            up2 = ImageIO.read(new File("resources/ghost/Pinky/u2.png"));
            right1 = ImageIO.read(new File("resources/ghost/Pinky/r1.png"));
            right2 = ImageIO.read(new File("resources/ghost/Pinky/r2.png"));
            left1 = ImageIO.read(new File("resources/ghost/Pinky/l1.png"));
            left2 = ImageIO.read(new File("resources/ghost/Pinky/l2.png"));
            down1 = ImageIO.read(new File("resources/ghost/Pinky/d1.png"));
            down2 = ImageIO.read(new File("resources/ghost/Pinky/d2.png"));
            fleeing1 = ImageIO.read(new File("resources/ghost/Pinky/FleeingBlue1.png"));
            fleeing2 = ImageIO.read(new File("resources/ghost/Pinky/FleeingBlue2.png"));
            fleeing3 = ImageIO.read(new File("resources/ghost/Pinky/FleeingWhite1.png"));
            fleeing4 = ImageIO.read(new File("resources/ghost/Pinky/FleeingWhite2.png"));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
