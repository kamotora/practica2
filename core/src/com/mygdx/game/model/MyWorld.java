package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.InputHandler;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;

import java.io.*;
import java.util.Scanner;

public class MyWorld {
    Array<Block> blocks = new Array<Block>();
    Array<Man> mans = new Array<Man>();
    Array<Sign> signs = new Array<Sign>();
    Controller controller;

    public MyWorld(){
        controller = new Controller(this);
        read();
    }

    public void read(){
        blocks.add(new Block(new Vector2(100,100),TypeBlock.WALL,TypePosition.HORIZONTAL,800));
        blocks.add(new Block(new Vector2(900,100),TypeBlock.WINDOW,TypePosition.VERTICAL,100));
        blocks.add(new Block(new Vector2(900,200),TypeBlock.WALL,TypePosition.VERTICAL,400));
        blocks.add(new Block(new Vector2(600,600),TypeBlock.WALL,TypePosition.HORIZONTAL,300));
        blocks.add(new Block(new Vector2(500,600),TypeBlock.EXIT,TypePosition.HORIZONTAL,100));
        blocks.add(new Block(new Vector2(300,600),TypeBlock.WALL,TypePosition.HORIZONTAL,200));
        blocks.add(new Block(new Vector2(200,600),TypeBlock.WINDOW,TypePosition.HORIZONTAL,100));
        blocks.add(new Block(new Vector2(100,600),TypeBlock.WALL,TypePosition.HORIZONTAL,100));
        blocks.add(new Block(new Vector2(100,100),TypeBlock.WALL,TypePosition.VERTICAL,500));
        signs.add(new Sign(new Vector2(550,300),TypeSign.ExitSign));
        signs.add(new Sign(new Vector2(200,200),TypeSign.AntiFire));
        signs.add(new Sign(new Vector2(300,300),TypeSign.Fire));
        signs.add(new Sign(new Vector2(600,200),TypeSign.AlarmButton));
        mans.add(new Man(new Vector2(550,600)));
        mans.add(new Man(new Vector2(300,500)));
        mans.add(new Man(new Vector2(400,400)));
        mans.add(new Man(new Vector2(550,300)));
        mans.add(new Man(new Vector2(400,200)));

    }
    public Array<Block> getBlocks() {
        return blocks;
    }

    public Array<Man> getMans() {
        return mans;
    }

    public Array<Sign> getSigns() {
        return signs;
    }

    public void update() {
        controller.check();
        for(Man man: mans) {
            man.update();
        }
        if(InputHandler.isClicked())
            signs.add(new Sign(InputHandler.getMousePosition(),TypeSign.Fire));
    }
}
