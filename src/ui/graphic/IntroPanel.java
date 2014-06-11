package ui.graphic;

import gameLogic.models.GameModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {
    private GameModel gm;
    
    private Image background;
    
    private final JPanel south;
    
    private final JButton start = new JButton("Start Game");
    private final JButton exit = new JButton("Exit");

    IntroPanel(GameModel gm) {
        try {
            background = ImageIO.read(Resources.getResourceFile("resources/images/eightminute-cover.jpg"));
            setPreferredSize(new Dimension(background.getHeight(null), background.getWidth(null)));
            setMinimumSize(getMinimumSize());
            setBackground(Color.DARK_GRAY);
        } catch (IOException e) {
        }
        
        south = new JPanel();
        south.setPreferredSize(new Dimension(100, 50));
        south.setBackground(Color.BLACK);
        south.setLayout(new FlowLayout());
        south.add(start);
        south.add(exit);
        
        setLayout(new BorderLayout());
        add(south, BorderLayout.SOUTH);
        
        registerListeners();
    }

    private void registerListeners() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.start();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Yes", "No"};
                int op = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to exit?",
                        "Exit message",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (op == 0) {
                    System.exit(0);
                }
            }
        });
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (background != null) {
            g.drawImage(background, (getWidth()/2)-225, 25, 450, 700, null);
        } else {
            super.paintComponent(g);
        }
    }
}
