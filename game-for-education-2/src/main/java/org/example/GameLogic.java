package org.example;

import org.example.logic.*;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {
    private Ball ball;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;
    private final int ENEMY_STEPS = 5;


    public GameLogic(int ballSteps) {
        this.ball = null;
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
    }

    public void initialize() {

        ball = new Ball(20, 20, "bomb_green.jpg");

        Enemy enemy1 = new Enemy(350,350, "bomb.jpg",100);
        Enemy enemy2 = new Enemy(150,250, "bomb.jpg",100);
        enemies.add(enemy1);
        enemies.add(enemy2);

        Wall wall1 = new Wall(250, 30, 250, 130, Color.BLACK);
        Wall wall2 = new Wall(100, 50, 150, 50, Color.BLACK);
        walls.add(wall1);
        walls.add(wall2);
    }

    public void update() {
        //ball.move(2, Direction.RIGHT);
        for (Enemy enemy: enemies) {
            int differenceX = Math.abs(ball.getCoord().x - enemy.getCoord().x);
            int differenceY = Math.abs(ball.getCoord().y - enemy.getCoord().y);

            if (differenceX > differenceY) {
                // Direction LEFT, RIGHT
                if (ball.getCoord().x - enemy.getCoord().x > 0) {
                    // Direction RIGHT
                    EnemyMove(Direction.RIGHT);
                } else {
                    // Direction LEFT
                    EnemyMove(Direction.LEFT);
                }
            } else {
                // Direction UP, DOWN
                if (ball.getCoord().y - enemy.getCoord().y > 0)  {
                    // Direction DOWN
                    EnemyMove(Direction.DOWN);
                } else {
                    // Direction UP
                    EnemyMove(Direction.UP);
                }
            }
        }

        /*for (Wall wall: walls) {
            if (ball.isCollided(wall.getRectangle())){
                wall.inactivate();
            }
        }*/
        /*for(Enemy enemy : enemies) {
            for (Wall wall : walls) {
                if (enemy.isCollided(wall.getRectangle())) {
                    wall.inactivate();
                }
            }
        }*/
    }

    public boolean predictCollision(int ballSteps, Direction direction) {
        Rectangle moveRectangle = new Rectangle();
        switch (direction) {
            case RIGHT -> {
                moveRectangle = new Rectangle(ball.getX() + ballSteps, ball.getY(), ball.getWidth(), ball.getHeight());
            }
            case LEFT -> {
                moveRectangle = new Rectangle(ball.getX() - ballSteps, ball.getY(), ball.getWidth(), ball.getHeight());
            }
            case UP -> {
                moveRectangle = new Rectangle(ball.getX(), ball.getY() - ballSteps, ball.getWidth(), ball.getHeight());
            }
            case DOWN -> {
                moveRectangle = new Rectangle(ball.getX(), ball.getY() + ballSteps, ball.getWidth(), ball.getHeight());
            }

        }
        for (Wall wall:walls) {
            if (moveRectangle.intersects(wall.getRectangle())){
                return true;
            }
        }
        return false;


    }
    public boolean predictCollisionEnemy(int ballSteps, Direction direction, Enemy enemy) {
        Rectangle moveRectangle = new Rectangle();
        switch (direction) {
            case RIGHT -> {
                moveRectangle = new Rectangle(enemy.getX() + ballSteps, enemy.getY(), enemy.getWidth(), enemy.getHeight());
            }
            case LEFT -> {
                moveRectangle = new Rectangle(enemy.getX() - ballSteps, enemy.getY(), enemy.getWidth(), enemy.getHeight());
            }
            case UP -> {
                moveRectangle = new Rectangle(enemy.getX(), enemy.getY() - ballSteps, enemy.getWidth(), enemy.getHeight());
            }
            case DOWN -> {
                moveRectangle = new Rectangle(enemy.getX(), enemy.getY() + ballSteps, enemy.getWidth(), enemy.getHeight());
            }
        }

        for (Wall wall : walls) {
            if (moveRectangle.intersects(wall.getRectangle())) {
                return true;
            }
        }
        return false;
    }


    public ArrayList<Enemy> getEnemy() {
        return enemies;
    }

    public Ball getBall() {
        return ball;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }
    private void EnemyMove(Direction direction) {
        for (Enemy enemy : enemies) {
            if (!predictCollisionEnemy(ENEMY_STEPS, direction, enemy)) {
                enemy.move(ENEMY_STEPS, direction);
            }
        }
    }
}
