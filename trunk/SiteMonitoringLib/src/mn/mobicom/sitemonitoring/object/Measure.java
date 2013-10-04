/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.object;

/**
 *
 * @author Developer
 */
public class Measure {

    public Double value;
    public String info;
    public byte[] bytes;

    public Measure(Double value, String info) {
        this.value = value;
        this.info = info;
    }
}
