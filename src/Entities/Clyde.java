package Entities;

import Tiles.TileManager;
import Core.Algorithms;
import Core.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Clyde extends Blinky{
    public Clyde(GamePanel gp, TileManager manager, Player player, int initial_x, int initial_y, String difficulty) {
        super(gp, manager, player, initial_x, initial_y, difficulty);
    }
    @Override
    public void updateIncomingDirection() {
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
            if (Math.pow(player.x_coordinate - x_coordinate, 2) + (Math.pow(player.y_coordinate - y_coordinate, 2)) >= 64) {
                incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, player.x_coordinate, player.y_coordinate, manager, this);
            } else {
                incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, 1, gp.maxScreenRow - 2,manager, this);
            }
        } else {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate, y_coordinate, 1, gp.maxScreenRow - 2, manager, this);
        }
    }
    @Override
    public void getGhostImage() {
        try {
            deaddown = ImageIO.read(new File("resources/ghost/Clyde/deaddown.png"));
            deadleft = ImageIO.read(new File("resources/ghost/Clyde/deadleft.png"));
            deadright = ImageIO.read(new File("resources/ghost/Clyde/deadright.png"));
            deadup = ImageIO.read(new File("resources/ghost/Clyde/deadup.png"));
            up1 = ImageIO.read(new File("resources/ghost/Clyde/u1.png"));
            up2 = ImageIO.read(new File("resources/ghost/Clyde/u2.png"));
            right1 = ImageIO.read(new File("resources/ghost/Clyde/r1.png"));
            right2 = ImageIO.read(new File("resources/ghost/Clyde/r2.png"));
            left1 = ImageIO.read(new File("resources/ghost/Clyde/l1.png"));
            left2 = ImageIO.read(new File("resources/ghost/Clyde/l2.png"));
            down1 = ImageIO.read(new File("resources/ghost/Clyde/d1.png"));
            down2 = ImageIO.read(new File("resources/ghost/Clyde/d2.png"));
            fleeing1 = ImageIO.read(new File("resources/ghost/Clyde/FleeingBlue1.png"));
            fleeing2 = ImageIO.read(new File("resources/ghost/Clyde/FleeingBlue2.png"));
            fleeing3 = ImageIO.read(new File("resources/ghost/Clyde/FleeingWhite1.png"));
            fleeing4 = ImageIO.read(new File("resources/ghost/Clyde/FleeingWhite2.png"));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    } 
}
