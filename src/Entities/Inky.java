package Entities;

import Tiles.TileManager;
import Core.Algorithms;
import Core.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Inky extends Blinky{
    Blinky blinky;
    int target_x, target_y;
    public Inky(GamePanel gp, TileManager manager, Player player, int initial_x, int initial_y, Blinky blinky, String difficulty) {
        super(gp, manager, player, initial_x, initial_y,difficulty);
        this.blinky = blinky;
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
            int pacman_x = switch (player.direction) {
                case "up" -> player.x_coordinate;
                case "left" -> player.x_coordinate - 2;
                case "down" -> player.x_coordinate;
                case "right" -> player.x_coordinate + 2;
                default -> player.y_coordinate;
            };
            int pacman_y = switch (player.direction) {
                case "up" -> player.y_coordinate - 2;
                case "left" -> player.y_coordinate;
                case "down" -> player.x_coordinate + 2;
                case "right" -> player.x_coordinate;
                default -> player.y_coordinate;
            };
            int incoming_target_x = (pacman_x * 2 - blinky.x_coordinate);
            int incoming_target_y = (pacman_y * 2 - blinky.y_coordinate);
            try {
                if (!manager.collisionMap[incoming_target_y][incoming_target_x]) {
                    target_x = incoming_target_x;
                    target_y = incoming_target_y;
                }
            } catch (Exception e) {

            }
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, target_x, target_y,manager, this);
        } else {
            incoming_direction = Algorithms.PacmanDirection(x_coordinate,y_coordinate, gp.maxScreenCol - 2, gp.maxScreenRow - 2,manager, this);
        }
    }
    public void getGhostImage() {
        try {
            deaddown = ImageIO.read(new File("resources/ghost/Inky/deaddown.png"));
            deadleft = ImageIO.read(new File("resources/ghost/Inky/deadleft.png"));
            deadright = ImageIO.read(new File("resources/ghost/Inky/deadright.png"));
            deadup = ImageIO.read(new File("resources/ghost/Inky/deadup.png"));
            up1 = ImageIO.read(new File("resources/ghost/Inky/u1.png"));
            up2 = ImageIO.read(new File("resources/ghost/Inky/u2.png"));
            right1 = ImageIO.read(new File("resources/ghost/Inky/r1.png"));
            right2 = ImageIO.read(new File("resources/ghost/Inky/r2.png"));
            left1 = ImageIO.read(new File("resources/ghost/Inky/l1.png"));
            left2 = ImageIO.read(new File("resources/ghost/Inky/l2.png"));
            down1 = ImageIO.read(new File("resources/ghost/Inky/d1.png"));
            down2 = ImageIO.read(new File("resources/ghost/Inky/d2.png"));
            fleeing1 = ImageIO.read(new File("resources/ghost/Inky/FleeingBlue1.png"));
            fleeing2 = ImageIO.read(new File("resources/ghost/Inky/FleeingBlue2.png"));
            fleeing3 = ImageIO.read(new File("resources/ghost/Inky/FleeingWhite1.png"));
            fleeing4 = ImageIO.read(new File("resources/ghost/Inky/FleeingWhite2.png"));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
