package knight.arkham.sprites.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import knight.arkham.scenes.Hud;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.BRICK_BIT;
import static knight.arkham.helpers.Constants.DESTROYED_BIT;

public class Brick extends InteractiveTileObject{

    private final AssetManager localAssetManager;


    public Brick(GameScreen gameScreen, TiledMap tiledMap, RectangleMapObject mapObject) {
        super(gameScreen, tiledMap, mapObject);

//        De esta forma guardo los datos de esta clase, para poder acceder a ella al momento de las colisiones
//        en la clase GameContactListener.
        fixture.setUserData(this);

        setCategoryFilter(BRICK_BIT);

        localAssetManager = gameScreen.getAssetManager();
    }

    @Override
    public void onHeadHit() {

        Gdx.app.log("Brick", "Collision");

//        Cuando mario golpee el ladrillo preparamos el objeto con el DESTROYED_BIT, de esta forma ya mario no podrá
//        colisionar más con este objeto
        setCategoryFilter(DESTROYED_BIT);

//        De esta forma desaparecemos el sprite relacionado este brick.
        getCell().setTile(null);

        Hud.addScore(200);

        localAssetManager.get("audio/sound/breakBlock.wav", Sound.class).play();
    }
}
