package knight.arkham.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//La clase sprite nos hereda un conjunto de funcionalidades encargadas de manejar sprites.
public class Mario extends Sprite {

    //    Con estas variables manejaré el estado del jugador, ya sea que este parado o corriendo
//    Y necesitaré una variable para almacenar el estado actual y el anterior
    public enum playerState {FALLING, JUMPING, STANDING, RUNNING, GROWING}
    public playerState currentState;
    public playerState previousState;

    //    Aqui almacenaremos el tiempo que hay en cada estado en específico, para llevar un record.
    private float stateTimer;

    //    En esta variable almacenaré las animaciones de mario
    private Animation<TextureRegion> playerRunning;
    private TextureRegion playerJumping;
    private boolean isPlayerRunningRight;
    private Body body;

    private final TextureRegion playerStand;

    private final TextureRegion bigPlayerStand;
    private TextureRegion bigPlayerJump;

    private Animation<TextureRegion> growPlayer;
    private Animation<TextureRegion> bigPlayerRunning;

    private boolean IsMarioBig;
    private boolean shouldStartGrowAnimation;
    private boolean timeToDefineBigMario;

    private final GameScreen gameScreen;

    public Mario(GameScreen gameScreen) {

//        Todo remover el super constructor
//        Debido a que heredamos de la clase sprite podemos implementar el constructor, al que le mandaremos un texture
//        region, y este texture region lo podremos referenciar más abajo mediante la función getTexture.
//        Esto nos dara la region que le pertenece a los sprite llamados little_mario
//        Esto puedo removerlo pues no tiene mucho proposito, ya que utilizo aqui varios getTextureAtlas
        super(gameScreen.getTextureAtlas().findRegion("little_mario"));

        this.gameScreen = gameScreen;


//        Standing debe de ser el estado inicial, tanto para el current como el previous state
        currentState = playerState.STANDING;
        previousState = playerState.STANDING;
        stateTimer = 0;

        isPlayerRunningRight = true;

        makePlayerAnimations();

        body = BodyHelper.createPlayerBody(

                new Box2DBody(new Vector2(32, 32), gameScreen.getWorld(), this)
        );

//Utilizamos getTexture para obtener el texture region que indicamos en el constructor súper y luego indicamos
// las coordenadas donde está la imagen inicial que deseamos, como es la primera imagen Le indicamos 0 0, aunque aqui
// tuve que hacer par de ajustes, debido a la existencia de las bolas de fuego El origen del textureRegion empieza en
// la esquina superior izquierda, el 10 en Y funciona para bajar 10px y asi tomar la imagen inicial de mario.
// Un texture region es un conjunto de imágenes juntas.
        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);

//        Tiene el doble de tamaño que little_mario, por lo tanto, su tamaño es de 32 px
        bigPlayerStand = new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                0 , 0 , 16 ,32);

        //    Funciones heredadas de la clase Sprite
        setRegion(playerStand);
        setBounds(0, 0, 16 / PIXELS_PER_METER, 16 / PIXELS_PER_METER);
    }

    private void makePlayerAnimations() {

        // Preparando mis animaciones
        Array<TextureRegion> animationFrames = new Array<TextureRegion>();

//        La animación de correr se encuentra en los sprite 1 hasta el 3, lo texture region empiezan también en 0
        for (int i = 1; i < 4; i++) {

//  Multiplico por 16 i, para asi escoger del segundo sprite de mario en adelante, pues aqui es que empieza
//  la animación, en conclusion la posición en X al principio será 16, en la siguiente iteración 32, etc..
            animationFrames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        }

// De esta forma defino una animación, el primer parametro es la duración de cada frame
//        y el segundo el arreglo de textureRegion
        playerRunning = new Animation<TextureRegion>(0.1f, animationFrames);

//        Luego limpiamos el arreglo pues ya no necesitamos los elementos dentro de este y necesitamos
//        llenar el arreglo con nuevos elementos
        animationFrames.clear();


        // Como el salto es de solo 1 frame tanto para little_mario como para big_mario no hay necesidad de guardar
        // esto en un tipo de dato animation y hacer el loop. Si no guardar directamente el textureRegion
        playerJumping = new TextureRegion(getTexture(), 80 , 10 , 16 ,16);

        bigPlayerJump = new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                80 , 0 , 16 ,32);

        for (int i = 1; i < 4; i++){

            animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario")
                    , i * 16, 0, 16, 32));
        }

        bigPlayerRunning = new Animation<TextureRegion>(0.1f, animationFrames);

        animationFrames.clear();

//        Set animation frames for growing mario. Para la animación intercalamos entre un sprite de mario pequeño
//        y el primer sprite, básicamente el marioStand
         animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                 240 , 0 , 16 ,32));
        animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                0 , 0 , 16 ,32));
        animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                240 , 0 , 16 ,32));
        animationFrames.add(new TextureRegion(gameScreen.getTextureAtlas().findRegion("big_mario"),
                0 , 0 , 16 ,32));

        growPlayer = new Animation<TextureRegion>(0.2f, animationFrames);
    }

    public void update(float deltaTime) {

//        Cuando mario es grande tenemos que restar varios pixeles en Y para que se ajuste a su nuevo body
        if(IsMarioBig)
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 - 6 / PIXELS_PER_METER);


//        Aqui actualizamos la posición, los cálculos extras son necesarios para que nuestro
//        body se quede junto a nuestro sprite
        else
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

//        Actualizamos la region, aqui es que actualizo la animación del personaje
        setRegion(getActualRegion(deltaTime));

        if (timeToDefineBigMario)
            createBigMarioBody();
    }

    private void createBigMarioBody() {

        Vector2 playerCurrentPosition = body.getPosition();

//        Vamos a crear un nuevo cuerpo para cuando mario sea grande y, por lo tanto, debemos destruir el body de mario
//        pequeño.
        gameScreen.world.destroyBody(body);

        body = BodyHelper.createBigPlayerBody(new Box2DBody(
                new Vector2(playerCurrentPosition), gameScreen.getWorld(), this
        ));


        timeToDefineBigMario = false;
    }

    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case GROWING:
                region = bigPlayerRunning.getKeyFrame(stateTimer);

//                Cuando la animation termine debo indicar la variable como false.
                if (growPlayer.isAnimationFinished(stateTimer))
                    shouldStartGrowAnimation = false;

                break;

            case JUMPING:
// El stateTimer será lo que esta función tomara de referencia para decidir si cambiara de un sprite al siguiente.
                region = IsMarioBig ? bigPlayerJump : playerJumping;
                break;
            case RUNNING:
// Como deseamos que esta animación se repita de principio a fin siempre que estemos corriendo, le enviamos
// un segundo parametro a esta función, donde le indicamos que sea true a looping
                region = IsMarioBig ? bigPlayerRunning.getKeyFrame(stateTimer, true)
                        : playerRunning.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = IsMarioBig ? bigPlayerStand : playerStand;
        }

        flipPlayerOnXAxis(region);

//Si el estado actual no es igual al anterior, entonces debemos pasar a otro estado, si no debemos reiniciar el timer.
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private void flipPlayerOnXAxis(TextureRegion region) {

        //Evaluación para determinar si las animaciones iran para la izquierda o la derecha isFlipX retorna true,
        // si la region ha sido volteada, en nuestro caso volteado a la izquierda La función flip requiere dos booleans
        // uno para X y otro para Y, en nuestro caso solo queremos voltear el eje X.
        if ((body.getLinearVelocity().x < 0 || !isPlayerRunningRight) && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || isPlayerRunningRight) && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }
    }

    private playerState getPlayerCurrentState() {

//        Esta debe de ser la animation a la que se le dé prioridad sobre las demás.
        if (shouldStartGrowAnimation)
            return playerState.GROWING;

//        La segunda condición en el O lógico, se refiere a que si el player había saltado y en ese
//        salto cayó por un hueco, entonces continua con la animación de jumping
        else if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == playerState.JUMPING))
            return playerState.JUMPING;

//        Deseamos que la animación de falling sea diferente a la de jumping y por esta
//        razón establecimos este estado y esta condición
        else if (body.getLinearVelocity().y < 0)
            return playerState.FALLING;

        else if (body.getLinearVelocity().x != 0)
            return playerState.RUNNING;

        else
            return playerState.STANDING;
    }

    public void growPlayer(){

        shouldStartGrowAnimation = true;
        IsMarioBig = true;
        timeToDefineBigMario = true;

        setBounds(getX(), getY(), getWidth() , getHeight() * 2);

        gameScreen.getAssetManager().get("audio/sound/powerup.wav", Sound.class).play();
    }

    public Body getBody() {
        return body;
    }

    public boolean isMarioBig() {
        return IsMarioBig;
    }
}
