package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.sprites.tileObjects.Brick;
import knight.arkham.sprites.tileObjects.Coin;
import knight.arkham.sprites.enemies.Goomba;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;
    private final Array<Goomba> goombas;

    public TileMapHelper(GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        goombas = new Array<>();
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/level1.tmx");

        parseMapObjectsToStaticBodies(tiledMap, "Ground");
        parseMapObjectsToStaticBodies(tiledMap, "Pipes");
        parseMapObjectsToStaticBodies(tiledMap, "Coins");
        parseMapObjectsToStaticBodies(tiledMap, "Bricks");
        parseMapObjectsToStaticBodies(tiledMap, "Goombas");

//        En el segundo elemento de la función indico la escala que va a tener el mapa
        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (RectangleMapObject mapObject : mapObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = mapObject.getRectangle();

// Mis objetos brick y coins deseo tenerlo en clases, para asi poder manejar su comportamiento cuando haya colisión.
            switch (objectsName) {

                case "Bricks":
                    new Brick(gameScreen, tiledMap, rectangle);
                    break;
                case "Coins":
                    new Coin(gameScreen, tiledMap, rectangle);
                    break;
                case "Goombas":
                    goombas.add(new Goomba(gameScreen, new Vector2(rectangle.x, rectangle.y)));
                    break;

//            Los demás objetos los crearé libremente.
                default:

                    BodyHelper.createStaticBody(

                            new Box2DBody(new Vector2(rectangle.x + rectangle.width / 2,
                                    rectangle.y + rectangle.height / 2), rectangle.width,
                                    rectangle.height, gameScreen.getWorld()
                            )
                    );
                    break;
            }
        }
    }

    public Array<Goomba> getGoombas() {return goombas;}
}
