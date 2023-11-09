package org.codeforall.ooptimus;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import java.util.ArrayList;
import java.util.List;

public class Elements extends Picture {
    private static final int PADDING = 10;
    public static Picture startBackground  = new Picture(PADDING, PADDING, "start.png");
    public static Picture background = new Picture(PADDING, PADDING, "background4.jpg");
    public static Picture backgroundBob = new Picture(PADDING, PADDING, "gustavo_winner.png");
    public static Picture backgroundMario = new Picture(PADDING, PADDING, "carol_winner.png");
    public static Picture bob = new Picture(170, 400, "Player1Normal.png");
    public static Picture mario = new Picture(1300, 400, "Player2 Background Removed.png");
    public static List<Picture> lifeBob = new ArrayList<>();
    public static List<Picture> lifeMario = new ArrayList<>();


    public static Picture getHammer() {
        return new Picture(Elements.bob.getX(), Elements.bob.getY(), "desgracados.png");
    }

    public static Picture getDisney() {
        return new Picture(Elements.mario.getX(), Elements.mario.getY(), "disney.png");
    }

    public void getBackground() {
        background.draw();
    }

    public static Picture getStartBackground() {
        return startBackground;
    }

    public void getBob() {
        bob.grow(-10,-20);
        bob.draw();
    }

    public void getMario() {
        mario.grow(-10,-20);
        mario.draw();
    }

    public void getLife() {
        for (int x = 0; x < 4; x++) {
            lifeBob.add(new Picture(35 + 50 * x, 40, "lifeicon .png"));
            lifeBob.get(x).draw();
        }
        for (int x = 0; x < 4; x++) {
            lifeMario.add(new Picture(1500 - 50 * x, 40, "lifeicon .png"));
            lifeMario.get(x).draw();
        }


    }
}
