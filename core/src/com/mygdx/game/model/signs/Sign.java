package com.mygdx.game.model.signs;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.renderer.WorldRenderer;


public class Sign{
    Vector2 position;
    Texture image;
    TypeSign type;
    boolean isUsed = false;
    final static int SIZE_ON_MAP = 32;
    public Sign(Vector2 position, TypeSign type) {
        this.position = new Vector2(position);
         image = new Texture(type.toString() + ".png");
         this.type = type;
    }

    public Texture getImage() {
        return image;
    }

    public TypeSign getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    static public Sign load(String str){
        String[] strings = str.split(" ");
        Sign sign;
        for (TypeSign type : TypeSign.values())
            if(str.contains(type.name())){
                sign = new Sign(new Vector2(Float.parseFloat(strings[3]), Float.parseFloat(strings[4])),type);
                return sign;
            }
        return null;
    }
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(image,position.x, position.y, SIZE_ON_MAP,SIZE_ON_MAP);
    }

    public void setUsed() {
        if(!isUsed)
            isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void change() {
        if(type != TypeSign.Fire)
            return;
        type = TypeSign.Smoke;
        image = new Texture(type.toString() + ".png");
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }
    public void update(){
        image = new Texture("used.png");
    }

}
