package Serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.Observable;

/**
 * Created by Adam Fowles on 1/19/2016.
 */
public abstract class AbstractSerialConnection
        extends Observable implements SerialPortEventListener
{

    protected SerialPort connection;

    @Override
    public abstract void serialEvent(SerialPortEvent serialPortEvent);
    public abstract void connectToDevice() throws SerialPortException;

    // Implement additional wrapper features (e.g writeInt) as appropriate
    public void reconnectDevice() throws SerialPortException
    {
        connection.closePort();
        connectToDevice();

    }
    public void writeString(String s) throws SerialPortException
    {
        connection.writeString(s);
    }

    public byte[] readBytes() throws SerialPortException
    {
        return connection.readBytes();
    }
}
