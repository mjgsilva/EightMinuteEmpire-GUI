package gameLogic.map;

import ui.graphic.Resources;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class MapData extends RegionData implements IMapData{
    private static final long serialVersionUID = 1L;
    transient Image mapBackground;
    String mapBackgroundName;
	
    public void setMapBackground(File f){
	mapBackgroundName=f.getName();
	mapBackground=null;		
    }
	
    @Override
    public Image getMapBackground() {
        if(mapBackground==null && (mapBackgroundName!=null))
        try {
            System.out.println("opening map "+mapBackgroundName);
            URL imgURL=Resources.getResourceFile("resources/images/"+mapBackgroundName);
            mapBackground=ImageIO.read(imgURL);
	} catch (IOException e) {
            e.printStackTrace();
	}
            return mapBackground;
    }
}
