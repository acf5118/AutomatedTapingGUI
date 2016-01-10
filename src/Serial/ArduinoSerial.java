package Serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;


/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class ArduinoSerial
{
    private SerialPort sp;
    public ArduinoSerial()
    {

    }

    public void connectToArduino()
            throws SerialPortException
    {
        SerialPortList ls = new SerialPortList();
        for(String s: ls.getPortNames())
        {
            sp = new SerialPort(s);
            sp.openPort();
            sp.setParams(115200, 8, 1, 0, true, true);
            break;
        }
        if (sp == null)
        {
            throw new SerialPortException("none", "none", "none");
        }

    }

    public void writeOut(String s)
            throws SerialPortException
    {
        sp.writeString(s);
    }

}
