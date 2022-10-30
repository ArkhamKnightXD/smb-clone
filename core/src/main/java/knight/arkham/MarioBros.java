package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import knight.arkham.helpers.AssetsLoader;
import knight.arkham.screens.GameScreen;

public class MarioBros extends Game {

	private AssetManager globalAssetManager;

//	A diferencia de los screens la function Create es completamente necesaria, para poder inicializar mi juego.
//	Pues sin esta me da error a la hora de iniciar el game.
	@Override
	public void create() {

		AssetsLoader assetsLoader = new AssetsLoader();

		globalAssetManager = assetsLoader.getGlobalAssetsManager();

		assetsLoader.loadAllAssetsByFolder("music");
		assetsLoader.loadAllAssetsByFolder("sound");

		globalAssetManager.finishLoading();

		setScreen(new GameScreen(this));
	}

	public AssetManager getGlobalAssetManager() {
		return globalAssetManager;
	}
}