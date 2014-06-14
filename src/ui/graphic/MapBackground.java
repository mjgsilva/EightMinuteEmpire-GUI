package ui.graphic;

import gameLogic.Army;
import gameLogic.City;
import gameLogic.Player;
import gameLogic.map.MapDataModel;
import gameLogic.map.Region;
import gameLogic.map.RegionData;
import gameLogic.models.GameModel;
import gameLogic.states.BuildCity;
import gameLogic.states.MoveArmyByLand;
import gameLogic.states.MoveArmyBySea;
import gameLogic.states.NeutralizeArmy;
import gameLogic.states.PickCard;
import gameLogic.states.PlaceNewArmy;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

class MapBackground extends JPanel implements Observer{
    MapDataModel model;
    String overLocation=null;
    int regionId;
    private GameModel gm;    
    
    MapBackground(MapDataModel themodel, final GameModel gm){
        themodel.addObserver(this);
        this.model=themodel;
        this.gm = gm;
        gm.addObserver(this);
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent ev){
                regionId = 0;
                    String region = model.getRegion(ev.getPoint());
                    if(region != null)
                        regionId = Integer.parseInt(region);
                if(gm.getState() instanceof PlaceNewArmy ||
                        gm.getState() instanceof MoveArmyByLand ||
                        gm.getState() instanceof MoveArmyBySea ||
                        gm.getState() instanceof BuildCity ||
                        gm.getState() instanceof NeutralizeArmy)
                {
                    gm.defineAction(regionId);
                    repaint();
                }
            }
	});
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (!(gm.getState() instanceof PickCard))
            repaint();	
    }

    private void fill(Graphics g) {
        Shape highlight=null;
        if(!model.getRegions().isEmpty()){
            for (int i = 0; i < model.getRegions().size(); i++) {
                if (model.getAreaName(model.getRegions().get(i)).equals(Integer.toString(regionId)))
                    highlight=model.getRegions().get(i);
            }
        }
        if(highlight!=null && !(gm.getGame().getState() instanceof PickCard))
        {
            Graphics2D g2d=(Graphics2D)g;
            g2d.setPaint(new Color(255,255,255,150));
            g2d.fill(highlight);
        } else {
            regionId = 0;
        }
        
        
        for (int i = 0; i < model.getRegions().size(); i++) {
            String r = model.getAreaName(model.getRegions().get(i));
            Point pCenter = (Point)model.getCenterPoint(r);
            int x = (int)pCenter.getX() - 36;
            int y = (int)pCenter.getY() - 36;
            int xx = 0;
            int yy = 0;
            
            for(Player tempPlayer:gm.getPlayers())
            {
                int z = 0;
                g.setColor(tempPlayer.getGraphicalColor());
                if(gm.getGame().getMap().getRegionById(Integer.parseInt(r)).checkArmiesOfPlayerOnRegion(tempPlayer))
                {
                    g.fillRect(x + xx*25, y + yy*25,18,18);
                    for(Army tempArmy:gm.getGame().getMap().getRegionById(Integer.parseInt(r)).getArmies())
                        if(tempArmy.getIdOfOwner() == tempPlayer.getId())
                            z++;
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(z), ((x+5) + xx*25), ((y+14)+yy*25));
                }
                
                if(gm.getGame().getMap().getRegionById(Integer.parseInt(r)).checkCitiesOfPlayerOnRegion(tempPlayer))
                {
                    z = 0;
                    xx++;
                    if (xx == 3) {
                        yy++;
                        xx = 0;
                    }
                    g.setColor(tempPlayer.getGraphicalColor());
                    g.fillOval(x + xx*25, y + yy*25,18, 18);
                    for(City tempCity:gm.getGame().getMap().getRegionById(Integer.parseInt(r)).getCities())
                        if(tempCity.getIdOfOwner() == tempPlayer.getId())
                            z++;
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(z), ((x+5) + xx*25), ((y+14)+yy*25));
                }
                
                z = 0;
                xx++;
                if (xx == 3) {
                    yy++;
                    xx = 0;
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x=0,y=0;
        List<Point> lst=model.getPoints();
        Image img=model.getMapBackground();

        if(img!=null)
            g.drawImage(img, x, y,  null);
        else
            g.drawString("Abra uma imagem antes de continuar", x+10, y+10);

        if(overLocation!=null){
            g.drawString(overLocation, x+10, y+10);
        }
        
        /* if(lst.size()>=1){
            Point p1=lst.get(0);
            int diameter=10;
            int x1=(int)(p1.getX());
            int y1=(int)(p1.getY());
            g.drawOval(x1-diameter/2, y1-diameter/2, diameter, diameter);
        }

         for(int i=0;i<lst.size()-1;i++)
        {
            Point p1=lst.get(i);
            Point p2=lst.get(i+1);

            int x1=(int)(p1.getX());
            int x2=(int)(p2.getX());
            int y1=(int)(p1.getY());
            int y2=(int)(p2.getY());

            g.drawLine(x1, y1, x2, y2);
            int diameter=10;
            g.drawOval(x2-diameter/2, y2-diameter/2, diameter, diameter);	
        } */
         fill(g);
    }		
}