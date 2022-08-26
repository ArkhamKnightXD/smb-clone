package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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

//        En el segundo parametro indicaremos la escala que tendra el mapa
        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (RectangleMapObject mapObject : mapObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = mapObject.getRectangle();

// Mis objectos brick y coins deseo tenerlo en clases, para asi poder manejar su comportamiento cuando haya colision
            if (objectsName.equals("Bricks"))
                new Brick(gameScreen.getWorld(), tiledMap, rectangle);

            else if (objectsName.equals("Coins"))
                new Coin(gameScreen.getWorld(), tiledMap, rectangle);

            else {

                BodyHelper.createStaticBody(

                        new Box2DBody(
                                rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2,
                                rectangle.getWidth(), rectangle.getHeight(), 100, gameScreen.getWorld()
                        )
                );
            }
        }
    }

}
