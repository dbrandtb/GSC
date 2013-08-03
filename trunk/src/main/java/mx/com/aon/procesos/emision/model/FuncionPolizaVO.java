/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Cesar Hernandex
 *
 */
public class FuncionPolizaVO {
    private String rol;
    private String nombre;
    private String nmSituac;
    private String cdRol;
    private String cdPerson;
    private String nmaximo;
    private String swDomici;
    private String swObliga;
    private String totalPerRol;
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }
    
    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nmSituac
     */
    public String getNmSituac() {
        return nmSituac;
    }

    /**
     * @param nmSituac the nmSituac to set
     */
    public void setNmSituac(String nmSituac) {
        this.nmSituac = nmSituac;
    }

    /**
     * @return the cdRol
     */
    public String getCdRol() {
        return cdRol;
    }

    /**
     * @param cdRol the cdRol to set
     */
    public void setCdRol(String cdRol) {
        this.cdRol = cdRol;
    }

    /**
     * @return the cdPerson
     */
    public String getCdPerson() {
        return cdPerson;
    }

    /**
     * @param cdPerson the cdPerson to set
     */
    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }

    /**
     * @return the nmaximo
     */
    public String getNmaximo() {
        return nmaximo;
    }

    /**
     * @param nmaximo the nmaximo to set
     */
    public void setNmaximo(String nmaximo) {
        this.nmaximo = nmaximo;
    }

    /**
     * @return the swDomici
     */
    public String getSwDomici() {
        return swDomici;
    }

    /**
     * @param swDomici the swDomici to set
     */
    public void setSwDomici(String swDomici) {
        this.swDomici = swDomici;
    }

    /**
     * @return the swObliga
     */
    public String getSwObliga() {
        return swObliga;
    }

    /**
     * @param swObliga the swObliga to set
     */
    public void setSwObliga(String swObliga) {
        this.swObliga = swObliga;
    }

    /**
     * @return the totalPerRol
     */
    public String getTotalPerRol() {
        return totalPerRol;
    }

    /**
     * @param totalPerRol the totalPerRol to set
     */
    public void setTotalPerRol(String totalPerRol) {
        this.totalPerRol = totalPerRol;
    }
}
