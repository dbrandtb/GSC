package mx.com.gseguros.portal.general.controller;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;
import mx.com.gseguros.portal.general.service.RecibosManager;

import com.opensymphony.xwork2.ActionSupport;

public class RecibosAction extends ActionSupport {

	@SuppressWarnings("unused")
	private org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(RecibosAction.class);
	
	private static final long serialVersionUID = 1L;

	private RecibosManager recibosManager;

	private Map<String, String> params;

	private List<ReciboVO> recibos;
	
	private List<DetalleReciboVO> detallesRecibo;
	
	
	/**
	 * Action usado para la carga de la seccion de recibos, propagando parametros
	 */
	public String loadRecibos() throws Exception {
		return SUCCESS;
	}

	public String obtieneRecibos() throws Exception {
		recibos = recibosManager.obtieneRecibos(params.get("cdunieco"),
				params.get("cdramo"), params.get("nmpoliza"),
				params.get("nmsuplem"));
		return SUCCESS;
	}

	public String obtieneDetalleRecibo() throws Exception {
		detallesRecibo = recibosManager.obtieneDetallesRecibo(params.get("cdunieco"),
				params.get("cdramo"), params.get("estado"),
				params.get("nmpoliza"), params.get("nmrecibo"));
		return SUCCESS;
	}

	// Getters and setters:
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<ReciboVO> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<ReciboVO> recibos) {
		this.recibos = recibos;
	}
	
	public List<DetalleReciboVO> getDetallesRecibo() {
		return detallesRecibo;
	}

	public void setDetallesRecibo(List<DetalleReciboVO> detallesRecibo) {
		this.detallesRecibo = detallesRecibo;
	}

	/**
	 * recibosManager setter
	 * 
	 * @param recibosManager
	 */
	public void setRecibosManager(RecibosManager recibosManager) {
		this.recibosManager = recibosManager;
	}

}