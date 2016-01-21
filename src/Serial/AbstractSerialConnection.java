package Serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.Observable;

/**
 * Created by Adam Fowles on 1/19/2016.
 * An abstract implementation of a serial connection
 * Extended by ArduinoSerialConnection
 */
public abstract class AbstractSerialConnection
        extends Observable implements SerialPortEventListener
{
    // The serial port itself
    protected SerialPort connection;

    // Overridden method from implementing SerialPortEventListener
    @Override
    public abstract void serialEvent(SerialPortEvent serialPortEvent);

    /**
     * Creates a connection to a device on the port
     * @throws SerialPortException - thrown by most uses
     * of the serial port object.
     */
    public abstract void connectToDevice() throws SerialPortException;

    /**
     * Opposite of connect to device method
     * @throws SerialPortException - same as above
     */
    public abstract void disconnectDevice() throws SerialPortException;

    // Implement additional wrapper features (e.g writeInt) as appropriate

    /**
     * Sometimes it is useful to reconnect a device,
     * close the port and then attempt to reconnect
     * @throws SerialPortException - any error that arises
     * from the described process
     */
    public void reconnectDevice() throws SerialPortException
    {
        connection.closePort();
        connectToDevice();

    }

    /**
     * Wrapper method that writes out a string to the
     * serial port.
     * @param s - the string to write out
     * @throws SerialPortException - see above
     */
    public void writeString(String s) throws SerialPortException
    {
        connection.writeString(s);
    }

    /**
     * Write out a single byte
     * @param b - the byte to write out
     * @throws SerialPortException - see above
     */
    public void writeByte(Byte b) throws SerialPortException
    {
        connection.writeByte(b);
    }

    /**
     * Reads bytes from the serial port
     * @return - the bytes read
     * @throws SerialPortException - see above
     */
    public byte[] readBytes() throws SerialPortException
    {
        return connection.readBytes();
    }

}
