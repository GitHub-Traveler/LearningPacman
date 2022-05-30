package Entities;

import java.awt.*;
interface Ghost {

    public void changeStatus();
    public void setDefaultValue(int x, int y);
    public void getGhostImage();
    public void update();
    public void draw(Graphics2D g2);

}
