package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//La clase sprite nos hereda un conjunto de funcionalidades encargadas de manejar sprites
public class Mario extends Sprite {

    //    Con estas variables manejare el estado del jugador, ya sea que este parado o corriendo
//    Y necesitare una variable para almacenar el estado actual y el anterior
    public enum playerState {FALLING, JUMPING, STANDING, RUNNING}
    public playerState currentState;
    public playerState previousState;

    //    Aqui almacenaremos el tiempo que hay en cada estado en especifico, para llevar un record
    private float stateTimer;

    //    En esta variable almacenare las animaciones de mario
    private final Animation<TextureRegion> playerRunning;
    private final Animation<TextureRegion> playerJumping;
    private boolean isPlayerRunningRight;
    private final Body body;

    private final TextureRegion playerStand;

    public Mario(GameScreen gameScreen) {

//        Debido a que heredamos de la clase sprite podemos implementar el constructor,
//        al que le mandaremos un texture region, y este texture region
//        lo podremos referenciar mediante el metodo getTexture
        super(gameScreen.getTextureAtlas().findRegion("little_mario"));

//        Standing debe de ser el estado inicial, tanto para el current como el previous state
        currentState = playerState.STANDING;
        previousState = playerState.STANDING;
        stateTimer = 0;

        isPlayerRunningRight = true;

// Seteando mis animaciones
        Array<TextureRegion> animationFrames = new Array<>();

//        La animacion de correr se encuentra en los sprite 1 hasta el 3, lo texture region empiezan tambien en 0
        for (int i = 1; i < 4; i++) {

//            Multiplico por 16 i, para asi escoger del segundo sprite de mario en adelante,
//            pues aqui es que empieza la animacion, en conclusion la posicion en X al principio sera 16,
//            en la sigte iteracion 32, etc..
            animationFrames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        }

//        De esta forma defino una animacion, el primer parametro es la duracion de cada frame
//        y el segundo el arreglo de texturegion
        playerRunning = new Animation<>(0.1f, animationFrames);

//        Luego limpiamos el arreglo pues ya no necesitamos los elementos dentro de este y necesitamos
//        llenar el arreglo con nuevos elementos
        animationFrames.clear();

        for (int i = 4; i < 6; i++)
            animationFrames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));


        playerJumping = new Animation<>(0.1f, animationFrames);

        animationFrames.clear();


        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        body = BodyHelper.createDynamicBody(new Box2DBody(32, 32, gameScreen.getWorld(), circleShape));

//        utilizamos getTexture para obtener el texture region que indicamos en el contructor super
//        y luego indicamos las coordenadas donde esta la imagen inicial que deseamos, como es la primera imagen
//        Le indicamos 0 0, aunque aqui tuve que hacer par de ajustes, debido a la existencia de las bolas de fuego
//        El origen del textureRegion empieza en la esquina superior izquierda, el 10 en Y funciona para bajar 10px
//        y asi tomar la imagen inicial de mario. Un texture region es un conjunto de imagenes juntas
        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);

        //    Metodos heredados de la Sprite
        setRegion(playerStand);
        setBounds(0, 0, 16 / PIXELS_PER_METER, 16 / PIXELS_PER_METER);
    }

    public void update(float deltaTime) {

//        Aqui actualizamos la posicion, los calculos extras son necesarios para que nuestro
//        body se quede junto a nuestro sprite
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

//        Actualizamos la rgion, aqui es que actualizo la animacion del personaje
        setRegion(getActualRegion(deltaTime));
    }

    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case JUMPING:
//                El stateTimer sera lo que esta funcion tomara de referencia para
//                decidir si cambiara de un sprite al sigte
                region = playerJumping.getKeyFrame(stateTimer);
                break;
            case RUNNING:
// Como deseamos que esta animacion se repita de principio a fin siempre que estemos corriendo, le enviamos
// un segundo parametro a esta funcion, donde le indicamos que sea true a looping
                region = playerRunning.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = playerStand;
        }

//        Evaluacion para determinar si las animaciones iran para la izquierda o la derecha
//        isFlipX retorna true, si la region ha sido volteada, en nuestro caso volteada a la izq
//        El metodo flip requiere dos booleans uno para x y otro para y, en nuestro caso solo queremos voltear el eje x
        if ((body.getLinearVelocity().x < 0 || !isPlayerRunningRight) && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || isPlayerRunningRight) && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }

//        Seteando statetimer, checar logica luego, que no comprendi part 11 min 14
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private playerState getPlayerCurrentState() {

//        La segunda condicion en el O logico, se refiere a que si el player habia saltado y en ese
//        salto cayo por un hueco, entonces continua con la animacion de jumping
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == playerState.JUMPING))
            return playerState.JUMPING;

//        Deseamos que la animacion de falling sea diferente a la de jumping y por esta
//        razon establecimos este estado y esta condicion
        else if (body.getLinearVelocity().y < 0)
            return playerState.FALLING;

        else if (body.getLinearVelocity().x != 0)
            return playerState.RUNNING;

        else
            return playerState.STANDING;
    }

    public Body getBody() {
        return body;
    }
}
