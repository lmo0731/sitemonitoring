/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.facade;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import mn.mobicom.sitemonitoring.servlet.Report;

/**
 *
 * @author munkhochir
 */
public abstract class AbstractFacade<T> implements InterfaceFacade<T>, LocalFacade {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public Report executeNamedQuery(String name, Map<String, Object> params, Integer max, Integer first) {
        Report r = new Report();
        try {
            Query q = getEntityManager().createNamedQuery(name);
            if (first != null) {
                q.setFirstResult(first);
            }
            if (max != null) {
                q.setMaxResults(max);
            }
            if (params != null) {
                for (Map.Entry<String, Object> e : params.entrySet()) {
                    q.setParameter(e.getKey(), e.getValue());
                }
            }
            if (q.getResultList().size() == 0) {
                r.info = "NO CONTENT";
            } else {
                r.info = "OK";
                r.result = q.getResultList();
            }
        } catch (Exception ex) {
            r.info = ex.getMessage();
        }
        return r;
    }

    @Override
    public Report executeCustomQuery(String query, Map<String, Object> params, Integer max, Integer first) {
        Report r = new Report();
        try {
            Query q = getEntityManager().createQuery(query);
            if (first != null) {
                q.setFirstResult(first);
            }
            if (max == null) {
                max = 20;
            }
            q.setMaxResults(max);
            if (params != null) {
                for (Map.Entry<String, Object> e : params.entrySet()) {
                    q.setParameter(e.getKey(), e.getValue());
                }
            }
            r.info = "OK";
            r.result = q.getResultList();
        } catch (Exception ex) {
            r.info = ex.getMessage();
        }
        return r;
    }
}
