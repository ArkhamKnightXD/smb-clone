package knight.arkham.helpers;

public class Constants {

//    Estas son las dimensiones que le dare a mi viewport. Estos valores son independientes de la resolución
//    especificada del juego. Esto quiere decir que esta será la resolución a la que mi juego se jugará
//    Si tengo 1280x720 de resolution. Mi ventana estará a esa resolution, pero mi juego solo ocupará lo especificado
//    en mis VIRTUAL_WIDTH y VIRTUAL_HEIGHT
    public static final float VIRTUAL_WIDTH = 400;
    public static final float VIRTUAL_HEIGHT = 208;

//    Con esta constante será que manejaré mi conversion de píxeles en el world de box2d. Todos los elementos que
//    yo vaya a agregar a mi world, su posición y dimensiones deben de ser divididos por esta constante.
    public static final float PIXELS_PER_METER = 100;

    //	Aqui definiremos las categorías de los fixture
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short ITEM_BIT = 256;
    public static final short MARIO_HEAD_BIT = 512;
}
