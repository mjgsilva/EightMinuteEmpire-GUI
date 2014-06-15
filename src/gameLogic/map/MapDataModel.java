package gameLogic.map;

import java.awt.Image;
import java.io.File;

public class MapDataModel extends RegionModel implements IMapData{
    private static final long serialVersionUID = 1L;
    public static int modelCount=0;
	
    public MapDataModel(IMapData m){super(m); modelCount++;}
    @Override
    public void setMapBackground(File f) {
        ((IMapData)r).setMapBackground(f);
        setChanged();
	notifyObservers();
    }
		
    public void setMapData(IMapData md){
	this.r=md;
	setChanged();
	notifyObservers();
    }
	
    public IMapData getMapData(){
        return (IMapData) r;	
    }
    
    @Override
    public Image getMapBackground() {
        return ((IMapData)r).getMapBackground();
    }
}
	


