package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.InteractiveTileObject;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {


        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head"){

//            Aqui realizo evaluación sobre cuál objeto será la cabeza de mario y otro el objeto.
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;

            // La segunda condición nos retornará true si el objeto extiende de interactiveTileObject
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){

//                De esta forma ejecuto la función onHeadHit.
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}


