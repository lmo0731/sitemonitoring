/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mn.mobicom.sitemonitoring.entity.Device;

/**
 *
 * @author munkhochir
 */
@Stateless
@Named("deviceFacade")
public class DeviceFacade extends AbstractFacade<Device> implements DeviceFacadeInterface {

    @PersistenceContext(unitName = "SiteMonitoringWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeviceFacade() {
        super(Device.class);
    }
}
