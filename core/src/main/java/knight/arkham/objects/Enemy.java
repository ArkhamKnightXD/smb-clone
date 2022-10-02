package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.screens.GameScreen;

public abstract class Enemy extends Sprite {

    protected GameScreen gameScreen;
    protected World world;

    protected Body body;

    public Enemy(GameScreen gameScreen, Vector2 position) {

        this.gameScreen = gameScreen;
        world = gameScreen.getWorld();

//        Como indico la posición mediante esta función, puedo utilizar las funciones getX and GetY en otros lugares.
        setPosition(position.x, position.y);

        defineEnemyBody();
    }

    protected abstract void defineEnemyBody();
    public abstract void hitOnHead();
}
