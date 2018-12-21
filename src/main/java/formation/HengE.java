package formation;

import world.*;
import creature.Creature;

public class HengE implements FormationImp {
    @Override
    public void arrange(BattleField battleField, Queue queue, Location location ) {
        int row = battleField.getRow();
        int column = battleField.getColumn();
        Creature[] creatures = queue.getCreatures();
        location.setEmpty(false);


        int location_x = location.getX();
        int location_y = location.getY();

        int num = creatures.length;

        for(int i=location.getX(),count=0;
            i<location.getX()+num&&count<num; count++,i++){
            Location location1 = new Location(location_x, location_y);

            battleField.addCreature(creatures[count],location1);
            creatures[count].setLocation(location1);
            location1.setEmpty(false);
            if(count%2 == 0) {
                location_x++;
                location_y++;
            }else {
                location_x++;
                location_y--;
            }
        }
    }
}
