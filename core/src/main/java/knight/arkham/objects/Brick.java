package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.MarioBros;

public class Brick extends InteractiveTileObject{

    public Brick(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);

        fixture.setUserData(this);

        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {

        Gdx.app.log("Brick", "Collision");

//        Cuando mario golpee el ladrillo seteamos el objeto con el DESTROYED_BIT, de esta forma ya mario no podra
//        colisionar mas con este objeto
        setCategoryFilter(MarioBros.DESTROYED_BIT);

//        De esta forma desaparecemos el sprite relacionado a mi brick
        getCell().setTile(null);
    }
}
