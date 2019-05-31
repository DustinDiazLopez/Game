package main;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Handler {
    List<GameObject> object = Collections.synchronizedList(new LinkedList<>());

    void clearAllEnemy() {
        this.object.removeIf(tempObject -> tempObject.getId() != ID.Player);
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject temporaryGameObject = object.get(i);
            temporaryGameObject.tick();
        }
    }

    public void render(Graphics graphics) {
        for (GameObject temporaryGameObject : object) {
            temporaryGameObject.render(graphics);
        }
    }

    public void addObject(GameObject gameObject) {
        this.object.add(gameObject);
    }

    public void removeObject(GameObject gameObject) {
        this.object.remove(gameObject);
    }
}
