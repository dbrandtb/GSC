package mx.com.aon.configurador.pantallas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.components.ColumnGridEstandarVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.com.aon.configurador.pantallas.model.AtributosMasterVO;
import mx.com.aon.configurador.pantallas.model.components.RecordVO;
import mx.com.aon.configurador.pantallas.model.master.MasterGrid;
import mx.com.aon.configurador.pantallas.model.master.NameLabelControlVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import com.biosnet.ice.ext.elements.data.StoreVO;
import com.biosnet.ice.ext.elements.form.ButtonMaster;
import com.biosnet.ice.ext.elements.form.CheckBoxControl;
import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;

import static com.biosnet.ice.ext.elements.form.ExtConstants.COMBO_TYPE;
import static com.biosnet.ice.ext.elements.form.ExtConstants.NUMBER_FIELD_TYPE;
import static com.biosnet.ice.ext.elements.form.ExtConstants.TEX_FIELD_TYPE;
import static com.biosnet.ice.ext.elements.form.ExtConstants.DATEFIELD_TYPE;
import static com.biosnet.ice.ext.elements.form.ExtConstants.CHECKBOX_TYPE;
//import static com.biosnet.ice.ext.elements.form.ExtConstants.BUTTON_RESET;
//import static com.biosnet.ice.ext.elements.form.ExtConstants.BUTTON_SUBMIT;
import com.biosnet.ice.ext.elements.utils.NestedComboUtils;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * 
 * @author Leopoldo Ramirez Montes
 *
 */
public class MasterTrasnformer implements UtilTransformer {

	private final transient Logger logger = Logger.getLogger(MasterTrasnformer.class);
    
    private static final String MASTER_TIPO_GRID = "1"; 
    
    private static final String CD_MASTER_COMPRA = "8";

    private static final String CD_MASTER_CAPTURA_COTIZACION = "2";
    
    private static final String CD_MASTER_CONSULTA_COTIZACION = "1";
    
    private static final String TIPO_LAYOUT_BORDER = "border";
    
    private static final String TIPO_LAYOUT_FORM = "form";
	
	Map<String,Endpoint> endpoints;


	/**
	 * @param endpoints the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void generateButtonArea(JSONObject json, Map<String, ?> parameters){		
		
		logger.debug("#### Entrando a generar Area de botones...");
		
		Endpoint endpoint = endpoints.get("GET_MASTER_BUTTONS");
		
		logger.debug("##### endpoint obtenido es " +  endpoint);		
		
		List<ButtonMaster> buttons = null;
		try {
            buttons = (List<ButtonMaster>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {			
			logger.error("Exception al obtener el area de botones" + bae);			
		}					
		
		logger.debug("#### botones son " + buttons );
		
		if(buttons != null && !buttons.isEmpty()){
			
			JSONArray buttonArray = new JSONArray();
			buttonArray.addAll(buttons);
			
			json.put("buttons", buttonArray );
		}

	}
    
    /**
     * @return 
     * 
     */
    @SuppressWarnings("unchecked")
    private List<ButtonMaster> generateButtonAreaGrid(Map<String, Object> parameters){        
        
        logger.debug("#### Entrando a generar Area de botones Grid...");
        
        Endpoint endpoint = endpoints.get("GET_MASTER_BUTTONS");
        
        logger.debug("##### endpoint obtenido es " +  endpoint);        
        
        List<ButtonMaster> buttons = null;
        try {
            buttons = (List<ButtonMaster>) endpoint.invoke(parameters);
        } catch (BackboneApplicationException bae) {            
            logger.error("Exception al obtener el area de botones" + bae);          
        }     
        //TODO quitar
        ButtonMaster submitGridButton = new ButtonMaster();
        submitGridButton.setId("cotizar");
        submitGridButton.setText("Buscar");
        submitGridButton.setButtonType("gridsubmit");
        submitGridButton.setUrl("agregaIncisos.action");
        submitGridButton.setSuccessAction("agregaIncisos.action");
        submitGridButton.setFailureAction("agregaIncisos.action");
        
        ButtonMaster resetButton = new ButtonMaster();
        resetButton.setId("limpiar");
        resetButton.setText("Limpiar");
        resetButton.setButtonType("reset");
        resetButton.setSuccessAction("flujocotizacion/limpiar.action");
        
        if (buttons == null) {
            buttons = new ArrayList<ButtonMaster>();            
        }
        
        buttons.add(submitGridButton);
        buttons.add(resetButton);
        ///////////////////////////////////////////////
        
        logger.debug("#### botones son " + buttons );
        
        return buttons;
    }
	
	/**
	 * 
	 * @param itemList
	 * @param comboList
	 * @param element
	 * @param isArray
	 * @param array2 
	 */
	private void searchExtControls ( List<ComboControl> comboList, JSONObject element, boolean isArray,  
            List<ExtElement> extList, JSONArray array2  ){
		logger.debug("isArray " + isArray);		
		JSONArray array = null;		
        ComboControl combo = null;
        TextFieldControl text = null;
        CheckBoxControl checkBoxControl = null;

		if( isArray ){
			array = element.getJSONArray("items");
			searchNestedControls( comboList ,array, extList, array2);			
		}else{
			logger.debug("element xtype is " +  element.get("xtype") +  " element is " + element   );
            
            if (element.get("xtype") != null) {
    			if(  COMBO_TYPE.equals( element.get("xtype") )   ){
    				combo = new ComboControl();
    
    				//index[0] : id   index[1]: backupTable  index[2]:Grouping, index[3]:GroupingId
    				String[] idTable = element.getString("id").split("[|]");				
    				logger.debug("idTable length is " + idTable.length + " elementos son" + idTable.toString()  );
    
    				element.put("id", idTable[0] );				
    
    				String storeName = new StringBuilder("store").append(idTable[1]).toString();
    				element.put("store", storeName  );				
    
    				//TODO: VERIFICAR LA FORMA DE HACER SINGLETEON  LA CLASE NestedComboUtils
    				element.put( "onSelect",  new NestedComboUtils().getOnSelect( idTable[0] )  );				
    
    				combo.setId(idTable[0]);
    				combo.setBackupTable(idTable[1]);
                    combo.setStore(storeName);
                    combo.setFieldLabel(element.getString("fieldLabel"));
                    if (element.containsKey("width")) {
                        combo.setWidth(element.getInt("width"));
                    }
                    if (element.containsKey("allowBlank")) {
                        combo.setAllowBlank(element.getBoolean("allowBlank"));
                    }
                    if (element.containsKey("readOnly")) {
                        combo.setReadOnly(element.getBoolean("readOnly"));
                    }
                    if (element.containsKey("disabled")) {
                        combo.setDisabled(element.getBoolean("disabled"));
                    }
                    //combo.setName(StringUtils.remove(element.getString("name"), "parameters."));
                    combo.setName(element.getString("name"));
                    if (element.containsKey("hiddenName")) {
                        combo.setHiddenName(element.getString("hiddenName"));
                    }
                    if (element.containsKey("Ayuda")) {
                        combo.setAyuda(element.getString("Ayuda"));
                    }
                    if (element.containsKey("tooltip")) {
                        combo.setTooltip(element.getString("tooltip"));
                    }
                    if (element.containsKey("usarcomoFiltro")) {
                        combo.setUsarcomoFiltro(element.getBoolean("usarcomoFiltro"));
                    }
    
    				//Para agrupar elementos
    				if( idTable.length == 4 ){
    					combo.setGrouping(idTable[2]);
    					combo.setGroupingId(idTable[3]);
    				}
    
    				comboList.add(combo);	
                    if (!extList.contains(combo)) {
                        extList.add(combo);
                    }
    			}else  if( NUMBER_FIELD_TYPE.equals(element.getString("xtype")) || TEX_FIELD_TYPE.equals(element.getString("xtype"))
    					  ||  DATEFIELD_TYPE.equals(element.getString("xtype")) || "timefield".equals(element.getString("xtype")) ) {
    			
    				text = new TextFieldControl();				
    				text.setXtype(element.getString("xtype"));
    				text.setFieldLabel(element.getString("fieldLabel"));
                    if (element.containsKey("width")) {
                        text.setWidth(element.getInt("width"));
                    }
                    if (element.containsKey("allowBlank")) {
                        text.setAllowBlank(element.getBoolean("allowBlank"));
                    }
                    if (element.containsKey("readOnly")) {
                        text.setReadOnly(element.getBoolean("readOnly"));
                    }
                    if (element.containsKey("disabled")) {
                        text.setDisabled(element.getBoolean("disabled"));
                    }
                    if (element.containsKey("maxLength")) {
                        text.setMaxLength(element.getInt("maxLength"));
                    }
                    if (element.containsKey("minLength")) {
                        text.setMinLength(element.getInt("minLength"));
                    }
                    if (element.containsKey("maxLengthText")) {
                        text.setMaxLengthText(element.getString("maxLengthText"));
                    }
                    if (element.containsKey("minLengthText")) {
                        text.setMinLengthText(element.getString("minLengthText"));
                    }
                    if (element.containsKey("id")) {
                        text.setId(element.getString("id"));
                    }
                    if (element.containsKey("tooltip")) {
                        text.setTooltip(element.getString("tooltip"));
                    }
                    if (element.containsKey("Ayuda")) {
                        text.setAyuda(element.getString("Ayuda"));
                    }
                    
                    //text.setName(StringUtils.remove(element.getString("name"), "parameters."));
                    text.setName(element.getString("name"));
                    if (element.containsKey("usarcomoFiltro")) {
                        text.setUsarcomoFiltro(element.getBoolean("usarcomoFiltro"));
                    }
                    logger.debug(":: element text is " +  text);
                    if (!extList.contains(text)) {
                        extList.add(text);
                    }
    			} else if (CHECKBOX_TYPE.equals(element.getString("xtype"))) {
    				checkBoxControl = new CheckBoxControl();
    				checkBoxControl.setXtype(element.getString("xtype"));
    				checkBoxControl.setFieldLabel(element.getString("fieldLabel"));
    				//checkBoxControl.setName(StringUtils.remove(element.getString("name"), "parameters."));
                    checkBoxControl.setName(element.getString("name"));
    				if (element.containsKey("usarcomoFiltro")) {
    					checkBoxControl.setUsarcomoFiltro(element.getBoolean("usarcomoFiltro"));
    				}
                    if (element.containsKey("tooltip")) {
                        checkBoxControl.setTooltip(element.getString("tooltip"));
                    }
                    if (element.containsKey("Ayuda")) {
                        checkBoxControl.setAyuda(element.getString("Ayuda"));
                    }
                    
    				if (!extList.contains(checkBoxControl)) {
    					extList.add(checkBoxControl);
    				}
    			}
                
                logger.debug("element.containsKey(usarcomoFiltro)..." + element.containsKey("usarcomoFiltro"));
                
/*
 * TODO: Corregir los valores de la propiedad "usarcomoFiltro" para el caso MASTER_TIPO_GRID (el cual usa el array "array2.add(element);" 
 * Ya que no hay algun elemento que tenga dicha propiedad fijada en "true", y no sale ningun filtro en la busqueda. 
 * En su dicho caso saber donde debe ser fijada esta propiedad.
 * Asi, de esta forma, descomentar lo siguiente: 
*/
	
/*              if (element.containsKey("usarcomoFiltro")) {
                    logger.debug("element.getBoolean(usarcomoFiltro)..." + element.getBoolean("usarcomoFiltro"));
                    boolean notHidden = element.getBoolean("usarcomoFiltro"); //mostrar en Filtros
                    if (notHidden) {
                        array2.add(element);
                    }
                }
*/
                array2.add(element);
            } else {
                array2.add(element);
            }
		}        
	}

	
	
	
	/**
	 * 
	 * @param itemList
	 * @param comboList
	 * @param array
	 * @param array2 
	 */
	@SuppressWarnings("unchecked")
	private void searchNestedControls( List<ComboControl> comboList,JSONArray array , 
            List<ExtElement> extList, JSONArray array2  ){
		Iterator<JSONObject> iterador = array.iterator();
		JSONObject element = null;		
		while( iterador.hasNext() ){
			element = iterador.next();
			searchExtControls( comboList, element, element.containsKey("items"), extList, array2 );				
		}
	}

	/*
	 * @param actual
	 * @param combos
	 * @param listaClearValue
	 * @param comboList
	 * @return lcv
	 * Mètodo que realiza una revisiòn sobre todos los combos para determinar si un combo debe 
	 * de incluir a ciertos combos en el evento onSelect segùn el mapa 'combos'.
	 */
	@SuppressWarnings("unchecked")
	private Map<String,String> finalOnSelect( String actual, Map<String,String> combos, Map<String,String> listaClearValue, List<ComboControl> comboList ) {
		Map<String,String> lcv = listaClearValue;
		Map<String,String> combosCopia = combos; 
		
		for ( int i = 0; i < comboList.size(); i++ ) {
			ComboControl c = comboList.get(i);
			String padre = c.getBackupTable();
			logger.debug("|||| PADRE = " + padre);
			if ( actual.equals( combos.get(padre) ) ) {
				logger.debug("|||| ENTRO IF CON = " + padre);
				lcv.put( " Ext.getCmp('" + c.getId() + "').clearValue(); ", "");
				combosCopia.remove( actual );
				lcv = finalOnSelect( c.getBackupTable(), combosCopia, lcv, comboList );
			}
		}
		
		return lcv;
	}
	
	
	/*
	 * @param completeJson
	 * @param cdtipsit
	 * @param comboList
	 * @return res
	 * Mètodo que modifica la cadena 'completeJson' para sólo dejar en el evento onSelect de 
	 * los combos padres a los combos hijos invocar el método clearValue().
	 */
	@SuppressWarnings("unchecked")
	private StringBuilder modifyOnSelect(StringBuilder completeJson, String cdtipsit, List<ComboControl> comboList){
		logger.debug("##### Entrando a método modifyOnSelect");
		
		StringBuilder res = completeJson;
		Map<String,String> combos = new HashMap<String,String>();
		
		for ( ComboControl c : comboList )
			combos.put( c.getBackupTable(), null );

		/*
		 * Este bloque llena el mapa 'combos' para determinar quién es el padre de cada uno de los combos
		 */
		try {
			Endpoint endpoint = endpoints.get("GET_COMBO_PADRE");
			for ( int i = 0; i < comboList.size(); i++ ) {
				Map<String,String> parameters = new HashMap<String,String>();
				ComboControl c = comboList.get(i);
				parameters.put("cdtipsit", cdtipsit);
				parameters.put("ottabval", c.getBackupTable());
				List<ComboClearOnSelectVO> resultados = (List<ComboClearOnSelectVO>) endpoint.invoke(parameters);
				if ( resultados.size() != 0 ) {
					ComboClearOnSelectVO ccosvo = resultados.get(0);
					combos.put(c.getBackupTable(), ccosvo.getOttabval());
				} else {
					combos.put(c.getBackupTable(), null);
				}
			}
		} catch (BackboneApplicationException bae) {			
			logger.error("Exception al obtener combos padres: " + bae);
		}

		
		Iterator it = combos.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			logger.debug("key = " + entry.getKey() + " value = " + entry.getValue());
		}

		/*
		 * Se modifica el evento onSelect de los combos para permitir a los combos que tienen  
		 * relación en el filtrado invocar el método clearValue.
		 */
		for ( int i = 0; i < comboList.size(); i++ ) {
			Map<String,String> lcv = new HashMap<String,String>();
			ComboControl c = comboList.get(i);
			lcv = finalOnSelect(c.getBackupTable(), combos, lcv, comboList);
			
			Iterator iter = lcv.entrySet().iterator();
			String listaCombosClearValue = "";

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				listaCombosClearValue += entry.getKey();
			}

			logger.debug("@@@ listaCombosClearValue = " + listaCombosClearValue);
			int startOnSelect = res.indexOf( "Ext.getCmp('" + (String) c.getId() + "').on('select',function(combo,record,ind) { " );
			int endOnSelect = res.indexOf("}); });", startOnSelect);
			if ( startOnSelect != -1 ) {
				String id = (String)c.getId();
				startOnSelect += "Ext.getCmp('".length() + id.length() + "').on('select',function(combo,record,ind) { ".length();
				StringBuilder tmp = new StringBuilder ( res.substring(startOnSelect, endOnSelect) );
				int endClearValue = tmp.lastIndexOf(".clearValue();") + ".clearValue();".length() + 1;
				tmp = tmp.replace( 0, endClearValue, listaCombosClearValue);
				res = res.replace( startOnSelect, endOnSelect, tmp.toString() );
			}
		}
		
		logger.debug("|||| COMPLETEJSON = " + res.toString());
		
    	logger.debug("#### SALIENDO DE modifyOnSelect");
		
		return res;
	}
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String transform(String file, Map<String, ?> parameters, Boolean vistaPrevia) {
		JSONObject jsonFile = null;
		String cdtipsit = (String) parameters.get("cdTipSit");
		parameters.remove("cdTipSit");

		logger.debug("obteniendo jsonObject...");
        logger.debug("####file##### " + file);
		
		try {
			jsonFile = JSONObject.fromObject(file);
            logger.debug("####jsonFile##### " + jsonFile);
		} catch (JSONException e) {			
			logger.error("#### exception is" + e);
			return null;
		}	
		
		logger.debug("comenzando iteracion de objetos");

		try {

			List<ComboControl> comboList = new ArrayList<ComboControl>();
			List<ExtElement> extList = new ArrayList<ExtElement>();
            JSONArray array = new JSONArray();
            
            if (CD_MASTER_CONSULTA_COTIZACION.equals(parameters.get("cdMaster"))) {
				if(logger.isDebugEnabled())logger.debug("Para el caso de consulta de Cotizaciones, se agrega el titulo de busqueda");
				
				//////////PARA AGREAGAR UN LAYOUT AL MASTER DE CONSULTA Y PONER EL TITULO DE BUSQUEDA//////////
	        	
	            JSONArray itemsConsulta = jsonFile.getJSONArray("items");
	            
	            StringBuilder jsonConsultTotal = new StringBuilder();
	            jsonConsultTotal.append("{\"items\" : [{layout:'column', border : false, html:'<span class=\"x-form-item\" style=\"color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;\">B&uacute;squeda</span><br>', \"items\" : [{\"layout\" : \"form\", \"border\" : false, columnWidth: 1 ,items :");
	            jsonConsultTotal.append(itemsConsulta.toString());
	            jsonConsultTotal.append("}]}]}");
	            
	            if(logger.isDebugEnabled())logger.debug("StringBuffer es : "+jsonConsultTotal.toString());
	            
	            JSONObject jsonTmp = JSONObject.fromObject(jsonConsultTotal.toString());
	            
	            jsonFile.put("items", jsonTmp.getJSONArray("items"));
	            
	            if(logger.isDebugEnabled())logger.debug("jsonARRAY sin el Titulo de busqueda:"+ jsonFile);

	            //////////////////////////////////// FIN PARA LAYOUT DE BUSQUEDA DE LA CONSULTA////////////////
			}
            
            if(!CD_MASTER_CONSULTA_COTIZACION.equals(parameters.get("cdMaster"))){
	            // SE BUSCAN OBJETOS Y SE DETERMINAN TODOS LOS QUE SON COMBOS
	            searchNestedControls( comboList , jsonFile.getJSONArray("items"), extList, array   );		
				
				logger.debug("### extList is " + extList );
	            logger.debug("### array is " + array );
				
	            if(MASTER_TIPO_GRID.equals((String)parameters.get("cdTipo"))) {
	            	jsonFile.put("items" ,  array );
	                
	            }
            }else {
            	searchNestedControls( comboList , jsonFile.getJSONArray("items").getJSONObject(0).getJSONArray("items").getJSONObject(0).getJSONArray("items"), extList, array   );		
				
				logger.debug("### extList is " + extList );
	            logger.debug("### array is " + array );
				
	            if(MASTER_TIPO_GRID.equals((String)parameters.get("cdTipo"))) {
	            	jsonFile.getJSONArray("items").getJSONObject(0).getJSONArray("items").getJSONObject(0).put("items" ,  array );
	                
	            }
            }
            
            logger.debug("json final es: "+jsonFile);
			jsonFile.put("title" ,  (String)parameters.get("nombrePantalla") );
			
			
			
			
			//TODO: CON ENDPOINT OBTENER LA URL DEL FORMULARIO...
			//String url = (String)endpoint.invoke(parameters);
			Endpoint endpoint = endpoints.get("GET_ATRIBUTOS_GENERALES_JSON");
			
			endpoint.invoke(parameters);
			List<AtributosMasterVO> atributosJsonList  =  (List<AtributosMasterVO>)endpoint.invoke(parameters);
			
			if( atributosJsonList != null  && !atributosJsonList.isEmpty() ){
				for(  AtributosMasterVO item : atributosJsonList  ){
					//if(  item.getValues().get("TipoDato").equals("String")  ){
						item.getValues().remove("TipoDato");
						jsonFile.putAll(item.getValues());						
					//}
					
				}
			}
			
			logger.debug("#### jsonFile after map is.. " + jsonFile );
			
			//ESTA AREA SERA SUSTITUIDA POR EL ITERADOR
			jsonFile.put("renderTo", "items");			
			jsonFile.put("labelWidth" , 75 );
			jsonFile.put("id" , "simpleForm");
			jsonFile.put("bodyStyle" , "padding:5px 5px 0");
			jsonFile.put("labelAlign" , "right");
			jsonFile.put("labelWidth" , 120 );
			
			logger.debug("El componente especifica una altura? : "+ jsonFile.containsKey("height"));
			if(!jsonFile.containsKey("height")){
				jsonFile.put("autoHeight" , true);
			}
			
			String tipoLayout = null;
			
			if(!jsonFile.containsKey("layout")){
				jsonFile.put("layout" , "form");
				tipoLayout = TIPO_LAYOUT_FORM;
			}else {
				tipoLayout = jsonFile.getString("layout");
			}
				
            if (!CD_MASTER_COMPRA.equals(parameters.get("cdMaster"))) {
                generateButtonArea( jsonFile, parameters  );
            }
			
			logger.debug("####jsonFile despues de area de botones es " + jsonFile);
			
			StringBuilder completeJson = new StringBuilder();
			
			String jsonString = jsonFile.toString();
            
			if( comboList != null && !comboList.isEmpty() ){
				//TODO: DETERMINAR LA URL DE SERVICIO GENERICO DE LISTAS
				String urlListas = null;
				if(vistaPrevia) urlListas= "../flujocotizacion/obtenerListaComboOttabval.action";
				else urlListas= "flujocotizacion/obtenerListaComboOttabval.action";
				
				List<StoreVO> storeList = new NestedComboUtils().getNestedStoreList(comboList, urlListas);
				for( StoreVO store : storeList ){ 
					completeJson.append( store );					
				}

				JSONObject listeners = new JSONObject();								

				JSONFunction beforerender = new JSONFunction( new NestedComboUtils().generateInitComboParameterListener(comboList) );				
				listeners.put("beforerender", beforerender );				
				jsonFile.put("listeners", listeners);

				jsonString = jsonFile.toString();

				StringBuilder jsonResult1 = null;
				StringBuilder jsonResult2 = null;

				for( ComboControl combo : comboList ){
					jsonResult1 = new StringBuilder().append("\"store\":\"").append(combo.getStore()).append("\"");
					jsonResult2  = new StringBuilder().append("\"store\":").append(combo.getStore());

					jsonString = jsonString.replace(jsonResult1.toString(), jsonResult2.toString());	                
				}
			}

            logger.debug("#### cdMaster es " + parameters.get("cdMaster") );
            
            if (!CD_MASTER_COMPRA.equals(parameters.get("cdMaster"))) {
            	
            	if(logger.isDebugEnabled())logger.debug("El tipo de layout resultante es: " + tipoLayout);
            	
            	if(StringUtils.isNotBlank(tipoLayout)&& TIPO_LAYOUT_BORDER.equalsIgnoreCase(tipoLayout.trim())){
            		completeJson.append(" var simple = new Ext.Panel( ");
            	}else{
            		completeJson.append(" var simple = new Ext.FormPanel( ");
            	}
    			
    			completeJson.append(jsonString);
    			completeJson.append(" ); simple.render(); ");
                
                if(comboList != null &&  !comboList.isEmpty()){
                    completeJson.append( new NestedComboUtils().getNestedOnSelectItemsValue(comboList) );
                    completeJson = modifyOnSelect(completeJson, cdtipsit, comboList);
                }
            } else {
                JSONObject jsonFilecompra = JSONObject.fromObject(file);
                JSONArray items = jsonFilecompra.getJSONArray("items");
                String json = items.toString();
                
                logger.debug("#### items json es " + json );
                                
                completeJson.append("{layout:'form',border:false, items:");                    
                completeJson.append(json);                    
                completeJson.append("}");
            }
				
            logger.debug("#### completeJson es " + completeJson );
			logger.debug("#### tipoMaster es " + parameters.get("cdTipo") );
            
            if(  ((String)parameters.get("cdTipo")).equals("3")   ) {
                //captura
            } else  if(MASTER_TIPO_GRID.equals((String)parameters.get("cdTipo")))  {
            	
            	//TODO: Modificar el codigo de la generacion del grid para la actual forma de realizar consultas de cotizacion
            	//      Ya que actualmente las cotizaciones se guardan completas y no individualmente dependiendo de los planes. 
            	
                //grid
                List<NameLabelControlVO> listFieldNames = getLabelNames(extList);
                
                logger.debug("##############  listFieldNames : " + listFieldNames );
                
                List<ColumnGridEstandarVO> listColumns = null;
                List<RecordVO> listRecords = null;
                
                obtieneColumnasRenglones(listColumns, listRecords);
                
                ColumnGridEstandarVO columnGridVO = null;
                RecordVO recordVO = null;
                if (listFieldNames != null) {
                    for (NameLabelControlVO fieldName : listFieldNames) {
                        columnGridVO = new ColumnGridEstandarVO(
                                fieldName.getFieldName(), fieldName.getName(), 120, true, null, false);
                        
                        if (listColumns == null) {
                            listColumns = new ArrayList<ColumnGridEstandarVO>();
                        }
                        listColumns.add(columnGridVO);
                        
                        //se llena lista de records
                        if (listRecords == null) {
                            listRecords = new ArrayList<RecordVO>();
                        }
                        
                        recordVO = new RecordVO();
                        recordVO.setName(fieldName.getName());
                        recordVO.setMapping(fieldName.getName());
                        recordVO.setType("string");
                        
                        listRecords.add(recordVO);
                    }
                }
                logger.debug("##############  listColumns : " + listColumns );
                logger.debug("##############  listColumnNames : " + listRecords );
                
                Map<String, Object> parametersGrid = new HashMap<String, Object>();
                parametersGrid.put("cdMaster", parameters.get("cdMaster"));
                parametersGrid.put("cdProceso", parameters.get("cdProceso"));
                parametersGrid.put("cdTipo", parameters.get("cdTipo"));
                parametersGrid.put("nombrePantalla", parameters.get("nombrePantalla"));
                parametersGrid.put("cdProducto", parameters.get("cdProducto"));
                parametersGrid.put("tipoBloque", "3");
                
                MasterGrid masterGrid = null;
                
                List<ButtonMaster> buttons = generateButtonAreaGrid(parametersGrid);
                
                if (StringUtils.isNotBlank(completeJson.toString()) &&
                        listColumns != null &&
                        !listColumns.isEmpty()) {
                    masterGrid = new MasterGrid();
                    masterGrid.setListColumns(listColumns);
                    masterGrid.setListRecords(listRecords);
                    masterGrid.setButtons(buttons);
                        
                    StringBuilder builder = new StringBuilder();
                    builder.append(masterGrid.getStoreGrid());
                    builder.append(completeJson.toString());
                    builder.append(masterGrid.getBodyGrid());
                        
                    logger.debug("##### builder :::: " + builder.toString() );
                    
                    completeJson = builder;
                }
            }//tipo 2 de detalle

			logger.debug("### pantalla completa es..." + completeJson );			
			
			if(CD_MASTER_CAPTURA_COTIZACION.equals((String)parameters.get("cdMaster"))){
				logger.debug("Reemplazando codigo de botones para cdMaster CAPTURA DATOS COTIZACION para:  " +completeJson.toString());
				//logger.debug("CADENA RESULTANTE: " + completeJson.toString().replace("window.location = \'resultCotizacion.action\'", "ejecutaValidaciones()"));
				logger.debug("CADENA RESULTANTE: " + completeJson.toString().replace("window.location = 'resultCotizacion.action'", "ejecutaValidaciones()"));
				
				return completeJson.toString().replace("window.location = 'resultCotizacion.action'", "ejecutaValidaciones()");
			}
				
			
			return completeJson.toString();
		
		}catch(Exception e){
			logger.debug( "#### Exception is " + e );
			return null;
		}
	}

    /**
     * @param listColumns
     * @param listRecords
     */
    private void obtieneColumnasRenglones(List<ColumnGridEstandarVO> listColumns, List<RecordVO> listRecords) {
        // TODO Auto-generated method stub
        
    }


    /**
     * @param extList
     * @return
     */
    private List<NameLabelControlVO> getLabelNames(List<ExtElement> extList) {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("-> getLabelNames");
        }
        
        List<NameLabelControlVO> listNameLabels = null;
        NameLabelControlVO nameLabelControlVO = null;
        ComboControl combo = null;
        TextFieldControl textfield = null;
        
        if (extList != null && !extList.isEmpty()) {
            listNameLabels = new ArrayList<NameLabelControlVO>();
            
            for (ExtElement element : extList) {
                if (COMBO_TYPE.equals(element.getXtype())) {
                    combo = (ComboControl) element;
                    
                    nameLabelControlVO = new NameLabelControlVO();
                    nameLabelControlVO.setFieldName(combo.getFieldLabel());
                    nameLabelControlVO.setName(StringUtils.remove(combo.getName(), "parameters."));
                } else if( NUMBER_FIELD_TYPE.equals(element.getXtype()) || TEX_FIELD_TYPE.equals(element.getXtype())
                      ||  DATEFIELD_TYPE.equals(element.getXtype()) || "timefield".equals(element.getXtype()) ) {                
                    textfield = (TextFieldControl) element;
                    
                    nameLabelControlVO = new NameLabelControlVO();
                    nameLabelControlVO.setFieldName(textfield.getFieldLabel());
                    nameLabelControlVO.setName(StringUtils.remove(textfield.getName(), "parameters."));
                }
                if (nameLabelControlVO != null && !listNameLabels.contains(nameLabelControlVO)) {
                    listNameLabels.add(nameLabelControlVO);
                }
            }
        }
            
        return listNameLabels;
    }
	

}
