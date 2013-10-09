/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import net.sf.json.JSONObject;

/**
 * @author eflores
 * @date 15/08/2008
 *
 */
public class ColumnGridEstandarVO extends ColumnGridVO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    public ColumnGridEstandarVO() {
        super();
    }



    /**
     * @param header
     * @param dataIndex
     * @param width
     * @param sortable
     * @param id
     * @param hidden
     */
    public ColumnGridEstandarVO(String header, String dataIndex, int width, Boolean sortable, String id, boolean hidden) {
        super(header, dataIndex, width, sortable, id, hidden);
    }



    public String toString() {        
        return JSONObject.fromObject(this).toString();
    }
}
