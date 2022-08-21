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

    public static void createBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = box2DBody.isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDefinition.position.set(box2DBody.xPosition + box2DBody.width
                / 2, box2DBody.yPosition + box2DBody.height / 2);

        Body body = box2DBody.world.createBody(bodyDefinition);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 ,
                box2DBody.height /2);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        fixtureDef.density = box2DBody.density;

        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
