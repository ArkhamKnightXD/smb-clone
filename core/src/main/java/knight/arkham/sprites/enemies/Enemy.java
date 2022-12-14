package knight.arkham.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.screens.GameScreen;
import knight.arkham.sprites.player.Mario;

public abstract class Enemy extends Sprite {

    protected final GameScreen gameScreen;

    public Body body;

    protected Vector2 velocity;

    public Enemy(GameScreen gameScreen, Vector2 position) {

        this.gameScreen = gameScreen;

//        Como indico la posición mediante esta función, puedo utilizar las funciones getX and GetY en otros lugares.
        setPosition(position.x, position.y);

        defineEnemyBody();

        velocity = new Vector2(1,0);

//        Esto pone el body de este objeto a dormir, en otras palabras vuelve este objeto static,
//        por lo tanto, este body no será calculado por mi world
        body.setActive(false);
    }

    public void reverseVelocityOnXAxis(){

        velocity.x = -velocity.x;
    }

    protected abstract void defineEnemyBody();
    public abstract void hitOnHead(Mario mario);
    public abstract void update(float deltaTime);
}
