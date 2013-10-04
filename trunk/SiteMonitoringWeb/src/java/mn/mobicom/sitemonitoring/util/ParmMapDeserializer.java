/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author munkhochir
 */
public class ParmMapDeserializer {

    public static Map<String, Object> deserialize(Map<String, String[]> map) {
        Map<String, Object> parms = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            if (!e.getKey().startsWith("_") && e.getValue() != null && e.getValue().length > 0) {
                Object o = null;
                System.out.println(e.getKey() + ": " + e.getValue().length);
                if (e.getValue().length == 1) {
                    o = deserializeStr(e.getValue()[0]);
                } else if (e.getValue().length > 1) {

                    List<Object> array = new ArrayList<Object>();
                    for (String k : e.getValue()) {
                        Object j = deserializeStr(k);
                        if (j != null) {
                            array.add(j);
                        }
                    }
                    o = array;
                }
                if (o != null) {
                    parms.put(e.getKey(), o);
                }

            }
        }
        return parms;
    }

    private static Object deserializeStr(String value) {
        Object o = null;
        if (value.startsWith("s")) {
            o = value.substring(1);
        } else if (value.startsWith("i")) {
            try {
                o = Long.parseLong(value.substring(1));
            } catch (Exception ex) {
            }
        } else if (value.startsWith("f")) {
            try {
                o = Double.parseDouble(value.substring(1));
            } catch (Exception ex) {
            }
        } else if (value.startsWith("b")) {
            try {
                o = Boolean.parseBoolean(value.substring(1));
            } catch (Exception ex) {
            }
        } else if (value.startsWith("dt")) {
            try {
                o = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.substring(2));
            } catch (Exception ex) {
            }
        } else if (value.startsWith("d")) {
            try {
                o = new SimpleDateFormat("yyyy-MM-dd").parse(value.substring(1));
            } catch (Exception ex) {
            }
        }
        return o;
    }
}
