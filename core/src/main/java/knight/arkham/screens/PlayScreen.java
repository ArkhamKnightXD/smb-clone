package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Mario;
import knight.arkham.scenes.Hud;

import static knight.arkham.helpers.Constants.VIRTUAL_HEIGHT;
import static knight.arkham.helpers.Constants.VIRTUAL_WIDTH;

public class PlayScreen extends ScreenAdapter {

	private final Mario game;
	private final SpriteBatch batch;
	private final BitmapFont font;

	private final OrthographicCamera camera;

	//	viewport, esto nos sirve para poder configurar como se vera nuestro juego en diferentes dispositivos
	private final Viewport viewport;
	
	private final Hud hud;


	public PlayScreen() {

		game = Mario.INSTANCE;

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
		hud = new Hud(batch);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0, 0, 0, 0);

//		Con esto le indicamos a nuestro gamebatch donde esta nuestra camara y que solo
//		renderice lo que nuestra camara puede ver
//		batch.setProjectionMatrix(camera.combined);

//		De esta forma seteamos el batch con nuestro hud
		batch.setProjectionMatrix(hud.stage.getCamera().combined);

//		De esta forma dibujamos en pantalla nuestro hud
		hud.stage.draw();
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
	}
}