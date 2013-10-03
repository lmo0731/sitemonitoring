/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.jndi.service;

import java.util.concurrent.ExecutionException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author munkhochir
 */
public class JNDIServiceDelegate {

    private static final Logger logger = Logger.getLogger(JNDIServiceDelegate.class);

    public static Object send(String app, Object req) throws NamingException, ExecutionException {
        return JNDIServiceDelegate.send(app, JNDIService.class.getCanonicalName(), req);
    }

    public static Object send(String app, String serviceName, Object req) throws NamingException, ExecutionException {
        try {
            JNDIService service = JNDIServiceLocator.getInstance().getService(app, serviceName);
            if (service != null) {
                return service.process(req);
            }
            throw new NamingException(serviceName + " service not found on " + app);
        } catch (NamingException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new ExecutionException(t);
        }
    }
}
