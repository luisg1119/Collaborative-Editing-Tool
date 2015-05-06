package Paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Paint extends JApplet {
   
	// Main method that displays a drawing area
   public static void main(String[] args) {
      JFrame mainWindow = new JFrame("Paint");
      PaintPanel paintPanel = new PaintPanel();
      mainWindow.setContentPane(paintPanel);
      mainWindow.setSize(600,480);
      //mainWindow.setLocation(100,100);
      mainWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      mainWindow.setVisible(true);
   }
   
   // This method called "createContentPane" sets the content pane to be a "new SimplePaintPanel()"
   // This does the paint functionality
   public void createContentPane() {
      setContentPane(new PaintPanel());
   }
   
   // This is the static class that gives all the functionality of the paint component.
   public static class PaintPanel extends JPanel
               implements MouseListener, MouseMotionListener {
      
	  // This represents the color the user chooses.
      private final static int BLACK = 0;
      
      // The color that the user chooses.
      private int defaultColor = BLACK;
      
      // The previous location of the mouse.
      private int locX, locY;
      
      private boolean dragOfMouse;		// This is set to true while the user is drawing.
      
      private Graphics drawingGraphics;	// A graphics context for the panel that is used to draw the user's curve.
      
      
      // Sets the background color to white
      PaintPanel() {
         setBackground(Color.WHITE);
         addMouseListener(this);
         addMouseMotionListener(this);
      }
      
      // The paintComponent takes in Graphics and calls super on the paintComponent and fills in the color white
      public void paintComponent(Graphics graphics) {
         
         super.paintComponent(graphics);  // Fill with background color (white).
         
         int width = getWidth();    // Width of the panel.
         int height = getHeight();  // Height of the panel.
         
         graphics.setColor(Color.LIGHT_GRAY);
         graphics.fillRect(width-53,  height-53, 50, 50);
         graphics.setColor(Color.BLACK);
         graphics.drawRect(width-53, height-53, 49, 49);
         graphics.drawString("CLEAR", width-48, height-23); 
         
      } // end paintComponent()
      
      // Set up for graphics content
      private void setUpDrawingGraphics() {
         drawingGraphics = getGraphics();
//         switch (defaultColor) {
//         case BLACK:
//            drawingGraphics.setColor(Color.BLACK);
//            break;
//         }
      } // end setUpDrawingGraphics()
      
      
      // This method is called if the user presses the mouse anywhere on the application.
      public void mousePressed(MouseEvent evt) {
         
         int x = evt.getX();   // x-coordinate where the user clicked.
         int y = evt.getY();   // y-coordinate where the user clicked.
         
         int width = getWidth();    // Width of the panel.
         int height = getHeight();  // Height of the panel.
         
         if (dragOfMouse == true)  // Ignore mouse presses that occur
            return;            //    when user is already drawing a curve.
                                //    (This can happen if the user presses
                                //    two mouse buttons at the same time.)
         
         if (x > width - 53) {
               // User clicked to the right of the drawing area.
               // This click is either on the clear button or
               // on the color palette.
            if (y > height - 53)
               repaint();       //  Clicked on "CLEAR button".
         }
         else if (x > 3 && x < width - 56 && y > 3 && y < height - 3) {
               // The user has clicked on the white drawing area.
               // Start drawing a curve from the point (x,y).
            locX = x;
            locY = y;
            dragOfMouse = true;
            setUpDrawingGraphics();
         }
         
      } // end mousePressed()
      
      // When the user releases the mouse, this method is called.
      public void mouseReleased(MouseEvent evt) {
         if (dragOfMouse == false)
            return;  // Nothing to do because the user isn't drawing.
         dragOfMouse = false;
         drawingGraphics.dispose();
         drawingGraphics = null;
      }
      
      
      // This method is called whenever the user is holding the mounse down and is dragging
      public void mouseDragged(MouseEvent evt) {
         
         if (dragOfMouse == false)
            return;  // Nothing to do because the user isn't drawing.
         
         int x = evt.getX();   // x-coordinate of mouse.
         int y = evt.getY();   // y-coordinate of mouse.
         
         drawingGraphics.drawLine(locX, locY, x, y);  // Draw the line.
         
         locX = x;  // Get ready for the next line segment in the curve.
         locY = y;
         
      } // end mouseDragged()
      
      
      public void mouseEntered(MouseEvent evt) { }   // Some empty routines.
      public void mouseExited(MouseEvent evt) { }    //    (Required by the MouseListener
      public void mouseClicked(MouseEvent evt) { }   //    and MouseMotionListener
      public void mouseMoved(MouseEvent evt) { }     //    interfaces).
      
      
   }  // End class SimplePaintPanel

} // end class SimplePaint