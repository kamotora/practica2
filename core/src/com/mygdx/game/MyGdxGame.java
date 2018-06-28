package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.renderer.WorldRenderer;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
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
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
