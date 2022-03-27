package controller;

import model.Model;
import view.WindowController;

import java.util.Observable;
import java.util.Observer;

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
        // if the update came from the model, we'll check its result
        if(o==m){
            int result = m.getResult();
            wc.display(result);
        }
        else{
            if(o==wc){
                m.calculate();
            }
        }
    }
}
