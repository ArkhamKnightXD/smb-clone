package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

//    Utilizaré un vector para guardar la posición en X e Y, para tener menos variables, y es recomendado esta forma
    public Vector2 position;
    public float width;
    public float height;
    public World world;

//    Agregando objeto generic donde guardaré la mi userData. Indico esto de una forma generica, pues el userData
//    Varía y, por lo tanto, es una clase distinta para cada objeto.
    public Object userData;


    public Box2DBody(Vector2 position, float width, float height, World world) {

        this.position = position;
        this.width = width;
        this.height = height;
        this.world = world;
    }

    public Box2DBody(Vector2 position, World world, Object userData) {

        this.position = position;
        this.world = world;
        this.userData = userData;
    }
}
