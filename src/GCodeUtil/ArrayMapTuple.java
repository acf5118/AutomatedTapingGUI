package GCodeUtil;

import java.util.HashMap;

/**
 * Created by Adam Fowles on 5/26/2016.
 * Java does not support multiple returns...
 * Both should probably be Maps instead
 * of double[] but that is future work...
 */
public class ArrayMapTuple
{
    public double[] mod;
    public HashMap<String, Double> params;

    public ArrayMapTuple(HashMap<String, Double> params, double[] mod)
    {
        this.params = params;
        this.mod = mod;
    }

}
