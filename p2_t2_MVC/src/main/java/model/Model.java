package model;

import java.util.Observable;
import java.util.Random;

public class Model extends Observable {

    int result;
    Random r = new Random();

    public void calculate(){
        result = r.nextInt(101); // generates numbers between 0-100
        setChanged();
        notifyObservers();
    }

    public int getResult(){
        return result;
    }
}
