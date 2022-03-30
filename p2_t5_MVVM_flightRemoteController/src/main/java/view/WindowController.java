package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import view_model.ViewModel;

import java.util.Observable;

/** the view paints the virtual Joystick. the view binds its attributes to the attributes of the viewModel.
 *  the view will have a ViewModel object, and this way the view can give orders to the viewModel by the binding */
public class WindowController {

    @FXML
    Canvas joystick;
    @FXML
    Slider throttle;
    @FXML
    Slider rudder;

    DoubleProperty aileron, elevators;

    ViewModel vm;

    // mouse clicked or released
    boolean mousePushed;
    // to show mouse move on joystick
    double jx,jy;
    // middle of canvas x,y
    double mx, my;


    public WindowController(){
        mousePushed = false;
        jx = 0;
        jy = 0;
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();
    }

    void init(ViewModel vm){
        this.vm = vm;
        /** whenever the throttle Slider changes - the viewModel's throttle changes.
         * and whenever the viewModel's throttle changes -
         * its Listener (which was added in the ViewModel) changes the Model (m.setThrottle)
         * and the Model sends the right command to the simulator (with setThrottle method).
         * view changes -> ViewModel changes -> Model changes -> simulator changes */
        vm.throttle.bind(throttle.valueProperty());
        vm.rudder.bind(rudder.valueProperty());
        vm.aileron.bind(aileron);
        vm.elevators.bind(elevators);
        // the viewModel's attributes are bound to the View's (WindowController) attributes
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
        /** whenever we call the set method of the aileron/elevators (aileron.set(..)),
         * whoever is bound to them will update itself to the new value that was set */
        aileron.set((jx-mx)/mx);
        elevators.set((my-jy)/my);
    }

    public void mouseDown(MouseEvent me){
        if(!mousePushed){
            mousePushed = true;
//            System.out.println("mouse is down");
        }
    }

    // release the mouse click
    public void mouseUp(MouseEvent me){
        if(mousePushed){
            mousePushed = false;
//            System.out.println("mouse is up");
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
