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
    
    private JLabel player = new JLabel();
    private JLabel coins = new JLabel();
    private JLabel numberOfPlays = new JLabel();

    PlayerInfoPanel(GameModel gm) {
        this.gm = gm;
        
        gm.addObserver(this);
        
        buildLayout();
        
        setAlignmentX(RIGHT_ALIGNMENT);
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.LIGHT_GRAY);
    }
    
    private void buildLayout() {
        Box vertical = Box.createVerticalBox();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //player.setText("Jacinto Cobridor Alfa");
        vertical.add(new JLabel("Current Player:"));
        //player.setFont(new Font(player.getFont().getFontName(), Font.ITALIC, player.getFont().getSize()));
        vertical.add(player);
        vertical.add(Box.createVerticalStrut(25));
        vertical.add(new JLabel("Coins:"));
        //coins.setText("falido");
        vertical.add(coins);
        vertical.add(Box.createVerticalStrut(25));
        vertical.add(new JLabel("Remaining Plays:"));
        //numberOfPlays.setText("nenhuma");
        vertical.add(numberOfPlays);
        
        add(vertical);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // TO DO: Depending on current state shows number of plays
        if (!(gm.getState() instanceof PrepareGame) && !(gm.getState() instanceof Auction)) {
        player.setText("" + gm.getCurrentPlayer().getId());
            player.setForeground(gm.getCurrentPlayer().getGraphicalColor());

            coins.setText("" + gm.getCurrentPlayer().getCoins());
        }
        
        // number of plays depends on current state, TO DO
    }
}
