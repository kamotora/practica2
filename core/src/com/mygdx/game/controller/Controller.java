package com.mygdx.game.controller;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.hepler.InputHandler;
import com.mygdx.game.model.world.TypeWorld;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.world.MyWorld;
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
    static String msg = new String("Кликните на человека,\nчтобы узнать всё о нём");
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
                checkWithOtherMans(man);
                //Хотят ли узнать про него
                updateManMsg(man);
            }

        }
        else {
            Array<Man> dead = new Array<Man>();
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
                if (man.isDead())
                    dead.add(man);
                checkWithOtherMans(man);
            }
            for (Man man: dead)
                man.kill();
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
        }
    }

    /*
    * Обработка событий с знаками
    */
    public void checkWithSigns(Man man){
        for(Sign sign : signs){
            //Если огонь или дым близко, он уменьшает здоровье
            if ((sign.getType() == TypeSign.Fire || sign.getType() == TypeSign.Smoke) && man.getBounds().overlaps(sign.getFigure()))
                man.setHealth(sign.getType());
            if(man.getCenterPosition().sub(sign.getPosition()).len() < Man.getSize()*2){
                //Берем огнетушитель
                if (sign.getType() == TypeSign.AntiFire && !sign.isUsed() && man.isKnow()) {
                    man.setAntiFire();
                    sign.setUsed();
                }
                //Тушим
                if(sign.getType() == TypeSign.Fire && man.isAntiFire()) {
                    sign.setUsed();
                    man.setCountDeadFire();
                }
                //Нажимаем на кнопку, если возможно
                if(sign.getType() == TypeSign.AlarmButton  && !sign.isUsed()) {
                    isTouchAlarm = true;
                    man.setKnow();
                    sign.setUsed();
                }
                //Спасаемся
                if(sign.getType() == TypeSign.ExitSign && man.isKnow() && man.getBounds().overlaps(sign.getFigure()))
                    man.save();
            }
            //Если находится в области видимости
            if(man.getCenterPosition().sub(sign.getPosition()).len() < man.getVision()){
                //Если чел видит знак выхода и ещё не находится там, направляем его
                if(sign.getType() == TypeSign.ExitSign && man.isKnow() && !man.getBounds().overlaps(sign.getFigure()))
                    man.setVelocity(sign.getPosition().cpy().sub(man.getPosition()).nor());
                //Если чел видит огонь, он узнал о пожаре
                if(sign.getType() == TypeSign.Fire)
                    man.setKnow();
            }
        }
    }
    public void checkWithOtherMans(Man man){
        Array.ArrayIterator<Man> iterator = new Array.ArrayIterator<Man>(mans);
        for(Man man2 = iterator.next();iterator.hasNext();man2 = iterator.next()){
            if(man == man2)
                continue;
            if(man.getBounds().overlaps(man2.getBounds())){
                man2.setVelocity(man2.getPosition().cpy().sub(man.getPosition()).nor());
                man.setVelocity(man.getPosition().cpy().sub(man2.getPosition()).nor());
            }
        }
    }


    public boolean checkBoundsOfScreen(Man man){
        if(man.getCenterPosition().x < 0
                || man.getCenterPosition().y < 0
                || man.getCenterPosition().x > MyWorld.WIDTH
                || man.getCenterPosition().y > MyWorld.HEIGHT){
            return true;
        }
        return false;
    }

    public void checkWorld(){
        int dead = 0, save =0;
        Array<Man> needDelete = new Array<Man>();
        for(Man man:mans){
            if(man.getType() == TypeMan.DEAD)
                dead++;
            if(man.getType() == TypeMan.SAVE)
                save++;
            if(checkBoundsOfScreen(man) && !man.isKnow())
                needDelete.add(man);
        }
        world.getMans().removeAll(needDelete, true);
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

