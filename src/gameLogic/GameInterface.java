package gameLogic;

import gameLogic.states.StateInterface;

import java.util.ArrayList;

interface GameInterface {    
    public ArrayList<Card> getTableCards();
    
    public StateInterface getState();
    
    public StateInterface getPreviousState();
    
    public Player getCurrentPlayer();
    
    public ArrayList<Player> getPlayers();
    
    public boolean getEndGameFlag();
    
    public void defineGame(int n);
    
    public void defineCard(int i);
    
    public void defineWinner(ArrayList<Integer> bets);
    
    public void defineAction(int n);
}