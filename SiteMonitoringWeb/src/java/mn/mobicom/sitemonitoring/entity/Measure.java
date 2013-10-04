/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author munkhochir
 */
@Entity
@Table(name = "T_MEASURE")
@NamedQueries({
    @NamedQuery(name = "Measure.findById", query = "SELECT m FROM Measure m WHERE m.id = :id"),
    @NamedQuery(name = "Measure.findByIds", query = "SELECT m, m.event.measure_date, m.event.device.udid FROM Measure m WHERE m.id in :id"),
    @NamedQuery(name = "Measure.findByEvent", query = "SELECT m FROM Event e JOIN e.measureList m where e.id = :event"),
    @NamedQuery(name = "Measure.findByEvents", query = "SELECT m FROM Event e JOIN e.measureList m where e.id in :event"),
    @NamedQuery(name = "Measure.findLastMeasuresOfSite",
            //query = "SELECT max(e.measure_date), 1, m, d, max(m.id) as u FROM Site s JOIN s.deviceList d JOIN d.eventList e JOIN e.measureList m where s.id = :site group by m.name having u = max(m.id) order by m.id desc")
            //query = "select k From Measure, IN(select m where Measure m order by m.id desc) k")
            query = "select max(e.measure_date), m, d FROM Site s JOIN s.deviceList d JOIN d.eventList e JOIN e.measureList m where s.id = :site group by m.name") //query = "select max(m.id) FROM Site s JOIN s.deviceList d JOIN d.eventList e JOIN e.measureList m where s.id = :site group by m.name")
    ,
    @NamedQuery(name = "Measure.findLastMeasureIdsOfSite",
            //query = "SELECT max(e.measure_date), k FROM Measure k, Site s JOIN s.deviceList d JOIN d.eventList e JOIN e.measureList m where s.id = :site group by lower(m.name) having k.id = max(m.id) order by m.id desc")
        //query = "SELECT k FROM Measure k, Measure m where m.event.device.site.id = :site group by m.name having max(m.id) = k.id")
        query = "select max(m.id) FROM Site s JOIN s.deviceList d JOIN d.eventList e JOIN e.measureList m where s.id = :site group by m.name")
})
public class Measure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "C_NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "C_INFO")
    private String info;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "C_VALUE")
    private Double value;
    @Lob
    @Column(name = "C_BYTE")
    private Serializable cByte;
    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @JoinColumn(name = "C_EVENT_ID", referencedColumnName = "C_ID")
    @ManyToOne
    private Event event;

    public Measure() {
    }

    public Measure(Integer cId) {
        this.id = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String cName) {
        this.name = cName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String cInfo) {
        this.info = cInfo;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double cValue) {
        this.value = cValue;
    }

    public Serializable getByte() {
        return cByte;
    }

    public void setByte(Serializable cByte) {
        this.cByte = cByte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer cId) {
        this.id = cId;
    }

    @XmlTransient
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event cEventId) {
        this.event = cEventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Measure)) {
            return false;
        }
        Measure other = (Measure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mn.mobicom.sitemonitoring.entity.Measure[ cId=" + id + " ]";
    }
}
