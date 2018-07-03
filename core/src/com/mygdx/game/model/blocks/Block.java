package com.mygdx.game.model.blocks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Block{
    //положение блока
    Vector2 position;
    //размеры блока
    int widht;
    int height;
    //тип блока
    TypeBlock typeBlock;
    //тип расположение блока
    TypePosition typePosition;
    //цвет
    Color color;
    //представление блока в виде прямоугольника
    Rectangle figure;
    public Block(Vector2 position, TypeBlock typeBlock, TypePosition typePosition, int length) {
        this.position = position;
        this.typePosition = typePosition;
        this.typeBlock = typeBlock;
        //толщина
        int stroke = 0;
        //Создаем блок в зависимотси от параметров
        if(typeBlock == TypeBlock.EXIT){
            color = Color.WHITE;
            stroke = 5;
        } else if(typeBlock == TypeBlock.WALL){
            color = Color.BLACK;
            stroke = 10;
        }else if(typeBlock == TypeBlock.WINDOW) {
            color = Color.BLACK;
            stroke = 1;
        }
        if(typePosition == TypePosition.HORIZONTAL){
            height = stroke;
            widht = length;
        }else if(typePosition == TypePosition.VERTICAL){
            widht = stroke;
            height = length;
        }
        figure = new Rectangle(position.x,position.y,widht,height);
    }

    /*
     * Рисуемся
     */
    public void draw(ShapeRenderer renderer){
        renderer.setColor(color);
        //и рисуем блоки
        renderer.rect(figure.x,figure.y,figure.width,figure.height);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Block{");
        sb.append("position=").append(position);
        sb.append(", widht=").append(widht);
        sb.append(", height=").append(height);
        sb.append(", typeBlock=").append(typeBlock);
        sb.append(", typePosition=").append(typePosition);
        sb.append('}');
        sb.append('\n');
        return sb.toString();
    }


    public static Block fromString(String str){
        int posStart = str.indexOf("position=", 1) + 9;
        int posFinish = str.indexOf("),", posStart)+1;
        int widhtStart = str.indexOf("widht=", 1) + 6;
        int widthFinish = str.indexOf(',', widhtStart);
        int heightStart = str.indexOf("height=", 1) + 7;
        int heightFinish = str.indexOf(',', heightStart);
        int typeBlockStart = str.indexOf("typeBlock=", 1) + 10;
        int typeBlockFinish = str.indexOf(',', typeBlockStart);
        int typePositionStart = str.indexOf("typePosition=", 1) + 13;
        int typePositionFinish = str.indexOf('}', typePositionStart);
        try{
            Vector2 vector = new Vector2();
            vector.fromString(str.substring(posStart,posFinish));
            TypeBlock typeBlock = TypeBlock.fromString(str.substring(typeBlockStart,typeBlockFinish));
            TypePosition typePosition = TypePosition.fromString(str.substring(typePositionStart,typePositionFinish));
            int width = Integer.parseInt(str.substring(widhtStart,widthFinish));
            int height = Integer.parseInt(str.substring(heightStart,heightFinish));
            int length = (typePosition == TypePosition.VERTICAL) ? height : width;
            return new Block(vector,typeBlock,typePosition,length);
        }
        catch (Exception e){
            System.out.println("block error");
        }
        return null;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getFigure() {
        return figure;
    }

    public TypeBlock getTypeBlock() {
        return typeBlock;
    }

    public TypePosition getTypePosition() {
        return typePosition;
    }

}
