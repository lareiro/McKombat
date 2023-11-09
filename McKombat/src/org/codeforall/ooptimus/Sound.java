package org.codeforall.ooptimus;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    private final AudioClip clip;

    public static final Sound startSound = new Sound("/starting theme.wav");
    public static final Sound gustavoShotSound = new Sound("/gustavo shot sound.wav");
    public static final Sound carolShotSound = new Sound("/carol shot sound.wav");
    public static final Sound carolWinnerSound = new Sound("/carol winner sound.wav");
    public static final Sound gustavoWinnerSound = new Sound("/gustavo winner sound.wav");

    private Sound(String name) {
        clip = Applet.newAudioClip(Sound.class.getResource(name));
    }

    public void play() {
        clip.play();
    }
    public void loop() {
        clip.loop();
    }
    public void stop() {
        clip.stop();
    }
}
