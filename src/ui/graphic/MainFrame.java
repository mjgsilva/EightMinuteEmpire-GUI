package ui.graphic;

import gameLogic.Game;
import gameLogic.models.GameModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * 
 */
class MainFrame extends JFrame implements Observer {
    private GameModel gm;
    
    private JPanel center, south;
    private JPanel intro;
    
    public MainFrame() {
        gm = new GameModel(new Game());
        
        gm.addObserver(this);
        
        setFrameLayout();
        
        setSize(1100, 720);
        setTitle("Eight Minutes Empire");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setFrameLayout() {
        Container contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());
        
        intro = new IntroPanel(gm);
        
        center = new JPanel();
        center.setPreferredSize(new Dimension(1100, 600));
        center.setLayout(new FlowLayout());
        center.setBackground(Color.DARK_GRAY);
        center.setAlignmentX(CENTER_ALIGNMENT);
        center.setAlignmentY(CENTER_ALIGNMENT);
        center.add(intro);
        
        south = new JPanel();
        south.setLayout(new FlowLayout());
        south.setBackground(Color.LIGHT_GRAY);
        south.setPreferredSize(new Dimension(100, 200));
        south.add(new CardsPanel(gm));
        south.add(new PlayerInfoPanel(gm));
        south.setVisible(false);
        
        contentPane.add(center, BorderLayout.CENTER);
        contentPane.add(south, BorderLayout.SOUTH);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        south.setVisible(true);
        intro.setVisible(false);
        revalidate();
    }
}
