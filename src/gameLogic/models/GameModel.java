package gameLogic.models;

import gameLogic.Game;
import java.util.Observable;

public class GameModel extends Observable {
    Game game;

    public GameModel(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        sendNotification();
    }
    
    public void start() {
        game.start();
        sendNotification();
    }

    private void sendNotification() {
        setChanged();
        notifyObservers();
    }
}
