/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.jndi;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Named;
import mn.mobicom.jndi.service.JNDIServiceDelegate;
import mn.mobicom.sitemonitoring.entity.Device;
import mn.mobicom.sitemonitoring.facade.DeviceFacadeInterface;
import mn.mobicom.sitemonitoring.object.Data;

/**
 *
 * @author munkhochir
 */
@Singleton
@Named("ControlService")
public class ControlService implements ControlServiceInterface {

    @EJB
    DeviceFacadeInterface deviceFacade;

    @Override
    public Object control(Data req) throws Throwable {
        Device d = deviceFacade.find(req.device);
        if (d != null) {
            return JNDIServiceDelegate.send(d.getContext(), req);
        } else {
            return "NOK";
        }
    }
}
