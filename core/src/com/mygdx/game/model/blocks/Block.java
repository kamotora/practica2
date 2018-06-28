package com.mygdx.game.model.blocks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Type;

public class Block{
    Vector2 position;
    int widht;
    int height;
    TypeBlock typeBlock;
    TypePosition typePosition;
    Color color;
    Rectangle figure;
    public Block(Vector2 position, TypeBlock typeBlock, TypePosition typePosition, int length) {
        this.position = position;
        this.typePosition = typePosition;
        this.typeBlock = typeBlock;
        int stroke = 0;
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

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getFigure() {
        return figure;
    }

    public Color getColor() {
        return color;
    }

    public TypeBlock getTypeBlock() {
        return typeBlock;
    }

    public TypePosition getTypePosition() {
        return typePosition;
    }

    @Override
    public String toString() {
        return "block "+typeBlock.name() + " " + widht + " " + height;
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(color);
        //и рисуем блоки
        renderer.rect(figure.x,figure.y,figure.width,figure.height);
    }
}
