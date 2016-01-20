package Serial;

import Main.LaMachinaGui;
import javafx.application.Application;
import javafx.concurrent.Task;
import jssc.SerialPortException;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Adam Fowles on 1/19/2016.
 */
public class SerialCommunication
        extends Observable implements Observer
{
    private AbstractSerialConnection connection;
    private Application app;

    public SerialCommunication(Application a)
    {
        app = a;
        connection = new ArduinoSerialConnection();
        // Serial communication object observes the arduino serial
        // connection object for information on the port.
        connection.addObserver(this);
    }

    public void connect()
    {
        String[] args;
        try
        {
            connection.connectToDevice();
            args = new String[]{"false","Connected"};
        }
        catch (SerialPortException e)
        {
            args = new String[]{"true","Could not connect"};
        }

        ((LaMachinaGui)app).updateState(UpdateMessageEnum.CONNECTION, args);
    }

    public void reconnect()
    {
        try
        {
            connection.reconnectDevice();
        }
        catch (SerialPortException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(String s)
    {
        try
        {
            connection.writeString(s + "\n");
        }
        catch (SerialPortException e)
        {
            // Notify observers

            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

}
