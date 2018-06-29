package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.InputHandler;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.controller.FireController;
import com.mygdx.game.controller.Loader;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.blocks.TypeBlock;
import com.mygdx.game.model.blocks.TypePosition;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.signs.Sign;
import com.mygdx.game.model.signs.TypeSign;

public class MyWorld {
    /*
     * Размеры окна
     */
    public static int WIDTH = 1500;
    public static  int HEIGHT = 800;

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
        //read();
        /*
         * Загружаемся
         */
        try {
            Loader.load(this);
        }
        catch (Exception e){
            System.out.println("load error");
        }
    }

    /*
     * Создаём людей
     */
    public void createMans(){
        mans.add(new Man(new Vector2(550,600)));
        mans.add(new Man(new Vector2(300,500)));
        mans.add(new Man(new Vector2(400,400)));
        mans.add(new Man(new Vector2(550,300)));
        mans.add(new Man(new Vector2(400,200)));
    }
    //Добавляем обьекты
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
        signs.add(new Sign(new Vector2(550,600),TypeSign.ExitSign));
        signs.add(new Sign(new Vector2(200,200),TypeSign.AntiFire));
        signs.add(new Sign(new Vector2(400,400),TypeSign.AntiFire));
        signs.add(new Sign(new Vector2(600,200),TypeSign.AlarmButton));
        createMans();

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
    /*
     * Обновление поля
     */
    public void update() {
        /*
         * Пока людей нет, ждем нажатие Entera
         */
        if(mans.size == 0) {
            if (InputHandler.key()) {
                createMans();
                countLive = mans.size;
            }
            else
                return;
        }
        /*
         * Проверка всяких коллизий
         */
        controller.check();
        /*
         * Обновление местоположения людей
         */
        for(Man man: mans) {
            man.update();
            countDead = countSave = 0;
            if(man.isDead())
                countDead++;
            if(man.isSave())
                countSave++;
        }
        countLive = mans.size -(countDead+countSave);
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
        if(!Controller.isFire()) {
            Sign fair = new Sign(InputHandler.getMousePosition(), TypeSign.Fire);
            fireController = new FireController(fair, this);
            signs.add(fair);
            Controller.setIsFire();
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

}
