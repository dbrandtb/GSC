package mx.com.gseguros.portal.siniestros.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DetalleSiniestroAction.class);

	private transient SiniestrosManager siniestrosManager;
	private PantallasManager       pantallasManager;
	
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");

	private boolean success;

	private HashMap<String, String> loadForm;
	
	private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<HashMap<String, String>> deleteList;
	
	private HashMap<String, String> params;
	private HashMap<String,Object> paramsO;
	
	private Map<String,Item> imap;
	
	private List<Map<String, String>> siniestro;
	
	private List<HistorialSiniestroVO> historialSiniestro;
	
	
	public String execute() throws Exception
	{
		logger.debug(""
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n######                        ######"
				);
		logger.debug("params:"+params);
		if(!params.containsKey("nmsinies"))
		{
			try
			{
				String ntramite = params.get("ntramite");
				params = (HashMap<String, String>) siniestrosManager.obtenerLlaveSiniestroReembolso(ntramite);
				Map<String,String>aux=new HashMap<String,String>();
				for(Entry<String,String>en:params.entrySet())
				{
					aux.put(en.getKey().toLowerCase(),en.getValue());
				}
				params=(HashMap<String, String>) aux;
				logger.debug("params obtenidos:"+params);
			}
			catch(Exception ex)
			{
				logger.error("error al obtener clave de siniestro para la pantalla del tabed panel",ex);
			}
		}
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
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	for(ComponenteVO com:componentes)
	    	{
	    		com.setWidth(100);
	    	}
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, false, true,false, false);
	    	
	    	imap = new HashMap<String,Item>();
	    	imap.put("gridColumns",gc.getColumns());
	    	
	    	pantalla = "DETALLE_FACTURA";
	    	seccion  = "BOTONES_CONCEPTOS";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, false, false,false, true);
	    	
	    	imap.put("conceptosButton",gc.getButtons());
	   		
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
		   		List<Map<String, String>> result = siniestrosManager.P_GET_FACTURAS_SINIESTRO(params.get("cdunieco"), params.get("cdramo"), params.get("estado"), params.get("nmpoliza"), params.get("nmsuplem"), params.get("nmsituac"), params.get("aaapertu"), params.get("status"), params.get("nmsinies")); 
		   		loadList = new ArrayList<HashMap<String, String>>();
		   		
		   		HashMap<String, String> mapa =null;
		   		for(Map item: result){
		   			mapa =  new HashMap<String, String>();
		   			mapa.putAll(item);
		   			loadList.add(mapa);
		   		}
	   		
	   	}catch( Exception e){
	   		logger.error("Error en loadListaFacturasTramite",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	}
	
	public String guardaFacturaTramite(){
	   	
	   	try {
	   		siniestrosManager.guardaListaFacMesaControl(params.get("ntramite"), params.get("nfactura"), params.get("fefactura"), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"));
	   	}catch( Exception e){
	   		logger.error("Error en guardaListaTramites",e);
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


	public List<HashMap<String, String>> getLoadList() {
		return loadList;
	}


	public void setLoadList(List<HashMap<String, String>> loadList) {
		this.loadList = loadList;
	}


	public List<HashMap<String, String>> getSaveList() {
		return saveList;
	}


	public void setSaveList(List<HashMap<String, String>> saveList) {
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
	
}