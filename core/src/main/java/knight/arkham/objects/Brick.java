package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractiveTileObject{

    public Brick(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);

        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {

        Gdx.app.log("Brick", "Collision");
    }
}
