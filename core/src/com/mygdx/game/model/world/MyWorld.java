package com.mygdx.game.model.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.hepler.InputHandler;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.controller.FireController;
import com.mygdx.game.hepler.Loader;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;

public class MyWorld {
    /*
     * Размеры окна
     */
    public static int WIDTH = 1400;
    public static  int HEIGHT = 1000;

    Array<Block> blocks = new Array<Block>();
    Array<Man> mans = new Array<Man>();
    Array<Sign> signs = new Array<Sign>();

    Controller controller;
    FireController fireController;
    /*
     * Столько тушит 1 огнетушитель, потом кончается
     */
    public static final int VOLUME_ANTIFIRE = 3;
    /*
     * Идёт процесс имитации
     */
    TypeWorld typeWorld = TypeWorld.LIVE;

    int countDead = 0;
    int countLive = 0;
    int countSave = 0;
    public MyWorld(){
        controller = new Controller(this);
        /*
         * Загружаемся
         */
        try {
            Loader.loadMap(this);
        }
        catch (Exception e){
            System.out.println("load error");
        }
    }

    /*
     * Создаём людей
     */

    public Array<Block> getBlocks() {
        return blocks;
    }

    public Array<Man> getMans() { return mans; }

    public Array<Sign> getSigns() {
        return signs;
    }
    /*
     * Обновление поля
     */
    public void update() {
        //Добавляем людей по нажатию Entera
        if (InputHandler.keyEnter()) {
            mans.add(new Man(new Vector2(230,900)));
            countLive = mans.size;
        }
        /*
         * Пока людей нет, нечего обновлять
         */
        if (mans.size == 0) {
            return;
        }
        /*
        if (InputHandler.key(Input.Keys.S))
            try {
                Loader.save(this);
            }catch (Exception e){
                System.out.println("kek");
            }
        */
        /*
         * Проверка всяких коллизий
         */
        controller.check();
        /*
         * Обновление кол-ва спасённых и умерших людей
         */
        countDead = countSave = 0;
        for(Man man: mans) {
            man.update();
            if(man.isDead())
                countDead++;
            if(man.isSave())
                countSave++;
        }
        if (typeWorld == TypeWorld.WIN){
            countSave = mans.size;
        }
        countLive = mans.size -countDead;
        //создаём однажды огонь по клику
        if(InputHandler.isClicked())
            createFire();
        if(fireController != null)
            fireController.spreadFire();

    }

    /*
     * Создаём огонь
     */
    public void createFire(){
        if(typeWorld == TypeWorld.LIVE) {
            Sign fair = new Sign(InputHandler.getMousePosition(), TypeSign.Fire);
            fireController = new FireController(fair, this);
            signs.add(fair);
            typeWorld = TypeWorld.FIRE;
        }
    }

    public TypeWorld getTypeWorld() {
        return typeWorld;
    }

    public void setTypeWorld(TypeWorld typeWorld) {
        this.typeWorld = typeWorld;
    }

    public int getCountDead() {
        return countDead;
    }

    public  int getCountLive() {
        return countLive;
    }


    public  int getCountSave() {
        return countSave;
    }
    public void destroy(){
        blocks.clear();
        mans.clear();
        signs.clear();
        fireController = null;
    }


}
