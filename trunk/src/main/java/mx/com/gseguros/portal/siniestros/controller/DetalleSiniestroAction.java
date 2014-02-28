package mx.com.gseguros.portal.siniestros.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DetalleSiniestroAction.class);

	private transient SiniestrosManager siniestrosManager;
	private PantallasManager       pantallasManager;

	private boolean success;

	private boolean esHospitalario;

	private HashMap<String, String> loadForm;
	
	private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<HashMap<String, String>> deleteList;
	
	private HashMap<String, String> params;
	private HashMap<String,Object> paramsO;
	
	private Map<String,Item> imap;
	
	
	
	public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }
	
	
	public String loadInfoGeneralReclamacion() {
		
		//
		
		//String nmsinies = params.get("nmsinies");
		//String nmtramite = params.get("nmtramite");
		success = true;
		return SUCCESS;
	}
	
	
	public String entradaCalculos() {

		esHospitalario = false;
		loadForm = new HashMap<String, String>();

		if (esHospitalario) {
			loadForm.put("asegurado", "Manuel,lopez");
			loadForm.put("deducible", "5");
			loadForm.put("copago", "54");
		} else {
			loadForm.put("proveedor", "Nombre, Proveedor");
			loadForm.put("isrProveedor", "Isr");
			loadForm.put("impuestoCedular", "Imp, ced");
			loadForm.put("iva", "17.5");
		}

		success = true;
		return SUCCESS;
	}
	
	
	public String obtieneCalculos() {
		loadList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> elements = new HashMap<String, String>();
		try {
			elements.put("cpt", "1");
			elements.put("cantidad", "1111");
			elements.put("arancel", "11111111");
			elements.put("subtotalArancel", "11");
			elements.put("descuento", "111111111");
			elements.put("subtotalDescuento", "11111111111");
			elements.put("porcentajeCopago", "11111111");
			elements.put("copago", "11111111111");
			elements.put("copagoAplicado", "1111111111111");
			elements.put("subtotal", "1111111");
			elements.put("isr", "11111111");
			elements.put("cedular", "111");
			elements.put("subtotalImpuestos", "1111");
			elements.put("iva", "1111");
			elements.put("total", "11111");
			elements.put("facturado", "11111");
			elements.put("autorizado", "11111");
			elements.put("valorUtilizar", "11111111111");

			loadList.add(elements);

			elements = new HashMap<String, String>();
			elements.put("cpt", "2");
			elements.put("cantidad", "222222");
			elements.put("arancel", "22222222");
			elements.put("subtotalArancel", "22");
			elements.put("descuento", "2222222");
			elements.put("subtotalDescuento", "2222222222");
			elements.put("porcentajeCopago", "222222222");
			elements.put("copago", "2222222222");
			elements.put("copagoAplicado", "222222222222");
			elements.put("subtotal", "222222222222");
			elements.put("isr", "22222222222");
			elements.put("cedular", "222");
			elements.put("subtotalImpuestos", "2222");
			elements.put("iva", "2222");
			elements.put("total", "22222");
			elements.put("facturado", "2222");
			elements.put("autorizado", "22222");
			elements.put("valorUtilizar", "2222222");

			loadList.add(elements);
			// List<AutorizacionServicioVO> lista =
			// siniestrosManager.getConsultaAutorizacionesEsp(params.get("nmautser"));
			// if(lista!=null && !lista.isEmpty()) datosAutorizacionEsp =
			// lista.get(0);
		} catch (Exception e) {
			logger.error("Error al obtener los datos de Autorizaci�n de Servicio en Especifico", e);
			success = false;
			return SUCCESS;
		}
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

	public boolean isEsHospitalario() {
		return esHospitalario;
	}

	public void setEsHospitalario(boolean esHospitalario) {
		this.esHospitalario = esHospitalario;
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
	
}