package ui.graphic;

import gameLogic.map.IMapData;
import gameLogic.map.MapDataModel;
import gameLogic.models.GameModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements Observer{
    private MapDataModel mm;
    private GameModel gm;
    private MapBackground mapBackgroundPanel;
    JLabel j = new JLabel();

    MapPanel(GameModel gm, MapDataModel mm)
    {
        this.gm = gm;
        this.mm = mm;
        mm.addObserver(this);
        
        buildLayout();
    }

    private void buildLayout() {
        loadMap(mm);
        
        mapBackgroundPanel = new MapBackground(mm,gm);
        mapBackgroundPanel.setPreferredSize(new Dimension(775,564));
        add(mapBackgroundPanel);
    }

    @Override
    public void update(Observable o, Object arg) {
        
    
    }
    
    private void loadMap(MapDataModel model)
    {
        URL url = Resources.getResourceFile("resources/map/eme.map");
        try {
            ObjectInputStream ois=new ObjectInputStream(url.openStream());
            IMapData mr=(IMapData)(ois.readObject());
            model.setMapData(mr);
            ois.close();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }    
    }
}
