package ai;

import gameLogic.Card;
import gameLogic.Player;
import gameLogic.models.GameModel;
import gameLogic.states.AND;
import gameLogic.states.Auction;
import gameLogic.states.BuildCity;
import gameLogic.states.MoveArmyByLand;
import gameLogic.states.MoveArmyBySea;
import gameLogic.states.NeutralizeArmy;
import gameLogic.states.OR;
import gameLogic.states.PickCard;
import gameLogic.states.PlaceNewArmy;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ComputerAI implements Observer, Serializable {
    private GameModel gm;
    private ArrayList<Player> aiPlayers = new ArrayList<>();
    private Random random = new Random();

    public ComputerAI(GameModel gm, int n) {
        this.gm = gm;
        
        gm.addObserver(this);
        
        for (int i = 0; i < n; i++) {
            aiPlayers.add(gm.getPlayers().get(gm.getPlayers().size() - n + i));
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (aiPlayers.contains(gm.getCurrentPlayer())) {
            if (gm.getState() instanceof Auction) {
                if (aiPlayers.size() == gm.getPlayers().size()) {
                    ArrayList<Integer> bets = new ArrayList<>();
                    for (; bets.size() < aiPlayers.size(); bets.add(random.nextInt(aiPlayers.get(bets.size()).getCoins())));
                    gm.defineWinner(bets);
                } else {
                    ArrayList<Integer> bets = new ArrayList<>();
                    for (; bets.size() < gm.getPlayers().size(); bets.add(0));
                    gm.defineWinner(bets);
                }
            } else if (gm.getState() instanceof PickCard) {
                gm.defineCard(random.nextInt(6));
            } else if (gm.getState() instanceof OR || gm.getState() instanceof AND) {
                Card c = gm.getCurrentPlayer().getLastCard();
                Map<Integer, Integer> actions = c.getActions();
                Iterator it = actions.entrySet().iterator();
                int index = 0;
                while (it.hasNext()) {
                    index++;
                    Map.Entry pairs = (Map.Entry) it.next();
                }
                gm.defineCard(index);
            } else if (gm.getState() instanceof PlaceNewArmy) {
                for (int i = 1; i <= 25; i++) {
                    if (gm.getGame().getMap().getRegionById(i).checkCitiesOfPlayerOnRegion(gm.getCurrentPlayer()))
                        gm.defineAction(i);
                }
            } else if (gm.getState() instanceof BuildCity) {
                for (int i = 1; i <= 25; i++) {
                    if (gm.getGame().getMap().getRegionById(i).checkArmiesOfPlayerOnRegion(gm.getCurrentPlayer()))
                        gm.defineAction(i);
                }
            } else if (gm.getState() instanceof NeutralizeArmy) {
                int p = gm.getPlayers().get(random.nextInt(gm.getPlayers().size()) - 1).getId();
                for (int i = 1; i <= 25; i++) {
                    if (gm.getGame().getMap().getRegionById(i).checkArmiesOfPlayerOnRegion(gm.getPlayers().get(p-1))) {
                        gm.defineAction(i-1);
                        gm.defineAction(p);
                    }
                }
            } else if (gm.getState() instanceof MoveArmyByLand) {
                for (int i = 1; i <= 25; i++) {
                    if (gm.getGame().getMap().getRegionById(i).checkArmiesOfPlayerOnRegion(gm.getCurrentPlayer())) {
                        if (gm.getState() instanceof MoveArmyByLand.InsertDestiny)
                            gm.defineAction(random.nextInt(25));
                        else
                            gm.defineAction(i);
                    }
                }
            } else if (gm.getState() instanceof MoveArmyBySea) {
                for (int i = 1; i <= 25; i++) {
                    if (gm.getGame().getMap().getRegionById(i).checkArmiesOfPlayerOnRegion(gm.getCurrentPlayer())) {
                        if (gm.getState() instanceof MoveArmyBySea.InsertDestiny)
                            gm.defineAction(random.nextInt(25)+1);
                        else
                            gm.defineAction(i);
                    }
                }
            }
        }
    }
}