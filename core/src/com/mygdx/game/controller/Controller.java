package com.mygdx.game.controller;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;


public class Controller {
    MyWorld world;
    Array<Man> mans;
    Array<Block> blocks;
    Array<Sign> signs;
    static boolean isFire;
    boolean isTouchAlarm = false;
    public Controller(MyWorld world){
        this.world = world;
        mans = world.getMans();
        signs = world.getSigns();
        blocks = world.getBlocks();
    }

    //Совершаем действия
    public void check(){
        //Если не начался пожар
        if(!isFire) {
            for (Man man: mans)
                checkWithBlock(man);
        }
        else {
            Array<Man> dead = new Array<Man>();
            Array<Man> saved = new Array<Man>();
            for (Man man: mans) {
                //если умер или спасён, ничего делать не надо
                if (man.isDead())
                    continue;
                //Если запущена сирена, чел узнает о пожаре
                if(isTouchAlarm)
                    man.setKnow();
                checkWithBlock(man);
                checkWithSigns(man);
                if (man.isDead())
                    dead.add(man);
                if(man.isSave())
                    saved.add(man);
            }
            for (Man man: dead)
                man.dead();
            for (Man man: saved)
                man.save();
        }
    }

    //обработка событий с блоком
    public void checkWithBlock(Man man){
        for(Block block : blocks){
            //Если чел заходит на стену
            if(block.getFigure().overlaps(man.getBounds())) {
                //System.out.println("contact");
                if((block.getTypeBlock() == TypeBlock.WALL) || (block.getTypeBlock() == TypeBlock.WINDOW && !man.isKnow()))
                    man.setVelocity(block);
                if(block.getTypeBlock() == TypeBlock.WINDOW && man.isKnow())
                    man.save();
            }

            /*if (block.getTypePosition() == TypePosition.VERTICAL) {
                //Если в следующий ход будет наложение, изменяем скорость
                if ((Math.abs(man.getCenterPosition().x - block.getPosition().x) < man.getSize())) {
                    if (block.getTypeBlock() == TypeBlock.EXIT) {
                        Position()).nor());
                        System.out.println("exit");
                        break;
                    }
                    if (block.getTypeBlock() == TypeBlock.WALL) {
                        man.setVelocity(man.getPosition().cpy().sub(new Vector2(block.getPosition().x, man.getPosition().y)).nor());
                        System.out.println("ecnm " + man.getPosition());
                    }man.setVelocity(block.getPosition().cpy().sub(man.get
                    if (block.getTypeBlock() == TypeBlock.WINDOW) {
                        System.out.println("window");
                    }
                }

            }
            if (block.getTypePosition() == TypePosition.HORIZONTAL){
                if ((Math.abs(man.getCenterPosition().y - block.getPosition().y) < man.getSize())) {
                    if (block.getTypeBlock() == TypeBlock.EXIT) {
                        man.setVelocity(block.getCenterPosition().cpy().sub(man.getPosition()).nor());
                        System.out.println("exit");
                        break;
                    }
                    if (block.getTypeBlock() == TypeBlock.WALL) {
                        man.setVelocity(man.getPosition().cpy().sub(new Vector2(man.getPosition().x, block.getPosition().y)).nor());
                        System.out.println("ecnm " + man.getPosition());
                    }
                    if (block.getTypeBlock() == TypeBlock.WINDOW) {
                        System.out.println("window");
                    }
                }

            }*/
        }
    }

    public void checkWithSigns(Man man){
        Array<Sign> needChange = new Array<Sign>();
        for(Sign sign : signs){
            if(man.getCenterPosition().sub(sign.getPosition()).len() < Man.getSize()*2){
                if (sign.getType() == TypeSign.AntiFire && !sign.isUsed() && man.isKnow()) {
                    man.setAntiFire();
                }
                if(sign.getType() == TypeSign.Fire && man.isAntiFire()) {
                    needChange.add(sign);
                    man.setCountDeadFire();
                }
                //Нажимаем на кнопку, если возможно
                if(sign.getType() == TypeSign.AlarmButton && !man.isKnow() && !sign.isUsed()) {
                    man.setKnow();
                    sign.setUsed();
                }
                if (sign.getType() == TypeSign.Fire || sign.getType() == TypeSign.Smoke)
                    man.setHealth(sign.getType());
                else
                    sign.setUsed();

            }

            //Если находится в области видимости
            if(man.getCenterPosition().sub(sign.getPosition()).len() < man.getVision()){
                //Если чел видит знак выхода и ещё не находится там
                if(sign.getType() == TypeSign.ExitSign && man.isKnow() && !man.getBounds().overlaps(sign.getFigure())){
                    man.setVelocity(sign.getPosition().cpy().sub(man.getPosition()).nor());
                    man.save();
                }
                //Если чел видит ононь, он узнал о пожаре
                if(sign.getType() == TypeSign.Fire)
                    man.setKnow();
            }
        }
        //Если нужно изменить знак
        for(Sign sign : needChange)
            sign.change();
    }

    public static boolean isFire() {
        return isFire;
    }

    public static void setIsFire(){
        if(!isFire)
            isFire = true;
    }

}

