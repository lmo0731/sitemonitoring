/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.jndi;

import mn.mobicom.sitemonitoring.object.Data;

/**
 *
 * @author munkhochir
 */
public interface ControlServiceInterface {

    public Object control(Data req) throws Throwable;
}
