package com.mygdx.game.hepler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import javafx.util.Pair;

/*
* Хранилище текстур, чтобы каждый раз не перезагружать текстуры, загрузим один раз сюда
*/
public class TextureStorage {
    private static Array<Pair<Texture,String>> array = new Array<Pair<Texture,String>>(15);
    static TextureStorage textureStorage = null;
    TextureStorage(){
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("AlarmButton.png")), "AlarmButton"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("AntiFire.png")), "AntiFire"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("dead.png")), "kill"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("ExitSign.png")), "ExitSign"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("Fire.png")), "Fire"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("Man.png")), "Man"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("PressAlarmButton.png")), "PressAlarmButton"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("PressAntiFire.png")), "PressAntiFire"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("Smoke.png")), "Smoke"));
        array.add(new Pair<Texture, String>(new Texture(Gdx.files.internal("saved.png")), "saved"));
    }
    public static Texture getTexture(String name){
        //Если ещё не создали массив, создаём
        if(textureStorage == null)
            textureStorage = new TextureStorage();
        //Ищем текстуру
        for(Pair<Texture,String> obj : array)
            /*
            * Сначала просто сравниваем
            * Если не вышло, пробуем привести к одному регистру
            */
            if(name.equals(obj.getValue()))
                return obj.getKey();
            else if(name.toLowerCase().equals(obj.getValue().toLowerCase()))
                return obj.getKey();
        //Если не найдено, пользователь напутал
        System.out.println("TextureStorage error");
        return null;
    }
}
