package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.renderer.WorldRenderer;

public class Main extends ApplicationAdapter {
	MyWorld world;
	WorldRenderer worldRenderer;
	Stage stage;
	@Override
	public void create () {
		world = new MyWorld();
		stage = new Stage();
		worldRenderer = new WorldRenderer(world);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		worldRenderer.render();
		update();
	}

	public void update(){
		world.update();
	}

}
