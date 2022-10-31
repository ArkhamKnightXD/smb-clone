package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import static knight.arkham.helpers.Constants.*;

public class Box2DBodyCreator {

    public static Body createPlayerBody(Box2DBody box2DBody){

        Body body = getPreparedBody(box2DBody, true);

        CircleShape circleShape = new CircleShape();

        FixtureDef fixtureDef = getPreparedFixtureDefinition(circleShape, MARIO_BIT, ENEMY_HEAD_BIT, false);

        circleShape.dispose();

//  A dynamic body should have at least one fixture with a non-zero density. Otherwise, you will get strange behavior.
        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        EdgeShape headCollider = makePlayerHeadCollider(fixtureDef);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        headCollider.dispose();

        return body;
    }

    private static EdgeShape makePlayerHeadCollider(FixtureDef fixtureDefinition) {
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

        fixtureDefinition.filter.categoryBits = MARIO_HEAD_BIT;
        return headCollider;
    }


    public static Body createBigPlayerBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;

        bodyDefinition.fixedRotation = true;

//        A nuestro nuevo body le otorgaremos la misma posición que el mario pequeño, pero le agregaremos 10 en Y Para
//        Que mario no traspase el piso
        bodyDefinition.position.set(box2DBody.position.add(0, 10/PIXELS_PER_METER));

        Body body = box2DBody.world.createBody(bodyDefinition);

        CircleShape circleShape = new CircleShape();

        FixtureDef fixtureDef = getPreparedFixtureDefinition(circleShape, MARIO_BIT, ENEMY_HEAD_BIT, true);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

//        Lo que haremos aqui será crear otra forma para que este más debajo de la forma inicial
        circleShape.setPosition(new Vector2(0, -14 / PIXELS_PER_METER));

//        Debemos de crear nuestro fixture de nuevo ya que lo que deseamos es crear un segundo fixture que se adapte
//        a la parte de abajo de mario. Asi que el mario grande tendrá dos fixture
        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        circleShape.dispose();

        EdgeShape headCollider = makePlayerHeadCollider(fixtureDef);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        headCollider.dispose();

        return body;
    }


    private static Body getPreparedBody(Box2DBody box2DBody, boolean hasDynamicBody) {

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = hasDynamicBody ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

//  Mi body rotaba por defecto lo cual hacía que mi headCollider no se quedara en un solo lugar, y esto hacía que
//  la colisión con los bloques fallara. Esta opción es util para personajes controlables.
        if (hasDynamicBody)
            bodyDefinition.fixedRotation = true;

        bodyDefinition.position.set(box2DBody.position.x /PIXELS_PER_METER, box2DBody.position.y /PIXELS_PER_METER);

        return box2DBody.world.createBody(bodyDefinition);
    }


    //    En esta función preparo los valores iniciales que va a tener mi fixture.
    private static FixtureDef getPreparedFixtureDefinition(CircleShape circleShape, short categoryBit, short extraBit, boolean isBigMario) {

        FixtureDef fixtureDef = new FixtureDef();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        fixtureDef.shape = circleShape;

//  100 es una densidad recomendable, si la densidad es muy alta nuestro personaje no va a poder saltar
        //  Debido a que mi big mario tendrá 2 fixture, por lo tanto, va a pesar el doble. Asi que reduciré
//  la densidad del primer fixture a 50 que es la mitad del fixture de little mario
        fixtureDef.density = isBigMario ? 50 : 100;
        fixtureDef.friction = 0.1f;

        //  Cada fixture en box2D tiene un filtro, el filtro tiene una categoría y una mask, la categoría es para
        //  indicar que este fixture, es mario, brick o un coin y el mask representa con cuáles fixture este fixture
        //  puede colisionar, los filtros se indican con bits, en este caso utilizaremos variables, short Y sus valores
        //  serán potencias de 2 para diferenciar los filtros. Finalmente, indico mi categoría y le indico
        //  a este fixture que será mario.
        fixtureDef.filter.categoryBits = categoryBit;

//        Aqui defino con que mi fixture de mario podrá colisionar, lo hare con O lógicos simples Indicamos que mario
//        pueda colisionar con todos los bits exceptuando el Destroyed_bit, pues cuando un fixture tenga este bit
//        no queremos que mario colisione, pues técnicamente este objeto está destruido.
        // Nuestros enemigos podrán colisionar entre ellos mismos y también con mario, por eso agrego,
        // MARIO_BIT, Cuando en el categoryBit envió a Enemy_bit.
        fixtureDef.filter.maskBits = (short) (GROUND_BIT | ITEM_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT | ENEMY_BIT | extraBit);

        return fixtureDef;
    }


    public static Body createEnemyBody(Box2DBody box2DBody){

        Body body = getPreparedBody(box2DBody, true);

        CircleShape circleShape = new CircleShape();

        FixtureDef fixtureDef = getPreparedFixtureDefinition(circleShape, ENEMY_BIT, MARIO_BIT, false);

        circleShape.dispose();

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        fixtureDef.shape = getCustomEnemyHeadShape();

//        Agregar rebote, para cuando mario le salte encima al enemigo
        fixtureDef.restitution = 1;
        fixtureDef.filter.categoryBits = ENEMY_HEAD_BIT;

//        Deseamos poder acceder a los datos de mi clase goomba o turtle, a la hora de la colisión.
        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        return body;
    }


    public static Body createItemBody(Box2DBody box2DBody){

        Body body = getPreparedBody(box2DBody, true);

        FixtureDef fixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        fixtureDef.shape = circleShape;

        fixtureDef.filter.categoryBits = ITEM_BIT;

        fixtureDef.filter.maskBits = MARIO_BIT | OBJECT_BIT | GROUND_BIT | COIN_BIT | BRICK_BIT;

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        circleShape.dispose();

        return body;
    }

    private static PolygonShape getCustomEnemyHeadShape() {
        //        Crear la cabeza del goomba para detectar cuando mario le salte encima
        PolygonShape head = new PolygonShape();

//        Aqui definiremos una forma personalizada de un trapecio invertido, para la cabeza de nuestro goomba.
        Vector2[] vertices = new Vector2[4];

        vertices[0] = new Vector2(-5 , 8).scl(1/ PIXELS_PER_METER);
        vertices[1] = new Vector2(5 , 8).scl(1/ PIXELS_PER_METER);
        vertices[2] = new Vector2(-3 , 3).scl(1/ PIXELS_PER_METER);
        vertices[3] = new Vector2(3 , 3).scl(1/ PIXELS_PER_METER);

//        De esta forma indicaremos la forma personalizada que tendrá nuestro polygonShape.
        head.set(vertices);

        return head;
    }


    public static Fixture createStaticBody(Box2DBody box2DBody){

        Body body = getPreparedBody(box2DBody, false);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 /PIXELS_PER_METER, box2DBody.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

//        Todo tengo que separar esto, pues no todos los elementos creados por esta función, pueden tener object_bit
//        Por ejemplo los coin y el ground no deberían de tener un object_bit, evaluaré esto para mas adelante.
        fixtureDef.filter.categoryBits = OBJECT_BIT;

    //A static body has zero mass by definition, so the density is not used in this case. The default density is zero.
        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();

//  Como desde mi fixture puedo obtener también el body, solo debo de retornar el fixture
        return fixture;
    }

    public static Vector2 getSimplifiedCurrentPosition(Body body) {
        //   Debido a que cuando obtengo las posiciones en X e Y del vector position del body,
//        tengo que multiplicar estas coordenadas por mi pixels_per_meter, debido a que si las obtengo del body quiere
//        decir que ya se le ha aplicado esta división
        return new Vector2(body.getPosition().x * PIXELS_PER_METER, body.getPosition().y * PIXELS_PER_METER);
    }
}
