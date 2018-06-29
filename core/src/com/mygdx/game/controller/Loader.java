package com.mygdx.game.controller;

import com.mygdx.game.model.MyWorld;
import com.mygdx.game.model.blocks.Block;
import com.mygdx.game.model.signs.Sign;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Loader {
    final static String PATH  = System.getProperty("user.dir") + "\\core\\assets\\config.conf";

    public static void load(MyWorld world) throws Exception{
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
                System.out.println("Error");
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
        }catch (Exception e){
            System.out.println("save error "+e);
        }
        if(writer != null)
            writer.close();
    }
}
