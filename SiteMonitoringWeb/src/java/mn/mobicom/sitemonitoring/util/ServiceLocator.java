/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import mn.mobicom.sitemonitoring.facade.AbstractFacade;
import org.apache.log4j.Logger;

/**
 *
 * @author Enkhbazar
 */
public class ServiceLocator {

    private Logger logger = Logger.getLogger(ServiceLocator.class);
    private Context initalContext;
    private Map cache;
    private static ServiceLocator ourInstance = new ServiceLocator();

    public static ServiceLocator getInstance() {
        return ourInstance;
    }

    private ServiceLocator() {
        try {
            this.initalContext = new InitialContext();
            this.cache = Collections.synchronizedMap(new HashMap());
        } catch (NamingException ex) {
            logger.error("Get initial context", ex);
        }
    }
    
    public DataSource getDataSource(String dataSourceName) {
        DataSource datasource = null;
        try {
            if (this.cache.containsKey(dataSourceName)) {
                datasource = (DataSource) this.cache.get(dataSourceName);
            } else {
                Context envContext = (Context) initalContext.lookup("java:comp/env");
                datasource = (DataSource) envContext.lookup(dataSourceName);
                this.cache.put(dataSourceName, datasource);
            }
        } catch (NamingException ex) {
            logger.error("Get datasource", ex);
        }
        return datasource;

    }

    public AbstractFacade getFacade(String facadeName) throws NamingException {
        AbstractFacade abstractFacade = null;
        try {
            if (this.cache.containsKey(facadeName)) {
                abstractFacade = (AbstractFacade) this.cache.get(facadeName);
            } else {
                abstractFacade = (AbstractFacade) initalContext.lookup("java:module/" + facadeName);
                this.cache.put(facadeName, abstractFacade);
            }
        } catch (NamingException ex) {
            logger.error("Get facade", ex);
            throw ex;
        }
        return abstractFacade;
    }
}
