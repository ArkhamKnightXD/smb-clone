package knight.arkham.sprites.tileObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import knight.arkham.scenes.Hud;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.player.Mario;
import static knight.arkham.helpers.Constants.BRICK_BIT;
import static knight.arkham.helpers.Constants.DESTROYED_BIT;

public class Brick extends InteractiveTileObject{

    private final AssetManager localAssetManager;


    public Brick(GameScreen gameScreen, TiledMap tiledMap, MapObject mapObject) {
        super(gameScreen, tiledMap, mapObject);

        localAssetManager = gameScreen.getAssetManager();

//        De esta forma guardo los datos de esta clase, para poder acceder a ella al momento de las colisiones
//        en la clase GameContactListener.
        fixture.setUserData(this);

        setCategoryFilter(BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {

//        Mario solo puede romper los bloques cuando es grande,
//        de lo contrario solo podrá golpearlos y sonara el sonido bump
        if (mario.isMarioIsBig()){

            //Cuando mario golpee el ladrillo preparamos el objeto con el DESTROYED_BIT, de esta forma ya mario no podrá
//        colisionar más con este objeto
            setCategoryFilter(DESTROYED_BIT);

//        De esta forma desaparecemos el sprite relacionado este brick.
            getCell().setTile(null);

            Hud.addScore(200);

            localAssetManager.get("audio/sound/breakBlock.wav", Sound.class).play();
        }

        else
            localAssetManager.get("audio/sound/bump.wav", Sound.class).play();
    }
}
