package org.codeforall.ooptimus;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Game {
    Game() {
        this.elements = new Elements();
        this.startBackground = elements.getStartBackground();
        this.eventHandlerBob = new EventHandlerBob(Elements.bob, Elements.mario, Elements.lifeMario, Elements.background, Elements.backgroundBob, this);
        this.eventHandlerMario = new EventHandlerMario(Elements.mario, Elements.bob, Elements.lifeBob, Elements.background, Elements.backgroundMario, this);
        this.gameIsStarted = false;
    }

    private Elements elements;
    private Picture startBackground;
    private EventHandlerBob eventHandlerBob;
    private EventHandlerMario eventHandlerMario;
    private boolean gameIsStarted;


    public void init() {
        elements.getBackground();
        elements.getBob();
        elements.getMario();
        elements.getLife();
        eventHandlerBob.init();
        eventHandlerMario.init();
    }

    public void startMenu() {
        Sound.startSound.play();
        startBackground.draw();
    }

    public void closeMenu() {
        startBackground.delete();
        gameIsStarted = true;
    }

    public boolean getGameIsStarted() {
        return gameIsStarted;
    }
}
