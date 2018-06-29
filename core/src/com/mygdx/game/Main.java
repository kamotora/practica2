package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.renderer.WorldRenderer;
import com.sun.javafx.scene.control.skin.LabelSkin;

public class Main extends ApplicationAdapter {
	MyWorld world;
	WorldRenderer worldRenderer;
	@Override
	public void create () {
		world = new MyWorld();
		worldRenderer = new WorldRenderer(world);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}

}
