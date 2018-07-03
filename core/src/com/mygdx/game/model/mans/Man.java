package com.mygdx.game.model.mans;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.hepler.TextureStorage;
import com.mygdx.game.model.world.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.signs.TypeSign;
import com.mygdx.game.renderer.WorldRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Man {
    private final static int SIZE_ON_MAP = 32;
    private final static int MIN_SPEED = 2;
    private final static int MIN_VISION = 120;

    /*
     * Место на карте
     */
    private Vector2 position;
    /*
     * Направление движения
     */
    private Vector2 velocity = new Vector2(0,-1);
    /*
     * Картинка человека
     */
    private Texture image = TextureStorage.getTexture("Man");
    /*
     * Остаток здоровья
     */
    private int health = 100;
    /*
     * Радиус видимости
     */
    private int vision = MIN_VISION;
    /*
     * Есть огнетушитель
     */
    private boolean haveAntiFire = false;
    /*
     * Знает о пожаре
     */
    private boolean isKnow = false;
    /*
     * жив, мертв, спасен
     */
    private TypeMan type;
    /**
     * Кол-во потушенных огоньков
     */
    private int countDeadFire = 0;
    /*
     * Скорость
     */
    private static int speed = MIN_SPEED;
    /*
     * Границы
     */
    private Rectangle bounds;

    static int saveX = 1000;
    static int saveY = 200;

    static Random rand = new Random(System.currentTimeMillis());



    public Man(Vector2 position){
        this.position = position;
        bounds = new Rectangle(position.x, position.y,SIZE_ON_MAP,SIZE_ON_MAP);
        type = TypeMan.LIVE;
        velocity.set(rand.nextFloat() % 1 - 0.5f, -1);
    }

    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(float x, float y) {
        position.set(x,y);
        bounds.setPosition(x,y);
    }
    /*
     * Обновление движения
     * Если мертв, стоит на месте
     */
    public void update(){
        if(health > 0) {
            //if (InputHandler.isPressed() && )
               // velocity = InputHandler.getMousePosition().sub(position.cpy()).nor();
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
        //Почему то не работает, но должно
        /*
        if (block.getTypePosition() == TypePosition.VERTICAL) {
            if(this.position.x > block.getPosition().x) {
                if (this.position.angle(block.getPosition()) > 90)
                    velocity.rotate90(-1);
                else
                    velocity.rotate90(1);
            }
            else{
                if (this.position.angle(block.getPosition()) > 90)
                    velocity.rotate90(1);
                else
                    velocity.rotate90(-1);
            }
        }
        if (block.getTypePosition() == TypePosition.HORIZONTAL) {
            if(this.position.y > block.getPosition().y) {
                if (this.position.angle(block.getPosition()) > 90)
                    velocity.rotate90(1);
                else
                    velocity.rotate90(-1);
            }
            else{
                if (this.position.angle(block.getPosition()) > 90)
                    velocity.rotate90(-1);
                else
                    velocity.rotate90(1);
            }
        }
        position.add(velocity.cpy().scl(speed));
        */
        //Работает, но не совсем правильно
        if (block.getTypePosition() == TypePosition.VERTICAL) {
            velocity = getPosition().cpy().sub(new Vector2(block.getPosition().x, getPosition().y+ rand.nextInt(20))).nor();
        }
        else
            velocity = getPosition().cpy().sub(new Vector2(getPosition().x + rand.nextInt(20), block.getPosition().y)).nor();
    }

    /*
     * Уменьшаем здоровье
     */
    public void setHealth(TypeSign typeSign) {
        if(typeSign == TypeSign.Fire)
            health -= 0.1;
        if(typeSign == TypeSign.Smoke)
            health -= 0.05;
        if(health <= 0)
            type = TypeMan.DEAD;
    }

    //Если умерли
    public boolean isDead(){ return type == TypeMan.DEAD; }

    public static int getSize() {
        return SIZE_ON_MAP - 10;
    }
    /*
     * Есть ли огнетушитель
     */
    public boolean isAntiFire() {
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
    public boolean isSave() { return type == TypeMan.SAVE; }
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
    public void kill() {
        type = TypeMan.DEAD;
        image = TextureStorage.getTexture("kill");
        velocity.set(0,0);
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }

    public Vector2 getCenterPosition(){
        return new Vector2(position.x+SIZE_ON_MAP/2,position.y+SIZE_ON_MAP/2);
    }

    public Rectangle getBounds() { return bounds; }

    /*
     * Спасаем
     */
    public void save() {
        if(type == TypeMan.SAVE)
            return;
        type = TypeMan.SAVE;
        //Добавить изображение
        image = TextureStorage.getTexture("saved");
        //Добавлять или нет?
        velocity.set(0,0);
        setPosition(saveX,saveY);
        saveX += SIZE_ON_MAP;
        if(saveX + SIZE_ON_MAP >= MyWorld.WIDTH){
            saveX = 1000;
            saveY += SIZE_ON_MAP;
        }
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();

    }
    public static Man fromString(String str){
        Man man = null;
        int posStart = str.indexOf("position=", 1) + 9;
        int posFinish = str.indexOf("),", posStart)+1;
        try{
            Vector2 vector = new Vector2();
            vector.fromString(str.substring(posStart,posFinish));
            man =  new Man(vector);
        }
        catch (Exception e){
            System.out.println("Man error " + e);
        }
        if(man == null)
            System.out.println("man null");
        return man;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Man{");
        sb.append("position=").append(position);
        sb.append('}');
        sb.append('\n');
        return sb.toString();
    }

    public StringBuilder toStringBuilder(){
        return  new StringBuilder("Уровень здоровья = " + health)
                .append("\nОбласть видимости = ")
                .append(vision)
                .append("\nОгнетушитель: ")
                .append( haveAntiFire ? "Да": "Нет")
                .append("\nКол-во потушенного огня: ")
                .append(countDeadFire)
                .append("\nЗнает о пожаре: ")
                .append(isKnow ? "Да": "Нет");
    }

    public TypeMan getType() {
        return type;
    }
}