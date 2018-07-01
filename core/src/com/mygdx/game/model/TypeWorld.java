package com.mygdx.game.model;

public enum  TypeWorld {END,LIVE, MAYBE,FIRE, WIN, ALLSAVE, WINANDDEAD;

    @Override
    public String toString() {
        String str = null;
        if(this == WIN)
            str = "Пожар потушен. Никто не умер. Люди-герои!";
        if(this == END)
            str = "Все люди погибли.Стоит лучше поработать над пожарной безопасностью";
        if(this == TypeWorld.LIVE)
            str = "Всё спокойно";
        if(this == TypeWorld.FIRE)
            str = "Пожар!";
        if(this == TypeWorld.MAYBE)
            str = "Умерли, но не все. Неплохо";
        if(this == TypeWorld.ALLSAVE)
            str = "Все спаслись. Пожарная безопасность на высоте!";
        if(this == TypeWorld.WINANDDEAD)
            str = "Есть люди герои, которые ценой своей жизни ликвидировали пожар.";
        if(str == null)
            System.out.println("error TypeWorld");
        return str;
    }
}
