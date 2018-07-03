package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.model.world.MyWorld;
import com.mygdx.game.renderer.WorldRenderer;

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
		update();
	}

	public void update(){
		world.update();
	}

}
