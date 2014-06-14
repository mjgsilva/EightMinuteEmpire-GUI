package ui.graphic;

import gameLogic.Game;
import gameLogic.map.MapDataModel;
import gameLogic.models.GameModel;
import gameLogic.states.PickCard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MainFrame extends JFrame implements Observer{
    private GameModel gm;
    private MapDataModel mdm;
    private JPanel center, south;
    private JPanel intro, map;
    private CardsPanel cards;
    private PlayerInfoPanel pInfoPanel;
    
    public MainFrame() {
        gm = new GameModel(new Game());
        mdm = new MapDataModel(null);
        gm.addObserver(this);
        
        setFrameLayout();
        
        setSize(1100, 770);
        setTitle("Eight Minutes Empire");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private MainFrame(GameModel gm) {
        this.gm = gm;
        mdm = new MapDataModel(null);
        this.gm.addObserver(this);
        
        setFrameLayout();
        
        setSize(1100, 770);
        setTitle("Eight Minutes Empire");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gm.sendNotification();
    }
       
    private void setFrameLayout() {
        Container contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());
        
        intro = new IntroPanel(gm);
        map = new MapPanel(gm,mdm);
        cards = new CardsPanel(gm);
        pInfoPanel = new PlayerInfoPanel(gm);
        buildMenu();
        center = new JPanel();
        center.setPreferredSize(new Dimension(1100, 600));
        center.setLayout(new FlowLayout());
        center.setBackground(Color.DARK_GRAY);
        center.setAlignmentX(CENTER_ALIGNMENT);
        center.setAlignmentY(CENTER_ALIGNMENT);
        center.add(intro);
        center.add(map);
        map.setVisible(false);
        
        south = new JPanel();
        south.setLayout(new FlowLayout());
        south.setBackground(Color.LIGHT_GRAY);
        south.setPreferredSize(new Dimension(100, 200));
        south.add(cards);
        south.add(pInfoPanel);
        south.setVisible(false);
        
        contentPane.add(center, BorderLayout.CENTER);
        contentPane.add(south, BorderLayout.SOUTH);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        south.setVisible(true);
        intro.setVisible(false);
        map.setVisible(true);
        revalidate();
    }
    
    final private void buildMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem saveGameMenu = new JMenuItem("Save Game");
        JMenuItem loadGameMenu = new JMenuItem("Load Game");
        JMenuItem exitMenu = new JMenuItem("Exit");
        file.add(saveGameMenu);
        file.add(loadGameMenu);
        file.add(exitMenu);
        setJMenuBar(menu);
        
        exitMenu.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               System.exit(0);
           }
        });
        
        
        
        loadGameMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                JFileChooser jfc = new JFileChooser();
                int retval = jfc.showOpenDialog(MainFrame.this);
                if(retval == JFileChooser.APPROVE_OPTION)
                {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
                        gm = ((GameModel)(ois.readObject()));
                        new MainFrame(gm);
                        ois.close();
                    } catch (IOException ex) {
			ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
			ex.printStackTrace();
                    }
                }
            }
        });
        
        saveGameMenu.addActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                if(gm.getState() instanceof PickCard)
                {
                    JFileChooser jfc = new JFileChooser();
                    int retval = jfc.showSaveDialog(MainFrame.this);
                    if(retval == JFileChooser.APPROVE_OPTION)
                    {
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(jfc.getSelectedFile()));
                            oos.writeObject(gm);
                            oos.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } 
                    }
                }
            }
        });
    }
}
