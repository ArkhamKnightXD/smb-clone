package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import knight.arkham.screens.GameScreen;

public class MarioBros extends Game {

	private AssetManager globalAssetManager;

//	A diferencia de los screens la function Create es completamente necesaria, para poder inicializar mi juego.
	@Override
	public void create() {

		globalAssetManager = new AssetManager();

		globalAssetManager.load("audio/music/mario_music.ogg", Music.class);

		globalAssetManager.load("audio/sound/coin.wav", Sound.class);
		globalAssetManager.load("audio/sound/bump.wav", Sound.class);
		globalAssetManager.load("audio/sound/breakBlock.wav", Sound.class);

//		Utilizare mi assetManager de forma síncrona, en pocas palabras cargare todos los asset antes de iniciar
//		Mi gameScreen
		globalAssetManager.finishLoading();

		setScreen(new GameScreen(globalAssetManager));
	}

	@Override
	public void dispose() {

		super.dispose();

		globalAssetManager.dispose();
	}
}