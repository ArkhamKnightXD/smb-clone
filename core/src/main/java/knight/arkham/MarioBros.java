package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import knight.arkham.screens.GameScreen;

public class MarioBros extends Game {

	public static MarioBros INSTANCE;
	private int screenWidth;
	private int screenHeight;

	public MarioBros() {

		INSTANCE = this;
	}

	@Override
	public void create() {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		setScreen(new GameScreen());
	}


	public int getScreenWidth() {return screenWidth;}

	public int getScreenHeight() {return screenHeight;}
}