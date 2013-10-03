package mn.mobicom.jndi.service;

import java.io.Serializable;
import javax.ejb.Remote;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author munkhochir
 */
@Remote
public interface JNDIService extends Serializable {

    Object process(Object req) throws Throwable;
}
