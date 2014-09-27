package mx.com.gseguros.portal.siniestros.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.controller.MesaControlAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoPago;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DetalleSiniestroAction.class);

	private transient SiniestrosManager siniestrosManager;
	private PantallasManager       pantallasManager;
	private KernelManagerSustituto kernelManager;
	
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");

	private boolean success;

	private Map<String, String> loadForm;
	
	private List<Map<String, String>> loadList;
    private List<Map<String, String>> saveList;
    private List<Map<String, String>> deleteList;
    private Map<String, String> parametros;
	private HashMap<String, String> params;
	private HashMap<String,Object> paramsO;
	
	private Map<String,Item> imap;
	
	private List<Map<String, String>> siniestro;
	
	private List<HistorialSiniestroVO> historialSiniestro;
	
	
	public String execute() throws Exception
	{
		
    	success = true;
    	return SUCCESS;
    }
	
	public String detalleSiniestro() throws Exception
	{
		logger.debug(""
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n######                        ######"
				);
		logger.debug("params:"+params);
		UserVO usuario= (UserVO)session.get("USUARIO");
		params.put("cdrol", usuario.getRolActivo().getObjeto().getValue());
		
		if(!params.containsKey("nmsinies"))
		{
			try{
				String ntramite = params.get("ntramite");
				usuario= (UserVO)session.get("USUARIO");
				params.put("cdrol", usuario.getRolActivo().getObjeto().getValue());
				
				Map<String,String> paramsRes = (HashMap<String, String>) siniestrosManager.obtenerLlaveSiniestroReembolso(ntramite);
				
				for(Entry<String,String>en:paramsRes.entrySet()){
					params.put(en.getKey().toLowerCase(),en.getValue());
					params.put("cdrol", usuario.getRolActivo().getObjeto().getValue());
				}
				
			}catch(Exception ex){
				logger.error("error al obtener clave de siniestro para la pantalla del tabed panel",ex);
			}
		}
		
		logger.debug("params obtenidos:"+params);
		logger.debug(""
				+ "\n######                        ######"
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		success = true;
		return SUCCESS;
	}
	
	
	public String loadInfoGeneralReclamacion() {
		success = true;
		return SUCCESS;
	}
	
	
	public String entradaRevisionAdmin(){
	   	
	   	try {
	   		logger.debug("Obteniendo Columnas dinamicas de Revision Administrativa");
	   		
	   		UserVO usuario  = (UserVO)session.get("USUARIO");
	    	String cdrol    = usuario.getRolActivo().getObjeto().getValue();
	    	String pantalla = "AFILIADOS_AGRUPADOS";
	    	String seccion  = "COLUMNAS";
	    	String cdunieco  = params.get("cdunieco");
	    	String cdramo    = params.get("cdramo");
	    	String estado    = params.get("estado");
	    	String nmpoliza  = params.get("nmpoliza");
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	for(ComponenteVO com:componentes)
	    	{
	    		com.setWidth(100);
	    	}
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	imap = new HashMap<String,Item>();
	    	
	    	List<ComponenteVO>tatrisin=kernelManager.obtenerTatrisinPoliza(cdunieco,cdramo,estado,nmpoliza);
	    	gc.generaComponentes(tatrisin, true, false, true, false, false, false);
	    	imap.put("tatrisinItems",gc.getItems());
	    	
	    	gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	gc.generaComponentes(componentes, true, false, false, true,false, false);
	    	
	    	imap.put("gridColumns",gc.getColumns());
	    	
	    	pantalla = "DETALLE_FACTURA";
	    	seccion  = "BOTONES_CONCEPTOS";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, false, false,false, true);
	    	
	    	imap.put("conceptosButton",gc.getButtons());
	    	
	    	seccion = "FORM_EDICION";
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	imap.put("itemsEdicion",gc.getItems());
	   		
	    	pantalla = "RECHAZO_SINIESTRO";
	    	seccion  = "FORMULARIO";
	    	componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	imap.put("itemsCancelar",gc.getItems());
	    	
	   		logger.debug("Resultado: "+imap);
	   		//siniestrosManager.guardaListaTramites(params, deleteList, saveList);
	   	}catch( Exception e){
	   		logger.error("Error en guardaListaTramites",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	   }
	
	public String loadListaFacturasTramite(){
	   	try {
	   			loadList = siniestrosManager.P_GET_FACTURAS_SINIESTRO(params.get("cdunieco"), params.get("cdramo"), params.get("estado"), params.get("nmpoliza"), params.get("nmsuplem"), params.get("nmsituac"), params.get("aaapertu"), params.get("status"), params.get("nmsinies")); 
		   		
	   	}catch( Exception e){
	   		logger.error("Error en loadListaFacturasTramite",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	}
	
	public String guardaFacturaTramite() throws ApplicationException{
		logger.debug(""
		+ "\n######################################"
		+ "\n######################################"
		+ "\n###### GUARDA FACTURA X TRAMITE ######"
		+ "\n######                          ######"
		);
		logger.debug("parametros de entrada: "+ params);
		Map<String,Object>paramsTvalosin = new HashMap<String,Object>();
		paramsTvalosin.put("pv_cdunieco"  , params.get("cdunieco"));
		paramsTvalosin.put("pv_cdramo"    , params.get("cdramo"));
		paramsTvalosin.put("pv_aaapertu"    , params.get("aaapertu"));
		paramsTvalosin.put("pv_status"    , params.get("status"));
		paramsTvalosin.put("pv_nmsinies"    , params.get("nmsinies"));
		paramsTvalosin.put("pv_cdtipsit"    , params.get("cdtipsit"));
		paramsTvalosin.put("pv_nmsuplem"    , params.get("nmsuplem"));
		paramsTvalosin.put("pv_cdusuari"    , null);
		paramsTvalosin.put("pv_feregist"    , null);
		paramsTvalosin.put("pv_otvalor01"    , parametros.get("pv_otvalor01"));
		paramsTvalosin.put("pv_otvalor02"    , parametros.get("pv_otvalor02"));
		paramsTvalosin.put("pv_accion_i"	, params.get("operacion"));
        kernelManager.PMovTvalosin(paramsTvalosin);
		
		//realizamos el llamado al guardado
		
		String cdunieco  = params.get("cdunieco");
		String cdramo    = params.get("cdramo");
		String estado    = params.get("estado");
		String nmpoliza  = params.get("nmpoliza");
		String nmsituac  = params.get("nmsituac");
		String nmsuplem  = params.get("nmsuplem");
		String status    = params.get("status");
		String aaapertu  = params.get("aaapertu");
		String nmsinies  = params.get("nmsinies");
		String nfactura  = params.get("nfactura");
		
		String autrecla = params.get("autrecla");
		String commenar = params.get("commenar");
		String autmedic = params.get("autmedic");
		String commenme = params.get("commenme");
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdrol    = usuario.getRolActivo().getObjeto().getValue();
		
		logger.debug("Guarda Factura, Rol Sistema: "+cdrol);
	   	
	   	try {
	   		siniestrosManager.guardaListaFacMesaControl(params.get("ntramite"), params.get("nfactura"), params.get("fefactura"), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"),params.get("tipoMoneda"),params.get("tasacamb"),params.get("ptimporta"),params.get("dctonuex"),null);
	   		
	   		
	   			siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	   					null,null,null,null,null,
	    				Constantes.MAUTSINI_AREA_RECLAMACIONES, autrecla, Constantes.MAUTSINI_FACTURA, commenar, Constantes.INSERT_MODE);
	   		
	   			siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	   					null,null,null,null,null,
	    				Constantes.MAUTSINI_AREA_MEDICA, autmedic, Constantes.MAUTSINI_FACTURA, commenme, Constantes.INSERT_MODE);
	   		
	   	}catch( Exception e){
	   		logger.error("Error en guardaListaTramites",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	
	   	logger.debug(""
		+ "\n######                           ######"
		+ "\n###### GUARDA FACTURA X TRAMITE  ######"
		+ "\n#######################################"
		+ "\n#######################################"
		);
	   	success = true;
	   	return SUCCESS;
	}
	
	public String actualizaFacturaTramite(){
		logger.debug(""
		+ "\n######################################"
		+ "\n######################################"
		+ "\n###### 	ACTUALIZA FACT      #######"
		+ "\n######                         #######"
		);
		logger.debug("parametros de entrada modificacion : "+ params);
		String cdunieco  = params.get("cdunieco");
		String cdramo    = params.get("cdramo");
		String estado    = params.get("estado");
		String nmpoliza  = params.get("nmpoliza");
		String nmsituac  = params.get("nmsituac");
		String nmsuplem  = params.get("nmsuplem");
		String status    = params.get("status");
		String aaapertu  = params.get("aaapertu");
		String nmsinies  = params.get("nmsinies");
		String nfactura  = params.get("nfactura");
		
		String autrecla = params.get("autrecla");
		String commenar = params.get("commenar");
		String autmedic = params.get("autmedic");
		String commenme = params.get("commenme");
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdrol    = usuario.getRolActivo().getObjeto().getValue();
		
		logger.debug("Actuliza Factura, Rol Sistema: "+cdrol);
		try {
			siniestrosManager.movFacMesaControl(params.get("ntramite"), params.get("nfactura"), params.get("fefactura"), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"), Constantes.UPDATE_MODE,params.get("tipoMoneda"),params.get("tasacamb"),params.get("ptimporta"),params.get("dctonuex"));
			
			
				siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	   					null,null,null,null,null,
	    				Constantes.MAUTSINI_AREA_RECLAMACIONES, autrecla, Constantes.MAUTSINI_FACTURA, commenar, Constantes.UPDATE_MODE);
			
				siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	   					null,null,null,null,null,
	    				Constantes.MAUTSINI_AREA_MEDICA, autmedic, Constantes.MAUTSINI_FACTURA, commenme, Constantes.UPDATE_MODE);
			
				
				Map<String,Object>paramsTvalosin = new HashMap<String,Object>();
				paramsTvalosin.put("pv_cdunieco"  , params.get("cdunieco"));
				paramsTvalosin.put("pv_cdramo"    , params.get("cdramo"));
				paramsTvalosin.put("pv_aaapertu"    , params.get("aaapertu"));
				paramsTvalosin.put("pv_status"    , params.get("status"));
				paramsTvalosin.put("pv_nmsinies"    , params.get("nmsinies"));
				paramsTvalosin.put("pv_cdtipsit"    , params.get("cdtipsit"));
				paramsTvalosin.put("pv_nmsuplem"    , params.get("nmsuplem"));
				paramsTvalosin.put("pv_cdusuari"    , null);
				paramsTvalosin.put("pv_feregist"    , null);
				paramsTvalosin.put("pv_otvalor01"    , parametros.get("pv_otvalor01"));
				paramsTvalosin.put("pv_otvalor02"    , parametros.get("pv_otvalor02"));
				paramsTvalosin.put("pv_otvalor03"    , parametros.get("pv_otvalor03"));
				paramsTvalosin.put("pv_accion_i","I");
		        kernelManager.PMovTvalosin(paramsTvalosin);
		        
			
			boolean cancela     = StringUtils.isNotBlank(params.get("cancelar"));
    		String  cdmotivo    = params.get("cdmotivo");
    		String  rechazoCome = params.get("rechazocomment");
    		
    		if(cancela)
    		{
    			String ntramite = params.get("ntramite");
    			Map<String,String> tramiteCompleto = siniestrosManager.obtenerTramiteCompleto(ntramite);
    			String tipoPago = tramiteCompleto.get("OTVALOR02");
    			Boolean rolMedico = null;
    			if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO_AJUSTADOR.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.TRUE;
    			}
    			else if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_SINIESTROS.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.OPERADOR_SINIESTROS.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.FALSE;
    			}
    			
    			if(rolMedico==null)
    			{
    				throw new Exception("El usuario actual no puede cancelar");
    			}
    			
    			MesaControlAction mca = new MesaControlAction();
    			mca.setKernelManager(kernelManager);
    			mca.setSession(session);
    			Map<String,String>smap1=new HashMap<String,String>();
    			smap1.put("ntramite" , ntramite);
    			smap1.put("status"   , EstatusTramite.RECHAZADO.getCodigo());
    			smap1.put("cdmotivo" , cdmotivo);
    			smap1.put("comments" , rechazoCome);
    			mca.setSmap1(smap1);
    			mca.actualizarStatusTramite();
    			if(!mca.isSuccess())
    			{
    				throw new Exception("Error al cancelar el trámite");
    			}
    			
    			String nombreReporte = null;
    			String nombreArchivo = null;
    			if(rolMedico)
    			{
    				nombreReporte = getText("rdf.siniestro.cartarechazo.medico.nombre");
    				nombreArchivo = getText("pdf.siniestro.rechazo.medico.nombre");
    			}
    			else//cancelacion por area de reclamaciones
    			{
    				boolean esReembolso = tipoPago.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo());
    				if(esReembolso)
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.reembolso.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.reemb.nombre");
    				}
    				else
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.pagodirecto.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.pdir.nombre");
    				}
    			}
    			
    			File carpeta=new File(getText("ruta.documentos.poliza") + "/" + ntramite);
    			if(!carpeta.exists())
    			{
    				logger.debug("no existe la carpeta::: "+ntramite);
    				carpeta.mkdir();
    				if(carpeta.exists())
    				{
    					logger.debug("carpeta creada");
    				}
    				else
    				{
    					logger.debug("carpeta NO creada");
    				}
    			}
    			else
    			{
    				logger.debug("existe la carpeta   ::: "+ntramite);
    			}
    			
    			String urlContrareciboSiniestro = ""
    					+ getText("ruta.servidor.reports")
    					+ "?p_usuario="  + usuario.getUser()
    					+ "&P_NTRAMITE=" + ntramite
    					+ "&userid="     + getText("pass.servidor.reports")
    					+ "&report="     + nombreReporte
    					+ "&destype=cache"
    					+ "&desformat=PDF"
    					+ "&ACCESSIBLE=YES"
    					+ "&paramform=no";
    			String pathArchivo=""
    					+ getText("ruta.documentos.poliza")
    					+ "/" + ntramite
    					+ "/" + nombreArchivo;
    			
    			HttpUtil.generaArchivo(urlContrareciboSiniestro, pathArchivo);
    	        
    			Map<String,Object>paramsDocupol = new HashMap<String,Object>();
    			paramsDocupol.put("pv_cdunieco_i"  , cdunieco);
    			paramsDocupol.put("pv_cdramo_i"    , cdramo);
    			paramsDocupol.put("pv_estado_i"    , estado);
    			paramsDocupol.put("pv_nmpoliza_i"  , nmpoliza);
    			paramsDocupol.put("pv_nmsuplem_i"  , nmsuplem);
    			paramsDocupol.put("pv_feinici_i"   , new Date());
    			paramsDocupol.put("pv_cddocume_i"  , nombreArchivo);
    			paramsDocupol.put("pv_dsdocume_i"  , "Carta rechazo");
    			paramsDocupol.put("pv_ntramite_i"  , ntramite);
    			paramsDocupol.put("pv_nmsolici_i"  , null);
    			paramsDocupol.put("pv_tipmov_i"    , tipoPago);
    			paramsDocupol.put("pv_swvisible_i" , Constantes.SI);
    			paramsDocupol.put("pv_codidocu_i"  , null);
    			paramsDocupol.put("pv_cdtiptra_i"  , TipoTramite.SINIESTRO.getCdtiptra());
    	        kernelManager.guardarArchivo(paramsDocupol);
    		}
    		
		}catch( Exception e){
			logger.error("Error en actualizaFacturaTramite",e);
			success =  false;
			return SUCCESS;
		}
		
		logger.debug(""
		+ "\n######                           ######"
		+ "\n###### 	ACTUALIZA FACTURA	  ######"
		+ "\n#######################################"
		+ "\n#######################################"
		);
		success = true;
		return SUCCESS;
	}

	public String borraFacturaTramite(){
		
		try {
			siniestrosManager.movFacMesaControl(params.get("ntramite"), params.get("nfactura"), null, null, null, null, null, null, null, null, Constantes.DELETE_MODE,null,null,null,null);
		}catch( Exception e){
			logger.error("Error en borraFacturaTramite",e);
			success =  false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	
	public String obtieneDatosGeneralesSiniestro() throws Exception {
		try {
			siniestro = siniestrosManager.obtieneDatosGeneralesSiniestro(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsituac"), params.get("nmsuplem"),
					params.get("status"), params.get("aaapertu"),
					params.get("nmsinies"), params.get("ntramite"));
			success = true;
		}catch(Exception e){
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	
	public String actualizaDatosGeneralesSiniestro() throws Exception {
		try {
			Map<String,Object> pMesaCtrl = new HashMap<String,Object>();
			pMesaCtrl.put("pv_ntramite_i", params.get("ntramite"));
			pMesaCtrl.put("pv_cdsucadm_i", params.get("cdsucadm"));
			pMesaCtrl.put("pv_cdsucdoc_i", params.get("cdsucdoc"));
			logger.debug("pMesaCtrl=" + pMesaCtrl);
    		siniestrosManager.actualizaOTValorMesaControl(pMesaCtrl);
			
			Date dFeocurre = renderFechas.parse(params.get("feocurre"));
            siniestrosManager.actualizaDatosGeneralesSiniestro(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsuplem"),params.get("aaapertu"),
					params.get("nmsinies"), dFeocurre,
					params.get("nmreclamo"), params.get("cdicd"),
					params.get("cdicd2"), params.get("cdcausa"));
			success = true;
		} catch(Exception e) {
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	
	public String obtieneHistorialReclamaciones() throws Exception {
		try {
			// Dummy data:
			
			// TODO: Terminar cuando este listo el SP
			historialSiniestro = siniestrosManager.obtieneHistorialReclamaciones(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsituac"), params.get("nmsuplem"),
					params.get("status"), params.get("aaapertu"),
					params.get("nmsinies"), params.get("ntramite"));
			
			success = true;
		} catch(Exception e) {
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	public String cargaHistorialSiniestros(){
	   	try {
	   			loadList = siniestrosManager.cargaHistorialSiniestros(params); 
	   	}catch( Exception e){
	   		logger.error("Error en loadListaFacturasTramite",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	}
	
	// Getters and setters:

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLoadForm() {
		try {
			return JSONUtil.serialize(loadForm);
		} catch (Exception e) {
			logger.error("Error al generar JSON de LoadForm",e);
			return null;
		}
	}

	public void setLoadForm(HashMap<String, String> loadForm) {
		this.loadForm = loadForm;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	
	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public String getParamsJson() {
		try {
			return JSONUtil.serialize(params);
		} catch (Exception e) {
			logger.error("Error al generar JSON de params",e);
			return null;
		}
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}


	public List<Map<String, String>> getLoadList() {
		return loadList;
	}


	public void setLoadList(List<Map<String, String>> loadList) {
		this.loadList = loadList;
	}


	public List<Map<String, String>> getSaveList() {
		return saveList;
	}


	public void setSaveList(List<Map<String, String>> saveList) {
		this.saveList = saveList;
	}


	public HashMap<String, Object> getParamsO() {
		return paramsO;
	}


	public void setParamsO(HashMap<String, Object> paramsO) {
		this.paramsO = paramsO;
	}


	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}


	public Map<String, Item> getImap() {
		return imap;
	}


	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public List<Map<String, String>> getSiniestro() {
		return siniestro;
	}

	public void setSiniestro(List<Map<String, String>> siniestro) {
		this.siniestro = siniestro;
	}


	public List<HistorialSiniestroVO> getHistorialSiniestro() {
		return historialSiniestro;
	}


	public void setHistorialSiniestro(List<HistorialSiniestroVO> historialSiniestro) {
		this.historialSiniestro = historialSiniestro;
	}


	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}
	
}