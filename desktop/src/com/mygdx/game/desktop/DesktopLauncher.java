package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;
import com.mygdx.game.model.MyWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = MyWorld.HEIGHT;
		config.width = MyWorld.WIDTH;
		new LwjglApplication(new Main(), config);
	}
}
