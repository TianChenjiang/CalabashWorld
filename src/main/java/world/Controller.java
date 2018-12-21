package world;

import creature.BadCreature.SnakeSpirit;
import creature.GoodCreature.CalabashBrothers;
import creature.GoodCreature.Grandpa;
import creature.BadCreature.LittleGuys;
import formation.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import record.Record;
import sort.*;

import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;

public class Controller {

    private BattleField battleField;
    private Canvas canvas;
    private Controller controller;
    private ExecutorService goodCreatrueExe;
    private ExecutorService badCreatureExe;
    private ExecutorService drawer;
    private StackPane root;
    private Record record;

    public Controller(Stage primaryStage){
        battleField = new BattleField(20,20);
        this.initUI(primaryStage);
        showUI();
        this.play();
    }


    public void initUI(Stage primaryStage){
//        root.setAlignment(Pos.CENTER);
//        root.setPadding(new Insets(25, 25, 25, 25));
        root = new StackPane();
        canvas = new Canvas(1000, 500);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.drawImage(new Image("pic/background.png"), 1000, 500);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root,1000,500);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("BattleField.css").toExternalForm()); //设置背景

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("葫芦大战");

    }


    public void startGame() {
        this.play();
    }


    public void showUI() {
        battleField.print();
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        battleField.loadImage(gc);
    }

    //响应空格的按下 开始游戏
    public void keyBoardEvent(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

        }
    }


    public void play() {

        //GeneralCoordinator coordinator = new GeneralCoordinator();

        /*打印初始阵型*/
        System.out.println("初始化");
        battleField.print();

        try {
            /*各生物初始化*/
            Queue brotherQueue = new Queue(new CalabashBrothers().initialCB());

            /*老爷爷和蛇精初始化*/
            Grandpa grandpa = new Grandpa();
            SnakeSpirit snakeSpirit = new SnakeSpirit();

            /*小喽啰初始化*/
            Queue lGuysQueue = new Queue(new LittleGuys().initialGuys());


            /*各阵型初始化*/
            FormationImp changShe = new ChangShe();
            FormationImp heYi = new HeYi();
            FormationImp yanXing = new YanXing();
            FormationImp hengE = new HengE();


            /*放置葫芦娃*/
            heYi.arrange(battleField, brotherQueue, new Location(5, 5));
            new RandomSort().sort(brotherQueue);
            showUI();

            new BubbleSort().sort(brotherQueue);



            /*System.out.println("请输入小喽啰的阵型，例如hengE,heYi,yanXing");
            Scanner in = new Scanner(System.in);
            String formation = in.next();

            switch (formation) {
                case "hengE":
                    hengE.arrange(battleField, lGuysQueue, new Location(5, 8));
                    break;
                case "heYi":
                    heYi.arrange(battleField, lGuysQueue, new Location(8, 8));
                    break;
                case "yanXing":
                    yanXing.arrange(battleField, lGuysQueue, new Location(8, 8));
                    break;
                default:
            }*/

            hengE.arrange(battleField, lGuysQueue, new Location(10, 15));
            showUI();

//            添加老爷爷和蛇精
            battleField.addCreature(grandpa, new Location(3, 3));
            battleField.addCreature(snakeSpirit, new Location(10, 12));
            battleField.print();
            showUI();

            battleField.clear();
            battleField.print();


//            变换阵法输出
            /*System.out.println("变换阵法");

//            放置葫芦娃
            changShe.arrange(battleField, brotherQueue, new Location(5, 5));
            heYi.arrange(battleField, lGuysQueue, new Location(7, 7));*/





           /* System.out.println("请输入小喽啰的阵型，例如hengE,heYi,yanXing");
            String formation2 = in.next();
            switch (formation2) {
                case "hengE":
                    hengE.arrange(battleField, lGuysQueue, new Location(5, 8));
                    break;
                case "heYi":
                    heYi.arrange(battleField, lGuysQueue, new Location(7, 7));
                    break;
                case "yanXing":
                    yanXing.arrange(battleField, lGuysQueue, new Location(8, 8));
                    break;
                default:
            }*/

            showUI();

//            添加老爷爷和蛇精
       /*     battleField.addCreature(grandpa, new Location(3, 3));
            battleField.addCreature(snakeSpirit, new Location(2, 10));
            showUI();*/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
