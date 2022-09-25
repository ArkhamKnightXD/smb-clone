package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.MarioBros;
import knight.arkham.scenes.Hud;

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileSet;
//    Cuando tenemos el tileset y hacemos click sobre uno de estos elementos, podemos ver que este elemento tiene un id
//    El id del elemento que necesitamos en este caso es 27 en tiledMap, debido a que en tiledmap se empieza
//    a contar desde 0, pero el tileSet empieza desde 1 por esta razon este elemento es 28 en vez de 27
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);

//        Buscamos nuestro tileSet completo
        tileSet = tiledMap.getTileSets().getTileSet("tileset_gutter");

        fixture.setUserData(this);

        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit() {

        getCell().setTile(tileSet.getTile(BLANK_COIN));

        Hud.addScore(100);

//        Hay 2 sonidos que podemos tocar, uno cuando hay un coin disponible y otro cuando el bloque esta vacio
        if(getCell().getTile().getId() == BLANK_COIN)
            MarioBros.assetManager.get("audio/sound/bump.wav", Sound.class).play();

        else
            MarioBros.assetManager.get("audio/sound/coin.wav", Sound.class).play();


        Gdx.app.log("Coin", "Collision");
    }
}
