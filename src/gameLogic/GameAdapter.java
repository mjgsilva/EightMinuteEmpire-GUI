package gameLogic;

import gameLogic.states.StateInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class GameAdapter extends Observable implements GameInterface, Serializable {    
    @Override
    public ArrayList<Card> getTableCards() {
        return null;
    }

    @Override
    public StateInterface getState() {
        return null;
    }
    
    @Override
    public StateInterface getPreviousState() {
        return null;
    }
    
    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
    }

    @Override
    public abstract boolean getEndGameFlag();
    
    @Override
    public abstract void defineGame(int n);

    @Override
    public abstract void defineCard(int i);
    
    @Override
    public abstract void defineWinner(ArrayList<Integer> bets);
}
