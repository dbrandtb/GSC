package mx.com.aon.configurador.pantallas.model.controls;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

@Deprecated
public class ComboMaster extends ComboControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8864795578003816884L;
	
	/**
	 * 
	 */
	public ComboMaster() {
		xtype = COMBO_TYPE;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {		
				
		JSONObject json = JSONObject.fromObject(super.toString());
		StringBuilder masterId = new StringBuilder( super.getId() ).append("|").append(super.getBackupTable());
		
		if(  StringUtils.isNotBlank(super.getGrouping())  && StringUtils.isNotBlank(getGroupingId()) ){
			masterId.append("|").append( super.getGrouping() ).append("|").append(super.getGroupingId());
		}
		
		json.put("id", masterId.toString()  );
		
		return json.toString();
	}
	
	
	

}
