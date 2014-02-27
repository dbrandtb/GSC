package mx.com.gseguros.portal.siniestros.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONUtil;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DetalleSiniestroAction.class);

	private transient SiniestrosManager siniestrosManager;

	private boolean success;

	private boolean esHospitalario;

	private HashMap<String, String> loadForm;
	
	private List<HashMap<String, String>> listado;
	
	private Map<String, String> params;
	
	
	
	public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }
	
	
	public String loadInfoGeneralReclamacion() {
		
		//
		
		String nmsinies = params.get("nmsinies");
		String nmtramite = params.get("nmtramite");
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
		listado = new ArrayList<HashMap<String, String>>();
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

			listado.add(elements);

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

			listado.add(elements);
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

	public List<HashMap<String, String>> getLoadList() {
		return listado;
	}

	public void setLoadList(List<HashMap<String, String>> loadList) {
		this.listado = loadList;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}