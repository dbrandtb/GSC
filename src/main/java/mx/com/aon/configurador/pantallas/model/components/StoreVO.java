/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;

/**
 *  Clase Value Object con los atributos necesarios
 *  para la generacion de los stores
 * 
 * @author  aurora.lozada
 * 
 */
public class StoreVO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3597853469388694093L;
    /**
     * nombre de la variable que almacenara el Ext.data.Store
     */
    private String name;
    
    /**
     * tipo del store
     */
    private String typeStore;
    
    /**
     * url de HttpProxy
     */
    private String url;
    
    /**
     * root de JsonReader
     */
    private String root;
    
    /**
     * id de JsonReader
     */
    private String id;
    
    /**
     * 
     */
    private boolean remoteSort;
    
    
    private List<RecordVO> recordList;

    /**
     * totalProperty de JsonReader
     */
    private String totalProperty;
    
    private List<BaseParamVO> baseParamList;
    
    private String sortField;
    
    private String sortDirection;
    
    private String groupField;
    
    //Constructors

    public StoreVO(){
        
    }
    
    public StoreVO(String name, String typeStore, String url, String root, String id, List<RecordVO> recordList, boolean remoteSort, String totalProperty, List<BaseParamVO> baseParamList, String sortField, String sortDirection, String groupField){
        this.name = name;
        this.typeStore = typeStore;
        this.url = url;
        this.root= root;
        this.id = id;
        this.recordList = recordList;
        this.remoteSort = remoteSort;
        this.totalProperty = totalProperty;
        this.baseParamList = baseParamList;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.groupField = groupField;
    }
    
    @Override
    public String toString() {
        
        StringBuffer bufferStore = new StringBuffer();
        bufferStore.append("var ").append(this.name).append(" = new ").append(this.typeStore).append(" ({");
        bufferStore.append("proxy: new Ext.data.HttpProxy({");
        bufferStore.append("url: '").append(this.url).append("'");
        bufferStore.append("}),");
        bufferStore.append("reader: new Ext.data.JsonReader({");
        bufferStore.append("root: '").append(this.root).append("',");
        bufferStore.append("id: '").append(this.id).append("'");
        bufferStore.append("},");
        bufferStore.append( JSONArray.fromObject(this.recordList).toString() );
        bufferStore.append(" ),");
        bufferStore.append("remoteSort: ").append(this.remoteSort);
        if(this.baseParamList!=null){
            bufferStore.append(",");
        bufferStore.append("baseParams: ").append(this.baseParamList.toString() );
        }
        if(this.sortField!=null && this.sortDirection!=null){
        bufferStore.append(",");
        bufferStore.append("sortInfo: { field:'").append(this.sortField).append("', direction:'").append(this.sortDirection).append("'}");
        }
        if(this.groupField!=null){
            bufferStore.append(",");
            bufferStore.append("groupField: '").append(this.groupField).append("'");
            }
        bufferStore.append("});");
       
        return bufferStore.toString();
    }
   
    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRemoteSort() {
        return remoteSort;
    }

    public void setRemoteSort(boolean remoteSort) {
        this.remoteSort = remoteSort;
    }
    
    public List<RecordVO> getRecordList() {
        return recordList;
    }

    

    public void setRecordList(List<RecordVO> recordList) {
        this.recordList = recordList;
    }

    public String getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(String totalProperty) {
        this.totalProperty = totalProperty;
    }

    /**
     * @return the baseParamList
     */
    public List<BaseParamVO> getBaseParamList() {
        return baseParamList;
    }

    /**
     * @param baseParamList the baseParamList to set
     */
    public void setBaseParamList(List<BaseParamVO> baseParamList) {
        this.baseParamList = baseParamList;
    }

    /**
     * @return the typeStore
     */
    public String getTypeStore() {
        return typeStore;
    }

    /**
     * @param typeStore the typeStore to set
     */
    public void setTypeStore(String typeStore) {
        this.typeStore = typeStore;
    }

    /**
     * @return the sortField
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * @param sortField the sortField to set
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }


    /**
     * @return the sortDirection
     */
    public String getSortDirection() {
        return sortDirection;
    }

    /**
     * @param sortDirection the sortDirection to set
     */
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    /**
     * @return the groupField
     */
    public String getGroupField() {
        return groupField;
    }

    /**
     * @param groupField the groupField to set
     */
    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

}
