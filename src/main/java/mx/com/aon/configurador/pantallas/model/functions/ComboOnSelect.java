/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.functions;

import java.io.Serializable;
import java.util.List;

import mx.com.aon.configurador.pantallas.model.components.BaseParamComboOnSelectVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;

/**
 *  Clase Value Object con los atributos necesarios
 *  para la generacion de las funciones on select de los componentes de tipo combo
 * 
 * @author  aurora.lozada
 * 
 * usar el correspondiente de  com.biosnet
 */
@Deprecated
public class ComboOnSelect implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9198386267391465416L;

    /**
     * identificador del combo actual que se le aplica la funcion on select
     */
    private String curretId;
    
    /**
     * lista de combos que se les aplicará un clear a traves de la funcion on select
     */
    private List<ComboClearOnSelectVO> comboClearList;
    
    /**
     * lista de baseParams con los valores necesarios para la invocacion del PL
     */
    private List<BaseParamComboOnSelectVO> baseParamList;
    
    /**
     * nombre de la variable asociada al store del combo a actualizar
     */
    private String storeVariable;
    
 // Constructors

    @Override
    public String toString() {

        StringBuffer bufferCombo = new StringBuffer();
        bufferCombo.append("Ext.getCmp('").append(this.curretId).append("').on('select', function(combo,record, ind){");
        bufferCombo.append("&");
        bufferCombo.append(this.comboClearList.toString().replace(",", ";"));
        bufferCombo.delete(bufferCombo.length()-1,bufferCombo.length());
        bufferCombo.append(";");
        bufferCombo.append("&");
        bufferCombo.append(this.baseParamList.toString().replace(",", ";"));
        bufferCombo.delete(bufferCombo.length()-1,bufferCombo.length());
        bufferCombo.append(";");
        bufferCombo.append(this.storeVariable).append(".load({ ");
        bufferCombo.append("callback : function(r,options,success) { if (!success) {");
        bufferCombo.append(this.storeVariable).append(".removeAll();");
        bufferCombo.append("} } }); });");
        return bufferCombo.toString().replace("&[", "  ");
       
        
    }

    
    public ComboOnSelect() {

    }

    public ComboOnSelect(String curretId, List<ComboClearOnSelectVO> comboClearList, List<BaseParamComboOnSelectVO> baseParamList, String storeVariable) {
        this.curretId = curretId;
        this.comboClearList = comboClearList;
        this.baseParamList = baseParamList;
        this.storeVariable = storeVariable;
    }

    // Getters y setters
    
    /**
     * @return the curretId
     */
    public String getCurretId() {
        return curretId;
    }

    /**
     * @param curretId the curretId to set
     */
    public void setCurretId(String curretId) {
        this.curretId = curretId;
    }

   

    /**
     * @return the baseParamList
     */
    public List<BaseParamComboOnSelectVO> getBaseParamList() {
        return baseParamList;
    }

    /**
     * @param baseParamList the baseParamList to set
     */
    public void setBaseParamList(List<BaseParamComboOnSelectVO> baseParamList) {
        this.baseParamList = baseParamList;
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
     * @return the comboClearList
     */
    public List<ComboClearOnSelectVO> getComboClearList() {
        return comboClearList;
    }


    /**
     * @param comboClearList the comboClearList to set
     */
    public void setComboClearList(List<ComboClearOnSelectVO> comboClearList) {
        this.comboClearList = comboClearList;
    }

}
