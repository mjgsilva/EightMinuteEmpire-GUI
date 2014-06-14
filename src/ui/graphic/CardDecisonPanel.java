package ui.graphic;

import gameLogic.Card;
import gameLogic.models.GameModel;
import gameLogic.states.AND;
import gameLogic.states.Auction;
import gameLogic.states.OR;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardDecisonPanel extends JPanel implements Observer{

    private final GameModel gm;

    private boolean initCombo = false;

    private int index = 0;

    private JLabel title = new JLabel();
    private JLabel player = new JLabel();
    private JComboBox action;
    private final JButton b = new JButton("Execute");

    private final ArrayList<Integer> bets = new ArrayList<>();

    public CardDecisonPanel(GameModel gm) {
        this.gm = gm;
        gm.addObserver(this);

        action = new JComboBox();

        buildLayout();

        setPreferredSize(new Dimension(200, 150));
        setBackground(Color.LIGHT_GRAY);
    }

    private void buildLayout() {
        Box vertical = Box.createVerticalBox();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        vertical.setAlignmentX(RIGHT_ALIGNMENT);

        vertical.add(title);

        vertical.add(Box.createVerticalStrut(5));

        vertical.add(new JLabel("Current Player:"));
        vertical.add(player);

        vertical.add(new JLabel("Select Action:"));
        vertical.add(Box.createVerticalGlue());
        vertical.add(action);
        vertical.add(Box.createVerticalGlue());
        action.setPreferredSize(new Dimension(100, 3));

        vertical.add(b);

        vertical.add(Box.createVerticalGlue());

        add(vertical);

        title.setText("- Select Action -");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!initCombo && (gm.getState() instanceof OR) || (gm.getState() instanceof AND)) {
            action.addItem("Check");
            Card c = gm.getCurrentPlayer().getLastCard();
            Map<Integer, Integer> actions = c.getActions();
            for (Map.Entry pairs : actions.entrySet()) {
                action.addItem(c.getActionString(Integer.parseInt(pairs.getKey().toString()), Integer.parseInt(pairs.getValue().toString())));
            }

            registerListeners();
            initCombo = true;
        }
        if (gm.getState() instanceof OR || gm.getState() instanceof AND) {
            setVisible(true);
            player.setText("" + gm.getPlayers().get(index).getId());
            player.setForeground(gm.getPlayers().get(index).getGraphicalColor());
        } else {
            initCombo = false;
            setVisible(false);
        }
    }

    private void registerListeners() {
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gm.defineCard(action.getSelectedIndex()+1);
            }
        });
    }
}
