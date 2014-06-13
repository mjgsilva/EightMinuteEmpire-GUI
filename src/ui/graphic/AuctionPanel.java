package ui.graphic;

import gameLogic.models.GameModel;
import gameLogic.states.Auction;
import gameLogic.states.PrepareGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

class AuctionPanel extends JPanel implements Observer{

    private final GameModel gm;
    
    private boolean initCombo = false;
    
    private int index = 0;
    
    private  JLabel title = new JLabel();
    private  JLabel player = new JLabel();
    private  JLabel coins = new JLabel();
    private JComboBox bet;
    private final JButton b = new JButton("Bet!");
    JComboBox resources = new JComboBox();
    
    
    private final ArrayList<Integer> bets = new ArrayList<>();
    
    public AuctionPanel(GameModel gm) {
        this.gm = gm;
        
        //this.bet = new JComboBox();
        
        gm.addObserver(this);
        
        bet = new JComboBox();
        
        buildLayout();
        
        setPreferredSize(new Dimension(200, 150));
        setBackground(Color.LIGHT_GRAY);
    }

    private void buildLayout() {
        Box vertical = Box.createVerticalBox();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        vertical.setAlignmentX(LEFT_ALIGNMENT);
        
        vertical.add(title);
        
        vertical.add(Box.createVerticalStrut(5));
        
        vertical.add(new JLabel("Current Player:"));
        vertical.add(player);
        
        vertical.add(coins);
        
        
        vertical.add(new JLabel("Select Bet:"));
        vertical.add(Box.createVerticalGlue());
        vertical.add(bet);
        vertical.add(Box.createVerticalGlue());
        bet.setPreferredSize(new Dimension(100, 3));
        
        vertical.add(b);
        
        vertical.add(Box.createVerticalGlue());
        
        add(vertical);
        
        title.setText("- Auction Phase -");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!initCombo) {
            for (int i = 0; i < gm.getPlayers().get(0).getCoins() + 1; i++)
                bet.addItem(Integer.toString(i));
            
            registerListeners();
            
            for (; bets.size() < gm.getPlayers().size(); bets.add(0));
            initCombo = true;
        }
        if (gm.getState() instanceof Auction) {
            player.setText("" + gm.getPlayers().get(index).getId());
            player.setForeground(gm.getPlayers().get(index).getGraphicalColor());

            coins.setText("Coins: " + gm.getPlayers().get(index).getCoins());
            
            
        } else if (gm.getState() instanceof PrepareGame.DefineJokers) {
            JButton changeCardButton = new JButton("Set");
            
            title.setText("- Define Jokers - ");
            player.setText("" + gm.getPlayers().get(index).getId());
            player.setForeground(gm.getPlayers().get(index).getGraphicalColor());
            coins.setVisible(false);
            bet.setVisible(false);

            resources.addItem("Jewelry");
            resources.addItem("Food");
            resources.addItem("Wood");
            resources.addItem("Iron");
            resources.addItem("Tools");
            add(resources);
            add(changeCardButton);
            
            changeCardButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gm.defineGame(resources.getSelectedIndex()+1);
                }
            });
            
        } else {
            setVisible(false);
        }
            
    }

    private void registerListeners() {
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index + 1 >= gm.getPlayers().size()) {
                    bets.set(index, bet.getSelectedIndex());
                    gm.defineWinner(bets);
                } else {
                    bets.set(index, bet.getSelectedIndex());
                    index++;
                }
                bet.setSelectedIndex(0);
                update(null, null);
            }
        });
    }
}
