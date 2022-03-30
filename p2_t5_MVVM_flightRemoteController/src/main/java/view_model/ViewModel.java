package view_model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Model;

import java.util.Observable;
import java.util.Observer;

/** the viewModel has to know the Model, even though in this program the Model doesn't send any notification
(in our program to model has nothing to calculate).
* but in MVVM model, the viewModel is the observer of the Model */
public class ViewModel implements Observer {

    Model m;
    /** the viewModel has to have attributes(תכונות), which he wants to send to the Model:
     * aileron, elevators, rudder, throttle. we use DoubleProperty because Property variables has the methods "set", "get"
     * (that's why these Property variables have to be public), and "bind" that uses
     * the Observer design pattern */
    public DoubleProperty aileron, elevators, rudder, throttle;

    public ViewModel(Model m) {
        this.m = m;
        m.addObserver(this);
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

        // addListener adds a Changelistener (functional interface with the method "changed") which will be notified whenever the value of the ObservableValue changes.
        // the parameters: ObserableValue, old value, new value.
        // whenever the attribute changes in the viewModel, automatically the model will change it to its new value */
        aileron.addListener((o, ov, nv)->m.setAileron((double)nv));
        elevators.addListener((o, ov, nv)->m.setElevators((double)nv));
        rudder.addListener((o, ov, nv)->m.setRudder((double)nv));
        throttle.addListener((o, ov, nv)->m.setThrottle((double)nv));


        /** what actually happens:
         * throttle.addListener(new ChangeListener<T>() {
         *             @Override
         *             public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
         *
         *             }
         *         });
         * */


    }

    @Override
    public void update(Observable o, Object arg) {}

}
