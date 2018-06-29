package com.mygdx.game.model.signs;

public enum TypeSign {AlarmButton, AntiFire, ExitSign, Fire, Smoke;
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.name());
        sb.append('}');
        return sb.toString();
    }
    public static TypeSign fromString(final String str){
        for(TypeSign typeSign : TypeSign.values())
            if (str.contains(typeSign.name()))
                return typeSign;
        return null;
    }
}
