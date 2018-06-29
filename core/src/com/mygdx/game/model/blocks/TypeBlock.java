package com.mygdx.game.model.blocks;
/*
 * Тип блока
 */
public enum TypeBlock {WINDOW, WALL, EXIT;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder('{');
        sb.append(this.name());
        sb.append('}');
        return sb.toString();
    }
    public static TypeBlock fromString(final String str){
        for(TypeBlock typeBlock : TypeBlock.values())
            if (str.contains(typeBlock.name()))
                return typeBlock;
            return null;
    }
}
