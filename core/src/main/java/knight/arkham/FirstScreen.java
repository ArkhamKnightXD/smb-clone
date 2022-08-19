package knight.arkham;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen extends ScreenAdapter {


	private final SpriteBatch batch;
	private final BitmapFont font;

	public FirstScreen() {

		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		batch.begin();

		font.draw(batch, "Hello", 100, 100);

		batch.end();
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