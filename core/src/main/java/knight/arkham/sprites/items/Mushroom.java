package knight.arkham.sprites.items;

import com.badlogic.gdx.math.Vector2;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.Mario;

public class Mushroom extends Item {

    public Mushroom(GameScreen gameScreen, Vector2 position) {

        super(gameScreen, position);

        setRegion(gameScreen.getTextureAtlas().findRegion("mushroom"), 0, 0 ,16 ,16);

        velocity = new Vector2(0.7f, 0);
    }


    @Override
    public void defineItemBody() {

        body = BodyHelper.createItemBody(

                new Box2DBody(new Vector2(getX(), getY()), gameScreen.getWorld()), this
        );

    }

    @Override
    public void useItem(Mario mario) {

        setToDestroy = true;

// todo   Da null
        mario.growPlayer();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

//  Obtenemos la velocidad lineal de nuestro cuerpo y se la agregamos a la coordenada Y de nuestro vector velocidad.
        velocity.y = body.getLinearVelocity().y;

        body.setLinearVelocity(velocity);
    }
}
