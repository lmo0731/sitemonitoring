/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.object;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author munkhochir
 */
public class Data implements Serializable {

    public String device;
    public String name;
    public Object additional;
    public Map<String, Object> map;

    @Override
    public String toString() {
        return String.format("Data(device: %s, name: %s, o:%s, %s)", device, name, additional, map); //To change body of generated methods, choose Tools | Templates.
    }
}
