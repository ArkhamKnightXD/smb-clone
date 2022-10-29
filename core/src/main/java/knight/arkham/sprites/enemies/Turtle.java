package knight.arkham.sprites.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.Box2DBodyCreator;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Turtle extends Enemy {

    private TurtleAnimationState previousState;
    private TurtleAnimationState currentState;
    private final TextureRegion shell;

    private float stateTimer;

    private Animation<TextureRegion> walkAnimation;

    private final boolean destroyed;


    public Turtle(GameScreen gameScreen, Vector2 position) {

        super(gameScreen, position);

        createWalkingAnimation(gameScreen);

        previousState = TurtleAnimationState.WALKING;
        currentState = TurtleAnimationState.WALKING;

//        Sprite de la tortuga escondida en el caparazón.
        shell = new TextureRegion(gameScreen.getTextureAtlas().findRegion("turtle"), 64, 0, 16, 24);

        setBounds(getX(), getY(), 16 / PIXELS_PER_METER, 24 / PIXELS_PER_METER);

        destroyed = false;

        stateTimer = 0;
    }


    private void createWalkingAnimation(GameScreen gameScreen) {

        Array<TextureRegion> animationFrames = new Array<TextureRegion>();

        for (int i = 0; i < 2; i++) {

            animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas()
                    .findRegion("turtle"), i * 16, 0, 16, 24));
        }

        walkAnimation = new Animation<TextureRegion>(0.2f, animationFrames);
    }


    @Override
    protected void defineEnemyBody() {

        body = Box2DBodyCreator.createEnemyBody(

                new Box2DBody(new Vector2(getX(), getY()), gameScreen.getWorld(), this)
        );
    }

    @Override
    public void hitOnHead() {

        if (currentState != TurtleAnimationState.SHELL) {

            currentState = TurtleAnimationState.SHELL;
            velocity.x = 0;
        }

        gameScreen.getAssetManager().get("audio/sound/stomp.wav", Sound.class).play();
    }

    @Override
    public void update(float deltaTime) {

        setRegion(getActualRegion(deltaTime));

        if (currentState == TurtleAnimationState.SHELL && stateTimer > 5) {

            currentState = TurtleAnimationState.WALKING;

            velocity.x = 1;
        }

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - 8 / PIXELS_PER_METER);

        velocity.y = body.getLinearVelocity().y;

        body.setLinearVelocity(velocity);
    }

    private TextureRegion getActualRegion(float deltaTime) {

        TextureRegion actualRegion;

        switch (currentState) {

            case SHELL:
                actualRegion = shell;

                break;

            case WALKING:
            default:
                actualRegion = walkAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        flipOnXAxis(actualRegion);

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return actualRegion;
    }

    private void flipOnXAxis(TextureRegion region) {

        if (velocity.x > 0 && !region.isFlipX())
            region.flip(true, false);

        else if (velocity.x < 0 && region.isFlipX())
            region.flip(true, false);

    }


    @Override
    public void draw(Batch batch) {

        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }
}
