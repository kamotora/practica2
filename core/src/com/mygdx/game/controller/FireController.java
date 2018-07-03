package com.mygdx.game.controller;

import com.mygdx.game.model.world.MyWorld;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;
import com.badlogic.gdx.math.Vector2;


public class FireController {
    int countLinesFire;
    final static int DELAY = 15000;
    long times;
    Vector2 startFire;
    MyWorld world;
    Sign fire;
    public FireController(Sign fire, MyWorld world) {
        this.startFire = new Vector2(fire.getPosition());
        startFire.y += Sign.getSizeOnMap();
        startFire.x -= Sign.getSizeOnMap();
        countLinesFire = 1;
        this.world = world;
        times = System.currentTimeMillis();
        this.fire = fire;
    }
    //распространение огня

    //Нужно сделать: если есть потушенные огоньки, распростанение уменьшается
    public void spreadFire(){
        if(System.currentTimeMillis() - times < DELAY)
            return;
        if(fire.getType() == TypeSign.Smoke) {
            return;
        }

        //Если есть потушенные огоньки, уменьшаем распространение пожара
        for(Sign sign : world.getSigns())
            if(sign.getType() == TypeSign.Smoke && !sign.isUsed()){
                countLinesFire--;
                sign.setIsUsed();
            }

        times = System.currentTimeMillis();
        countLinesFire += 2;
        /*
        * Заполнили верхнюю и нижнюю линии над огоньком
        */
        for(int i = 0; i < countLinesFire; i++){
            world.getSigns().add(new Sign(new Vector2(startFire.x + 32*i, startFire.y),TypeSign.Fire));
            world.getSigns().add(new Sign(new Vector2(startFire.x + 32*i, startFire.y - 32*(countLinesFire-1)),TypeSign.Fire));
        }
        for(int i = 0; i < countLinesFire - 2; i++){
            world.getSigns().add(new Sign(new Vector2(startFire.x, startFire.y - 32*(i+1)),TypeSign.Fire));
            world.getSigns().add(new Sign(new Vector2(startFire.x + 32*(countLinesFire-1), startFire.y - 32*(i+1)),TypeSign.Fire));
        }
        startFire.x -= 32;
        startFire.y += 32;
    }
}
