package Paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import Editor.AddTextCommand;

/** Description of PaintPanel:
* This class called PaintPanel extends JPanel and implements MouseListener and MouseMotionListener.
* This class contains many instance variables such as the final long serialVersionUID that creates a unique thread.
* It also has a static int that is called Black and is set to zero so the user chooses this as default.
* It also has a location for the mouse with x and y cordinates.
* We have a boolean value that is representing if the mouse is moved or not.
* We have graphics content that represents the drawing and any new graphics drawn as well as an output stream.
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -4369529014081136664L;
	private final static int BLACK = 0;									// This represents the color the user chooses.
    private int locX;													// The previous X location of the mouse.
    private int locY; 						  							// The previous Y location of the mouse.	
    private boolean dragOfMouse;			  							// This is set to true while the user is drawing.
    private Graphics drawingGraphics;	      							// A graphics context for the panel that is used to draw the user's curve.
    private Graphics newGraphic;
    private ObjectOutputStream output;

    
    
    public PaintPanel(String name, ObjectOutputStream output){
    	//Set Up the Panel
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.output = output;
        newGraphic = getGraphics();
     }
    
    public void paintComponent(Graphics graphics) {
        
        super.paintComponent(graphics); 								// Fill with background color (white).
        
        int width = getWidth(); 										// Width of the panel.
        int height = getHeight();										// Height of the panel.
        
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(width-53,  height-53, 50, 50);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(width-53, height-53, 49, 49);
        graphics.drawString("CLEAR", width-48, height-23); 
     } 
    	
    public void mousePressed(MouseEvent evt) {
    	mousePressedListener(evt);       
     } 
     
     // When the user releases the mouse, this method is called.
     public void mouseReleased(MouseEvent evt) {
    	 mouseReleasedListener(evt);
     }
     
     
     // This method is called whenever the user is holding the mounse down and is dragging
     public void mouseDragged(MouseEvent evt) {
        mouseDraggedListener(evt);        
     }
     
     
     public void mouseEntered(MouseEvent evt) { 
    	 
     }   
     
     public void mouseExited(MouseEvent evt) { 
    	 
     }
     
     public void mouseClicked(MouseEvent evt) { 
    	 
     }
     
     public void mouseMoved(MouseEvent evt) {
    	 
     } 
    
     private void setUpDrawingGraphics() {
         drawingGraphics = getGraphics();
     }
     
     
     public void pressedUpdate(MouseEvent evt){
    	 int x = evt.getX(); 										// x-cord where the user clicked.
         int y = evt.getY(); 										// y-cord where the user clicked.
         
         int width = getWidth(); 									// Width of the panel.
         int height = getHeight(); 									// Height of the panel.
         
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
     }
     
     public void releasedUpdate(MouseEvent evt){
         if (dragOfMouse == false)
             return;  // Nothing to do because the user isn't drawing.
          dragOfMouse = false;
          drawingGraphics.dispose();
          drawingGraphics = null;
     }
     
     public void draggedUpdate(MouseEvent evt){
         if (dragOfMouse == false)
             return;  // Nothing to do because the user isn't drawing.
          
          int x = evt.getX();   // x-cord of mouse.
          int y = evt.getY();   // y-cord of mouse.
          
          drawingGraphics.drawLine(locX, locY, x, y);  // Draw the line.
          
          locX = x;  // Get ready for the next line segment in the curve.
          locY = y;
     }
     
     private void mousePressedListener(MouseEvent evt){
 		try{
 				output.writeObject(new PressedCommand(evt));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}	
 	}
     
     private void mouseReleasedListener(MouseEvent evt){
 		try{
 				output.writeObject(new ReleasedCommand(evt));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}	
 	}
     
     private void mouseDraggedListener(MouseEvent evt){
 		try{
 				output.writeObject(new DraggedCommand(evt));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}	
 	}
     
     
}
