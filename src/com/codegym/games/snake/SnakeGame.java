package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import static javax.swing.JOptionPane.YES_NO_OPTION;


public class SnakeGame extends Game{
    public final static int WIDTH = 15;
    public final static int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private Bomb bomb;
    private Bird bird;
    private int turnDelay;
    private boolean isGameStopped;
    //private static final int GOAL = 28;
    private int score;
    private int goal;
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();
    private int messageSnelheid;


    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }



    @Override
    public void onTurn(int a){
        snake.move(apple, bomb, bird);
        if(!apple.isAlive){
            if(!(messageSnelheid == JOptionPane.YES_OPTION)){
                setTurnTimer(turnDelay);
            }
            else{
                setTurnTimer(turnDelay -= 10);
            }
            System.out.println("Vertraging is " +turnDelay);

            setScore(score += 5);
            createNewApple();
        }
        if(!bird.isAlive){
            createNewBird();
            setScore(score -= 10);
        }
        if(!snake.isAlive){
            gameOver();
        }

        if(snake.getLength() > goal){
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
                    if(snake.isAlive){
                        setTurnTimer(0);
                    }
                    createGame();
                    break;
                case ESCAPE:
                    gameOver();

            }
    }

    private void createGame(){
        turnDelay = 300;
        System.out.println("Vertraging is " +turnDelay);
        String lengteSlang= JOptionPane.showInputDialog("Geef max lengte van je slang in");
        goal = Integer.parseInt(lengteSlang);
        messageSnelheid = JOptionPane.showConfirmDialog(null, "Wilt u dat het spelletje sneller gaat?");
        score = 0;
        setScore(score);
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        placeBombsOnTimer();
        placeBirdTimer();
        isGameStopped = false;
        drawScene();
    };

    public void placeBombsOnTimer(){
        createNewBomb();
        TimerTask repeatBombs = new TimerTask() {
            @Override
            public void run() {
                createNewBomb();
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(repeatBombs, 4000, 4000);

    }

    public void placeBirdTimer(){
        createNewBird();
            TimerTask repeatItem = new TimerTask() {
                @Override
                public void run() {
                    createNewBird();
                }
            };
        timer2.cancel();
        timer2 = new Timer();
        timer2.scheduleAtFixedRate(repeatItem, 0,getRandomNumber(4000, 8500));

    }

    private boolean checkCollisionItems(GameObject object1, GameObject object2) {
        if( object1 == null || object2 ==null){
            return false;
        }
        return (object1.x == object2.x && object1.y == object2.y);
    }



    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while(snake.checkCollision(apple)){
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        };
    }

    private void createNewBomb() {
        bomb = new Bomb(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(bomb) || (checkCollisionItems(apple, bomb)) || checkCollisionItems(bird, bomb)){
            bomb = new Bomb(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }

    }

    private void createNewBird(){
        bird = new Bird(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
         while (snake.checkCollision(bird) || ((checkCollisionItems(apple, bird)) || checkCollisionItems(bomb, bird))){
             bird = new Bird(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
         }

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
        bird.draw(this);
        }
    }
