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

public class EventHandlerMario implements KeyboardHandler {
    private Picture bob;
    private Picture mario;
    private Picture ball;
    private Picture background;
    private Picture backgroundMario;
    private static final int PADDING = 10;
    private boolean falling = false;
    private boolean launch = false;
    private Timer timer;
    private int verticalVelocity = 0;
    private List<Picture> lifes;
    private int remainingLife;
    private boolean rising = false;
    private Game game;

    public EventHandlerMario(Picture player1, Picture player2, List<Picture> lifes, Picture background, Picture backgroundMario, Game game) {
        this.mario = player1;
        this.bob = player2;
        this.lifes = lifes;
        remainingLife = lifes.size() - 1;
        this.background = background;
        this.backgroundMario = backgroundMario;
        this.game = game;
    }

    public void init() {
        Keyboard marioKb = new Keyboard(this);

        KeyboardEvent arrowRight = new KeyboardEvent();
        arrowRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        arrowRight.setKey(KeyboardEvent.KEY_RIGHT);
        marioKb.addEventListener(arrowRight);

        KeyboardEvent arrowLeft = new KeyboardEvent();
        arrowLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        arrowLeft.setKey(KeyboardEvent.KEY_LEFT);
        marioKb.addEventListener(arrowLeft);

        KeyboardEvent kJump = new KeyboardEvent();
        kJump.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        kJump.setKey(KeyboardEvent.KEY_UP);
        marioKb.addEventListener(kJump);

        KeyboardEvent kLand = new KeyboardEvent();
        kLand.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        kLand.setKey(KeyboardEvent.KEY_UP);
        marioKb.addEventListener(kLand);

        KeyboardEvent pShoot = new KeyboardEvent();
        pShoot.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pShoot.setKey(KeyboardEvent.KEY_P);
        marioKb.addEventListener(pShoot);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (game.getGameIsStarted()) {
                    if (mario.getX() > background.getMaxX() - (background.getMaxX() / 2) + 75) {
                        mario.translate(-50, 0);
                    }
                }
                break;
            case KeyboardEvent.KEY_RIGHT:
                if (game.getGameIsStarted()) {
                    if (mario.getMaxX() < background.getMaxX() - PADDING - 30) {
                        mario.translate(50, 0);
                    }
                }
                break;
            case KeyboardEvent.KEY_UP:
                if (game.getGameIsStarted()) {
                    if (!falling) {
                        slowRiseMario();
                    }
                }
                break;
            case KeyboardEvent.KEY_P: {
                if (!launch && !isDead() && game.getGameIsStarted()) {
                    launch = true;
                    Sound.carolShotSound.play();
                    ball = Elements.getDisney();
                    ball.grow(-20, -10);
                    ball.draw();

                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (ball.getMaxX() < PADDING) {
                                ball.delete();
                                launch = false;
                                timer.stop();
                            } else if ((ball.getMaxX() > bob.getX() && ball.getX() < bob.getMaxX()) &&
                                    (ball.getMaxY() > bob.getY() && ball.getY() < bob.getMaxY())) {
                                ball.delete();
                                lifes.get(remainingLife).delete();
                                remainingLife--;
                                isDead();
                                launch = false;
                                timer.stop();
                            } else {
                                ball.translate(-10, 0);
                            }
                        }
                    });
                    timer.start();
                }
            }
            break;
        }
    }

    public boolean isDead() {
        if (remainingLife > -1) {
            return false;
        }
        backgroundMario.draw();
        Sound.carolWinnerSound.play();
        bob.delete();
        mario.delete();
        ball.delete();
        return true;
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        /*switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_UP:
                break;
        }*/
    }

    public void slowFallMario() {
        falling = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (falling) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mario.getMaxY() <= 670) {
                    mario.translate(0, verticalVelocity);
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

    private void slowRiseMario() {
        rising = true;
        Thread fallThread = new Thread(() -> {
            verticalVelocity = 0;
            while (rising) {
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mario.getY() > 0) {
                    verticalVelocity++;
                    mario.translate(0, -verticalVelocity);
                    if (mario.getY() < 0) {
                        slowFallMario();
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
