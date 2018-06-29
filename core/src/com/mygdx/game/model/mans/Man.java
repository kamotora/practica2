package com.mygdx.game.model.mans;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.InputHandler;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.model.MyWorld;
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
    final static int SIZE_ON_MAP = 32;
    final static int MIN_SPEED = 2;
    final static int MIN_VISION = 100;

    /*
     * Место на карте
     */
    Vector2 position = new Vector2();
    /*
     * Направление движения
     */
    Vector2 velocity = new Vector2(0,-1);
    /*
     * Картинка человека
     */
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
     * Есть огнетушитель
     */
    boolean haveAntiFire = false;
    /*
     * Знает о пожаре
     */
    boolean isKnow = false;
    /*
     * жив, мертв, спасен
     */
    TypeMan type;
    /**
     * Кол-во потушенных огоньков
     */
    int countDeadFire = 0;
    /*
     * Скорость
     */
    static int speed = MIN_SPEED;
    /*
     * Границы
     */
    Rectangle bounds;

    public Man(Vector2 position){
        this.position = position;
        bounds = new Rectangle(position.x, position.y,SIZE_ON_MAP,SIZE_ON_MAP);
        type = TypeMan.LIVE;
    }

    public Vector2 getPosition() {
        return position;
    }
    /*
     * Обновление движения
     * Если мертв, стоит на месте
     */
    public void update(){
        if(health > 0) {
            if (InputHandler.isPressed())
                velocity = InputHandler.getMousePosition().sub(position.cpy()).nor();
            position.add(velocity.cpy().scl(speed));
            bounds.setPosition(position);
        }
    }

    /*
     * Рисуемся
     */
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(image,position.x, position.y, SIZE_ON_MAP,SIZE_ON_MAP);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    //Отрикошетить от стены
    public void setVelocity(Block block) {
        if (block.getTypePosition() == TypePosition.VERTICAL)
            velocity = getPosition().cpy().sub(new Vector2(block.getPosition().x, getPosition().y)).nor();
        else
            velocity = getPosition().cpy().sub(new Vector2(getPosition().x, block.getPosition().y)).nor();
    }

    /*
     * Уменьшаем здоровье
     */
    public void setHealth(TypeSign typeSign) {
        if(typeSign == TypeSign.Fire)
            health -= 0.1;
        if(typeSign == TypeSign.Smoke)
            health -= 0.05;
        System.out.println(health);
        if(health <= 0)
            type = TypeMan.DEAD;
    }

    //Если умерли
    public boolean isDead(){ return type == TypeMan.DEAD ? true: false; }

    public static int getSize() {
        return SIZE_ON_MAP - 10;
    }
    /*
     * Есть ли огнетушитель
     */
    public boolean isAntiFire() {
        System.out.println(haveAntiFire);
        return haveAntiFire;
    }
    /*
     * Получаем огнетушитель
     */
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
    /*
     * Спасён?
     */
    public boolean isSave() {
        return (type == TypeMan.SAVE) ? true : false;
    }
    /*
     * Узнал о пожаре
     */
    public void setKnow() {
        if(!isKnow)
            isKnow = true;
    }
    /*
    * Кол-во использований огнетушителя
    * не более волюмеАнтифайр раз для одного огнетушителя
    */
    public void setCountDeadFire() {
        countDeadFire++;
        if(countDeadFire % MyWorld.VOLUME_ANTIFIRE == 0)
            haveAntiFire = false;
    }

    //меняем текстуру,если сдох
    public void dead() {
        type = TypeMan.DEAD;
        image = new Texture("dead.png");
        velocity.set(0,0);
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }

    public Vector2 getCenterPosition(){
        return new Vector2(position.x+SIZE_ON_MAP/2,position.y+SIZE_ON_MAP/2);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /*
     * Спасаем
     */
    public void save() {
        type = TypeMan.SAVE;

        //Добавить изображение
        /*
        image = new Texture("save.png");
        velocity.set(0,0);
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
        */
    }
}