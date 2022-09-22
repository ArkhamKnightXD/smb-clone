package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import knight.arkham.screens.GameScreen;

public class MarioBros extends Game {

	public static MarioBros INSTANCE;
	private int screenWidth;
	private int screenHeight;


//	Aqui definiremos las categorias de los fixture
	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

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