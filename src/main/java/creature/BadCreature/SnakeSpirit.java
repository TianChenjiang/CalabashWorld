package creature.BadCreature;

import creature.Creature;
import javafx.scene.image.Image;

public class SnakeSpirit extends Creature {
    @Override
    public String toString() {
        return "大蛇精";
    }

    @Override
    public Image getImage() {
        String url = "pic/" + "蛇精" + ".png";
        return new Image(url);
    }
}
