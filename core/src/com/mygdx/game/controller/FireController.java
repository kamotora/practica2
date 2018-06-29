package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.TypeWorld;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;
import com.badlogic.gdx.math.Vector2;


public class FireController {
    int countAddFire;
    int countLinesFire;
    final static int DELAY = 15000;
    long times;
    Vector2 startFire;
    MyWorld world;
    Sign fire;
    public FireController(Sign fire, MyWorld world) {
        this.startFire = new Vector2(fire.getPosition());
        startFire.y += 32;
        startFire.x -= 32;
        countAddFire = 0;
        countLinesFire = 1;
        this.world = world;
        times = System.currentTimeMillis();
        this.fire = fire;
    }
    //распространение огня

    //Нужно сделать: если есть потушенные огоньки, распростанение уменьшается
    public void spreadFire(){
        if(world.getTypeWorld() == TypeWorld.WIN)
            return;
        if(System.currentTimeMillis() - times < DELAY)
            return;
        if(fire.getType() == TypeSign.Smoke) {
            world.setTypeWorld(TypeWorld.WIN);
            System.out.println(world.getTypeWorld().name());
        }
        times = System.currentTimeMillis();
        countAddFire += 8;
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
