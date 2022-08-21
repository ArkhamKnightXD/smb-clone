package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class BodyHelper {

    public static Body createDynamicBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;

        bodyDefinition.position.set(box2DBody.xPosition / PIXELS_PER_METER, box2DBody.yPosition / PIXELS_PER_METER);

        Body body = box2DBody.world.createBody(bodyDefinition);

        body.createFixture(box2DBody.shape, 100);

        return body;
    }

    public static void createStaticBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;
        bodyDefinition.position.set(box2DBody.xPosition / PIXELS_PER_METER, box2DBody.yPosition / PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 /PIXELS_PER_METER , box2DBody.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        fixtureDef.density = box2DBody.density;

        Body body = box2DBody.world.createBody(bodyDefinition);

        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
