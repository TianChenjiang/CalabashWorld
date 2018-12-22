package creature;
import javafx.scene.image.Image;

import java.util.Random;


public class Creature implements Comparable, Runnable {

    protected String name;
    private Creature creature;
    private Location location;
    private Image image;
    private Random random;
    private Status status = Status.LIVE;

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


    public final void run() {
        try {

        } catch (Exception e) {

        }
    }


    public void move(Location location){
        int num = random.nextInt(4);
        switch (num) {
//            case 0 : this.move(new Location());
        }
    }


    /*synchronized来起到同步加锁*/
    public synchronized void isConfilct(int offsetx, int offsety){

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
