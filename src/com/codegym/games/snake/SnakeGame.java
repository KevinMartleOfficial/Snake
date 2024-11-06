package com.codegym.games.snake;

import com.codegym.engine.cell.*;


public class SnakeGame extends Game{
    public final static int WIDTH = 15;
    public final static int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private Bomb bomb;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    
    
    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int a){
        snake.move(apple, bomb);
        if(!apple.isAlive){
            setTurnTimer(turnDelay -= 10);
            setScore(score += 5);
            createNewApple();
        }
        if(!bomb.isAlive){
            createNewBomb();
        }
        if(!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key){
            switch (key) {
                case LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case UP:
                    snake.setDirection(Direction.UP);
                    break;
                case DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case SPACE:
                    createGame();
                    break;

            }
    }

    private void createGame(){
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        createNewApple();
        createNewBomb();
        isGameStopped = false;
        drawScene();

    };

    private void createNewBomb(){

        bomb = new Bomb(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while(snake.checkCollision(bomb) && (bomb.x == apple.x && bomb.y == apple.y)){
            bomb = new Bomb(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
    }




    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while(snake.checkCollision(apple)){
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        };

    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.TEAL, "Game over! Je score was " +score, Color.CORNSILK, 50);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKRED, "You win!! Good job!", Color.SNOW, 60);
    }


        private void drawScene(){
        for (int x = 0; x < WIDTH; x++){
            if(x % 2 == 0) {
                for (int y = 0; y < HEIGHT; y +=2) {
                    setCellValueEx(x, y, Color.WHEAT, "");
                }
                for(int y = 1 ; y < HEIGHT; y += 2){
                    setCellValueEx(x, y, Color.WHITE, "");
                }
            }
                else{
                    for(int y = 0; y < HEIGHT; y += 2){
                    setCellValueEx(x, y, Color.WHITE, "");
                    }
                    for (int y = 1; y < HEIGHT; y += 2){
                        setCellValueEx(x, y, Color.WHEAT, "");
                    }
                }

            }
        snake.draw(this);
        apple.draw(this);
        bomb.draw(this);
        }


    }
