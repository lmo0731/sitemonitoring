/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.jndi.service;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author munkhochir
 */
public class JNDIServiceLocator {

    private Context initalContext;
    private static Logger logger = Logger.getLogger(JNDIServiceLocator.class);
    private static JNDIServiceLocator instance = null;

    public JNDIServiceLocator() {
        try {
            Hashtable env = new Hashtable();
            //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            //env.put(Context.PROVIDER_URL, "iiop://127.0.0.1:3700");
            this.initalContext = new InitialContext(env);
        } catch (NamingException ex) {
            logger.error("", ex);
        }
    }

    public static JNDIServiceLocator getInstance() {
        if (instance == null) {
            instance = new JNDIServiceLocator();
        }
        return instance;
    }

    public JNDIService getService(String app) {
        return getService(app, JNDIService.class.getCanonicalName());
    }

    public JNDIService getService(String app, String name) {
        try {
            logger.info("finding " + name + " from java:global/" + app);
            Context envContext = (Context) initalContext.lookup("java:global/" + app);
            NamingEnumeration<Binding> bindings = envContext.listBindings("");
            while (bindings.hasMore()) {
                Binding binding = bindings.next();
                if (binding.getName().endsWith(name) || binding.getName().startsWith(name)) {
                    Object o = initalContext.lookup("java:global/" + app + "/" + binding.getName());
                    if (o instanceof JNDIService) {
                        logger.info("found " + name + " from java:global/" + app);
                        return (JNDIService) o;
                    }
                }
            }
            logger.info(name + " EJB not found on java:global/" + app);
        } catch (NamingException ex) {
            logger.error("", ex);
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
    }
}
