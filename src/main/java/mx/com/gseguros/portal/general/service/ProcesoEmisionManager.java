package mx.com.gseguros.portal.general.service;

import java.util.Map;


public interface ProcesoEmisionManager {
	
	public Map<String, String> emitir(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String cdtipsit, String ntramite, String cdusuari, String cdsisrol, String cdelemento,
			boolean esFlotilla, String tipoGrupoInciso) throws Exception;
	
}