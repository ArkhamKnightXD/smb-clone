package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.MarioBros;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.TileMapCreator;
import knight.arkham.sprites.enemies.Enemy;
import knight.arkham.sprites.player.Mario;
import knight.arkham.scenes.Hud;
import knight.arkham.sprites.items.Item;
import knight.arkham.sprites.items.ItemDefinition;
import knight.arkham.sprites.items.Mushroom;
import knight.arkham.sprites.player.PlayerAnimationState;
import java.util.concurrent.LinkedBlockingQueue;
import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {

    private final SpriteBatch batch;

    private final OrthographicCamera camera;

    //	viewport, esto nos sirve para poder configurar como se verá nuestro juego en diferentes dispositivos.
    private final Viewport viewport;

    private final Hud hud;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final World world;


//Ahora mismo no tiene proposito el debugRenderer
    private final Box2DDebugRenderer debugRenderer;

    private final Mario mario;

    private final TextureAtlas textureAtlas;

    private final AssetManager assetManager;

    private final TileMapCreator mapCreator;

    private final Array<Item> items;

    private final MarioBros game;


    //    Investigar más sobre esto y sobre porque no puedo utilizar un array normal en vez de esta opción.
//    Ahora mismo con esta lista no hay error, pero no pasa nada. Si logro hacer esto funcionar probaré con Array
    public LinkedBlockingQueue<ItemDefinition> itemsToSpawn;


    public GameScreen(MarioBros game) {

        this.game = game;

        assetManager = game.getGlobalAssetManager();

//       Debemos de Inicializar world al principio para evitar errores a la hora de crear body2D con doSleep en true
//       mejoro el rendimiento debido a que en mi world no se calcularan las físicas de mis objetos static
//       como paredes y pisos.
        world = new World(new Vector2(0, -10), true);

        world.setContactListener(new GameContactListener());

        debugRenderer = new Box2DDebugRenderer();

//        Asi cargamos un textureAtlas, un texture atlas es un conjunto de imágenes convertidas en una sola
//        Y en el pack se guardan los nombres de las imágenes con sus posiciones X e Y, también su tamaño se guarda.
        textureAtlas = new TextureAtlas("images/Mario_and_Enemies.pack");

        mario = new Mario(this);

        batch = new SpriteBatch();

        hud = new Hud(batch);

        camera = new OrthographicCamera();

//		Los viewport se inicializan junto a la camara hay varios tipos de viewport, stretch screen, cada uno tienes sus
//		ventajas y desventajas, con stretch, si agrando la ventaja mis images se estrecharan con la pantalla, lo cual
//		se ve raro, pero con screenViewPort, no importa cuanto estreche o achique la pantalla mis imágenes tendrán
//		el mismo tamaño y finalmente con fitViewPort es parecido a screen, lo único que a este le enviamos la altura y
//		ancho de la ventana y si la pantalla es más pequeña de ahi, entonces se agregaran barras negras.
        viewport = new FitViewport(VIRTUAL_WIDTH / PIXELS_PER_METER,
                VIRTUAL_HEIGHT / PIXELS_PER_METER, camera);

//		A la hora de preparar la posición inicial de la camara debemos de hacerlo con el world width y el height.
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        mapCreator = new TileMapCreator(this);

        mapRenderer = mapCreator.setupMap();

        Music music = assetManager.get("audio/music/mario_music.ogg", Music.class);

        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        items = new Array<Item>();

        itemsToSpawn = new LinkedBlockingQueue<ItemDefinition>();
    }

    public void spawnItems(ItemDefinition itemDefinition) {

        itemsToSpawn.add(itemDefinition);
    }


    public void handleSpawningItems() {

        if (!itemsToSpawn.isEmpty()) {

//            Esto funciona igual que un pop en JS. Elimina el último elemento de la lista y me lo retorna.
            ItemDefinition itemDefinition = itemsToSpawn.poll();

            if (itemDefinition.classType == Mushroom.class)
                items.add(new Mushroom(this, new Vector2(itemDefinition.position.x, itemDefinition.position.y)));

        }
    }


    private void handleUserInput() {

//        Si mario esta muerto no se podrá mover
        if (mario.currentState != PlayerAnimationState.DEAD) {

            // Todo salta varias veces
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                mario.getBody().applyLinearImpulse(new Vector2(0, 4f), mario.getBody().getWorldCenter(), true);

//        Si quiero reducir o aumentar la maxima velocidad de mario debo jugar con los valores al final del if
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.getBody().getLinearVelocity().x <= 1.3)
                mario.getBody().applyLinearImpulse(new Vector2(1, 0), mario.getBody().getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.getBody().getLinearVelocity().x >= -1.3)
                mario.getBody().applyLinearImpulse(new Vector2(-1, 0), mario.getBody().getWorldCenter(), true);

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            Gdx.app.exit();
            dispose();
        }
    }


    private void update(float deltaTime) {

        handleUserInput();

        handleSpawningItems();

        //There are two phases in the constraint solver: a velocity phase and a position phase.
        // In the velocity phase the solver computes the impulses necessary for the bodies to move correctly.
        // In the position phase the solver adjusts the positions of the bodies to reduce overlap and joint detachment.
        // Each phase has its own iteration count. In addition, the position phase may exit iterations early if the
        // errors are small. The suggested iteration count for Box2D is 8 for velocity and 3 for position.
        // You can tune this number to your liking, just keep in mind that this has a trade-off between performance and
        // accuracy. Using fewer iterations increases performance but accuracy suffers. Likewise, using more iterations
        // decreases performance but improves the quality of your simulation.
        world.step(1 / 60f, 6, 2);

//        Nuestra camara seguirá la posición en X de nuestro personaje. Esto se hará siempre y cuando nuestro
//        player no este muerto. Cuando mario muera la camara se quedara trabada en su última posición
        if (mario.currentState != PlayerAnimationState.DEAD)
            camera.position.x = mario.getBody().getPosition().x;

        mario.update(deltaTime);

//        Si tengo varios objetos que tengo que actualizar esta es la única forma.
//        Nota podría cambiar el forEach por un for, por cuestiones de performance.
        for (Enemy enemy : mapCreator.getEnemies()) {

            enemy.update(deltaTime);

//            Aqui activo el body de mi enemigo cuando este a cierta distancia de mario.
            if (enemy.getX() < mario.getX() + 2.5f)
                enemy.body.setActive(true);
        }

        for (Item item : items)
            item.update(deltaTime);

        hud.update(deltaTime);

        camera.update();

        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.render();

        //Con esto le indicamos a nuestro gameBatch donde está nuestra camara y que solo
//		renderice lo que nuestra camara puede ver.
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

//        La función draw es heredado de la clase sprite, implementada mi clase Mario.
        mario.draw(batch);

        for (Enemy enemy : mapCreator.getEnemies())
            enemy.draw(batch);

        for (Item item : items)
            item.draw(batch);

        batch.end();

//		De esta forma preparamos el batch con nuestro hud.
        batch.setProjectionMatrix(hud.stage.getCamera().combined);

//		De esta forma dibujamos en pantalla nuestro hud, mediante nuestro campo stage que tiene una función draw.
        hud.stage.draw();

        if (isGameOver()) {

            game.setScreen(new GameOverScreen(game, batch));

//            Cuando cambie de pantalla es el momento correcto para llamar al metodo dispose
            dispose();
        }

        debugRenderer.render(world, camera.combined);
    }

    public boolean isGameOver() {

//    Si mario tiene estado DEAD y lleva más de 3 segundos en este estado esto me retornara true de lo contrario false
        return mario.currentState == PlayerAnimationState.DEAD && mario.getStateTimer() > 3;
    }

    @Override
    public void resize(int width, int height) {

//		Si nuestra pantalla cambia de tamaño deseamos que el viewport ajuste nuestro juego.
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

//        Todo buscarle solución a los dispose
//        No puedo hacer dispose de ninguno de estos a la hora de cambiar de pantalla, pues me da error.
//        batch.dispose();
//        world.dispose();
//        debugRenderer.dispose();
        //assetManager.dispose();

        mapRenderer.dispose();
        hud.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}