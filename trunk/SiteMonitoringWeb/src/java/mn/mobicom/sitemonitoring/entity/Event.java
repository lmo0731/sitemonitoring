/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author munkhochir
 */
@Entity
@Table(name = "T_EVENT")
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findByDevice", query = "SELECT e FROM Device d JOIN d.eventList e where d.udid = :device order by e.measure_date desc"),
    @NamedQuery(name = "Event.findRangeByDevice",
    query = "SELECT e FROM Device d JOIN d.eventList e where d.udid = :device and :from <= e.measure_date and e.measure_date < :to order by e.measure_date desc"),
    @NamedQuery(name = "Event.findLastEventOfDevicesOfSite",
    query = "SELECT e1.device, MAX(e1.measure_date) FROM Site s1 JOIN s1.deviceList d1 JOIN d1.eventList e1 where s1.id = :site group by e1.device")
})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "C_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "C_INFO")
    private String info;
    @Column(name = "C_MEASUREDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date measure_date;
    @JoinColumn(name = "C_DEVICE_UDID", referencedColumnName = "C_UDID")
    @ManyToOne
    private Device device;
    @OneToMany(mappedBy = "event")
    private List<Measure> measureList;

    public Event() {
    }

    public Event(Integer cId) {
        this.id = cId;
    }

    public Event(Integer cId, Date cDate, Device device) {
        this.id = cId;
        this.date = cDate;
        this.device = device;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer cId) {
        this.id = cId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date cDate) {
        this.date = cDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String cInfo) {
        this.info = cInfo;
    }

    public Date getMeasuredate() {
        return measure_date;
    }

    public void setMeasuredate(Date cMeasuredate) {
        this.measure_date = cMeasuredate;
    }

    @XmlTransient
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @XmlTransient
    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mn.mobicom.sitemonitoring.entity.Event[ cId=" + id + " ]";
    }
}
