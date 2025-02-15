package com.codegym.games.snake;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.*;
import com.codegym.engine.cell.Game;

public class Bomb extends GameObject implements CheckCollisionItems{
    private static final String BOMB = "\uD83D\uDCA3";
    public boolean isAlive = true;


    public Bomb(int x, int y) {
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, BOMB, Color.DARKBLUE, 75);
    }

    @Override
    public boolean checkCollisionItems(GameObject object) {
        if(object != null){
    }
        return (x == object.x && y == object.y);
    }


}
