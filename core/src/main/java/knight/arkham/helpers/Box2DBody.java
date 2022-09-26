package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Box2DBody {

//    Utilizaré un vector para guardar la posición en X y Y, para tener menos variables, y es recomendado esta forma
    public Vector2 position;

    public float width;
    public float height;
    public World world;
//    Defino el campo con la interfaz para poder enviar cualquier tipo de forma por el constructor
    public Shape shape;

    public Box2DBody(Vector2 position, float width, float height, World world) {

// Hago esto aqui para no tener que dividir a la hora de crear mi body. Es posible que generar otro
// vector2 sea algo innecesario, pero de esta forma automatizo más la creación de mis body2D. Ojo con esto
        this.position = new Vector2(position.x / PIXELS_PER_METER,position.y / PIXELS_PER_METER);
        this.width = width;
        this.height = height;
        this.world = world;
    }

    public Box2DBody(Vector2 position, World world, Shape shape) {

        this.position = new Vector2(position.x / PIXELS_PER_METER,position.y / PIXELS_PER_METER);
        this.world = world;
        this.shape = shape;
    }
}
