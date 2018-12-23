package creature;
import javafx.scene.image.Image;
import world.BattleField;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Creature implements Comparable, Runnable {

    protected String name;
    private Creature creature;
    private Location location;
    private Image image;
    private Random random;
    private Status status = Status.LIVE;


    protected BattleField battleField;

    /**基本方法*/
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


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*获得两个生物的距离*/
    public int distanceTo (Creature anotherCreature) {
        Location another = anotherCreature.getLocation();
        return Math.abs(another.getX() - this.location.getX()) + Math.abs(another.getY() - this.location.getY());
    }


    /* 设计模式：模板方法（Template Method） */
    public final void run(){
        System.out.println(this.toString() + "线程启动");
        try{
            while(true){
                synchronized (battleField) {
                    switch (status){
                        case LIVE:  move(); break;
                        case FIGHTING: fight(); break;
                        case DEAD: dead(); break;
                        default: ;
                    }
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (Exception e){
            System.out.println(this.toString() + "线程切断:"+this.status);
        }
    }


    /**作战方法*/
    protected void move(){
        switch (new Random().nextInt(5)){
            case 0:
            case 1:
                this.moveAStep(Direction.LEFT);
                break;
            case 2:
                this.moveAStep(Direction.RIGHT);
                break;
            case 3:
                this.moveAStep(Direction.UP);
                break;
            case 4:
                this.moveAStep(Direction.DOWN);
                break;
            default: ;
        }
    }

    private final int step = 5;
    protected final void moveAStep(Direction d){
        int nx = 0, ny = 0;
        switch (d) {
            case LEFT:
                nx -= step;
                break;
            case RIGHT:
                nx += step;
                break;
            case UP:
                ny -= step;
                break;
            case DOWN:
                ny += step;
                break;
            default: ;
        }
       /* CheckStatus checkStatus = checkForward(nx,ny);
        if(checkStatus==CheckStatus.NORMAL)
            this.setLocation(new Location(this.location.getX() + nx, this.location.getY() + ny));*/
    }

    /* 对当前前进方向进行检测是否会与其他Creature相撞，offsetX,offsetY均为偏移量 */
    /*public synchronized CheckStatus checkForward(int offsetX, int offsetY){
        boolean flag = this instanceof GoodCharacter;
        for(Creature ct:room.getCreatures())
            if(ct!=this && ct.getStatus()==Status.RUNNING && this.isCollidesWith(ct, offsetX, offsetY)) {
                if(flag == (ct instanceof GoodCharacter)) {
                    this.position.setPosition(this.x()-offsetX, this.y()-offsetY);
                    return CheckStatus.FRIEND;
                } else {
                    boolean alive = new Random().nextBoolean();
                    ct.setFight(alive);
                    this.setFight(!alive);
                    return CheckStatus.ENEMY;
                }
            }
        return CheckStatus.NORMAL;
    }

    *//* 新位置上是否会和指定Creature相撞 *//*
    public boolean isCollidesWith(Creature ct, int offsetX, int offsetY){
        return this.x()+this.getWidth()>ct.x() && ct.x()+ct.getWidth()>this.x()
                && this.y()+10>ct.y() && ct.y()+10>this.y();
    }*/

    public void setImage(Image image) {
        this.image = image;
    }

    protected enum CheckStatus{
        NORMAL, FRIEND, ENEMY
    }
    protected void fight(){

    }
    protected void dead(){

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



    /**
     * 枚举类
     */
    enum Status {
        LIVE, FIGHTING, DEAD
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN
    }


/*
     打印当前名字+位置
    public void printCreature(){
        System.out.print(name+":  (" +x+","+y+")");
    }
*/







}
