package ui.graphic;

import gameLogic.Card;
import gameLogic.models.GameModel;
import gameLogic.states.PickCard;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CardsPanel extends JPanel implements Observer {
    private GameModel gm;
    private JButton b;
    
    private int cardSelectedIndex;

    private final ArrayList<JButton> cardsImgs = new ArrayList<>();
    
    CardsPanel(GameModel gm) {
        this.gm = gm;
        
        gm.addObserver(this);
        
        setLayout(new FlowLayout());

        buildLayout();

        setBackground(Color.LIGHT_GRAY);
    }
    
    private void buildLayout() {
        // Instanciate arraylist
        while(cardsImgs.size() < 6)
            cardsImgs.add(new JButton());
        
        for (JButton aux : cardsImgs)
            add(aux);
        
        registerListeners();
    }
    
    private void fillCards() {
        ArrayList <Card> cards = gm.getTableCards();
        
        int i = 1;
        for (JButton aux : cardsImgs) {
            placeImg(aux, cards.get(i-1).getId());
            i++;
        }
    }
    
    private void placeImg(JButton aux, int i) {
        BufferedImage img = null;
        try {
            if (i < 10)
                img = ImageIO.read(Resources.getResourceFile("resources/images/cards/card0" + i + ".jpg"));
            else
                img = ImageIO.read(Resources.getResourceFile("resources/images/cards/card" + i + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            return;
        }

        aux.setMargin(new Insets(0,0,0,0));
        aux.setFocusPainted(false);
        aux.setIcon(new ImageIcon(img.getScaledInstance(126, 200,Image.SCALE_SMOOTH)));
    }
    
    private void registerListeners() {
        for (int i = 0; i < cardsImgs.size(); i++) {
            cardsImgs.get(i).addMouseListener(new MouseAdapter() {
                int n = cardSelectedIndex;
                @Override
                public void mouseClicked(MouseEvent e) {
                    gm.defineCard(n);
                }
            });
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        fillCards();
        if (gm.getState() instanceof PickCard)
            // update table cards
            fillCards();
        //else
        //    for (JButton aux : cardsImgs) 
        //        aux.setEnabled(false);
    }
}