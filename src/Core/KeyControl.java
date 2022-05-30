package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControl implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed, escapePressed, enterPressed;
    public String curr_key = null;
    public GamePanel gp;
    public KeyControl(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
        }
        if (gp.gameState == gp.titleState) {
            if (gp.UIstate == gp.UIbegin) {
                if (code == KeyEvent.VK_UP) {
                    gp.ui.command_num--;
                    if (gp.ui.command_num < 0) {
                        gp.ui.command_num = 2;
                    }
                }
                if (code == KeyEvent.VK_DOWN) {
                    gp.ui.command_num++;
                    if (gp.ui.command_num > 2) {
                        gp.ui.command_num = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    gp.UIstate = switch (gp.ui.command_num) {
                        case 0 -> gp.UIdifficulty;
                        case 1 -> gp.UIleaderboard;
                        case 2 -> gp.UIdifficulty;
                        default -> throw new IllegalStateException("Unexpected value: " + gp.ui.command_num);
                    };
                    if (gp.ui.command_num == 2) {
                        System.exit(0);
                    }
                }
            }
            else if (gp.UIstate == gp.UIdifficulty) {
                if (code == KeyEvent.VK_UP) {
                    gp.ui.command_num--;
                    if (gp.ui.command_num < 0) {
                        gp.ui.command_num = 3;
                    }
                }
                if (code == KeyEvent.VK_DOWN) {
                    gp.ui.command_num++;
                    if (gp.ui.command_num > 3) {
                        gp.ui.command_num = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.command_num == 0) {
                        gp.initiateGame("easy",1);
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.command_num == 1) {
                        gp.initiateGame("normal",1);
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.command_num == 2) {
                        gp.initiateGame("hard",1);
                        gp.gameState = gp.playState;
                    }
                    if (gp.ui.command_num == 3) {
                        gp.UIstate = gp.UIbegin;
                        gp.ui.command_num = 0;
                    }
                }
            } else if (gp.UIstate == gp.UIleaderboard) {
                if (code == KeyEvent.VK_ENTER) {
                    gp.UIstate = gp.UIbegin;
                    gp.ui.command_num = 0;
                }
            }
        }
        if (gp.gameState == gp.endgameState) {
            char chr = e.getKeyChar();
            if (code == KeyEvent.VK_ENTER) {
                gp.ui.leaderboard.addToLeaderboard(gp.player.player_name, gp.player.score);
                gp.gameState = gp.titleState;
                gp.UIstate = gp.UIbegin;
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (gp.player.player_name.length() > 0) {
                    gp.player.player_name = gp.player.player_name.substring(0, gp.player.player_name.length() - 1);
                }
            } else if (Character.isLetterOrDigit(chr) || Character.isSpaceChar(chr)){
                gp.player.player_name += e.getKeyChar();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
