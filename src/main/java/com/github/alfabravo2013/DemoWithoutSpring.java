package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Cubs;
import com.github.alfabravo2013.entities.RedSox;

public class DemoWithoutSpring {

    public static void main(String[] args) {
        var redSox = new RedSox();
        var cubs = new Cubs();
        var game = new BaseballGame(redSox, cubs);

        System.out.println(game.playGame());
    }
}
