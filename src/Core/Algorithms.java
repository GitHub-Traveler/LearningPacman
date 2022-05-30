package Core;

import Entities.Entity;
import Tiles.TileManager;

public class Algorithms {

    public static String PacmanDirection(int start_x, int start_y, int finish_x, int finish_y, TileManager manager, Entity entity) {
        int lowest_distance = 10000;
        int left_distance;
        int right_distance;
        int up_distance;
        int down_distance;
        String direction = "up";
        try {
            if (!manager.collisionMap[start_y][start_x - 1]) {
                left_distance = (start_x - 1 - finish_x) * (start_x - 1 - finish_x) + (start_y - finish_y) * (start_y - finish_y);
            } else {
                left_distance = 99999;
            }
            if (!manager.collisionMap[start_y][start_x + 1]) {
                right_distance = (start_x + 1 - finish_x) * (start_x + 1 - finish_x) + (start_y - finish_y) * (start_y - finish_y);
            } else {
                right_distance = 99999;
            }
            if (!manager.collisionMap[start_y - 1][start_x]) {
                up_distance = (start_x - finish_x) * (start_x - finish_x) + (start_y - 1 - finish_y) * (start_y - 1 - finish_y);
            } else {
                up_distance = 99999;
            }
            if (!manager.collisionMap[start_y + 1][start_x]) {
                down_distance = (start_x - finish_x) * (start_x - finish_x) + (start_y + 1 - finish_y) * (start_y + 1 - finish_y);
            } else {
                down_distance = 99999;
            }
            if (entity.direction == "up") {
                down_distance = 99999;
            } else if (entity.direction == "left") {
                right_distance = 99999;
            } else if (entity.direction == "down") {
                up_distance = 99999;
            } else if (entity.direction == "right") {
                left_distance = 99999;
            }
            if (up_distance < lowest_distance) {
                lowest_distance = up_distance;
            }
            if (left_distance < lowest_distance) {
                lowest_distance = left_distance;
            }
            if (down_distance < lowest_distance) {
                lowest_distance = down_distance;
            }
            if (right_distance < lowest_distance) {
                lowest_distance = right_distance;
            }
            if (lowest_distance == up_distance) {
                direction = "up";
            } else if (lowest_distance == left_distance) {
                direction = "left";
            } else if (lowest_distance == down_distance) {
                direction = "down";
            } else if (lowest_distance == right_distance) {
                direction = "right";
            }
            return direction;
        } catch (ArrayIndexOutOfBoundsException e) {
            direction = entity.direction;
            return direction;
        }
    }
}