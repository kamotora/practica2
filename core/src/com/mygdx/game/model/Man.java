package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.InputHandler;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.signs.TypeSign;
import com.mygdx.game.renderer.WorldRenderer;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import com.mygdx.game.model.blocks.TypeBlock;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

public class Man {

    public enum State {
        NONE, WALKING, DEAD
    }

    final static int SIZE_ON_MAP = 32;
    final static int MIN_SPEED = 2;
    final static int MIN_VISION = 50;
    final static Random rnd = new Random(System.currentTimeMillis());
    /*
     * Место на карте
     */
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2(0,-1);
    Texture image = new Texture("Man.png");
    /*
     * Остаток здоровья
     */
    int health = 100;
    /*
     * Радиус видимости
     */
    int vision = MIN_VISION;
    /*
     * Скорость перемещения
     */
    boolean haveAntiFire = false;
    boolean isKnow = false;
    static int speed = MIN_SPEED;
    public Man(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(){
        if(health > 0) {
            if (InputHandler.isPressed())
                velocity = InputHandler.getMousePosition().sub(position.cpy()).nor();
            position.add(velocity.cpy().scl(speed));
        }
    }

    public void checkBlock(Block block) {
        if ((Math.abs(position.x - block.getPosition().x) < SIZE_ON_MAP) && (block.getTypePosition() == TypePosition.VERTICAL)){
            velocity = this.position.cpy().sub(new Vector2(block.getPosition().x, position.y)).nor();
            System.out.println("ecnm " + position);
        }
        if((Math.abs(position.y - block.getPosition().y) < SIZE_ON_MAP) && (block.getTypePosition() == TypePosition.HORIZONTAL)){
            velocity = this.position.cpy().sub(new Vector2(position.x,block.getPosition().y)).nor();
            System.out.println("ecnm " + position);
        }
    }
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(image,position.x, position.y, SIZE_ON_MAP,SIZE_ON_MAP);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setHealth(TypeSign typeSign) {
        if(typeSign == TypeSign.Fire)
            health -= 0.2;
        if(typeSign == TypeSign.Smoke)
            health -= 0.1;
        System.out.println(health);
    }
    public boolean isDead(){
        if(health <= 0)
            return true;
        return false;
    }
    public static int getSize() {
        return SIZE_ON_MAP;
    }

    public boolean isAntiFire() {
        return haveAntiFire;
    }

    public void setAntiFire() {
        if(!haveAntiFire)
            haveAntiFire = true;
    }

    public int getVision() {
        return vision;
    }

    public boolean isKnow() {
        return isKnow;
    }

    public void setKnow() {
        if(!isKnow)
            isKnow = true;
    }

    public void dead() {
        image = new Texture("dead.png");
        velocity.set(0,0);
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }
}