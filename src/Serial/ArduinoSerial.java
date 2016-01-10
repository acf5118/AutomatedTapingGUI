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
    {
        SerialPortList ls = new SerialPortList();
        for(String s: ls.getPortNames())
        {
            sp = new SerialPort(s);
            try
            {
                sp.openPort();
                sp.setParams(115200, 8, 1, 0, true, true);
                break;
            }
            catch(SerialPortException e)
            {

            }
            System.out.println(s);
        }

    }

    public void writeOut(String s)
    {
        try
        {
            sp.writeString(s);

        }
        catch(SerialPortException e){}
    }

}
