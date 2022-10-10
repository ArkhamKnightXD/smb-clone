package knight.arkham.sprites.items;

import com.badlogic.gdx.math.Vector2;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

public class Mushroom extends Item {

    public Mushroom(GameScreen gameScreen, Vector2 position) {

        super(gameScreen, position);

        setRegion(gameScreen.getTextureAtlas().findRegion("mushroom"), 0, 0 ,16 ,16);

        velocity = new Vector2(0, 0);
    }

//    Talvez lo que falla esta aqui.
    @Override
    public void defineItemBody() {

        body = BodyHelper.createItemBody(

                new Box2DBody(new Vector2(getX(), getY()), gameScreen.getWorld()), this
        );

    }

    @Override
    public void useItem() {

        destroyObject();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        body.setLinearVelocity(velocity);
    }
}
