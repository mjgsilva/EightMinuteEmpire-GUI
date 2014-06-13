package gameLogic.models;

import gameLogic.Card;
import gameLogic.Game;
import gameLogic.GameAdapter;
import gameLogic.Player;
import gameLogic.states.StateInterface;
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
    public ArrayList<Card> getTableCards() {
        ArrayList<Card> temp = game.getTableCards();
        return temp;
    }

    @Override
    public StateInterface getState() {
        return game.getState();
    }
    
    @Override
    public StateInterface getPreviousState() {
        return game.getPreviousState();
    }
    
    @Override
    public void defineGame(int n) {
        game.defineGame(n);
        sendNotification();
    }
    
    private void sendNotification() {
        setChanged();
        notifyObservers();
    }

    @Override
    public void defineCard(int i) {
        game.defineCard(i);
        sendNotification();
    }
    
    @Override
    public void defineWinner(ArrayList<Integer> bets) {
        game.defineWinner(bets);
        sendNotification();
    }
    
    @Override
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
    
    @Override
    public ArrayList<Player> getPlayers() {
        return game.getPlayers();
    }

    @Override
    public boolean getEndGameFlag() {
        return game.getEndGameFlag();
    }
}
