package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

//	Usare el assetmanager de forma static para ahorrar tiempo, pero esto no es correcto pues puede
//	dar problemas en dispositivos android, luego debo cambiar esto
	public static AssetManager assetManager;

	public MarioBros() {

		INSTANCE = this;
	}

	@Override
	public void create() {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

//		Seteando mi assetmanager para
		assetManager = new AssetManager();

		assetManager.load("audio/music/mario_music.ogg", Music.class);

		assetManager.load("audio/sound/coin.wav", Sound.class);
		assetManager.load("audio/sound/bump.wav", Sound.class);
		assetManager.load("audio/sound/breakblock.wav", Sound.class);

//		Utilizare mi assetManager de forma sincrona, en pocas palabras cargare todos los asset antes de iniciar
//		Mi gameScreen
		assetManager.finishLoading();

		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {

		super.dispose();

		assetManager.dispose();
	}

	public int getScreenWidth() {return screenWidth;}

	public int getScreenHeight() {return screenHeight;}
}