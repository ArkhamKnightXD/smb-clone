package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Goomba extends Enemy{

    private float stateTimer;

    private final Animation<TextureRegion> walkAnimation;

    private boolean setToDestroy;
    private boolean destroyed;


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

        setToDestroy = false;
        destroyed = false;
    }

    // Esta es la forma correcta para destruir un body de nuestro world, pues no podemos simplemente destruirlo
    //  de una vez, pues causaría error por con las colisiones.
    private void destroyEnemy(){

        // Destruyo el body
        world.destroyBody(body);
        destroyed = true;

//            Cambio el sprite de mi goomba por el sprite de goomba aplastado.
        setRegion(new TextureRegion(gameScreen.getTextureAtlas()
                .findRegion("goomba"), 32, 0, 16, 16));

        stateTimer = 0;
    }

    public void update(float deltaTime){

        stateTimer += deltaTime;

        if (setToDestroy && !destroyed){

            destroyEnemy();
        }
//        Si mi goomba no ha sido destruido, que continue con su movimiento y sprite iguales.
        else if(!destroyed){

            body.setLinearVelocity(velocity);

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTimer, true));
        }
    }

    public void draw (Batch batch){

//        Con esto le indico a mi función draw que solo podrá dibujar el sprite del goomba siempre
//        y cuando el goomba no haya sido destruido o el stateTimer sea 0.
        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }

    @Override
    protected void defineEnemyBody() {

        body = BodyHelper.createEnemyBody(

                        new Box2DBody(new Vector2(getX(), getY()), gameScreen.getWorld()), this
                );
    }

    @Override
    public void hitOnHead() {

//        Si golpeamos este objeto indicaremos que este objeto debe de ser destruido.
        setToDestroy = true;
    }
}
