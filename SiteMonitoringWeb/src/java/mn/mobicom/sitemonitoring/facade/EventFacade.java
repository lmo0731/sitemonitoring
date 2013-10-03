/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mn.mobicom.sitemonitoring.entity.Event;

/**
 *
 * @author munkhochir
 */
@Stateless
@Named("eventFacade")
public class EventFacade extends AbstractFacade<Event> implements EventFacadeInterface {

    @PersistenceContext(unitName = "SiteMonitoringWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }
}
