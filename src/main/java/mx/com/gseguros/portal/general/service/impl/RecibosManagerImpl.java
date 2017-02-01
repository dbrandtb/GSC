package mx.com.gseguros.portal.general.service.impl;

import java.util.HashMap;
import java.util.List;

import mx.com.gseguros.portal.general.dao.RecibosDAO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;
import mx.com.gseguros.portal.general.service.RecibosManager;

public class RecibosManagerImpl implements RecibosManager {

	private RecibosDAO recibosDAO;
	
	@Override
	public List<ReciboVO> obtieneRecibos(String cdunieco, String cdramo, String nmpoliza, String nmsuplem) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i",   cdramo);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		return recibosDAO.obtieneRecibos(params);
	}

	@Override
	public List<DetalleReciboVO> obtieneDetallesRecibo(String cdunieco, String cdramo, String estado, String nmpoliza, String nmrecibo) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdUnieco_i", cdunieco);
		params.put("pv_cdRamo_i", cdramo);
		params.put("pv_Estado_i", estado);
		params.put("pv_NmPoliza_i", nmpoliza);
		params.put("pv_nmRecibo_i", nmrecibo);
		return recibosDAO.obtieneDetalleRecibo(params);
	}

	
	/**
	 * recibosDAO setter
	 * @param recibosDAO
	 */
	public void setRecibosDAO(RecibosDAO recibosDAO) {
		this.recibosDAO = recibosDAO;
	}

}