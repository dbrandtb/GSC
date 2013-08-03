/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.controls;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;


/**
 * @author eflores
 * @date 18/06/2008
 *
 */
@Deprecated
public class ComboControl extends AbstractMasterModelControl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private String emptyText;
    
    /**
     * 
     */
    private String hiddenName;
    
    /**
     * 
     */
    private int listWidth;
    
    /**
     * 
     */
    private int width;
    
    private boolean typeAhead;
    
    private String store;
    
    private String displayField;
    
    private String valueField; 
    
    private String triggerAction;
    
    private String mode; 
    
    private boolean editable;
    
    private boolean forceSelection;
    
    private String labelSeparator;
    
    /**
     * 
     */
    private boolean selectOnFocus;
    
    /**
     * 
     */
    private JSONFunction onSelect;
    
    /**
     * 
     */
    private Boolean allowBlank;
    
    /**
     * 
     */
    private int height;
    
    
    /**
     * 
     */
    private String backupTable;
    
    /**
     * 
     */
    private String grouping;
    
    /**
     * 
     */
    private String groupingId;
    
    /**
     * 
     */
    public ComboControl() {
		xtype = COMBO_TYPE;
	}
    
    /**
     * 
     */
    @Override
    public String toString() {
    	
    	if( listWidth <= 0 ){
    		listWidth = 200;
    	}
    	
    	if( width <= 0 ){
    		width = 200;
    	}
    	
    	if( StringUtils.isBlank(triggerAction) ){
    		triggerAction = "all";
    	}
    	
    	
    	JSONObject jsonResult = JSONObject.fromObject(this);
    	
    	jsonResult.remove("backupTable");
    	jsonResult.remove("grouping");
    	jsonResult.remove("groupingId");
        
    	
    	if( height <= 0){
    		jsonResult.remove("height");
    	}
    	
    	
    	if( StringUtils.isBlank(this.id) ){
    		jsonResult.remove("id");
    	}
    	
    	
    	if ( StringUtils.isBlank(value) ){
    		jsonResult.remove("value");
    	}
    	
    	
    	if(  allowBlank == null   ){
    		jsonResult.element("allowBlank", true);
    	}    	
    	
    	if( onSelect == null){
    		jsonResult.remove("onSelect");
    	}
    	
    	if (StringUtils.isBlank(store)) {
        	jsonResult.remove("store");
            return jsonResult.toString();
        }else {
        	if (JSONUtils.mayBeJSON(store) ){
        		return jsonResult.toString();
        	}else{
        		StringBuilder jsonResult1 = new StringBuilder().append("\"store\":\"").append(this.store).append("\"");
                StringBuilder jsonResult2 = new StringBuilder().append("\"store\":").append(this.store);        		
        		
                return jsonResult.toString().replace(jsonResult1.toString(), jsonResult2.toString());
        	}
        }
    }
    
    /**
     * @return the displayField
     */
    public String getDisplayField() {
        return displayField;
    }
    /**
     * @param displayField the displayField to set
     */
    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }    
    /**
     * @return the emptyText
     */
    public String getEmptyText() {
        return emptyText;
    }
    /**
     * @param emptyText the emptyText to set
     */
    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }    
    /**
     * @return the hiddenName
     */
    public String getHiddenName() {
        return hiddenName;
    }
    /**
     * @param hiddenName the hiddenName to set
     */
    public void setHiddenName(String hiddenName) {
        this.hiddenName = hiddenName;
    }
    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }
    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }    
    /**
     * @return the store
     */
    public String getStore() {
        return store;
    }
    /**
     * @param store the store to set
     */
    public void setStore(String store) {
        this.store = store;
    }
    /**
     * @return the triggerAction
     */
    public String getTriggerAction() {
        return triggerAction;
    }
    /**
     * @param triggerAction the triggerAction to set
     */
    public void setTriggerAction(String triggerAction) {
        this.triggerAction = triggerAction;
    }    
    /**
     * @return the valueField
     */
    public String getValueField() {
        return valueField;
    }
    /**
     * @param valueField the valueField to set
     */
    public void setValueField(String valueField) {
        this.valueField = valueField;
    }    
    /**
     * @return the listWidth
     */
    public int getListWidth() {
        return listWidth;
    }
    /**
     * @param listWidth the listWidth to set
     */
    public void setListWidth(int listWidth) {
        this.listWidth = listWidth;
    }
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * @return the typeAhead
     */
    public boolean isTypeAhead() {
        return typeAhead;
    }
    /**
     * @param typeAhead the typeAhead to set
     */
    public void setTypeAhead(boolean typeAhead) {
        this.typeAhead = typeAhead;
    }
    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }
    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the forceSelection
     */
    public boolean isForceSelection() {
        return forceSelection;
    }

    /**
     * @param forceSelection the forceSelection to set
     */
    public void setForceSelection(boolean forceSelection) {
        this.forceSelection = forceSelection;
    }

    /**
     * @return the labelSeparator
     */
    public String getLabelSeparator() {
        return labelSeparator;
    }

    /**
     * @param labelSeparator the labelSeparator to set
     */
    public void setLabelSeparator(String labelSeparator) {
        this.labelSeparator = labelSeparator;
    }

    /**
     * @return the selectOnFocus
     */
    public boolean isSelectOnFocus() {
        return selectOnFocus;
    }

    /**
     * @param selectOnFocus the selectOnFocus to set
     */
    public void setSelectOnFocus(boolean selectOnFocus) {
        this.selectOnFocus = selectOnFocus;
    }

	public JSONFunction getOnSelect() {
		return onSelect;
	}

	public void setOnSelect(JSONFunction onSelect) {
		this.onSelect = onSelect;
	}
	
	
    public static void main(String[] args) {
		
    	ComboMaster combo = new ComboMaster();
		combo.setDisplayField("label");
		combo.setEditable(false);
		combo.setEmptyText("Seleccione");
		combo.setFieldLabel("Marca");
		combo.setForceSelection(true);
		combo.setHiddenName("hmarca");
		combo.setId("parameters.marca");
		combo.setListWidth(200);
		combo.setMode("local");
		combo.setName("marca");
//		combo.setOnSelect("marca");
		combo.setXtype("combo");
		
		combo.setWidth(200);
		combo.setBackupTable("APDIT008");
		System.out.println("combo MARCAes " + combo);
		
		
		ComboControl combo2 = new ComboControl();
		combo2.setDisplayField("label");
		combo2.setEditable(false);
		combo2.setEmptyText("Seleccione");
		combo2.setFieldLabel("Modelo");
		combo2.setForceSelection(true);
		combo2.setHiddenName("hmodelo");
		combo2.setId("parameters.modelo");
		combo2.setListWidth(200);
		combo2.setMode("local");
		combo2.setName("parameters.modelo");
//		combo.setOnSelect("modelo");
		combo2.setXtype("combo");
		combo2.setStore("ANIOAUTO");
		combo2.setWidth(200);
		System.out.println("combo MODELO es " + combo2);
		
		
		ComboControl combo3 = new ComboControl();
		combo3.setDisplayField("label");
		combo3.setEditable(false);
		combo3.setEmptyText("Seleccione");
		combo3.setFieldLabel("Descripcion");
		combo3.setForceSelection(true);
		combo3.setHiddenName("hdescripcion");
		combo3.setId("descripcion");
		combo3.setListWidth(200);
		combo3.setMode("local");
		combo3.setName("parameters.descripcion");
		combo3.setXtype("combo");
		combo3.setStore("APDIT016");
		combo3.setWidth(200);
		System.out.println("combo DESCRIPCION es " + combo3);
		
		
		List<ComboControl> lista = new ArrayList<ComboControl>();
		lista.add(combo);
		lista.add(combo2);
		lista.add(combo3);
		
//		JSONObject json = JSONObject.fromObject(lista);
		JSONArray array = JSONArray.fromObject(lista);
		XMLSerializer xmls = new XMLSerializer();
		xmls.setRootName("master");
		xmls.setElementName("element");
		
		
//		xmls.setArrayName("");
		
		String xml = xmls.write( array, "ISO-8859-1" );
		
		System.out.println("xml es" + xml );
		
//		combo.setOnSelect("otval");
	}

    /**
     * 
     * @return
     */
	public String getBackupTable() {
		return backupTable;
	}

	/**
	 * 
	 * @param backupTable
	 */
	public void setBackupTable(String backupTable) {
		this.backupTable = backupTable;
	}

	/**
	 * 
	 * @return
	 */
	public String getGrouping() {
		return grouping;
	}

	/**
	 * 
	 * @param grouping
	 */
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	/**
	 * 
	 * @return
	 */
	public String getGroupingId() {
		return groupingId;
	}

	/**
	 * 
	 * @param groupingId
	 */
	public void setGroupingId(String groupingId) {
		this.groupingId = groupingId;
	}


	/**
	 * @return the allowBlank
	 */
	public Boolean getAllowBlank() {
		return allowBlank;
	}


	/**
	 * @param allowBlank the allowBlank to set
	 */
	public void setAllowBlank(Boolean allowBlank) {
		this.allowBlank = allowBlank;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}    
    
}
