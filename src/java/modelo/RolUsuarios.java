/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CEL1
 */
@Entity
@Table(name = "rol_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolUsuarios.findAll", query = "SELECT r FROM RolUsuarios r"),
    @NamedQuery(name = "RolUsuarios.findByIdRolUsuario", query = "SELECT r FROM RolUsuarios r WHERE r.idRolUsuario = :idRolUsuario"),
    @NamedQuery(name = "RolUsuarios.findByRol", query = "SELECT r FROM RolUsuarios r WHERE r.rol = :rol")})
public class RolUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_usuario")
    private Integer idRolUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rol")
    private String rol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRolUsuario")
    private List<Usuarios> usuariosList;

    public RolUsuarios() {
    }

    public RolUsuarios(Integer idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public RolUsuarios(Integer idRolUsuario, String rol) {
        this.idRolUsuario = idRolUsuario;
        this.rol = rol;
    }

    public Integer getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(Integer idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolUsuario != null ? idRolUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolUsuarios)) {
            return false;
        }
        RolUsuarios other = (RolUsuarios) object;
        if ((this.idRolUsuario == null && other.idRolUsuario != null) || (this.idRolUsuario != null && !this.idRolUsuario.equals(other.idRolUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RolUsuarios[ idRolUsuario=" + idRolUsuario + " ]";
    }
    
}
