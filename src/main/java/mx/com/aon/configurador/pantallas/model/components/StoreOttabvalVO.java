/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;


/**
 * @author eflores
 * @date 30/07/2008
 *
 */
public class StoreOttabvalVO extends StoreVO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String OBTENER_LISTA_COMBO_URL = "flujocotizacion/obtenerListaComboOttabval.action";
    
//  Constructors

    public StoreOttabvalVO(){
        
    }
    
    public StoreOttabvalVO(String name){
        setName(name);     
    }

    /* (non-Javadoc)
     * @see com.aon.catweb.portal.model.data.StoreVO#toString()
     */
    @Override
    public String toString() {
        StringBuffer bufferStore = new StringBuffer();
        bufferStore.append("var ").append("store").append(this.getName()).append(" = new Ext.data.Store({");
        bufferStore.append("proxy: new Ext.data.HttpProxy({");
        bufferStore.append("url: '").append(OBTENER_LISTA_COMBO_URL).append("'");
        bufferStore.append("}),");
        bufferStore.append("reader: new Ext.data.JsonReader({");
        bufferStore.append("root: 'itemList',");
        bufferStore.append("id: 'value'");
        bufferStore.append("}, [{");
        bufferStore.append("name : 'value',");
        bufferStore.append("type : 'string',");
        bufferStore.append("mapping : 'value'");
        bufferStore.append("}, {");
        bufferStore.append("name : 'label',");
        bufferStore.append("type : 'string',");
        bufferStore.append("mapping : 'label'");
        bufferStore.append("}]),");
        bufferStore.append("remoteSort : true,");
        bufferStore.append("baseParams : [{");
        bufferStore.append("ottabval : ' '");
        bufferStore.append("}, {");
        bufferStore.append("valAnterior : ' '");
        bufferStore.append("}, {");
        bufferStore.append("valAntAnt : ' '");
        bufferStore.append("}],");
        bufferStore.append("sortInfo : {");
        bufferStore.append("field : 'value',");
        bufferStore.append("direction : 'DESC'");
        bufferStore.append("}");
        bufferStore.append("});");
        
        return bufferStore.toString();
    }
}
