package com.mygdx.game.model.signs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.hepler.TextureStorage;
import com.mygdx.game.renderer.WorldRenderer;


public class Sign{
    Vector2 position= new Vector2();
    Texture image;
    TypeSign type;
    Rectangle figure;
    boolean isUsed = false;
    final static int SIZE_ON_MAP = 32;
    public Sign(Vector2 centerPosition, TypeSign type) {
        this.position.set(centerPosition.x, centerPosition.y) ;
         image = TextureStorage.getTexture(type.name());
         this.type = type;
         figure = new Rectangle(position.x,position.y,SIZE_ON_MAP,SIZE_ON_MAP);
    }

    public static int getSizeOnMap() {
        return SIZE_ON_MAP;
    }

    public TypeSign getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
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
        if(type == TypeSign.Fire) {
            type = TypeSign.Smoke;
            image = TextureStorage.getTexture(type.name());
        }
        WorldRenderer.getSpriteBatch().begin();
        draw(WorldRenderer.getSpriteBatch());
        WorldRenderer.getSpriteBatch().end();
    }
    public void setIsUsed() { if(!isUsed) isUsed = true; }

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
        Sign sign = null;
        try{
            Vector2 vector = new Vector2();
            vector.fromString(str.substring(posStart,posFinish));
            TypeSign typeSign = TypeSign.fromString(str.substring(typeStart,typeFinish));
            if (typeSign == null)
                throw new Exception("typeSign null");
            sign =  new Sign(vector,typeSign);
        }
        catch (Exception e){
            System.out.println("sign error "+e);
            return null;
        }
        return sign;
    }

}
