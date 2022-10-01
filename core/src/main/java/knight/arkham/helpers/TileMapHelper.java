package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import knight.arkham.objects.Brick;
import knight.arkham.objects.Coin;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/level1.tmx");

        parseMapObjectsToStaticBodies(tiledMap, "Ground");
        parseMapObjectsToStaticBodies(tiledMap, "Pipes");
        parseMapObjectsToStaticBodies(tiledMap, "Coins");
        parseMapObjectsToStaticBodies(tiledMap, "Bricks");

//        En el segundo elemento de la función indico la escala que va a tener el mapa
        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (RectangleMapObject mapObject : mapObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = mapObject.getRectangle();

// Mis objetos brick y coins deseo tenerlo en clases, para asi poder manejar su comportamiento cuando haya colisión.
            if (objectsName.equals("Bricks"))
                new Brick(gameScreen.getWorld(), tiledMap, rectangle, gameScreen.getAssetManager());

            else if (objectsName.equals("Coins"))
                new Coin(gameScreen.getWorld(), tiledMap, rectangle, gameScreen.getAssetManager());

//            Los demás objetos los crearé libremente.
            else {

                BodyHelper.createStaticBody(

                        new Box2DBody(new Vector2(rectangle.x + rectangle.width / 2,
                                rectangle.y + rectangle.height / 2), rectangle.width,
                                rectangle.height, gameScreen.getWorld()
                        )
                );
            }
        }
    }

}
