package creature.BadCreature;

import creature.Creature;
import javafx.scene.image.Image;

public class ScorpionEssence extends Creature {
    @Override
    public String toString(){
        return "蝎子精";
    }

    @Override
    public Image getImage() {
        String url = "pic/" + "蝎子精" + ".png";
        return new Image(url);
    }
}
