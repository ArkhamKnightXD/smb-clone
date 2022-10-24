package knight.arkham.sprites.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Turtle extends Enemy{

    private float stateTimer;

    private Animation<TextureRegion> walkAnimation;

    private boolean setToDestroy;
    private boolean destroyed;


    public Turtle(GameScreen gameScreen, Vector2 position) {

        super(gameScreen, position);

        createWalkAnimation(gameScreen);

        stateTimer = 0;

        setBounds(getX(), getY(), 16/ PIXELS_PER_METER, 16/ PIXELS_PER_METER);

        setToDestroy = false;
        destroyed = false;
    }

    private void createWalkAnimation(GameScreen gameScreen) {

        Array<TextureRegion> animationFrames = new Array<TextureRegion>();

        for (int i = 0; i < 2; i++) {

            animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas()
                    .findRegion("turtle"), i * 16, 0, 16, 16));
        }

        walkAnimation = new Animation<TextureRegion>(0.4f, animationFrames);
    }

    private void destroyEnemy() {

        // Destruyo el body
        world.destroyBody(body);
        destroyed = true;

//            Cambio el sprite de mi goomba por el sprite de goomba aplastado.
        setRegion(new TextureRegion(gameScreen.getTextureAtlas()
                .findRegion("turtle"), 64, 0, 16, 16));

        stateTimer = 0;
    }


    @Override
    protected void defineEnemyBody() {

        body = BodyHelper.createEnemyBody(

                new Box2DBody(new Vector2(getX(), getY()), true, gameScreen.getWorld(), this)
        );
    }

    @Override
    public void hitOnHead() {

        setToDestroy = true;

        gameScreen.getAssetManager().get("audio/sound/stomp.wav", Sound.class).play();
    }

    @Override
    public void update(float deltaTime) {

        stateTimer += deltaTime;

        if (setToDestroy && !destroyed)
            destroyEnemy();

        else if (!destroyed) {

            velocity.y = body.getLinearVelocity().y;

            body.setLinearVelocity(velocity);

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTimer, true));
        }
    }

    @Override
    public void draw(Batch batch) {

        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }
}
