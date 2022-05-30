package Tiles;

import Core.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

public class TileManager {
    public Image icon;
    GamePanel gp;
    Tile[][] tiles;
    public int[][] MapData;
    public LinkedList<String> map_list;
    public boolean[][] collisionMap;
    public Node[][] NodeMap;
    public int level = 1;
    public TileManager(GamePanel gp, String MapList) {
        this.gp = gp;
        tiles = new Tile[10][];
        map_list = new LinkedList<>();
        level = 1;
        MapData = new int[gp.maxScreenRow][gp.maxScreenCol];
        collisionMap = new boolean[gp.maxScreenRow][gp.maxScreenCol];
        NodeMap = new Node[gp.maxScreenRow][gp.maxScreenCol];
        loadMapList(MapList);
        getTileImages();
        loadMap(map_list.get(0));
        setCollisionMap();
        loadNodes();
    }
    public void loadMapList(String map_list_url) {
        try {
            InputStream is = new FileInputStream(map_list_url);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str;
            while ((str = br.readLine()) != null) {
                map_list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeMap(int index) {
        loadMap(map_list.get(index - 1));
        setCollisionMap();
        loadNodes();
    }
//    public TileManager(TileManager manager) {
//        this.NodeMap = manager.NodeMap;
//    }


    public void refreshNodeMap() {
        for (int y = 1; y < gp.maxScreenRow - 1; y++) {
            for (int x = 1; x < gp.maxScreenCol - 1; x++) {
                if (NodeMap[y][x] != null) {
                    NodeMap[y][x].value = 99999;
                    NodeMap[y][x].visited = false;
                }
            }
        }
    }

    public void loadMap(String MapDirectory) {
        try {
            InputStream is = new FileInputStream(MapDirectory);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();
                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    MapData[row][col] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    row++;
                    col = 0;
                }

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getTileImages() {
        try {
            tiles[0] = new Tile[2];
            tiles[1] = new Tile[5];
            tiles[2] = new Tile[7];
            tiles[3] = new Tile[5];
            tiles[4] = new Tile[2];
            tiles[5] = new Tile[4];
            tiles[9] = new Tile[2];

            tiles[0][0] = new Tile();
            tiles[0][0].img = ImageIO.read(new File("resources/tiles/tilePoint.png"));
            tiles[0][0].collision = false;

            tiles[0][1] = new Tile();
            tiles[0][1].img = ImageIO.read(new File("resources/tiles/tilePowerUp.png"));
            tiles[0][1].collision = false;

            tiles[1][1] = new Tile();
            tiles[1][1].img = ImageIO.read(new File("resources/tiles/tile1_1.png"));
            tiles[1][1].collision = true;

            tiles[1][2] = new Tile();
            tiles[1][2].img = ImageIO.read(new File("resources/tiles/tile1_2.png"));
            tiles[1][2].collision = true;

            tiles[1][3] = new Tile();
            tiles[1][3].img = ImageIO.read(new File("resources/tiles/tile1_3.png"));
            tiles[1][3].collision = true;

            tiles[1][4] = new Tile();
            tiles[1][4].img = ImageIO.read(new File("resources/tiles/tile1_4.png"));
            tiles[1][4].collision = true;

            tiles[2][1] = new Tile();
            tiles[2][1].img = ImageIO.read(new File("resources/tiles/tile2_1.png"));
            tiles[2][1].collision = true;

            tiles[2][2] = new Tile();
            tiles[2][2].img = ImageIO.read(new File("resources/tiles/tile2_2.png"));
            tiles[2][2].collision = true;

            tiles[2][3] = new Tile();
            tiles[2][3].img = ImageIO.read(new File("resources/tiles/tile2_3.png"));
            tiles[2][3].collision = true;

            tiles[2][4] = new Tile();
            tiles[2][4].img = ImageIO.read(new File("resources/tiles/tile2_4.png"));
            tiles[2][4].collision = true;

            tiles[2][5] = new Tile();
            tiles[2][5].img = ImageIO.read(new File("resources/tiles/tile2_5.png"));
            tiles[2][5].collision = true;

            tiles[2][6] = new Tile();
            tiles[2][6].img = ImageIO.read(new File("resources/tiles/tile2_6.png"));
            tiles[2][6].collision = true;

            tiles[3][1] = new Tile();
            tiles[3][1].img = ImageIO.read(new File("resources/tiles/tile3_1.png"));
            tiles[3][1].collision = true;

            tiles[3][2] = new Tile();
            tiles[3][2].img = ImageIO.read(new File("resources/tiles/tile3_2.png"));
            tiles[3][2].collision = true;

            tiles[3][3] = new Tile();
            tiles[3][3].img = ImageIO.read(new File("resources/tiles/tile3_3.png"));
            tiles[3][3].collision = true;

            tiles[3][4] = new Tile();
            tiles[3][4].img = ImageIO.read(new File("resources/tiles/tile3_4.png"));
            tiles[3][4].collision = true;

            tiles[4][1] = new Tile();
            tiles[4][1].img = ImageIO.read(new File("resources/tiles/tile4.png"));
            tiles[4][1].collision = true;

            tiles[9][0] = new Tile();
            tiles[9][0].img = ImageIO.read(new File("resources/tiles/tile9_0.png"));
            tiles[9][0].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setCollisionMap() {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = MapData[row][col];
            int data2 = tileNum % 10;
            int data1 = (tileNum - data2) / 10;
            collisionMap[row][col] = false;
            if (tileNum != 99) {
                collisionMap[row][col] = tiles[data1][data2].collision;
            }
            col++;
            x += gp.block_size;
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.block_size;
            }
        }
    }
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = MapData[row][col];
            int data2 = tileNum % 10;
            int data1 = (tileNum - data2)/10;
            if (tileNum != 99) {
                g2.drawImage(tiles[data1][data2].img,x,y,gp.block_size,gp.block_size,null);
            }
            col++;
            x += gp.block_size;
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.block_size;
            }
        }
    }
    public void loadNodes() {
        for (int y = 1; y < gp.maxScreenRow-1; y++) {
            for (int x = 1; x < gp.maxScreenCol-1; x++) {
                if (!collisionMap[y][x] && !(collisionMap[y+1][x] && collisionMap[y-1][x]) &&
                        !(collisionMap[y][x-1] && collisionMap[y][x+1])) {
                    NodeMap[y][x] = new Node(x,y);
                }
            }
        }
        for (int y = 1; y < gp.maxScreenRow-1; y++) {
            for (int x = 1; x < gp.maxScreenCol-1; x++) {
                if (NodeMap[y][x] != null) {
                    for (int i = x-1; i > 0; i--) {
                        if (NodeMap[y][i] != null) {
                            NodeMap[y][x].updateLeft(i);
                            break;
                        } else if (collisionMap[y][i]) {
                            break;
                        }
                    }
                    for (int i = x+1; i < gp.maxScreenCol; i++) {
                        if (NodeMap[y][i] != null) {
                            NodeMap[y][x].updateRight(i);
                            break;
                        } else if (collisionMap[y][i]) {
                            break;
                        }
                    }
                    for (int i = y-1; i > 0; i--) {
                        if (NodeMap[i][x] != null) {
                            NodeMap[y][x].updateUp(i);
                            break;
                        } else if (collisionMap[i][x]) {
                            break;
                        }
                    }
                    for (int i = y+1; i < gp.maxScreenRow; i++) {
                        if (NodeMap[i][x] != null) {
                            NodeMap[y][x].updateDown(i);
                            break;
                        } else if (collisionMap[i][x]) {
                            break;
                        }
                    }
                }
            }
        }
    }
    public boolean isComplete() {
        boolean complete = true;
        for (int i = 0; i < gp.maxScreenRow; i++) {
            for (int j = 0; j < gp.maxScreenCol;j++) {
                if (MapData[i][j] == 00) {
                    complete = false;
                }
            }
        }
        return complete;
    }
}
