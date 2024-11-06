package com.codegym.games.snake;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

public class Bird extends GameObject{
    private static final String BIRD = "\ud83e\udd85";
    public boolean isAlive;

    public Bird(int x, int y) {
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, BIRD, Color.FIREBRICK, 75);

    }
}
