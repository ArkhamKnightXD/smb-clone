package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.sprites.enemies.Enemy;
import knight.arkham.sprites.enemies.Turtle;
import knight.arkham.sprites.tileObjects.Brick;
import knight.arkham.sprites.tileObjects.Coin;
import knight.arkham.sprites.enemies.Goomba;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapCreator {

    private final GameScreen localGameScreen;
    private final Array<Goomba> goombas;
    private final Array<Turtle> turtles;

    public TileMapCreator(GameScreen gameScreen) {

        localGameScreen = gameScreen;

        goombas = new Array<Goomba>();
        turtles = new Array<Turtle>();
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/level1.tmx");

        parseMapObjectsToStaticBodies(tiledMap, "Ground");
        parseMapObjectsToStaticBodies(tiledMap, "Pipes");
        parseMapObjectsToStaticBodies(tiledMap, "Coins");
        parseMapObjectsToStaticBodies(tiledMap, "Bricks");
        parseMapObjectsToStaticBodies(tiledMap, "Goombas");
        parseMapObjectsToStaticBodies(tiledMap, "Turtles");

//        En el segundo elemento de la función indico la escala que va a tener el mapa
        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();


// Mis objetos brick y coins deseo tenerlo en clases, para asi poder manejar su comportamiento cuando haya colisión.
            switch (objectsName) {

// Para tener un mayor manejo de mis bricks y coin, voy a enviarle el mapObject, de esta forma podre controlar
// de una mejor forma que item contendrán mis clases coin.
                case "Bricks":
                    new Brick(localGameScreen, tiledMap, mapObject);
                    break;

                case "Coins":
                    new Coin(localGameScreen, tiledMap, mapObject);
                    break;

                case "Goombas":
                    goombas.add(new Goomba(localGameScreen, new Vector2(rectangle.x, rectangle.y)));
                    break;

                case "Turtles":
                    turtles.add(new Turtle(localGameScreen, new Vector2(rectangle.x, rectangle.y)));
                    break;

//            Los demás objetos los crearé libremente.
                default:

                    Box2DBodyCreator.createStaticBody(

                            new Box2DBody(

                                    new Vector2(rectangle.x + rectangle.width / 2,
                                            rectangle.y + rectangle.height / 2), rectangle.width,
                                            rectangle.height, localGameScreen.getWorld()
                            )
                    );

                    break;
            }
        }
    }

    public Array<Enemy> getEnemies() {

        Array<Enemy> enemies = new Array<Enemy>();

        enemies.addAll(goombas);
        enemies.addAll(turtles);

        return enemies;
    }
}
