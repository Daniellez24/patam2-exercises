package controller;

import model.Model;
import view.WindowController;

import java.util.Observable;
import java.util.Observer;

// the controller tells the plane what to do, using the model who connects with the simulator
public class Controller implements Observer {

    Model m;
    WindowController wc;

    public Controller(Model m, WindowController wc) {
        // the controller "follows" both the model and the WindowController(view)
        this.m = m;
        m.addObserver(this);
        this.wc = wc;
        wc.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // the update always comes from the view (WindowController), to the model.
        // so we don't have to check which Observable sent a changes notification
        m.setAileron(wc.getAileron());
        m.setElevators(wc.getElevator());
    }
}
