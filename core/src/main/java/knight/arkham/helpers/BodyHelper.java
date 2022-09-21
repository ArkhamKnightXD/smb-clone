package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class BodyHelper {

    public static Body createDynamicBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;

        bodyDefinition.position.set(box2DBody.xPosition / PIXELS_PER_METER, box2DBody.yPosition / PIXELS_PER_METER);

        Body body = box2DBody.world.createBody(bodyDefinition);

        FixtureDef fixtureDefinition = new FixtureDef();

        fixtureDefinition.shape = box2DBody.shape;
        fixtureDefinition.density = 100;

//A dynamic body should have at least one fixture with a non-zero density. Otherwise, you will get strange behavior.
        body.createFixture(fixtureDefinition);

        //        Necesito crear un sensor para detectar collision en la cabeza de mario, el edgeShape es básicamente una línea
//        entre 2 puntos
        EdgeShape headCollider = new EdgeShape();

//        De esta forma posiciono el collider justo encima de la cabeza de mario
        headCollider.set(new Vector2(-2 / PIXELS_PER_METER, 6 / PIXELS_PER_METER),
                new Vector2(2 / PIXELS_PER_METER, 6 / PIXELS_PER_METER));

//        Puedo agregar varias shape fixture, solo debo de asegurarme de hacer un createFixture al final para salvar
        fixtureDefinition.shape = headCollider;

//        Cuando le indicamos a mi fixture que isSensor es true, no collisiona con nada en nuestro mundo,
//        nos funciona como un sensor para programar sus colisiones
        fixtureDefinition.isSensor = true;

        body.createFixture(fixtureDefinition).setUserData("head");

        return body;
    }

    public static Fixture createStaticBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;
        bodyDefinition.position.set(box2DBody.xPosition / PIXELS_PER_METER, box2DBody.yPosition / PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 /PIXELS_PER_METER , box2DBody.height / 2 / PIXELS_PER_METER);

        Body body = box2DBody.world.createBody(bodyDefinition);

// A static body has zero mass by definition, so the density is not used in this case.  The default density is zero.
        return body.createFixture(shape,0);

//        shape.dispose();
    }
}
