/**
 * 
 */
package mx.com.aon.procesos.emision.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.FuncionPolizaVO;
import mx.com.aon.procesos.emision.model.IncisosVO;
import mx.com.aon.procesos.emision.model.ObjetoAsegurableVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;
import mx.com.aon.procesos.emision.model.PolizaMaestraVO;
import mx.com.aon.procesos.emision.model.RecibosVO;
import mx.com.aon.procesos.emision.service.EmisionManager;
import mx.com.aon.utils.Constantes;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Cesar Hernandez
 *
 */
public class EmisionAction extends PrincipalEmisionAction{

	private String producto;
	private String poliza;
	private String nombreAsegurado;
	private String aseguradora;
	private String inicioVigencia;
	private String finVigencia;
	private String statusPoliza;
	private String fechaInicioFin;
	private String cdUnieco;
	private String estado;
	private String cdRamo;
	private String nmPoliza;
	private String nmSituac;
	private String itemsPantalla;
	private String cdInciso;
	private String dsDescripcion;
	private String status;
	private String cdGarant;
	private String nmObjeto;
	private String nmRecibo;
	private String dsElemen;
	private String dsRamo;
	private String dsUnieco;
	private String cdElemento;
	
	//utilizado para volver a la pantalla de AGRUPACION POLIZA POR ROL
	private String cdPolMtra;
	private String codigoConfiguracion;
	private String codigoAgrupacion;
	
	//utilizado para volver a la pantalla de AGRUPACION POR GRUPO DE PERSONA	
	private String cveAgrupa;
	
	//utilizado para ir a la pantalla de CANCELACION MANUAL
	private String feEfecto;
	private String feVencim;
	private String cdUniAge;
	
	private String store;
	
	private String volver1;
	private String volver2;
	private String volver3;
	private String volver4;
	
	private boolean success;
	private String messageResult;
	
	private EmisionManager emisionManager;
	
	
	private List<EmisionVO> emisiones;
	private List<PolizaDetVO> polizaDet;
	private List<ObjetoAsegurableVO> objAsegurable;
	private List<FuncionPolizaVO> fnPoliza;
	private List<DatosAdicionalesVO> datAdicionales;
	private AyudaCoberturaCotizacionVO ayudaCoberturaVo;
	
	protected CatalogService catalogManager;

    @SuppressWarnings("unchecked")
    private List listProductos;
    
    @SuppressWarnings("unchecked")
    private List listAseguradoras;
    
    @SuppressWarnings("unchecked")
    private List listStatusPoliza;
	
    protected int start = 0;
    protected int limit = 20;
    protected int totalCount;
    protected int totalCountFnPol;
    

    private List<RecibosVO> listRecibos;
    private ArrayList<TextFieldControl> dext;
    private ArrayList<TextFieldControl> dextWin;
    private List<IncisosVO> listCoberturasIncisos;
    private List<BaseObjectVO> listAccesoriosIncisos;
    private List<MpoliagrVO> listMpoliagr;
    private List<PolizaMaestraVO> listPolizasMaestras;
    private Map<String, String> parametrosBusqueda;
    private List<ClienteCorpoVO> listaNivel;
    private List<BaseObjectVO> listaAseguradoras;
    private List<BaseObjectVO> listaProductos;
    private List<BaseObjectVO> listaTipoDeSituacion;
    private List<BaseObjectVO> listaTipos;
    private String respuestaPL;
    private PolizaMaestraVO polizaMaestraReg;
    
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
    
	private String mensajeError;
    
	private String cdCia;
	
	private String cdTipo;
	private String nmPoliEx;
	private String feInicio;
	private String feFin;
	private String cdNumPol;
	private String nmpoliex;
	private transient PolizasManager  polizasManager;
    /**
     * 
     */
	private String cdCliente;
	private String cdAsegurado;
	
	/**
	 * Utilizado para cuando venimos de la pantalla de Numeracion de Polizas
	 */
	private String pantallaOrigen;
	
	/**
	 * Utilizado para cuando venimos de la pantalla de polizas canceladas
	 */
	private String plzacancelada;
	
	private boolean validaEndoso;

	/**
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
	
	
    private static final long serialVersionUID = 1839776301724353232L;
    
    public String execute(){
    	
    	return INPUT;
    }

    /**
     * Obtiene parte de la pantalla a pintar
     * @return INPUT
     */
    @SuppressWarnings("unchecked")
    public String busquedaPoliza(){
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
    	
    	if ( session.containsKey("PARAMETROS_REGRESAR") ) {
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    		logger.debug(session.get("PARAMETROS_REGRESAR"));
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	}

    	if (logger.isDebugEnabled()){
	    	logger.debug("******busquedaPoliza******");
	    	logger.debug("cdUnieco  : " + cdUnieco);
	    	logger.debug("cdRamo    : " + cdRamo);
	    	logger.debug("estado    : " + estado);
	    	logger.debug("nmPoliza  : " + nmPoliza);
	    	logger.debug("nmSituac  : " + nmSituac);
    	}
    	
    	
    	//VALORES PARA EXPORTACION:
		String [] NOMBRE_COLUMNAS = {"ASEGURADORA","PRODUCTO","POLIZA","PERIODO_PAGO","ASEGURADO","RFC","VIGENCIA","FORMA_PAGO","PRIMA","INCISO"};
		session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
		session.put("ENDPOINT_EXPORT_NAME", "OBTIENE_POLIZAS_EXPORT");
		Map<String,Object> params = new HashMap<String,Object>();
    	params.put("cdUnieco", "1");
    	params.put("fechaInicioFin", "I");
		session.put("PARAMETROS_EXPORT", params);
		logger.debug("parameters:"+session.get("PARAMETROS_EXPORT"));
		
    	
    	return INPUT;
    }
    
    /**
     * Obtiene parte de la pantalla a pintar
     * @return INPUT
     */
    @SuppressWarnings("unchecked")
    public String obtienePantalla(){

    	this.cdUnieco="1";//TODO Ver de donde se puede obtener el cdUniEco correctamente.

    	if ( session.containsKey("PARAMETROS_REGRESAR") ) {
    		Map<String,String> pr = (Map<String,String>)session.get("PARAMETROS_REGRESAR");
    		pr.put("idRegresar", idRegresar);
    		pr.put("clicBotonRegresar", "S");
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    		logger.debug(pr);
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    		session.put("PARAMETROS_REGRESAR", pr);
    	}
		
		
    	if (logger.isDebugEnabled()){
	    	logger.debug("******obtienePantalla******");
	    	logger.debug("cdUnieco  : " + cdUnieco);
	    	logger.debug("cdRamo    : " + cdRamo);
	    	logger.debug("estado    : " + estado);
	    	logger.debug("nmPoliza  : " + nmPoliza);
    	}
    	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("cdunieco", cdUnieco);
    	params.put("cdramo",   cdRamo);
    	params.put("estado",   estado);
    	params.put("nmpoliza", nmPoliza);
    	
    	try{
    		dext = emisionManager.getDatosRolExt(params, "OBTIENE_DATOS_ADICIONALES_DETALLE_POLIZA");
    		logger.debug("obtienePantalla dext: " + dext);
    	}catch(Exception ex){
    		if (logger.isDebugEnabled())
    			logger.debug("obtienePantalla EXCEPTION :"+ex);
    	}
    	
    	return INPUT;
    }
    
    /**
     * Obtiene los datod adicionales
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneDatosAdicionales(){
    	Map<String, String> params = new HashMap<String, String>();
    	/*params.put("cdUnieco", "1");
    	params.put("cdRamo", "2");
    	params.put("estado", "W");
    	params.put("nmPoliza", "10039");*/
    	
    	params.put("cdUnieco", cdUnieco);
    	params.put("cdRamo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmPoliza", nmPoliza);
    	
    	try{
    		datAdicionales = emisionManager.obtieneDatosAdicionales(params);
    	}
    	catch(Exception ex){
    		logger.debug("obtieneDatosAdicionales EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Obtiene los Productos
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerProductos(){
    	try{
    		listProductos = catalogManager.getItemList("OBTIENE_PRODUCTOS_CAT");
    	}
    	catch(Exception ex){
    		logger.debug("obtenerProductos EXCEPTION:: " + ex);
    	}
    	
    	//logger.debug("Productos:: " + itemList);
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Obtiene las Aseguradoras
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerAseguradoras(){
    	try{
    		listAseguradoras = catalogManager.getItemList("OBTIENE_ASEGURADORAS_CAT");
    	}
    	catch(Exception ex){
    		logger.debug("obtenerAseguradoras EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Obtiene el status de la poliza
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerStatusPoliza(){
    	try{
    		listStatusPoliza = catalogManager.getItemList("OBTIENE_STATUSPOLIZA_CAT");
    	}
    	catch(Exception ex){
    		logger.debug("obtenerStatusPoliza EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Consulta la funcion de poliza
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String consultaFuncionPoliza(){
    	GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
    	if (logger.isDebugEnabled()){
	    	logger.debug("::globalVarVO:::::::::: " + globalVarVO);
	    	logger.debug("::nmPoliza session::::: " + globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
	    	logger.debug("::nmPoliza request::::: " + nmPoliza);
    	}
    	
    	if (StringUtils.isNotBlank(nmPoliza) &&
    			!nmPoliza.equals(globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()))) {
    		nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
    	}
    	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("cdUnieco", cdUnieco);
    	params.put("cdRamo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmPoliza", nmPoliza);
    	params.put("nmSituac", nmSituac);
    	
    	if (logger.isDebugEnabled()){
	    	logger.debug(".:EmisionAction->CONSULTA FUNCION EN LA POLIZA:.");
	    	logger.debug("cdUnieco:: " + cdUnieco);
	    	logger.debug("cdRamo:: " + cdRamo);
	    	logger.debug("estado:: " + estado);
	    	logger.debug("nmPoliza:: " + nmPoliza);
	    	logger.debug("nmSituac:: " + nmSituac);
    	}
	    	
    	try{
    		PagedList pagedList = emisionManager.consultaFuncionPoliza(params, start, limit);
			fnPoliza = pagedList.getItemsRangeList();
            fnPoliza = countRoles(fnPoliza);
			totalCountFnPol = pagedList.getTotalItems();
    	}
    	catch(ApplicationException ex){
    		logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * @param fnPoliza2
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<FuncionPolizaVO> countRoles(List<FuncionPolizaVO> fnPoliza2) {
        if (logger.isDebugEnabled()){
            logger.debug("***** countRoles ******");    
        }
        
        String cdNumeroRol = null;
        String nmaximRol = null;   
        int numeroRoles = 0;
        Map<String, String> mapRoles = new HashMap<String, String>();
        
        for (FuncionPolizaVO polizaVO : fnPoliza2) {
            cdNumeroRol = polizaVO.getCdRol();
            
            if (mapRoles.containsKey(cdNumeroRol)) {
                numeroRoles = Integer.parseInt(mapRoles.get(cdNumeroRol));
                mapRoles.put(cdNumeroRol, String.valueOf(numeroRoles + 1));
            } else {
                mapRoles.put(cdNumeroRol, "1");
            }
            
            nmaximRol = new StringBuilder().append("NMAXIMROL").append(cdNumeroRol).toString();
            
            if (!mapRoles.containsKey(nmaximRol)) {
                mapRoles.put(nmaximRol, polizaVO.getNmaximo());
            }
        }
        
        if (logger.isDebugEnabled()){
            logger.debug("** mapRoles : " + mapRoles);    
        }
        
        for (FuncionPolizaVO polizaVO : fnPoliza2) {
            polizaVO.setTotalPerRol(mapRoles.get(polizaVO.getCdRol()));
        }
        session.put("MAP_ROLES_EMISION", mapRoles);
        
        return fnPoliza2;
    }
    
    /**
     * Consulta el objeto Asegurable
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String consultaObjetoAsegurable(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("cdUnieco", cdUnieco);
    	params.put("cdRamo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmPoliza", nmPoliza);
    	//params.put("nmSituac", nmSituac);
    	params.put("nmSituac", "");
    	
    	if (logger.isDebugEnabled()){
			logger.debug(".:CONSULTA OBJETO ASEGURABLE:.");
			logger.debug("cdUnieco:: " + cdUnieco);
			logger.debug("cdRamo:: " + cdRamo);
			logger.debug("estado:: " + estado);
			logger.debug("nmPoliza:: " + nmPoliza);
			logger.debug("nmSituac:: " + nmSituac);
    	}
    	
    	try{
    		PagedList pagedList = emisionManager.consultaObjetoAsegurable(params, start, limit);
    		objAsegurable = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
    	}
    	catch(ApplicationException ex){
    		logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Consulta el detalle de poliza (Datos generales de poliza)
     * @return SUCCESS
     */
    public String consultaDetallePoliza(){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("cdUnieco", cdUnieco);
    	params.put("cdRamo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmPoliza", nmPoliza);
    	
    	try{
    		polizaDet = emisionManager.consultaPolizaDetalle(params);
    	}
    	catch(ApplicationException ex){
    		logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Busca las polizas existentes
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String buscaPolizas()
    {
    	Map<String,String> map = new HashMap<String, String>();
    	Map<String,Object> params = new HashMap<String,Object>();
    	map.put("cdUnieco", "1");
    	params.put("cdUnieco", "1");

		Map<String,String> parametrosRegresar = new HashMap<String,String>();

		parametrosRegresar.put("producto", producto);
		parametrosRegresar.put("poliza", poliza);
		parametrosRegresar.put("nombreAsegurado", nombreAsegurado);
		parametrosRegresar.put("cdInciso", cdInciso);
		parametrosRegresar.put("aseguradora", aseguradora);
		parametrosRegresar.put("fechaInicioFin", fechaInicioFin);
		parametrosRegresar.put("inicioVigencia", inicioVigencia);
		parametrosRegresar.put("finVigencia", finVigencia);
		parametrosRegresar.put("statusPoliza", statusPoliza);
		parametrosRegresar.put("idRegresar", idRegresar);
		parametrosRegresar.put("clicBotonRegresar", "N");
    	
    	logger.debug("parametrosRegresar");
    	logger.debug(parametrosRegresar);
    	
    	session.put("PARAMETROS_REGRESAR", parametrosRegresar);
    	
    	if(store == null){
    		session.put("poliza", poliza);
    		session.put("producto", producto);
    		session.put("nombreAsegurado", nombreAsegurado);
    		session.put("aseguradora", aseguradora);
    		session.put("inicioVigencia", inicioVigencia);
    		session.put("finVigencia", finVigencia);
    		session.put("statusPoliza", statusPoliza);
    		session.put("fechaInicioFin", fechaInicioFin);
    		session.put("inciso", cdInciso);
    		
    		map.put("poliza", poliza);
        	map.put("producto", producto);
        	map.put("nombreAsegurado", nombreAsegurado);
        	map.put("aseguradora", aseguradora);
        	map.put("inicioVigencia", inicioVigencia);
        	map.put("finVigencia", finVigencia);
        	map.put("statusPoliza", statusPoliza);
        	map.put("fechaInicioFin", fechaInicioFin);
        	map.put("inciso", cdInciso);
	    	
	    	params.put("poliza", poliza);
	    	params.put("producto", producto);
	    	params.put("nombreAsegurado", nombreAsegurado);
	    	params.put("aseguradora", aseguradora);
	    	params.put("inicioVigencia", inicioVigencia);
	    	params.put("finVigencia", finVigencia);
	    	params.put("statusPoliza", statusPoliza);
	    	params.put("fechaInicioFin", fechaInicioFin);
	    	params.put("inciso", cdInciso);
    	}
    	else{
    		map.put("poliza", (String)session.get("poliza"));
        	map.put("producto", (String)session.get("producto"));
        	map.put("nombreAsegurado", (String)session.get("nombreAsegurado"));
        	map.put("aseguradora", (String)session.get("aseguradora"));
        	map.put("inicioVigencia", (String)session.get("inicioVigencia"));
        	map.put("finVigencia", (String)session.get("finVigencia"));
        	map.put("statusPoliza", (String)session.get("statusPoliza"));
        	map.put("fechaInicioFin", (String)session.get("fechaInicioFin"));
        	map.put("inciso", (String)session.get("inciso"));
        	
        	params.put("poliza", (String)session.get("poliza"));
        	params.put("producto", (String)session.get("producto"));
        	params.put("nombreAsegurado", (String)session.get("nombreAsegurado"));
        	params.put("aseguradora", (String)session.get("aseguradora"));
        	params.put("inicioVigencia", (String)session.get("inicioVigencia"));
        	params.put("finVigencia", (String)session.get("finVigencia"));
        	params.put("statusPoliza", (String)session.get("statusPoliza"));
        	params.put("fechaInicioFin", (String)session.get("fechaInicioFin"));
        	params.put("inciso", (String)session.get("inciso"));
    	}
		
    	logger.debug(".:******BUSCA POLIZAS ACTION******:.");
    	logger.debug(".:numPoliza:: " + poliza);
    	logger.debug(".:producto:: " + producto);
    	logger.debug(".:nombreAsegurado:: " + nombreAsegurado);
    	logger.debug(".:aseguradora:: " + aseguradora);
    	logger.debug(".:inicioVigencia:: " + inicioVigencia);
    	logger.debug(".:finVigencia:: " + finVigencia);
    	logger.debug(".:statusPoliza:: " + statusPoliza);
    	logger.debug(".:fechaInicioFin:: " + fechaInicioFin);
    	logger.debug(".:inciso:: " + cdInciso);

		try{
			PagedList pagedList = emisionManager.buscaPolizas(map, start, limit);
			emisiones = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			
	    	//VALORES PARA EXPORTACION:
			String [] NOMBRE_COLUMNAS = {"ASEGURADORA","PRODUCTO","POLIZA","PERIODO_PAGO","ASEGURADO","RFC","VIGENCIA","FORMA_PAGO","PRIMA","INCISO"};
			session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
			session.put("ENDPOINT_EXPORT_NAME", "OBTIENE_POLIZAS_EXPORT");
			session.put("PARAMETROS_EXPORT", params);
			logger.debug("parameters:"+session.get("PARAMETROS_EXPORT"));
		}
		catch(Exception ex){
			logger.debug("EXCEPTION:: " + ex);
		}
		
		logger.debug("*****emisiones:: " + emisiones);
		
		if (emisiones != null && !emisiones.isEmpty()) {
			success = true;
		} else {
			success = false;
		}
    	
    	return SUCCESS;
    }
    
    /**
     * Validación de endosos en la póliza
     * @return SUCCESS
     * @author Emilio Flores
     */
    public String validacionPolizaEndosos(){
   	
    	if (logger.isDebugEnabled()){
    		logger.debug("-> validacionPolizaEndosos");
    		logger.debug(":: cdRamo : " + cdRamo);
    	}
    	
    	try{
    		validaEndoso = emisionManager.validaEndosoPoliza(cdRamo, "VALIDA_ENDOSO_POLIZA");
    		
    		if (logger.isDebugEnabled()){
        		logger.debug(":: validaEndoso : " + validaEndoso);
        	}
    	}catch(ApplicationException ex){
    		logger.error("validacionPolizaEndosos EXCEPTION:: " + ex);
    	}
    	
    	success = true;    	
    	return SUCCESS;
    }
    
    /**
     * Validación de endosos para la Rehabilitacion
     * @return SUCCESS
     * @author Emilio Flores
     */
    public String validacionPolizaRehabilitacion(){
   	
    	if (logger.isDebugEnabled()){
    		logger.debug("-> validacionPolizaRehabilitacion");
    		logger.debug(":: cdRamo : " + cdRamo);
    	}
    	
    	try{
    		validaEndoso = emisionManager.validaEndosoPoliza(cdRamo, "VALIDA_ENDOSO_REHABILITACION");
    		
    		if (logger.isDebugEnabled()){
        		logger.debug(":: validaEndoso : " + validaEndoso);
        	}
    	}catch(ApplicationException ex){
    		logger.error("validacionPolizaRehabilitacion EXCEPTION:: " + ex);
    	}
    	
    	success = true;    	
    	return SUCCESS;
    }

    /**
     * Consulta de los recibos
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String consultaDetalleRecibos(){
   	
    	if (logger.isDebugEnabled()){
    		logger.debug("***consultaDetalleRecibos****");
    	}
    	Map <String,String> param = new HashMap<String,String>();
    	param.put("aseguradora", cdUnieco);
    	param.put("producto", cdRamo);
    	param.put("poliza", nmPoliza);    	
    	
    	try{
    		PagedList pagedList = emisionManager.getPagedList(param, "EMISION_OBTIENE_RECIBOS", start, limit);
    		listRecibos = pagedList.getItemsRangeList();
    		totalCount = pagedList.getTotalItems();
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("consultaDetalleRecibos EXCEPTION:: " + ex);
    	}
    	
    	success = true;    	
    	return SUCCESS;
    }
    
    /**
     * Consulta de los detalles de recibos
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String consultaRecibosDetalle(){

    	if (logger.isDebugEnabled()){
    		logger.debug("***consultaRecibosDetalle****");
    		logger.debug("cdUnieco  :"+cdUnieco);
    		logger.debug("cdRamo    :"+cdRamo);
    		logger.debug("estado    :"+estado);
    		logger.debug("nmPoliza  :"+nmPoliza);
    		logger.debug("nmRecibo  :"+nmRecibo);
    	}
    	Map <String,String> param = new HashMap<String,String>();
    	param.put("cdunieco", cdUnieco);
    	param.put("cdramo",   cdRamo);
    	param.put("estado",   estado);
    	param.put("nmpoliza", nmPoliza);
    	param.put("nmrecibo", nmRecibo);

    	try{
    		PagedList pagedList = emisionManager.getPagedList(param, "EMISION_OBTIENE_RECIBOS_DETALLE", start, limit);
    		listRecibos = pagedList.getItemsRangeList();
    		totalCount = pagedList.getTotalItems();
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("consultaRecibosDetalle EXCEPTION:: " + ex);
    	}

    	success = true;
    	return SUCCESS;
    }
    
    /**
     * Consulta de los detalles de recibos provenientes de polizas canceladas.
     * @return SUCCESS
    */
    @SuppressWarnings("unchecked")
    public String consultaPolizasCanceladasRecibosDetalle(){   

    	if (logger.isDebugEnabled()){
    		logger.debug("***consultaRecibosDetalle****");
    		logger.debug("cdUnieco  :"+cdUnieco);
    		logger.debug("cdRamo    :"+cdRamo);
    		logger.debug("estado    :"+estado);
    		logger.debug("nmPoliza  :"+nmPoliza);
    		logger.debug("nmRecibo  :"+nmRecibo);
    	}
    	
    	try {
    		PagedList pagedList = polizasManager.buscarPolizasCanceladasRecibosDetalle( cdUnieco,cdRamo,estado,nmPoliza,nmRecibo, start, limit);
    		listRecibos = pagedList.getItemsRangeList();
			totalCount  = pagedList.getTotalItems();
            success = true;
     
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
         
    	return SUCCESS;
    }
    
   
    /**
     * Consulta de datos rol
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenerPantallaDatosRol(){
    	
    	if (logger.isDebugEnabled()){
	    	logger.debug("***** obtenPantallaDatosRol ******");
	    	logger.debug("cdUnieco : " + cdUnieco);
	    	logger.debug("cdRamo   : " + cdRamo);
	    	logger.debug("estado   : " + estado);
	    	logger.debug("nmPoliza : " + nmPoliza);
	    	logger.debug("nmSituac : " + nmSituac);   	
    	}
    	Map <String,String> param = new HashMap<String,String>(); 	
    	param.put("cdunieco", cdUnieco);
    	param.put("cdramo", cdRamo);
    	param.put("estado", estado);
    	param.put("nmpoliza", nmPoliza);
    	param.put("nmsituac", nmSituac);    	
    	
    	try{
    		dext = emisionManager.getDatosRolExt(param, "EMISION_OBTIENE_DATOS_ROL_EXT");
    		logger.debug("dext: " + dext);
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("obtenPantallaDatosRol EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }

    /**
     * Consulta de Inciso
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenerPantallaIncisos(){
    	
    	if (logger.isDebugEnabled()){
	    	logger.debug("***getPantallaIncisos***");
	    	logger.debug("cdUnieco     :" + cdUnieco);
	    	logger.debug("cdRamo       :" + cdRamo);
	    	logger.debug("estado       :" + estado);
	    	logger.debug("nmPoliza     :" + nmPoliza);
	    	logger.debug("nmSituac     :" + nmSituac);
	    	logger.debug("poliza       :" + poliza);
	    	logger.debug("status       :" + status);
	    	logger.debug("cdInciso     :" + cdInciso);
	    	logger.debug("dsDescripcion:" + dsDescripcion);
	    	logger.debug("aseguradora  :" + aseguradora);
	    	logger.debug("producto     :" + producto);
    	}
    	
    	Map <String,String> params = new HashMap<String,String>();
    	params.put("cdunieco", cdUnieco);
    	params.put("cdramo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmpoliza", nmPoliza);
//    	params.put("cdinciso", "1");
//    	params.put("dsDescripcion", "1");
    	params.put("nmsituac", nmSituac);
//    	param.put("nmobjeto", "1");
//    	param.put("cdatribu", "");

    	try{
    		dext = emisionManager.getDatosRolExt(params, "EMISION_DATOS_INCISOS");    		
    		logger.debug("dext: " + dext);
    	}
    	catch(ApplicationException ex){
    		logger.debug("getPantallaIncisos EXCEPTION:: " + ex);
    	}
    	
    	success = true;
    	
    	return SUCCESS;
    }
    
    /**
     * Consulta para datos del grid de coberturas
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String coberturasGridJson(){
    	
    	if (logger.isDebugEnabled()){
    		logger.debug("***coberturasGridJson****");
	    	logger.debug("cdUnieco     :" + cdUnieco);
	    	logger.debug("cdRamo       :" + cdRamo);
	    	logger.debug("estado       :" + estado);
	    	logger.debug("nmPoliza     :" + nmPoliza);
	    	logger.debug("nmSituac     :" + nmSituac);
    	}

    	Map <String,String> params = new HashMap<String,String>();
    	params.put("cdunieco", cdUnieco);
    	params.put("cdramo", cdRamo);
    	params.put("estado", estado);
    	params.put("nmpoliza", nmPoliza);
    	params.put("nmsituac", nmSituac);
    	
    	try{
	    	PagedList pagedList = emisionManager.getPagedList(params,"EMISION_GRID_COBERTURA_INCISOS",start, limit);
	    	listCoberturasIncisos = pagedList.getItemsRangeList();
	        totalCount = pagedList.getTotalItems();
	        
	        if (logger.isDebugEnabled())
	            logger.debug(".. listCoberturasIncisos : " + listCoberturasIncisos);
    	
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("coberturasGridJson EXCEPTION:: " + ex);
    	}
    	
        success = true;
        return SUCCESS;
    }
    
    /**
     * Consulta para datos del grid de accesorios
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String accesoriosGridJson(){
    	
    	if (logger.isDebugEnabled()){
    		logger.debug("***accesoriosGridJson****");
	    	logger.debug("cdUnieco     :" + cdUnieco);
	    	logger.debug("cdRamo       :" + cdRamo);
	    	logger.debug("estado       :" + estado);
	    	logger.debug("nmPoliza     :" + nmPoliza);
	    	logger.debug("nmSituac     :" + nmSituac);
    	}

    	Map <String,String> param = new HashMap<String,String>();
    	param.put("cdunieco", cdUnieco);
    	param.put("cdramo", cdRamo);
    	param.put("estado", estado);
    	param.put("nmpoliza", nmPoliza);
    	param.put("nmsituac", nmSituac);
    	
    	try{
	    	PagedList pagedList = emisionManager.getPagedList(param,"EMISION_GRID_ACCESORIOS_INCISOS",start, limit);
	    	listAccesoriosIncisos = pagedList.getItemsRangeList();
	        totalCount = pagedList.getTotalItems();
	        
	        if (logger.isDebugEnabled())
	            logger.debug(".. listAccesoriosIncisos : " + listAccesoriosIncisos);
	        
	    }catch(ApplicationException ex){
	    	if (logger.isDebugEnabled())
	    		logger.debug("accesoriosGridJson EXCEPTION:: " + ex);
    	}
        success = true;
        return SUCCESS;
    }
    
    
    /**
     * Consulta detalles del grid de agrupador
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenerAgrupador(){
        
        if (logger.isDebugEnabled()){
            logger.debug("***getAgrupador***");
            logger.debug("cdunieco   :" + cdUnieco);
            logger.debug("cdramo     :" + cdRamo);
            logger.debug("estado     :" + estado);
            logger.debug("nmpoliza   :" + nmPoliza);
        }
        
        Map <String,String> params = new HashMap<String,String>();
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        
        try{
            listMpoliagr = emisionManager.getAgrupador(params, "OBTIENE_MPOLIAGR");          
            logger.debug("listMpoliagr Agrupador : " + listMpoliagr);
        }
        catch(ApplicationException ex){
            logger.debug("***** getAgrupador EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    
    /**
     * Consulta de detalles de Coberturas
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenerPantallaCoberturas(){
    	if (logger.isDebugEnabled()){
    		logger.debug("***getPantallaCoberturas****");
	    	logger.debug("cdUnieco     :"+cdUnieco);
	    	logger.debug("cdRamo       :"+cdRamo);
	    	logger.debug("estado       :"+estado);
	    	logger.debug("nmPoliza     :"+nmPoliza);
	    	logger.debug("nmSituac     :"+nmSituac);
	    	logger.debug("cdGarant     :"+cdGarant);
	    	logger.debug("status       :"+status);
    	}

    	Map <String,String> param = new HashMap<String,String>();
    	param.put("cdunieco", cdUnieco);
    	param.put("cdramo", cdRamo);
    	param.put("estado", estado);
    	param.put("nmpoliza", nmPoliza);
    	param.put("nmsituac", nmSituac);
    	param.put("cdgarant", cdGarant);
    	param.put("cdatribu", "");
    	param.put("status", status);    	
    	
    	try{    		
    		dext = emisionManager.getDatosRolExt(param, "EMISION_OBTIENE_DATOS_COBERTURAS");
            
            if (dext == null || dext.isEmpty()) {
                if (dext == null) {
                    dext = new ArrayList<TextFieldControl>();
                }
                TextFieldControl tfHidden = new TextFieldControl();
                tfHidden.setName("noFields");
                tfHidden.setId("noFields");
                tfHidden.setWidth(0);
                tfHidden.setHidden(true);
                tfHidden.setAllowBlank(true);
                dext.add(tfHidden);
            }
            
    		logger.debug("getPantallaCoberturas dext: " + dext);
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("getPantallaCoberturas EXCEPTION:: " + ex);
    	}
    	
    	success = true;    	
    	return SUCCESS;
    }
    
    /**
     * Consulta de detalles de Accesorios
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenerPantallaAccesorios(){
    	if (logger.isDebugEnabled()){
    		logger.debug("***getPantallaAccesorios****");
	    	logger.debug("cdUnieco     :"+cdUnieco);
	    	logger.debug("cdRamo       :"+cdRamo);
	    	logger.debug("estado       :"+estado);
	    	logger.debug("nmPoliza     :"+nmPoliza);
	    	logger.debug("nmSituac     :"+nmSituac);
	    	logger.debug("nmObjeto     :"+nmObjeto);
    	}

    	Map <String,String> param = new HashMap<String,String>();
    	param.put("cdunieco", cdUnieco);
    	param.put("cdramo", cdRamo);
    	param.put("estado", estado);
    	param.put("nmpoliza", nmPoliza);
    	param.put("nmsituac", nmSituac);
    	param.put("nmobjeto", nmObjeto);
    	param.put("cdatribu", "");

    	try{
    		dext = emisionManager.getDatosRolExt(param, "EMISION_OBTIENE_DATOS_ACCESORIOS");
    		logger.debug("dext: " + dext);
    	}catch(ApplicationException ex){
    		if (logger.isDebugEnabled())
    			logger.debug("getPantallaAccesorios EXCEPTION:: " + ex);
    	}

    	success = true;    	
    	return SUCCESS;
    }


    @SuppressWarnings("unchecked")
    public String obtenerAyudaCobertura() throws Exception {


        if (logger.isDebugEnabled()) {
            logger.debug("idA :"+cdUnieco);
            logger.debug("idR :"+cdRamo);
            logger.debug("idG :"+cdGarant);
        }

        Map<String, String> parametersAyudaCobertura = new HashMap<String, String>();
        parametersAyudaCobertura.put("idA",cdUnieco);
        parametersAyudaCobertura.put("idR",cdRamo);
        parametersAyudaCobertura.put("idG",cdGarant);


        ayudaCoberturaVo = new AyudaCoberturaCotizacionVO();
        ayudaCoberturaVo = emisionManager.getAyudaCobertura(parametersAyudaCobertura);
        //session.put("AYUDA_ENCABEZADO", ayudaCoberturaVo.getDsGarant());
        //session.put("AYUDA_LEYENDA", ayudaCoberturaVo.getDsAyuda());
        
        success = true;
        return SUCCESS;
    }
    
    /*
     * Pólizas Maestras
     */
    @SuppressWarnings("unchecked")
    public String obtienePolizaMaestra() throws Exception{
    	
        if (logger.isDebugEnabled()) {
        	logger.debug("store: "+store);
            logger.debug("parametrosBusqueda: "+parametrosBusqueda);
        }
		if(store == null){
			session.put("parametrosBusqueda", parametrosBusqueda);
		}else{
			parametrosBusqueda = (Map<String, String>) session.get("parametrosBusqueda");
		}
		try
		{
			PagedList pagedList = emisionManager.buscaPolizasMaestras(parametrosBusqueda, start, limit);
			listPolizasMaestras = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
	    	return SUCCESS;
		}catch (Exception e){
			success = false;
            mensajeError = e.getMessage();
			return SUCCESS;
		}
    }
    
    @SuppressWarnings("unchecked")
    public String obtenerComboNivel(){
    	try{
    		if(session.containsKey("PMAESTRA_LNIVEL") && session.get("PMAESTRA_LNIVEL")!=null){
    			listaNivel = (List<ClienteCorpoVO>) session.get("PMAESTRA_LNIVEL");
    		}else{
	    		listaNivel = catalogManager.getItemList("OBTIENE_CLIENTES_CORPO_CATALOGS");
	    		session.put("PMAESTRA_LNIVEL", listaNivel);
    		}
    	}catch(Exception ex){
    		logger.debug("getComboNivel EXCEPTION:: " + ex);
    	}
    	success = true;
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtenerComboAseguradora(){
    	try{
    		if(session.containsKey("PMAESTRA_LASEG") && session.get("PMAESTRA_LASEG")!=null){
    			listaAseguradoras = (List<BaseObjectVO>) session.get("PMAESTRA_LASEG");
    		}else{
	    		listaAseguradoras = catalogManager.getItemList("OBTIENE_ASEGURADORAS_CAT");
	    		session.put("PMAESTRA_LASEG", listaAseguradoras);
    		}
    	}
    	catch(Exception ex){
    		logger.debug("getComboAseguradora EXCEPTION:: " + ex);
    	}
    	success = true;
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtenerComboProducto(){
    	try{
    		if(session.containsKey("PMAESTRA_LPROD") && session.get("PMAESTRA_LPROD")!=null){
    			listaProductos = (List<BaseObjectVO>) session.get("PMAESTRA_LPROD");
    		}else{
    			listaProductos = catalogManager.getItemList("OBTIENE_PRODUCTOS_CAT");
    			session.put("PMAESTRA_LPROD", listaProductos);
    		}
    	}
    	catch(Exception ex){
    		logger.debug("getComboProducto EXCEPTION:: " + ex);
    	}
    	success = true;
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtenerComboTipos(){
    	try{
    		if(session.containsKey("PMAESTRA_LTIPOS") && session.get("PMAESTRA_LTIPOS")!=null){
    			listaTipos = (List<BaseObjectVO>) session.get("PMAESTRA_LTIPOS");
    		}else{
	    		Map<String,Object> params = new HashMap<String,Object>();
	    		params.put("cdtabla", "TPOLIMTA");
	    		params.put("cdidioma", 1);
	    		params.put("cdregion", 1);
	    		listaTipos = catalogManager.getItemList("OBTIENE_LISTA_TIPOS",params);
	    		session.put("PMAESTRA_LTIPOS", listaTipos);
    		}
    		success = true;
    	}catch(Exception ex){
    		success = false;
    		logger.debug("getComboTipos EXCEPTION:: " + ex);
    	}
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtenerComboTipoDeSituacion(){
    	logger.debug("obtenerComboTipoDeSituacion cdRamo=" + cdRamo);
    	try{
    		if(session.containsKey("PMAESTRA_LTIPOS_DE_SIT") && session.get("PMAESTRA_LTIPOS_DE_SIT")!=null && listaTipoDeSituacion != null){
    			listaTipoDeSituacion = (List<BaseObjectVO>) session.get("PMAESTRA_LTIPOS_DE_SIT");
    		}else{
	    		listaTipoDeSituacion = catalogManager.getItemList("OBTIENE_TIPO_SITUACION", cdRamo);
	    		session.put("PMAESTRA_LTIPOS_DE_SIT", listaTipoDeSituacion);
    		}
    		logger.debug("listaTipoDeSituacion=" + listaTipoDeSituacion);
    		success = true;
    	}catch(Exception ex){
    		success = false;
    		logger.debug("ComboTipoDeSituacion EXCEPTION:: " + ex);
    	}
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String guardaPolizaMaestra(){
    	try{
            if (logger.isDebugEnabled()) {
            	logger.debug("***guardaPolizaMaestra***");
                logger.debug("parametrosBusqueda: "+parametrosBusqueda);
            }
            if(!parametrosBusqueda.isEmpty() && parametrosBusqueda.containsKey("cdpolmtra")){
                if (logger.isDebugEnabled())
                    logger.debug("guardaPolizaMaestra accion EDITAR");
                
	                List<BaseObjectVO> tipos = (List<BaseObjectVO>) ActionContext.getContext().getSession().get("PMAESTRA_LTIPOS");
	                for(BaseObjectVO t : tipos){
	                    if(t.getLabel().trim().equals(parametrosBusqueda.get("cdtipo").trim())){
	                    	parametrosBusqueda.remove("cdtipo");
	                    	parametrosBusqueda.put("cdtipo", t.getValue());
	                    	break;
	                    }
	                }
	                
	                List<ClienteCorpoVO> nivel = (List<ClienteCorpoVO>) ActionContext.getContext().getSession().get("PMAESTRA_LNIVEL");
	                logger.debug("cdelemento session: *"+parametrosBusqueda.get("cdelemento")+"*");
	                for(ClienteCorpoVO cl : nivel){
	                	logger.debug("cdelemento lista id: "+cl.getCdCliente());
	                	logger.debug("cdelemento lista val: *"+cl.getDsCliente()+"*");
	                    if(cl.getDsCliente().trim().equals(parametrosBusqueda.get("cdelemento").trim())){
	                    	parametrosBusqueda.remove("cdelemento");
	                    	parametrosBusqueda.put("cdelemento", cl.getCdCliente());
	                    	break;
	                    }
	                }
            	
            }else{
                if (logger.isDebugEnabled())
                    logger.debug("guardaPolizaMaestra accion GUARDAR");            	
            }
            
            if (logger.isDebugEnabled())
                logger.debug("parametrosBusqueda: "+parametrosBusqueda);
            
            
            messageResult = polizasManager.guardarPolizaMaestra(parametrosBusqueda, "POLIZAS_MAESTRAS_GUARDAR");
            if (logger.isDebugEnabled()){
            	logger.debug("messageResult guardarPolizaMaestra=" + messageResult);
            }
            // respuestaPL = emisionManager.getAction(parametrosBusqueda, "EMISION_GUARDA_POLIZA_MAESTRA");
    		success = true;
    	}catch(Exception e){
    		success = false;
    		logger.debug("guardaPolizaMaestra EXCEPTION :"+e);
    	}
    	return SUCCESS;
    }

	/**
	 * Obtiene un conjunto de ayudas de cobertura y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Recibos." + exportFormat.getExtension();
			
			TableModelExport model = emisionManager.getModel(dsElemen, dsUnieco, dsRamo);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	
	@SuppressWarnings("unchecked")
	public String obtenerComboAseguradoraPorCliente() {

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pv_cdelemento_i", cdCliente);

			listaAseguradoras = catalogManager.getItemList(
					"OBTIENE_ASEGURADORAS_CLIENTE_CAT", params);
		} catch (Exception ex) {
			logger.debug("getComboAseguradoraPorCliente EXCEPTION:: " + ex);
		}
		success = true;
		return SUCCESS;
	}
	
	 @SuppressWarnings("unchecked")  
	    public String obtenerComboProductoPorAseguradora(){
	    	try{
	    		  Map<String, Object> params = new HashMap<String, Object>();
	    		  params.put("pv_cdunieco_i",cdAsegurado);
	    		
	    	      listaProductos = catalogManager.getItemList("OBTIENE_PRODUCTOS_ASEGURADORA_CAT",params);
	    	}
	    	catch(Exception ex){
	    		logger.debug("getComboProductoPorAseguradora EXCEPTION:: " + ex);
	    	}
	    	success = true;
	    	return SUCCESS;
	    }
	 
	
	public String irAgrupacionPorRolClick() {
		return "agrupacionPapel";
	}
	
	public String irAgrupacionPorGrupoPersonaClick() {
		return "agrupacionGrupoPersonas";
	}    
	
	@SuppressWarnings("unchecked")
    public String cmdIrAsignarNumero() throws Exception{ 
		return "numeroPolizas";
     }
    
	public String irCancelacionManualClick() throws Exception{ 
		return "cancelacionManualPolizas";
     }	
	public String irPolizasCanceladasClick() throws Exception{ 
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		return "polizasCanceladas";
     }
	public String irPolizasARenovarClick() throws Exception{ 
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		return "renovacionPolizas";
     }
	public String irRehabilitacionMasivaClick()throws Exception{
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		return "rehabilitacionMasiva";
	}
	
	
	
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}

	/**
	 * @return the poliza
	 */
	public String getPoliza() {
		return poliza;
	}

	/**
	 * @param poliza the poliza to set
	 */
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	/**
	 * @return the nombreAsegurado
	 */
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	/**
	 * @param nombreAsegurado the nombreAsegurado to set
	 */
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	/**
	 * @return the aseguradora
	 */
	public String getAseguradora() {
		return aseguradora;
	}

	/**
	 * @param aseguradora the aseguradora to set
	 */
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}

	/**
	 * @return the statusPoliza
	 */
	public String getStatusPoliza() {
		return statusPoliza;
	}

	/**
	 * @param statusPoliza the statusPoliza to set
	 */
	public void setStatusPoliza(String statusPoliza) {
		this.statusPoliza = statusPoliza;
	}

	/**
	 * @return the fechaInicioFin
	 */
	public String getFechaInicioFin() {
		return fechaInicioFin;
	}

	/**
	 * @param fechaInicioFin the fechaInicioFin to set
	 */
	public void setFechaInicioFin(String fechaInicioFin) {
		this.fechaInicioFin = fechaInicioFin;
	}

	/**
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	

	/**
	 * @param emisionManager the emisionManager to set
	 */
	public void setEmisionManager(EmisionManager emisionManager) {
		this.emisionManager = emisionManager;
	}

	/**
	 * @return the emisiones
	 */
	public List<EmisionVO> getEmisiones() {
		return emisiones;
	}

	/**
	 * @param emisiones the emisiones to set
	 */
	public void setEmisiones(List<EmisionVO> emisiones) {
		this.emisiones = emisiones;
	}

	/**
	 * @return the inicioVigencia
	 */
	public String getInicioVigencia() {
		return inicioVigencia;
	}

	/**
	 * @param inicioVigencia the inicioVigencia to set
	 */
	public void setInicioVigencia(String inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	/**
	 * @return the finVigencia
	 */
	public String getFinVigencia() {
		return finVigencia;
	}

	/**
	 * @param finVigencia the finVigencia to set
	 */
	public void setFinVigencia(String finVigencia) {
		this.finVigencia = finVigencia;
	}

	/**
	 * @return the cdUnieco
	 */
	public String getCdUnieco() {
		return cdUnieco;
	}

	/**
	 * @param cdUnieco the cdUnieco to set
	 */
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	
	/**
	 * @param catalogManager the catalogManager to set
	 */
	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}

	/**
	 * @return the listProductos
	 */
	@SuppressWarnings("unchecked")
	public List getListProductos() {
		return listProductos;
	}

	/**
	 * @param listProductos the listProductos to set
	 */
	@SuppressWarnings("unchecked")
	public void setListProductos(List listProductos) {
		this.listProductos = listProductos;
	}

	/**
	 * @return the listAseguradoras
	 */
	@SuppressWarnings("unchecked")
	public List getListAseguradoras() {
		return listAseguradoras;
	}

	/**
	 * @param listAseguradoras the listAseguradoras to set
	 */
	@SuppressWarnings("unchecked")
	public void setListAseguradoras(List listAseguradoras) {
		this.listAseguradoras = listAseguradoras;
	}

	/**
	 * @return the listStatusPoliza
	 */
	@SuppressWarnings("unchecked")
	public List getListStatusPoliza() {
		return listStatusPoliza;
	}

	/**
	 * @param listStatusPoliza the listStatusPoliza to set
	 */
	@SuppressWarnings("unchecked")
	public void setListStatusPoliza(List listStatusPoliza) {
		this.listStatusPoliza = listStatusPoliza;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the cdRamo
	 */
	public String getCdRamo() {
		return cdRamo;
	}

	/**
	 * @param cdRamo the cdRamo to set
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	/**
	 * @return the nmPoliza
	 */
	public String getNmPoliza() {
		return nmPoliza;
	}

	/**
	 * @param nmPoliza the nmPoliza to set
	 */
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	/**
	 * @return the polizaDet
	 */
	public List<PolizaDetVO> getPolizaDet() {
		return polizaDet;
	}

	/**
	 * @param polizaDet the polizaDet to set
	 */
	public void setPolizaDet(List<PolizaDetVO> polizaDet) {
		this.polizaDet = polizaDet;
	}

	/**
	 * @return the nmSituac
	 */
	public String getNmSituac() {
		return nmSituac;
	}

	/**
	 * @param nmSituac the nmSituac to set
	 */
	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	/**
	 * @return the objAsegurable
	 */
	public List<ObjetoAsegurableVO> getObjAsegurable() {
		return objAsegurable;
	}

	/**
	 * @param objAsegurable the objAsegurable to set
	 */
	public void setObjAsegurable(List<ObjetoAsegurableVO> objAsegurable) {
		this.objAsegurable = objAsegurable;
	}

	/**
	 * @return the fnPoliza
	 */
	public List<FuncionPolizaVO> getFnPoliza() {
		return fnPoliza;
	}

	/**
	 * @param fnPoliza the fnPoliza to set
	 */
	public void setFnPoliza(List<FuncionPolizaVO> fnPoliza) {
		this.fnPoliza = fnPoliza;
	}

	/**
	 * @return the totalCountFnPol
	 */
	public int getTotalCountFnPol() {
		return totalCountFnPol;
	}

	/**
	 * @param totalCountFnPol the totalCountFnPol to set
	 */
	public void setTotalCountFnPol(int totalCountFnPol) {
		this.totalCountFnPol = totalCountFnPol;
	}

	/**
	 * @return the listRecibos
	 */
	public List<RecibosVO> getListRecibos() {
		return listRecibos;
	}

	/**
	 * @param listRecibos the listRecibos to set
	 */
	public void setListRecibos(List<RecibosVO> listRecibos) {
		this.listRecibos = listRecibos;
	}

	/**
	 * @return the itemsPantalla
	 */
	public String getItemsPantalla() {
		return itemsPantalla;
	}

	/**
	 * @param itemsPantalla the itemsPantalla to set
	 */
	public void setItemsPantalla(String itemsPantalla) {
		this.itemsPantalla = itemsPantalla;
	}

	/**
	 * @return the datAdicionales
	 */
	public List<DatosAdicionalesVO> getDatAdicionales() {
		return datAdicionales;
	}

	/**
	 * @param datAdicionales the datAdicionales to set
	 */
	public void setDatAdicionales(List<DatosAdicionalesVO> datAdicionales) {
		this.datAdicionales = datAdicionales;
	}
	
	/**
	 * @return the dext
	 */
	public ArrayList<TextFieldControl> getDext() {
		return dext;
	}

	/**
	 * @param dext the dext to set
	 */
	public void setDext(ArrayList<TextFieldControl> dext) {
		this.dext = dext;
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
	 * @return the cdInciso
	 */
	public String getCdInciso() {
		return cdInciso;
	}

	/**
	 * @param cdInciso the cdInciso to set
	 */
	public void setCdInciso(String cdInciso) {
		this.cdInciso = cdInciso;
	}

	/**
	 * @return the dsDescripcion
	 */
	public String getDsDescripcion() {
		return dsDescripcion;
	}

	/**
	 * @param dsDescripcion the dsDescripcion to set
	 */
	public void setDsDescripcion(String dsDescripcion) {
		this.dsDescripcion = dsDescripcion;
	}

	/**
	 * @return the listCoberturasIncisos
	 */
	public List<IncisosVO> getListCoberturasIncisos() {
		return listCoberturasIncisos;
	}

	/**
	 * @param listCoberturasIncisos the listCoberturasIncisos to set
	 */
	public void setListCoberturasIncisos(List<IncisosVO> listCoberturasIncisos) {
		this.listCoberturasIncisos = listCoberturasIncisos;
	}

	/**
	 * @return the listAccesoriosIncisos
	 */
	public List<BaseObjectVO> getListAccesoriosIncisos() {
		return listAccesoriosIncisos;
	}

	/**
	 * @param listAccesoriosIncisos the listAccesoriosIncisos to set
	 */
	public void setListAccesoriosIncisos(List<BaseObjectVO> listAccesoriosIncisos) {
		this.listAccesoriosIncisos = listAccesoriosIncisos;
	}

	/**
	 * @return the dextWin
	 */
	public ArrayList<TextFieldControl> getDextWin() {
		return dextWin;
	}

	/**
	 * @param dextWin the dextWin to set
	 */
	public void setDextWin(ArrayList<TextFieldControl> dextWin) {
		this.dextWin = dextWin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cdGarant
	 */
	public String getCdGarant() {
		return cdGarant;
	}

	/**
	 * @param cdGarant the cdGarant to set
	 */
	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}

	/**
	 * @return the nmObjeto
	 */
	public String getNmObjeto() {
		return nmObjeto;
	}

	/**
	 * @param nmObjeto the nmObjeto to set
	 */
	public void setNmObjeto(String nmObjeto) {
		this.nmObjeto = nmObjeto;
	}

	/**
	 * @return the listMpoliagr
	 */
	public List<MpoliagrVO> getListMpoliagr() {
		return listMpoliagr;
	}

	/**
	 * @param listMpoliagr the listMpoliagr to set
	 */
	public void setListMpoliagr(List<MpoliagrVO> listMpoliagr) {
		this.listMpoliagr = listMpoliagr;
	}

	/**
	 * @return the nmRecibo
	 */
	public String getNmRecibo() {
		return nmRecibo;
	}

	/**
	 * @param nmRecibo the nmRecibo to set
	 */
	public void setNmRecibo(String nmRecibo) {
		this.nmRecibo = nmRecibo;
	}

	/**
	 * @return the ayudaCoberturaVo
	 */
	public AyudaCoberturaCotizacionVO getAyudaCoberturaVo() {
		return ayudaCoberturaVo;
	}

	/**
	 * @param ayudaCoberturaVo the ayudaCoberturaVo to set
	 */
	public void setAyudaCoberturaVo(AyudaCoberturaCotizacionVO ayudaCoberturaVo) {
		this.ayudaCoberturaVo = ayudaCoberturaVo;
	}

	/**
	 * @return the listPolizasMaestras
	 */
	public List<PolizaMaestraVO> getListPolizasMaestras() {
		return listPolizasMaestras;
	}

	/**
	 * @param listPolizasMaestras the listPolizasMaestras to set
	 */
	public void setListPolizasMaestras(List<PolizaMaestraVO> listPolizasMaestras) {
		this.listPolizasMaestras = listPolizasMaestras;
	}

	/**
	 * @return the parametrosBusqueda
	 */
	public Map<String, String> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	/**
	 * @param parametrosBusqueda the parametrosBusqueda to set
	 */
	public void setParametrosBusqueda(Map<String, String> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	/**
	 * @return the listaNivel
	 */
	public List<ClienteCorpoVO> getListaNivel() {
		return listaNivel;
	}

	/**
	 * @param listaNivel the listaNivel to set
	 */
	public void setListaNivel(List<ClienteCorpoVO> listaNivel) {
		this.listaNivel = listaNivel;
	}

	/**
	 * @return the listaAseguradoras
	 */
	public List<BaseObjectVO> getListaAseguradoras() {
		return listaAseguradoras;
	}

	/**
	 * @param listaAseguradoras the listaAseguradoras to set
	 */
	public void setListaAseguradoras(List<BaseObjectVO> listaAseguradoras) {
		this.listaAseguradoras = listaAseguradoras;
	}

	/**
	 * @return the listaProductos
	 */
	public List<BaseObjectVO> getListaProductos() {
		return listaProductos;
	}

	/**
	 * @param listaProductos the listaProductos to set
	 */
	public void setListaProductos(List<BaseObjectVO> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<BaseObjectVO> getListaTipoDeSituacion() {
		return listaTipoDeSituacion;
	}

	public void setListaTipoDeSituacion(List<BaseObjectVO> listaTipoDeSituacion) {
		this.listaTipoDeSituacion = listaTipoDeSituacion;
	}

	/**
	 * @return the listaTipos
	 */
	public List<BaseObjectVO> getListaTipos() {
		return listaTipos;
	}

	/**
	 * @param listaTipos the listaTipos to set
	 */
	public void setListaTipos(List<BaseObjectVO> listaTipos) {
		this.listaTipos = listaTipos;
	}

	/**
	 * @return the respuestaPL
	 */
	public String getRespuestaPL() {
		return respuestaPL;
	}

	/**
	 * @param respuestaPL the respuestaPL to set
	 */
	public void setRespuestaPL(String respuestaPL) {
		this.respuestaPL = respuestaPL;
	}

	/**
	 * @return the polizaMaestraReg
	 */
	public PolizaMaestraVO getPolizaMaestraReg() {
		return polizaMaestraReg;
	}

	/**
	 * @param polizaMaestraReg the polizaMaestraReg to set
	 */
	public void setPolizaMaestraReg(PolizaMaestraVO polizaMaestraReg) {
		this.polizaMaestraReg = polizaMaestraReg;
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



	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

	public String getCdPolMtra() {
		return cdPolMtra;
	}

	public void setCdPolMtra(String cdPolMtra) {
		this.cdPolMtra = cdPolMtra;
	}

	public String getCodigoConfiguracion() {
		return codigoConfiguracion;
	}

	public void setCodigoConfiguracion(String codigoConfiguracion) {
		this.codigoConfiguracion = codigoConfiguracion;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCveAgrupa() {
		return cveAgrupa;
	}

	public void setCveAgrupa(String cveAgrupa) {
		this.cveAgrupa = cveAgrupa;
	}

	public String getCodigoAgrupacion() {
		return codigoAgrupacion;
	}

	public void setCodigoAgrupacion(String codigoAgrupacion) {
		this.codigoAgrupacion = codigoAgrupacion;
	}

	public String getFeEfecto() {
		return feEfecto;
	}

	public void setFeEfecto(String feEfecto) {
		this.feEfecto = feEfecto;
	}

	public String getFeVencim() {
		return feVencim;
	}

	public void setFeVencim(String feVencim) {
		this.feVencim = feVencim;
	}

	public String getVolver1() {
		return volver1;
	}

	public void setVolver1(String volver1) {
		this.volver1 = volver1;
	}

	public String getCdUniAge() {
		return cdUniAge;
	}

	public void setCdUniAge(String cdUniAge) {
		this.cdUniAge = cdUniAge;
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

	public String getCdCia() {
		return cdCia;
	}

	public void setCdCia(String cdCia) {
		this.cdCia = cdCia;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

	public String getNmPoliEx() {
		return nmPoliEx;
	}

	public void setNmPoliEx(String nmPoliEx) {
		this.nmPoliEx = nmPoliEx;
	}

	public String getFeInicio() {
		return feInicio;
	}

	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}

	public String getFeFin() {
		return feFin;
	}

	public void setFeFin(String feFin) {
		this.feFin = feFin;
	}

	public String getCdNumPol() {
		return cdNumPol;
	}

	public void setCdNumPol(String cdNumPol) {
		this.cdNumPol = cdNumPol;
	}

	public void setPolizasManager(PolizasManager polizasManager) {
		this.polizasManager = polizasManager;
	}

	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}

	public String getCdCliente() {
		return cdCliente;
	}

	public void setCdAsegurado(String cdAsegurado) {
		this.cdAsegurado = cdAsegurado;
	}

	public String getCdAsegurado() {
		return cdAsegurado;
	}

	public String getMessageResult() {
		return messageResult;
	}

	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}

	public String getVolver4() {
		return volver4;
	}

	public void setVolver4(String volver4) {
		this.volver4 = volver4;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}

	public String getPantallaOrigen() {
		return pantallaOrigen;
	}

	public void setPantallaOrigen(String pantallaOrigen) {
		this.pantallaOrigen = pantallaOrigen;
	}

	public void setPlzacancelada(String plzacancelada) {
		this.plzacancelada = plzacancelada;
	}

	public String getPlzacancelada() {
		return plzacancelada;
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