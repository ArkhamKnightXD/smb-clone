package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.sprites.Mario;
import knight.arkham.sprites.enemies.Enemy;
import knight.arkham.sprites.items.Item;
import knight.arkham.sprites.tileObjects.InteractiveTileObject;
import static knight.arkham.helpers.Constants.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        manageCollision(fixtureA, fixtureB);


//        Forma inicial de colisión dejare esto aqui para tener este código de ejemplo para cualquier otro proyecto.
//        if (fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head") {

//            Aqui realizo evaluación sobre cuál objeto será la cabeza de mario y otro el objeto.
//            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
//            Fixture object = head == fixtureA ? fixtureB : fixtureA;

        // La segunda condición nos retornará true si el objeto extiende de interactiveTileObject
//            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {

//                De esta forma ejecuto la función onHeadHit.
//                ((InteractiveTileObject) object.getUserData()).onHeadHit();
//            }
    }


    private static void manageCollision(Fixture fixtureA, Fixture fixtureB) {

        //  Aqui estoy juntando los 2 categoryBits de los objetos que colisionen. Esto básicamente, sumará los valores
//        Y como los categoryBits están definidos como short, será una suma binaria.
        int collisionDefinition = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (collisionDefinition) {

//            Case válido para mis objetos coin y brick, Hice esto con if al principio, pero esta forma es más elegante.
            case MARIO_HEAD_BIT | BRICK_BIT:
            case MARIO_HEAD_BIT | COIN_BIT:
                if (fixtureA.getFilterData().categoryBits == MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixtureB.getUserData()).onHeadHit();

                else
                    ((InteractiveTileObject) fixtureA.getUserData()).onHeadHit();

                break;

            case ENEMY_HEAD_BIT | MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).hitOnHead();

                else
                    ((Enemy) fixtureB.getUserData()).hitOnHead();
                break;

            // Las colisiones se leen de esta forma. Si el enemy colisiona con un objeto.
            case ENEMY_BIT | OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocityOnXAxis();

                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocityOnXAxis();
                break;

//                Si hay 2 enemigos que colisionan, ambos deben de revertir la velocidad.
            case ENEMY_BIT:
                ((Enemy) fixtureA.getUserData()).reverseVelocityOnXAxis();
                ((Enemy) fixtureB.getUserData()).reverseVelocityOnXAxis();
                break;

//                Cuando el item choca con un objeto
            case ITEM_BIT | OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == ITEM_BIT)
                    ((Item) fixtureA.getUserData()).reverseVelocityOnXAxis();

                else
                    ((Item) fixtureB.getUserData()).reverseVelocityOnXAxis();
                break;

//            Cuando el item choca con Mario.
            case ITEM_BIT | MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits == ITEM_BIT)
                    ((Item) fixtureA.getUserData()).useItem((Mario) fixtureB.getUserData());

                else
                    ((Item) fixtureB.getUserData()).useItem((Mario) fixtureA.getUserData());
                break;

            case MARIO_BIT | ENEMY_BIT:
                Gdx.app.log("Mario", "Died");
                break;
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


