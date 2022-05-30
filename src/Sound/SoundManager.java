package Sound;

import Core.GamePanel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class SoundManager {
    GamePanel gp;
    public LinkedList<Sound> soundlist = new LinkedList<Sound>();
    public SoundManager(GamePanel gp, String path) {
        this.gp = gp;
        setSoundlist(path);
    }

    public void setSoundlist(String path) {
        try {
            InputStream soundpath = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(soundpath));
            String str;
            while ((str = br.readLine()) != null) {
                soundlist.add(new Sound(str));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    public void playSound(int index) {
        soundlist.get(index).restart();
    }
    public void continueSound(int index) {
        soundlist.get(index).cont();
    }
    public void stopSound(int index) {
        soundlist.get(index).stop();
    }
    public boolean isStopped(int index) { return soundlist.get(index).isStopped(); }
    public void stopAllSound() {
        for (Sound i:soundlist) {
            i.stop();
        }
    }
//    public void resumeAllSound() {
//        for (Sound i:soundlist) {
//            i.resume();
//        }
//    }
//    public void pauseAllSound() {
//        for (Sound i:soundlist) {
//            i.pause();
//        }
//    }
//    public void resumeAllSound() {
//        for (Sound i:soundlist) {
//            i.resume();
//        }
//    }
}
