package knight.arkham.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;

public abstract class InteractiveTileObject {

    protected final World world;
    protected final TiledMap tiledMap;
//    protected TiledMapTile tiledMapTile;
    protected final Rectangle bounds;
//    protected Body body;

//    Todas las clases que hereden de esta implementaran este constructor
    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.bounds = bounds;

//        Todas las clases que heredan crean su cuerpo de la misma manera
        BodyHelper.createStaticBody(

                new Box2DBody(
                        bounds.x + bounds.width / 2 , bounds.y + bounds.height / 2,
                        bounds.getWidth(), bounds.getHeight(), world
                )
        );
    }
}
