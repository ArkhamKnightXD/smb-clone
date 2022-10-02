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

    protected Vector2 velocity;

    public Enemy(GameScreen gameScreen, Vector2 position) {

        this.gameScreen = gameScreen;
        world = gameScreen.getWorld();

//        Como indico la posición mediante esta función, puedo utilizar las funciones getX and GetY en otros lugares.
        setPosition(position.x, position.y);

        defineEnemyBody();

        velocity = new Vector2(1,0);
    }

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;

        if (y)
            velocity.y = -velocity.y;
    }

    protected abstract void defineEnemyBody();
    public abstract void hitOnHead();
}
