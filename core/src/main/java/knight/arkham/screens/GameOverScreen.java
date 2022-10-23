package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.MarioBros;
import static knight.arkham.helpers.Constants.*;

public class GameOverScreen extends ScreenAdapter {

    private final Stage stage;

    private final MarioBros game;

    public GameOverScreen(MarioBros game, SpriteBatch batch) {

        this.game = game;

        Viewport viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();

        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        table.add(gameOverLabel).expandX();

        table.row();

        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);

    }


    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()){

            game.setScreen(new GameScreen(game));

            dispose();
        }

        ScreenUtils.clear(0, 0, 0, 0);

        stage.draw();
    }


    @Override
    public void dispose() {

        stage.dispose();
    }
}
