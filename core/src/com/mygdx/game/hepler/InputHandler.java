package com.mygdx.game.hepler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputHandler {

    public static boolean isClicked() {
        return Gdx.input.justTouched();
    }

    public static Vector2 getMousePosition() {
        System.out.println(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
        return new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }

    public static boolean keyEnter() {
        return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
    }
    public static boolean key(int key) {
        return Gdx.input.isKeyPressed(key);
    }

}
