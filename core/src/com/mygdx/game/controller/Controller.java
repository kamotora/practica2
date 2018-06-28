package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.Man;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;


public class Controller {
    MyWorld world;
    Array<Man> mans;
    Array<Block> blocks;
    Array<Sign> signs;
    boolean isFire;
    public Controller(MyWorld world){
        this.world = world;
        mans = world.getMans();
        signs = world.getSigns();
        blocks = world.getBlocks();
    }
    public void check(){
        if(!isFire)
            for(Sign sign : signs) {
                if(isFire)
                    break;
                if (sign.getType() == TypeSign.Fire || sign.getType() == TypeSign.Smoke)
                    isFire = true;
            }
        if(!isFire) {
            for (Man man: mans)
                checkWithBlock(man);
        }
        else {
            Array<Man> dead = new Array<Man>();
            for (Man man: mans) {
                if (man.isDead())
                    continue;
                checkWithBlock(man);
                checkWithSigns(man);
                if (man.isDead())
                    dead.add(man);
            }
            for (Man man: dead) {
                //mans.removeValue(man,true);
                man.dead();
            }
        }
    }

    public void checkWithBlock(Man man){
        for(Block block : blocks){
            if (block.getTypePosition() == TypePosition.VERTICAL) {
                if ((Math.abs(man.getPosition().x - block.getPosition().x) < man.getSize())) {
                    if (block.getTypeBlock() == TypeBlock.WALL) {
                        man.setVelocity(man.getPosition().cpy().sub(new Vector2(block.getPosition().x, man.getPosition().y)).nor());
                        System.out.println("ecnm " + man.getPosition());
                    }
                    if (block.getTypeBlock() == TypeBlock.WINDOW) {
                        System.out.println("window");
                    }
                    if (block.getTypeBlock() == TypeBlock.EXIT) {
                        man.setVelocity(block.getPosition().cpy().sub(man.getPosition()).nor());
                        System.out.println("exit");
                    }
                }

            }
            if (block.getTypePosition() == TypePosition.HORIZONTAL){
                if ((Math.abs(man.getPosition().y - block.getPosition().y) < man.getSize())) {
                    if (block.getTypeBlock() == TypeBlock.WALL) {
                        man.setVelocity(man.getPosition().cpy().sub(new Vector2(man.getPosition().x, block.getPosition().y)).nor());
                        System.out.println("ecnm " + man.getPosition());
                    }
                    if (block.getTypeBlock() == TypeBlock.WINDOW) {
                        System.out.println("window");
                    }
                    if (block.getTypeBlock() == TypeBlock.EXIT) {
                        //man.setVelocity(block.getPosition().cpy().sub(man.getPosition()).nor());
                        System.out.println("exit");
                    }
                }

            }
        }
    }

    public void checkWithSigns(Man man){
        Array<Sign> needChange = new Array<Sign>();
        for(Sign sign : signs){
            if(man.getPosition().cpy().sub(sign.getPosition()).len() < Man.getSize()){
                if (sign.getType() == TypeSign.AntiFire && !sign.isUsed() && man.isKnow()) {
                    man.setAntiFire();
                }
                if(sign.getType() == TypeSign.Fire && man.isAntiFire())
                    needChange.add(sign);
                if (sign.getType() == TypeSign.Fire || sign.getType() == TypeSign.Smoke)
                    man.setHealth(sign.getType());
                else
                    sign.setUsed();

            }
            if(man.getPosition().cpy().sub(sign.getPosition()).len() < man.getVision()){
                if(sign.getType() == TypeSign.ExitSign && man.isKnow()){
                    man.setVelocity(sign.getPosition().cpy().sub(man.getPosition()).nor());
                }
                if(sign.getType() == TypeSign.Fire){
                    man.setKnow();
                }
            }
        }
        for(Sign sign : needChange)
            sign.change();

    }
}
