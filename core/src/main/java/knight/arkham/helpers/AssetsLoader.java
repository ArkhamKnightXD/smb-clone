package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class AssetsLoader {

    private final AssetManager globalAssetsManager = new AssetManager();

    public void loadAllAssetsByFolder(String folderName) {

        FileHandle[] files = Gdx.files.local(folderName + "/").list();

        for (FileHandle file : files) {

            if ("images".equals(folderName))
                globalAssetsManager.load(file.path(), Texture.class);

            else if ("sound".equals(folderName))
                globalAssetsManager.load(file.path(), Sound.class);

            else if ("music".equals(folderName))
                globalAssetsManager.load(file.path(), Music.class);
        }
    }

    public AssetManager getGlobalAssetsManager() {return globalAssetsManager;}
}
