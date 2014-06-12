package gameLogic;

import gameLogic.states.StateInterface;

import java.util.ArrayList;

interface GameInterface {    
    public ArrayList<Card> getTableCards();
    
    public StateInterface getState();
    
    public StateInterface getPreviousState();
    
    public Player getCurrentPlayer();
    
    public void defineGame(int n);
    
    public void defineCard(int i);
}