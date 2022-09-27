package knight.arkham.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class InteractiveTileObject {

    protected final World world;
    protected final TiledMap tiledMap;
    protected final Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

//    Todas las clases que hereden de esta implementaran este constructor
    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.bounds = bounds;

        createTileObjectStaticBody(world, bounds);
    }

    private void createTileObjectStaticBody(World world, Rectangle bounds) {

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;

        bodyDefinition.position.set((bounds.x + bounds.width / 2) / PIXELS_PER_METER,
                (bounds.y + bounds.height / 2 )/ PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(bounds.width / 2 /PIXELS_PER_METER , bounds.height / 2 / PIXELS_PER_METER);

        body = world.createBody(bodyDefinition);

//        guardaré, el fixture, para poder acceder a este, en las demás clases, que heredan de esta
        fixture = body.createFixture(shape,0);
    }

//    Función encargada de preparar el filtro de categoría a mis distintos objetos.
    protected void setCategoryFilter(short filterBit){

        Filter filter = new Filter();
        filter.categoryBits = filterBit;

        fixture.setFilterData(filter);
    }

    protected TiledMapTileLayer.Cell getCell(){

// Obtengo el graphic layer
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Graphic Layer");

//        Debido a que la textura en un inicio tengo que dividirlas entre ppm ahora tengo que multiplicarla
//        para que tenga su posición original.
        return layer.getCell((int)(body.getPosition().x * PIXELS_PER_METER / 16),
                (int)(body.getPosition().y * PIXELS_PER_METER / 16));
    }

    public abstract void onHeadHit();
}
