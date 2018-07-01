package com.mygdx.game.controller;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.InputHandler;
import com.mygdx.game.model.TypeWorld;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.mans.TypeMan;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;


public class Controller {
    MyWorld world;
    Array<Man> mans;
    Array<Block> blocks;
    Array<Sign> signs;
    boolean isTouchAlarm = false;
    static String msg = new String("Кликните на человека,\nчтобы узнать ин-фу о нём");
    public Controller(MyWorld world){
        this.world = world;
        mans = world.getMans();
        signs = world.getSigns();
        blocks = world.getBlocks();
    }

    //Совершаем действия
    public void check(){
        //Если не начался пожар
        if(world.getTypeWorld() != TypeWorld.FIRE) {
            for (Man man: mans){
                checkWithBlock(man);
                //Хотят ли узнать про него
                updateManMsg(man);
            }

        }
        else {
            Array<Man> dead = new Array<Man>();
            Array<Man> saved = new Array<Man>();
            for (Man man: mans) {
                //Хотят ли узнать про него
                updateManMsg(man);
                //если умер или спасён, ничего делать не надо
                if (man.isDead())
                    continue;
                //Если запущена сирена, чел узнает о пожаре
                if(isTouchAlarm)
                    man.setKnow();
                checkWithBlock(man);
                checkWithSigns(man);
                checkBoundsOfScreen(man);
                if (man.isDead())
                    dead.add(man);
                if(man.isSave())
                    saved.add(man);
            }
            for (Man man: dead)
                man.dead();
        }

        //Узнаем, как дела
        checkWorld();
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

    /*
    * Подкорректировать дальность огня
    */
    public void checkWithSigns(Man man){
        for(Sign sign : signs){
            if(man.getCenterPosition().sub(sign.getPosition()).len() < Man.getSize()*2){
                if (sign.getType() == TypeSign.AntiFire && !sign.isUsed() && man.isKnow()) {
                    man.setAntiFire();
                    sign.setUsed();
                }
                if(sign.getType() == TypeSign.Fire && man.isAntiFire()) {
                    sign.setUsed();
                    man.setCountDeadFire();
                }
                //Нажимаем на кнопку, если возможно
                if(sign.getType() == TypeSign.AlarmButton && !man.isKnow() && !sign.isUsed()) {
                    man.setKnow();
                    sign.setUsed();
                }
                if (sign.getType() == TypeSign.Fire || sign.getType() == TypeSign.Smoke)
                    man.setHealth(sign.getType());
                if(sign.getType() == TypeSign.ExitSign && man.isKnow() && man.getBounds().overlaps(sign.getFigure()))
                    man.save();

            }

            //Если находится в области видимости
            if(man.getCenterPosition().sub(sign.getPosition()).len() < man.getVision()){
                //Если чел видит знак выхода и ещё не находится там
                if(sign.getType() == TypeSign.ExitSign && man.isKnow() && !man.getBounds().overlaps(sign.getFigure()))
                    man.setVelocity(sign.getPosition().cpy().sub(man.getPosition()).nor());
                //Если чел видит ононь, он узнал о пожаре
                if(sign.getType() == TypeSign.Fire)
                    man.setKnow();
            }
        }
    }
    /*
    * Если чел вышел за экран, он спасся
    */
    public void checkBoundsOfScreen(Man man){
        if(man.getCenterPosition().x < 0
                || man.getCenterPosition().y < 0
                || man.getCenterPosition().x > MyWorld.WIDTH
                || man.getCenterPosition().y > MyWorld.HEIGHT){
            man.save();
        }
    }

    public void checkWorld(){
        int dead = 0, save =0;
        for(Man man:mans){
            if(man.getType() == TypeMan.DEAD)
                dead++;
            if(man.getType() == TypeMan.SAVE)
                save++;
        }
        boolean haveFire = false, haveSmoke = false;
        for(Sign sign: signs){
            if(sign.getType() == TypeSign.Fire){
                haveFire = true;
                break;
            }
            if(sign.getType() == TypeSign.Smoke)
                haveSmoke = true;
        }
        /*
        * Все умерли
        */
        if(dead == mans.size) {
            world.setTypeWorld(TypeWorld.END);
            return;
        }
        /*
         * Все спаслись
         */
        if(save == mans.size ){
            world.setTypeWorld(TypeWorld.WIN);
            return;
        }
        /*
         * Пожар ликвидирован
         */
        if(!haveFire && haveSmoke) {
            /*
             * Некоторые умерли, но потушили пожар
             */
            if(dead > 0)
                world.setTypeWorld(TypeWorld.WINANDDEAD);
            else
                /*
                 * Потушили пожар и выжили
                 */
                world.setTypeWorld(TypeWorld.WIN);
        }
        /*
         * Людей в здании не осталось, часть умерла, часть спаслась
         */
        if((dead+save == mans.size) && (dead != 0) && (save != 0)){
            world.setTypeWorld(TypeWorld.MAYBE);
            return;
        }
        /*
         * Пожар продолжается, есть люди в здинии
         */
        if(haveFire){
            world.setTypeWorld(TypeWorld.FIRE);
            return;
        }

    }

    public void updateManMsg (Man man){
        if(InputHandler.isClicked() && man.getBounds().contains(InputHandler.getMousePosition()))
            msg = man.toStringBuilder().toString();
    }


    public static String getManMsg() {
        return msg;
    }



}

