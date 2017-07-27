package mx.com.gseguros.portal.consultas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.AvisoHospitalizacionVO;
import mx.com.gseguros.portal.consultas.model.CoberturaBasicaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosGeneralesPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTitularVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaActualVO;
import mx.com.gseguros.portal.consultas.model.ConsultaResultadosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ContratanteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.EndosoVO;
import mx.com.gseguros.portal.consultas.model.EnfermedadVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.consultas.model.HistoricoVO;
import mx.com.gseguros.portal.consultas.model.PeriodoVigenciaVO;
import mx.com.gseguros.portal.consultas.model.PlanVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasAseguradoManager;
import mx.com.gseguros.portal.documentos.service.DocumentosManager;
import mx.com.gseguros.portal.general.model.BaseVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.service.MailService;
import mx.com.gseguros.portal.general.service.MailServiceForSms;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.com.aon.portal2.web.GenericVO;


/**
*
* @author MAVR
*/
@ParentPackage(value="default")
@Namespace("/consultasAsegurado")
@Controller(value="ConsultasAseguradoAction")
@Scope(value="prototype")
public class ConsultasAseguradoAction extends PrincipalCoreAction { 

	private static final long serialVersionUID = 7279218302506728952L;

	static final Logger logger = LoggerFactory.getLogger(ConsultasAseguradoAction.class);
	
	/**
     * Success property
     */
    private boolean success;
    
    private String mensajeRes;
    
    private String iCodAviso;
    
	/**
	 * Indica si el usuario es de CallCenter
	 */
	private boolean usuarioCallCenter;
    
    @Autowired
    private MailServiceForSms mailServiceForSms;
    
    @Autowired
    private MailService mailService;
	
	@Autowired
	@Qualifier("consultasAseguradoManagerImpl")
    private ConsultasAseguradoManager consultasAseguradoManager;
	
	@Autowired
    private DocumentosManager documentosManager;
	
	/*
	@Autowired
	@Qualifier("consultasDAOSISAImpl")
	private IConsultasPolizaDAO consultasPolizaDAO;
	*/
	private Map<String, String> params = new HashMap<String, String>();
	
	private List<?> resultados;
	
	private List<ConsultaResultadosAseguradoVO> resultadosAsegurado;
	
	private List<ConsultaPolizaActualVO> datosPolizaActual;
	
	private ConsultaDatosComplementariosVO datosComplementarios;
	
	private ConsultaDatosGeneralesPolizaVO datosPoliza;
	
	private List<AseguradoDetalleVO> datosAseguradoDetalle;
	
	private ConsultaDatosTitularVO datosTitular;
	
	private ContratanteVO datosContratante;
	
	private List<AseguradoVO> datosAsegurados;
	
	private List<EndosoVO> datosEndosos;
	
	private List<EnfermedadVO> datosEnfermedades;
	
	private PlanVO datosPlan;
	
	private List<CopagoVO> datosCopagosPoliza;
	
	private List<CoberturaBasicaVO> datosCoberturasPoliza;
	
	private List<CoberturaBasicaVO> datosCoberturasBasicas;
	
	private List<HistoricoVO> datosHistorico;
	
	private List<HistoricoFarmaciaVO> historicoFarmacia;
	
	private List<PeriodoVigenciaVO> periodosVigencia;
	
	private List<BaseVO> datosHospitales;
	
	private List<AvisoHospitalizacionVO> datosAvisosAnteriores;
	
	private Map<String,String> smap1;
	
	private List<GenericVO> listaCatalogo;

	private List<Map<String,String>> loadList;

	private String mensaje;
	
	@Value("${ruta.servidor.reports}")
    private String rutaServidorReports;
    
    @Value("${pass.servidor.reports}")
    private String passServidorReports;
    
    @Value("${ruta.documentos.poliza}")
    private String rutaDocumentosPoliza;
	
	/**
	 * Metodo de entrada a consulta de polizas
	 */
	public String pantallaConsultasAsegurado() throws Exception {

		// Obtenemos el rol de sistema del usuario en sesion:
		UserVO usuario = (UserVO) session.get("USUARIO");
		String cdRolSistema = usuario.getRolActivo().getClave();
		// Si es Call Center, tendra permiso de ver Aviso Hospitalizacion:
		if (cdRolSistema.equals(RolSistema.CALLCENTER.getCdsisrol())) {
			usuarioCallCenter = true;
		}
		return SUCCESS;
	}
	

	@Action(value   = "pantallaMedicinaPreventiva",
    results = {
	    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
        @Result(name="success" , location="/jsp/extjs4/ventanaMedicinaPreventiva.jsp")
    }
	)
	public String pantallaMedicinaPreventiva() throws Exception {
		
		logger.debug(" **** Entrando a pantallaMedicinaPreventiva ****");
    	try {
    		smap1.putAll(consultasAseguradoManager.obtieneDatosAsegurado(smap1));
    		smap1.put("random", String.format("%.0f", Math.random()*10000d));

    		boolean cobMedPrev = false;
    		try{
    			cobMedPrev = consultasAseguradoManager.validaAsegCobMedicinaPreventiva(smap1);
    		}catch(Exception exp){
    			logger.error("Error al obtener pantallaMedicinaPreventiva ",exp);
    		}
    		 
    		smap1.put("COBMEDPREV", cobMedPrev?Constantes.SI:Constantes.NO);
    		
    		logger.debug("Valor de RAMDOM smap1 <<<<<<>>>>>> ::: "+ smap1);
    	}catch( Exception e){
    		logger.error("Error al obtener pantallaMedicinaPreventiva ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
	}

	@Action(value   = "obtieneCopagoCobMedPrevPol",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCopagoCobMedPrevPol() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCopagoCobMedPrevPol ****");
		try {
			params.putAll(consultasAseguradoManager.obtieneCopagoCobMedPrevPol(params));
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCopagoCobMedPrevPol ",e);
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
	}
	
	@Action(value   = "obtieneCatalogoPadecimientos",
			results = { @Result(name="success", type="json") }
	)
	public String obtieneCatalogoPadecimientos() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoPadecimientos ****");
    	try {
    		
    		listaCatalogo = consultasAseguradoManager.obtieneCatalogoICDs();
    		
    	}catch( Exception e){
    		logger.error("Error al obtener obtieneCatalogoPadecimientos ",e);
    		success = false;
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
		
	}

	@Action(value   = "obtieneCatalogoEstadosProvMedicos",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatalogoEstadosProvMedicos() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoEstadosProvMedicos ****");
		try {
			
			listaCatalogo = consultasAseguradoManager.obtieneCatalogoEstadosProvMedicos();
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatalogoEstadosProvMedicos ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@Action(value   = "obtieneCatalogoMunicipiosProvMedicos",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatalogoMunicipiosProvMedicos() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoMunicipiosProvMedicos ****");
		try {
			
			listaCatalogo = consultasAseguradoManager.obtieneCatalogoMunicipiosProvMedicos(params);
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatalogoMunicipiosProvMedicos ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@Action(value   = "obtieneCatalogoEspecialidadesMedicos",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatalogoEspecialidadesMedicos() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoEspecialidadesMedicos ****");
		try {
			
			listaCatalogo = consultasAseguradoManager.obtieneCatalogoEspecialidadesMedicos();
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatalogoEspecialidadesMedicos ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}
	
	@Action(value   = "obtieneCatDireccionProvMedPorEspecialidad",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatDireccionProvMedPorEspecialidad() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatDireccionProvMedPorEspecialidad ****");
		try {
			
			loadList = consultasAseguradoManager.obtieneCatDireccionProvMedPorEspecialidad(params);
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatDireccionProvMedPorEspecialidad ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@Action(value   = "obtieneCatalogoFrecuenciaVisitas",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatalogoFrecuenciaVisitas() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoFrecuenciaVisitas ****");
		try {
			
			listaCatalogo = consultasAseguradoManager.obtieneCatalogoFrecuenciaVisitas();
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatalogoFrecuenciaVisitas ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@Action(value   = "obtieneCatalogoPeriodicidadVisitas",
			results = { @Result(name="success", type="json") }
			)
	public String obtieneCatalogoPeriodicidadVisitas() throws Exception {
		
		logger.debug(" **** Entrando a obtieneCatalogoPeriodicidadVisitas ****");
		try {
			
			listaCatalogo = consultasAseguradoManager.obtieneCatalogoPeriodicidadVisitas();
			
		}catch( Exception e){
			logger.error("Error al obtener obtieneCatalogoPeriodicidadVisitas ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}
	
	
	@Action(value   = "obtienePadecimientosAseg",
			results = { @Result(name="success", type="json") }
			)
	public String obtienePadecimientosAseg() throws Exception {
		
		logger.debug(" **** Entrando a obtienePadecimientosAseg ****");
		try {
			
			loadList = consultasAseguradoManager.obtienePadecimientosAsegurado(params);
			
		}catch( Exception e){
			logger.error("Error al obtener obtienePadecimientosAseg ",e);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@Action(value   = "actualizaPadecimientoAseg",
			results = { @Result(name="success", type="json") }
			)
	public String actualizaPadecimientoAsegurado() throws Exception {
		
		logger.debug(" **** Entrando a actualizaPadecimientoAsegurado ****");
		try {
			
			UserVO usuario = (UserVO) session.get("USUARIO");
			String usuarioSistema = usuario.getUser();
			
			params.put("pi_cdusuari", usuarioSistema);
			
			consultasAseguradoManager.actualizaPadecimientoAsegurado(params);
			
		}catch( Exception ex){
			logger.error("Error al obtener actualizaPadecimientoAsegurado ",ex);
			mensaje = Utils.manejaExcepcion(ex);
	        success = false;
	        return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}

	@SuppressWarnings("deprecation")
	@Action(value   = "generaCartaMedicinaPreventiva",
			results = { @Result(name="success", type="json") }
			)
	public String generaCartaMedicinaPreventiva() throws Exception {
		
		logger.debug(" **** Entrando a generaCartaMedicinaPreventiva ****");
		try {
			
			UserVO usuario = (UserVO) session.get("USUARIO");
			params.put("pi_cdusuari", usuario.getUser());
			
			try {
				Map<String, String> datosNtramite = consultasAseguradoManager.obtenerNtramiteEmision(params.get("pi_cdunieco"), params.get("pi_cdramo"), params.get("pi_estado"), params.get("pi_nmpoliza"));
				params.put("pi_ntramite", datosNtramite.get("NTRAMITE"));
				params.put("pi_nmsuplem", datosNtramite.get("NMSUPLEM"));
						
	    	}catch( Exception ex){
	    		logger.error("Error al obtener datos de Tramite para guardar Documento ",ex);
	    		mensaje = "Error al obtener datos de Tramite para guardar Documento. "+Utils.manejaExcepcion(ex);
				success = false;
				return SUCCESS;
	    	}
			
			
			String reporteSeleccion = getText("rdf.medicinaprev.impresion.cartaMedPrev");
			
			String urlGenerarCartaMedPrev = ""
				+ rutaServidorReports
				+ "?P_UNIECO="   + params.get("pi_cdunieco")
				+ "&P_RAMO="     + params.get("pi_cdramo")
				+ "&P_POLIZA="   + params.get("pi_nmpoliza")
				+ "&P_SITUAC="   + params.get("pi_nmsituac")
				+ "&P_CDPERSON=" + params.get("pi_cdperson")
				+ "&P_CDICD="    + params.get("pi_cdicd")
				+ "&destype=cache"
				+ "&desformat=PDF"
				+ "&userid="+passServidorReports
				+ "&ACCESSIBLE=YES"
				+ "&report="+reporteSeleccion
				+ "&paramform=no"
				;
			
			logger.debug("urlAutorizacionServicio: {}", urlGenerarCartaMedPrev);
			
			String nombreArchivoModificado = "CartaMP_Aseg_"+params.get("pi_cdperson")+"_ICD_"+params.get("pi_cdicd")+"_t"+System.currentTimeMillis()+"_"+((long)(Math.random()*10000l))+".pdf";
			String pathArchivo=""
				+ rutaDocumentosPoliza
				+ "/" + params.get("pi_ntramite")
				+ "/" + nombreArchivoModificado
				;
			HttpUtil.generaArchivo(urlGenerarCartaMedPrev, pathArchivo);
			
			documentosManager.guardarDocumento(
					params.get("pi_cdunieco"),
					params.get("pi_cdramo"),
					params.get("pi_estado"),
					params.get("pi_nmpoliza"),
					params.get("pi_nmsuplem")
					,new Date()
					,nombreArchivoModificado
					,"Carta de Medicina Preventiva ASEG:"+ params.get("pi_cdperson")+" ICD:"+params.get("pi_cdicd")+"_"+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
					,null
					,params.get("pi_ntramite")
					,TipoEndoso.APARTADO_DE_MEDICINA_PREVENTIVA.getCdTipSup().toString()
					,null//visible
					,null//codidocu
					,TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,null//cdordoc
					,null//documento
					,usuario.getUser()
					,usuario.getRolActivo().getClave(), false
					);
			
					//PARA ACTUALIZAR EL SW CARTA DE MEDICINA PREVENTIVA GENERADA
					consultasAseguradoManager.actualizaPadecimientoAsegurado(params);
			
		}catch( Exception ex){
			logger.error("Error al obtener generaCartaMedicinaPreventiva ",ex);
			mensaje = Utils.manejaExcepcion(ex);
			success = false;
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
		
	}
	
	
	/**
     * Obtiene las coincidencias de asegurados
     * @return String result
     */
    public String consultaResultadosAsegurado(){
    	logger.debug(" **** Entrando a consultaResultadosAsegurado ****");
    	try {
    		
    		//logger.debug("Nueva implementacion...");
    		resultadosAsegurado = consultasAseguradoManager.obtieneResultadosAsegurado(params.get("rfc"), params.get("cdperson"), params.get("nombre"));
    		
    		if(resultadosAsegurado != null) {
    			logger.debug("Coincidencias por asegurado encontradas: {}", resultadosAsegurado.size());
    		}
    		
    	}catch( Exception e){
    		logger.error("Error al obtener consultaResultadosAsegurado ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene los datos de la poliza actual del asegurado
     * @return String result
     */
    public String consultaPolizaActual(){
    	logger.debug(" **** Entrando a consultaPolizaActual ****");
    	mensajeRes = "";
    	try {    		    		
    		PolizaAseguradoVO poliza = new PolizaAseguradoVO();
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		poliza.setNmpoliex(params.get("nmpoliex"));
    		datosPolizaActual = consultasAseguradoManager.obtienePolizaActual(poliza);
    		
    		if(datosPolizaActual != null) {
    			logger.debug("Poliza actual encontrada: {}", datosPolizaActual.size());
    		}
    		
    		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaPolizaActual ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
	
    /**
     * Obtiene los datos complementarios de la Seccion Datos Generales
     * @return String result
     */
    public String consultaDatosComplementarios(){
    	logger.debug(" **** Entrando a Consulta de Datos Complementarios ****");
        try {
			
        	PolizaVO poliza = new PolizaVO();    		
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		poliza.setCdunieco(params.get("cdunieco"));
    		poliza.setCdramo(params.get("cdramo"));
    		poliza.setEstado(params.get("estado"));
    		poliza.setNmpoliza(params.get("nmpoliza"));
    		poliza.setNmsuplem(params.get("suplemento"));
    		poliza.setNmsituac(params.get("nmsituac"));
    		AseguradoVO asegurado = new AseguradoVO();
    		asegurado.setCdperson(params.get("cdperson"));
    					
			List<ConsultaDatosComplementariosVO> listaAux = consultasAseguradoManager.obtieneDatosComplementarios(poliza, asegurado);
			
			if(listaAux!=null && !listaAux.isEmpty())	datosComplementarios = listaAux.get(0);
			
        	
        	logger.debug("Resultado de la consulta de datos complementarios: {}" , datosComplementarios);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos complementarios ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
	
    /**
     * Obtiene los datos generales de una p\u00f3liza
     * @return String result
     */
    public String consultaDatosPoliza(){
    	logger.debug(" **** Entrando a Consulta de Poliza ****");
        try {			        	
        	PolizaAseguradoVO polizaAseguradoVO = new PolizaAseguradoVO();
        	polizaAseguradoVO.setCdunieco(params.get("cdunieco"));
        	polizaAseguradoVO.setCdramo(params.get("cdramo"));
        	polizaAseguradoVO.setEstado(params.get("estado"));
        	polizaAseguradoVO.setNmpoliza(params.get("nmpoliza"));
        	polizaAseguradoVO.setIcodpoliza(params.get("icodpoliza"));
        	
        	List<ConsultaDatosGeneralesPolizaVO> lista = consultasAseguradoManager.obtieneDatosPoliza(polizaAseguradoVO);
        	
        	if(lista!=null && !lista.isEmpty())	datosPoliza = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de poliza: {}" , datosPoliza);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos de la poliza ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * Obtiene la informacion del asegurado
     * @return String result
     */
    public String consultaAseguradoDetalle(){
		logger.debug(" *** Entrando a consultaAseguradoDetalle ****");
		try{
			PolizaVO poliza = new PolizaVO();
        	poliza.setCdunieco(params.get("cdunieco"));
        	poliza.setCdramo(params.get("cdramo"));
        	poliza.setEstado(params.get("estado"));
        	poliza.setNmpoliza(params.get("nmpoliza"));
        	poliza.setNmsuplem(params.get("suplemento"));        	
        	poliza.setIcodpoliza(params.get("icodpoliza"));
			AseguradoVO asegurado = new AseguradoVO();
			asegurado.setCdperson(params.get("cdperson"));
			
			datosAseguradoDetalle = consultasAseguradoManager.obtieneAseguradoDetalle(poliza, asegurado);
			
			logger.debug("Resultado de consultaAseguradoDetalle: {}" , datosAseguradoDetalle);
			
		}catch(Exception e){
			logger.error("Error al obtener el detalle del asegurado.",e);
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
	}
    
    /**
     * Obtiene los datos del titular
     * @return String result
     */
    public String consultaDatosTitular(){
    	logger.debug(" **** Entrando a Consulta de Titular ****");
        try {
			        	
        	PolizaVO polizaVO = new PolizaVO();
        	polizaVO.setCdunieco(params.get("cdunieco"));
        	polizaVO.setCdramo(params.get("cdramo"));
        	polizaVO.setEstado(params.get("estado"));
        	polizaVO.setNmpoliza(params.get("nmpoliza"));
        	polizaVO.setIcodpoliza(params.get("icodpoliza"));
        	polizaVO.setNmsituac(params.get("nmsituac"));
        	AseguradoVO aseguradoVO = new AseguradoVO();
    		aseguradoVO.setCdperson(params.get("cdperson"));
        	
        	List<ConsultaDatosTitularVO> lista = consultasAseguradoManager.obtieneDatosTitular(polizaVO, aseguradoVO);
        	
        	if(lista!=null && !lista.isEmpty())	datosTitular = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de titular: {}" , datosTitular);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos del titular ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * Obtiene los datos del contratante
     * @return String result
     */
    public String consultaDatosContratante(){
    	logger.debug(" **** Entrando a Consulta de Contratante ****");
        try {
			        	
        	PolizaVO polizaVO = new PolizaVO();
        	polizaVO.setCdunieco(params.get("cdunieco"));
        	polizaVO.setCdramo(params.get("cdramo"));
        	polizaVO.setEstado(params.get("estado"));
        	polizaVO.setNmpoliza(params.get("nmpoliza"));        	
        	polizaVO.setIcodpoliza(params.get("icodpoliza"));
        	
        	List<ContratanteVO> lista = consultasAseguradoManager.obtieneDatosContratante(polizaVO);
        	
        	if(lista!=null && !lista.isEmpty())	datosContratante = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de contratante: {}" , datosContratante);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos del contratante ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * Obtiene los datos del asegurado
     * @return String result
     */
    public String consultaDatosAsegurado(){
    	logger.debug(" **** Entrando a consultaDatosAsegurado ****");
    	try {
    		    		
    		PolizaVO poliza = new PolizaVO();
    		poliza.setCdunieco(params.get("cdunieco"));
    		poliza.setCdramo(params.get("cdramo"));
    		poliza.setEstado(params.get("estado"));
    		poliza.setNmpoliza(params.get("nmpoliza"));
    		poliza.setNmsuplem(params.get("suplemento"));
    		poliza.setNmsituac(params.get("nmsituac"));
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		AseguradoVO asegurado = new AseguradoVO();
    		asegurado.setCdperson(params.get("cdperson"));
    		datosAsegurados = consultasAseguradoManager.obtieneAsegurados(poliza,asegurado);
    		
    		logger.debug("Resultado de la consultaDatosAsegurado: {}" , datosAsegurados);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los datos del Asegurado ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene los endosos de una poliza
     * @return String result
     */    
    public String consultaDatosEndosos(){
    	logger.debug(" **** Entrando a consultaDatosEndosos ****");
    	PolizaVO poliza = new PolizaVO();
    	poliza.setCdunieco(params.get("cdunieco"));
    	poliza.setCdramo(params.get("cdramo"));
    	poliza.setEstado(params.get("estado"));
    	poliza.setNmpoliza(params.get("nmpoliza"));
    	poliza.setNmsuplem(params.get("suplemento"));
    	poliza.setNmsituac(params.get("nmsituac"));
    	poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
    	try {    		
    		datosEndosos = consultasAseguradoManager.obtieneEndososPoliza(poliza, asegurado);
    		logger.debug("Resultados de la consulta de endosos: {}" , datosEndosos.size());
    		success = true;
    	} catch(Exception e){
    		logger.error("Error al obtener las clausulas de la poliza",e);
    		return SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    /**
     * Obtiene las enfermedades cronicas
     * @return String result
     */    
    public String consultaDatosEnfermedades(){
    	logger.debug(" **** Entrando a consultaDatosEnfermedades ****");
    	PolizaVO poliza = new PolizaVO();
    	poliza.setCdunieco(params.get("cdunieco"));
    	poliza.setCdramo(params.get("cdramo"));
    	poliza.setEstado(params.get("estado"));
    	poliza.setNmpoliza(params.get("nmpoliza"));
    	poliza.setNmsuplem(params.get("suplemento"));
    	poliza.setNmsituac(params.get("nmsituac"));
    	poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
    	try {    		
    		logger.debug("El asegurado a buscar es: {}" , params.get("cdperson"));
    		datosEnfermedades = consultasAseguradoManager.obtieneEnfermedades(poliza, asegurado);
    		success = true;
    	} catch(Exception e){
    		logger.error("Error al obtener las enfermedades cronicas",e);
    		return SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    /**
     * Obtiene los datos del plan
     * @return String result
     */
    public String consultaDatosPlan(){
    	logger.debug(" **** Entrando a Consulta de Plan ****");
        try {
			        	
        	PolizaVO polizaVO = new PolizaVO();
        	polizaVO.setCdunieco(params.get("cdunieco"));
        	polizaVO.setCdramo(params.get("cdramo"));
        	polizaVO.setEstado(params.get("estado"));
        	polizaVO.setNmpoliza(params.get("nmpoliza"));
        	polizaVO.setIcodpoliza(params.get("icodpoliza"));
        	
        	List<PlanVO> lista = consultasAseguradoManager.obtieneDatosPlan(polizaVO);
        	
        	if(lista!=null && !lista.isEmpty())	datosPlan = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de plan: {}" , datosPlan);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos del plan ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * Obtiene los copagos de una poliza
     * @return String result
     */
    public String consultaCopagosPoliza(){
    	logger.debug(" **** Entrando a consultaCopagosPoliza ****");
    	try {
    		    		
    		PolizaVO poliza = new PolizaVO();
    		poliza.setCdunieco(params.get("cdunieco"));
    		poliza.setCdramo(params.get("cdramo"));
    		poliza.setEstado(params.get("estado"));
    		poliza.setNmpoliza(params.get("nmpoliza"));
    		poliza.setNmsuplem(params.get("suplemento"));
    		poliza.setNmsituac(params.get("nmsituac"));
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		
    		datosCopagosPoliza = consultasAseguradoManager.obtieneCopagosPoliza(poliza);
    		   		
    		
    		logger.debug("Resultado de consultaCopagosPoliza: {}" , datosCopagosPoliza);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los copagos de la poliza ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene las coberturas
     * @return String result
     */
    public String consultaCoberturasPoliza(){
    	logger.debug(" **** Entrando a consultaCoberturasPoliza ****");
    	try {
    		    		
    		PolizaVO poliza = new PolizaVO();
    		poliza.setCdunieco(params.get("cdunieco"));
    		poliza.setCdramo(params.get("cdramo"));
    		poliza.setEstado(params.get("estado"));
    		poliza.setNmpoliza(params.get("nmpoliza"));    		
    		poliza.setNmsituac(params.get("nmsituac"));
    		poliza.setNmsuplem(params.get("suplemento"));
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		datosCoberturasPoliza = consultasAseguradoManager.obtieneCoberturasPoliza(poliza);
    		   		
    		
    		logger.debug("Resultado de consultaCoberturas: {}" , datosCoberturasPoliza);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener las coberturas",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene las coberturas basicas
     * @return String result
     */
    public String consultaCoberturasBasicas(){
    	logger.debug(" **** Entrando a consultaConsultaCoberturasBasicas ****");
    	try {
    		    		
    		PolizaVO poliza = new PolizaVO();
    		poliza.setCdunieco(params.get("cdunieco"));
    		poliza.setCdramo(params.get("cdramo"));
    		poliza.setEstado(params.get("estado"));
    		poliza.setNmpoliza(params.get("nmpoliza"));
    		poliza.setNmsuplem(params.get("suplemento"));
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		
    		datosCoberturasBasicas = consultasAseguradoManager.obtieneCoberturasBasicas(poliza);
    		   		
    		
    		logger.debug("Resultado de consultaCoberturasBasicas: {}" , datosCoberturasBasicas);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener las coberturas basicas ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene los datos del historico de una p�liza
     * @return String result
     */
    public String consultaDatosHistorico(){
    	logger.debug(" **** Entrando a consultaDatosHistorico ****");
    	mensajeRes = "";
    	try {
    		    		
    		PolizaAseguradoVO poliza = new PolizaAseguradoVO();
    		poliza.setIcodpoliza(params.get("icodpoliza"));
    		poliza.setNmpoliex(params.get("nmpoliex"));
    		poliza.setCdramo(params.get("cdramo"));
    		AseguradoVO asegurado = new AseguradoVO();
    		asegurado.setCdperson(params.get("cdperson"));
    		datosHistorico = consultasAseguradoManager.obtieneHistoricoAsegurado(poliza,asegurado);
    		
    		logger.debug("Resultado de la consultaDatosHistorico: {}" , datosHistorico);
    		    		   		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaDatosHistorico ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene los datos del avisos anteriores
     * @return String result
     */
    public String consultaAvisosAnteriores(){
    	logger.debug(" **** Entrando a consultaAvisosAnteriores ****");
    	PolizaVO poliza = new PolizaVO();
    	poliza.setIcodpoliza(params.get("icodpoliza"));
    	AseguradoVO asegurado = new AseguradoVO();
    	asegurado.setCdperson(params.get("cdperson"));
    	try{    		
    		datosAvisosAnteriores = consultasAseguradoManager.obtieneAvisosAnteriores(poliza, asegurado);
    		logger.debug("Resultado de la consultaAvisosAnteriores: {}" , datosAvisosAnteriores);
    	}catch(Exception e){
    		logger.error("Error al obtener avisos anteriores.",e);
    		return 	SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    /**
     * Obtiene el historico de farmacia
     * @return String result
     */	
    public String consultaDatosHistoricoFarmacia(){
    	logger.debug(" **** Entrando a consultaDatosHistoricoFarmacia ****");
    	PolizaVO poliza = new PolizaVO();
    	poliza.setCdunieco(params.get("cdunieco"));
    	poliza.setCdramo(params.get("cdramo"));
    	poliza.setEstado(params.get("estado"));
    	poliza.setNmpoliza(params.get("nmpoliza"));
    	poliza.setNmsuplem(params.get("suplemento"));
    	poliza.setNmsituac(params.get("nmsituac"));
    	poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
    	try {
    		historicoFarmacia = consultasAseguradoManager.obtieneHistoricoFarmacia(poliza, asegurado);
    		success = true;
    	} catch(Exception e){
    		logger.error("Error al obtener el historico de farmacia",e);
    		return SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    /**
     * Obtiene periodos de vigencia
     * @return String result
     */	
    public String consultaPeriodosVigencia(){
    	logger.debug(" **** Entrando a consultaPeriodosVigencia ****");
    	PolizaVO poliza = new PolizaVO();
    	poliza.setCdunieco(params.get("cdunieco"));
    	poliza.setCdramo(params.get("cdramo"));
    	poliza.setEstado(params.get("estado"));
    	poliza.setNmpoliza(params.get("nmpoliza"));
    	poliza.setNmsuplem(params.get("suplemento"));
    	poliza.setNmsituac(params.get("nmsituac"));
    	poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
    	try {
    		
    		periodosVigencia = consultasAseguradoManager.obtienePeriodosVigencia(poliza, asegurado);
    		success = true;
    	} catch(Exception e){
    		logger.error("Error al obtener periodos de vigencia",e);
    		return SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    public String consultaHospitales(){
    	logger.debug("***Entrando a consultaHospitales***");
    	PolizaVO poliza = new PolizaVO();
    	String filtro = params.get("hospital");
    	poliza.setIcodpoliza(params.get("icodpoliza"));
    	try{
    		datosHospitales = consultasAseguradoManager.obtieneHospitales(filtro,poliza);
    	}catch(Exception e){
    		logger.error("Error al obtener hospitales",e);
    		return 	SUCCESS;
    	}
    	success = true;
    	return SUCCESS;
    }
    
    public String enviarAvisoHospitalizacion() {
    	logger.debug("***Entrando a enviarAvisoHospitalizacion***");
    	AvisoHospitalizacionVO aviso = new AvisoHospitalizacionVO();
    	UserVO usuario = (UserVO)session.get("USUARIO");
    	String telefonoAgente="";
    	String mensajeAgente="";
    	boolean smsSend, mailSend;
		String [] mailSms = { "smsgs@gseguros.com.mx" };
		String [] mails = { "ahernandezc@gsalud.com.mx","nlromeroc@gsalud.com.mx","jvargas@gsalud.com.mx" };
		String [] adjuntos = new String[0];
		mensajeAgente = "EL AFILIADO(" + params.get("cdperson") + "), " + params.get("nombre") +
                ", DE LA POLIZA DE GSALUD: (" + params.get("cdunieco") + "/ ";
				if(StringUtils.isNotBlank(params.get("icodpoliza"))){
					mensajeAgente+=(params.get("cdramo"));
				}else{
					mensajeAgente+=(params.get("cdsubram"));
				}
				mensajeAgente+="/ " + params.get("nmpoliza") + "), SE ENCUENTRA HOSPITALIZADO.";
    	PolizaVO poliza = new PolizaVO();
    	poliza.setIcodpoliza(params.get("icodpoliza"));
    	aviso.setCdperson(params.get("cdperson"));
    	aviso.setNmpoliza(params.get("nmpoliza"));
    	aviso.setCdagente(params.get("cdagente"));
    	aviso.setCdpresta(params.get("hospital"));
    	aviso.setFeingreso(params.get("feingreso"));
    	aviso.setCdusuari(usuario.getUser());
    	aviso.setComentario(params.get("comentario"));
    	try{
    		iCodAviso = consultasAseguradoManager.enviarAvisoHospitalizacion(aviso, poliza);
			telefonoAgente = consultasAseguradoManager.consultaTelefonoAgente(params.get("cdagente"));
			logger.debug("El telefono del agente {} es: {}", params.get("cdagente"), telefonoAgente);
			if(StringUtils.isBlank(telefonoAgente)) {
				throw new ApplicationException("3");
			}
			smsSend = mailServiceForSms.enviaCorreo(mailSms, null, null, telefonoAgente, mensajeAgente, adjuntos, false);
			mailSend = mailService.enviaCorreo(mails, null, null, "Aviso de Hospitalizacion", mensajeAgente, adjuntos, false);
    		if(!smsSend && !mailSend)
    		{
    			throw new ApplicationException("4");
    		}else{
    			consultasAseguradoManager.actualizaEstatusEnvio(iCodAviso, poliza);
			}
    		mensajeRes = "Se registr\u00f3 y envi\u00f3 Aviso de Hospitalizaci\u00f3n.";
    		success = true;
    		
    	}catch(Exception e){
    		mensajeRes = e.getMessage();
    		logger.error(e.getMessage(),e);
    	}
    	logger.debug("***Saliendo de enviarAvisoHospitalizacion***");
    	return SUCCESS;
    }
    
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void setParam(Map<String, String> params) {
		this.params = params;
	}
	public String getParamsJson() {
		try {
			return JSONUtil.serialize(params);
		} catch (Exception e) {
			logger.error("Error al generar JSON de params",e);
			return null;
		}
	}

	public List<?> getResultados() {
		return resultados;
	}

	public void setResultados(List<?> resultados) {
		this.resultados = resultados;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMensajeRes() {
		return mensajeRes;
	}

	public void setMensajeRes(String mensajeRes) {
		this.mensajeRes = mensajeRes;
	}

	public String getiCodAviso() {
		return iCodAviso;
	}

	public void setiCodAviso(String iCodAviso) {
		this.iCodAviso = iCodAviso;
	}

	public boolean isUsuarioCallCenter() {
		return usuarioCallCenter;
	}

	public void setUsuarioCallCenter(boolean usuarioCallCenter) {
		this.usuarioCallCenter = usuarioCallCenter;
	}

	public List<ConsultaResultadosAseguradoVO> getResultadosAsegurado() {
		return resultadosAsegurado;
	}

	public void setResultadosAsegurado(
			List<ConsultaResultadosAseguradoVO> resultadosAsegurado) {
		this.resultadosAsegurado = resultadosAsegurado;
	}

	public List<ConsultaPolizaActualVO> getDatosPolizaActual() {
		return datosPolizaActual;
	}

	public void setDatosPolizaActual(List<ConsultaPolizaActualVO> datosPolizaActual) {
		this.datosPolizaActual = datosPolizaActual;
	}

	public ConsultaDatosComplementariosVO getDatosComplementarios() {
		return datosComplementarios;
	}

	public void setDatosComplementarios(
			ConsultaDatosComplementariosVO datosComplementarios) {
		this.datosComplementarios = datosComplementarios;
	}

	public ConsultaDatosGeneralesPolizaVO getDatosPoliza() {
		return datosPoliza;
	}

	public void setDatosPoliza(ConsultaDatosGeneralesPolizaVO datosPoliza) {
		this.datosPoliza = datosPoliza;
	}

	public ContratanteVO getDatosContratante() {
		return datosContratante;
	}

	public void setDatosContratante(ContratanteVO datosContratante) {
		this.datosContratante = datosContratante;
	}

	public List<AseguradoVO> getDatosAsegurados() {
		return datosAsegurados;
	}

	public void setDatosAsegurados(List<AseguradoVO> datosAsegurados) {
		this.datosAsegurados = datosAsegurados;
	}

	public PlanVO getDatosPlan() {
		return datosPlan;
	}

	public void setDatosPlan(PlanVO datosPlan) {
		this.datosPlan = datosPlan;
	}

	public List<CopagoVO> getDatosCopagosPoliza() {
		return datosCopagosPoliza;
	}

	public void setDatosCopagosPoliza(List<CopagoVO> datosCopagosPoliza) {
		this.datosCopagosPoliza = datosCopagosPoliza;
	}

	public List<CoberturaBasicaVO> getDatosCoberturasPoliza() {
		return datosCoberturasPoliza;
	}

	public void setDatosCoberturasPoliza(
			List<CoberturaBasicaVO> datosCoberturasPoliza) {
		this.datosCoberturasPoliza = datosCoberturasPoliza;
	}

	public List<CoberturaBasicaVO> getDatosCoberturasBasicas() {
		return datosCoberturasBasicas;
	}

	public void setDatosCoberturasBasicas(
			List<CoberturaBasicaVO> datosCoberturasBasicas) {
		this.datosCoberturasBasicas = datosCoberturasBasicas;
	}

	public List<HistoricoVO> getDatosHistorico() {
		return datosHistorico;
	}

	public void setDatosHistorico(List<HistoricoVO> datosHistorico) {
		this.datosHistorico = datosHistorico;
	}

	public List<HistoricoFarmaciaVO> getHistoricoFarmacia() {
		return historicoFarmacia;
	}

	public void setHistoricoFarmacia(List<HistoricoFarmaciaVO> historicoFarmacia) {
		this.historicoFarmacia = historicoFarmacia;
	}

	public List<PeriodoVigenciaVO> getPeriodosVigencia() {
		return periodosVigencia;
	}

	public void setPeriodosVigencia(
			List<PeriodoVigenciaVO> periodosVigencia) {
		this.periodosVigencia = periodosVigencia;
	}

	public List<BaseVO> getDatosHospitales() {
		return datosHospitales;
	}

	public void setDatosHospitales(List<BaseVO> datosHospitales) {
		this.datosHospitales = datosHospitales;
	}

	public List<AvisoHospitalizacionVO> getDatosAvisosAnteriores() {
		return datosAvisosAnteriores;
	}

	public void setDatosAvisosAnteriores(List<AvisoHospitalizacionVO> datosAvisosAnteriores) {
		this.datosAvisosAnteriores = datosAvisosAnteriores;
	}

	public List<AseguradoDetalleVO> getDatosAseguradoDetalle() {
		return datosAseguradoDetalle;
	}

	public void setDatosAseguradoDetalle(
			List<AseguradoDetalleVO> datosAseguradoDetalle) {
		this.datosAseguradoDetalle = datosAseguradoDetalle;
	}

	public List<EndosoVO> getDatosEndosos() {
		return datosEndosos;
	}

	public void setDatosEndosos(List<EndosoVO> datosEndosos) {
		this.datosEndosos = datosEndosos;
	}
	
	public List<EnfermedadVO> getDatosEnfermedades() {
		return datosEnfermedades;
	}

	public void setDatosEnfermedades(List<EnfermedadVO> datosEnfermedades) {
		this.datosEnfermedades = datosEnfermedades;
	}

	public ConsultaDatosTitularVO getDatosTitular() {
		return datosTitular;
	}

	public void setDatosTitular(ConsultaDatosTitularVO datosTitular) {
		this.datosTitular = datosTitular;
	}
	
	
	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public List<GenericVO> getListaCatalogo() {
		return listaCatalogo;
	}

	public void setListaCatalogo(List<GenericVO> listaCatalogo) {
		this.listaCatalogo = listaCatalogo;
	}

	public List<Map<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<Map<String, String>> loadList) {
		this.loadList = loadList;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getRutaServidorReports() {
		return rutaServidorReports;
	}


	public void setRutaServidorReports(String rutaServidorReports) {
		this.rutaServidorReports = rutaServidorReports;
	}


	public String getPassServidorReports() {
		return passServidorReports;
	}


	public void setPassServidorReports(String passServidorReports) {
		this.passServidorReports = passServidorReports;
	}


	public String getRutaDocumentosPoliza() {
		return rutaDocumentosPoliza;
	}


	public void setRutaDocumentosPoliza(String rutaDocumentosPoliza) {
		this.rutaDocumentosPoliza = rutaDocumentosPoliza;
	}
}
