/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.facade;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import mn.mobicom.sitemonitoring.servlet.Report;

/**
 *
 * @author munkhochir
 */
@Local
public interface InterfaceFacade<T> {

    public void create(T entity);

    public void edit(T entity);

    public void remove(T entity);

    public T find(Object id);

    public List<T> findAll();

    public List<T> findRange(int[] range);

    public int count();
    
    public Report executeNamedQuery(String name, Map<String, Object> params, Integer max, Integer first);
    
    public Report executeCustomQuery(String query, Map<String, Object> params, Integer max, Integer first);
}
