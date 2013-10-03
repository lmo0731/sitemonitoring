 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author munkhochir
 */
@Entity
@Table(name = "T_SITE")
@NamedQueries({
    @NamedQuery(name = "Site.findById", query = "select s from Site s where s.id = :id"),
    @NamedQuery(name = "Site.findAll", query = "select s from Site s")
})
public class Site implements Serializable {

    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "site")
    private List<Device> deviceList;
    @Column(name = "C_NAME")
    private String name;
    @Column(name = "C_INFO")
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
