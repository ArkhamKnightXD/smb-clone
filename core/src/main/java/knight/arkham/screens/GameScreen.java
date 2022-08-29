package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.Mario;
import knight.arkham.scenes.Hud;

import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {

//    private final Mario game;
    private final SpriteBatch batch;

    private final OrthographicCamera camera;

    //	viewport, esto nos sirve para poder configurar como se vera nuestro juego en diferentes dispositivos
    private final Viewport viewport;

    private final Hud hud;

    private final OrthogonalTiledMapRenderer mapRenderer;

    public World world;

    private final Box2DDebugRenderer debugRenderer;

    private final Mario mario;

    private final TextureAtlas textureAtlas;


    public GameScreen() {

//        game = Mario.INSTANCE;

//       Inicializar world al principio para evitar errores a la hora de crear body2
//      con doSleep en true mejoro el rendimiento debido a que en mi world no se calcularan las fisicas
//		de mis objetos que esten quietos, osea de mis objetos estaticos como paredes y piso
        world = new World(new Vector2(0, -10), true);


//        Asi cargamos un texturealtlas, un texture atlas es un conjunto de imagenes vuelta una sola
//        Y en el pack se guardan los nombres de las imagenes con sus posiciones x y y tamaño
        textureAtlas = new TextureAtlas("images/Mario_and_Enemies.pack");

        mario = new Mario(this);

        debugRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();

        hud = new Hud(batch);

        camera = new OrthographicCamera();

//		Los viewport se inicializan junto a la camara hay varios tipos de viewport, stretch screen,
//		cada uno tienes sus ventajas y desventajas, con strecth, si agrando la ventaja mis images
//		se estrecharan con la pantalla, lo cual se ve raro, pero con screenviewport, no importa cuanto
//		estreche o achique la pantalla mis imagenes tendran el mismo tamaño y finalmente con fitviewport
//		es parecido a screen, lo unico que a este le enviamos la altura y tamaño de la ventana y si la
//		pantalla es mas pequeña de ahi, entonces se agregaran barras negras
        viewport = new FitViewport(VIRTUAL_WIDTH / PIXELS_PER_METER,
                VIRTUAL_HEIGHT / PIXELS_PER_METER, camera);

//		a la hora de setear la posicion de la camara debemos de hacerlo con world width en height
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        TileMapHelper tileMapHelper = new TileMapHelper(this);

        mapRenderer = tileMapHelper.setupMap();
    }


    @Override
    public void show() {

    }


    private void handleUserInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            mario.getBody().applyLinearImpulse(new Vector2(0, 4f), mario.getBody().getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && mario.getBody().getLinearVelocity().x <= 2)
            mario.getBody().applyLinearImpulse(new Vector2(1f, 0), mario.getBody().getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && mario.getBody().getLinearVelocity().x >= -2)
            mario.getBody().applyLinearImpulse(new Vector2(-1f, 0), mario.getBody().getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            Gdx.app.exit();
            dispose();
        }
    }


    private void update(float deltaTime) {

        handleUserInput();

//        Nuestra camara seguira la posicion x de nuestro personaje
        camera.position.x = mario.getBody().getPosition().x;

        mario.update(deltaTime);

        camera.update();

        mapRenderer.setView(camera);

    }

    @Override
    public void render(float delta) {

//There are two phases in the constraint solver: a velocity phase and a position phase. In the velocity phase the solver
// computes the impulses necessary for the bodies to move correctly. In the position phase the solver adjusts the
// positions of the bodies to reduce overlap and joint detachment. Each phase has its own iteration count. In addition,
// the position phase may exit iterations early if the errors are small.

// The suggested iteration count for Box2D is 8 for velocity and 3 for position. You can tune this number to your
// liking, just keep in mind that this has a trade-off between performance and accuracy. Using fewer iterations
// increases performance but accuracy suffers. Likewise, using more iterations decreases performance but improves the
// quality of your simulation.
        world.step(1/60f, 6, 2);

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.render();

        //Con esto le indicamos a nuestro gamebatch donde esta nuestra camara y que solo
//		renderice lo que nuestra camara puede ver
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

//        El metodo draw es heredado de la clase sprite, implementada en el objeto mario
        mario.draw(batch);

        batch.end();

//		De esta forma seteamos el batch con nuestro hud
        batch.setProjectionMatrix(hud.stage.getCamera().combined);


//		De esta forma dibujamos en pantalla nuestro hud, mediante nuestro campo hud que tiene un metodo draw
        hud.stage.draw();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

//		Si nuestra pantalla cambia de tamaño deseamos que el viewport ajuste nuestro juego
        viewport.update(width, height);
    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
        mapRenderer.dispose();
        hud.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }
}