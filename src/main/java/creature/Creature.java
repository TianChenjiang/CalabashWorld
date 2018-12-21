package creature;
import javafx.scene.image.Image;
import jdk.nashorn.internal.objects.annotations.Getter;
import world.*;


public class Creature implements Comparable, Runnable {

    protected String name;
    private Creature creature;
    private Location location;
    private Image image;

    public String toString(){return name;}

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        location.setLocation_creature(this);
    }

    public void setCreature(Creature creature){
        this.creature = creature;
    }


    //另外的方法

    @Override
    public boolean compareTo(Comparable another) {
        return false;
    }

    public Image getImage() {
        return image;
    }

    /*获得两个生物的距离*/
    public int distanceTo (Creature anotherCreature) {
        Location another = anotherCreature.getLocation();
        return Math.abs(another.getX() - this.location.getX()) + Math.abs(another.getY() - this.location.getY());
    }

    @Override
    public final void run() {
        try {

        } catch (Exception e) {

        }
    }

    /*synchronized来起到同步加锁*/
    public synchronized void isConfilct(int offsetx, int offsety){

    }


    /**
     * 枚举类
     */
    enum Status {
        LIVE, FIGHTING, DEAD
    }

    enum Direaction {
        LEFT, RIGHT, UP, DOWN
    }


/*
     打印当前名字+位置
    public void printCreature(){
        System.out.print(name+":  (" +x+","+y+")");
    }
*/







}
