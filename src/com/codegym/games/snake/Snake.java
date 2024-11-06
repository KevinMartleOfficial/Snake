package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

import static com.codegym.games.snake.SnakeGame.HEIGHT;
import static com.codegym.games.snake.SnakeGame.WIDTH;

public class Snake extends GameObject {
    private List<GameObject>snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "⚫";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        super(x, y);
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2 , y));
    }

    public void setDirection(Direction directionInput) {
        boolean xDirection = snakeParts.get(0).x == snakeParts.get(1).x;
        boolean yDirection = snakeParts.get(0).y == snakeParts.get(1).y;
        if( ((directionInput == Direction.RIGHT || xDirection ) && direction == Direction.LEFT )
        ||  ((directionInput == Direction.LEFT || xDirection) && direction == Direction.RIGHT )
        ||  ((directionInput == Direction.UP || yDirection)  && direction == Direction.DOWN )
        ||  ((directionInput == Direction.DOWN || yDirection) && direction == Direction.UP  )){
        }
        else{
        direction = directionInput;
        }
    }


    public void draw(Game game) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            }
    }

    public boolean checkCollision(GameObject gameObject){
        boolean collisie = false;
        for (GameObject part : snakeParts){
            if( part.x == gameObject.x && part.y == gameObject.y){
                collisie = true;
                break;
            }
        }return collisie;
    }

    public void move(Apple apple, Bomb bomb){
        GameObject newSnakeHead = createNewHead();
        if(newSnakeHead.x >= WIDTH  || newSnakeHead.x < 0 || newSnakeHead.y < 0 || newSnakeHead.y >= HEIGHT){
            isAlive = false;
            return;
        }
        if(checkCollision(newSnakeHead)){
            isAlive = false;
        }
        else {
            snakeParts.add(0, newSnakeHead);
            if (apple.x == newSnakeHead.x && apple.y == newSnakeHead.y) {
                apple.isAlive = false;
            } else {
                removeTail();
            }
            if(bomb.x == newSnakeHead.x && bomb.y == newSnakeHead.y){
                bomb.isAlive = false;
                    isAlive = false;
            }

        }
    }

    public int getLength(){
        return snakeParts.size();
    }





    public GameObject createNewHead(){
        GameObject snakeHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y);
        switch (direction){
            case LEFT :
                snakeHead = new GameObject(snakeHead.x -1, snakeHead.y);
                break;
            case RIGHT:
                snakeHead = new GameObject(snakeHead.x +1, snakeHead.y);
                break;
            case UP:
                snakeHead = new GameObject(snakeHead.x, snakeHead.y - 1);
                break;
            case DOWN:
                snakeHead = new GameObject(snakeHead.x, snakeHead.y + 1);
                break;

        }
        return snakeHead;


    }

    public void removeTail(){
        snakeParts.remove((snakeParts.size())-1);
    }


}
