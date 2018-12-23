package world;

import creature.BadCreature.SnakeSpirit;
import creature.Creature;
import creature.GoodCreature.CalabashBrothers;
import creature.GoodCreature.Grandpa;
import creature.BadCreature.LittleGuys;
import creature.Location;
import formation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.*;
import javafx.util.Duration;
import record.Record;
import sort.*;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class Controller implements Runnable {

    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 500;

    private BattleField battleField;
    private Controller controller;
    private ExecutorService goodCreatureExe = Executors.newCachedThreadPool(); //正义势力线程池
    private ExecutorService badCreatureExe = Executors.newCachedThreadPool(); //邪恶势力线程池
    private ExecutorService controllerExe = Executors.newSingleThreadExecutor(); //绘画师单线程
    private Record record;
    private Timer timer;
    private Stage primaryStage;

    private Queue brotherQueue;
    private Queue lGuysQueue;

    @FXML
    private AnchorPane root = new AnchorPane() ;

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    private boolean isGaming = false;
    private boolean isControllerKilled = false;

    public Controller(Stage primaryStage){

        battleField = new BattleField(20,20);
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        record = new Record();
        this.primaryStage = primaryStage;

        try {
            System.out.println(getClass().getClassLoader().getResource("world/fxml/mainController.fxml"));
//            root = FXMLLoader.load(getClass().getClassLoader().getResource("world/fxml/mainController.fxml"));
            Scene scene = new Scene(root,1000,500);
             scene.getStylesheets().add(getClass().getClassLoader().getResource("world/css/mainController.css").toExternalForm()); //设置背景

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("葫芦大战");
            /*this.initStyle(StageStyle.TRANSPARENT);
            this.setScene(scene);*/
            this.initUI(primaryStage);

            scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.SPACE) {
//                        controllerExe.execute(controller);
                        run();
                    } else if (event.getCode() == KeyCode.E) {
                        System.out.println("Exit");
                        stopGame();
                    }

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void run(){
        record.beginRecord();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showUI();
                record.beginRecord();
            }
        }, 0, 100);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.play();
    }

    public void stopGame(){
        record.stopRecord();
        timer.cancel();
        ruinWorld();
    }


    public void showUI() {
//        canvas = new Canvas(1000, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        synchronized (battleField) {
            battleField.loadImage(gc);
        }
        try{
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearUI(){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(5,5,CANVAS_WIDTH, CANVAS_HEIGHT);
    }




/*//响应空格的按下 开始游戏
    class KeyAdapter extends java.awt.event.KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            if (isGaming) return;

            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                if (isGaming == false) {
                    isGaming = true;
                    run();
                }
                else {
                    isGaming = false;
                    stopGame();
                }
            } else {
                //回放
            }
        }

    }*/



    public void play() {


        /*打印初始阵型*/
        System.out.println("初始化");
        battleField.print();
        this.showUI();

        try {


            /**正义势力初始化*/
            brotherQueue = new Queue(new CalabashBrothers().initialCB());
            Grandpa grandpa = new Grandpa();

            goodCreatureExe = Executors.newCachedThreadPool();
            for (Creature bro : brotherQueue.getList()) {
                goodCreatureExe.execute(bro);
            }
            goodCreatureExe.execute(grandpa);
            System.out.println("正义线程启动");
//            goodCreatureExe.shutdown();

            /**邪恶阵营初始化*/
            lGuysQueue = new Queue(new LittleGuys().initialGuys());
            SnakeSpirit snakeSpirit = new SnakeSpirit();

            for (Creature lGuys : lGuysQueue.getList()) {
                badCreatureExe.execute(lGuys);
            }
            badCreatureExe.execute(snakeSpirit);
            System.out.println("邪恶线程启动");

//            badCreatureExe.shutdown();


            /**阵型初始化*/
            FormationImp changShe = new ChangShe();
            FormationImp heYi = new HeYi();
            FormationImp yanXing = new YanXing();
            FormationImp hengE = new HengE();


            /**放置葫芦娃*/
            heYi.arrange(battleField, brotherQueue, new Location(5, 5));
            new RandomSort().sort(brotherQueue);
            battleField.print();
            this.showUI();
            primaryStage.show();


            //随机选择小喽啰阵型
           /* int count = 0;
            while (count < 5) {
                int random = new Random().nextInt(2);
                switch (random) {
                    case 0:
                        hengE.arrange(battleField, lGuysQueue, new Location(10, 15));
                        break;
                    case 1:
                        heYi.arrange(battleField, lGuysQueue, new Location(8, 8));
                        break;
                    case 2:
                        yanXing.arrange(battleField, lGuysQueue, new Location(8, 8));
                        break;
                    default:
                }
*/

            Timeline ThreeSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int random = new Random().nextInt(2);
                    switch (random) {
                        case 0:
                            hengE.arrange(battleField, lGuysQueue, new Location(10, 15));
                            showUI();
                            break;
                        case 1:
                            heYi.arrange(battleField, lGuysQueue, new Location(8, 8));
                            showUI();
                            break;
                        default:
                            yanXing.arrange(battleField, lGuysQueue, new Location(8, 8));
                            break;
                    }
                }
            }));

            ThreeSecondsWonder.setCycleCount(2);
            ThreeSecondsWonder.play();


//            添加老爷爷和蛇精
            TimeUnit.SECONDS.sleep(3);
            battleField.addCreature(grandpa, new Location(3, 3));
            battleField.addCreature(snakeSpirit, new Location(10, 12));
            battleField.print();
            showUI();

            battleField.clear();
            int random2 =  new Random().nextInt(2);
            switch (random2) {
                case 0:
                    hengE.arrange(battleField, lGuysQueue, new Location(10, 15));
                    break;
                case 1:
                    heYi.arrange(battleField, lGuysQueue, new Location(8, 8));
                    break;
                case 2:
                    yanXing.arrange(battleField, lGuysQueue, new Location(8, 8));
                    break;
                default:
            }

            battleField.print();
            isControllerKilled = true;

            showUI();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //
    public void ruinWorld() {
        battleField.clear();
        showUI();

        goodCreatureExe.shutdown();
        System.out.println("正义生物线程结束");
        badCreatureExe.shutdown();
        System.out.println("邪恶生物线程结束");
        controllerExe.shutdown();
        System.out.println("控制器线程结束");

        System.exit(1);
    }

   /* class Player implements Runnable {
        private File recordFile;
        public Player(File recordFile){
            this.recordFile = recordFile;
        }
        public void run(){
            isGaming = true;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(recordFile));
                long begin = System.currentTimeMillis();
                while(true){
                    String recordInfoLine = bufferedReader.readLine();
                    if(recordInfoLine==null)
                        break;

                    String[] recordInfo = recordInfoLine.split(" ");
                    long timeOffset = Long.valueOf(recordInfo[0]); //时间
                    int size = Integer.valueOf(recordInfo[1]); //记录的Thing2D数
                    for(int i=0;i<size;i++) {
                        String[] thingInfo = bufferedReader.readLine().split(" ");
                        int x = Integer.valueOf(thingInfo[1]); //坐标x
                        int y = Integer.valueOf(thingInfo[2]); //坐标y
                        boolean alive = Boolean.valueOf(thingInfo[3]); //活/死
                        for (Creature ct : room.getCreatures())
                            if (thingInfo[0].equals(ct.toString())) {
                                ct.setPosition(x, y);
                                if(alive)
                                    ct.setStatus(Status.FIGHTING);
                                else
                                    ct.setStatus(Creature.Status.DEAD);
                                break;
                            }
                    }

                    while(System.currentTimeMillis()-begin<timeOffset)
                        ;
                    repaint();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            showing = false;
    }*/


    @FXML
    public void onRuinButtonClicked(ActionEvent actionEvent)
    {
        Platform.runLater(() ->
        {
            try
            {
//                ruinWorld();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });

    }

    public void initUI(Stage primaryStage){
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("battleField.fxml"));
        root.getChildren().add(canvas);
//        Scene scene = new Scene(root,1000,500);
        /*scene.getStylesheets().add(getClass().getClassLoader().getResource("world/css/mainController.css").toExternalForm()); //设置背景
        scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE){
                    controllerExe = Executors.newSingleThreadExecutor();
                    controllerExe.execute(controller);
                    isControllerKilled = false;
                    play();
                }
            }
        });*/

    }
}


