/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.jndi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;
import mn.mobicom.jndi.service.JNDIService;
import mn.mobicom.sitemonitoring.entity.Device;
import mn.mobicom.sitemonitoring.entity.Event;
import mn.mobicom.sitemonitoring.entity.Measure;
import mn.mobicom.sitemonitoring.facade.DeviceFacadeInterface;
import mn.mobicom.sitemonitoring.facade.EventFacadeInterface;
import mn.mobicom.sitemonitoring.facade.MeasureFacadeInterface;
import mn.mobicom.sitemonitoring.object.Data;

/**
 *
 * @author munkhochir
 */
@Singleton
@Named("registerService")
@Startup
public class RegisterService implements JNDIService {

    @EJB
    DeviceFacadeInterface deviceFacade;
    @EJB
    EventFacadeInterface eventFacade;
    @EJB
    MeasureFacadeInterface measureFacade;

    @PostConstruct()
    public void init() {
        System.out.println(eventFacade);
    }

    @Override
    public Object process(Object req) throws Throwable {
        if (req instanceof Data) {
            Data data = (Data) req;
            Event e = new Event();
            e.setInfo(data.name);
            Device d = deviceFacade.find(data.device);
            if (d != null) {
                e.setDevice(d);
                e.setDate(new Date());
                e.setMeasuredate((Date) data.additional);
                e.setInfo(data.name);
                eventFacade.create(e);
                List<Measure> measures = new ArrayList<Measure>();
                for (Entry<String, Object> i : data.map.entrySet()) {
                    measures.addAll(getMeasure(i.getKey(), i.getValue(), e));
                }
                for(Measure m: measures){
                    measureFacade.create(m);
                }
            }
        }
        return "OK";
    }

    private List<Measure> getMeasure(String key, Object o, Event e) {
        List<Measure> ret = new ArrayList<Measure>();
        if (o instanceof Number) {
            Measure m = new Measure();
            m.setEvent(e);
            m.setName(key);
            m.setValue(((Number) o).doubleValue());
            ret.add(m);
        } else if (o instanceof byte[]) {
        } else if (o instanceof Collection) {
            for (Object i : (Collection) o) {
                ret.addAll(getMeasure(key, i, e));
            }
        } else {
            Measure m = new Measure();
            m.setEvent(e);
            m.setName(key);
            m.setInfo(o.toString());
            ret.add(m);
        }
        return ret;
    }
}