package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class Apple extends GameObject implements CheckCollisionItems{
    private static final String APPLE_SIGN = "\uD83C\uDF4E";
    public boolean isAlive = true;


    public Apple(int x, int y) {
        super(x, y);
    }



    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.RED, 75);
    }


    @Override
    public boolean checkCollisionItems(GameObject object) {
        return (x == object.x && y == object.y);
    }
}
