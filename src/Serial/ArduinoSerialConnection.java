package Serial;

import jssc.*;
import java.util.Observable;


/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class ArduinoSerialConnection extends AbstractSerialConnection
{
    private StringBuilder inputBuffer;

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {
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
                    String[] cmds = inputBuffer.toString().split("\r\n", -1);
                }
            }
            //textFlow.getChildren().add(new Text(inputBuffer.toString()));
            System.out.println("Monitor: " + inputBuffer);
            inputBuffer = new StringBuilder();
        }
        catch (SerialPortException e)
        {
            // TODO set flag, notify observers
            e.printStackTrace();
        }
    }

    @Override
    public void connectToDevice() throws SerialPortException
    {
        SerialPortList ls = new SerialPortList();
        for(String s: ls.getPortNames())
        {
            connection = new SerialPort(s);
            connection.openPort();
            connection.setParams(115200, 8, 1, 0, true, true);
            break;
        }

        if (connection == null)
        {
            throw new SerialPortException("none", "none", "none");
        }
        else
        {
            try {
                connection.addEventListener(this);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
}
