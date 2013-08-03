/**
 * 
 */
package mx.com.aon.portal.model.opcmenuusuario;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 27/05/2008
 *
 */
public class ConfigOpcionMenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String claveMenu;

    private String nombreMenu;
    
    private String claveCliente;
    
    private String claveRol;
    
    private String claveUsuario;
    
    private String claveOpcionMenu;
    
    private String opcionMenu;
    
    private String ramo;
    
    private String situacion;
    
    private String opcionPadre;
    
    private String pantallaAsociada;
    
    private String estado;
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the claveCliente
     */
    public String getClaveCliente() {
        return claveCliente;
    }

    /**
     * @param claveCliente the claveCliente to set
     */
    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    /**
     * @return the claveRol
     */
    public String getClaveRol() {
        return claveRol;
    }

    /**
     * @param claveRol the claveRol to set
     */
    public void setClaveRol(String claveRol) {
        this.claveRol = claveRol;
    }

    /**
     * @return the claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * @param claveUsuario the claveUsuario to set
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the nombreMenu
     */
    public String getNombreMenu() {
        return nombreMenu;
    }

    /**
     * @param nombreMenu the nombreMenu to set
     */
    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    /**
     * @return the opcionMenu
     */
    public String getOpcionMenu() {
        return opcionMenu;
    }

    /**
     * @param opcionMenu the opcionMenu to set
     */
    public void setOpcionMenu(String opcionMenu) {
        this.opcionMenu = opcionMenu;
    }

    /**
     * @return the opcionPadre
     */
    public String getOpcionPadre() {
        return opcionPadre;
    }

    /**
     * @param opcionPadre the opcionPadre to set
     */
    public void setOpcionPadre(String opcionPadre) {
        this.opcionPadre = opcionPadre;
    }

    /**
     * @return the pantallaAsociada
     */
    public String getPantallaAsociada() {
        return pantallaAsociada;
    }

    /**
     * @param pantallaAsociada the pantallaAsociada to set
     */
    public void setPantallaAsociada(String pantallaAsociada) {
        this.pantallaAsociada = pantallaAsociada;
    }

    /**
     * @return the ramo
     */
    public String getRamo() {
        return ramo;
    }

    /**
     * @param ramo the ramo to set
     */
    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    /**
     * @return the claveMenu
     */
    public String getClaveMenu() {
        return claveMenu;
    }

    /**
     * @param claveMenu the claveMenu to set
     */
    public void setClaveMenu(String claveMenu) {
        this.claveMenu = claveMenu;
    }

    /**
     * @return the claveOpcionMenu
     */
    public String getClaveOpcionMenu() {
        return claveOpcionMenu;
    }

    /**
     * @param claveOpcionMenu the claveOpcionMenu to set
     */
    public void setClaveOpcionMenu(String claveOpcionMenu) {
        this.claveOpcionMenu = claveOpcionMenu;
    }

    /**
     * @return the situacion
     */
    public String getSituacion() {
        return situacion;
    }

    /**
     * @param situacion the situacion to set
     */
    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }
}
