package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap;

        tiledMap = new TmxMapLoader().load("maps/level1.tmx");

        MapObjects groundObjects = tiledMap.getLayers().get("Ground").getObjects();
        MapObjects pipeObjects = tiledMap.getLayers().get("Pipes").getObjects();
        MapObjects coinObjects = tiledMap.getLayers().get("Coins").getObjects();
        MapObjects brickObjects = tiledMap.getLayers().get("Bricks").getObjects();

        parseMapObjectsToStaticBodies(groundObjects);
        parseMapObjectsToStaticBodies(pipeObjects);
        parseMapObjectsToStaticBodies(coinObjects);
        parseMapObjectsToStaticBodies(brickObjects);

//        En el segundo parametro indicaremos la escala que tendra el mapa
        return new OrthogonalTiledMapRenderer(tiledMap, 1/ PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(MapObjects mapObjects) {

        for (RectangleMapObject mapObject : mapObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = mapObject.getRectangle();

            BodyHelper.createStaticBody(

                    new Box2DBody(
                            rectangle.x + rectangle.width / 2 , rectangle.y + rectangle.height / 2,
                            rectangle.getWidth(), rectangle.getHeight(), 100, gameScreen.getWorld()
                    )
            );
        }
    }

}
