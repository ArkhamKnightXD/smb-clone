package knight.arkham.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.player.Mario;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class InteractiveTileObject {

    protected GameScreen gameScreen;
    protected final World world;
    protected final TiledMap tiledMap;
    protected final Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    protected MapObject mapObject;

    //    Todas las clases que hereden de esta implementaran este constructor
    public InteractiveTileObject(GameScreen gameScreen, TiledMap tiledMap, MapObject mapObject) {

        this.mapObject = mapObject;
        this.gameScreen = gameScreen;
        world = gameScreen.getWorld();
        this.tiledMap = tiledMap;
        bounds = ((RectangleMapObject) mapObject).getRectangle();

        fixture = BodyHelper.createStaticBody(

                new Box2DBody(new Vector2(bounds.x + bounds.width / 2,
                        bounds.y + bounds.height / 2), bounds.width, bounds.height, world
                )
        );

//        De esta forma obtengo el body desde el fixture.
        body = fixture.getBody();
    }


    //    Función encargada de preparar el filtro de categoría a mis distintos objetos.
    protected void setCategoryFilter(short filterBit) {

        Filter filter = new Filter();
        filter.categoryBits = filterBit;

        fixture.setFilterData(filter);
    }

    protected TiledMapTileLayer.Cell getCell() {

// Obtengo el graphic layer
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Graphic Layer");

//        Debido a que la textura en un inicio tengo que dividirlas entre ppm ahora tengo que multiplicarla
//        para que tenga su posición original.
        return layer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
                (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }

    public abstract void onHeadHit(Mario mario);
}
