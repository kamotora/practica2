package com.mygdx.game.renderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.world.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.signs.Sign;

public class WorldRenderer {

    private MyWorld world;
    OrthographicCamera cam;
    private static ShapeRenderer renderer = new ShapeRenderer();
    private static SpriteBatch spriteBatch = new SpriteBatch();
    private static BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"),false);

    public WorldRenderer(MyWorld world) {
        this.world = world;
    }

    //основной метод, здесь мы отрисовываем все объекты мира
    public void render() {
        drawBlocks();
        spriteBatch.begin();
        drawSigns();
        drawMans();
        drawLabels();
        spriteBatch.end();
    }

    private void drawLabels() {
        bitmapFont.draw(spriteBatch,"Живых людей: "+world.getCountLive(), 1000,700);
        bitmapFont.draw(spriteBatch,"Мертвых людей: "+world.getCountDead(), 1000,650);
        bitmapFont.draw(spriteBatch,"Людей спаслось: "+world.getCountSave(), 1000,600);
        bitmapFont.draw(spriteBatch, Controller.getManMsg(),1000,500);
        bitmapFont.draw(spriteBatch,world.getTypeWorld().toString(), 1000,800);
    }

    //отрисовка кирпичей
    private void drawBlocks() {
        //тип устанавливаем...а данном случае с заливкой
        renderer.begin(ShapeType.Filled);
        //прогоняем блоки
        for (Block block : world.getBlocks()) {
            block.draw(renderer);
        }

        renderer.end();
    }
    private void drawSigns(){
        for(Sign sign : world.getSigns()){
            sign.draw(spriteBatch);
        }
    }
    //отрисовка персонажа по аналогии
    private void drawMans() {
        for(Man man : world.getMans()){
            man.draw(spriteBatch);
        }
    }

    public static SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    public static ShapeRenderer getShapeRenderer() {
        return renderer;
    }
}
