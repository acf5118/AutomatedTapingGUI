package Serial;

import Main.LaMachinaGui;
import javafx.application.Application;
import jssc.SerialPortException;

import java.util.Observer;

/**
 * Created by Adam Fowles on 1/19/2016.
 * A Serial Communication object that handles
 * all the communication between an
 * application and a Serial Connection. Also handles
 * all exceptions possibly thrown from that connection
 * and takes appropriate action.
 */
public class SerialCommunication
{
    // private fields
    private AbstractSerialConnection connection;
    private Application app;

    /**
     * Serial Communication constructor,
     * takes in an application
     * @param a - application (in this case LaMachinaGui)
     */
    public SerialCommunication(Application a)
    {
        app = a;
        connection = new ArduinoSerialConnection();
    }

    /**
     * Connect method is called by the application
     * to connect to the device, serial communication
     * object then relays back whether or not it was successful.
     */
    public void connect()
    {
        // TODO make this process cleaner
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

    /**
     * Reconnects a device, works nicely
     * for when a emergency stop is issued.
     */
    public void reconnect()
    {
        try
        {
            connection.reconnectDevice();
        }
        catch (SerialPortException e)
        {
            //TODO do something else
            e.printStackTrace();
        }
    }

    /**
     * Send messages writes out a string
     * on the serial connection
     * @param s - the string to write out.
     */
    public void sendMessage(String s)
    {
        try
        {
            connection.writeString(s + "\n");
        }
        catch (SerialPortException e)
        {
            //TODO do something else
            e.printStackTrace();
        }
    }


    /**
     * Shutting down the device does a disconnect
     */
    public void shutdown()
    {
        try
        {
            connection.disconnectDevice();
        }
        catch (SerialPortException e)
        {
            //TODO do something else
            e.printStackTrace();
        }
    }

    /**
     * Adds an observer to the connection to wait for
     * messages coming from the serial port.
     * @param o - an observer (most likely an application component)
     */
    public void addSerialMonitorObserver(Observer o)
    {
        connection.addObserver(o);
    }

}
