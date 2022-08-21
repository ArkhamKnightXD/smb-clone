package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

    public float xPosition;
    public float yPosition;
    public float width;
    public float height;
    public float density;
    public World world;
//    Seteo el campo con la interfaz para poder enviar cualquier tipo de forma por el constructor
    public Shape shape;

    public Box2DBody(float xPosition, float yPosition, float width, float height, float density, World world) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.density = density;
        this.world = world;
    }

    public Box2DBody(float xPosition, float yPosition, float density, World world, Shape shape) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.density = density;
        this.world = world;
        this.shape = shape;
    }
}
