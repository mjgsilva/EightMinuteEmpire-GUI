package ui.graphic;

import gameLogic.models.GameModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardsPanel extends JPanel implements Observer {
    private GameModel gm;
    JButton b;

    private ArrayList<JLabel> cardsImgs = new ArrayList<>();
    
    CardsPanel(GameModel gm) {
        this.gm = gm;
        
        setLayout(new FlowLayout());

       
        while(cardsImgs.size() < 6) {
            cardsImgs.add(new JLabel());
        }
        int i = 1;
        for (JLabel aux : cardsImgs) {
            placeImg(aux, i);
            add(aux);
            i++;
        }
        setBackground(Color.LIGHT_GRAY);
    }
    
    private void placeImg(JLabel aux, int i) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(Resources.getResourceFile("resources/images/cards/card00" + i + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        aux.setIcon(new ImageIcon(img.getScaledInstance(126, 200,Image.SCALE_SMOOTH)));
    }

    private ImageIcon createImageIcon(String path) {
        ClassLoader cl = this.getClass().getClassLoader();
        java.net.URL imgURL = cl.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println(" Nao encontrou o ficheiro: " + path);
            return null;
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
