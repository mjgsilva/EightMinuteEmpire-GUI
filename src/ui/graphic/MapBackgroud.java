package ui.graphic;

import gameLogic.map.MapDataModel;
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
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

class MapBackground extends JPanel implements Observer{
    MapDataModel model;
    String overLocation=null;
    Shape highlight=null;
	
    MapBackground(MapDataModel themodel){
        themodel.addObserver(this);
        this.model=themodel;

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent ev){
                String s=model.getRegion(ev.getPoint());
                if(s!=overLocation){
                    overLocation=s;
                    repaint();
                }
            }
        });
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent ev){
                model.addNewPoint(getMousePosition());
            }
	});
    }

    @Override
    public void update(Observable o, Object arg) {
            repaint();	
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
        
        if(lst.size()>=1){
            Point p1=lst.get(0);
            int diameter=10;
            int x1=(int)(p1.getX());
            int y1=(int)(p1.getY());
            g.drawOval(x1-diameter/2, y1-diameter/2, diameter, diameter);
        }

        /* for(int i=0;i<lst.size()-1;i++)
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
        }
                */
        
        for(Shape a:model.getRegions())
        {
                Graphics2D g2d=(Graphics2D)g;
                //g2d.draw(a);
        }
        if(!model.getRegions().isEmpty())
                highlight=model.getRegions().get(model.getRegions().size()-1);

        if(highlight!=null)
        {
            Graphics2D g2d=(Graphics2D)g;
            g2d.setPaint(new Color(255,255,255,150));
            g2d.fill(highlight);
        }
    }		
}