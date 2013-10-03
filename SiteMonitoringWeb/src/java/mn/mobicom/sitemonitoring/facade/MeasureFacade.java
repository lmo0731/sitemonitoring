/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mn.mobicom.sitemonitoring.entity.Measure;

/**
 *
 * @author munkhochir
 */
@Stateless
@Named("measureFacade")
public class MeasureFacade extends AbstractFacade<Measure> implements MeasureFacadeInterface {

    @PersistenceContext(unitName = "SiteMonitoringWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MeasureFacade() {
        super(Measure.class);
    }
}
