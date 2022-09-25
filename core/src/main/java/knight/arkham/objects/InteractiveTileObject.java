package knight.arkham.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.*;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class InteractiveTileObject {

    protected final World world;
    protected final TiledMap tiledMap;
//    protected TiledMapTile tiledMapTile;
    protected final Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

//    Todas las clases que hereden de esta implementaran este constructor
    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.bounds = bounds;

//        Todas las clases que heredan crean su cuerpo de la misma manera, guardare, el fixture, para poder
//        acceder a este, en las demas clases, que heredan de esta


        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;

        bodyDefinition.position.set((bounds.x + bounds.width / 2) / PIXELS_PER_METER,
                (bounds.y + bounds.height / 2 )/ PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(bounds.width / 2 /PIXELS_PER_METER , bounds.height / 2 / PIXELS_PER_METER);

        body = world.createBody(bodyDefinition);

// A static body has zero mass by definition, so the density is not used in this case.  The default density is zero.
        fixture = body.createFixture(shape,0);


//        fixture = BodyHelper.createStaticBody(
//
//                new Box2DBody(
//                        bounds.x + bounds.width / 2 , bounds.y + bounds.height / 2,
//                        bounds.getWidth(), bounds.getHeight(), world
//                )
//        );
    }


    public abstract void onHeadHit();

//    Metodo encargado de setear el filtro de categoria a mis distintos objetos
    public void setCategoryFilter(short filterBit){

        Filter filter = new Filter();
        filter.categoryBits = filterBit;

        fixture.setFilterData(filter);
    }

    //Nota esta funcion de abajo me da error debido a que el body nunca se inicializa y por eso es null
    public TiledMapTileLayer.Cell getCell(){

//        Obtengo el graphic layer que esta en el index 1 de tiledmap
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
//        Debido que la textura en un inicio tengo que dividirlas entre ppm ahora tengo que multiplicarla
//        para que tenga su pos original, debo repasar la matematica detras del ppm pues no lo entiendo completamente
        return layer.getCell((int)(body.getPosition().x * PIXELS_PER_METER / 16),
                (int)(body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
