package mx.com.gseguros.ws.autosgs.service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.ws.autosgs.emision.model.EmisionAutosVO;

public interface EmisionAutosService {

	/**
	 * Ejecuta el WS de Autos de SIGS
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param ntramite
	 * @param userVO
	 * @return
	 */
	public EmisionAutosVO cotizaEmiteAutomovilWS(String cdunieco, String cdramo,
			String estado, String nmpoliza, String tipopol, String nmsuplem, String ntramite, String cdtipsit, UserVO userVO);
	
	public boolean enviaRecibosAutosSigs(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmpoliex);
}
