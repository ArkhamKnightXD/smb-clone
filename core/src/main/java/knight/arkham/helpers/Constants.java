package knight.arkham.helpers;

public class Constants {

//    Estas son las dimensiones que le dare a mi viewport
    public static final float VIRTUAL_WIDTH = 400;
    public static final float VIRTUAL_HEIGHT = 208;

//    Con esta constante sera que manejare mi conversion de pixeles en el world de box2d
//    Todo debo repasar la matemática detrás del ppm pues no lo entiendo completamente.
    public static final float PIXELS_PER_METER = 100;

    //	Aqui definiremos las categorías de los fixture
    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
}
