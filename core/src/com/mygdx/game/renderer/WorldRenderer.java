package com.mygdx.game.renderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.signs.Sign;

public class WorldRenderer {

    private MyWorld world;
    public OrthographicCamera cam;
    static ShapeRenderer renderer = new ShapeRenderer();
    static SpriteBatch spriteBatch = new SpriteBatch();

    //установка камеры
    public void SetCamera(float x, float y){
        this.cam.position.set(x, y,0);
        this.cam.update();
    }

    public WorldRenderer(MyWorld world) {
        this.world = world;
    }

    //основной метод, здесь мы отрисовываем все объекты мира
    public void render() {
        world.update();
        drawBlocks();
        drawSigns();
        drawMans();
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
    void drawSigns(){
        spriteBatch.begin();
        for(Sign sign : world.getSigns()){
            sign.draw(spriteBatch);
        }
        spriteBatch.end();
    }
    //отрисовка персонажа по аналогии
    private void drawMans() {
        spriteBatch.begin();
        for(Man man : world.getMans()){
            man.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    public static SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
