package org.codeforall.ooptimus;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventHandlerBob implements KeyboardHandler {
    private Picture bob;
    private Picture mario;
    private Picture hammer;
    private Picture background;
    private Picture backgroundBob;
    private static final int PADDING = 10;
    private boolean falling = false;
    private boolean throwing = false;
    private Timer timer;
    private int verticalVelocity = 0;
    private List<Picture> lifes;
    private int remainingLife;
    private boolean rising = false;
    private Game game;


    public EventHandlerBob(Picture player1, Picture player2, List<Picture> lifes, Picture background, Picture backgroundBob, Game game) {
        this.bob = player1;
        this.mario = player2;
        this.lifes = lifes;
        remainingLife = lifes.size() - 1;
        this.background = background;
        this.backgroundBob = backgroundBob;
        this.game = game;
    }

    public void init() {
        Keyboard bobKb = new Keyboard(this);

        KeyboardEvent dRight = new KeyboardEvent();
        dRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        dRight.setKey(KeyboardEvent.KEY_D);
        bobKb.addEventListener(dRight);

        KeyboardEvent aLeft = new KeyboardEvent();
        aLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        aLeft.setKey(KeyboardEvent.KEY_A);
        bobKb.addEventListener(aLeft);

        KeyboardEvent shiftJump = new KeyboardEvent();
        shiftJump.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        shiftJump.setKey(KeyboardEvent.KEY_W);
        bobKb.addEventListener(shiftJump);

        KeyboardEvent shiftLand = new KeyboardEvent();
        shiftLand.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        shiftLand.setKey(KeyboardEvent.KEY_W);
        bobKb.addEventListener(shiftLand);

        KeyboardEvent sShoot = new KeyboardEvent();
        sShoot.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        sShoot.setKey(KeyboardEvent.KEY_SHIFT);
        bobKb.addEventListener(sShoot);


        KeyboardEvent startgame = new KeyboardEvent();
        startgame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        startgame.setKey(KeyboardEvent.KEY_ENTER);
        bobKb.addEventListener(startgame);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_A:
                if (game.getGameIsStarted()) {
                    if (bob.getX() > background.getX() + PADDING + 30) {
                        bob.translate(-50, 0);
                    }
                }
                break;
            case KeyboardEvent.KEY_D:
                if (game.getGameIsStarted()) {
                    if (bob.getMaxX() < background.getMaxX() - (background.getMaxX() / 2) - 75) {
                        bob.translate(50, 0);
                    }
                }
                break;
            case KeyboardEvent.KEY_W:
                if (game.getGameIsStarted()) {
                    if (!falling) {
                        slowRiseBob();
                    }
                }
                break;
            case KeyboardEvent.KEY_SHIFT: {
                if (!throwing && !isDead() && game.getGameIsStarted()) {
                    throwing = true;
                    Sound.gustavoShotSound.play();
                    hammer = Elements.getHammer();
                    hammer.grow(-20, -10);
                    hammer.draw();

                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (hammer.getMaxX() > 1600) {
                                hammer.delete();
                                throwing = false;
                                timer.stop();
                            } else if ((hammer.getMaxX() > mario.getX() && hammer.getX() < mario.getMaxX()) &&
                                    (hammer.getMaxY() > mario.getY() && hammer.getY() < mario.getMaxY())) {
                                hammer.delete();
                                lifes.get(remainingLife).delete();
                                remainingLife--;
                                isDead();
                                throwing = false;
                                timer.stop();

                            } else {
                                hammer.translate(10, 0);
                            }
                        }
                    });
                    timer.start();
                }
            }
            break;
            case KeyboardEvent.KEY_ENTER:
                game.closeMenu();
                Sound.startSound.stop();
                break;
        }
    }

    public boolean isDead() {
        if (remainingLife > -1) {
            return false;
        }
        backgroundBob.draw();
        Sound.gustavoWinnerSound.play();
        bob.delete();
        mario.delete();
        hammer.delete();
        return true;
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
       /* switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_S:
                break;
        }*/
    }

    public void slowFallBob() {
        falling = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (falling) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (bob.getMaxY() <= 670) {
                    bob.translate(0, verticalVelocity);
                    verticalVelocity++;
                } else {
                    falling = false;
                    verticalVelocity = 0;
                    break;
                }
            }
        });
        fallThread.start();
    }

    private void slowRiseBob() {
        rising = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (rising) {
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (bob.getY() > 0) {
                    verticalVelocity++;
                    bob.translate(0, -verticalVelocity);
                    if (bob.getY() < 0) {
                        slowFallBob();
                        break;
                    }
                } else {
                    rising = false;
                    verticalVelocity = 0;
                    break;
                }
            }
        });
        fallThread.start();
    }
}
