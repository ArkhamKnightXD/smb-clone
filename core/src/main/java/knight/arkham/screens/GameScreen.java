package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.scenes.Hud;
import static knight.arkham.helpers.Constants.VIRTUAL_HEIGHT;
import static knight.arkham.helpers.Constants.VIRTUAL_WIDTH;

public class GameScreen extends ScreenAdapter {

//    private final Mario game;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final OrthographicCamera camera;

    //	viewport, esto nos sirve para poder configurar como se vera nuestro juego en diferentes dispositivos
    private final Viewport viewport;

    private final Hud hud;

    private final OrthogonalTiledMapRenderer mapRenderer;

    public World world;

    private final Box2DDebugRenderer debugRenderer;


    public GameScreen() {

//        game = Mario.INSTANCE;

//       Inicializar world al principio para evitar errores a la hora de crear body2
//      con doSleep en true mejoro el rendimiento debido a que en mi world no se calcularan las fisicas
//		de mis objetos que esten quietos, osea de mis objetos estaticos como paredes y piso
        world = new World(new Vector2(0, 0), true);

        batch = new SpriteBatch();
        font = new BitmapFont();

        camera = new OrthographicCamera();

//		Los viewport se inicializan junto a la camara hay varios tipos de viewport, stretch screen,
//		cada uno tienes sus ventajas y desventajas, con strecth, si agrando la ventaja mis images
//		se estrecharan con la pantalla, lo cual se ve raro, pero con screenviewport, no importa cuanto
//		estreche o achique la pantalla mis imagenes tendran el mismo tamaño y finalmente con fitviewport
//		es parecido a screen, lo unico que a este le enviamos la altura y tamaño de la ventana y si la
//		pantalla es mas pequeña de ahi, entonces se agregaran barras negras
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

//		a la hora de setear la posicion de la camara debemos de hacerlo con world width en height
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        hud = new Hud(batch);

        TileMapHelper tileMapHelper = new TileMapHelper(this);

        mapRenderer = tileMapHelper.setupMap();

        debugRenderer = new Box2DDebugRenderer();

    }


    @Override
    public void show() {

    }


    private void handleUserInput(float deltaTime) {

        if (Gdx.input.isTouched())
            camera.position.x += 100 * deltaTime;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            Gdx.app.exit();
            dispose();
        }
    }


    private void update(float deltaTime) {

        handleUserInput(deltaTime);

        camera.update();

        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

//		Con esto le indicamos a nuestro gamebatch donde esta nuestra camara y que solo
//		renderice lo que nuestra camara puede ver
//		batch.setProjectionMatrix(camera.combined);

        mapRenderer.render();


//		De esta forma seteamos el batch con nuestro hud
        batch.setProjectionMatrix(hud.stage.getCamera().combined);


//		De esta forma dibujamos en pantalla nuestro hud
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
        font.dispose();
        world.dispose();
    }

    public World getWorld() {
        return world;
    }
}