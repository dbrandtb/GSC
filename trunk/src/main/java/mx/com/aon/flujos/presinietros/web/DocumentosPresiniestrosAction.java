package mx.com.aon.flujos.presinietros.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.atributosVariables.model.AtributosVariablesVO;
import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.presinietros.model.DocumentoVO;
import mx.com.aon.flujos.presinietros.service.DctosPresiniestrosManager;
import mx.com.aon.portal.model.AtributosVariablesInstPagoVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.model.InstrumentoPagoVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.InstrumentoPagoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import static mx.com.aon.utils.Constantes.INSERT_MODE;
import static mx.com.aon.utils.Constantes.UPDATE_MODE;

public class DocumentosPresiniestrosAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7620421167902207413L;

	public Map session;

	protected final transient Logger logger = Logger.getLogger(DocumentosPresiniestrosAction.class);

	private boolean success;
	
	private DctosPresiniestrosManager dctosPresiniestrosManagerJdbcTemplate;
	private CatalogService catalogManager;
	
	private List<ClienteCorpoVO> clientesList;
	private List<BaseObjectVO> aseguradorasList;
    private List<BaseObjectVO> productosList;
    private List<DocumentoVO> documentosList;
    private List <InstrumentoPagoAtributosVO> instrumentosClienteList;
    private List <AtributosVariablesInstPagoVO> atributosInstrumentoPago;
	
    /*
     * PROPIEDADES PARA LOS INTRUMENTOS DE PAGO POR CLIENTE
     */
    private String cdElemento;
    private String cdRamo;
    private String cdUnieco;
    private String cdForPag;
    private String cdInsCte;
    private String cdValoDoc;
    
    /*
     * PROPIEDADES PARA LOS LOS ATRIBUTOS DE LOS INTRUMENTOS DE PAGO POR CLIENTE
     */
    private String cdAtribu;
    private String dsAtribu;
    private String descripcion;
    private String codigoRadioAtributosVariables;
    private String minimo;
    private String maximo;
    private String obligatorio;
    private String modificaEmision;
    private String despliegaCotizador;
    private String retarificacion;
    private String datoComplementario;
    private String obligatorioComplementario;
    private String modificableComplementario;
    private String apareceEndoso;
    private String modificaEndoso;
    private String obligatorioEndoso;
    private String padre;
    private String agrupador;
    private String orden;
    private String condicion;
    private String valorDefecto;
    private String codigoTabla;
    private String codigoExpresion;
    private String codigoExpresionSession;
    
    private String tipoTrans;
    private String mensajeRespuesta;
    
    /*
     * VARIABLES PARA PAGINACION
     */
	private int start = 0;
	private int limit = 20;
	private int totalCount;
    
	@SuppressWarnings("unchecked")
    public String obtenComboCliente() throws Exception{
		
		if(logger.isDebugEnabled()) logger.debug("******obtenComboCliente******");
    	try{
    		clientesList = catalogManager.getItemList("OBTIENE_CLIENTES_CORPO_CATALOGS");
        	
    		if(clientesList!=null && StringUtils.isBlank(tipoTrans)){
    			ClienteCorpoVO all = new ClienteCorpoVO();
            	all.setDsCliente("----- Todos -----");
            	all.setCdCliente("");
    			clientesList.add(0, all );
    		}
    		
    	}catch(Exception e){
        	logger.error("Exception obtenComboCliente: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;
    	}
        success = true;
    	return SUCCESS;
    }
	
	 @SuppressWarnings("unchecked")
	    public String obtenComboAseguradora() throws Exception{
	    	
	        if(logger.isDebugEnabled()){
	        	logger.debug("******obtenComboAseguradora******");
	        	logger.debug("id cliente:" + cdElemento);
	        }
	        
	        try{
	        	HashMap params = new HashMap();
	    		params.put("cdElemento", cdElemento);
		        aseguradorasList = catalogManager.getItemList("OBTIENE_ASEGURADORAS_CAT2", params);
		        
		        if(aseguradorasList != null && StringUtils.isBlank(tipoTrans)){
		        	BaseObjectVO all = new BaseObjectVO();
		        	all.setLabel("----- Todas -----");
		        	all.setValue("");
		        	aseguradorasList.add(0, all);
		        }
	        }catch(Exception e){
	        	logger.error("Exception obtenComboAseguradora: " + e.getMessage(),e);
	        	success = false;
	        	return SUCCESS;
	        }
	        
	        success = true;
	    	return SUCCESS;
	    }
	    
	    @SuppressWarnings("unchecked")
	    public String obtenComboProducto() throws Exception{
	    	
	        if(logger.isDebugEnabled()){
	        	logger.debug("******obtenComboProducto******");
	            logger.debug("id cliente: " + cdElemento);
	        	logger.debug("id aseguradora: " + cdUnieco);
	        }
	    	
	        try{
	        	Map<String, Object> params = new HashMap<String, Object>();
		        params.put("cdElemento", cdElemento);
		        params.put("cdUnieco", cdUnieco);
		        productosList = catalogManager.getItemList("OBTIENE_PRODUCTOS_CLIENTE_ASEGURADORA",params);
	        }catch(Exception e){
	        	logger.error("Exception obtenComboProducto: " + e.getMessage(),e);
	        	success = false;
	        	return SUCCESS;
	        }

	        success = true;
	    	return SUCCESS;
	    }
	
	    @SuppressWarnings("unchecked")
	    public String obtenComboDocumentos() throws Exception{
	    	
	        if(logger.isDebugEnabled()){
	        	logger.debug("******obtenComboDocumentos******");
	        	logger.debug("id cliente: " + cdElemento);
	        }
	        
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("cdElemento", cdElemento);
	        try{
	        	documentosList = catalogManager.getWrapperItemList("OBTIENE_TIPOS_DOCUMENTOS",params);
		        if(logger.isDebugEnabled()) logger.debug("COMBO DOCUMENTOS: "+ documentosList);
	        }catch(Exception e){
	        	logger.error("Exception obtenComboDocumentos: " + e.getMessage(),e);
	        	success = false;
	        	return SUCCESS;
	        }
	        
	        success = true;
	    	return SUCCESS;
	    }
		
	    
	    public String obtenGridInstrumetosPagoCliente()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******obtenGridInstrumetosPagoCliente******");
            logger.debug("cdElemento: " + cdElemento);
            logger.debug("cdForPag: " + cdForPag);
            logger.debug("cdUnieco: " + cdUnieco);
            logger.debug("cdRamo: " + cdRamo);
            
        }
        
        
        try{
        	PagedList pagedList = dctosPresiniestrosManagerJdbcTemplate.getInstrumetosPagoCliente(cdElemento, cdForPag, cdUnieco, cdRamo, start , limit);
        	instrumentosClienteList = pagedList.getItemsRangeList();
        	setTotalCount(pagedList.getTotalItems());
	        
        	if(logger.isDebugEnabled()) logger.debug("GRID INSTRUMENTOS PAGO LIST: "+ instrumentosClienteList);
        	
        }catch(Exception e){
        	instrumentosClienteList = new ArrayList<InstrumentoPagoAtributosVO>();
        	logger.error("Exception obtenGridInstrumetosPagoCliente: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;	
        }
        
        success = true;
		return SUCCESS;
	}

	public String agregarIntrumentoPagoCliente()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******agregarIntrumentoPagoCliente******");
            logger.debug("cdElemento: " + cdElemento);
            logger.debug("cdForPag: " + cdForPag);
            logger.debug("cdUnieco: " + cdUnieco);
            logger.debug("cdRamo: " + cdRamo);
            
        }
        
        WrapperResultados resultado;
        try{
        	 resultado = dctosPresiniestrosManagerJdbcTemplate.agregarInstrumetoPagoCliente(cdElemento, cdForPag, cdUnieco, cdRamo);
        	 mensajeRespuesta = resultado.getMsgText();
        	 
        	 if(StringUtils.isNotBlank((String) resultado.getItemMap().get("CDDOCCTE"))){
        		 llenarDescripcionYmonto((String) resultado.getItemMap().get("CDDOCCTE"));
        	 }
        }catch(Exception e){
        	mensajeRespuesta = "Error al guardar. Consulte a su soporte";
        	logger.error("Exception agregarInstrumetoPagoCliente: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;
        }
        
        if(StringUtils.isBlank(mensajeRespuesta))mensajeRespuesta = "Error al guardar. Consulte a su soporte";
        success = true;
		return SUCCESS;
	}
	
	
	
	public String borrarIntrumentoPagoCliente()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******borrarIntrumentoPagoCliente******");
            logger.debug("cdInsCte: " + cdInsCte);
        }
        
        
        try{
        	mensajeRespuesta = dctosPresiniestrosManagerJdbcTemplate.borrarInstrumetoPagoCliente(cdInsCte);
        }catch(Exception e){
        	mensajeRespuesta = "Error al borrar. Consulte a su soporte";
        	logger.error("Exception borrarInstrumetoPagoCliente: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;
        }
        
        if(StringUtils.isBlank(mensajeRespuesta))mensajeRespuesta = "Error al borrar. Consulte a su soporte";
        success = true;
		return SUCCESS;
	}
	
		
	
	
	/**
	 *	METODOS PARA LA CONFIGURACION DE LOS ATRIBUTOS VARIABLES POR INSTRUMENTO DE PAGO:  
	 * 
	 */
	
	
	public String obtenAtributosGridInstrumetosPago()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******obtenAtributosGridInstrumetosPago******");
            logger.debug("cdInsCte: " + cdInsCte);
            logger.debug("dsAtribu: " + dsAtribu);
        }
        
        
        try{
        	PagedList pagedList = dctosPresiniestrosManagerJdbcTemplate.getAtributosInstrumetoPago(cdInsCte, dsAtribu, start , limit);
        	setTotalCount(pagedList.getTotalItems());
        	
        	if(getTotalCount() == 0){
        		atributosInstrumentoPago = new ArrayList<AtributosVariablesInstPagoVO>();
        	}else{
        		atributosInstrumentoPago = pagedList.getItemsRangeList();
        	}
        	
        	boolean llenarDefault = false;
        	if(pagedList.getItemMap() != null && !pagedList.getItemMap().isEmpty() && pagedList.getItemMap().containsKey("EXISTEN_REGISTROS")){
        		if(!"S".equals(pagedList.getItemMap().get("EXISTEN_REGISTROS")))llenarDefault = true;
        	}
        	
        	if(llenarDefault){
        		logger.debug("LLenando atributos por default (Descripcion y Monto)");
        		llenarDescripcionYmonto(cdInsCte);
        		obtenAtributosGridInstrumetosPago();
        	}
        	
        	if(!llenarDefault && logger.isDebugEnabled()) logger.debug("GRID ATRIBUTOS INSTRUMENTO PAGO LIST: "+ atributosInstrumentoPago);
        	
        }catch(Exception e){
        	atributosInstrumentoPago =  new ArrayList<AtributosVariablesInstPagoVO>();
        	logger.error("Exception obtenAtributosGridInstrumetosPago: " + e.getMessage(),e);
        	mensajeRespuesta = e.getMessage();
        	success = false;
        	return SUCCESS;	
        }
        
        success = true;
		return SUCCESS;
	}
	
	public boolean llenarDescripcionYmonto(String cdUnica){
		cdInsCte = cdUnica;
		try{
				cdAtribu = "1";
				codigoRadioAtributosVariables = "A";
				descripcion = "Descripci&oacute;n";
				maximo = "40";
				minimo = "1";
				modificaEmision = "S";
				obligatorio = "S";
			
				if(!SUCCESS.equals(guardaAtributoIntrumentoPago()))return false;
				
				cdAtribu = "2";
				codigoRadioAtributosVariables = "N";
				descripcion = "Monto";
				maximo = "10";
				minimo = "1";
				modificaEmision = "S";
				obligatorio = "S";
				
				if(!SUCCESS.equals(guardaAtributoIntrumentoPago()))return false;
				
		}catch(Exception e){
			logger.error("Error al agregar los atributos por default (Descripcion y Monto)"+e.getMessage(),e);
			return false;
		}
		return true;
	}
	public String guardaAtributoIntrumentoPago()throws Exception{
		
		success = true;
		Map<String, Object> params = new HashMap<String, Object>();
        if(logger.isDebugEnabled()){
        	logger.debug("******guardaAtributoIntrumentoPago******");
        }
        
        
		if (StringUtils.isNotBlank(codigoExpresionSession) && codigoExpresionSession.equals("undefined")) codigoExpresionSession = "EXPRESION";

		AtributosVariablesVO atributos = new AtributosVariablesVO();

		if (StringUtils.isBlank(obligatorio)) obligatorio = "N";
		
		if (StringUtils.isBlank(modificaEmision)) modificaEmision = "N";
		if (StringUtils.isBlank(modificaEndoso)) modificaEndoso = "N";
		if (StringUtils.isBlank(retarificacion)) retarificacion = "N";
		if (StringUtils.isBlank(despliegaCotizador)) despliegaCotizador = "N";
		if (StringUtils.isBlank(apareceEndoso)) apareceEndoso = "N";
		if (StringUtils.isBlank(datoComplementario)) datoComplementario = "N";
		if (StringUtils.isBlank(obligatorioComplementario)) obligatorioComplementario = "N";
		if (StringUtils.isBlank(obligatorioEndoso)) obligatorioEndoso = "N";
		if (StringUtils.isBlank(modificableComplementario)) modificableComplementario = "N";
		if (StringUtils.isBlank(codigoRadioAtributosVariables)) codigoRadioAtributosVariables = "A";
		
		//atributos.setInserta("S"); //Parametro para actualizar, en este caso no se ha puesto ese parametro, verificar si es necesario
		
		params.put("pv_cdunica_i", cdInsCte);
		params.put("pv_cdatribu_i", cdAtribu);
		params.put("pv_dsatribu_i", descripcion);
		params.put("pv_swformat_i", codigoRadioAtributosVariables);
		params.put("pv_swemisi_i", "S");
		params.put("pv_swemiobl_i", obligatorio);
		params.put("pv_swemiupd_i", modificaEmision);
		params.put("pv_swendupd_i", modificaEndoso);
		params.put("retarificacion", retarificacion);//parametro sin usarse
		params.put("despliegaCotizador", despliegaCotizador);//parametro sin usarse
		params.put("pv_nmlmax_i", maximo);
		params.put("pv_nmlmin_i", minimo);
		params.put("pv_ottabval_i", codigoTabla);
		params.put("pv_swendoso_i", apareceEndoso);
		params.put("pv_swdatcom_i", datoComplementario);
		params.put("pv_swcomobl_i", obligatorioComplementario);
		params.put("pv_swendobl_i", obligatorioEndoso);
		params.put("pv_swcomupd_i", modificableComplementario);
		params.put("pv_nmagrupa_i", agrupador);
		params.put("pv_nmorden_i", orden);
		params.put("pv_cdcondicvis_i", condicion);
		params.put("pv_cdatribu_padre_i", padre);

		/*int codigoExpresionInt = 0;
		if (session.containsKey(codigoExpresionSession)) {
			// ExpresionVO e = (ExpresionVO)session.get(codigoExpresionSession);
			// codigoExpresionInt = insertarExpresion(success, e);
			codigoExpresionInt = (Integer) session.get(codigoExpresionSession);
		}*/
		params.put("pv_cdexpress_i", codigoExpresion);
		
		//Parametros para agregar las leyendas
		params.put("pv_swlegend_i", "");
		params.put("pv_dslegend_i", "");
		
		
		try{
        	mensajeRespuesta = dctosPresiniestrosManagerJdbcTemplate.guardarAtributoInstrumetoPagoCliente(params);
        }catch(Exception e){
        	mensajeRespuesta = "Error al guardar. Consulte a su soporte";
        	logger.error("Exception guardarInstrumetoPagoCliente: " + e.getMessage(),e);
        	success = false;
        }
		
		if (success) {
			if (session.containsKey(codigoExpresionSession)) session.remove(codigoExpresionSession);
		}
        
        if(StringUtils.isBlank(mensajeRespuesta))mensajeRespuesta = "Error al guardar. Consulte a su soporte";
        
		return SUCCESS;
	}
	
	
	
	public String borrarAtributoIntrumentoPago()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******borrarAtributoIntrumentoPago******");
            logger.debug("cdInsCte: " + cdInsCte);
            logger.debug("cdAtribu: " + cdAtribu);
        }
        
        
        try{
        	mensajeRespuesta = dctosPresiniestrosManagerJdbcTemplate.borrarAtributoInstrumetoPago(cdInsCte, cdAtribu);
        }catch(Exception e){
        	mensajeRespuesta = e.getMessage();
        	logger.error("Exception borrarAtributoIntrumentoPago: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;
        }
        
        if(StringUtils.isBlank(mensajeRespuesta))mensajeRespuesta = "Error al borrar. Consulte a su soporte";
        success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String obtenAtributosDocumento()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******obtenAtributosDocumento******");
        }
        
        ArrayList <AtributosVariablesInstPagoVO> atributosInstrumentoPago =  null;
        try{
        	
        	PagedList pagedList = dctosPresiniestrosManagerJdbcTemplate.getAtributosInstrumetoPago(cdInsCte, null, 0 , -1);
        	
        	
        	if(pagedList.getTotalItems() == 0){
        		success = false;
            	return SUCCESS;	
        	}else{
        		atributosInstrumentoPago = (ArrayList<AtributosVariablesInstPagoVO>) pagedList.getItemsRangeList();
        		mensajeRespuesta = obtieneFieldsetElementosExt(atributosInstrumentoPago);
        	}
        	
        	if(logger.isDebugEnabled()) logger.debug("ATRIBUTOS DINAMICOS PARA EL DOCUMENTO : "+cdInsCte+" SON:"+ atributosInstrumentoPago);
        	
        }catch(Exception e){
        	logger.error("Exception obtenAtributosDocumento: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;	
        }
            	
        logger.debug("componentes para Instrumentos de Pago creados: " + mensajeRespuesta);
            
        success = true;
        return SUCCESS;
        	
	}
	
	
	@SuppressWarnings("unchecked")
	public String obtieneFieldsetElementosExt(ArrayList <AtributosVariablesInstPagoVO> atributosInstrumentoPago){
		
		StringBuilder fieldset = new StringBuilder();
		try{
		fieldset.append("{layout: 'form', border: false , labelAlign:'right");
		//fieldset.append("', title: '");"); fieldset.append(descripcionFormaDePago);
		fieldset.append("' , id: 'fieldsetId', autoHeight: true, items: [");
		
		if(atributosInstrumentoPago !=null){
			Map<String, List<String>> listasValor =  new HashMap<String, List<String>>(); //Ottabval, cdAtribu
			Map<String, AtributosVariablesInstPagoVO> combos =  new HashMap<String, AtributosVariablesInstPagoVO>(); //cdAtribu, AtributosVariablesInstPagoVO
			
			for(AtributosVariablesInstPagoVO elemento : atributosInstrumentoPago){
				if(StringUtils.isNotBlank(elemento.getCdTabla())){
					ArrayList<String> tmp;
					/*para añadir todos los ids de los combos que usan una tabla de lista valor*/
					if(listasValor.containsKey(elemento.getCdTabla())){
						tmp = (ArrayList<String>) listasValor.get(elemento.getCdTabla());
						tmp.add(elemento.getCdAtribu());
						listasValor.put(elemento.getCdTabla(), tmp);
						
					}else{
						tmp = new ArrayList<String>();
						tmp.add(elemento.getCdAtribu());
						listasValor.put(elemento.getCdTabla(),tmp);
					}
					
					/*se añade a la lista que contiene todos los combos identificados por su numero de atributo*/
					combos.put(elemento.getCdAtribu(), elemento);
				}
			}
			
			/*if(!listasValor.isEmpty()){
				asignarPadres(listasValor, combos);
				if(logger.isDebugEnabled())logger.debug("LOS COMBOS LUEGO DE ASIGNAR PADRES SON: "+ combos);
				
			}*/
			
			
			/*PARA SABER SI ES ES MODO EDICION O AGREGADO (Y OBTENER SUS VALORES EN CASO DE MODO EDICION)*/
			int inc = 0;
			
			if(StringUtils.isNotBlank(tipoTrans) && tipoTrans.equals(UPDATE_MODE)){
				DocumentoVO documento = null;
				HashMap<String, DocumentoVO> docs = null;
				if(session.containsKey("DOCUMENTOS_PRE")){
					docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE");
					if(docs.containsKey(cdValoDoc)){
						documento = docs.get(cdValoDoc);
					}else if(session.containsKey("DOCUMENTOS_PRE_INSERT")){
						docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
						if(docs.containsKey(cdValoDoc)){
							documento = docs.get(cdValoDoc);
						}else if(session.containsKey("DOCUMENTOS_PRE_UPDATE")){
							docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_UPDATE");
							if(docs.containsKey(cdValoDoc)){
								documento = docs.get(cdValoDoc);
							}else{
								logger.error("Error en el metodo obtieneFieldsetElementosExt modo EDICION,  NO HAY VALORES EN LA SESION para DOCUMENTOS_PRE!!!");
								return null;
								
							}
						}
					}
				}
				
				if(documento == null){
					logger.error("Error en el metodo obtieneFieldsetElementosExt modo EDICION documento NULL,  NO HAY VALORES EN LA SESION para DOCUMENTOS_PRE!!!");
					return null;
				}
				
				for(AtributosVariablesInstPagoVO elemento : atributosInstrumentoPago){
					String cdFormat = elemento.getSwformat();
					String valorAtr = obtieneValorAtributo(elemento.getCdAtribu(),documento);
					
					if(StringUtils.isNotBlank(cdFormat) && !StringUtils.isNotBlank(elemento.getCdTabla())){
						if(cdFormat.equals("A")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaTextfield(elemento, valorAtr));
						}else if(cdFormat.equals("N") || cdFormat.equals("P")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaNumberfield(elemento, valorAtr));
						}else if(cdFormat.equals("F")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaDatefield(elemento,valorAtr));
						}
						inc++;
					}else if(StringUtils.isNotBlank(elemento.getCdTabla())){
						if(inc > 0)fieldset.append(",");
						fieldset.append(creaComboListaValor(elemento ,valorAtr));
						inc++;
					}
				}
				
			}else{
				
				for(AtributosVariablesInstPagoVO elemento : atributosInstrumentoPago){
					String cdFormat = elemento.getSwformat();
					if(StringUtils.isNotBlank(cdFormat) && !StringUtils.isNotBlank(elemento.getCdTabla())){
						if(cdFormat.equals("A")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaTextfield(elemento));
						}else if(cdFormat.equals("N") || cdFormat.equals("P")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaNumberfield(elemento));
						}else if(cdFormat.equals("F")){
							if(inc > 0)fieldset.append(",");
							fieldset.append(creaDatefield(elemento));
						}
						inc++;
					}else if(StringUtils.isNotBlank(elemento.getCdTabla())){
						if(inc > 0)fieldset.append(",");
						fieldset.append(creaComboListaValor(elemento));
						inc++;
					}
				}
				
			}
			
			/*FIN DE LA OTENCION DE LOS ELEMENTOS Y EN SU CASO SUS VALORES*/
		}
		
		fieldset.append("]}");
		}catch(Exception e){
			logger.error("ERROR AL OBTENER LOS COMPONENTES EXT : "+e.getMessage(),e);
			return null;
		}
		return fieldset.toString();
	}
	
	public String creaTextfield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'textfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", maxLength: "); text.append(atributo.getNmMax()); 
		text.append(", minLength: "); text.append(atributo.getNmMin()); 
		text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" caracteres'");
		text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" caracteres'");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaNumberfield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'numberfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		if(atributo.getSwformat().equals("N")){
			text.append(", maxLength: "); text.append(atributo.getNmMax()); 
			text.append(", minLength: "); text.append(atributo.getNmMin()); 
			text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" d&iacute;gitos'");
			text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" d&iacute;gitos'");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaDatefield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'datefield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}

	public String creaComboListaValor(AtributosVariablesInstPagoVO atributo){
		
		StringBuilder text =  new StringBuilder();
		
		text.append("{xtype: 'combo', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', hiddenName: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', id: 'combo"); text.append(atributo.getCdAtribu()); text.append("id");
		text.append("', allowBlank: "); 
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", store: ");
		text.append(" new Ext.data.Store({ proxy: new Ext.data.HttpProxy({ url: 'flujocotizacion/obtenerListaComboOttabval.action' })");
		text.append(", id: 'store"); text.append(atributo.getCdAtribu()); text.append("id', reader: new Ext.data.JsonReader({ root: 'itemList', id: 'value' }, [{ mapping: 'value', name: 'value', type: 'string'},{mapping: 'label', name: 'label', type: 'string'}] ), remoteSort: true , autoLoad: false , baseParams : {ottabval: '"); text.append(atributo.getCdTabla());
		text.append("', valAnterior : '0', valAntAnt : '0'}, sortInfo : [{field: 'value'},{ direction :'DESC'}] ");
		text.append(", listeners: { load: function(){");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
			}
			
		}
		
		//FIN DEL LISTENER LOAD
		text.append("} ");
		//PARA EL LISTENER BEFORELOAD
		text.append(", beforeload: function(thisStore){");
		
			if(atributo.getParent() != null && !atributo.getParent().isEmpty()){
				for(int i=0 ; i<atributo.getParent().size() ; i++){
					String cdtablaPadre = atributo.getParent().get(i);
					if(i==0){
						text.append("var valAnt = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
						text.append("thisStore.baseParams['valAnterior'] = Ext.isEmpty(valAnt)? '0' : valAnt;");
					}
					else {
						text.append("thisStore.baseParams['"); text.append(cdtablaPadre); text.append("'] = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
					}
				}
				
			}
		
		//FIN DEL LISTENER BEFORELOAD
		text.append("} ");
		text.append("}");//cierre de los listeners del store del combo

		text.append("})"); //Cierre del store del combo
		
		text.append(", displayField: 'label'");
		text.append(", valueField: 'value'");  
		text.append(", editable: true");
		text.append(", emptyText: 'Seleccione...'");
		text.append(", forceSelection: true");
		text.append(", listWidth: 200");
		/*
		 *PARA EL LISTENER DE RENDER Y EL SELECT (render para que ya exista el componente) 
		 */
		text.append(", listeners: { render: function(){");
		
		if(atributo.getParent() == null || atributo.getParent().isEmpty()){
			text.append(" this.store.load(); ");
		}
			
		text.append("}, select: function(){  ");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
			}
			
		}
		
		//FIN DEL LISTENER SELECT PARA COMBOS ANIDADOS
		text.append("}");
		text.append("}");//fin de los listeners del combo
		text.append(", mode: 'local'");
		text.append(", selectOnFocus: true");
		text.append(", triggerAction: 'all'");
		text.append(", typeAhead: true");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	
	/*PARA OBTENCION DE ATRIBUTOS PERO EN MODO EDICION  DEL GRID DE DOCUMENTOS PARA LOS PRESINIESTROS*/
	
	public String creaTextfield(AtributosVariablesInstPagoVO atributo, String valorAtr){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'textfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', value: '"); text.append(valorAtr);
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", maxLength: "); text.append(atributo.getNmMax()); 
		text.append(", minLength: "); text.append(atributo.getNmMin()); 
		text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" caracteres'");
		text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" caracteres'");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaNumberfield(AtributosVariablesInstPagoVO atributo, String valorAtr){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'numberfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', value: '"); text.append(valorAtr);
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		if(atributo.getSwformat().equals("N")){
			text.append(", maxLength: "); text.append(atributo.getNmMax()); 
			text.append(", minLength: "); text.append(atributo.getNmMin()); 
			text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" d&iacute;gitos'");
			text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" d&iacute;gitos'");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaDatefield(AtributosVariablesInstPagoVO atributo, String valorAtr){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'datefield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', value: '"); text.append(valorAtr);
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}

	public String creaComboListaValor(AtributosVariablesInstPagoVO atributo, String valorAtr){
		
		StringBuilder text =  new StringBuilder();
		
		text.append("{xtype: 'combo', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', hiddenName: 'documento.atr"); text.append(atributo.getCdAtribu());
		text.append("', id: 'combo"); text.append(atributo.getCdAtribu()); text.append("id");
		text.append("', allowBlank: "); 
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", store: ");
		text.append(" new Ext.data.Store({ proxy: new Ext.data.HttpProxy({ url: 'flujocotizacion/obtenerListaComboOttabval.action' })");
		text.append(", id: 'store"); text.append(atributo.getCdAtribu()); text.append("id', reader: new Ext.data.JsonReader({ root: 'itemList', id: 'value' }, [{ mapping: 'value', name: 'value', type: 'string'},{mapping: 'label', name: 'label', type: 'string'}] ), remoteSort: true , autoLoad: false , baseParams : {ottabval: '"); text.append(atributo.getCdTabla());
		text.append("', valAnterior : '0', valAntAnt : '0'}, sortInfo : [{field: 'value'},{ direction :'DESC'}] ");
		text.append(", listeners: { load: function(){");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
				text.append(" Ext.getCmp('combo"); text.append(atributo.getCdAtribu()); text.append("id').setValue('"); 
				text.append(valorAtr); text.append("');");
				
			}
			
		}
		
		//FIN DEL LISTENER LOAD
		text.append("} ");
		//PARA EL LISTENER BEFORELOAD
		text.append(", beforeload: function(thisStore){");
		
			if(atributo.getParent() != null && !atributo.getParent().isEmpty()){
				for(int i=0 ; i<atributo.getParent().size() ; i++){
					String cdtablaPadre = atributo.getParent().get(i);
					if(i==0){
						text.append("var valAnt = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
						text.append("thisStore.baseParams['valAnterior'] = Ext.isEmpty(valAnt)? '0' : valAnt;");
					}
					else {
						text.append("thisStore.baseParams['"); text.append(cdtablaPadre); text.append("'] = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
					}
				}
				
			}
		
		//FIN DEL LISTENER BEFORELOAD
		text.append("} ");
		text.append("}");//cierre de los listeners del store del combo

		text.append("})"); //Cierre del store del combo
		
		text.append(", displayField: 'label'");
		text.append(", valueField: 'value'");  
		text.append(", editable: true");
		text.append(", emptyText: 'Seleccione...'");
		text.append(", forceSelection: true");
		text.append(", listWidth: 200");
		/*
		 *PARA EL LISTENER DE RENDER Y EL SELECT (render para que ya exista el componente) 
		 */
		text.append(", listeners: { render: function(){");
		
		if(atributo.getParent() == null || atributo.getParent().isEmpty()){
			text.append(" this.store.load(); ");
		}
			
		text.append("}, select: function(){  ");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
			}
			
		}
		
		//FIN DEL LISTENER SELECT PARA COMBOS ANIDADOS
		text.append("}");
		text.append("}");//fin de los listeners del combo
		text.append(", mode: 'local'");
		text.append(", selectOnFocus: true");
		text.append(", triggerAction: 'all'");
		text.append(", typeAhead: true");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	String obtieneValorAtributo(String cdAtributo,DocumentoVO doc){
		if(StringUtils.isBlank(cdAtributo))return "";
		
		if(cdAtributo.equals("1"))return StringUtils.isBlank(doc.getAtr1())? "" : doc.getAtr1();
		if(cdAtributo.equals("2"))return StringUtils.isBlank(doc.getAtr2())? "" : doc.getAtr2();
		if(cdAtributo.equals("3"))return StringUtils.isBlank(doc.getAtr3())? "" : doc.getAtr3();
		if(cdAtributo.equals("4"))return StringUtils.isBlank(doc.getAtr4())? "" : doc.getAtr4();
		if(cdAtributo.equals("5"))return StringUtils.isBlank(doc.getAtr5())? "" : doc.getAtr5();
		if(cdAtributo.equals("6"))return StringUtils.isBlank(doc.getAtr6())? "" : doc.getAtr6();
		if(cdAtributo.equals("7"))return StringUtils.isBlank(doc.getAtr7())? "" : doc.getAtr7();
		if(cdAtributo.equals("8"))return StringUtils.isBlank(doc.getAtr8())? "" : doc.getAtr8();
		if(cdAtributo.equals("9"))return StringUtils.isBlank(doc.getAtr9())? "" : doc.getAtr9();
		if(cdAtributo.equals("10"))return StringUtils.isBlank(doc.getAtr10())? "" : doc.getAtr10();
		if(cdAtributo.equals("11"))return StringUtils.isBlank(doc.getAtr11())? "" : doc.getAtr11();
		if(cdAtributo.equals("12"))return StringUtils.isBlank(doc.getAtr12())? "" : doc.getAtr12();
		if(cdAtributo.equals("13"))return StringUtils.isBlank(doc.getAtr13())? "" : doc.getAtr13();
		if(cdAtributo.equals("14"))return StringUtils.isBlank(doc.getAtr14())? "" : doc.getAtr14();
		if(cdAtributo.equals("15"))return StringUtils.isBlank(doc.getAtr15())? "" : doc.getAtr15();
		if(cdAtributo.equals("16"))return StringUtils.isBlank(doc.getAtr16())? "" : doc.getAtr16();
		if(cdAtributo.equals("17"))return StringUtils.isBlank(doc.getAtr17())? "" : doc.getAtr17();
		if(cdAtributo.equals("18"))return StringUtils.isBlank(doc.getAtr18())? "" : doc.getAtr18();
		if(cdAtributo.equals("19"))return StringUtils.isBlank(doc.getAtr19())? "" : doc.getAtr19();
		if(cdAtributo.equals("20"))return StringUtils.isBlank(doc.getAtr20())? "" : doc.getAtr20();
		if(cdAtributo.equals("21"))return StringUtils.isBlank(doc.getAtr21())? "" : doc.getAtr21();
		if(cdAtributo.equals("22"))return StringUtils.isBlank(doc.getAtr22())? "" : doc.getAtr22();
		if(cdAtributo.equals("23"))return StringUtils.isBlank(doc.getAtr23())? "" : doc.getAtr23();
		if(cdAtributo.equals("24"))return StringUtils.isBlank(doc.getAtr24())? "" : doc.getAtr24();
		if(cdAtributo.equals("25"))return StringUtils.isBlank(doc.getAtr25())? "" : doc.getAtr25();
		if(cdAtributo.equals("26"))return StringUtils.isBlank(doc.getAtr26())? "" : doc.getAtr26();
		if(cdAtributo.equals("27"))return StringUtils.isBlank(doc.getAtr27())? "" : doc.getAtr27();
		if(cdAtributo.equals("28"))return StringUtils.isBlank(doc.getAtr28())? "" : doc.getAtr28();
		if(cdAtributo.equals("29"))return StringUtils.isBlank(doc.getAtr29())? "" : doc.getAtr29();
		if(cdAtributo.equals("30"))return StringUtils.isBlank(doc.getAtr30())? "" : doc.getAtr30();
		if(cdAtributo.equals("31"))return StringUtils.isBlank(doc.getAtr31())? "" : doc.getAtr31();
		if(cdAtributo.equals("32"))return StringUtils.isBlank(doc.getAtr32())? "" : doc.getAtr32();
		if(cdAtributo.equals("33"))return StringUtils.isBlank(doc.getAtr33())? "" : doc.getAtr33();
		if(cdAtributo.equals("34"))return StringUtils.isBlank(doc.getAtr34())? "" : doc.getAtr34();
		if(cdAtributo.equals("35"))return StringUtils.isBlank(doc.getAtr35())? "" : doc.getAtr35();
		if(cdAtributo.equals("36"))return StringUtils.isBlank(doc.getAtr36())? "" : doc.getAtr36();
		if(cdAtributo.equals("37"))return StringUtils.isBlank(doc.getAtr37())? "" : doc.getAtr37();
		if(cdAtributo.equals("38"))return StringUtils.isBlank(doc.getAtr38())? "" : doc.getAtr38();
		if(cdAtributo.equals("39"))return StringUtils.isBlank(doc.getAtr39())? "" : doc.getAtr39();
		if(cdAtributo.equals("40"))return StringUtils.isBlank(doc.getAtr40())? "" : doc.getAtr40();
		if(cdAtributo.equals("41"))return StringUtils.isBlank(doc.getAtr41())? "" : doc.getAtr41();
		if(cdAtributo.equals("42"))return StringUtils.isBlank(doc.getAtr42())? "" : doc.getAtr42();
		if(cdAtributo.equals("43"))return StringUtils.isBlank(doc.getAtr43())? "" : doc.getAtr43();
		if(cdAtributo.equals("44"))return StringUtils.isBlank(doc.getAtr44())? "" : doc.getAtr44();
		if(cdAtributo.equals("45"))return StringUtils.isBlank(doc.getAtr45())? "" : doc.getAtr45();
		if(cdAtributo.equals("46"))return StringUtils.isBlank(doc.getAtr46())? "" : doc.getAtr46();
		if(cdAtributo.equals("47"))return StringUtils.isBlank(doc.getAtr47())? "" : doc.getAtr47();
		if(cdAtributo.equals("48"))return StringUtils.isBlank(doc.getAtr48())? "" : doc.getAtr48();
		if(cdAtributo.equals("49"))return StringUtils.isBlank(doc.getAtr49())? "" : doc.getAtr49();
		if(cdAtributo.equals("50"))return StringUtils.isBlank(doc.getAtr50())? "" : doc.getAtr50();
		
		return "";
	}
	
	/* FIN DE METODOS PARA LA OBTENCION DE ATRIBUTOS PERO EN MODO EDICION  DEL GRID DE DOCUMENTOS PARA LOS PRESINIESTROS*/
	
	
	/**
	 * TODO: AL PARECER EL SIGUIENTE METODO obtenDctosPresiniestro() NO SE ESTA UTILIZANDO
	 * */
	
	@SuppressWarnings("unchecked")
    public String obtenDctosPresiniestro() throws Exception{
		
		if(logger.isDebugEnabled()) logger.debug("******obtenDctosPresiniestro******");
    	try{
    		clientesList = catalogManager.getItemList("OBTIENE_CLIENTES_CORPO_CATALOGS");
        	
    		if(clientesList!=null && StringUtils.isBlank(tipoTrans)){
    			ClienteCorpoVO all = new ClienteCorpoVO();
            	all.setDsCliente("----- Todos -----");
            	all.setCdCliente("");
    			clientesList.add(0, all );
    		}
    		
    	}catch(Exception e){
        	logger.error("Exception obtenComboCliente: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;
    	}
        success = true;
    	return SUCCESS;
    }
	
	
	
	/**
	 *	GETTERS & SETTERS  
	 * 
	 */

	public List<ClienteCorpoVO> getClientesList() {
		return clientesList;
	}


	public void setClientesList(List<ClienteCorpoVO> clientesList) {
		this.clientesList = clientesList;
	}


	public List<BaseObjectVO> getAseguradorasList() {
		return aseguradorasList;
	}


	public void setAseguradorasList(List<BaseObjectVO> aseguradorasList) {
		this.aseguradorasList = aseguradorasList;
	}


	public List<BaseObjectVO> getProductosList() {
		return productosList;
	}


	public void setProductosList(List<BaseObjectVO> productosList) {
		this.productosList = productosList;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}

	public List<InstrumentoPagoAtributosVO> getInstrumentosClienteList() {
		return instrumentosClienteList;
	}

	public void setInstrumentosClienteList(
			List<InstrumentoPagoAtributosVO> instrumentosClienteList) {
		this.instrumentosClienteList = instrumentosClienteList;
	}

	public String getCdForPag() {
		return cdForPag;
	}

	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}

	public String getCdInsCte() {
		return cdInsCte;
	}

	public void setCdInsCte(String cdInsCte) {
		this.cdInsCte = cdInsCte;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getTipoTrans() {
		return tipoTrans;
	}

	public void setTipoTrans(String tipoTrans) {
		this.tipoTrans = tipoTrans;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public List<AtributosVariablesInstPagoVO> getAtributosInstrumentoPago() {
		return atributosInstrumentoPago;
	}

	public void setAtributosInstrumentoPago(
			List<AtributosVariablesInstPagoVO> atributosInstrumentoPago) {
		this.atributosInstrumentoPago = atributosInstrumentoPago;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoRadioAtributosVariables() {
		return codigoRadioAtributosVariables;
	}

	public void setCodigoRadioAtributosVariables(
			String codigoRadioAtributosVariables) {
		this.codigoRadioAtributosVariables = codigoRadioAtributosVariables;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getModificaEmision() {
		return modificaEmision;
	}

	public void setModificaEmision(String modificaEmision) {
		this.modificaEmision = modificaEmision;
	}

	public String getDespliegaCotizador() {
		return despliegaCotizador;
	}

	public void setDespliegaCotizador(String despliegaCotizador) {
		this.despliegaCotizador = despliegaCotizador;
	}

	public String getRetarificacion() {
		return retarificacion;
	}

	public void setRetarificacion(String retarificacion) {
		this.retarificacion = retarificacion;
	}

	public String getDatoComplementario() {
		return datoComplementario;
	}

	public void setDatoComplementario(String datoComplementario) {
		this.datoComplementario = datoComplementario;
	}

	public String getObligatorioComplementario() {
		return obligatorioComplementario;
	}

	public void setObligatorioComplementario(String obligatorioComplementario) {
		this.obligatorioComplementario = obligatorioComplementario;
	}

	public String getModificableComplementario() {
		return modificableComplementario;
	}

	public void setModificableComplementario(String modificableComplementario) {
		this.modificableComplementario = modificableComplementario;
	}

	public String getApareceEndoso() {
		return apareceEndoso;
	}

	public void setApareceEndoso(String apareceEndoso) {
		this.apareceEndoso = apareceEndoso;
	}

	public String getModificaEndoso() {
		return modificaEndoso;
	}

	public void setModificaEndoso(String modificaEndoso) {
		this.modificaEndoso = modificaEndoso;
	}

	public String getObligatorioEndoso() {
		return obligatorioEndoso;
	}

	public void setObligatorioEndoso(String obligatorioEndoso) {
		this.obligatorioEndoso = obligatorioEndoso;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public String getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(String agrupador) {
		this.agrupador = agrupador;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getValorDefecto() {
		return valorDefecto;
	}

	public void setValorDefecto(String valorDefecto) {
		this.valorDefecto = valorDefecto;
	}

	public String getCodigoTabla() {
		return codigoTabla;
	}

	public void setCodigoTabla(String codigoTabla) {
		this.codigoTabla = codigoTabla;
	}

	public String getCodigoExpresion() {
		return codigoExpresion;
	}

	public void setCodigoExpresion(String codigoExpresion) {
		this.codigoExpresion = codigoExpresion;
	}

	public String getCodigoExpresionSession() {
		return codigoExpresionSession;
	}

	public void setCodigoExpresionSession(String codigoExpresionSession) {
		this.codigoExpresionSession = codigoExpresionSession;
	}

	public void setDctosPresiniestrosManagerJdbcTemplate(
			DctosPresiniestrosManager dctosPresiniestrosManagerJdbcTemplate) {
		this.dctosPresiniestrosManagerJdbcTemplate = dctosPresiniestrosManagerJdbcTemplate;
	}

	public List<DocumentoVO> getDocumentosList() {
		return documentosList;
	}

	public void setDocumentosList(List<DocumentoVO> documentosList) {
		this.documentosList = documentosList;
	}

	public String getCdValoDoc() {
		return cdValoDoc;
	}

	public void setCdValoDoc(String cdValoDoc) {
		this.cdValoDoc = cdValoDoc;
	}

}
