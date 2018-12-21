package creature.BadCreature;

import creature.Creature;
import javafx.scene.image.Image;

public class LittleGuys extends Creature {

    public LittleGuys[] initialGuys() {
        LittleGuys[] LGuys = new LittleGuys[7];
        for (int i = 0; i < LGuys.length; i++) {
            LGuys[i] = new LittleGuys();
        }
        return LGuys;
    }


    @Override
    public String toString() {
        return "小喽啰";
    }

    @Override
    public Image getImage() {
        String url = "pic/" + "小喽啰" + ".png";
        return new Image(url);
    }
}
