package com.codegym.games.snake;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

public class Bird extends GameObject implements CheckCollisionItems{
    private static final String BIRD = "\ud83e\udd85";
    public boolean isAlive = true;

    public Bird(int x, int y) {
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, BIRD, Color.FIREBRICK, 75);
    }

    @Override
    public boolean checkCollisionItems(GameObject object) {
        if(object == null){

        }
        return (x == object.x && y == object.y);
    }
}
