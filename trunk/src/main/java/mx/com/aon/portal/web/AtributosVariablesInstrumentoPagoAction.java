package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import mx.com.aon.catweb.configuracion.producto.atributosVariables.model.AtributosVariablesVO;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.reglanegocio.model.ReglaNegocioVO;
import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.portal.model.AseguradoraVO;
import mx.com.aon.portal.model.AtributosVariablesInstPagoVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.model.InstrumentoPagoVO;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.InstrumentoPagoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


public class AtributosVariablesInstrumentoPagoAction extends ActionSupport implements SessionAware{
	
	public Map session;
	private static final long serialVersionUID = 4479437004252887173L;

	protected final transient Logger logger = Logger.getLogger(AtributosVariablesInstrumentoPagoAction.class);
	
	private InstrumentoPagoManager instrumentoPagoManagerJdbcTemplate;
	private CatalogService catalogManager;
	
	private boolean success;
	
	private List<ClienteCorpoVO> clientesList;
	private List<BaseObjectVO> aseguradorasList;
    private List<BaseObjectVO> productosList;
    private List<InstrumentoPagoVO> instrumentosList;
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
	    public String obtenComboInstrumentosPago() throws Exception{
	    	
	        if(logger.isDebugEnabled()){
	        	logger.debug("******obtenComboInstrumentosPago******");
	            logger.debug("id cliente: " + cdElemento);
	        }
	        
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("cdElemento", cdElemento);
	        try{
		        instrumentosList = catalogManager.getWrapperItemList("OBTIENE_INTRUMENTOS_PAGO_CLIENTE",params);
		        if(logger.isDebugEnabled()) logger.debug("COMBO INSTRUMENTOS: "+ instrumentosList);
	        }catch(Exception e){
	        	logger.error("Exception obtenComboInstrumentosPago: " + e.getMessage(),e);
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
        	PagedList pagedList = instrumentoPagoManagerJdbcTemplate.getInstrumetosPagoCliente(cdElemento, cdForPag, cdUnieco, cdRamo, start , limit);
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
        
        
        try{
        	mensajeRespuesta = instrumentoPagoManagerJdbcTemplate.agregarInstrumetoPagoCliente(cdElemento, cdForPag, cdUnieco, cdRamo);
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
        	mensajeRespuesta = instrumentoPagoManagerJdbcTemplate.borrarInstrumetoPagoCliente(cdInsCte);
        }catch(Exception e){
        	mensajeRespuesta = "Error al borrar. Consulte a su soporte";
        	logger.error("Exception borrarInstrumetoPagoCliente: " + e);
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
        	PagedList pagedList = instrumentoPagoManagerJdbcTemplate.getAtributosInstrumetoPago(cdInsCte, dsAtribu, start , limit);
        	atributosInstrumentoPago = pagedList.getItemsRangeList();
        	setTotalCount(pagedList.getTotalItems());
        	if(logger.isDebugEnabled()) logger.debug("GRID ATRIBUTOS INSTRUMENTO PAGO LIST: "+ atributosInstrumentoPago);
        	
        }catch(Exception e){
        	atributosInstrumentoPago = new ArrayList<AtributosVariablesInstPagoVO>();
        	logger.error("Exception obtenAtributosGridInstrumetosPago: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;	
        }
        
        success = true;
		return SUCCESS;
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
        	mensajeRespuesta = instrumentoPagoManagerJdbcTemplate.guardarAtributoInstrumetoPagoCliente(params);
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
        	mensajeRespuesta = instrumentoPagoManagerJdbcTemplate.borrarAtributoInstrumetoPago(cdInsCte, cdAtribu);
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

	public void setInstrumentoPagoManagerJdbcTemplate(
			InstrumentoPagoManager instrumentoPagoManagerJdbcTemplate) {
		this.instrumentoPagoManagerJdbcTemplate = instrumentoPagoManagerJdbcTemplate;
	}

	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}

	public List<InstrumentoPagoVO> getInstrumentosList() {
		return instrumentosList;
	}

	public void setInstrumentosList(List<InstrumentoPagoVO> instrumentosList) {
		this.instrumentosList = instrumentosList;
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
	

}
