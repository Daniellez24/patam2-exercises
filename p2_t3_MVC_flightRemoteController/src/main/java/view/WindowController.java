package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

// the view paints the virtual Joystick
public class WindowController extends Observable {

    @FXML
    Canvas joystick;
    // mouse clicked or released
    boolean mousePushed;
    // to show mouse move on joystick
    double jx,jy;
    // middle of canvas x,y
    double mx, my;

    double aileron, elevator;

    public WindowController(){
        mousePushed = false;
        jx = 0;
        jy = 0;
        aileron = 0;
        elevator = 0;
    }

    public double getAileron() {
        return aileron;
    }

    public double getElevator() {
        return elevator;
    }

    void paint(){
        // in order to paint the joystick, we first need to extract from it a GraphicsContext
        GraphicsContext gc = joystick.getGraphicsContext2D();
        // get the middle of the canvas (x,y values of the canvas' center)
        mx = joystick.getWidth()/2;
        my = joystick.getHeight()/2;
        // clear the last drawing everytime "paint()" is called, and then re-paint
        gc.clearRect(0,0, joystick.getWidth(), joystick.getHeight());

        // paint an oval
        gc.strokeOval(jx-50, jy-50, 100,100);

        /* get the aileron and elevator's position between -1,1.
        ATTENTION! here we didn't fix the problem that we can get the mouse out of the canvas,
        and we might get values bigger/smaller than 1,-1.
        this can be fixed by taking the max/min value between 1,-1 and the (jx-mx)/mx and (jy-my)/my.
        the aileron moves on the X's, and the elevator on the Y's */
        aileron = (jx-mx)/mx;
        elevator = (jy-my)/my;
        // notify changes to the controller (the observer)
        setChanged();
        notifyObservers();
        System.out.println(aileron+","+elevator);

    }

    public void mouseDown(MouseEvent me){
        if(!mousePushed){
            mousePushed = true;
            System.out.println("mouse is down");
        }
    }

    // release the mouse click
    public void mouseUp(MouseEvent me){
        if(mousePushed){
            mousePushed = false;
            System.out.println("mouse is up");
            // return the joystick to the middle on mouse up
            jx = mx;
            jy = my;
            paint();
        }
    }

    public void mouseMove(MouseEvent me){
        // check if there was a mousePush, beacuse we want to relate only to the mouse drag
        if(mousePushed){
//            System.out.println("mouse move "+me.getX()+","+me.getY());
            // paint the joystick according to where to mouse is moving
            jx = me.getX();
            jy = me.getY();
            paint();
        }
    }

}
