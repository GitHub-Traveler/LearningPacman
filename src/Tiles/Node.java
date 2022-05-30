package Tiles;

import javax.swing.text.StyledEditorKit;
import java.nio.file.StandardOpenOption;

public class Node {
    public int x;
    public int y;
    public int value;
    public boolean visited = false;
    public int left_x = -1;
    public int left_y = -1;
    public int right_x = -1;
    public int right_y = -1;
    public int down_x = -1;
    public int down_y = -1;
    public int up_x = -1;
    public int up_y = -1;
    public String direction = null;
    Node(int x, int y) {
        this.x = x;
        this.y = y;
        left_y = y;
        right_y = y;
        up_x = x;
        down_x = x;
        this.value = 99999;
    }
    public void updateLeft(int x) {
        left_x = x;
    }
    public void updateRight(int x) {
        right_x = x;
    }
    public void updateUp(int y) {
        up_y = y;
    }

    public void updateDown(int y) {
        down_y = y;
    }
}
