package ui.graphic;

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
import gameLogic.states.PrepareGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerInfoPanel extends JPanel implements Observer{
    private GameModel gm;
    
    private Box vertical;
    private Box verticalAuction;
    private Box verticalDecision;
    
    private final JLabel title = new JLabel();
    private final JLabel player = new JLabel();
    private final JLabel coins = new JLabel();
    private final JTextArea cardDescription = new JTextArea();
    private final JButton checkBt = new JButton("Check");
    private final JComboBox playersCombo = new JComboBox();
    private final JButton defPlayerBt = new JButton("Send");
    
    private final JPanel auctionPanel;
    private final JPanel cardDecisionPanel;

    PlayerInfoPanel(GameModel gm) {
        this.gm = gm;
        
        gm.addObserver(this);
        
        auctionPanel = new AuctionPanel(gm);
        cardDecisionPanel = new CardDecisonPanel(gm);
        
        buildLayout();
        
        setAlignmentX(RIGHT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.LIGHT_GRAY);
    }
    
    private void buildLayout() {
        vertical = Box.createVerticalBox();
        verticalAuction = Box.createVerticalBox();
        verticalDecision = Box.createVerticalBox();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        vertical.add(title);
        vertical.add(Box.createGlue());
        vertical.add(Box.createVerticalStrut(5));
        
        vertical.add(new JLabel("Current Player:"));
        vertical.add(player);
        vertical.add(Box.createGlue());
        
        vertical.add(Box.createVerticalStrut(5));
        vertical.add(coins);
        vertical.add(Box.createGlue());
        
        vertical.add(Box.createVerticalStrut(20));
        cardDescription.setEditable(false);
        cardDescription.setBackground(Color.LIGHT_GRAY);
        cardDescription.setPreferredSize(new Dimension(160, 50));
        vertical.add(cardDescription);
        //vertical.add(Box.createGlue());
        vertical.add(Box.createVerticalStrut(5));
        vertical.add(checkBt);
        vertical.add(playersCombo);
        vertical.add(defPlayerBt);
        playersCombo.setVisible(false);
        defPlayerBt.setVisible(false);
        add(vertical);
        
        verticalAuction.add(auctionPanel);
        add(verticalAuction);
        
        verticalDecision.add(cardDecisionPanel);
                
        add(verticalDecision);
        
        registerListeners();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (!(gm.getState() instanceof PrepareGame) && !(gm.getState() instanceof Auction) &&
                !(gm.getState() instanceof OR) && !(gm.getState() instanceof AND)) {
            if (gm.getPreviousState() instanceof Auction) {
                verticalAuction.setVisible(true);
            }
            vertical.setVisible(true);
            title.setText("- Player Information -");
            player.setText("" + gm.getCurrentPlayer().getId());
            player.setForeground(gm.getCurrentPlayer().getGraphicalColor());

            coins.setText("Coins: " + gm.getCurrentPlayer().getCoins());
            
            if (!(gm.getState() instanceof PickCard)) {
                cardDescription.setVisible(true);
                checkBt.setVisible(true);
                cardDescription.setText("Card Description:\n " + gm.getCurrentPlayer().getLastCard());
                
                if (gm.getState() instanceof NeutralizeArmy.InsertPlayer) {
                    cardDescription.setVisible(false);
                    playersCombo.setVisible(true);
                    defPlayerBt.setVisible(true);
                    
                    for (int i = 0; i < gm.getPlayers().size(); i++)
                        playersCombo.addItem("Player " + (i+1));
                }
            } else {
                cardDescription.setVisible(false);
                checkBt.setVisible(false);
                playersCombo.setVisible(false);
                defPlayerBt.setVisible(false);
            }
        } else if (gm.getState() instanceof Auction) {
            vertical.setVisible(false);
            verticalAuction.setVisible(true);
        } else if (gm.getState() instanceof AND || gm.getState() instanceof OR) {
            vertical.setVisible(false); 
        }
        
        if (gm.getEndGameFlag()) {
            gm.defineGamee(0);
            if (gm.getState() instanceof PrepareGame.DefineJokers) {
                // JOKERS
                // using auction panel to do the jokers insertion
                vertical.setVisible(false);
                verticalDecision.setVisible(false);
                auctionPanel.setVisible(true);
            } else {
                // SHOW SCORES
                if (gm.getCurrentPlayer() != null) {
                    vertical.setVisible(true);
                    title.setText("- Game Over -");
                    player.setText("Winner is Player " + gm.getCurrentPlayer().getId() + "!");
                    coins.setVisible(false);
                    checkBt.setVisible(false);
                } else {
                    player.setText("Game tied! No one wins!");
                }
                cardDescription.setText("");
                for (Player aux : gm.getPlayers()) {
                    cardDescription.setText(cardDescription.getText() + "\n" + "Score " + aux.getId() + ": " + aux.getScore());
                }
            }
        }
    }

    private void registerListeners() {
        checkBt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gm.defineAction(0);
            }
        });
        
        defPlayerBt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gm.defineAction(playersCombo.getSelectedIndex() + 1);
            }
        });
    }
}
