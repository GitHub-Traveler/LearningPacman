package Core;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Collections;
import java.lang.Comparable;

class PlayerInfo implements Comparable<PlayerInfo>{
    String name;
    int score;

    public PlayerInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(PlayerInfo playerInfo) {
        return Integer.compare(this.score, playerInfo.score);
    }

    @Override
    public String toString() {
        return this.name + ": " + this.score + "g";
    }
}
public class Leaderboard {
    String url;
    PriorityQueue<PlayerInfo> leaderboard;
    public Leaderboard(String url) {
        this.url = url;
        try {
            InputStream is = new FileInputStream(url);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            leaderboard = new PriorityQueue<>(Collections.reverseOrder());

            String f;
            while ((f = br.readLine()) != null) {
                String[] info = f.split(": ");
                leaderboard.add(new PlayerInfo(info[0], Integer.parseInt(info[1])));
            }
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public String[] getMaxInfo(){
        String[] info = new String[2];

        info[0] = leaderboard.peek().name;
        info[1] = Integer.toString(leaderboard.poll().score);
        return info;
    }

    public void addToLeaderboard(String name, int score) {
        try {
            FileWriter fr = new FileWriter("resources/leaderboard.txt", true);
            BufferedWriter br = new BufferedWriter(fr);
            br.newLine();
            br.write(name + ": " + score);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

