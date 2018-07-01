package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.Loader;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.signs.Sign;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.renderer.WorldRenderer;

import java.util.Vector;


public class Creator {
    Array<Block> blocks;
    Array<Sign> signs;
    MyWorld world;
    public Creator(MyWorld world){
        this.world = world;
        try {
            Loader.load(world);
        }catch (Exception e) { }
        blocks = world.getBlocks();
        signs = world.getSigns();
    }
    public void create(){
        if(InputHandler.key(Input.Keys.B))
            createBlocks();
        //if(InputHandler.key(Input.Keys.S))
            //createSigns();
    }

    private void createBlocks() {
        Vector2 position = new Vector2();
        while (true) {
            if(InputHandler.isClicked()) {
                position.set(InputHandler.getMousePosition());
                break;
            }
        }
        int length = 10;
        TypePosition typePosition;
        TypeBlock typeBlock = null;
        while (true) {
            if(InputHandler.key(Input.Keys.Z)) {
                typeBlock = TypeBlock.WALL;
                break;
            }
            if(InputHandler.key(Input.Keys.X)) {
                typeBlock = TypeBlock.EXIT;
                break;
            }
        }
        Block block = null;
        WorldRenderer.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        do {
            if(InputHandler.key(Input.Keys.UP)) {
                length += 10;
                typePosition = TypePosition.VERTICAL;
                block = new Block(position,typeBlock,typePosition,length);
            }

            if(InputHandler.key(Input.Keys.RIGHT)) {
                length += 10;
                typePosition = TypePosition.HORIZONTAL;
                block = new Block(position,typeBlock,typePosition,length);
            }
            if(block != null)
                block.draw(WorldRenderer.getShapeRenderer());
        }while (!InputHandler.key(Input.Keys.ENTER));
        WorldRenderer.getShapeRenderer().end();

    }
}
