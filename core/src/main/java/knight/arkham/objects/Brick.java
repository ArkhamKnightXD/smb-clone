package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.scenes.Hud;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.BRICK_BIT;
import static knight.arkham.helpers.Constants.DESTROYED_BIT;

public class Brick extends InteractiveTileObject{

    private final AssetManager localAssetManager;


    public Brick(GameScreen gameScreen, TiledMap tiledMap, Rectangle bounds) {
        super(gameScreen.getWorld(), tiledMap, bounds);

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
