/**
 * 
 */
package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;
import java.util.List;

/**
 * MasterVO
 * 
 * 
 *  Clase Value Object para los atributos del master 
 *  
 *  
 * @author  aurora.lozada
 * 
 */

public class MasterVO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2882137745933124968L;

    
    private String cdTipo;
    
    private String cdMaster;
    
    private String dsMaster;

    
    private List <SectionVo> seccionList;
    
    /**
     * @return the cdTipo
     */
    public String getCdTipo() {
        return cdTipo;
    }

    /**
     * @param cdTipo the cdTipo to set
     */
    public void setCdTipo(String cdTipo) {
        this.cdTipo = cdTipo;
    }

    /**
     * @return the cdMaster
     */
    public String getCdMaster() {
        return cdMaster;
    }

    /**
     * @param cdMaster the cdMaster to set
     */
    public void setCdMaster(String cdMaster) {
        this.cdMaster = cdMaster;
    }

    /**
     * @return the dsMaster
     */
    public String getDsMaster() {
        return dsMaster;
    }

    /**
     * @param dsMaster the dsMaster to set
     */
    public void setDsMaster(String dsMaster) {
        this.dsMaster = dsMaster;
    }

    /**
     * @return the seccionList
     */
    public List<SectionVo> getSeccionList() {
        return seccionList;
    }

    /**
     * @param seccionList the seccionList to set
     */
    public void setSeccionList(List<SectionVo> seccionList) {
        this.seccionList = seccionList;
    }
   
}
