package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//La clase sprite nos hereda un conjunto de funcionalidades encargadas de manejar sprites
public class Mario extends Sprite {

    private final Body body;

    public Mario(GameScreen gameScreen) {

//        Debido a que heredamos de la clase sprite podemos implementar el constructor,
//        al que le mandaremos un texture region
        super(gameScreen.getTextureAtlas().findRegion("little_mario"));

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        body = BodyHelper.createDynamicBody(new Box2DBody(32,32,
                gameScreen.getWorld(), circleShape));

//        utilizamos getTexture para obtener el texture region que indicamos en el contructor super
//        y luego indicamos las coordenadas donde esta la imagen inicial que deseamos, como es la primera imagen
//        Le indicamos 0 0, aunque aqui tuve que hacer par de ajustes
        //    Un texture region es un conjunto de imagenes juntas
        TextureRegion marioStand = new TextureRegion(getTexture(), 0, 10, 16, 16);
//        Metodos heredados de la Sprite
        setBounds(0,0, 16/ PIXELS_PER_METER, 16/ PIXELS_PER_METER);
        setRegion(marioStand);
    }

    public void update(float deltaTime){

//        Aqui actualizamos la posicion, los calculos extras son necesarios para que nuestro
//        body se quede junto a nuestro sprite
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight() /2);
    }

    public Body getBody() {return body;}
}
