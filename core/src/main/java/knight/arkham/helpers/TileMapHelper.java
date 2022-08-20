package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }


    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap;

        //cargo el mapa
        tiledMap = new TmxMapLoader().load("maps/level1.tmx");

//        MapObjects collisionObjects = tiledMap.getLayers().get("collisions").getObjects();

//        parseMapObjectsToStaticBodies(collisionObjects);

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

//    private void parseMapObjectsToStaticBodies(MapObjects mapObjects) {
//
//        for (MapObject mapObject : mapObjects) {
//
//            if (mapObject instanceof PolygonMapObject)
//                createStaticBody(((PolygonMapObject) mapObject));
//
//            if (mapObject instanceof RectangleMapObject)
//                createPlayer(mapObject);
//
//        }
//    }

//    private void createPlayer(MapObject mapObject) {
//
//        Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
//
//        String rectangleName = mapObject.getName();
//
//        if (rectangleName.equals("player")) {
//
//            Body playerBody = BodyHelper.createBody(
//
//                    new Box2DBody(
//                            rectangle.getX() + rectangle.getWidth() / 2,
//                            rectangle.getY() + rectangle.getHeight() / 2,
//                            rectangle.getWidth(), rectangle.getHeight(),
//                            false, 1000, gameScreen.getWorld()
//                    )
//            );
//
//            gameScreen.setPlayer(new Player(playerBody, rectangle.getWidth(), rectangle.getHeight()));
//        }
//    }


//    private void createStaticBody(PolygonMapObject mapObject) {
//
//        PolygonShape shape = createPolygonShape(mapObject);
//
//        BodyHelper.createStaticBody(new Box2DBody(true, 1000, gameScreen.getWorld(), shape));
//
//        shape.dispose();
//    }

    private PolygonShape createPolygonShape(PolygonMapObject polygonMapObject) {

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {

            Vector2 current = new Vector2(vertices[i * 2] / PIXELS_PER_METER, vertices[i * 2 + 1] / PIXELS_PER_METER);

            worldVertices[i] = current;
        }

        PolygonShape polygonShape = new PolygonShape();

        polygonShape.set(worldVertices);

        return polygonShape;
    }
}
