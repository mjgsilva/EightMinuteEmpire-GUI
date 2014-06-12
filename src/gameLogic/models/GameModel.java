package gameLogic.models;

import gameLogic.Card;
import gameLogic.Game;
import gameLogic.GameAdapter;
import java.io.Serializable;
import java.util.ArrayList;

public class GameModel extends GameAdapter implements Serializable {
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
    
    @Override
    public void start() {
        game.start();
        sendNotification();
    }

    @Override
    public ArrayList<Card> getTableCards() {
        ArrayList<Card> temp = game.getTableCards();
        sendNotification();
        return temp;
    }
    
    private void sendNotification() {
        setChanged();
        notifyObservers();
    }
}
