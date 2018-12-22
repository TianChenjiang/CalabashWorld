package world;

import javax.security.auth.Refreshable;

public class Refresh implements Runnable{

    private Controller controller;

    public Refresh(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {

        }

    }

    public void refreshUI(BattleField battleField) {

    }
}
