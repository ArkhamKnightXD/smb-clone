package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import knight.arkham.screens.PlayScreen;

public class Mario extends Game {

	public static Mario INSTANCE;

	private int screenWidth;
	private int screenHeight;

	public Mario() {

		INSTANCE = this;
	}

	@Override
	public void create() {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		setScreen(new PlayScreen());
	}


	public int getScreenWidth() {return screenWidth;}

	public int getScreenHeight() {return screenHeight;}
}