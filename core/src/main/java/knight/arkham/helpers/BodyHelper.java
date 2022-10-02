package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import static knight.arkham.helpers.Constants.*;

public class BodyHelper {

    public static Body createDynamicBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;

//  Mi body rotaba por defecto lo cual hacía que mi headCollider no se quedara en un solo lugar, y esto hacía que
//  la colisión con los bloques fallara. Esta opción es util para personajes controlables.
        bodyDefinition.fixedRotation = true;

        bodyDefinition.position.set(box2DBody.position.x / PIXELS_PER_METER, box2DBody.position.y / PIXELS_PER_METER);

        Body body = box2DBody.world.createBody(bodyDefinition);

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        FixtureDef fixtureDefinition = getPreparedFixtureDefinition(circleShape);

//  A dynamic body should have at least one fixture with a non-zero density. Otherwise, you will get strange behavior.
        body.createFixture(fixtureDefinition);

        circleShape.dispose();

        //Necesito crear un sensor para detectar collision en la cabeza de mario, el edgeShape es básicamente una línea
        // entre 2 puntos. Podemos imaginarlo como un sombrero muy fino.
        EdgeShape headCollider = new EdgeShape();

//  De esta forma posiciono el collider justo encima de la cabeza de mario
        headCollider.set(new Vector2(-2 / PIXELS_PER_METER, 7 / PIXELS_PER_METER),
                new Vector2(2 / PIXELS_PER_METER, 7 / PIXELS_PER_METER));

//  Puedo agregar varias shape a un fixture, solo debo de asegurarme de hacer un createFixture luego de agregar cada
//  nuevo shape, para cada shape debo de realizar un createFixture diferente
        fixtureDefinition.shape = headCollider;

//  Cuando le indicamos a mi fixture que isSensor es true, no colisionara con nada en nuestro mundo,
//  nos funciona como un sensor para programar sus colisiones.
        fixtureDefinition.isSensor = true;

        body.createFixture(fixtureDefinition).setUserData("head");

        headCollider.dispose();

        return body;
    }

//    En esta función preparo los valores iniciales que va a tener mi fixture.
    private static FixtureDef getPreparedFixtureDefinition(CircleShape shape) {

        FixtureDef fixtureDefinition = new FixtureDef();

        //  Cada fixture en box2D tiene un filtro, el filtro tiene una categoría y una mask, la categoría es para
        //  indicar que este fixture, es mario, brick o un coin y el mask representa con cuáles fixture este fixture
        //  puede colisionar, los filtros se indican con bits, en este caso utilizaremos variables, short Y sus valores
        //  serán potencias de 2 para diferenciar los filtros. Finalmente, indico mi categoría y le indico
        //  a este fixture que será mario.
        fixtureDefinition.filter.categoryBits = MARIO_BIT;

//        Aqui defino con que mi fixture de mario podrá colisionar, lo hare con O lógicos simples Indicamos que mario
//        pueda colisionar con todos los bits exceptuando el Destroyed_bit, pues cuando un fixture tenga este bit
//        no queremos que mario colisione, pues técnicamente este objeto está destruido.
        fixtureDefinition.filter.maskBits = DEFAULT_BIT | COIN_BIT | BRICK_BIT;

        fixtureDefinition.shape = shape;

//  100 es una densidad recomendable, si la densidad es muy alta nuestro personaje no va a poder saltar
        fixtureDefinition.density = 100;
        fixtureDefinition.friction = 0.1f;

        return fixtureDefinition;
    }

    public static Fixture createStaticBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;
        bodyDefinition.position.set(box2DBody.position.x / PIXELS_PER_METER, box2DBody.position.y / PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 /PIXELS_PER_METER , box2DBody.height / 2 / PIXELS_PER_METER);

        Body body = box2DBody.world.createBody(bodyDefinition);

    //A static body has zero mass by definition, so the density is not used in this case. The default density is zero.
        Fixture fixture = body.createFixture(shape,0);

        shape.dispose();

//  Como desde mi fixture puedo obtener también el body, solo debo de retornar el fixture
        return fixture;
    }
}
