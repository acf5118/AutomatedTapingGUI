package Serial;

import jssc.*;
import java.util.Observable;


/**
 * Created by Adam Fowles on 1/9/2016.
 * Implementation of a serial connection
 */
public class ArduinoSerialConnection extends AbstractSerialConnection
{
    // Input read from the serial connection
    private StringBuilder inputBuffer;

    /**
     * Overridden method from implementing SerialPortEventListener
     * Listens on the port for incoming messages
     * @param serialPortEvent
     */
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {
        String[] messages = null;
        if (inputBuffer == null)
        {
            inputBuffer = new StringBuilder();
        }
        try
        {
            byte[] buf = readBytes();
            if (buf != null && buf.length > 0)
            {
                String s = new String(buf, 0, buf.length);
                inputBuffer.append(s);
                if (inputBuffer.toString().contains("\r\n"))
                {
                    messages = inputBuffer.toString().split("\r\n", -1);
                    System.out.println("Monitor: ");
                    for (int i = 0; i < messages.length; i++)
                    {
                        System.out.println(messages[i]);
                    }
                }
                else
                {
                    System.out.println("Monitor: " + s);
                    messages = new String[]{s.toString()};
                }
            }
            inputBuffer = new StringBuilder();
        }
        catch (SerialPortException e)
        {
            // TODO set flag, notify observers
            e.printStackTrace();
            return;
        }
        setChanged();
        notifyObservers(messages);
    }

    /**
     * Loop through all of the ports on the list
     * and try a connection
     * @throws SerialPortException
     */
    @Override
    public void connectToDevice() throws SerialPortException
    {
        SerialPortList ls = new SerialPortList();
        try
        {
            for (String s : ls.getPortNames())
            {
                connection = new SerialPort(s);
                connection.openPort();
                connection.setParams(115200, 8, 1, 0, true, true);
                break;
            }
        }
        // Do nothing here, it is expected that a few ports on the list
        // will not be able to connected.
        catch (SerialPortException e) {}

        if (connection == null)
        {
            throw new SerialPortException("none", "none", "none");
        }
        else
        {
            connection.addEventListener(this);
        }
    }

    /**
     * Disconnects the application from the device
     * remove any event listener that is attached
     * and close off the input buffer and connection
     * in case of possible reuse without a reconnect
     * @throws SerialPortException
     */
    public void disconnectDevice() throws SerialPortException
    {
        if (connection != null)
        {
            try
            {
                connection.removeEventListener();
                if (connection.isOpened())
                {
                    connection.closePort();
                }
            }
            finally
            {
                inputBuffer = null;
                connection = null;
            }
        }
    }

    public StringBuilder getSerialBuffer(){return inputBuffer;}
}
