/**
 * Created by VqP on 10/19/16.
 */

import java.awt.*;
import java.awt.event.*;

public class Pythagoras extends Frame {

    public static void main(String[] args) {new Pythagoras();}

    Pythagoras()
    {
        super("Pythagoras Tree");
        addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e){System.exit(0);}});
        setSize(800,800);
        add("Center", new CvDraw());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        show();

    }

}

class Point2D
{  int x, y;
    Point2D(int x, int y){this.x = x; this.y = y;}
}


class CvDraw extends Canvas
{
    int centerX, centerY, xP1 , yP1, xP2, yP2, limitCount = 0;
    float pixelSize, rWidth = 16.0F, rHeight = 16.0F;
    boolean flag = true;

    CvDraw()
    {
        addMouseListener(new MouseAdapter()
        {  public void mousePressed(MouseEvent evt)
        {
            if(flag) // flag to make sure there is no click event.
            {
                xP1 = evt.getX();
                yP1 = evt.getY();
                flag = false;
            }
            else
            {
                xP2 = evt.getX();
                yP2 = evt.getY();
                flag = true;
                repaint();
            }
        }
        });

    }

    void initgr()
    {  Dimension d = getSize();
        int maxX = d.width - 1, maxY = d.height - 1;
        pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
        centerX = maxX/2; centerY = maxY/2;
    }


    public void paint(Graphics g)
    {	super.paint(g);
        initgr();
        drawTree(g, xP1, yP1,xP2,yP2,12);
    }

    private void drawTree(Graphics g, int x1, int y1, int x2, int y2, int limit )
    {
        if (limit == limitCount)
            return;

        int u1 = x2-x1;
        int u2 = y2-y1;

        // Find all points required for the square and triangle
        Point2D A = new Point2D(x1, y1);
        Point2D B = new Point2D(x2, y2);
        Point2D C = new Point2D(B.x + u2, B.y - u1);
        Point2D D = new Point2D(A.x + u2, A.y - u1);
        Point2D E = new Point2D(D.x + ((u1+u2)/2), D.y + ((u2-u1)/2)); //

        // Draw the square
        g.drawLine(A.x, A.y, B.x, B.y); // A to B
        g.drawLine(B.x, B.y, C.x, C.y); // B to C
        g.drawLine(C.x, C.y, D.x,D.y);  // C to D
        g.drawLine(D.x, D.y, A.x, A.y); // D to E

        // Get the points for filling the square
        int xPoints[] = {A.x, B.x, C.x, D.x};
        int yPoints[] = {A.y, B.y, C.y, D.y};

        // Fill the square
        g.fillPolygon(xPoints,yPoints,4);

        // Recursive calls
        drawTree(g, E.x, E.y, C.x, C.y, limit-1);
        drawTree(g, D.x, D.y, E.x, E.y, limit-1);
    }
}
