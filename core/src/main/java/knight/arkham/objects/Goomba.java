package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Goomba extends Enemy{

    private float stateTimer;

    private final Animation<TextureRegion> walkAnimation;


    public Goomba(GameScreen gameScreen, Vector2 position) {
        super(gameScreen, position);

        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = 0; i < 2; i++){

            animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas()
                    .findRegion("goomba"), i * 16, 0, 16, 16));
        }


        walkAnimation = new Animation<>(0.4f, animationFrames);

        animationFrames.clear();

        stateTimer = 0;

        setBounds(getX(), getY(), 16/PIXELS_PER_METER, 16/PIXELS_PER_METER);
    }

    public void update(float deltaTime){

        stateTimer += deltaTime;

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        setRegion(walkAnimation.getKeyFrame(stateTimer, true));
    }

    @Override
    protected void defineEnemy() {

        body = BodyHelper.createEnemyDynamicBody(new Box2DBody(new Vector2(32, 32), gameScreen.getWorld()));
    }
}
