package org.codeforall.ooptimus;

public class Main {
    public static void main(String[] args) {
        Elements elements = new Elements();
        Game game = new Game();

        elements.getBackground();
        elements.getBob();
        elements.getLife();
        elements.getMario();

        EventHandlerBob eventHandlerBob = new EventHandlerBob(Elements.bob, Elements.mario,Elements.lifeMario, Elements.background, Elements.backgroundBob,game);
        eventHandlerBob.init();

        EventHandlerMario eventHandlerMario = new EventHandlerMario(Elements.mario,Elements.bob,Elements.lifeBob,Elements.background,Elements.backgroundMario,game);
        game.startMenu();
        eventHandlerMario.init();
    }
}