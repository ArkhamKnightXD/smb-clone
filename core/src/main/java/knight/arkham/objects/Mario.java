package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Mario extends Sprite {

    private final Body body;

    public Mario(World world) {

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(5 / PIXELS_PER_METER);

        body = BodyHelper.createDynamicBody(new Box2DBody(32,32,50, world, circleShape));
    }

    public Body getBody() {return body;}
}
