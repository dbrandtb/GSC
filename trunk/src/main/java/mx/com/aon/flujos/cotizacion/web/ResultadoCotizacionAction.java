/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

/**
 * 
 * Clase Action para el control y visualizacion de datos de la pantalla de resultados del proceso de cotización
 * 
 * @author aurora.lozada
 * 
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.MessagingException;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.catbo.model.AseguradoraVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
import mx.com.aon.configurador.pantallas.model.components.BaseParamVO;
import mx.com.aon.configurador.pantallas.model.components.ColumnGridVO;
import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.configurador.pantallas.model.components.ItemVO;
import mx.com.aon.configurador.pantallas.model.components.RecordVO;
import mx.com.aon.configurador.pantallas.model.components.StoreVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.ice.services.business.ServiciosGeneralesSistema;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.TextFieldControl;

public class ResultadoCotizacionAction extends PrincipalCotizacionAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2407707000895607658L;

    public static final String RECORD_STORE = "RECORD_STORE";
    public static final String COLUMN_GRID = "COLUMN_GRID";
    public static final String DATOS_GRID = "DATOS_GRID";
    public static final String PARAMETROS_RESULTADO = "PARAMETROS_RESULTADO";

    private CotizacionService cotizacionManager;
    private ServiciosGeneralesSistema serviciosGeneralesSistema;
    private CotizacionPrincipalManager cotizacionManagerJdbcTemplate;
    
    /**
     * Servicio inyectado por spring para llamar a los servicios de valores por defecto del kernel
     */
    private KernelManager kernelManager;

    private boolean success;

    private String id;

    
    private List <ConjuntoPantallaVO> dataGrid;

    private List<CoberturaCotizacionVO> coberturasResult = new ArrayList<CoberturaCotizacionVO>();

    private AyudaCoberturaCotizacionVO ayudaCoberturaVo;

    private GridVO gridResultado;

    private JSONArray registroList = new JSONArray();

    private JSONArray dataResult = new JSONArray();
    
    private String botonGuardaCotizacion;
    private String urlComprarDesdeConsulta;
    private String botonNuevaCotizacion;
    private String botonRegresaCotizacion;
    private String datosImpresion1;
    private String datosImpresion2;
    private String clicBotonRegresaComprar;

    private String cdIdentifica;
    private String cdUnieco;
    private String cdCiaaseg;
    private String ciaAseg;
    private String dsUnieco;
    private String cdPerpag;
    private String cdRamo;
    private String dsPerpag;
    private String cdPlan;
    private String numeroSituacion;
    private String numIdentificador;
    
    private String paramsEntradaCotizacion;
    
    private String numeroPoliza;
    
	private String correo;
	private String CC;
	private String comentario;
    private String adjunto;
    
    /**
     * Propiedad que contiene un mensaje de validacion si hubo un error
     */
    private String mensajeValidacion;
    
    /**
     * @author augusto.perez
     * @param void
     * @return void
     * 
     * Método que carga en session el mapa 'namesTextFieldsResultadoCotizacion' que 
     * contiene las propiedades 'name' (como llave) y 'fieldLabel' (como valor) 
     * de los campos que aparecen en la pantalla de Cotización.
     */
    @SuppressWarnings("unchecked")
    private void cargaNamesCapturaCotizacion() throws Exception {
    	logger.debug(">>> Entrando a método cargaNamesCapturaCotizacion");
    	
    	String CP = (String) session.get("CAPTURA_PANTALLA");
    	boolean bandera = true;
    	Map<String,String> names = new HashMap<String,String>();
    	
    	while(bandera){
    		int i = StringUtils.indexOf( CP, "\"name\":\"parameters." );
    		if ( i != -1 ) {
    			int j = StringUtils.lastIndexOf( StringUtils.substring( CP, 0, i ), "\"fieldLabel\":\"" );
    			int fin = StringUtils.indexOf( StringUtils.substring( CP, i + "\"name\":\"".length() + 1 ) , "\"" );
    			String name = StringUtils.substring( CP, i + "\"name\":\"".length(), i + "\"name\":\"".length() + fin + 1 );

    			fin = StringUtils.indexOf( StringUtils.substring( CP, j + "\"fieldLabel\":\"".length() + 1 ) , "\"" );
    			String fieldLabel = StringUtils.substring( CP , j + "\"fieldLabel\":\"".length(), j + "\"fieldLabel\":\"".length() + fin + 1 );

    			logger.debug("/// name = " + name);
    			logger.debug("/// fieldLabel = " + fieldLabel);
    			
    			names.put( name, fieldLabel );

    			CP = StringUtils.substring( CP, i + "\"name\":\"".length() + name.length() + "\",".length() );
    		} else {
    			bandera = false;
    		}
    	}
    	
    	session.put("namesTextFieldsResultadoCotizacion", (Map<String,String>)names);

    	logger.debug("<<< Saliendo a método cargaNamesCapturaCotizacion");
    	
    	return;
    }
    
    /**
     * @author augusto.perez
     * @param name - Cadena con la propiedad 'name' del textfield que aparece en la pantalla 'Resultados de Cotización'
     * @return fieldLabel - Cadena con la propiedad 'fieldLabel' correspondiente al establecido en el módulo 'Configurador de Pantallas'.
     * 
     * Método que obtiene del mapa de session 'namesTextFieldsResultadoCotizacion' el fieldLabel que se ha establecido
     * en el módulo 'Configurador de Pantallas'. Si no se encuentra en el mapa la propiedad 'name' de dicho campo,
     * entonces se devuelve la cadena vacía.
     */
    @SuppressWarnings("unchecked")
    private String obtieneFieldLabelTextFieldsResultadoCotizacion( String name ) {
    	String fieldLabel = "";
    	
    	Map<String,String> names = (Map<String,String>) session.get("namesTextFieldsResultadoCotizacion");
    	if ( names.containsKey( name ) ) {
    		return names.get( name );
    	}
    	
    	return fieldLabel;
    }
    
    
    
    /**
     * Metodo que se Separó del Metodo entrar para realizar las validaciones independientemente
     * 
     * @return Cadena INPUT
     */
    @SuppressWarnings("unchecked")
    public String validaCamposEntrada() throws Exception {

    	logger.debug("### METODO validaCampos en ResultadoCotizacionAction...");
    	
    	boolean isDetalleCotizacion = false;
    	Map<String, String> detalleCotizacion = new HashMap<String, String>();
    	List<DatosEntradaCotizaVO> listaDatosEntradaCotiza = null;
    	logger.debug("session.containsKey(DETALLE_COTIZACION)=" + session.containsKey( "DETALLE_COTIZACION" ));
    	if ( session.containsKey( "DETALLE_COTIZACION" ) )
    		isDetalleCotizacion = true;
    	logger.debug(">> isDetalleCotizacion = " + isDetalleCotizacion);

        UserVO usuario = (UserVO) session.get("USUARIO");
    	logger.debug("CONTAIN GLOBAL: " + session.containsKey("GLOBAL_VARIABLE_CONTAINER"));

    	GlobalVariableContainerVO globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");

    	if ( session.containsKey( "DETALLE_COTIZACION" ) ) {

			detalleCotizacion = ( Map<String, String> ) session.get( "DETALLE_COTIZACION" );
        	/*
        	 * listaDatosEntradaCotiza servirá para obtener la lista de los campos de entrada originalmente 
        	 * en la cotización guardada
        	 */
        	Map<String, String> paramsCotiza = new HashMap<String, String>();
            paramsCotiza.put( "pv_cdunieco_i", (String) detalleCotizacion.get("cdunieco") );
            paramsCotiza.put( "pv_cdramo_i", (String) detalleCotizacion.get("cdramo") );
            paramsCotiza.put( "pv_estado_i", (String) detalleCotizacion.get("estado") );
            paramsCotiza.put( "pv_nmpoliza_i", (String) detalleCotizacion.get("nmpoliza") );
            listaDatosEntradaCotiza = kernelManager.getDatosEntradaCotiza(paramsCotiza);
        	/*
        	 * parameters servirá para subir a session COTIZACION_INPUT 
        	 */
        	Map<String, String> parameters = new HashMap<String, String>();
        	for ( DatosEntradaCotizaVO decVO : listaDatosEntradaCotiza  ) {
        	/*
 			* Se agrega a parameters los campos con sus respectivos valores de la forma
 			* 	parameters.put( "id-del-campo", "valor" ); 
 			*/
        		parameters.put( decVO.getDsNombre(), decVO.getOtValor() );
        	}
        	/* 
        	 * Se agrega COTIZACION_INPUT en la variable de session
        	 */
        	if ( parameters.size() == 0 ) {
        		logger.error("NO se puede continuar porque COTIZACION_INPUT esta vacio");
        		session.remove("DETALLE_COTIZACION");
        		success = false;
        		return ERROR;
        	} else {
        		session.put( "COTIZACION_INPUT", parameters );
        		logger.debug("COTIZACION_INPUT = " + session.get("COTIZACION_INPUT") );
        	}
        }
    	
    	if ( isDetalleCotizacion ) {
       		// VALIDACION en caso de que se esté solicitando este método desde ConsultaCotizacionAction.java
   			mensajeValidacion = null;
   			botonGuardaCotizacion = "N";
   			botonNuevaCotizacion = "N";
   			botonRegresaCotizacion = "S";
   			urlComprarDesdeConsulta = 
   				"&cdtipsit="+ detalleCotizacion.get("cdtipsit") +
   				"&estado="+ detalleCotizacion.get("estado") +
   				"&nmPoliza="+ detalleCotizacion.get("nmpoliza") +
   				"&procesoOrigen="+ "PROCESO_CONSULTA_COTIZACION";
   			
    	} else if ( StringUtils.isNotBlank(clicBotonRegresaComprar) ) {
    		mensajeValidacion = null;
   			botonGuardaCotizacion = "S";
   			botonNuevaCotizacion = "S";
   			botonRegresaCotizacion = "N";
   			urlComprarDesdeConsulta = ""; 
    		logger.debug("clicBotonRegresaComprar = " + clicBotonRegresaComprar);
    	} else {
    		//	EJECUTA VALIDACIONES DEL PRODUCTO
    		try{
    			globalVarVo = kernelManager.ejecutaValidaciones(ServletActionContext.getRequest().getSession().getId(), (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER"), (Map<String, String>) session.get("COTIZACION_INPUT"),  usuario);
   			} catch(mx.com.ice.services.exception.ApplicationException appExcKernel){
    			mensajeValidacion = appExcKernel.getMessage();
    			logger.error("Error al ejecutar validaciones de producto: " + appExcKernel.toString(), appExcKernel);
    			logger.debug("mensajeValidacion ERROR:" + mensajeValidacion);
    			success = false;
    			return ERROR;
    		}
   			
   			logger.debug("mensajeValidacion = " + mensajeValidacion);
	    	  
    	}
        success = true;
        return INPUT;
    }
    
    
    /**
     * Metodo que carga valores en sesion para el despliegue del grid: store, columns y datos resultado
     * 
     * @return Cadena INPUT
     */
    @SuppressWarnings("unchecked")
    public String entrar() throws Exception {

    	logger.debug("### METODO entrar en ResultadoCotizacionAction...");
    	
    	boolean isDetalleCotizacion = false;
    	Map<String, String> detalleCotizacion = new HashMap<String, String>();
    	List<DatosEntradaCotizaVO> listaDatosEntradaCotiza = null;
    	logger.debug("session.containsKey(DETALLE_COTIZACION)=" + session.containsKey( "DETALLE_COTIZACION" ));
    	if ( session.containsKey( "DETALLE_COTIZACION" ) )
    		isDetalleCotizacion = true;
    	logger.debug(">> isDetalleCotizacion = " + isDetalleCotizacion);

        UserVO usuario = (UserVO) session.get("USUARIO");
    	logger.debug("CONTAIN GLOBAL: " + session.containsKey("GLOBAL_VARIABLE_CONTAINER"));

    	GlobalVariableContainerVO globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");

    	if ( session.containsKey( "DETALLE_COTIZACION" ) ) {

			detalleCotizacion = ( Map<String, String> ) session.get( "DETALLE_COTIZACION" );
        	/*
        	 * listaDatosEntradaCotiza servirá para obtener la lista de los campos de entrada originalmente 
        	 * en la cotización guardada
        	 */
        	Map<String, String> paramsCotiza = new HashMap<String, String>();
            paramsCotiza.put( "pv_cdunieco_i", (String) detalleCotizacion.get("cdunieco") );
            paramsCotiza.put( "pv_cdramo_i", (String) detalleCotizacion.get("cdramo") );
            paramsCotiza.put( "pv_estado_i", (String) detalleCotizacion.get("estado") );
            paramsCotiza.put( "pv_nmpoliza_i", (String) detalleCotizacion.get("nmpoliza") );
            listaDatosEntradaCotiza = kernelManager.getDatosEntradaCotiza(paramsCotiza);
        	/*
        	 * parameters servirá para subir a session COTIZACION_INPUT 
        	 */
        	Map<String, String> parameters = new HashMap<String, String>();
        	for ( DatosEntradaCotizaVO decVO : listaDatosEntradaCotiza  ) {
        	/*
 			* Se agrega a parameters los campos con sus respectivos valores de la forma
 			* 	parameters.put( "id-del-campo", "valor" ); 
 			*/
        		parameters.put( decVO.getDsNombre(), decVO.getOtValor() );
        	}
        	/* 
        	 * Se agrega COTIZACION_INPUT en la variable de session
        	 */
        	if ( parameters.size() == 0 ) {
        		logger.error("NO se puede continuar porque COTIZACION_INPUT esta vacio");
        		session.remove("DETALLE_COTIZACION");
        		return ERROR;
        	} else {
        		session.put( "COTIZACION_INPUT", parameters );
        		logger.debug("COTIZACION_INPUT = " + session.get("COTIZACION_INPUT") );
        	}
        }
    	
    	if ( isDetalleCotizacion ) {
       		// VALIDACION en caso de que se esté solicitando este método desde ConsultaCotizacionAction.java
   			mensajeValidacion = null;
   			botonGuardaCotizacion = "N";
   			botonNuevaCotizacion = "N";
   			botonRegresaCotizacion = "S";
   			urlComprarDesdeConsulta = 
   				"&cdtipsit="+ detalleCotizacion.get("cdtipsit") +
   				"&estado="+ detalleCotizacion.get("estado") +
   				"&nmPoliza="+ detalleCotizacion.get("nmpoliza") +
   				"&procesoOrigen="+ "PROCESO_CONSULTA_COTIZACION";
   			
    	} else if ( StringUtils.isNotBlank(clicBotonRegresaComprar) ) {
    		mensajeValidacion = null;
   			botonGuardaCotizacion = "S";
   			botonNuevaCotizacion = "S";
   			botonRegresaCotizacion = "N";
   			urlComprarDesdeConsulta = ""; 
    		logger.debug("clicBotonRegresaComprar = " + clicBotonRegresaComprar);
    	} else {
    		/*//	EJECUTA VALIDACIONES DEL PRODUCTO
    		try{
    			globalVarVo = kernelManager.ejecutaValidaciones(ServletActionContext.getRequest().getSession().getId(), (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER"), (Map<String, String>) session.get("COTIZACION_INPUT"),  usuario);
   			} catch(mx.com.ice.services.exception.ApplicationException appExcKernel){
    			mensajeValidacion = appExcKernel.getMessage();
    			logger.error("Error al ejecutar validaciones de producto: " + appExcKernel.toString(), appExcKernel);
    			logger.debug("mensajeValidacion ERROR:" + mensajeValidacion);
    			success = false;
    			return ERROR;
    		}
    		
    		logger.debug("mensajeValidacion = " + mensajeValidacion);
    		*/
	    	
	       	botonGuardaCotizacion = "S";
	       	botonNuevaCotizacion = "S";
	       	botonRegresaCotizacion = "N";
	       	urlComprarDesdeConsulta = "";   
    	}

    	logger.debug("### OBTENER datos del grid........");

    	Map<String, String> parametersResultado = new HashMap<String, String>();

        if ( session.get("COTIZACION_INPUT") != null ) {
        	//TODO: cambiar valores fijos
        	 parametersResultado = (Map<String, String>) session.get("COTIZACION_INPUT");
             parametersResultado.put( "pv_cdusuari_i", usuario.getUser());
             parametersResultado.put( "pv_cdunieco_i", "1");
             parametersResultado.put( "pv_cdramo_i", isDetalleCotizacion ? detalleCotizacion.get("cdramo") : globalVarVo.getValueVariableGlobal("vg.CdRamo") );
             parametersResultado.put( "pv_estado_i",	"W");
             parametersResultado.put( "pv_nmpoliza_i", isDetalleCotizacion ? detalleCotizacion.get("nmpoliza") : globalVarVo.getValueVariableGlobal("vg.NmPoliza") );
             parametersResultado.put( "pv_cdelemen_i", usuario.getEmpresa().getElementoId());
             parametersResultado.put( "pv_cdtipsit_i", isDetalleCotizacion ? detalleCotizacion.get("cdtipsit") :  globalVarVo.getValueVariableGlobal("vg.CdTipSit") );
             
             logger.debug(">>> CD_RAMO " + parametersResultado.get("pv_cdramo_i")); /////// QUITAR
             logger.debug(">>> NMPOLIZA " + parametersResultado.get("pv_nmpoliza_i")); /////// QUITAR
             logger.debug(">>> CD_TIPSIT " + parametersResultado.get("pv_cdtipsit_i")); /////// QUITAR
             
             if ( StringUtils.isNotBlank(clicBotonRegresaComprar) ) {
            	 gridResultado = (GridVO)session.get("gridResultado");
             } else {
            	 gridResultado = cotizacionManagerJdbcTemplate.getResultados(parametersResultado);
            	 session.put("gridResultado", gridResultado);
             }
        
             List<RecordVO> itemListStore = new ArrayList<RecordVO>();
             List<ColumnGridVO> itemListColumn = new ArrayList<ColumnGridVO>();
        
             itemListStore = gridResultado.getRecordList();
             itemListColumn = gridResultado.getColumnList();
       
             if(itemListStore != null && itemListColumn != null){
            	 logger.debug("itemListStore size = " + itemListStore.size());
                 logger.debug("itemListColumn size = " + itemListColumn.size());
             }
             
             datosImpresion2 = "";
             if(itemListColumn != null){
            	for (ColumnGridVO cgvo : itemListColumn) {
					if (!cgvo.isHidden()) {
						datosImpresion2 += cgvo.getHeader() + ","
								+ cgvo.getDataIndex() + "|";
					}
				}
             }

             ///////////////// quitar store ////////////////////////////
             List<StoreVO> storeControList = new ArrayList<StoreVO>();
        
             ////////Records del Store Grid/////////////////
        
             List<RecordVO> listaRecordsGrid = new ArrayList<RecordVO>();
             listaRecordsGrid.add(new RecordVO("cdIdentifica","string","cdIdentifica"));
             listaRecordsGrid.add(new RecordVO("cdUnieco","string","cdUnieco"));
             listaRecordsGrid.add(new RecordVO("cdRamo","string","cdRamo"));
             listaRecordsGrid.add(new RecordVO("cdCiaaseg","string","cdCiaaseg"));
             listaRecordsGrid.add(new RecordVO("dsUnieco","string","dsUnieco"));
             listaRecordsGrid.add(new RecordVO("cdPerpag","string","cdPerpag"));
             listaRecordsGrid.add(new RecordVO("dsPerpag","string","dsPerpag"));
             listaRecordsGrid.add(new RecordVO("feEmisio","string","feEmisio"));
             listaRecordsGrid.add(new RecordVO("feVencim","string","feVencim"));
             listaRecordsGrid.add(new RecordVO("cdPlan","string","cdPlan"));
             listaRecordsGrid.add(new RecordVO("dsPlan","string","dsPlan"));
             listaRecordsGrid.add(new RecordVO("numeroSituacion","string","numeroSituacion"));
             if(itemListStore != null){
            	 listaRecordsGrid.addAll(itemListStore);
             }
 
             ////////Records del Store registro/////////////////
             List<RecordVO> listaRecordsRegistro = new ArrayList<RecordVO>();
             listaRecordsRegistro.add(new RecordVO("marca","string","labelMarca"));
             listaRecordsRegistro.add(new RecordVO("modelo","string","labelModelo"));
             listaRecordsRegistro.add(new RecordVO("descripcion","string","labelDescripcion"));
             listaRecordsRegistro.add(new RecordVO("otclave1","string","hmarca"));
             listaRecordsRegistro.add(new RecordVO("otclave2","string","hmodelo"));
             listaRecordsRegistro.add(new RecordVO("otclave3","string","hdescripcion"));
        
             ////////Records del Store Coberturas/////////////////
             List<RecordVO> listaRecordsCoberturas = new ArrayList<RecordVO>();
             listaRecordsCoberturas.add(new RecordVO("cdGarant","string","cdGarant"));
             listaRecordsCoberturas.add(new RecordVO("dsGarant","string","dsGarant"));
             listaRecordsCoberturas.add(new RecordVO("sumaAsegurada","string","sumaAsegurada"));
             listaRecordsCoberturas.add(new RecordVO("deducible","string","deducible"));
             listaRecordsCoberturas.add(new RecordVO("cdCiaaseg","string","cdCiaaseg"));
             listaRecordsCoberturas.add(new RecordVO("cdRamo","string","cdRamo"));
        
             List<BaseParamVO> listaParametros = new ArrayList<BaseParamVO>();
             listaParametros.add(new BaseParamVO("otclave1"," "));
             listaParametros.add(new BaseParamVO("otclave2"," "));
             listaParametros.add(new BaseParamVO("otclave3"," "));
             listaParametros.add(new BaseParamVO("ciaAseg"," "));
             listaParametros.add(new BaseParamVO("cdPlan"," "));
             listaParametros.add(new BaseParamVO("cdRamo"," "));
             listaParametros.add(new BaseParamVO("feEmisio"," "));
             listaParametros.add(new BaseParamVO("feVencim"," "));
             listaParametros.add(new BaseParamVO("dsPlan"," "));
             listaParametros.add(new BaseParamVO("numeroSituacion"," "));

             //**********************************   INICIA GRID   *************************************
             StoreVO storeGrid = new StoreVO("storeGrid", "Ext.data.GroupingStore", "flujocotizacion/obtenerResultados.action", "dataResult", "cdIdentifica", listaRecordsGrid, true, null, null, "cdCiaaseg", "ASC", "dsUnieco");
             storeControList.add(storeGrid);
             logger.debug("### storeGrid...        " + storeGrid);

             StoreVO storeCoberturas = new StoreVO("storeCoberturas", "Ext.data.Store", "flujocotizacion/obtenerCoberturas.action", "coberturasResult", "cdGarant", listaRecordsCoberturas, true, null, listaParametros, "cdGarant", "DESC", null);
             storeControList.add(storeCoberturas);
             logger.debug("### storeDescripcion... " + storeCoberturas);

             StoreVO storeRegistro = new StoreVO("storeRegistro", "Ext.data.Store", "flujocotizacion/obtenerDatosRegistro.action", "registroList", "marca", listaRecordsRegistro, true, null, null, null, null, null);
             storeControList.add(storeRegistro);
             logger.debug("### storeRegistro...    " + storeRegistro);

             session.put("STORES_CONTROL_RESULT", storeControList);
             
             if ( logger.isDebugEnabled() ) {
            	 logger.debug(">>> itemListColumn = "+ itemListColumn);
             }
             
             session.put(COLUMN_GRID, itemListColumn);
             logger.debug("COLUMN_GRID = " + session.get(COLUMN_GRID));
        
             session.put(DATOS_GRID, gridResultado);
           //*******************************   FIN ****************************************************
             
             String _CDRAMO = isDetalleCotizacion ? detalleCotizacion.get("cdramo") : globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
             String _CDTIPSIT = isDetalleCotizacion ? detalleCotizacion.get("cdtipsit") : globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
             
             paramsEntradaCotizacion = "?CDRAMO="+ _CDRAMO +
             "&CDTIPSIT=" + _CDTIPSIT +
             "&usuarioLogueado=" + usuario.getUser() +
             "&rolActivo="+ usuario.getRolActivo().getObjeto().getValue() +
             "&cdElemento="+ usuario.getEmpresa().getElementoId();
             logger.debug(">>> paramsEntradaCotizacion = " + paramsEntradaCotizacion);
             
             session.put("BACK",paramsEntradaCotizacion);
        }

        // Datos de entrada elegidos por el usuario:
        List<TextFieldControl> textFields = new ArrayList<TextFieldControl>();
        GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER");
       	Map<String, String> paramsCotiza = new HashMap<String, String>();
       	paramsCotiza.put( "pv_cdunieco_i", isDetalleCotizacion ? detalleCotizacion.get("cdunieco") : globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()) );
       	paramsCotiza.put("pv_cdramo_i", isDetalleCotizacion ? detalleCotizacion.get("cdramo") : globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()) );
       	paramsCotiza.put("pv_estado_i", isDetalleCotizacion ? detalleCotizacion.get("estado") : globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
       	paramsCotiza.put("pv_nmpoliza_i", isDetalleCotizacion ? detalleCotizacion.get("nmpoliza") : globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
       	
        if ( StringUtils.isNotBlank(clicBotonRegresaComprar) ) {
        	listaDatosEntradaCotiza = (List<DatosEntradaCotizaVO>)session.get("listaDatosEntradaCotiza");
        } else {
        	listaDatosEntradaCotiza = kernelManager.getDatosEntradaCotiza(paramsCotiza);
       	 	session.put("listaDatosEntradaCotiza", listaDatosEntradaCotiza);
        }

       	// Se carga en session el mapa 'namesTextFieldsResultadoCotizacion' con las propiedades 'name' y 'fieldLabel'
       	// de los campos que aparecen en la pantalla de 'Captura de Cotización'.
       	cargaNamesCapturaCotizacion();
       	
       	datosImpresion1 = "";
        for (DatosEntradaCotizaVO entrada : listaDatosEntradaCotiza) {
        	TextFieldControl textField = new TextFieldControl();
        	textField.setDisabled(true);
        	textField.setLabelSeparator(":");
        	textField.setXtype("textfield");
        	textField.setWidth(200);
        	textField.setValue(entrada.getDsValor());
        	textField.setName(entrada.getDsNombre());
        	textField.setFieldLabel(entrada.getDsAtribu());
        	// Se obtiene el fieldLabel que se ha establecido en el módulo 'Configurador de Pantallas'.
        	// Si este campo (que aparece en la pantalla 'Resultados de Cotización') no aparece originalmente
        	// en la pantalla 'Captura de Cotización' entonces no se setea un fieldLabel, se queda con el que 
        	// se ha establecido anteriormente
        	String fieldLabel = obtieneFieldLabelTextFieldsResultadoCotizacion( StringUtils.substring( entrada.getDsNombre(), 0, StringUtils.lastIndexOf( entrada.getDsNombre(), "_" ) ) );
        	if ( !StringUtils.isBlank( fieldLabel ) )
        		textField.setFieldLabel( fieldLabel );

        	textFields.add(textField);
        	datosImpresion1 += entrada.getDsAtribu() + "," + entrada.getDsValor() + "|";
        }

        session.put("TEXT_FIELDS", textFields);
        logger.debug("TEXT_FIELDS = " + textFields);
        
        //Agregar a sesion para poder utilizarlas en los links de la pantalla.
        session.put("CCCDRAMO", isDetalleCotizacion ? detalleCotizacion.get("cdramo") : globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()) );
        session.put("CCCDTIPSIT", isDetalleCotizacion ? detalleCotizacion.get("cdtipsit") : globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()) );
        
        logger.debug(">>> CCCDRAMO = " + session.get("CCCDRAMO"));
        logger.debug(">>> CCCDTIPSIT = " + session.get("CCCDTIPSIT"));
        
        success = true;
        return INPUT;
    }
    
    /**
     * Metodo que envia correo electronico con la cotización generada como archivo adjunto.
     * 
     * @author augusto.perez
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
	public String enviaMailCotizacion() throws ApplicationException{

		logger.debug( "## ENTRANDO a enviaMailCotizacion()..." );
	    try {
	    	Properties props = new Properties();
			props.put( "mail.smtp.host", "smtp.bizmail.yahoo.com" );
			props.put( "mail.smtp.port", "25" );
			props.put( "mail.smtp.auth", "true" );

			Session ses = Session.getDefaultInstance( props, null );

			//Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );
			Message msg = new MimeMessage( ses );
			
			logger.debug( ">> correo :[" + correo + "]" );
			logger.debug( ">> CC :[" + CC + "]" );
			logger.debug( ">> comentario :[" + comentario + "]" );

			InternetAddress fromAddress = new InternetAddress( "catweb@biosnettcs.com", "AON Catweb" );
			InternetAddress toAddress = new InternetAddress( correo );

			msg.setFrom( fromAddress );
			InternetAddress[] address = { toAddress };
			msg.setRecipients( Message.RecipientType.TO, address );

			if ( !CC.equals("") && !CC.equals(null) && CC.contains("@")) {
				toAddress = new InternetAddress( CC );
				InternetAddress[] address2 = { toAddress };
				msg.setRecipients( Message.RecipientType.CC, address2 );
			}

			msg.setSubject( "Cotización" );

			// Primera parte del correo: comentario introducido por el usuario
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText( comentario );
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart( messageBodyPart );

			// Segunda parte del correo: creación de archivo adjunto
			String adjuntoFinal = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' "; 
			adjuntoFinal += "'http://www.w3.org/TR/html4/loose.dtd'>";
			adjuntoFinal += "<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en' lang='en'>";
			adjuntoFinal += "<head><style type='text/css'> ";
			adjuntoFinal += "#campoConDatosUsuario {background-color: #A10C33; color: #FFFFFF; font-weight: bold;} ";
			adjuntoFinal += "#campoResultados {background-color: #C0C0C0; color: #000000; font-weight: bold;} ";
			adjuntoFinal += "#campoAseguradora {text-decoration: underline; color: #A10C33; font-weight: bold;} ";
			adjuntoFinal += "</style><title>Resultado de Cotizaci&oacute;n</title></head>";
			adjuntoFinal += "<body>" + adjunto + "</body></html>";
			
			File tempFile = File.createTempFile( "cotizacion", ".html" );
			FileOutputStream fout = new FileOutputStream( tempFile );
			PrintStream out = new PrintStream( fout );
			out.println( adjuntoFinal );
			out.close();
			
			BodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource( tempFile );
			messageBodyPart2.setDataHandler( new DataHandler( source ) );
			messageBodyPart2.setFileName("cotizacion.html");
			multipart.addBodyPart( messageBodyPart2 );
			msg.setContent( multipart );
			msg.setSentDate( new Date() );

			// Envio de correo
			Transport trans = ses.getTransport( "smtp" );
			// TODO: Crear metodo privado o conexion a base de datos para recuperar estos datos
			// y hacer lo posible para que sean ocultos 
			trans.connect( "smtp.bizmail.yahoo.com", "catweb@biosnettcs.com", "biosnet1" );
			msg.saveChanges();
			trans.sendMessage( msg, msg.getAllRecipients() );
			trans.close();
			
			tempFile.delete();

			success = true;
			return SUCCESS;
	    }
	    catch (AddressException e) {
	    	e.printStackTrace();
	    	logger.error("Exception in enviaMailCotizacion() ", e);
	    	success = false;
	    	addActionError(e.getMessage());
	    	return SUCCESS;
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	    	logger.error("Exception in enviaMailCotizacion() ", e);
	    	success = false;
	    	addActionError(e.getMessage());
	    	return SUCCESS;
	    } catch ( IOException e ) {
	    	e.printStackTrace();
	        logger.error( "Error al intentar crear archivo: " + e );
	        success = false;
	        addActionError(e.getMessage());
	        return SUCCESS;
	    } catch (Exception e){
	    	e.printStackTrace();
	    	logger.error("Exception in enviaMailCotizacion() ", e);
	    	success = false;
	    	addActionError(e.getMessage());
	    	return SUCCESS;
	    }
	}    

    /**
     * Metodo que obtiene los parametros de la pantalla de resultado de los ejemplos en Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneDetalle() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo getDetalleHHH...");
            logger.debug("### id " + id);
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parameters = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parameters.put(key, servletReq.getParameter(key));
        }

        logger.debug("parameter size is-- " + parameters.size());

        session.put("COTIZACION_RESULTADO", parameters);

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene datos resultado dummy para el grid de la pantalla de resultado de los ejemplos en Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerDatosGrid() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();
        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerDatosGrid en ResultadoCotizacionAction...");
            logger.debug("######################################################");
        }

        logger.debug("### llenando lista del grid... ");

        ConjuntoPantallaVO item = new ConjuntoPantallaVO();
        item.setCdConjunto("1");
        item.setCdProceso("1");
        item.setNombreConjunto("Ejemplo1");
        item.setProceso("Anual Contado");
        item.setCliente("1.00");
        item.setDescripcion("Anual Contado");

        ConjuntoPantallaVO item2 = new ConjuntoPantallaVO();
        item2.setCdConjunto("2");
        item2.setCdProceso("1");
        item2.setNombreConjunto("Ejemplo2");
        item2.setProceso("Mesual Tarjeta de Credito");
        item2.setCliente("2.00");
        item2.setDescripcion("Mesual Tarjeta de Credito");

        ConjuntoPantallaVO item3 = new ConjuntoPantallaVO();
        item3.setCdConjunto("3");
        item3.setCdProceso("1");
        item3.setNombreConjunto("Ejemplo3");
        item3.setProceso("Mesual Efectivo");
        item3.setCliente("3.00");
        item3.setDescripcion("Mesual Efectivo");

        ConjuntoPantallaVO item4 = new ConjuntoPantallaVO();
        item4.setCdConjunto("4");
        item4.setCdProceso("2");
        item4.setNombreConjunto("Ejemplo4");
        item4.setProceso("Anual Contado");
        item4.setCliente("1.00");
        item4.setDescripcion("Anual Contado");

        ConjuntoPantallaVO item5 = new ConjuntoPantallaVO();
        item5.setCdConjunto("5");
        item5.setCdProceso("2");
        item5.setNombreConjunto("Ejemplo5");
        item5.setProceso("Mesual Tarjeta de Credito");
        item5.setCliente("2.00");
        item5.setDescripcion("Mesual Tarjeta de Credito");

        dataGrid = new ArrayList();

        dataGrid.add(item);
        dataGrid.add(item2);
        dataGrid.add(item3);
        dataGrid.add(item4);
        dataGrid.add(item5);
        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene los datos que generan la pantalla de resultado en Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerDatosRegistro() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();
        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerDatosRegistro en ResultadoCotizacionAction...");
            logger.debug("######################################################");
        }

        Map<String, String> parametros = new HashMap<String, String>();

        parametros = (Map<String, String>) session.get("COTIZACION_INPUT");

        if (parametros == null) {
            logger.debug("### parametros en null obtenerDatosRegistro en resultado---");
            success = false;
        } else {

            logger.debug("parametros size is--------- " + parametros.size());

            try {
                registroList.add(parametros);
                logger.debug("### registroList---" + registroList);

            } catch (Exception e) {
                logger.error("Exception en JSONObject...", e);
            }

            success = true;

        }

        return SUCCESS;

    }

    /**
     * Metodo que obtiene datos resultado para el grid de la pantalla de resultado del proceso de Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerResultado() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();
        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerResultado en ResultadoCotizacionAction...");
            logger.debug("######################################################");
        }

        logger.debug("### Llenando lista del grid... ");

        List<RecordVO> rVo = new ArrayList<RecordVO>();
        List<ColumnGridVO> cVo = new ArrayList<ColumnGridVO>();
        List<ItemVO> aseguradoras = new ArrayList<ItemVO>();
        List<ItemVO> pagos = new ArrayList<ItemVO>();
        List<ResultadoCotizacionVO> resultadosDatos = new ArrayList<ResultadoCotizacionVO>();
        List<DynaBean> registrosTransformados = new ArrayList<DynaBean>();

        GridVO gResultado = new GridVO();

        gResultado = (GridVO) session.get(DATOS_GRID);

        if (gResultado == null || gResultado.equals("")) {
            logger.debug("resultadosDatos null");
        } else {
            rVo = gResultado.getRecordList();
            cVo = gResultado.getColumnList();
            aseguradoras = gResultado.getItemListAseguradora();
            pagos = gResultado.getItemListPago();
            resultadosDatos = gResultado.getResultList();
            logger.debug("gResultado-----" + gResultado);
            logger.debug("RecordVO-----" + rVo.size());
            logger.debug("ColumnGridVO-----" + cVo.size());
            logger.debug("resultadosDatos-----" + resultadosDatos.size());
            
            //////Atributos estaticos///////////////////////////
            DynaProperty[] properties = new DynaProperty[18 + rVo.size()];
            properties[0] = new DynaProperty("cdIdentifica", String.class);
            properties[1] = new DynaProperty("cdUnieco", String.class);
            properties[2] = new DynaProperty("cdRamo", String.class);
            properties[3] = new DynaProperty("estado", String.class);
            properties[4] = new DynaProperty("nmPoliza", String.class);
            properties[5] = new DynaProperty("nmSuplem", String.class);
            properties[6] = new DynaProperty("status", String.class);
            properties[7] = new DynaProperty("cdCiaaseg", String.class);
            properties[8] = new DynaProperty("dsUnieco", String.class);
            properties[9] = new DynaProperty("cdPerpag", String.class);
            properties[10] = new DynaProperty("dsPerpag", String.class);
            properties[11] = new DynaProperty("cdTipsit", String.class);
            properties[12] = new DynaProperty("dsTipsit", String.class);
            properties[13] = new DynaProperty("cdPlan", String.class);
            properties[14] = new DynaProperty("dsPlan", String.class);
            properties[15] = new DynaProperty("feEmisio", String.class);
            properties[16] = new DynaProperty("feVencim", String.class);
            properties[17] = new DynaProperty("numeroSituacion", String.class);
            
            
            //////Atributos dinamicos conforme a los planes//////////////////////////
            int m = 18;
            logger.debug("####m before---" + m);
            for (RecordVO pl : rVo) {
                properties[m] = new DynaProperty(pl.getName(), String.class);
                m++;
            }// for pagos

            logger.debug("####properties size final---" + properties.length);

            DynaClass resultDynaClass = new BasicDynaClass("result", null, properties);

            Integer identifica = 1;

            for (ItemVO aseg : aseguradoras) {
                logger.debug("####aseguradora---" + aseg.getDescripcion());
                for (ItemVO perp : pagos) {
                    logger.debug("####pago--" + perp.getDescripcion());

                    DynaBean r = resultDynaClass.newInstance();
                   
                    r.set("cdIdentifica", identifica.toString());
                    r.set("cdCiaaseg", aseg.getClave());
                    r.set("dsUnieco", aseg.getDescripcion());
                    r.set("cdPerpag", perp.getClave());
                    r.set("dsPerpag", perp.getDescripcion());
                    
                    identifica++;

                    registrosTransformados.add(r);
               
                }// for pagos
           }// for aseguradoras

        
            for (int g = 0; g < registrosTransformados.size(); g++) {
                logger.debug("####for registro numero--" + g);

                for (ResultadoCotizacionVO rDatos : resultadosDatos) {
                    logger.debug("####for resultado--" + rDatos.getCdIdentifica());
                
                   
                    if (rDatos.getCdCiaaseg().equals(registrosTransformados.get(g).get("cdCiaaseg"))
                            && rDatos.getCdPerpag().equals(registrosTransformados.get(g).get("cdPerpag"))) {
                        logger.debug("####Entró a una aseguradora y tipo pago iguales--" + registrosTransformados.get(g).get("cdIdentifica"));

                        registrosTransformados.get(g).set("cdUnieco", rDatos.getCdUnieco());
                        registrosTransformados.get(g).set("cdRamo", rDatos.getCdRamo());
                        registrosTransformados.get(g).set("estado", rDatos.getEstado());
                        registrosTransformados.get(g).set("nmPoliza", rDatos.getNmPoliza());
                        registrosTransformados.get(g).set("nmSuplem", rDatos.getNmSuplem());
                        registrosTransformados.get(g).set("status", rDatos.getStatus());
                        registrosTransformados.get(g).set("cdTipsit", rDatos.getCdTipsit());
                        registrosTransformados.get(g).set("dsTipsit", rDatos.getDsTipsit());
                        registrosTransformados.get(g).set("cdPlan", rDatos.getCdPlan());
                        registrosTransformados.get(g).set("dsPlan", rDatos.getDsPlan());
                        registrosTransformados.get(g).set("feEmisio", rDatos.getFeEmisio());
                        registrosTransformados.get(g).set("feVencim", rDatos.getFeVencim());
                        registrosTransformados.get(g).set("numeroSituacion", rDatos.getNumeroSituacion());
                        
                        ///Quitar espacios a descripcion del plan/////////////////////
                        String sPlanSinBlancos = "";
                        StringTokenizer stTexto = new StringTokenizer(rDatos.getDsPlan());
                        while (stTexto.hasMoreElements())
                            sPlanSinBlancos += stTexto.nextElement();
                        logger.debug("####dsplan sin espacios--" + sPlanSinBlancos + "--");
                        logger.debug("####rDatos.getMnPrima()        --" + rDatos.getMnPrima());
                        logger.debug("####rDatos.getCdPlan()         --" + rDatos.getCdPlan());
                        logger.debug("####rDatos.getDsPlan()         --" + rDatos.getDsPlan());
                        logger.debug("####rDatos.getNumeroSituacion()--" + rDatos.getNumeroSituacion());
                        logger.debug("####getDynaClass().getDynaProperty--" + 
                                registrosTransformados.get(g).getDynaClass().getDynaProperty(sPlanSinBlancos));

                        if (registrosTransformados.get(g).getDynaClass().getDynaProperty(sPlanSinBlancos)
                                != null) {
                            logger.debug("####llenado de valores a datos dinamicos---");
                            //jtezva sumador de cotizaciones
                            if(registrosTransformados.get(g).get(sPlanSinBlancos)!=null
                                    &&((String)registrosTransformados.get(g).get(sPlanSinBlancos)).length()>0)
                            {
                                //Ya existía un valor aquí
                                Double prima=Double.parseDouble((String)registrosTransformados.get(g).get(sPlanSinBlancos));
                                prima+=Double.parseDouble(rDatos.getMnPrima().replace(",", ""));
                                registrosTransformados.get(g).set(sPlanSinBlancos, prima.toString());
                                logger.debug("####valor de prima sumado: "+prima.toString());
                            }
                            else
                            {
                                registrosTransformados.get(g).set(sPlanSinBlancos, rDatos.getMnPrima().replace(",", ""));
                                logger.debug("####primer valor de prima: "+rDatos.getMnPrima().replace(",", ""));
                            }
                            //!jtezva sumador de cotizaciones
                            //registrosTransformados.get(g).set(sPlanSinBlancos, rDatos.getMnPrima().replace(",", ""));
                            registrosTransformados.get(g).set("CD" + sPlanSinBlancos, rDatos.getCdPlan());
                            registrosTransformados.get(g).set("DS" + sPlanSinBlancos, rDatos.getDsPlan());
                            registrosTransformados.get(g).set("NM" + sPlanSinBlancos, rDatos.getNumeroSituacion());
                        }
                      
                    }//if
                
                    
                }// for result
                dataResult.add(registrosTransformados.get(g));
            }// for nuevos registros
            
        
        }// else
       
        logger.debug("####dataResult--" + dataResult);
        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene realiza la accion de comprar de la pantalla de resultado del proceso de Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String comprar() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo comprar...");
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parameters = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parameters.put(key, servletReq.getParameter(key));
        }

        logger.debug("parameter size is-- " + parameters.size());

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene las coberturas del plan seleccionado en la pantalla de resultado del proceso de Cotizacion
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneCoberturas() throws Exception {
    	UserVO usuario = (UserVO) session.get("USUARIO");
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		
        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo get Coberturas...");
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parametersCoberturas = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parametersCoberturas.put(key, servletReq.getParameter(key));
        }

        logger.debug("parametersCoberturas size is-- " + parametersCoberturas.size());

        if (parametersCoberturas.size() < 3) {
            logger.debug("### parametrosCoberturas insuficientes---");
            success = false;
        } else {
            logger.debug("parametersCoberturas size is-- " + parametersCoberturas.size());
            parametersCoberturas.put("USUARIO",  usuario.getUser());
            parametersCoberturas.put("CDUNIECO", globalVariable.getValueVariableGlobal("vg.CdUnieco"));
            parametersCoberturas.put("ESTADO",   globalVariable.getValueVariableGlobal("vg.Estado"));
            parametersCoberturas.put("NMPOLIZA", globalVariable.getValueVariableGlobal("vg.NmPoliza"));
            parametersCoberturas.put("NMSITUAC", numeroSituacion);
            parametersCoberturas.put("NMSUPLEM", globalVariable.getValueVariableGlobal("vg.NmSuplem"));        
            parametersCoberturas.put("CDPLAN",   cdPlan);
            parametersCoberturas.put("CDRAMO",   globalVariable.getValueVariableGlobal("vg.CdRamo"));
            parametersCoberturas.put("CDCIA",    ciaAseg);
            parametersCoberturas.put("REGION",   "ME");
            parametersCoberturas.put("PAIS",     usuario.getPais().getValue());
            parametersCoberturas.put("IDIOMA",   usuario.getIdioma().getValue());
            coberturasResult = (List<CoberturaCotizacionVO>) cotizacionManager.getCoberturas(parametersCoberturas);

        }

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene la ayuda de la cobertura seleccionada en la pantalla de resultados del proceso de Cotizacion
     * 
     * @return Cadena INPUT
     */
    @SuppressWarnings("unchecked")
    public String obtieneAyudaCobertura() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo get Ayuda Cobertura...");
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parametersAyudaCobertura = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parametersAyudaCobertura.put(key, servletReq.getParameter(key));
        }

        logger.debug("parametersAyudaCobertura size is-- " + parametersAyudaCobertura.size());

        if (parametersAyudaCobertura.size() < 3) {
            logger.debug("### parametrosAyudaCobertura insuficientes---");
            success = false;
        } else {

            ayudaCoberturaVo = new AyudaCoberturaCotizacionVO();

            ayudaCoberturaVo = cotizacionManager.getAyudaCobertura(parametersAyudaCobertura);
            //String ayudaLeyenda = StringEscapeUtils.unescapeHtml(ayudaCoberturaVo.getDsAyuda());
            session.put("AYUDA", ayudaCoberturaVo);
            session.put("AYUDA_ENCABEZADO", ayudaCoberturaVo.getDsGarant());
            session.put("AYUDA_LEYENDA", ayudaCoberturaVo.getDsAyuda());

            logger.debug("### AYUDA sesion ---" + session.get("AYUDA"));
            logger.debug("### AYUDA ENCABEZADO ---" + session.get("AYUDA_ENCABEZADO"));
            logger.debug("### AYUDA LEYENDA ---" + session.get("AYUDA_LEYENDA"));

        }
        success = true;
        return SUCCESS;
    }

    public String guardaCotizacion() throws Exception{
    	UserVO usuario = (UserVO) session.get("USUARIO");
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		
		if (logger.isDebugEnabled()) {
		    	logger.debug("cdIdentifica = " + cdIdentifica);
		    	logger.debug("cdUnieco			= " + cdUnieco);
		    	logger.debug("cdCiaaseg			= " + cdCiaaseg);
		    	logger.debug("dsUnieco			= " + dsUnieco);
		    	logger.debug("cdPerpag			= " + cdPerpag);
		    	logger.debug("cdRamo			= " + cdRamo);
		    	logger.debug("dsPerpag			= " + dsPerpag);
		    	logger.debug("cdPlan			= " + cdPlan);
		    	logger.debug("numeroSituacion	= " + numeroSituacion);
		    	logger.debug(" llave del usuario en session ===>CONTENIDO_USER");
		}
		
    	String cdTipsit =globalVariable.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
    	String estado = globalVariable.getValueVariableGlobal("vg.Estado");
    	String nmPoliza = globalVariable.getValueVariableGlobal("vg.NmPoliza");
    	String nmSuplem = globalVariable.getValueVariableGlobal("vg.NmSuplem");
    	cdUnieco = globalVariable.getValueVariableGlobal(VariableKernel.UnidadEconomica());
    	String cdusuari = usuario.getUser();
    	cdRamo = globalVariable.getValueVariableGlobal("vg.CdRamo");
    	ResultadoCotizacionVO rcvo = new ResultadoCotizacionVO();
    	rcvo.setCdCiaaseg(cdCiaaseg);
    	rcvo.setCdIdentifica(cdIdentifica);
    	rcvo.setCdPerpag(cdPerpag);
    	rcvo.setCdPlan(cdPlan);
    	rcvo.setCdRamo(cdRamo);
    	rcvo.setCdTipsit(cdTipsit);
    	rcvo.setCdUnieco(cdUnieco);
    	rcvo.setDsPerpag(dsPerpag);
    	rcvo.setDsUnieco(dsUnieco);
    	rcvo.setEstado(estado);
    	rcvo.setNmPoliza(nmPoliza);
    	rcvo.setNmSuplem(nmSuplem);
    	rcvo.setNumeroSituacion(numeroSituacion);
    	
    	cotizacionManager.saveCotizacionResultado(cdusuari, rcvo);
    	
    	if (logger.isDebugEnabled()) {
    		
    		logger.debug("><Antes de llamar a los casos para las Aseguradoras se tienen los valores: ");
	    	logger.debug("cdIdentifica = " + cdIdentifica);
	    	logger.debug("cdUnieco			= " + cdUnieco);
	    	logger.debug("cdCiaaseg			= " + cdCiaaseg);
	    	logger.debug("dsUnieco			= " + dsUnieco);
	    	logger.debug("cdPerpag			= " + cdPerpag);
	    	logger.debug("cdRamo			= " + cdRamo);
	    	logger.debug("dsPerpag			= " + dsPerpag);
	    	logger.debug("cdPlan			= " + cdPlan);
	    	logger.debug("numeroSituacion	= " + numeroSituacion);
	    	
	}
    	
    	ArrayList<AseguradoraVO> cdCias=(ArrayList<AseguradoraVO>) cotizacionManager.getAseguradoras(rcvo);
    	
    	
    	if(cdCias!=null){
    		logger.debug("Array Aseguradoras: " + cdCias);
    		for(AseguradoraVO aseguradora : cdCias){
    			
    			if(StringUtils.isNotBlank(aseguradora.getCdUniEco())){
    				logger.debug("Ejectutando caso para Cdcia: " + aseguradora.getCdUniEco());
    				kernelManager.ejecutarCaso(globalVariable, usuario, cdUnieco, cdRamo, cdTipsit, nmPoliza, nmSuplem, aseguradora.getCdUniEco(), numeroSituacion, estado, "1", "");
    			}else{
            		
            		kernelManager.ejecutarCaso(globalVariable, usuario, cdUnieco, cdRamo, cdTipsit, nmPoliza, nmSuplem, cdCiaaseg, numeroSituacion, estado, "1", "");
            	}
    			
    		}
    		
    	}else{
    		
    		kernelManager.ejecutarCaso(globalVariable, usuario, cdUnieco, cdRamo, cdTipsit, nmPoliza, nmSuplem, cdCiaaseg, numeroSituacion, estado, "1", "");
    	}
    	
    	
    	numIdentificador = nmPoliza;
    	
    	//Atributo de respuesta que contendra el numero de la poliza:
    	numeroPoliza = globalVariable.getValueVariableGlobal(VariableKernel.NumeroPoliza());

    	success = true;
    	return SUCCESS;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the registroList
     */
    public JSONArray getRegistroList() {
        return registroList;
    }

    /**
     * @param registroList the registroList to set
     */
    public void setRegistroList(JSONArray registroList) {
        this.registroList = registroList;
    }

    /**
     * @param cotizacionManager the cotizacionManager to set
     */
    public void setCotizacionManager(CotizacionService cotizacionManager) {
        this.cotizacionManager = cotizacionManager;
    }

    /**
     * @return the dataGrid
     */
    @SuppressWarnings("unchecked")
    public List getDataGrid() {
        return dataGrid;
    }

    /**
     * @param dataGrid the dataGrid to set
     */
    @SuppressWarnings("unchecked")
    public void setDataGrid(List dataGrid) {
        this.dataGrid = dataGrid;
    }

    /**
     * @return the coberturasResult
     */
    @SuppressWarnings("unchecked")
    public List getCoberturasResult() {
        return coberturasResult;
    }

    /**
     * @return the dataResult
     */
    public JSONArray getDataResult() {
        return dataResult;
    }

    /**
     * @param coberturasResult the coberturasResult to set
     */
    public void setCoberturasResult(List<CoberturaCotizacionVO> coberturasResult) {
        this.coberturasResult = coberturasResult;
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
     * @param dataResult the dataResult to set
     */
    public void setDataResult(JSONArray dataResult) {
        this.dataResult = dataResult;
    }

	public String getBotonGuardaCotizacion() { return botonGuardaCotizacion; }
	public void setBotonGuardaCotizacion(String botonGuardaCotizacion) { this.botonGuardaCotizacion = botonGuardaCotizacion; }

	public String getCdIdentifica() { return cdIdentifica; }
	public void setCdIdentifica(String cdIdentifica) { this.cdIdentifica = cdIdentifica; }

	public String getCdUnieco() { return cdUnieco; }
	public void setCdUnieco(String cdUnieco) { this.cdUnieco = cdUnieco; }

	public String getCdCiaaseg() { return cdCiaaseg; }
	public void setCdCiaaseg(String cdCiaaseg) { this.cdCiaaseg = cdCiaaseg; }

	public String getDsUnieco() { return dsUnieco; }
	public void setDsUnieco(String dsUnieco) { this.dsUnieco = dsUnieco; }

	public String getCdPerpag() { return cdPerpag; }
	public void setCdPerpag(String cdPerpag) { this.cdPerpag = cdPerpag; }

	public String getCdRamo() { return cdRamo; }
	public void setCdRamo(String cdRamo) { this.cdRamo = cdRamo; }

	public String getDsPerpag() { return dsPerpag; }
	public void setDsPerpag(String dsPerpag) { this.dsPerpag = dsPerpag; }
	
	public String getCdPlan() { return cdPlan; }
	public void setCdPlan(String cdPlan) { this.cdPlan = cdPlan; }

	public String getNumeroSituacion() { return numeroSituacion; }
	public void setNumeroSituacion(String numeroSituacion) { this.numeroSituacion = numeroSituacion; }

	public String getCiaAseg() { return ciaAseg; }
	public void setCiaAseg(String ciaAseg) { this.ciaAseg = ciaAseg; }	
	
	public String getParamsEntradaCotizacion() { return paramsEntradaCotizacion; }
    public void setParamsEntradaCotizacion(String paramsEntradaCotizacion) { this.paramsEntradaCotizacion = paramsEntradaCotizacion; }

    /**
     * 
     * @param kernelManager the kernelManager to set (method used by Spring) 
     */
	public void setKernelManager(KernelManager kernelManager) {
		this.kernelManager = kernelManager;
	}

	public String getNumIdentificador() {
		return numIdentificador;
	}

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumIdentificador(String numIdentificador) {
		this.numIdentificador = numIdentificador;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public ServiciosGeneralesSistema getServiciosGeneralesSistema() {
		return serviciosGeneralesSistema;
	}

	public void setServiciosGeneralesSistema(
			ServiciosGeneralesSistema serviciosGeneralesSistema) {
		this.serviciosGeneralesSistema = serviciosGeneralesSistema;
	}

	public String getUrlComprarDesdeConsulta() {
		return urlComprarDesdeConsulta;
	}

	public void setUrlComprarDesdeConsulta(String urlComprarDesdeConsulta) {
		this.urlComprarDesdeConsulta = urlComprarDesdeConsulta;
	}

	public String getBotonNuevaCotizacion() {
		return botonNuevaCotizacion;
	}

	public void setBotonNuevaCotizacion(String botonNuevaCotizacion) {
		this.botonNuevaCotizacion = botonNuevaCotizacion;
	}

	public String getBotonRegresaCotizacion() {
		return botonRegresaCotizacion;
	}

	public void setBotonRegresaCotizacion(String botonRegresaCotizacion) {
		this.botonRegresaCotizacion = botonRegresaCotizacion;
	}

	public String getDatosImpresion1() {
		return datosImpresion1;
	}

	public void setDatosImpresion1(String datosImpresion1) {
		this.datosImpresion1 = datosImpresion1;
	}

	public String getDatosImpresion2() {
		return datosImpresion2;
	}

	public void setDatosImpresion2(String datosImpresion2) {
		this.datosImpresion2 = datosImpresion2;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCC() {
		return CC;
	}

	public void setCC(String cc) {
		CC = cc;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public String getAdjunto() {
		return adjunto;
	}


	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public void setCotizacionManagerJdbcTemplate(
			CotizacionPrincipalManager cotizacionManagerJdbcTemplate) {
		this.cotizacionManagerJdbcTemplate = cotizacionManagerJdbcTemplate;
	}

	public String getClicBotonRegresaComprar() {
		return clicBotonRegresaComprar;
	}

	public void setClicBotonRegresaComprar(String clicBotonRegresaComprar) {
		this.clicBotonRegresaComprar = clicBotonRegresaComprar;
	}

}
