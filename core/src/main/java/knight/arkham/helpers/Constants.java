package knight.arkham.helpers;

import knight.arkham.MarioBros;

public class Constants {

//    Estas son las dimensiones que le dare a mi viewport
    public static final float VIRTUAL_WIDTH = 400;
    public static final float VIRTUAL_HEIGHT = 208;

//    Con esta constante sera que manejare mi conversion de pixeles en el world de box2d
    public static final float PIXELS_PER_METER = 100;
    public static final int FULL_SCREEN_HEIGHT = MarioBros.INSTANCE.getScreenHeight();
    public static final int FULL_SCREEN_WIDTH = MarioBros.INSTANCE.getScreenWidth();
    public static final int MID_SCREEN_HEIGHT = MarioBros.INSTANCE.getScreenHeight() / 2;
    public static final int MID_SCREEN_WIDTH = MarioBros.INSTANCE.getScreenWidth() / 2;
}
