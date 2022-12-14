package knight.arkham.sprites.tileObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import knight.arkham.helpers.Box2DBodyCreator;
import knight.arkham.scenes.Hud;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.player.Mario;
import knight.arkham.sprites.items.ItemDefinition;
import knight.arkham.sprites.items.Mushroom;
import static knight.arkham.helpers.Constants.COIN_BIT;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Coin extends InteractiveTileObject{

    private final TiledMapTileSet tileSet;

    private final AssetManager localAssetManager;

    public Coin(GameScreen gameScreen, TiledMap tiledMap, MapObject mapObject) {
        super(gameScreen, tiledMap, mapObject);

        localAssetManager = gameScreen.getAssetManager();

//        Buscamos nuestro tileSet completo
        tileSet = tiledMap.getTileSets().getTileSet("tileset_gutter");

        fixture.setUserData(this);

        setCategoryFilter(COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {

        //Cuando tenemos el tileset y hacemos clic sobre uno de estos elementos, podemos ver que este elemento tiene
        // un ID, este, id del elemento que necesitamos en este caso es 27 en tiledMap, debido a que en tiledMap
        // se empieza a contar desde 0, pero el tileSet empieza desde 1 por esta razón este elemento es 28 en vez de 27.
        int BLANK_COIN = 28;

//        Hay 2 sonidos que podemos tocar, uno cuando hay un coin disponible y otro cuando el bloque esta vació.
//        Comparo él, id actual del tile si es igual a un Blank_coin significa que el tile esta vació.
        if(getCell().getTile().getId() == BLANK_COIN)
            localAssetManager.get("sound/bump.wav", Sound.class).play();

        else{
//            Con getProperties obtengo las propiedades personalizadas del mapObject utilizado para crear esta clase.
//            Si este mapObject contiene en sus propiedades un key llamado mushroom entonces agregaré un mushroom.
            if (mapObject.getProperties().containsKey("mushroom")){

                Vector2 objectCurrentPosition = Box2DBodyCreator.getSimplifiedCurrentPosition(body);


                // Spawn a coin when the block is hit. Deseo que el coin aparezca justo encima de mi block por eso el +16
                gameScreen.spawnItems(new ItemDefinition(new Vector2(objectCurrentPosition.x,
                        objectCurrentPosition.y +16 / PIXELS_PER_METER), Mushroom.class));

                localAssetManager.get("sound/spawn.wav", Sound.class).play();
            }

            else
                localAssetManager.get("sound/coin.wav", Sound.class).play();

        }

//        Al final actualizo el tile por el tile con él, id de blank coin.
        getCell().setTile(tileSet.getTile(BLANK_COIN));

        Hud.addScore(100);
    }
}
