package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

    public float xPosition;
    public float yPosition;
    public float width;
    public float height;
    public boolean isStatic;
    public float density;
    public World world;
    public PolygonShape polygonShape;


//    Constructor completo para cuerpo mas completos
    public Box2DBody(float xPosition, float yPosition, float width, float height,
                     boolean isStatic, float density, World world) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
        this.density = density;
        this.world = world;
    }

//    Constructor mas simple para crear cuerpos para elementos del mapa
    public Box2DBody(boolean isStatic, float density, World world, PolygonShape polygonShape) {
        this.isStatic = isStatic;
        this.density = density;
        this.world = world;
        this.polygonShape = polygonShape;
    }
}
