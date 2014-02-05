package mx.com.gseguros.portal.emision.service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.ws.client.Ice2sigsWebServices;


public interface EmisionManager {

	/**
	 * 
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param op
	 * @param userVO
	 * @return
	 */
	public boolean ejecutaWSclienteSalud(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, Ice2sigsWebServices.Operacion op, UserVO userVO);
	
}
