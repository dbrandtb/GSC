package mx.com.gseguros.portal.siniestros.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.MesaControlVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	static final Logger logger = LoggerFactory.getLogger(DetalleSiniestroAction.class);
	private transient SiniestrosManager siniestrosManager;
	private PantallasManager       pantallasManager;
	private KernelManagerSustituto kernelManager;
	
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");

	private boolean success;

	private Map<String, String> loadForm;
	private List<Map<String, String>> loadList;
	private List<Map<String, String>> saveList;
	private Map<String, String> parametros;
	private HashMap<String, String> params;
	private HashMap<String,Object> paramsO;
	private String                   mensaje;
	private Map<String,Item> imap;
	private List<Map<String, String>> siniestro;
	private List<HistorialSiniestroVO> historialSiniestro;
	
	
	public String execute() throws Exception{
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que visualiza la informacion del historial de reclamaciones
	* @param params
	* @return Historial de reclamaciones de siniestros
	*/
	public String cargaHistorialSiniestros(){
		logger.debug(""
			+ "\n######################################"
			+ "\n###### cargaHistorialSiniestros ######"
		);
		logger.debug("Params: {}", params);
		try {
			loadList = siniestrosManager.cargaHistorialSiniestros(params); 
		}catch( Exception e){
			logger.error("Error en loadListaFacturasTramite : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### cargaHistorialSiniestros ######"
			+ "\n######################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion para asociar al Siniestro Maestro para GMMI
	* @param cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac,aaapertu, status, nmsinies, nmsiniesRef
	* @return exito si se asocia el siniestro
	*/
	public String asociaMsiniestroReferenciado() throws Exception {
		logger.debug(""
			+ "\n##########################################"
			+ "\n###### asociaMsiniestroReferenciado ######"
		);
		logger.debug("Params: {}", params);
		try {
			siniestrosManager.actualizaMsiniestroReferenciado(
				params.get("cdunieco"), params.get("cdramo"),
				params.get("estado"), params.get("nmpoliza"),
				params.get("nmsuplem"),params.get("nmsituac"),
				params.get("aaapertu"),params.get("status"),
				params.get("nmsinies"), params.get("nmsiniesRef"));
			success = true;
		} catch(Exception e) {
			logger.error("Error en actualizaDatosGeneralesSiniestro : {}", e.getMessage(), e);
		}
		logger.debug(""
			+ "\n###### asociaMsiniestroReferenciado ######"
			+ "\n##########################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para asociar al Siniestro Maestro para GMMI
	* @param cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac,aaapertu, status, nmsinies, nmsiniesRef
	* @return exito si se asocia el siniestro
	*/
	public String actualizaDatosGeneralesSiniestro() throws Exception {
		logger.debug(""
			+ "\n##############################################"
			+ "\n###### actualizaDatosGeneralesSiniestro ######"
		);
		logger.debug("Params: {}", params);
		try {
			Date dFeocurre = renderFechas.parse(params.get("feocurre"));
			String valor = null;
			if(!params.get("nmautser").toString().equalsIgnoreCase("N/A")){
				valor = params.get("nmautser");
			}
			siniestrosManager.actualizaDatosGeneralesSiniestro(
				params.get("cdunieco"), params.get("cdramo"),
				params.get("estado"), params.get("nmpoliza"),
				params.get("nmsuplem"),params.get("aaapertu"),
				params.get("nmsinies"), dFeocurre,
				params.get("nmreclamo"), params.get("cdicd"),
				params.get("cdicd2"), params.get("cdcausa"),
				params.get("cdgarant"), params.get("cdconval"),
				valor, params.get("cdperson"), params.get("tipoProceso"),
				params.get("complemento"));
			
			String formatoFeEgreso;
			if(params.get("feegreso").length() > 0){
				formatoFeEgreso = params.get("feegreso").toString().substring(8,10)+"/"+params.get("feegreso").toString().substring(5,7)+"/"+params.get("feegreso").toString().substring(0,4);
			}else{
				formatoFeEgreso = params.get("fefactura").toString().substring(8,10)+"/"+params.get("fefactura").toString().substring(5,7)+"/"+params.get("fefactura").toString().substring(0,4);
			}
			String formatoFechaFactura = params.get("fefactura").toString().substring(8,10)+"/"+params.get("fefactura").toString().substring(5,7)+"/"+params.get("fefactura").toString().substring(0,4);
			Date dFeFactura = renderFechas.parse(formatoFechaFactura);
			Date dFeEgreso = renderFechas.parse(formatoFeEgreso); 
			
			siniestrosManager.guardaListaFacMesaControl(params.get("ntramite"), params.get("nfactura"), dFeFactura , params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"),params.get("tipoMoneda"),params.get("tasacamb"),params.get("ptimporta"),params.get("dctonuex"),dFeEgreso, params.get("diasdedu"),null);
			success = true;
		}catch(Exception e){
			logger.error("Error en actualizaDatosGeneralesSiniestro {}", e.getMessage(), e);
		}
		logger.debug(""
			+ "\n###### actualizaDatosGeneralesSiniestro ######"
			+ "\n##############################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para guardar la informacion de la factura
	* @param params y parametros
	* @return guardamos la informacion en TVALOSIN, MAUTSINI
	*/
	public String guardaFacturaTramite() throws ApplicationException{
		logger.debug(""
		+ "\n######################################"
		+ "\n######   guardaFacturaTramite   ######"
		);
		logger.debug("Params: {} parametros :{}", params, parametros);
		try {
			Date feegreso = null;
			if(params.get("feegreso").length() > 0){
				feegreso = renderFechas.parse(params.get("feegreso"));
			}else{
				feegreso = renderFechas.parse(params.get("fefactura"));
			}
			siniestrosManager.guardaListaFacMesaControl(params.get("ntramite"), params.get("nfactura"), renderFechas.parse(params.get("fefactura")), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"),params.get("tipoMoneda"),params.get("tasacamb"),params.get("ptimporta"),params.get("dctonuex"),feegreso,params.get("diasdedu"),null);
			
			List<Map<String,String>> asegurados = siniestrosManager.listaSiniestrosTramite2(params.get("ntramite"), params.get("nfactura"),null);
			
			for(int i =0; i < asegurados.size();i++){
				String munSiniestro=asegurados.get(i).get("NMSINIES")+"";
				if(!munSiniestro.equalsIgnoreCase("null")){
					Map<String,Object>paramsTvalosin = new HashMap<String,Object>();
					paramsTvalosin.put("pv_cdunieco"  , asegurados.get(i).get("CDUNIECO"));
					paramsTvalosin.put("pv_cdramo"    , asegurados.get(i).get("CDRAMO"));
					paramsTvalosin.put("pv_aaapertu"  , asegurados.get(i).get("AAAPERTU"));
					paramsTvalosin.put("pv_status"    , asegurados.get(i).get("STATUS"));
					paramsTvalosin.put("pv_nmsinies"  , asegurados.get(i).get("NMSINIES"));
					paramsTvalosin.put("pv_cdtipsit"  , asegurados.get(i).get("CDTIPSIT"));
					paramsTvalosin.put("pv_nmsuplem"  , asegurados.get(i).get("NMSUPLEM"));
					paramsTvalosin.put("pv_cdusuari"  , null);
					paramsTvalosin.put("pv_feregist"  , null);
					paramsTvalosin.put("pv_otvalor01" , parametros.get("pv_otvalor01"));
					paramsTvalosin.put("pv_otvalor02" , parametros.get("pv_otvalor02"));
					paramsTvalosin.put("pv_otvalor03" , parametros.get("pv_otvalor03"));
					paramsTvalosin.put("pv_otvalor04" , params.get("ntramite"));
					paramsTvalosin.put("pv_otvalor05" , params.get("nfactura"));
					paramsTvalosin.put("pv_accion_i"  , "I");
					kernelManager.PMovTvalosin(paramsTvalosin);
					
					siniestrosManager.P_MOV_MAUTSINI(asegurados.get(i).get("CDUNIECO"), asegurados.get(i).get("CDRAMO"), asegurados.get(i).get("ESTADO"), 
						asegurados.get(i).get("NMPOLIZA"), asegurados.get(i).get("NMSUPLEM"), asegurados.get(i).get("NMSITUAC"), asegurados.get(i).get("AAAPERTU"), 
						asegurados.get(i).get("STATUS"), asegurados.get(i).get("NMSINIES"),params.get("nfactura"),
						null,null,null,null,null,
						Constantes.MAUTSINI_AREA_RECLAMACIONES, params.get("autrecla"), Constantes.MAUTSINI_FACTURA, params.get("commenar"), Constantes.INSERT_MODE);
					
					siniestrosManager.P_MOV_MAUTSINI(asegurados.get(i).get("CDUNIECO"), asegurados.get(i).get("CDRAMO"), asegurados.get(i).get("ESTADO"), 
						asegurados.get(i).get("NMPOLIZA"), asegurados.get(i).get("NMSUPLEM"), asegurados.get(i).get("NMSITUAC"), asegurados.get(i).get("AAAPERTU"), 
						asegurados.get(i).get("STATUS"), asegurados.get(i).get("NMSINIES"), params.get("nfactura"),
						null,null,null,null,null,
						Constantes.MAUTSINI_AREA_MEDICA, params.get("autmedic"), Constantes.MAUTSINI_FACTURA, params.get("commenme"), Constantes.INSERT_MODE);
				}
			}
			actualizaMesaControlSiniestro(params.get("ntramite"));
		}catch( Exception e){
			logger.error("Error en guardaListaTramites : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
		+ "\n######   guardaFacturaTramite   ######"
		+ "\n#######################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para guardar la informacion de la factura
	* @param params y parametros
	* @return guardamos la informacion en TVALOSIN, MAUTSINI
	*/
	public String obtieneMensajeMautSini() throws ApplicationException{
		logger.debug(""
		+ "\n######################################"
		+ "\n######  obtieneMensajeMautSini  ######"
		);
		logger.debug("Params: {}", params);
		try {
			loadList = siniestrosManager.obtenerFacturasTramite(params.get("ntramite"));
			String mensajeRespuesta ="";
			for(int i=0; i< loadList.size();i++){
				List<Map<String,String>> asegurados = siniestrosManager.listaSiniestrosTramite2(params.get("ntramite"), loadList.get(i).get("NFACTURA"),null);
				for(int j =0; j < asegurados.size();j++){
					Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
							asegurados.get(j).get("CDUNIECO"), asegurados.get(j).get("CDRAMO"), asegurados.get(j).get("ESTADO"),
							asegurados.get(j).get("NMPOLIZA"), asegurados.get(j).get("NMSUPLEM"),asegurados.get(j).get("NMSITUAC"),
							asegurados.get(j).get("AAAPERTU"), asegurados.get(j).get("STATUS"), asegurados.get(j).get("NMSINIES"), loadList.get(i).get("NFACTURA"));
					String respuesta = "Factura "+loadList.get(i).get("NFACTURA")+ " "+autorizacionesFactura.get("COMMENME")+"\n";
					mensajeRespuesta = mensajeRespuesta + respuesta;
				}
			}
			mensaje = mensajeRespuesta;
		}catch( Exception e){
			logger.error("Error obtieneMensajeMautSini : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
		+ "\n######   obtieneMensajeMautSini  ######"
		+ "\n#######################################"
		);
		success = true;
		return SUCCESS;
	}
	
	public void actualizaMesaControlSiniestro (String ntramiteProceso){
		logger.debug(""
			+ "\n###########################################"
			+ "\n###### actualizaMesaControlSiniestro ######"
		);
		logger.debug("Actualizar Mesa de Control : {}", ntramiteProceso);
		try{
			List<Map<String,String>> slist1;
			slist1 = siniestrosManager.obtenerFacturasTramite(ntramiteProceso);
			double SumaTotal = 0d;
			String nfacturaInd = null;
			for(int i=0; i< slist1.size();i++){
				SumaTotal += Double.parseDouble(slist1.get(i).get("PTIMPORT"));
				nfacturaInd = slist1.get(i).get("NFACTURA");
			}
			logger.debug("Suma Total : {}", SumaTotal);
			logger.debug("Total Facturas : {}",slist1.size());
			//modificamos la mesa de control valores ot
			List<MesaControlVO> lista = siniestrosManager.getConsultaListaMesaControl(ntramiteProceso);
			logger.debug("Total Registro MC : {}",lista.size());
			
			HashMap<String, Object> modMesaControl = new HashMap<String, Object>();
			modMesaControl.put("pv_ntramite_i",ntramiteProceso);
			modMesaControl.put("pv_cdunieco_i",lista.get(0).getCduniecomc());
			modMesaControl.put("pv_cdramo_i",lista.get(0).getCdramomc());
			modMesaControl.put("pv_estado_i",lista.get(0).getEstadomc());
			modMesaControl.put("pv_nmpoliza_i",lista.get(0).getNmpolizamc());
			modMesaControl.put("pv_nmsuplem_i",lista.get(0).getNmsuplemmc());
			modMesaControl.put("pv_nmsolici_i",lista.get(0).getNmsolicimc());
			modMesaControl.put("pv_cdtipsit_i",lista.get(0).getCdtipsitmc());
			modMesaControl.put("pv_cdsucadm_i",lista.get(0).getCdsucadmmc());
			modMesaControl.put("pv_cdsucdoc_i",lista.get(0).getCdsucdocmc());
			modMesaControl.put("pv_cdtiptra_i",lista.get(0).getCdtiptramc());
			modMesaControl.put("pv_ferecepc_i",renderFechas.parse(lista.get(0).getFerecepcmc()));
			modMesaControl.put("pv_nombre_i",lista.get(0).getNombremc());
			modMesaControl.put("pv_festatus_i",renderFechas.parse(lista.get(0).getFecstatumc()));
			modMesaControl.put("pv_status_i",lista.get(0).getStatusmc());
			modMesaControl.put("pv_otvalor01_i",lista.get(0).getOtvalor01mc());
			modMesaControl.put("pv_otvalor02_i",lista.get(0).getOtvalor02mc());
			modMesaControl.put("pv_otvalor03_i",SumaTotal+"");
			modMesaControl.put("pv_otvalor04_i",lista.get(0).getOtvalor04mc());
			modMesaControl.put("pv_otvalor05_i",lista.get(0).getOtvalor05mc());
			modMesaControl.put("pv_otvalor06_i",lista.get(0).getOtvalor06mc());
			modMesaControl.put("pv_otvalor07_i",lista.get(0).getOtvalor07mc());
			modMesaControl.put("pv_otvalor08_i",(slist1.size()>1)?null:nfacturaInd);
			modMesaControl.put("pv_otvalor09_i",lista.get(0).getOtvalor09mc());
			modMesaControl.put("pv_otvalor10_i",lista.get(0).getOtvalor10mc());
			modMesaControl.put("pv_otvalor11_i",lista.get(0).getOtvalor11mc());
			modMesaControl.put("pv_otvalor15_i",lista.get(0).getOtvalor15mc());
			modMesaControl.put("pv_otvalor20_i",lista.get(0).getOtvalor20mc());
			siniestrosManager.actualizaValorMC(modMesaControl);
		}catch( Exception e){
			logger.error("Error al actualizar la Mesa de Control: {}", e.getMessage(), e);
		}
		logger.debug(""
			+ "\n###### actualizaMesaControlSiniestro ######"
			+ "\n###########################################"
		);
	}

	public String detalleSiniestro() throws Exception
	{
		logger.debug(""
			+ "\n####################################"
			+ "\n###### DetalleSiniestroAction ######"
		);
		logger.debug("Params: {}", params);
		UserVO usuario= (UserVO)session.get("USUARIO");
		params.put("cdrol", usuario.getRolActivo().getClave());
		
		if(!params.containsKey("nmsinies")){
			try{
				String ntramite = params.get("ntramite");
				usuario= (UserVO)session.get("USUARIO");
				params.put("cdrol", usuario.getRolActivo().getClave());
				Map<String,String> paramsRes = (HashMap<String, String>) siniestrosManager.obtenerLlaveSiniestroReembolso(ntramite);
				
				for(Entry<String,String>en:paramsRes.entrySet()){
					params.put(en.getKey().toLowerCase(),en.getValue());
					params.put("cdrol", usuario.getRolActivo().getClave());
				}
			}catch(Exception ex){
				logger.error("Error la obtener clave de siniestro : {}", ex.getMessage(), ex);
			}
		}
		logger.debug("Params obtenidos: {}", params);
		logger.debug(""
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n####################################"
				);
		success = true;
		return SUCCESS;
	}
	
	
	public String loadInfoGeneralReclamacion() {
		logger.debug(""
			+ "\n########################################"
			+ "\n###### loadInfoGeneralReclamacion ######"
		);
		logger.debug(""
			+ "\n###### loadInfoGeneralReclamacion ######"
			+ "\n########################################"
		);
		success = true;
		return SUCCESS;
	}
	
	public String entradaRevisionAdmin(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  entradaRevisionAdmin  ######"
		);
		logger.debug("paramis RevisionAdmin: {}", params);
		try {
			UserVO usuario  = (UserVO)session.get("USUARIO");
			String cdrol    = usuario.getRolActivo().getClave();
			params.put("cdrol", usuario.getRolActivo().getClave());
			String pantalla = "AFILIADOS_AGRUPADOS";
			String seccion  = "COLUMNAS";
			String cdunieco  = params.get("cdunieco");
			String cdramo    = params.get("cdramo");
			String estado    = params.get("estado");
			String nmpoliza  = params.get("nmpoliza");
			
			List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
					null, null, null, null, null, cdrol, pantalla, seccion, null);
			
			for(ComponenteVO com:componentes){
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
			
			logger.debug("Resultado de imap : {}", imap);
		}catch( Exception e){
			logger.error("Error al mostrar los datos {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######  entradaRevisionAdmin  ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}
	
	public String entradaRevisionAdminConsulta(){
		logger.debug(""
			+ "\n##########################################"
			+ "\n###### entradaRevisionAdminConsulta ######"
		);
		logger.debug("EntradaRevisionAdminConsulta : {}",params);
		try {
			UserVO usuario  = (UserVO)session.get("USUARIO");
			String cdrol    =  RolSistema.COORDINADOR_SINIESTROS.getCdsisrol();
			String pantalla = "AFILIADOS_AGRUPADOS";
			String seccion  = "COLUMNAS";
			String cdunieco  = params.get("cdunieco");
			String cdramo    = params.get("cdramo");
			String estado    = params.get("estado");
			String nmpoliza  = params.get("nmpoliza");
			
			List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
				null, null, null, null, null, cdrol, pantalla, seccion, null);

			for(ComponenteVO com:componentes){
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

			logger.debug("Resultado: {} ",imap);
			//siniestrosManager.guardaListaTramites(params, deleteList, saveList);
		}catch( Exception e){
			logger.error("Error en guardaListaTramites : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### entradaRevisionAdminConsulta ######"
			+ "\n##########################################"
		);
		success = true;
		return SUCCESS;
	}
	
	public String loadListaFacturasTramite(){
		logger.debug(""
			+ "\n######################################"
			+ "\n###### loadListaFacturasTramite ######"
		);
		logger.debug("Valores de entrada: {}",params);
		try {
			loadList = siniestrosManager.P_GET_FACTURAS_SINIESTRO(params.get("cdunieco"), params.get("cdramo"), params.get("estado"), params.get("nmpoliza"), params.get("nmsuplem"), params.get("nmsituac"), params.get("aaapertu"), params.get("status"), params.get("nmsinies"), params.get("cdtipsit")); 
			logger.debug("Valores recuperados: {}",loadList);
		}catch( Exception e){
			logger.error("Error loadListaFacturasTramite : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### loadListaFacturasTramite ######"
			+ "\n######################################"
		);
		success = true;
		return SUCCESS;
	}

	public String obtieneDatosGeneralesSiniestro() throws Exception {
		logger.debug(""
			+ "\n############################################"
			+ "\n###### obtieneDatosGeneralesSiniestro ######"
		);
		logger.debug("Valores de entrada: {}",params);
		try {
			siniestro = siniestrosManager.obtieneDatosGeneralesSiniestro(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsituac"), params.get("nmsuplem"),
					params.get("status"), params.get("aaapertu"),
					params.get("nmsinies"), params.get("ntramite"));
			success = true;
		}catch(Exception e){
			logger.error("Error en obtieneDatosGeneralesSiniestro : {}", e.getMessage(), e);
			
		}
		logger.debug(""
			+ "\n###### obtieneDatosGeneralesSiniestro ######"
			+ "\n############################################"
		);
		return SUCCESS;
	}
	
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
			logger.error("Error al generar JSON de LoadForm {}", e.getMessage(), e);
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
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}