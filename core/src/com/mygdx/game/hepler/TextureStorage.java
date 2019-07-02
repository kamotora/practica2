package com.mygdx.game.hepler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/*
* Хранилище текстур, чтобы каждый раз не перезагружать текстуры, загрузим один раз сюда
*/
public class TextureStorage {
    private static HashMap<String,Texture> array = new HashMap<>(15);
    static TextureStorage textureStorage = null;
    TextureStorage(){
        array.put("AlarmButton",new Texture(Gdx.files.internal("AlarmButton.png")));
        array.put( "AntiFire", new Texture(Gdx.files.internal("AntiFire.png")));
        array.put("kill", new Texture(Gdx.files.internal("dead.png")));
        array.put( "ExitSign", new Texture(Gdx.files.internal("ExitSign.png")));
        array.put("Fire", new Texture(Gdx.files.internal("Fire.png")));
        array.put("Man", new Texture(Gdx.files.internal("Man.png")));
        array.put("PressAlarmButton", new Texture(Gdx.files.internal("PressAlarmButton.png")));
        array.put("PressAntiFire", new Texture(Gdx.files.internal("PressAntiFire.png")));
        array.put("Smoke",new Texture(Gdx.files.internal("Smoke.png")));
        array.put("saved",new Texture(Gdx.files.internal("saved.png")));
    }
    public static Texture getTexture(String name){
        //Если ещё не создали массив, создаём
        if(textureStorage == null)
            textureStorage = new TextureStorage();
        //Ищем текстуру
        for(String key : array.keySet())
            /*
            * Сначала просто сравниваем
            * Если не вышло, пробуем привести к одному регистру
            */
            if(name.equals(key))
                return array.get(key);
            else if(name.toLowerCase().equals(key.toLowerCase()))
                return array.get(key);
        //Если не найдено, пользователь напутал
        System.out.println("TextureStorage error");
        return null;
    }
}
