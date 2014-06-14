package ui.graphic;

import ai.ComputerAI;
import gameLogic.models.GameModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {
    private GameModel gm;
    
    private Image background;
    
    private final JPanel south;
    private final JPanel center;
    
    private final JButton start = new JButton("Start Game");
    private final JButton exit = new JButton("Exit");

    IntroPanel(GameModel gm) {
        this.gm = gm;
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(Resources.getResourceFile("resources/images/eightminute-cover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel j = new JLabel();
        j.setIcon(new ImageIcon(img.getScaledInstance(450, 630,Image.SCALE_SMOOTH)));
        
        center = new JPanel();
        center.add(j);

        south = new JPanel();
        south.setPreferredSize(new Dimension(100, 50));
        south.setBackground(Color.LIGHT_GRAY);
        south.setLayout(new FlowLayout());
        south.add(start);
        south.add(exit);
        
        setLayout(new BorderLayout());
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
        
        registerListeners();
    }

    private void registerListeners() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show MessageBox
                Object[] options = {"2", "3", "4", "5"};
                Object val = JOptionPane.showInputDialog(null,
                        "Number of Players:", "Define Number of Players",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        options, options[0]);
                if (val != null) {
                    // Artificial Intelligence Number
                    Object[] optionsComputers = new Object[Integer.parseInt(val.toString()) + 1];
                    for (int i = 0; i < Integer.parseInt(val.toString()) + 1; i++) {
                        optionsComputers[i] = Integer.toString(i);
                    }
                    
                    Object val2 = JOptionPane.showInputDialog(null,
                            "Number of Computer Players:", "Define Number of Computer Players",
                            JOptionPane.INFORMATION_MESSAGE, null,
                            optionsComputers, optionsComputers[0]);

                    gm.defineGame(Integer.parseInt(val.toString()));
                    
                    if (val2 != null && Integer.parseInt(val2.toString()) != 0) {
                        gm.getGame().setAi(new ComputerAI(gm, Integer.parseInt(val2.toString())));
                        gm.setNumbAi(Integer.parseInt(val2.toString()));
                    }
                }
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
}
