package Entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x,y;
    public int speed;
    public int base_speed;
    public BufferedImage up1,up2,up3,up4,down1,down2,down3,down4,left1,left2,left3,left4,right1,right2,right3,right4;
    public String direction, incoming_direction;
    public int x_coordinate, y_coordinate;
    public int initial_x_coordinate, initial_y_coordinate;

    public int SpriteCounter = 0;
    public int SpriteNum = 1;

}
