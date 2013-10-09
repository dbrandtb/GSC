/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 04/08/2008
 *
 */
public class ComboOnSelectComponentsVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String currentId;
    
    private Map<Integer, String> ids;
    
    private String storeVariable;
    
    private String ottabval;
    
    private String valAnterior;
    
    private String valAntAnt;

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @return the currentId
     */
    public String getCurrentId() {
        return currentId;
    }

    /**
     * @param currentId the currentId to set
     */
    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    /**
     * @return the ottabval
     */
    public String getOttabval() {
        return ottabval;
    }

    /**
     * @param ottabval the ottabval to set
     */
    public void setOttabval(String ottabval) {
        this.ottabval = ottabval;
    }

    /**
     * @return the storeVariable
     */
    public String getStoreVariable() {
        return storeVariable;
    }

    /**
     * @param storeVariable the storeVariable to set
     */
    public void setStoreVariable(String storeVariable) {
        this.storeVariable = storeVariable;
    }

    /**
     * @return the valAntAnt
     */
    public String getValAntAnt() {
        return valAntAnt;
    }

    /**
     * @param valAntAnt the valAntAnt to set
     */
    public void setValAntAnt(String valAntAnt) {
        this.valAntAnt = valAntAnt;
    }

    /**
     * @return the valAnterior
     */
    public String getValAnterior() {
        return valAnterior;
    }

    /**
     * @param valAnterior the valAnterior to set
     */
    public void setValAnterior(String valAnterior) {
        this.valAnterior = valAnterior;
    }

    /**
     * @return the ids
     */
    public Map<Integer, String> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(Map<Integer, String> ids) {
        this.ids = ids;
    }
    
}
