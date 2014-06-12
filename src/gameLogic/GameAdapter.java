package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class GameAdapter extends Observable implements GameInterface, Serializable {

    @Override
    public void start() {}
    
    @Override
    public ArrayList<Card> getTableCards() {
        return null;
    }
}
