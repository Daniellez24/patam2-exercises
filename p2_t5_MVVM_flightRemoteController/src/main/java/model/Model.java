package model;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;

/* ATTENTION! the model in MVVM stays the same as it was in MVC. no need to change it, behaves the same.
* in this program, nothing comes from the model that it has to notify about - therefore no changes are needed */

// the model connects with the flight simulator
// the model will read the properties from the properties.txt file
public class Model extends Observable {

    // <key=what part to change(for example, aileron), value=the value we want to change it to>
    HashMap<String, String> properties;
    Socket fg;
    // in order to write to the socket, we'll have the sockets's outputstream
    PrintWriter out2fg;

    // the controller will tell the Model from where to take the properties
    public Model(String propertiesFileName){
        properties = new HashMap<>();
        try {
            // read the properties file, and set the map
            BufferedReader in = new BufferedReader(new FileReader(propertiesFileName));
            String line;
            while((line=in.readLine()) != null){
                // split the lines from the properties file by ","
                String sp[] = line.split(",");
                // insert values into the map
                properties.put(sp[0],sp[1]);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // connecting to the flight simulator(FlightGear) as Client socket
        int port = Integer.parseInt(properties.get("port"));
        try {
            fg = new Socket(properties.get("ip"), port);
            out2fg = new PrintWriter(fg.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* from our the properties.txt file:
    aileron,set /controls/flight/aileron
    elevators,set /controls/flight/elevator
    rudder,set /controls/flight/rudder
    throttle,set /controls/engines/current-engine/throttle
     */

    // the controller will give us the x, after he takes it from the GUI (view)

    public void setAileron(double x){
        // we'll get the command: set /controls/flight/aileron, and write it to the socket's outPutStream with the given x
        String command = properties.get("aileron");
        out2fg.println(command+" "+x);
        out2fg.flush();
    }

    public void setElevators(double x){
        String command = properties.get("elevators");
        out2fg.println(command+" "+x);
        out2fg.flush();
    }

    public void setRudder(double x){
        String command = properties.get("rudder");
        out2fg.println(command+" "+x);
        out2fg.flush();
    }

    public void setThrottle(double x){
        String command = properties.get("throttle");
        out2fg.println(command+" "+x);
        out2fg.flush();
    }

    @Override
    public void finalize(){
        // close the socket fg, and the out2fg outPutStream
        try {
            out2fg.close();
            fg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
