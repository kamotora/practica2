package com.mygdx.game.hepler;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.world.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.mans.Man;
import com.mygdx.game.model.signs.Sign;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Loader {
    final static String PATH  = Gdx.files.internal("config.conf").toString();

    public static void loadMap(MyWorld world) throws Exception{
        FileReader reader = new FileReader(new File(PATH));
        Scanner scan = new Scanner(reader);
        while(scan.hasNextLine()){
            try {
                String str = scan.nextLine();
                if(str.length() <= 1)
                    continue;
                if(str.contains(Block.class.getSimpleName()))
                    world.getBlocks().add(Block.fromString(str));
                if(str.contains(Sign.class.getSimpleName()))
                    world.getSigns().add(Sign.fromString(str));
            }catch (Exception e){
                System.out.println("Error load map " + e);
                scan.close();
                reader.close();
            }
        }
        scan.close();
        reader.close();
    }

    public static void loadMan(MyWorld world) throws Exception{
        FileReader reader = new FileReader(new File(PATH));
        Scanner scan = new Scanner(reader);
        while(scan.hasNextLine()){
            try {
                String str = scan.nextLine();
                if(str.length() <= 1)
                    continue;
                if(str.contains(Man.class.getSimpleName()))
                    world.getMans().add(Man.fromString(str));
            }catch (Exception e){
                System.out.println("Error load man " + e);
                scan.close();
                reader.close();
            }
        }
        scan.close();
        reader.close();
    }

    public static void save(MyWorld world) throws Exception{
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(PATH));
            for (Block block: world.getBlocks())
                writer.write(block.toString());
            for (Sign sign: world.getSigns())
                writer.write(sign.toString());
            for (Man man: world.getMans())
                writer.write(man.toString());
        }catch (Exception e){
            System.out.println("save error "+e);
        }
        if(writer != null)
            writer.close();
    }
}
