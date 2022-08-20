package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class BodyHelper {

    public static void createStaticBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type =  BodyDef.BodyType.StaticBody;

        Body body = box2DBody.world.createBody(bodyDefinition);

        body.createFixture(box2DBody.polygonShape, 1000);
    }

    public static Body createBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = box2DBody.isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDefinition.position.set(box2DBody.xPosition / Constants.PIXELS_PER_METER,
                box2DBody.yPosition /Constants.PIXELS_PER_METER);

        bodyDefinition.fixedRotation = true;

        Body body = box2DBody.world.createBody(bodyDefinition);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 / Constants.PIXELS_PER_METER,
                box2DBody.height /2 /Constants.PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        fixtureDef.density = box2DBody.density;
        fixtureDef.friction = 0;

        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }
}
