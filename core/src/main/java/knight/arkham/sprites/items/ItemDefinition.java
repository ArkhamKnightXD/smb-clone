package knight.arkham.sprites.items;

import com.badlogic.gdx.math.Vector2;

public class ItemDefinition {

    public Vector2 position;

//    Utilizaré una clase generica, para poder recibir clases de cualquier tipo.
    public Class<?> classType;

    public ItemDefinition(Vector2 position, Class<?> classType) {
        this.position = position;
        this.classType = classType;
    }
}
