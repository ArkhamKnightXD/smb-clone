package knight.arkham.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static knight.arkham.helpers.Constants.VIRTUAL_HEIGHT;
import static knight.arkham.helpers.Constants.VIRTUAL_WIDTH;

//Clase encargada del manejo del hud de mi juego
//Si deseamos que nuestra clase tenga un metodo dispose hacemos esto
public class Hud implements Disposable {

    public Stage stage;
//    Cuando nuestro mundo se mueve, queremos que nuestro hud se quede igual por eso
//    debemos agregar una nueva camara y un nuevo viewport
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

//    Scene2d elements
    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;


    public Hud(SpriteBatch batch) {

        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, batch);

//        Crearemos una tabla en nuestro stage, para asi poder ordenar nuestros widget como deseemos
        Table table = new Table();

//        Cuando indicamos esto , quiere decir que pondra los elementos en la parte de arriba de nuestro stage
        table.top();

//        Esto quiere decir que nuestra tabla ahora es del tamaño de nuestro stage
        table.setFillParent(true);

//        Set all scene2d widgets
//        debo de hacer un string format para llevar de integer a string El 03 indica la cantidad de unidades
//        que tendra nuestro numero y la d para indicar que es un integer, y finalmente indicamos el estilo
//        de mi label, el color sera blanco
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

//        Add elements to the table
//        Agregaremos el label de mario de primero y haremos que se expanda en x, con esto,
//        si hay 3 elementos en pantalla, cada uno tendra un tercio de pantalla y finalmente agrego un paddingtop de 10px
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

//        Creamos una nueva fila en mi tabla
        table.row();
//        Ahora todos los elementos que agregue de aqui para adelante estaran en una nueva tabla
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

//        add table to my stage, tanto table como label son actors, asi que puedo agregar todos esos elementos
//        a mi stage utilizando addActor
        stage.addActor(table);
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
