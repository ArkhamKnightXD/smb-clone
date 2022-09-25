package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.scenes.Hud;

import static knight.arkham.helpers.Constants.COIN_BIT;

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileSet;

    private final AssetManager localAssetManager;

    public Coin(World world, TiledMap tiledMap, Rectangle bounds, AssetManager assetManager) {
        super(world, tiledMap, bounds);

//        Buscamos nuestro tileSet completo
        tileSet = tiledMap.getTileSets().getTileSet("tileset_gutter");

        fixture.setUserData(this);

        setCategoryFilter(COIN_BIT);

        localAssetManager = assetManager;
    }

    @Override
    public void onHeadHit() {

        //Cuando tenemos el tileset y hacemos clic sobre uno de estos elementos, podemos ver que este elemento tiene
        // un ID, este, id del elemento que necesitamos en este caso es 27 en tiledMap, debido a que en tiledMap
        // se empieza a contar desde 0, pero el tileSet empieza desde 1 por esta razón este elemento es 28 en vez de 27.
        int BLANK_COIN = 28;

        getCell().setTile(tileSet.getTile(BLANK_COIN));

        Hud.addScore(100);

//        Hay 2 sonidos que podemos tocar, uno cuando hay un coin disponible y otro cuando el bloque esta vació.
        if(getCell().getTile().getId() == BLANK_COIN)
            localAssetManager.get("audio/sound/bump.wav", Sound.class).play();

        else
            localAssetManager.get("audio/sound/coin.wav", Sound.class).play();


        Gdx.app.log("Coin", "Collision");
    }
}
