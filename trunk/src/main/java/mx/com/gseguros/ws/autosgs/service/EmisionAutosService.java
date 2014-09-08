package mx.com.gseguros.ws.autosgs.service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.ws.autosgs.model.EmisionAutosVO;

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
			String estado, String nmpoliza, String nmsuplem, String ntramite, UserVO userVO);
}
