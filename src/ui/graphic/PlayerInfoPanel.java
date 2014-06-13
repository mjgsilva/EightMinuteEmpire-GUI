package ui.graphic;

import gameLogic.models.GameModel;
import gameLogic.states.Auction;
import gameLogic.states.PrepareGame;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerInfoPanel extends JPanel implements Observer {
    private GameModel gm;
    
    private Box vertical;
    private Box verticalAuction;
    
    private final JLabel title = new JLabel();
    private final JLabel player = new JLabel();
    private final JLabel coins = new JLabel();
    private final JLabel numberOfPlays = new JLabel();
    
    private final JPanel auctionPanel;

    PlayerInfoPanel(GameModel gm) {
        this.gm = gm;
        
        gm.addObserver(this);
        
        auctionPanel = new AuctionPanel(gm);
        
        buildLayout();
        
        setAlignmentX(RIGHT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.LIGHT_GRAY);
    }
    
    private void buildLayout() {
        vertical = Box.createVerticalBox();
        verticalAuction = Box.createVerticalBox();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        vertical.add(title);
        
        vertical.add(Box.createVerticalStrut(5));
        
        vertical.add(new JLabel("Current Player:"));
        vertical.add(player);
        
        vertical.add(Box.createVerticalStrut(25));
        
        vertical.add(new JLabel("Coins:"));
        vertical.add(coins);
        
        vertical.add(Box.createVerticalStrut(25));
        vertical.add(new JLabel("Remaining Plays:"));
        
        vertical.add(numberOfPlays);
        
        add(vertical);
        
        verticalAuction.add(auctionPanel);
        add(verticalAuction);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // TO DO: Depending on current state shows number of plays
        if (gm.getPreviousState() instanceof Auction)
            verticalAuction.setVisible(true);
        if (!(gm.getState() instanceof PrepareGame) && !(gm.getState() instanceof Auction)) {
            title.setText("- Player Information -");
            player.setText("" + gm.getCurrentPlayer().getId());
            player.setForeground(gm.getCurrentPlayer().getGraphicalColor());

            coins.setText("" + gm.getCurrentPlayer().getCoins());
        } else if (gm.getState() instanceof Auction) {
            vertical.setVisible(false);
            verticalAuction.setVisible(true);
        }
        
        // number of plays depends on current state, TO DO
    }
}
