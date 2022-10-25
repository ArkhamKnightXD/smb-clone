package knight.arkham.sprites.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.player.Mario;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class Item extends Sprite {

    protected GameScreen gameScreen;
    protected World world;
    protected Vector2 velocity;
    protected boolean setToDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(GameScreen gameScreen, Vector2 position) {

        this.gameScreen = gameScreen;
        world = gameScreen.getWorld();

        setPosition(position.x, position.y);

        setBounds(getX(), getY(), 16/ PIXELS_PER_METER, 16/ PIXELS_PER_METER);

        setToDestroy = false;
        destroyed = false;

        defineItemBody();
    }


    @Override
    public void draw(Batch batch) {

        if (!destroyed)
            super.draw(batch);
    }


    public void update(float deltaTime){

        if (setToDestroy && !destroyed) {

            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void reverseVelocityOnXAxis(){

        velocity.x = -velocity.x;
    }


    public abstract void defineItemBody();
    public abstract void useItem(Mario mario);
}
