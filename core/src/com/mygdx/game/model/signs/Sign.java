package com.mygdx.game.model.signs;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.TextureStorage;
import com.mygdx.game.renderer.WorldRenderer;


public class Sign{
    Vector2 position;
    Texture image;
    TypeSign type;
    Rectangle figure;
    boolean isUsed = false;
    final static int SIZE_ON_MAP = 32;
    public Sign(Vector2 position, TypeSign type) {
        this.position = new Vector2(position);
         image = TextureStorage.getTexture(type.name());
         this.type = type;
         figure = new Rectangle(position.x,position.y,SIZE_ON_MAP,SIZE_ON_MAP);
    }

    public TypeSign getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getCenterPosition(){
        return new Vector2(position.x+SIZE_ON_MAP/2,position.y+SIZE_ON_MAP/2);
    }


    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(image,position.x, position.y, SIZE_ON_MAP,SIZE_ON_MAP);
    }

    //Меняет текстуру, помечаем, если надо
    public void setUsed() {
        if(!isUsed && (type == TypeSign.AlarmButton || type == TypeSign.AntiFire)){
            isUsed = true;
            image = TextureStorage.getTexture("Press"+type.name());
        }
        if(type == TypeSign.Fire)
            image = TextureStorage.getTexture(type.name());
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }

    public boolean isUsed() {
        return isUsed;
    }


    public Rectangle getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sign{");
        sb.append("position=").append(position);
        sb.append(", type=").append(type);
        sb.append('}');
        sb.append('\n');
        return sb.toString();
    }

    public static Sign fromString(String str){
        int posStart = str.indexOf("position=", 1) + 9;
        int posFinish = str.indexOf("),", posStart)+1;
        int typeStart = str.indexOf("type=", 1) + 5;
        int typeFinish = str.indexOf('}', typeStart);
        try{
            Vector2 vector = new Vector2();
            vector.fromString(str.substring(posStart,posFinish));
            TypeSign typeSign = TypeSign.fromString(str.substring(typeStart,typeFinish));
            if (typeSign == null)
                throw new Exception("typeSign null");
            return new Sign(vector,typeSign);
        }
        catch (Exception e){
            System.out.println("sign error "+e);
        }
        return null;
    }
}
