package mx.com.gseguros.ws.autosgs.dao;

import java.util.Map;


public interface AutosDAOSIGS {
		
	public Integer endosoDomicilio(Map<String, Object> params) throws Exception;

	public Integer cambioDomicilioCP(Map<String, Object> params) throws Exception;

	public Integer insertaReciboAuto(Map<String, Object> params) throws Exception;

	public Integer confirmaRecibosAuto(Map<String, Object> params) throws Exception;
	
}
