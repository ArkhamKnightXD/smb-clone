package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

//    Utilizaré un vector para guardar la posición en X y Y, para tener menos variables, y es recomendado esta forma
    public Vector2 position;

    public float width;
    public float height;
    public World world;
//    Defino el campo con la interfaz para poder enviar cualquier tipo de forma por el constructor
    public Shape shape;

    public Box2DBody(Vector2 position, float width, float height, World world) {

        this.position = position;
        this.width = width;
        this.height = height;
        this.world = world;
    }

    public Box2DBody(Vector2 position, World world, Shape shape) {

        this.position = position;
        this.world = world;
        this.shape = shape;
    }
}
