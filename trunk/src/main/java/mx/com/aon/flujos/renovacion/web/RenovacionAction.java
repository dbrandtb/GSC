/**
 * 
 */
package mx.com.aon.flujos.renovacion.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.renovacion.model.RenovacionVO;
import mx.com.aon.flujos.renovacion.service.RenovacionManager;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author Consultor Java
 * 
 */
public class RenovacionAction extends PrincipalRenovacionAction {

	private int start = 0;
	private int limit = 20;
	private int totalCount;

	private static final long serialVersionUID = 282790294226308306L;
	private static final String CHECK_RENOVACION = "S";
	private static final String FALSE = "false";
	private static final String TRUE = "true";
	

	private String success;
	private RenovacionManager renovacionManager;
	protected CatalogService catalogManager;
	
	//private processResultManagerJdbcTemplate
	private List<RenovacionVO> listaPolizas;
	private List<BaseObjectVO> listaAseguradora;
	private List<BaseObjectVO> listaTiposPoliza;
	private List<BaseObjectVO> listaProducto;
	private List<ClienteCorpoVO> listaCliente;
	private String cdElemento;
    private String cdUniEco;
    private String cdRamo;
    private String cdUnieco;
    private String Asegurado;
    private String nmPoliEx;
    private String volver2;
    private String volver3;

	private Map<String, String> parametrosBusqueda;
	private List<RenovacionVO> parametrosRenovar;
	
	
	private String filtros;
	private String estado;
	private String nmPoliza;
	private String nmSuplem;
	private String resultadoValidaPoliza;
    private String contentType ;
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	/**
	 * Atributos para mandar mesajes de respuesta(titulo del mensaje y contenido de mensaje) 
	 * ya sea para un success true o false
	 */
	private String msgId;
	private String msgTitle;
	private String msgText;
	
	
	private boolean validaEndoso;

	/**
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
	
	public String obtienePantallaPolizasRenovar() {
		logger.debug("**** Entrando a obtienePantallaPolizasRenovar");
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		
		return INPUT;
	}
	
	
	public String renovacionPolizas() {
		this.cdUnieco="1";//TODO Ver de donde se puede obtener el cdUniEco correctamente.
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();		
		
		if (logger.isDebugEnabled()) {
			logger.debug("******busquedaPoliza******");
			logger.debug("parametrosBusqueda            : "
					+ parametrosBusqueda);
			if (session.get("parametrosBusqueda") != null) {
				session.remove("parametrosBusqueda");
			}
		}
		return "consultaPolizaRenovar";
	}

	@SuppressWarnings("unchecked")
	public String obtienePolizas() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("******obtienePolizas******");
			logger.debug("parametrosBusqueda            : "
					+ parametrosBusqueda);

			if (parametrosBusqueda == null)
				parametrosBusqueda = (Map<String, String>) session
						.get("parametrosBusqueda");

			if (parametrosBusqueda != null) {
				session.put("parametrosBusqueda", parametrosBusqueda);
				logger.debug("parametrosBusqueda.Asegurado  : "
						+ parametrosBusqueda.get("Asegurado"));
				logger.debug("parametrosBusqueda.cdElemento : "
						+ parametrosBusqueda.get("cdElemento"));
				logger.debug("parametrosBusqueda.cdUnieco   : "
						+ parametrosBusqueda.get("cdUnieco"));
				logger.debug("parametrosBusqueda.nmPoliEx   : "
						+ parametrosBusqueda.get("nmPoliEx"));
				logger.debug("parametrosBusqueda.cdUnieco   : "
						+ parametrosBusqueda.get("cdUnieco"));
			}
		}

		parametrosBusqueda.put("idRegresar", idRegresar);
		parametrosBusqueda.put("clicBotonRegresar", "N");
    	
    	logger.debug("parametrosRegresar");
    	logger.debug(parametrosBusqueda);
    	
    	session.put("PARAMETROS_REGRESAR", parametrosBusqueda);
		
		try {
			PagedList pagedList = renovacionManager.getPagedList(
					parametrosBusqueda, "RENOVACION_OBTIENE_POLIZAS", getStart(),
					getLimit());
			
			logger.debug("start: "+getStart());
			logger.debug("limit: "+getLimit());
			
			listaPolizas = pagedList.getItemsRangeList();
			setTotalCount(pagedList.getTotalItems());
			
			logger.debug("listaPolizas: "+listaPolizas);
			logger.debug("totalCount: "+getTotalCount());
			
			
	    	//VALORES PARA EXPORTACION:
			String [] NOMBRE_COLUMNAS = {"ASEGURADO","ASEGURADORA","PRODUCTO","POLIZA","INCISO","FECHA_RENOVA"};
			session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
			session.put("ENDPOINT_EXPORT_NAME", "RENOVACION_OBTIENE_POLIZAS_EXPORTAR");
			session.put("PARAMETROS_EXPORT", parametrosBusqueda);
			logger.debug("parameters:"+session.get("PARAMETROS_EXPORT"));
			success = "true";
		} catch (Exception e) {
			success = null;
			logger.debug("obtienePolizas EXCEPTION :" + e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String obtenComboAsegradora() {

		try {
			listaAseguradora = renovacionManager
					.getItemList("OBTIENE_ASEGURADORAS_CAT");
		} catch (Exception e) {
			logger.debug("obtenComboAsegradora EXCEPTION :" + e);
		}

		// success = true;
		return SUCCESS;
	}

	
	@SuppressWarnings("unchecked")
	public String obtenComboTiposPoliza()throws Exception{
			
		UserVO usuario = (UserVO) session.get("USUARIO");
		
		try {
			
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("cdidioma",usuario.getIdioma().getValue());
    		params.put("cdtabla", "TEDORENO");
    		params.put("cdregion", usuario.getRegion().getValue());
    		
    		//logger.debug("region: "+params.get("cdregion"));
    		//logger.debug("idioma: "+params.get("cdidioma"));
    		
    		
    		listaTiposPoliza = catalogManager.getItemList("OBTIENE_LISTA_TIPOS",params);
    		logger.debug("ListaTiposPoliza: "+listaTiposPoliza);
    		
		} catch (Exception e) {
			logger.debug("obtenComboTiposPoliza EXCEPTION :" + e);
		}

		// success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String obtenComboProducto() {

		try {
			listaProducto = renovacionManager
					.getItemList("OBTIENE_PRODUCTOS_CAT");
		} catch (Exception e) {
			logger.debug("obtenComboProducto EXCEPTION :" + e);
		}

		// success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String obtenComboCliente() {

		try {
			listaCliente = renovacionManager
					.getItemList("OBTIENE_CLIENTES_CORPO");
		} catch (Exception e) {
			logger.debug("obtenComboCliente EXCEPTION :" + e);
		}

		// success = true;
		return SUCCESS;
	}

	public String polizasRenovadas() {

    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		if (logger.isDebugEnabled()) {
			logger.debug("******polizaRenovadas******");
			logger.debug("parametrosBusqueda            : "
					+ parametrosBusqueda);
			if (session.get("parametrosBusqueda") != null) {
				session.remove("parametrosBusqueda");
			}
		}

		UserVO usuario = (UserVO) session.get("USUARIO");

		if (logger.isDebugEnabled()) {
			logger.debug("CDELEMENTO :" + usuario.getEmpresa().getElementoId());
			logger.debug("CDPERSON   :" + usuario.getCodigoPersona());
			logger.debug("CDROL      :"
					+ usuario.getRolActivo().getObjeto().getValue());
		}
		return INPUT;
	}
	
	/**
     * Validación de endosos en la póliza
     * @return SUCCESS
     * @author Emilio Flores
     */
    public String validacionPolizaEndososRenovacion(){
   	
    	if (logger.isDebugEnabled()){
    		logger.debug("-> validacionPolizaEndososRenovacion");
    		logger.debug(":: cdRamo : " + cdRamo);
    	}
    	
    	try{
    		validaEndoso = renovacionManager.validaEndosoPoliza(cdRamo, "VALIDA_ENDOSO_POLIZA_RENOVA");
    		
    		if (logger.isDebugEnabled()){
        		logger.debug(":: validaEndosoRenova : " + validaEndoso);
        	}
    	}catch(ApplicationException ex){
    		logger.error("validacionPolizaEndososRenovacion EXCEPTION:: " + ex);
    	}
    	
    	success = TRUE;    	
    	return SUCCESS;
    }

	@SuppressWarnings("unchecked")
	public String obtienePolizasRenovadas() {

		if (logger.isDebugEnabled()) {
			logger.debug("******obtienePolizasRenovadas******");
			logger.debug("parametrosBusqueda            : "
					+ parametrosBusqueda);

			if (parametrosBusqueda == null)
				parametrosBusqueda = (Map<String, String>) session
						.get("parametrosBusqueda");

			if (parametrosBusqueda != null) {
				session.put("parametrosBusqueda", parametrosBusqueda);
				logger.debug("parametrosBusqueda.Asegurado  : "
						+ parametrosBusqueda.get("Asegurado"));
				logger.debug("parametrosBusqueda.cdElemento : "
						+ parametrosBusqueda.get("cdElemento"));
				logger.debug("parametrosBusqueda.cdUnieco   : "
						+ parametrosBusqueda.get("cdUnieco"));
				logger.debug("parametrosBusqueda.nmPoliEx   : "
						+ parametrosBusqueda.get("nmPoliEx"));
				logger.debug("parametrosBusqueda.cdUnieco   : "
						+ parametrosBusqueda.get("cdUnieco"));
				logger.debug("parametrosBusqueda.estado   : "
						+ parametrosBusqueda.get("estado"));
			}
		}

		parametrosBusqueda.put("idRegresar", idRegresar);
		parametrosBusqueda.put("clicBotonRegresar", "N");
    	
    	logger.debug("parametrosRegresar");
    	logger.debug(parametrosBusqueda);
    	
    	session.put("PARAMETROS_REGRESAR", parametrosBusqueda);
		
		try {
			PagedList pagedList = renovacionManager.getPagedList(
					parametrosBusqueda, "RENOVACION_OBTIENE_POLIZAS_RENOVADAS",
					getStart(), getLimit());
			listaPolizas = pagedList.getItemsRangeList();
			setTotalCount(pagedList.getTotalItems());
			success = "true";
	    	//VALORES PARA EXPORTACION:
			String [] NOMBRE_COLUMNAS = {"ASEGURADO","ASEGURADORA","PRODUCTO","POLIZAANTERIOR","POLIZARENOVACION","INCISO","FECHARENOVACION","PRIMA","SWAPROBADA"};
			session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
			session.put("ENDPOINT_EXPORT_NAME", "RENOVACION_OBTIENE_POLIZAS_RENOVADAS_EXPORTAR");
			session.put("PARAMETROS_EXPORT", parametrosBusqueda);
			logger.debug("parameters:"+session.get("PARAMETROS_EXPORT"));
		} catch (Exception e) {
			success = null;
			logger.debug("obtienePolizasRenovadas EXCEPTION :" + e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String guardaRenovarPolizas() {

		if (logger.isDebugEnabled()) {
			logger.debug("******guardaRenovarPolizas******");
			logger.debug("parametrosRenovar            : " + parametrosRenovar);

			for (RenovacionVO r : parametrosRenovar) {
				logger.debug("cdUnieco   : " + r.getCdCia());
				logger.debug("cdRamo     : " + r.getCdRamo());
				logger.debug("nmPoliza   : " + r.getNmPoliza());
				logger.debug("renovar    : " + r.getRenovar());
			}
		}
		
		try{
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("GUARDAR", parametrosRenovar);			
			WrapperResultados Resultado = renovacionManager.getActionWrapperResultados(params, "RENOVACION_GUARDA_POLIZA_SELECCION");
			
			if(Resultado!=null){
				
				this.msgTitle=Resultado.getMsgTitle();
				this.msgId=Resultado.getMsgId();
				
				logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
				logger.debug("Mensaje Contenido Resultado: " + this.msgId);
				
				params.put("MSG_ID",this.msgId);
				WrapperResultados mensaje = renovacionManager.getActionWrapperResultados(params, "RENOVACION_OBTIENE_MENSAJE");
				
				if(mensaje!=null){
					
						this.msgText=mensaje.getMsgText();
						//logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
						logger.debug("Mensaje Texto Resultado al guardar Poliza: " + this.msgText);
						
						if(Resultado.isMsgError()){
							success=FALSE;
						}else{
							success=TRUE;
						}
				}else{
					success=FALSE;
					this.msgText="Ocurrio un error en la transaccion";
				}
				
			}else {
				success=FALSE;
				this.msgText="Ocurrio un error en la transaccion";
			}
			
			if(FALSE.equals(success)){
				logger.debug("Error al Guardar Poliza");
			}
			
			
			
		}catch(Exception e){
			logger.debug("Exception guardaRenovarPolizas : " + e);
			this.msgText="Ocurrio un error en la transaccion";
			success=FALSE;
		}

		return SUCCESS;
	}

	
	/*
	 * Metodo para Generar el Borrador del las Polizas
	 */
	public String borradorPolizas()throws Exception {
		
		UserVO usuario = (UserVO) session.get("USUARIO");
		List<RenovacionVO> paramsCopiaTarificaPol = new ArrayList<RenovacionVO> ();

		if (logger.isDebugEnabled()) {
			logger.debug("******borradorPolizas******");
			logger.debug("parametrosRenovar  : " + parametrosRenovar);
			//logger.debug("USUARIO "+ usuario.getUser());
			//logger.debug("Idioma value "+ usuario.getIdioma().getValue());
			
			for (RenovacionVO r : parametrosRenovar) {
				logger.debug("Asegurado   : " + r.getAsegurado());
				logger.debug("cdUnieco    : " + r.getCdCia()); //en realidad aqui CdCia es la compania porq lo que falta el de CdUnieco
				logger.debug("cdRamo      : " + r.getCdRamo());
				logger.debug("cdCliente   : " + r.getCdCliente());
				logger.debug("nmPoliza    : " + r.getNmPoliza());
				logger.debug("renovar     : " + r.getRenovar());
				logger.debug("año         : " + r.getNmanno());
				logger.debug("mes 		  : " + r.getNmmes());
				logger.debug("Seleccionar : " + r.getSeleccionar());
				
				//cuando los dos checkbox esten seleccionados ('seleccionar' y 'renovar') 
							
				if(r.getRenovar()!=null && CHECK_RENOVACION.equalsIgnoreCase(r.getRenovar())){
					paramsCopiaTarificaPol.add(r);
				}
			}
		}

		try{
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("RENOVAR", parametrosRenovar);
			
			params.put("COPIA_TARIFICA_POL", paramsCopiaTarificaPol);
			params.put("CD_USUARIO",usuario.getUser());
			params.put("CD_IDIOMA",usuario.getIdioma().getValue());
			
			
			WrapperResultados Resultado = renovacionManager.getActionWrapperResultados(params, "RENOVACION_BORRADOR_POLIZAS");
			
			if(Resultado!=null){
				
				this.msgTitle=Resultado.getMsgTitle();
				this.msgId=Resultado.getMsgId();
				
				logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
				logger.debug("Mensaje Contenido Resultado: " + this.msgId);
				
				params.put("MSG_ID",this.msgId);
				WrapperResultados mensaje = renovacionManager.getActionWrapperResultados(params, "RENOVACION_OBTIENE_MENSAJE");
				
				if(mensaje!=null){
					
						this.msgText=mensaje.getMsgText();
						//logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
						logger.debug("Mensaje Texto Resultado: " + this.msgText);
						
						if(Resultado.isMsgError()){
							success=FALSE;
						}else{
							success=TRUE;
						}
				}else{
					success=FALSE;
					this.msgText="Ocurrio un error en la transaccion";
				}
				
			}else {
				success=FALSE;
				this.msgText="Ocurrio un error en la transaccion";
			}
			
			if(FALSE.equals(success)){
				logger.debug("Error al renovar");
			}
			
			
			
			
		}catch(Exception e){
			logger.debug("Exception en metodo renovarRenovarPolizas: " + e);
			this.msgText="Ocurrio un error en la transaccion";
			success=FALSE;
		}
		return SUCCESS;
	}
	
	/*
	 * Metodo para renovar las Polizas
	 */
	public String renovarPolizas()throws Exception {
		
		UserVO usuario = (UserVO) session.get("USUARIO");

		if (logger.isDebugEnabled()) {
			logger.debug("******renovarPolizas******");
			logger.debug("parametrosRenovar: " + parametrosRenovar);
			//logger.debug("USUARIO "+ usuario.getUser());
			//logger.debug("Idioma value "+ usuario.getIdioma().getValue());

		}

		try{
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("COPIA_TARIFICA_MTA", parametrosRenovar);
			params.put("CD_USUARIO",usuario.getUser());
			params.put("CD_IDIOMA",usuario.getIdioma().getValue());
			
			
			WrapperResultados Resultado = renovacionManager.getActionWrapperResultados(params, "RENOVACION_RENOVAR_POLIZAS");
			
			if(Resultado!=null){
				
				this.msgTitle=Resultado.getMsgTitle();
				this.msgId=Resultado.getMsgId();
				
				logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
				logger.debug("Mensaje Contenido Resultado: " + this.msgId);
				
				params.put("MSG_ID",this.msgId);
				WrapperResultados mensaje = renovacionManager.getActionWrapperResultados(params, "RENOVACION_OBTIENE_MENSAJE");
				
				if(mensaje!=null){
					
						this.msgText=mensaje.getMsgText();
						//logger.debug("Mensaje Titulo Resultado: " + this.msgTitle);
						logger.debug("Mensaje Texto Resultado: " + this.msgText);
						
						if(Resultado.isMsgError()){
							success=FALSE;
						}else{
							success=TRUE;
						}
				}else{
					success=FALSE;
					this.msgText="Ocurrio un error en la transaccion";
				}
				
			}else {
				success=FALSE;
				this.msgText="Ocurrio un error en la transaccion";
			}
			
			if(FALSE.equals(success)){
				logger.debug("Error al renovar");
			}
			
			
		}catch(Exception e){
			logger.debug("Exception en metodo renovarRenovarPolizas: " + e);
			this.msgText="Ocurrio un error en la transaccion";
			success=FALSE;
		}

		return SUCCESS;
	}
	
	/**
	 * Metdodo para la aprobacion de las polizas a renovar
	 * @return
	 */
	public String aprobarPolizas() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("******aprobarPolizas******");
			logger.debug("parametrosRenovar  : " + parametrosRenovar);

			for (RenovacionVO r : parametrosRenovar) {
				logger.debug("cdUnieco   : " + r.getCdUnieco());
				logger.debug("cdRamo     : " + r.getCdRamo());
				logger.debug("nmPoliza   : " + r.getNmPoliza());
				logger.debug("swAprobada : " + r.getSwAprobada());
			}
		}
		try{
			
			WrapperResultados resultadoAprobar = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("APROBAR", parametrosRenovar);			
			resultadoAprobar = renovacionManager.getActionWrapperResultadosMsgTxt(params, "RENOVACION_APROBAR_POLIZAS");
			
			if(resultadoAprobar!=null && StringUtils.isNotBlank(resultadoAprobar.getMsgText()))this.msgText = resultadoAprobar.getMsgText();
			else this.msgText = "Error Consulte a Soporte";
			
		}catch(Exception e){
			this.msgText = "Error Consulte a Soporte";
			success=FALSE;
			logger.debug("Exception aprobarPolizas :" + e);
			return SUCCESS;
		}
		success = TRUE;
		return SUCCESS;
	}
	
	/**
	 * Obtiene un conjunto de  polizas a exportar en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Recibos." + exportFormat.getExtension();
			
			TableModelExport model = renovacionManager.getModel(Asegurado, cdRamo, cdElemento, cdUnieco, nmPoliEx);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	
	public String validaPoliza() throws Exception{
		logger.debug("***EndosoPolizaAction validaPoliza***");
		/*
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            logger.debug("globalVarVO	:"+globalVarVO);
        }
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        */
		
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdunieco",	cdUnieco);
        params.put("cdramo",	cdRamo);
        params.put("estado",	estado);
        params.put("nmpoliza",	nmPoliza);
        params.put("nmsuplem",	nmSuplem);
        
        logger.debug("params	:"+params);
        
        WrapperResultados res = renovacionManager.getResultadoWrapper(params, "VALIDA_TARIFICACION_ENDOSOS_POLIZA");
        logger.debug("res			:"+res.getResultado());
        logger.debug("res MsgText	:"+res.getMsgText());
        logger.debug("res MsgTitle	:"+res.getMsgTitle());
        
        if(res.getResultado().equals("0")){
        	resultadoValidaPoliza = "ok";
        }else{
        	resultadoValidaPoliza = "fail";
        }
        
        return SUCCESS;
	}
	
	/**
	 * @return the renovacionManager
	 */
	public RenovacionManager obtenRenovacionManager() {
		return renovacionManager;
	}

	/**
	 * @param renovacionManager
	 *            the renovacionManager to set
	 */
	public void setRenovacionManager(RenovacionManager renovacionManager) {
		this.renovacionManager = renovacionManager;
	}

	/**
	 * @return the listaPolizas
	 */
	public List<RenovacionVO> getListaPolizas() {
		return listaPolizas;
	}

	/**
	 * @param listaPolizas
	 *            the listaPolizas to set
	 */
	public void setListaPolizas(List<RenovacionVO> listaPolizas) {
		this.listaPolizas = listaPolizas;
	}

	/**
	 * @return the listaAseguradora
	 */
	public List<BaseObjectVO> getListaAseguradora() {
		return listaAseguradora;
	}

	/**
	 * @param listaAseguradora
	 *            the listaAseguradora to set
	 */
	public void setListaAseguradora(List<BaseObjectVO> listaAseguradora) {
		this.listaAseguradora = listaAseguradora;
	}

	/**
	 * @return the listaProducto
	 */
	public List<BaseObjectVO> getListaProducto() {
		return listaProducto;
	}

	/**
	 * @param listaProducto
	 *            the listaProducto to set
	 */
	public void setListaProducto(List<BaseObjectVO> listaProducto) {
		this.listaProducto = listaProducto;
	}

	/**
	 * @return the listaCliente
	 */
	public List<ClienteCorpoVO> getListaCliente() {
		return listaCliente;
	}

	/**
	 * @param listaCliente
	 *            the listaCliente to set
	 */
	public void setListaCliente(List<ClienteCorpoVO> listaCliente) {
		this.listaCliente = listaCliente;
	}

	/**
	 * @return the parametrosBusqueda
	 */
	public Map<String, String> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	/**
	 * @param parametrosBusqueda
	 *            the parametrosBusqueda to set
	 */
	public void setParametrosBusqueda(Map<String, String> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	/**
	 * @return the filtros
	 */
	public String getFiltros() {
		return filtros;
	}

	/**
	 * @param filtros
	 *            the filtros to set
	 */
	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * @return the parametrosRenovar
	 */
	public List<RenovacionVO> getParametrosRenovar() {
		return parametrosRenovar;
	}

	/**
	 * @param parametrosRenovar
	 *            the parametrosRenovar to set
	 */
	public void setParametrosRenovar(List<RenovacionVO> parametrosRenovar) {
		this.parametrosRenovar = parametrosRenovar;
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

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}



	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getAsegurado() {
		return Asegurado;
	}

	public void setAsegurado(String asegurado) {
		Asegurado = asegurado;
	}

	public String getNmPoliEx() {
		return nmPoliEx;
	}

	public void setNmPoliEx(String nmPoliEx) {
		this.nmPoliEx = nmPoliEx;
	}

	public String getVolver2() {
		return volver2;
	}

	public void setVolver2(String volver2) {
		this.volver2 = volver2;
	}

	public String getVolver3() {
		return volver3;
	}

	public void setVolver3(String volver3) {
		this.volver3 = volver3;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getNmSuplem() {
		return nmSuplem;
	}

	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	public String getResultadoValidaPoliza() {
		return resultadoValidaPoliza;
	}

	public void setResultadoValidaPoliza(String resultadoValidaPoliza) {
		this.resultadoValidaPoliza = resultadoValidaPoliza;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setListaTiposPoliza(List<BaseObjectVO> listaTiposPoliza) {
		this.listaTiposPoliza = listaTiposPoliza;
	}

	public List<BaseObjectVO> getListaTiposPoliza() {
		return listaTiposPoliza;
	}

	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}

	public boolean isValidaEndoso() {
		return validaEndoso;
	}

	public void setValidaEndoso(boolean validaEndoso) {
		this.validaEndoso = validaEndoso;
	}

	public String getIdRegresar() {
		return idRegresar;
	}

	public void setIdRegresar(String idRegresar) {
		this.idRegresar = idRegresar;
	}


}
