package com.mygdx.game.model.blocks;
/*
 * Расположение блока блока
 */
public enum TypePosition {VERTICAL, HORIZONTAL;
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.name());
        sb.append('}');
        return sb.toString();
    }
    public static TypePosition fromString(final String str){
        for(TypePosition typePosition : TypePosition.values())
            if (str.contains(typePosition.name()))
                return typePosition;
        return null;
    }
}
