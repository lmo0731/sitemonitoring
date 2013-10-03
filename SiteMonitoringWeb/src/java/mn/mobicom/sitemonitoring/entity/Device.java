/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author munkhochir
 */
@Entity
@Table(name = "T_DEVICE")
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findByUdid", query = "SELECT d FROM Device d where d.udid = :device"),
    @NamedQuery(name = "Device.findBySiteId", query = "SELECT d FROM Site s JOIN s.deviceList d where s.id = :site")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(min = 1, max = 255)
    @Column(name = "C_UDID")
    private String udid;
    @Size(max = 255)
    @Column(name = "C_INFO")
    private String info;
    @Size(max = 20)
    @Column(name = "C_URI")
    private String uri;
    @Size(max = 20)
    @Column(name = "C_CONTEXT")
    private String context;
    @ManyToOne
    @JoinColumn(name = "C_SITE_ID", referencedColumnName = "C_ID")
    private Site site;
    @OneToMany(mappedBy = "device")
    private List<Event> eventList;

    public Device() {
    }

    public Device(String cUdid) {
        this.udid = cUdid;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String cUdid) {
        this.udid = cUdid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String cInfo) {
        this.info = cInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (udid != null ? udid.hashCode() : 0);
        return hash;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site cSite) {
        this.site = cSite;
    }

    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.udid == null && other.udid != null) || (this.udid != null && !this.udid.equals(other.udid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mn.mobicom.sitemonitoring.entity.Device[ cUdid=" + udid + " ]";
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
